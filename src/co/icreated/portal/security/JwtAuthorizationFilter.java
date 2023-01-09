package co.icreated.portal.security;

import java.io.IOException;
import java.util.ArrayList;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import co.icreated.portal.bean.SessionUser;
import co.icreated.portal.config.SecurityConfig;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

  CLogger log = CLogger.getCLogger(JwtAuthorizationFilter.class);
  
  static String BEARER_PREFIX = "Bearer ";
  
  SessionUserDetailsService userDetailsService;

  public JwtAuthorizationFilter(AuthenticationManager authenticationManager,
      SessionUserDetailsService userDetailsService) {
    super(authenticationManager);

    this.userDetailsService = userDetailsService;
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
        		.setSigningKey(SecurityConfig.SECRET) //
        		.build() //
        		.parseClaimsJws(token) //
        		.getBody() //
        		.getSubject();

        
        if (StringUtils.isBlank(username)) {
          return null;
        }

        SessionUser sessionUser =
            (SessionUser) this.userDetailsService.loadUserByUsername(username);

        return new UsernamePasswordAuthenticationToken(sessionUser, null, List.of(new SimpleGrantedAuthority("ROLE_USER")));
      } catch (JwtException exception) {
        log.log(Level.SEVERE, token, exception.getMessage());
      }
    }

    return null;
  }


}
