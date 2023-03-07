package kr.or.kkwk.common.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Slf4j
public class JwtFilter extends OncePerRequestFilter {
  public static final String AUTHORIZATION_HEADER = "x-auth-token";
  private final JwtAuthTokenProvider tokenProvider;


  public JwtFilter(JwtAuthTokenProvider tokenProvider) {
    this.tokenProvider = tokenProvider;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

    Cookie[] cookies = request.getCookies();
    if(cookies != null){
      for(Cookie cookie : cookies){
        log.info("name : {}, value : {}", cookie.getName(), cookie.getValue());
      }
    }


    Optional<String> token = resolveToken(request);

    if(token.isPresent()){
      JwtAuthToken jwtAuthToken = tokenProvider.convertAuthToken(token.get());

      if(jwtAuthToken.validate()) {
        Authentication authentication = tokenProvider.getAuthentication(jwtAuthToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    }
    filterChain.doFilter(request, response);
  }

  private Optional<String> resolveToken(HttpServletRequest request) {
    String authToken = request.getHeader(AUTHORIZATION_HEADER);
    if(StringUtils.hasText(authToken)){
      return Optional.of(authToken);
    } else
      return Optional.empty();
  }
}
