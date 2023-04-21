package kr.or.kkwk.controller.movie;



import kr.or.kkwk.model.dto.movie.MovieSectionDto;
import kr.or.kkwk.service.movie.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MovieController {

  MovieService movieService;

  @Autowired
  public MovieController(MovieService movieService){
    this.movieService=movieService;
  }

  @GetMapping("/getMovieSection" )
  public ResponseEntity getMovieSection(MovieSectionDto movieSectionDto){

    return ResponseEntity
            .status(HttpStatus.OK)
            .body(movieService.getMovieSectionList(movieSectionDto));
  }

  @RequestMapping("/getMovieList")
  public ResponseEntity getMovieDtoList(int section){

    return ResponseEntity
            .status(HttpStatus.OK)
            .body(movieService.getMovieList(section));
  }

  @GetMapping("/getMovieDetail")
  public ResponseEntity getMovieDetail(Long id){

    return ResponseEntity
            .status(HttpStatus.OK)
            .body(movieService.getMovieInfo(id));
  }

}
