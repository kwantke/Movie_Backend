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

  int idx;
  Long movieId;

  String actorName;
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
