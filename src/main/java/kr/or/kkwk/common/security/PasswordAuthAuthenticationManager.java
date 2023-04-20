package kr.or.kkwk.common.security;

import kr.or.kkwk.common.exception.ApiException;
import kr.or.kkwk.common.exception.ExceptionEnum;
import kr.or.kkwk.model.entity.member.MemberEntity;
import kr.or.kkwk.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PasswordAuthAuthenticationManager implements AuthenticationProvider {

  private final MemberRepository memberRepository;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    Optional<MemberEntity> memberOptional = memberRepository.findById(authentication.getPrincipal().toString());
    if(memberOptional.isEmpty()){
      throw new ApiException(ExceptionEnum.ERROR_0003);
    }
    MemberEntity memberEntity = memberOptional.get();
    if(!authentication.getCredentials().equals(memberEntity.getPassword())){
      throw new ApiException(ExceptionEnum.ERROR_0004);
    }
    PasswordAuthenticationToken token = new PasswordAuthenticationToken(memberEntity.getId(),memberEntity.getPassword()
    , Collections.singleton(new SimpleGrantedAuthority(memberEntity.getRole())));
    token.setId(memberEntity.getId());
    /*token.setRole(memberEntity.getRole());*/
    token.setName(memberEntity.getName());
    token.setEmail(memberEntity.getEmail());
    return token;
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(PasswordAuthenticationToken.class);
  }
}
