package kr.spring.member.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import kr.spring.member.service.MemberService;
import kr.spring.member.vo.MemberVO;
import kr.spring.member.vo.PrincipalDetails;
import lombok.extern.slf4j.Slf4j;
//로그인 성공시 작동

@Slf4j
@Component
public class CustomAuthenticationSuccessHandler 
               extends SimpleUrlAuthenticationSuccessHandler{
	
	@Override
	public void onAuthenticationSuccess(
			               HttpServletRequest request,
			               HttpServletResponse response,
			               Authentication authentication)
	                   throws IOException,ServletException{
		
		//정보를 받았으니까 로그로 진입 확인
		log.debug("<<====CustomAuthenticationSuccessHandler 진입===>>");
		log.debug("<<Authentication>> : " + authentication);
		MemberVO user = ((PrincipalDetails)authentication.getPrincipal()).getMemberVO();
		log.debug("<<로그인 체크 여부 - MemberVO>> : " + user);
		
		if(user.getAuth() == 9) {//관리자
			setDefaultTargetUrl("/main/admin");
		}else {
			//루트로 이동시 생략 가능
			setDefaultTargetUrl("/");
		}
		super.onAuthenticationSuccess(request, response, authentication);
	}

}



