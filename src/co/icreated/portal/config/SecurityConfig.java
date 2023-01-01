package co.icreated.portal.config;

import java.security.Key;

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
import co.icreated.portal.security.SessionUserDetailsService;
import co.icreated.portal.utils.IdempierePasswordEncoder;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


  public final static Key SECRET = Keys.secretKeyFor(SignatureAlgorithm.HS256);

  @Value("${jwt.expiration-time}")
  private long jwtExpirationTime;


  @Autowired
  SessionUserDetailsService sessionUserDetailsService;

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(authenticationProvider());
  }


  @Bean
  public DaoAuthenticationProvider authenticationProvider() {

    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(sessionUserDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
  }


  @Bean
  public IdempierePasswordEncoder passwordEncoder() {

    return new IdempierePasswordEncoder();
  }


  @Bean
  CorsConfigurationSource corsConfigurationSource() {

    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
    return source;
  }


  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/user/password/emaillink")
        .permitAll().antMatchers(HttpMethod.POST, "/api/user/password/validate").permitAll()
        .antMatchers("/api/swagger-ui.html").authenticated().antMatchers("/api/v2/api-docs")
        .authenticated().and().httpBasic();



    http.cors().and().csrf().disable()
        .addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtExpirationTime))
        .addFilter(new JwtAuthorizationFilter(authenticationManager(), sessionUserDetailsService))
        .authorizeRequests().antMatchers("/api/**").authenticated().and().sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }

  // @Override
  // public void configure(AuthenticationManagerBuilder auth) throws Exception {
  // auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
  // }



}
