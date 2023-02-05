package kr.or.kkwk.service.movie;

import kr.or.kkwk.model.dto.movie.MovieDto;
import kr.or.kkwk.model.dto.movie.MovieSectionDto;

import java.util.List;

public interface MovieService {


  List<MovieSectionDto> getMovieSectionList(MovieSectionDto movieSectionDto);

  List<MovieDto> getMovieList(int section);
}
