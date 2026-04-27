package co.icreated.portal.config;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import co.icreated.portal.security.JwtAuthenticationFilter;
import co.icreated.portal.security.JwtAuthorizationFilter;
import co.icreated.portal.service.UserService;
import co.icreated.portal.utils.IdempierePasswordEncoder;
import io.jsonwebtoken.security.Keys;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Value("${jwt.secret}")
  private String jwtSecret;

  @Value("${jwt.expiration-time}")
  private long jwtExpirationTime;

  @Autowired
  UserService userService;

  @Autowired
  IdempierePasswordEncoder idempierePasswordEncoder;

  @Bean
  Key jwtSigningKey() {
    return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(authenticationProvider());
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {

    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userService);
    authProvider.setPasswordEncoder(idempierePasswordEncoder);
    return authProvider;
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {

    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(List.of("http://localhost:4200"));
    configuration.addAllowedHeader("*");
    configuration.addAllowedMethod("*");
    configuration.setAllowCredentials(true);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    Key signingKey = jwtSigningKey();
    http.cors().configurationSource(corsConfigurationSource()).and().csrf().disable() //
        .addFilter(new JwtAuthenticationFilter(authenticationManager(), signingKey,
            jwtExpirationTime)) //
        .addFilter(new JwtAuthorizationFilter(authenticationManager(), userService, signingKey)) //
        .authorizeRequests() //
        .antMatchers(HttpMethod.POST, "/api/users/email/token").permitAll() //
        .antMatchers(HttpMethod.PUT, "/api/users/password/**").permitAll() //
        .antMatchers("/api/**").authenticated().and().sessionManagement() //
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

  }

  // @Override
  // public void configure(AuthenticationManagerBuilder auth) throws Exception {
  // auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
  // }

}
