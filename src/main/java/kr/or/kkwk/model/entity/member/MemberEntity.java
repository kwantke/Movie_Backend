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
@Table(name="MEMBER")
public class MemberEntity {
// Entity는 jpa가 관리하는 객체
  @Id
  @Column(name="ID", nullable=false)
  @NotBlank
  private String id;

  @Column(name="NAME")
  @NotBlank
  private String name;

  @Column(name="PASSWORD")
  private String password;

  @Column(name="EMAIL")
  private String email;

  @Column(name="ROLE")
  private String role;

  @Column(name="IMG")
  private String img;

  public MemberEntity(MemberDto memberDto){
    BeanUtils.copyProperties(memberDto, this);
  }

  public MemberDto toDomain(){
    MemberDto memberDto = new MemberDto();
    memberDto.setId(this.id);
    memberDto.setName(this.name);
    memberDto.setEmail(this.email);
    memberDto.setRole(this.role);
    memberDto.setImg(this.img);
    return memberDto;
  }

}
