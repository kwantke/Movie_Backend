package kr.or.kkwk.common.exceptio2;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public enum ExceptionEnum {
  RUNTIME_EXCEPTION(HttpStatus.BAD_REQUEST, "zero0001"), // 400 에러

  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "zero0003"), // 500 에러
  NOT_HANDLER_FOUND(HttpStatus.NOT_FOUND,"zero0004"), //404 에러

  SQL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"SQL 에려입니다."),
  //커스텀 에러
  ZERO_01(HttpStatus.NOT_FOUND, "zero0002","데이터가 없습니다."),
  ZERO_03(HttpStatus.NOT_FOUND, "zero0005","해당 아이디가 존재합나다.");

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