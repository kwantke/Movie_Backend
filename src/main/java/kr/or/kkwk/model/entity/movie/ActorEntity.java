package kr.or.kkwk.model.entity.movie;

import kr.or.kkwk.model.dto.movie.ActorDto;
import lombok.*;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
@Table(name="actor")
public class ActorEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  int idx;
  Long movieId;
  String actorName;
  String actorImg;

  public ActorDto toDomain(){

    ActorDto actorDto = new ActorDto();
    actorDto.setIdx(this.idx);
    actorDto.setMovieId(this.movieId);
    actorDto.setActorName(this.actorName);
    actorDto.setActorImg(this.actorImg);

    return actorDto;
  }
}
