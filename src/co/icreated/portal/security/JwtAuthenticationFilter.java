package co.icreated.portal.security;

import java.io.IOException;
import java.io.PrintWriter;
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

import co.icreated.portal.api.SecurityConfig;
import co.icreated.portal.bean.Credentials;
import co.icreated.portal.bean.FrontendUser;
import co.icreated.portal.bean.SessionUser;
import io.jsonwebtoken.Jwts;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
    private long jwtExpirationTime;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, long jwtExpirationTime) {

        	this.setAuthenticationManager(authenticationManager);
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
			credentials = new ObjectMapper()
			        .readValue(request.getInputStream(), Credentials.class);
			
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
    protected void successfulAuthentication(
            HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain, Authentication authentication) {
    	
        SessionUser user = (SessionUser)authentication.getPrincipal();
        String token = Jwts.builder()
                .signWith(SecurityConfig.SECRET)
                .setSubject(user.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationTime))
                .compact();
        response.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        
        FrontendUser fuser = new FrontendUser(user.getUserId(), user.getUsername(), user.getName(), token);
        
        String body = "";
        ObjectMapper mapper = new ObjectMapper();
        
        try {
			body = mapper.writeValueAsString(fuser);
			
	        PrintWriter out = response.getWriter();
	        response.setContentType("application/json");
	        response.setCharacterEncoding("UTF-8");
	        out.print(body);
	        out.flush(); 
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
         
        
    }
}
