package co.icreated.portal.security;

import java.io.IOException;
import java.security.Key;
import java.util.List;
import java.util.logging.Level;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.compiere.util.CLogger;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import co.icreated.portal.bean.SessionUser;
import co.icreated.portal.service.UserService;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

  CLogger log = CLogger.getCLogger(JwtAuthorizationFilter.class);

  static String BEARER_PREFIX = "Bearer ";

  UserService userService;
  Key signingKey;

  public JwtAuthorizationFilter(AuthenticationManager authenticationManager,
      UserService userService, Key signingKey) {
    super(authenticationManager);
    this.userService = userService;
    this.signingKey = signingKey;
  }


  @Override
  protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res,
      FilterChain chain) throws IOException, ServletException {
    String header = req.getHeader(HttpHeaders.AUTHORIZATION);

    if (header == null || !header.startsWith(BEARER_PREFIX)) {
      chain.doFilter(req, res);
      return;
    }

    UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    chain.doFilter(req, res);
  }


  private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {

    String token = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (token != null && token.startsWith(BEARER_PREFIX)) {
      token = token.replace(BEARER_PREFIX, "");
      try {
        String username = Jwts //
            .parserBuilder() //
            .setSigningKey(signingKey) //
            .build() //
            .parseClaimsJws(token) //
            .getBody() //
            .getSubject();


        if (StringUtils.isBlank(username)) {
          return null;
        }

        SessionUser sessionUser = (SessionUser) userService.loadUserByUsername(username);

        return new UsernamePasswordAuthenticationToken(sessionUser, null,
            List.of(new SimpleGrantedAuthority("ROLE_USER")));
      } catch (JwtException exception) {
        // Malformed or expired token is a normal client error, not a server fault.
        log.log(Level.FINE, "Rejected JWT: {0}", exception.getMessage());
      }
    }

    return null;
  }


}
