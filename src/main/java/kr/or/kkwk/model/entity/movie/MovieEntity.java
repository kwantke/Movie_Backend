package kr.or.kkwk.model.entity.movie;

import kr.or.kkwk.model.dto.movie.MovieDto;
import kr.or.kkwk.model.dto.movie.MovieSectionDto;
import lombok.*;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;


@Entity
@SqlResultSetMapping(
        name = "MyDtoMapping",
        classes = @ConstructorResult(
                targetClass = MovieDto.class,
                columns = {
                        @ColumnResult(name = "id", type = Integer.class)
                        ,@ColumnResult(name = "img", type = String.class)
                        ,@ColumnResult(name = "name", type = String.class)
                        ,@ColumnResult(name = "detail", type = String.class)
                }
        )
)
@NamedNativeQuery(
        name = "findByMovieDtoById"
        ,query = "select m.id, m.img, m.name ,md.detail  from movie m" +
        " inner join movie_detail md on m.id = md.movie_id" +
        " where m.id = :id"
        ,resultSetMapping="MyDtoMapping"
)
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name="movie")
public class MovieEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @NotNull
  private String img;
  private String name;

  private String type;
  private String score;

  private String star;
  private int section;

  private String bgImg;

  private String video;
  private String detail;

  public MovieEntity(MovieDto movieDto){
    BeanUtils.copyProperties(movieDto, this);
  }




  public MovieDto toDomain(){
    MovieDto movieDto = new MovieDto();
    movieDto.setId(this.id);
    movieDto.setImg(this.img);
    movieDto.setName(this.name);
    movieDto.setType(this.type);
    movieDto.setScore(this.score);
    movieDto.setStar(this.star);
    movieDto.setSection(this.section);
    movieDto.setBgImg(this.bgImg);
    movieDto.setVideo(this.video);
    movieDto.setDetail(this.detail);
    return movieDto;
  }

}
