package kr.or.kkwk.common.exption;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.springframework.http.HttpStatus.NOT_FOUND;


// 프로젝트 전역에 적용된다.
@Slf4j
@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler({NotFoundException.class})
    public @ResponseBody HttpErrorInfo handleNotFoundExceptions(HttpServletRequest request, NotFoundException ex) {

        return createHttpErrorInfo(NOT_FOUND, request, ex);
    }

    private HttpErrorInfo createHttpErrorInfo(HttpStatus httpStatus, HttpServletRequest request, Exception ex){
        String path = request.getRequestURI();
        final String message = ex.getMessage();
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));

        log.error("Return HTTP status; {} for path: {}, message: {}",httpStatus, path, message);
        return new HttpErrorInfo(now, httpStatus, path, message);
    }



    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataIllegalArgumentException.class)
    public @ResponseBody HttpErrorInfo handleNotFoundExceptions2(HttpServletRequest request, DataIllegalArgumentException ex) {

        return createHttpErrorInfo(HttpStatus.BAD_REQUEST, request, ex);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({RuntimeException.class})
    public @ResponseBody HttpErrorInfo exceptionHandler(HttpServletRequest request, final RuntimeException e) {
        e.printStackTrace();
        return createHttpErrorInfo(HttpStatus.BAD_REQUEST, request, e);
    }
}

