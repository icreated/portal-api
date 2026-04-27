package co.icreated.portal.security;

import java.io.IOException;
import java.security.Key;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.icreated.portal.api.model.UserDto;
import co.icreated.portal.bean.Credentials;
import co.icreated.portal.bean.SessionUser;
import io.jsonwebtoken.Jwts;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final Key signingKey;
  private final long jwtExpirationTime;

  public JwtAuthenticationFilter(AuthenticationManager authenticationManager, Key signingKey,
      long jwtExpirationTime) {

    this.setAuthenticationManager(authenticationManager);
    this.signingKey = signingKey;
    this.jwtExpirationTime = jwtExpirationTime;
    setFilterProcessesUrl("/api/login");
  }


  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException {

    if (!request.getMethod().equals("POST")) {
      throw new AuthenticationServiceException(
          "Authentication method not supported: " + request.getMethod());
    }


    Credentials credentials = null;
    try {
      credentials = new ObjectMapper().readValue(request.getInputStream(), Credentials.class);

    } catch (IOException e) {

      throw new AuthenticationServiceException(
          "Authentication json format not respected: " + request.getMethod());
    }


    UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
        credentials.getUsername(), credentials.getPassword());

    // Allow subclasses to set the "details" property
    setDetails(request, authRequest);

    return this.getAuthenticationManager().authenticate(authRequest);
  }


  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain, Authentication authentication) throws IOException {

    SessionUser user = (SessionUser) authentication.getPrincipal();
    String token = Jwts.builder() //
        .signWith(signingKey) //
        .setSubject(user.getUsername()) //
        .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationTime)) //
        .compact();
    response.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);

    UserDto userDto = new UserDto() //
        .id(user.getUserId()) //
        .username(user.getUsername()) //
        .name(user.getName()) //
        .token(token);

    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    new ObjectMapper().writeValue(response.getWriter(), userDto);
  }
}
