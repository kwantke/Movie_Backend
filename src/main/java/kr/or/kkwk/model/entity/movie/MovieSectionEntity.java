package kr.or.kkwk.model.entity.movie;

import kr.or.kkwk.model.dto.movie.MovieSectionDto;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name="movieSection")
public class MovieSectionEntity {

  @Id
  int id;

  int section;

  String sectionName;


  char useYn;

  @Temporal(TemporalType.TIMESTAMP)
  Date regDtm;

  public MovieSectionEntity(MovieSectionDto movieSectionDto){
    BeanUtils.copyProperties(movieSectionDto, this);
  }

  public MovieSectionDto toDomain(){
    MovieSectionDto movieSectionDto = new MovieSectionDto();
    movieSectionDto.setId(this.id);
    movieSectionDto.setSection(this.section);
    movieSectionDto.setSectionName(this.sectionName);
    movieSectionDto.setUseYn(this.useYn);
    movieSectionDto.setRegDtm(this.regDtm);

    return movieSectionDto;
  }


}
