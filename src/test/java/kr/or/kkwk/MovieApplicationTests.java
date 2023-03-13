package kr.or.kkwk;

import kr.or.kkwk.service.movie.MovieService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MovieApplicationTests {

	@Autowired
	MovieService movieService;
	@Test
	void save() {
		movieService.findByMovieDtoById(2);

	}
}
