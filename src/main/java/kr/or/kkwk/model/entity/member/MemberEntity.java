package kr.or.kkwk.model.entity.member;

import kr.or.kkwk.model.dto.member.MemberDto;

import lombok.*;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;


@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name="member")
public class MemberEntity {
// Entity는 jpa가 관리하는 객체
  @Id
  @Column(name="id", nullable=false)
  @NotBlank
  private String id;

  @Column(name="name")
  @NotBlank
  private String name;

  @Column(name="role")
  private String role;

  @Column(name="password")
  private String password;

  @Column(name="email")
  private String email;

  @Column(name="img")
  private String img;





  public MemberEntity(MemberDto memberVo){
    BeanUtils.copyProperties(memberVo, this);
  }



  public MemberDto toDomain(){
    MemberDto memberVo = new MemberDto();
    memberVo.setId(this.id);
    memberVo.setName(this.name);
    memberVo.setEmail(this.email);
    memberVo.setImg(this.img);
    return memberVo;
  }

}
