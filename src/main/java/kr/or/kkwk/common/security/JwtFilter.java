package kr.or.kkwk.common.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import kr.or.kkwk.common.exception.ExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
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
    try {
    if(token.isPresent()){
      JwtAuthToken jwtAuthToken = tokenProvider.convertAuthToken(token.get());

      if(jwtAuthToken.validate()) {
        Authentication authentication = tokenProvider.getAuthentication(jwtAuthToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    }

    } catch (SecurityException | MalformedJwtException e) {// 손상된 토큰
      request.setAttribute("exception", ExceptionEnum.ERROR_TOKEN_0001.getCode());
    } catch (ExpiredJwtException e) {
      request.setAttribute("exception", ExceptionEnum.ERROR_TOKEN_0002.getCode());
    } catch (UnsupportedJwtException e) {
      request.setAttribute("exception", ExceptionEnum.ERROR_TOKEN_0003.getCode());
    } catch (IllegalArgumentException e) {
      request.setAttribute("exception", ExceptionEnum.ERROR_TOKEN_0004.getCode());
    } catch (Exception e) {
      log.error("================================================");
      log.error("JwtFilter - doFilterInternal() 오류발생");
      log.error("token : {}", token);
      log.error("Exception Message : {}", e.getMessage());
      log.error("Exception StackTrace : {");
      e.printStackTrace();
      log.error("}");
      log.error("================================================");
      request.setAttribute("exception", ExceptionEnum.RUNTIME_EXCEPTION.getCode());
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
