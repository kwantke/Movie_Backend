package kr.or.kkwk.model.dto.movie;

import kr.or.kkwk.model.entity.movie.ActorEntity;
import kr.or.kkwk.model.entity.movie.MovieEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActorDto {

  /**배우 INDEX*/
  int idx;
  /**영화 ID*/
  Long movieId;
  /**배우 이름*/
  String actorName;
  /**배우 이미지*/
  String actorImg;

  public ActorEntity toEntity(){

    return ActorEntity.builder()
            .idx(idx)
            .movieId(movieId)
            .actorName(actorName)
            .actorImg(actorImg)
            .build();
  }

}
