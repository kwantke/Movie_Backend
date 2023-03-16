package kr.or.kkwk.service.movie;

import kr.or.kkwk.model.dto.movie.ActorDto;

import java.util.List;

public interface ActorService {

  public List<ActorDto> getActorDtoList(Long movieId);

}
