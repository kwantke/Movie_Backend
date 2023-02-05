package kr.or.kkwk.model.dto.movie;

import kr.or.kkwk.model.entity.movie.MovieEntity;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class MovieDto {

    private int id;
    private String img;
    private String name;
    private String type;
    private String score;
    private String star;
    private String section;

    public MovieEntity toEntity(){
        return MovieEntity.builder()
                .id(id)
                .img(img)
                .name(name)
                .score(score)
                .section(section)
                .build();
    }
}
