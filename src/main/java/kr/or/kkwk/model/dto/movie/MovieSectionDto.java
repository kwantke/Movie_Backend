package kr.or.kkwk.model.dto.movie;

import kr.or.kkwk.model.entity.movie.MovieSectionEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.Date;

@Data
@NoArgsConstructor
public class MovieSectionDto {

  int id;
  int section;
  String sectionName;

  char useYn;
  Date regDtm;

  public MovieSectionDto(int section, String sectionName, char useYn, Date regDtm){
    this.section=section;
    this.sectionName=sectionName;
    this.useYn=useYn;
    this.regDtm=regDtm;
  }

  public MovieSectionEntity saveMovieSectionEntity(){
    return MovieSectionEntity.builder()
            .id(id)
            .sectionName(sectionName)
            .useYn(useYn)
            .regDtm(regDtm)
            .build();
  }

}
