package com.muchatlu.filter;

import com.muchatlu.exception.InvalidSessionException;
import com.muchatlu.model.AuthenticateToken;
import com.muchatlu.model.MyUserDetails;
import com.muchatlu.service.AuthenticationTokenService;
import com.muchatlu.service.MyUserDetailsService;
import com.muchatlu.service.PersonService;
import com.muchatlu.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private  MyUserDetailsService userDetailsService;

    @Autowired
    private AuthenticationTokenService authService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if(null != authorization && authorization.startsWith("Bearer ")){
            token = authorization.substring(7);
            username = jwtUtil.getUsernameFromToken(token);
            AuthenticateToken authToken = authService.getAuthToken(token);
            if(authToken != null && !authToken.getIsActive()){
                throw new InvalidSessionException("Session Expired");
            }

        }

        System.out.println("authentication object >>>>>>> "+SecurityContextHolder.getContext().getAuthentication());

        if(null != username && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            MyUserDetails details = (MyUserDetails) userDetails;
            if(jwtUtil.validateToken(token,details)){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(details,null,details .getAuthorities());
                authToken.setDetails( new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContext securityContext = SecurityContextHolder.getContext();
                securityContext.setAuthentication(authToken);
                HttpSession session = request.getSession(true);
                session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);

            }

        }

        filterChain.doFilter(request,response);

    }

//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        String authorization = ((HttpServletRequest)request).getHeader("Authorization");
//        String token = null;
//        String username = null;
//
//        if(null != authorization && authorization.startsWith("Bearer ")){
//            token = authorization.substring(7);
//            username = jwtUtil.getUsernameFromToken(token);
//        }
//
//        System.out.println("authentication object >>>>>>> "+SecurityContextHolder.getContext().getAuthentication());
//
//        if(null != username && SecurityContextHolder.getContext().getAuthentication() == null){
//            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//            MyUserDetails details = (MyUserDetails) userDetails;
//            if(jwtUtil.validateToken(token,details)){
//                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(details,null,details .getAuthorities());
//                authToken.setDetails( new WebAuthenticationDetailsSource().buildDetails(((HttpServletRequest)request)));
//                SecurityContext securityContext = SecurityContextHolder.getContext();
//                securityContext.setAuthentication(authToken);
//                HttpSession session = ((HttpServletRequest)request).getSession(true);
//                session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
//
//            }
//
//        }
//
//        chain.doFilter(request,response);
//    }
}
