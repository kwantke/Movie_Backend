package kr.or.kkwk.common.exceptio2;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.postgresql.util.PSQLException;
import javax.servlet.http.HttpServletRequest;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler({ApiException.class})
  public ResponseEntity<ApiExceptionEntity> exceptionHandler(HttpServletRequest request, final ApiException e) {
      //e.printStackTrace();
    return createCustomErrorInfo(e.getError().getCode(), request.getPathInfo(), e.getMessage());

  }

  @ExceptionHandler({RuntimeException.class})
  public ResponseEntity<ApiExceptionEntity> exceptionHandler(HttpServletRequest request, final RuntimeException e) {
    //e.printStackTrace();
    return createCustomErrorInfo(ExceptionEnum.RUNTIME_EXCEPTION.getCode(), request.getPathInfo(), e.getMessage());

  }
  @ExceptionHandler({PSQLException.class})
  public ResponseEntity<ApiExceptionEntity> exceptionHandler(HttpServletRequest request, final PSQLException e) {
    //e.printStackTrace();
    return createCustomErrorInfo(ExceptionEnum.SQL_ERROR.getCode(), request.getPathInfo(), e.getMessage());

  }

  @ExceptionHandler({NoHandlerFoundException.class})
  public ResponseEntity<ApiExceptionEntity> NoHandlerFound(HttpServletRequest request, final NoHandlerFoundException e) {
    //e.printStackTrace();
    return createCustomErrorInfo(ExceptionEnum.NOT_HANDLER_FOUND.getCode(), request.getPathInfo(), e.getMessage());

  }


  @ExceptionHandler({Exception.class})
  public ResponseEntity<ApiExceptionEntity> exceptionHandler(HttpServletRequest request, final Exception e) {
    //e.printStackTrace();
    return createCustomErrorInfo(ExceptionEnum.INTERNAL_SERVER_ERROR.getCode(), request.getPathInfo(), e.getMessage());

  }

  private ResponseEntity<ApiExceptionEntity> createCustomErrorInfo(String code, String path, String message){
    ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));

    log.error("Error code: {}, path: {}, message: {}",code, path, message);
    return ResponseEntity
            .status(200)
            .body(ApiExceptionEntity.builder()
                    .errorCode(code)
                    .errorMessage(message)
                    .path(path)
                    .timestamp(now)
                    .build());
  }


}
