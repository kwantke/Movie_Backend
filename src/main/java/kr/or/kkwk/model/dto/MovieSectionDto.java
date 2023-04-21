package kr.or.kkwk.model.dto;


import lombok.Data;

import java.util.Date;

@Data
public class MovieSectionDto {

  int id;
  int section;
  String sectionName;
  char useYn;
  Date regDtm;


}