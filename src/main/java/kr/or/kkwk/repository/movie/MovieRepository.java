package kr.or.kkwk.repository.movie;

import kr.or.kkwk.model.dto.movie.MovieDto;
import kr.or.kkwk.model.entity.movie.MovieEntity;
import kr.or.kkwk.model.entity.movie.MovieView;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.SqlResultSetMapping;
import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<MovieEntity, Long> {

  Optional<List<MovieEntity>> findBySection(@Param("section")int section);


  @Query(value="SELECT new kr.or.kkwk.model.dto.movie.MovieDto(m.id,m.img, m.name,m.type, m.score,m.star) " +
          " FROM movie m " +
          " WHERE m.id = :id", nativeQuery = true
         )
 /*@Query(value="select m.id,m.img, m.name,m.type, m.score,m.star " +
         " from movie m " +
         " where m.id = :id", nativeQuery = true)*/
  MovieDto findMovieDtoById(@Param("id")int id);
}
