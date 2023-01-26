package kr.or.kkwk.service;



import kr.or.kkwk.model.dto.MemberDto;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface MemberService {


  MemberDto getMemberInfo(String id, String password);




  MemberDto getMemberId(String id);

  boolean save(MemberDto memberVo);


  List<MemberDto> getMemberList();

  String memberLogin(MemberDto memberVo);
}
