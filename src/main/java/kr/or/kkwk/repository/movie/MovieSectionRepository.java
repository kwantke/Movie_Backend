package kr.or.kkwk.repository.movie;

import kr.or.kkwk.model.dto.movie.MovieSectionDto;
import kr.or.kkwk.model.entity.movie.MovieEntity;
import kr.or.kkwk.model.entity.movie.MovieSectionEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieSectionRepository extends JpaRepository<MovieSectionEntity, Long> {

  List<MovieSectionEntity> findByUseYn(@Param("useYn")char useYn);


}