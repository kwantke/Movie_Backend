package kr.or.kkwk.common.security;

import kr.or.kkwk.model.entity.MemberEntity;
import kr.or.kkwk.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class PasswordAuthAuthenticationManager implements AuthenticationProvider {

  private final MemberRepository memberRepository;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    MemberEntity memberEntity = memberRepository.findById(authentication.getPrincipal().toString()).get();
    if(!authentication.getCredentials().equals(memberEntity.getPassword())){
      throw new BadCredentialsException("비밀번호 오류입니다.");
    }
    PasswordAuthenticationToken token = new PasswordAuthenticationToken(memberEntity.getId(),memberEntity.getPassword()
    , Collections.singleton(new SimpleGrantedAuthority(memberEntity.getRole())));
    token.setId(1234L);
    token.setRole(memberEntity.getRole());
    token.setName(memberEntity.getName());
    return token;
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(PasswordAuthenticationToken.class);
  }
}
