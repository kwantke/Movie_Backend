package kr.or.kkwk.repository.movie;

import kr.or.kkwk.model.entity.movie.ActorEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ActorRepository extends JpaRepository<ActorEntity, Long> {
  Optional<List<ActorEntity>> findByMovieId(@Param("movie_id")Long movieId);
}
