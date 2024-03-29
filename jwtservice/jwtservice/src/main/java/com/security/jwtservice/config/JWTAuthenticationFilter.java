package com.security.jwtservice.config;

import com.security.jwtservice.JwtService;
import com.security.jwtservice.UserService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNullApi;
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
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userservice;

    @Override
    protected void doFilterInternal( HttpServletRequest request,  HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, IOException {

        final String auth = request.getHeader("Authorization");
        String jwt;
        System.out.println("The value of auth is " + auth);
        if (auth == null || !auth.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = auth.substring(7);
        String username = jwtService.extractUsername(jwt);
        System.out.println("The value of username is " + username);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userservice.loadUserByUsername(username);
            if (jwtService.isTokenValid(jwt, userDetails)) {
                System.out.println("This logger will be printed when the token is valid for username " + username);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, userDetails.getAuthorities()
                );
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        // hello world
        filterChain.doFilter(request, response);
    }
}
