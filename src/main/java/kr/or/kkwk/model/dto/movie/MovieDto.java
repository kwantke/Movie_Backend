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
    private int section;

    private String bgImg;

    private String video;
    private String detail;
    public MovieDto(int id, String img, String name, String detail/*, String type, String score, String star*/){
        this.id = id;
        this.img = img;
        this.name= name;
        this.detail = detail;
       /* this.type = type;
        this.score = score;
        this.star = star;*/
    }
    public MovieEntity toEntity(){
        return MovieEntity.builder()
                .id(id)
                .img(img)
                .name(name)
                .type(type)
                .score(score)
                .star(star)
                .section(section)
                .bgImg(bgImg)
                .video(video)
                .detail(detail)
                .build();
    }
}
