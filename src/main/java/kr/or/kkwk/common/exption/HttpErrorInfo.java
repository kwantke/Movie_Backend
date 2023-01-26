package kr.or.kkwk.common.exption;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
public class HttpErrorInfo {
    private final ZonedDateTime timestamp;
    private final HttpStatus httpStatus;
    private final String path;
    private final String message;

    @Builder
    public HttpErrorInfo(ZonedDateTime timestamp, HttpStatus httpStatus, String path, String message) {
        this.timestamp = timestamp;
        this.httpStatus = httpStatus;
        this.path = path;
        this.message = message;
    }
}
