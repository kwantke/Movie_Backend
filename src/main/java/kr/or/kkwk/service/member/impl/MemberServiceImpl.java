package kr.or.kkwk.service.member.impl;


import kr.or.kkwk.common.exceptio2.ApiException;
import kr.or.kkwk.common.exceptio2.ExceptionEnum;
import kr.or.kkwk.common.security.JwtAuthToken;
import kr.or.kkwk.common.security.JwtAuthTokenProvider;
import kr.or.kkwk.common.security.PasswordAuthenticationToken;
import kr.or.kkwk.mapper.MemberMapper;
import kr.or.kkwk.model.entity.member.MemberEntity;
import kr.or.kkwk.model.dto.member.MemberDto;
import kr.or.kkwk.repository.member.MemberRepository;
import kr.or.kkwk.service.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class MemberServiceImpl implements MemberService {


  MemberRepository memberRepository;

  MemberMapper memberMapper;
  private final AuthenticationManager authenticationManager;
  private final JwtAuthTokenProvider tokenProvider;
  @Autowired
  MemberServiceImpl(MemberRepository memberRepository, MemberMapper memberMapper, AuthenticationManager authenticationManager, JwtAuthTokenProvider tokenProvider){
    this.memberRepository = memberRepository;
    this.memberMapper = memberMapper;
    this.authenticationManager = authenticationManager;
    this.tokenProvider = tokenProvider;
  }

  @Override
  public MemberDto getMemberInfo(String id, String password) {
      Optional<MemberEntity> memberEntity = Optional.ofNullable(memberRepository.findById(id)
              .orElseThrow(() -> new ApiException(ExceptionEnum.ZERO_01)));
    return memberEntity.get().toDomain();
  }

  @Override
  public MemberDto getMemberId(String id) {
    Optional<MemberEntity> memberEntity = Optional.ofNullable(memberRepository.findById(id)
            .orElseThrow(() -> new ApiException(ExceptionEnum.ZERO_01)));
    return memberEntity.get().toDomain();
  }

  @Override
  @Transactional
  public boolean save(MemberDto memberVo) {
    //MemberVo memberVo = new MemberVo();
    //memberVo.setId("");
    /*memberVo.setPassword("member3");
    memberVo.setName("최홍익3");
    memberVo.setEmail("chl3@gmail.com");*/

    if(!memberRepository.existsById(memberVo.getId())){
      MemberEntity saveMember = memberVo.saveMember();

      memberRepository.save(saveMember);
    } else{
      throw new ApiException(ExceptionEnum.ZERO_03);
    }
      return true;
  }

  @Override
  public List<MemberDto> getMemberList() {
    return memberMapper.getMemberList();
  }

  @Override
  public String memberLogin(MemberDto memberVo) {
    PasswordAuthenticationToken token = new PasswordAuthenticationToken(memberVo.getId(),memberVo.getPassword());
    Authentication authentication = authenticationManager.authenticate(token);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String userToken = createToken((PasswordAuthenticationToken) authentication);

    return userToken;
  }

  public String createToken(PasswordAuthenticationToken token){
    Date expiredDate = Date.from(LocalDateTime.now().plusMinutes(180).atZone(ZoneId.systemDefault()).toInstant());
    Map<String, String> claims = new HashMap<>();
    claims.put("id", token.getId().toString());
    claims.put("name", token.getName().toString());
    claims.put("role", token.getRole().toString());

    JwtAuthToken jwtAuthToken = tokenProvider.createAuthToken(
            token.getPrincipal().toString(),
            token.getAuthorities().iterator().next().getAuthority(),
            claims,
            expiredDate
    );
    return jwtAuthToken.getToken(jwtAuthToken);
  }


}
