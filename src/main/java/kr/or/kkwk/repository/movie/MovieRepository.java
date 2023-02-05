package kr.or.kkwk.repository.movie;

import kr.or.kkwk.model.entity.movie.MovieEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<MovieEntity, Long> {

  Optional<List<MovieEntity>> findBySection(@Param("section")int section);
}
