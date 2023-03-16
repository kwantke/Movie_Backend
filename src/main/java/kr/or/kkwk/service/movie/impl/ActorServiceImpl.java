package kr.or.kkwk.service.movie.impl;

import kr.or.kkwk.model.dto.movie.ActorDto;
import kr.or.kkwk.model.entity.movie.ActorEntity;
import kr.or.kkwk.repository.movie.ActorRepository;
import kr.or.kkwk.service.movie.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ActorServiceImpl implements ActorService {


  @Autowired
  ActorRepository actorRepository;

  @Override
  public List<ActorDto> getActorDtoList(Long movieId) {
    List<ActorEntity> actorEntityList = actorRepository.findByMovieId(movieId).get();
    List<ActorDto> actorDtoList = actorEntityList.stream().map(ActorEntity::toDomain).collect(Collectors.toList());

    return actorDtoList;
  }
}
