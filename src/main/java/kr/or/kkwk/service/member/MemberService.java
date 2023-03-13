package kr.or.kkwk.service.member;



import kr.or.kkwk.model.dto.member.MemberDto;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface MemberService {


  MemberDto getMemberInfo(String id, String password);




  MemberDto getMemberId(String id);

  boolean save(MemberDto memberVo);


  List<MemberDto> getMemberList();

  MemberDto memberLogin(MemberDto memberVo);
}
