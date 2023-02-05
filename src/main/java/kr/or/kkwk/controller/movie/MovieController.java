package kr.or.kkwk.controller.movie;


import kr.or.kkwk.model.dto.movie.MovieDto;
import kr.or.kkwk.model.dto.movie.MovieSectionDto;
import kr.or.kkwk.service.movie.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MovieController {

  MovieService movieService;

  @Autowired
  public MovieController(MovieService movieService){
    this.movieService=movieService;
  }

  @RequestMapping("/getMovieSection")
  public List<MovieSectionDto> getMovieSection(MovieSectionDto movieSectionDto){
    return movieService.getMovieSectionList(movieSectionDto);
  }

  @RequestMapping("/getMovieList")
  public List<MovieDto> getMovieDtoList(int section){
    return movieService.getMovieList(section);
  }
}
