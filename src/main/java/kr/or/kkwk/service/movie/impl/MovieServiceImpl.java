package kr.or.kkwk.service.movie.impl;

import kr.or.kkwk.common.exceptio2.ApiException;
import kr.or.kkwk.common.exceptio2.ExceptionEnum;
import kr.or.kkwk.model.dto.movie.ActorDto;
import kr.or.kkwk.model.dto.movie.MovieDto;
import kr.or.kkwk.model.dto.movie.MovieSectionDto;
import kr.or.kkwk.model.entity.member.MemberEntity;
import kr.or.kkwk.model.entity.movie.MovieEntity;
import kr.or.kkwk.model.entity.movie.MovieSectionEntity;
import kr.or.kkwk.model.entity.movie.MovieView;
import kr.or.kkwk.repository.movie.MovieRepository;
import kr.or.kkwk.repository.movie.MovieSectionRepository;
import kr.or.kkwk.service.movie.ActorService;
import kr.or.kkwk.service.movie.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

  @PersistenceContext
  private EntityManager entityManager;
  MovieSectionRepository movieSectionRepository;

  MovieRepository movieRepository;

  @Autowired
  ActorService actorService;


  @Autowired
  MovieServiceImpl(MovieSectionRepository movieSectionRepository, MovieRepository movieRepository){
    this.movieSectionRepository=movieSectionRepository;
    this.movieRepository=movieRepository;
  }

  @Override
  public List<MovieSectionDto> getMovieSectionList(MovieSectionDto movieSectionDto) {
    MovieSectionEntity movieSectionEntity = movieSectionDto.saveMovieSectionEntity();
    List<MovieSectionEntity> movieSectionEntitiesList = movieSectionRepository.findByUseYn(movieSectionEntity.getUseYn());
    List<MovieSectionDto> movieSectionDtoList = movieSectionEntitiesList
            .stream().map(MovieSectionEntity::toDomain).collect(Collectors.toList());


    for(MovieSectionDto sectiondto : movieSectionDtoList){
      Optional<List<MovieEntity>> movieEntityList = movieRepository.findBySection(sectiondto.getSection());
      List<MovieDto> movieDtoList = movieEntityList.get().stream().map(MovieEntity::toDomain).collect(Collectors.toList());
      sectiondto.setMovieDtoList(movieDtoList);
    }

    return movieSectionDtoList;
  }

  @Override
  public List<MovieDto> getMovieList(int section) {
    Optional<List<MovieEntity>> movieEntityList = Optional.ofNullable(movieRepository.findBySection(section)
            .orElseThrow(() -> new ApiException(ExceptionEnum.ZERO_01)));

    return movieEntityList.get().stream().map(MovieEntity::toDomain).collect(Collectors.toList());
  }

  @Override
  public MovieDto getMovieInfo(Long id) {
    Optional<MovieEntity> movieEntity = Optional.ofNullable(movieRepository.findById(id)
            .orElseThrow(() -> new ApiException(ExceptionEnum.ZERO_01)));
    MovieDto movieDto = movieEntity.get().toDomain();
    List<ActorDto> actorDtoList = actorService.getActorDtoList(id);
    movieDto.setActorDtoList(actorDtoList);
    return movieDto;
  }


  @Override
  public MovieDto findByMovieDtoById(int movie_id) {

    String sql = "select m.id, m.img, m.name ,md.detail  from movie m\n" +
            "inner join movie_detail md on m.id = md.movie_id\n" +
            "where m.id = :id";
    String sql2= "select m.id,m.img, m.name from movie m where m.id=:id ";
    //MovieDto movieEntity = movieRepository.findMovieDtoById(movie_id);
    /*MovieDto movieDto = (MovieDto) entityManager
            .createNativeQuery(sql,"MyDtoMapping")
            .setParameter("id", 2)
            .getSingleResult();*/
    Object movieDto =  entityManager.createNamedQuery("findByMovieDtoById")
            .setParameter("id",2)
            .getSingleResult();
    return null;
  }
}
