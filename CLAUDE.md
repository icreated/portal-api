# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project context

Icreated Portal API (IPA) — a REST API **OSGi plugin** for the Idempiere ERP, packaged as `eclipse-plugin` via Eclipse Tycho. It is built as a sibling of the Idempiere source tree (parent POM at `../idempiere/org.idempiere.parent/pom.xml`). The companion frontend lives at `../portal-frontend` and owns the canonical `openapi.yaml`.

Bundle: `co.icreated.portal.api`, web context path `portal`, dispatcher servlet mounted at `/api/*`. Swagger UI: `http://localhost:8080/portal/api`.

## Build & run

- Build with Maven from this directory: standard Tycho lifecycle (`mvn clean install`). The build is only meaningful when invoked under the Idempiere parent reactor — it has no standalone parent.
- The `validate` phase performs two side-effects you should know about:
  - `maven-dependency-plugin` copies a fixed list of jars (Spring, Spring Security, JJWT, MapStruct, Hibernate Validator, Swagger, Jackson JSR310, classgraph, …) into `WEB-INF/lib/` with versions stripped. These are listed by name in `META-INF/MANIFEST.MF` `Bundle-ClassPath` — adding a new runtime dep means editing **both** `pom.xml` and `MANIFEST.MF`.
  - `maven-resources-plugin` copies `../portal-frontend/openapi.yaml` into the project root. Do not hand-edit `openapi.yaml` here; edit it in `portal-frontend` and let the build copy it.
- `openapi-generator-maven-plugin` (`generate` phase) regenerates DTOs and API interfaces into `src/co/icreated/portal/api/model/` (DTOs, `*Dto` suffix) and `src/co/icreated/portal/api/service/` (Spring `*Api` interfaces, `interfaceOnly=true`). These directories are generated — controllers implement the `*Api` interfaces, never edit the generated files.
- Code formatting is enforced by `formatter-maven-plugin` using `eclipse-java-google-style.xml`, and imports by `impsort-maven-plugin` (which **excludes** the two generated packages above). Both run on every build.
- Eclipse import: requires the MapStruct annotation processor plugin in the IDE; `m2e.apt.activation=jdt_apt` is set in the POM.
- **No special `--add-opens` flag is required** since the OSGi/Spring bridge was rewritten to use `ImportBeanDefinitionRegistrar` (see below). The README's instruction to add `--add-opens java.base/sun.reflect.annotation=ALL-UNNAMED` is obsolete and was needed only by the previous reflective-annotation-mutation mechanism.
- There is no test source folder; no JUnit suite. `generateModelTests=true` only emits tests for generated DTOs. README's curl example against `/api/login` is the smoke-test path.

## Architecture

### OSGi/Spring bridge (the unusual part)
Spring's classpath component scan does not work under OSGi (Spring's `ContextLoader` cannot see bundle classes). The bridge has two pieces:
1. `Activator.start()` calls `Adempiere.startup(false)` to boot Idempiere before Spring touches anything.
2. `PortalConfig` is annotated `@Import(ComponentScanRegistrar.class)`. `ComponentScanRegistrar` is an `ImportBeanDefinitionRegistrar` that, during Spring context refresh, calls `Activator.getSpringComponents()` (a ClassGraph scan of `co.icreated.portal` for `@Component`-annotated classes — `PortalConfig` itself is filtered out) and registers each result via `AnnotatedBeanDefinitionReader.register(...)`. That reader honors `@Scope`, `@Lazy`, `@Primary`, etc.

This replaces an earlier hack that mutated the `@Import` annotation's `value` array via reflection on `sun.reflect.annotation.AnnotationInvocationHandler`. That mechanism broke under JDK 17+ strict module access (`InaccessibleObjectException`) and required an `--add-opens java.base/sun.reflect.annotation=ALL-UNNAMED` JVM flag. The current registrar uses only public Spring API and works on any modern JDK.

`PortalConfig` is the root config (referenced from `WEB-INF/web.xml` via `contextConfigLocation`). It loads `webportal.properties` and exposes the Idempiere context as a Spring bean named `"ctx"` (a `Properties` with `#AD_Client_ID` / `#AD_Org_ID`). Services receive this `Properties ctx` via constructor injection — that is how Idempiere context flows into business code.

### Layering
`controller` → `service` → Idempiere model (`M*` POs) via `PQuery` / `QueryTool`, with `mapper` (MapStruct, `componentModel="spring"`) translating between PO and DTO at the service↔controller boundary.

- **Controllers** implement the generated `*Api` interface and the `Authenticated` mixin (`getSessionUser()` reads the JWT principal from `SecurityContextHolder`). They are plain `@RestController` singletons — no scoped proxy is needed because nothing in this project (no `@Transactional`, no `@Validated`, no aspect) wraps them.
- **Services** (`@Service`) take `Properties ctx` and any required mappers/services via constructor injection. They never call `Env.getCtx()` directly for the request context — they use the injected `ctx`.
- **Mappers** are abstract MapStruct classes that may inject other Spring beans (`@Autowired`) and use `@AfterMapping` to enrich the DTO with related-entity lookups (e.g. addresses, taxes, payments).

### Idempiere data access
- **`co.icreated.portal.utils.PQuery`** extends Idempiere's `Query`. Idempiere's stock `Query` loses the custom `ctx` on `first()`/`firstOnly()`/`list()` (it falls back to `Env.getCtx()` deep in the call stack). `PQuery` overrides those to re-`putAll(ctx)` onto the loaded PO. **Always use `PQuery`, never `org.compiere.model.Query` directly**, when running queries in this project — the bean-injected `ctx` would otherwise be lost. `list()` re-implements the loop and reaches `Query.buildSQL` / `createResultSet` by reflection because they are private upstream.
- **`QueryTool.nativeList(sql, params, mapper)`** is the escape hatch for raw SQL/views (e.g. `RV_OpenItem`).
- **`co.icreated.portal.utils.Transaction.run(...)`** wraps an Idempiere `Trx` (`Trx.createTrxName` / `commit` / `rollback` / `close`) around a lambda that receives the `trxName`. Use it for any write that needs transactional semantics; on any throwable it rolls back and rethrows as `PortalBusinessException`.

### Security
- Stateless JWT, configured in `SecurityConfig` (still on `WebSecurityConfigurerAdapter` — Spring Security 5.2.x).
- `JwtAuthenticationFilter` issues tokens on `POST /api/login`; `JwtAuthorizationFilter` validates `Authorization: Bearer …` on every other request and puts a `SessionUser` (custom principal carrying `userId`, `partnerId`, `salt`, etc.) into the security context.
- Permit-all carve-outs: `POST /api/users/email/token`, `PUT /api/users/password/**`. Everything else under `/api/**` requires authentication.
- Passwords are checked via `IdempierePasswordEncoder` against `MUser` (Idempiere stores its own salted hash; the encoder is stateful per-request — the salt is set in `UserService.loadUserByUsername`).
- CORS allows `http://localhost:4200` (Angular frontend dev server).

### Exceptions
Throw `PortalNotFoundException` / `PortalInvalidInputException` / `PortalBusinessException` from services. They are translated to HTTP responses by `PortalExceptionHandler` (a `@ControllerAdvice`).

## Conventions to follow
- When adding an endpoint: edit `openapi.yaml` in `../portal-frontend` first, rebuild (regenerates DTO + `*Api`), then implement the new method in the matching controller.
- Do not edit anything under `src/co/icreated/portal/api/` — it is regenerated. The `impsort` exclusions for that package are a signal of the same.
- Configuration values (Idempiere client/org, JWT, email templates, AD_Reference IDs) live in `src/webportal.properties` and are read via `@Value("${…}")` or the `ctx` bean.
- New runtime dependencies must be added in three places: `pom.xml` `<artifactItems>` (so the jar is copied into `WEB-INF/lib`), `MANIFEST.MF` `Bundle-ClassPath`, and `MANIFEST.MF` `Import-Package` if needed.
- Do not annotate `PortalConfig` itself as scannable — `Activator.getSpringComponents()` explicitly filters it out, since it is the root config that receives the scan result.
