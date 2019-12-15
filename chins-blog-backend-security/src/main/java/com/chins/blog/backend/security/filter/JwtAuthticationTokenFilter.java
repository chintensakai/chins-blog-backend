package com.chins.blog.backend.security.filter;

import com.chins.blog.backend.security.utils.JwtUtils;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthticationTokenFilter extends OncePerRequestFilter {

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private JwtUtils jwtUtils;

  /***
   * 判断是否携带了有效token
   * @param httpServletRequest
   * @param httpServletResponse
   * @param filterChain
   * @throws ServletException
   * @throws IOException
   */
  @Override
  protected void doFilterInternal(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse, FilterChain filterChain)
      throws ServletException, IOException {

    String authHeader = httpServletRequest.getHeader(jwtUtils.getHeader());
    if (authHeader != null && StringUtils.isNotEmpty(authHeader)) {
      String usernameFromToken = jwtUtils.getUsernameFromToken(authHeader);
      if (usernameFromToken != null
          && SecurityContextHolder.getContext().getAuthentication() == null) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(usernameFromToken);

        if (jwtUtils.validateToken(authHeader, userDetails)) {

          //加载用户、角色、权限信息，Spring Security根据这些信息判断接口的访问权限
          UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
              userDetails, null, userDetails.getAuthorities());
          authenticationToken
              .setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
          SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
      }
    }

    filterChain.doFilter(httpServletRequest, httpServletResponse);
  }
}
