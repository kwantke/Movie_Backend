package kr.or.kkwk.common.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
  private final JwtAuthTokenProvider tokenProvider;

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
    return configuration.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

    return httpSecurity
            .httpBasic().disable()
            /*.cors().configurationSource(request -> {
              var cors = new CorsConfiguration();
              cors.setAllowedOrigins(List.of("http://localhost:3000","http://localhost:8080"));
              cors.setAllowedMethods(List.of("GET","POST", "PUT", "DELETE", "OPTIONS"));
              cors.setAllowedHeaders(List.of("*"));
              cors.setAllowCredentials(true);
              return cors;
            })*/
            .cors().configurationSource(corsConfigurationSource())
            .and()
            .csrf().disable()
            .cors().and()
            .headers().frameOptions().disable().and()
            .authorizeRequests()
            .antMatchers("/test2","/test3","/login","/save","/getMovieSection","/getMovieList","/test").permitAll()
            .antMatchers("/api/**").authenticated()
            .anyRequest().authenticated().and()
            .addFilterBefore(new JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
            .build();
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(List.of("http://localhost:3000","http://localhost:8080"));
    configuration.setAllowedMethods(List.of("GET","POST", "PUT", "DELETE", "OPTIONS"));
    configuration.setAllowedHeaders(List.of("*"));
    configuration.setAllowCredentials(true);
    // you can configure many allowed CORS headers

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  /*CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList("https://example.com"));
    configuration.setAllowedMethods(Arrays.asList("GET","POST"));
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }*/
}
