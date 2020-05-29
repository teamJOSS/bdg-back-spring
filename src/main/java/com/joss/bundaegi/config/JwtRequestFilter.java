package com.joss.bundaegi.config;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.joss.bundaegi.service.UserService;
import com.joss.bundaegi.utils.JwtTokenUtil;
import com.joss.bundaegi.domain.RestException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException, RestException {
        final String requestTokenHeader = httpServletRequest.getHeader("Authorization");
        String username = null;
        String jwtToken = null;
        // JWT Token is in the form "Bearer token". Remove Bearer word and get
        // only the Token
        System.out.println("doFilterInternal");
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            try {
                jwtToken = requestTokenHeader.substring(7);
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
                System.out.println("jwtToken do Filter " + username);
            } catch (IllegalArgumentException e) {
                System.out.println("IllegalArgumentException");
                httpServletRequest.setAttribute("code",0);
                httpServletRequest.setAttribute("message","fail.http.badrequest");
                httpServletRequest.setAttribute("status",HttpStatus.BAD_REQUEST);
                httpServletRequest.setAttribute("detail",e.getLocalizedMessage());
            } catch (ExpiredJwtException e) {
                System.out.println("ExpiredJwtException");
                httpServletRequest.setAttribute("code",0);
                httpServletRequest.setAttribute("message","fail.token.expired");
                httpServletRequest.setAttribute("status",HttpStatus.UNAUTHORIZED);
                httpServletRequest.setAttribute("detail",e.getLocalizedMessage());
            } catch (JWTVerificationException e){
                System.out.println("JWTVerificationException");
                httpServletRequest.setAttribute("code",0);
                httpServletRequest.setAttribute("message","fail.token.valid");
                httpServletRequest.setAttribute("status",HttpStatus.UNAUTHORIZED);
                httpServletRequest.setAttribute("detail",e.getLocalizedMessage());
            }
             catch (SignatureException e){
                System.out.println("SignatureException");
                httpServletRequest.setAttribute("code",0);
                httpServletRequest.setAttribute("message","fail.token.signature");
                httpServletRequest.setAttribute("status",HttpStatus.UNAUTHORIZED);
                httpServletRequest.setAttribute("detail",e.getLocalizedMessage());
            } catch (Exception e){
                System.out.println("Exception");
                httpServletRequest.setAttribute("code",0);
                httpServletRequest.setAttribute("message","fail.exception");
                httpServletRequest.setAttribute("status",HttpStatus.EXPECTATION_FAILED);
                httpServletRequest.setAttribute("detail",e.getLocalizedMessage());
            }
        } else {
            System.out.println("Token is Empty");
            httpServletRequest.setAttribute("code",0);
            httpServletRequest.setAttribute("message","fail.token.empty");
            httpServletRequest.setAttribute("status",HttpStatus.UNAUTHORIZED);
            httpServletRequest.setAttribute("detail","");
        }
        // Once we get the token validate it.
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userService.loadUserByUsername(username);
            // if token is valid configure Spring Security to manually set
            // authentication
            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                System.out.println(userDetails.getUsername());
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                System.out.println(usernamePasswordAuthenticationToken);
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                // After setting the Authentication in the context, we specify
                // that the current user is authenticated. So it passes the
                // Spring Security Configurations successfully.
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        System.out.println("filterChain");
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
