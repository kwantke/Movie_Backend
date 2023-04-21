package kr.or.kkwk.controller;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class RestResponseEntity<VO> {

  private boolean success;
  private VO data;

  @Builder
  public RestResponseEntity(boolean success, VO data){
    this.success = success;
    this.data = data;
  }
}
