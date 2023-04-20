package kr.or.kkwk.common.exception;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public enum ExceptionEnum {
  RUNTIME_EXCEPTION(HttpStatus.BAD_REQUEST, "BAD_REQUEST"), // 400 에러
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR"), // 500 에러
  NOT_HANDLER_FOUND(HttpStatus.NOT_FOUND,"NOT_FOUND"), //404 에러

  SQL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"SQL 에러입니다."),
  ERROR_0001(HttpStatus.NOT_FOUND, "ERROR0001","데이터가 없습니다."),

  ERROR_0002(HttpStatus.NOT_FOUND, "ERROR0002","해당 아이디가 존재합나다."),

  ERROR_0003(HttpStatus.NOT_FOUND, "ERROR0003","해당 아이디가 존재하지 않습니다."),

  ERROR_0004(HttpStatus.BAD_REQUEST, "ERROR0004","비밀번호가 일치하지 않습니다.");

  private final HttpStatus status;
  private final String code;
  private String message;

  ExceptionEnum(HttpStatus status, String code) {
    this.status = status;
    this.code = code;
  }

  ExceptionEnum(HttpStatus status, String code, String message) {
    this.status = status;
    this.code = code;
    this.message = message;
  }
  }