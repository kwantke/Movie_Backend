package kr.or.kkwk.model.dto.member;

import kr.or.kkwk.model.entity.member.MemberEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
public class MemberDto {

  String id;
  String name;
  String password;
  String email;
  String img;
  String role;

  String token;


/*
  public MemberDto(String id, String name, String password, String email, String role, String img, String token){
    this.id = id;
    this.name = name;
    this.password = password;
    this.email = email;
    this.img = img;
  }
*/


  public MemberEntity saveMember(){
    return MemberEntity.builder()
            .id(id)
            .name(name)
            .password(password)
            .email(email)
            .role(role)
            .img(img)
            .build();
  }



}
