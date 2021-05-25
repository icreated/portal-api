package co.icreated.portal.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.compiere.util.CLogger;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import co.icreated.portal.api.SecurityConfig;
import co.icreated.portal.bean.SessionUser;
import co.icreated.portal.service.SessionUserDetailsService;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
	
	CLogger log = CLogger.getCLogger(JwtAuthorizationFilter.class);
	SessionUserDetailsService userDetailsService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, SessionUserDetailsService userDetailsService) {
        super(authenticationManager);
        
        this.userDetailsService = userDetailsService;
    }
    

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
    	
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (token != null && token.startsWith("Bearer ")) {
            token = token.replace("Bearer ", "");
            try {
                String username = Jwts.parser()
                        .setSigningKey(SecurityConfig.SECRET)
                        .parseClaimsJws(token)
                        .getBody()
                        .getSubject();

                if ("".equals(username) || username == null) {
                    return null;
                }
                
                SessionUser sessionUser = (SessionUser)this.userDetailsService.loadUserByUsername(username);
        		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

                return new UsernamePasswordAuthenticationToken(sessionUser, null, authorities);
            } catch (JwtException exception) {
                log.log(Level.SEVERE, token, exception.getMessage());
            }
        }

        return null;
    }

    
}