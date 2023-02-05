package kr.or.kkwk.controller.member;



import kr.or.kkwk.common.exceptio2.ApiException;
import kr.or.kkwk.common.exceptio2.ExceptionEnum;
import kr.or.kkwk.model.dto.member.MemberDto;
import kr.or.kkwk.service.member.MemberService;
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
    @RequestMapping("/")
    public void goDomain(){
        System.out.println("");


    }

    /**
     * EntityManagerFactory 생성
     *
     * @param
     * @return
     */
    @GetMapping("/getMember")
    public MemberDto getMember(){
        MemberDto memberVo = memberService.getMemberId("admin");
        return memberVo;

    }

    @RequestMapping("/getMemberPost")
    public MemberDto getMemberPost(@RequestBody MemberDto memberVo){
        //throw new NotFoundException("예외 처리");
        MemberDto memberVo1 = memberService.getMemberId(memberVo.getId());
        if(memberVo1 == null){
            throw new ApiException(ExceptionEnum.ZERO_01);
        }
        return memberVo1;
    }

    @RequestMapping("/save")
    public String saveMeber(@Valid MemberDto memberVo){
        memberService.save(memberVo);

        return "";
    }

    @PostMapping("/login")
    public String login(@RequestBody MemberDto memberVo) {
        String token = memberService.memberLogin(memberVo);

        return token;
    }

    /*@RequestMapping("/getSection")
    public MovieSectionDto getSection(){
        return
    }
*/

}
