package kr.or.kkwk.model.dto;

import kr.or.kkwk.model.entity.MemberEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
public class MemberDto {

  String id;
  @NotNull
  String name;
  String password;
  String email;


  public MemberDto(String id, String name, String password, String email){
    this.id = id;
    this.name = name;
    this.password = password;
    this.email = email;
  }


  public MemberEntity saveMember(){
    return MemberEntity.builder()
            .id(id)
            .name(name)
            .password(password)
            .email(email)
            .build();
  }

}
