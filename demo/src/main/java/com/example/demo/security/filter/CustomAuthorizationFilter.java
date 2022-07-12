package com.example.demo.security.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.security.SecurityConfig;
import com.example.demo.security.dto.JwtBodyDto;
import com.example.demo.security.utility.SecurityUtility;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {

  @Autowired
  private SecurityUtility securityUtility;

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    log.info("go should filter");
    log.info("whitelist: {}", Arrays.toString(SecurityConfig.WHITE_LIST_PATH));
    log.info("path: {}", request.getServletPath());

    AntPathMatcher antPathMatcher = new AntPathMatcher();
    for (String path : SecurityConfig.WHITE_LIST_PATH) {
      if (antPathMatcher.match(path, request.getServletPath()))
        return true;
    }

    return false;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    log.info("go filter authen");
    log.info("security: {}", securityUtility);

    Long id = null;

    try {
      id = securityUtility.verifyToken(request);
    } catch (Exception e) {
      response.sendError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
      return;
    }

    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority("role"));

    SecurityContextHolder.getContext().setAuthentication(
        new UsernamePasswordAuthenticationToken(id, null, null));
    filterChain.doFilter(request, response);
  }

}
