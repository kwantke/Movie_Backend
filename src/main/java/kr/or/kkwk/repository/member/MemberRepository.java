package kr.or.kkwk.repository.member;

import kr.or.kkwk.model.entity.member.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;



@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, String> {




  Optional<MemberEntity> findById(@Param("id")String id);

  boolean existsById(@Param("id")String id);

  Optional<MemberEntity> findByIdAndPassword(String id, String password);
}
