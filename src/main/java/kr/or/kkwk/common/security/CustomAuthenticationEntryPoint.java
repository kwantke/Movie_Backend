package kr.or.kkwk.common.security;

import kr.or.kkwk.common.exception.ExceptionEnum;
import org.json.simple.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
    String exception = (String)request.getAttribute("exception");

    if(exception == null) {
      setResponse(response, ExceptionEnum.RUNTIME_EXCEPTION);
    }
    //잘못된 타입의 토큰인 경우
    else if(exception.equals(ExceptionEnum.ERROR_TOKEN_0001.getCode())) {
      setResponse(response, ExceptionEnum.ERROR_TOKEN_0001);
    }
    //토큰 만료된 경우
    else if(exception.equals(ExceptionEnum.ERROR_TOKEN_0002.getCode())) {
      setResponse(response, ExceptionEnum.ERROR_TOKEN_0002);
    }
    //지원되지 않는 토큰인 경우
    else if(exception.equals(ExceptionEnum.ERROR_TOKEN_0003.getCode())) {
      setResponse(response, ExceptionEnum.ERROR_TOKEN_0003);
    }
    else {
      setResponse(response, ExceptionEnum.ERROR_TOKEN_0004);
    }
  }

  private void setResponse(HttpServletResponse response, ExceptionEnum ExceptionEnum) throws IOException {
    response.setContentType("application/json;charset=UTF-8");
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

    JSONObject responseJson = new JSONObject();
    responseJson.put("message", ExceptionEnum.getMessage());
    responseJson.put("code", ExceptionEnum.getCode());

    response.getWriter().print(responseJson);
  }
}
