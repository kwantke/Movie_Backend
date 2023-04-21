package kr.or.kkwk.controller.member;



import kr.or.kkwk.common.exception.ApiException;
import kr.or.kkwk.common.exception.ExceptionEnum;
import kr.or.kkwk.controller.RestResponseEntity;
import kr.or.kkwk.model.dto.member.MemberDto;
import kr.or.kkwk.service.member.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RequiredArgsConstructor
@RestController
public class MemberController {


    MemberService memberService;

    /**
     * 데이터베이스 연결
     * <pre>
     * NOTE: 데이터베이스 설정 정보는 application.yml에 설정된
     *       dataSource 설정 정보이다.
     *       여기 정보를 가져오는 부분이 @ConfigurationProperties
     *       이다.
     * </pre>
     *
     * @return
     */
    @Autowired
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    @PostMapping("/save")
    public ResponseEntity saveMeber(@RequestBody MemberDto memberVo){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(memberService.save(memberVo));
    }
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody MemberDto memberVo) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(memberService.memberLogin(memberVo));
    }

    /**
     * TDD Test 케이스 생성
     *
     * @param
     * @return
     */
    @GetMapping("/getMember")
    public ResponseEntity getMember(){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(memberService.getMemberId("admin"));

    }

    @RequestMapping("/getMemberPost")
    public MemberDto getMemberPost(@RequestBody MemberDto memberVo){
        //throw new NotFoundException("예외 처리");
        MemberDto memberVo1 = memberService.getMemberId(memberVo.getId());
        if(memberVo1 == null){
            throw new ApiException(ExceptionEnum.ERROR_0001);
        }
        return memberVo1;
    }

}
