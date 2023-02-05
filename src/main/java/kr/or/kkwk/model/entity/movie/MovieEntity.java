package kr.or.kkwk.model.entity.movie;

import kr.or.kkwk.model.dto.movie.MovieDto;
import kr.or.kkwk.model.dto.movie.MovieSectionDto;
import lombok.*;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name="movie")
public class MovieEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String img;
  private String name;
  private String type;
  private String score;
  private String star;
  private String section;

  public MovieEntity(MovieDto movieDto){
    BeanUtils.copyProperties(movieDto, this);
  }




  public MovieDto toDomain(){
    MovieDto movieDto = new MovieDto();
    movieDto.setId(this.id);
    movieDto.setImg(this.img);
    movieDto.setName(this.name);
    movieDto.setScore(this.score);
    movieDto.setSection(this.section);
    return movieDto;
  }

}
