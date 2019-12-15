package com.chins.blog.backend.security.config;

import com.chins.blog.backend.security.filter.JwtAuthticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private UserDetailsService userDetailsService;

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {

    return new BCryptPasswordEncoder();
  }

  @Bean
  public JwtAuthticationTokenFilter jwtAuthticationTokenFilter() {
    return new JwtAuthticationTokenFilter();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder());
  }

  @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http
        .authorizeRequests();
    // 让Spring security放行所有preflight request
    registry.requestMatchers(CorsUtils::isPreFlightRequest).permitAll();

    // 暂时禁用csrc否则无法提交
    http.csrf().disable();

    http.authorizeRequests().antMatchers("/article/**", "/login").permitAll()
        .anyRequest().authenticated();

    http.addFilterBefore(jwtAuthticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);

  }

  /**
   * 配置跨域请求的
   *
   * @return Cors过滤器
   */
  @Bean
  public CorsFilter corsFilter() {
    final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
    final CorsConfiguration cors = new CorsConfiguration();
    cors.setAllowCredentials(true);
    cors.addAllowedOrigin("*");
    cors.addAllowedHeader("*");
    cors.addAllowedMethod("*");
    urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", cors);
    return new CorsFilter(urlBasedCorsConfigurationSource);
  }
}


