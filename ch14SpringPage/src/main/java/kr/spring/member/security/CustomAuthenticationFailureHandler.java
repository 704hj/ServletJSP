package kr.spring.member.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.FlashMapManager;
import org.springframework.web.servlet.support.SessionFlashMapManager;

import lombok.extern.slf4j.Slf4j;
//로그인 실패시 작동

@Slf4j
@Component
public class CustomAuthenticationFailureHandler 
             extends SimpleUrlAuthenticationFailureHandler{
	@Override
	public void onAuthenticationFailure(
			              HttpServletRequest request,
			              HttpServletResponse response,
			              AuthenticationException exception)
	                    throws IOException,ServletException{
		log.error("<<CustomAuthenticationFailureHandler 진입>> : " + exception.toString());
		
		String error = "";//왜 비어있지
		if(exception instanceof IllegalUserException) {
			if(exception.getMessage().equals("USERNAME")) {
				error = "username";
			}else if(exception.getMessage().equals("PASSWORD")) {
				error = "password";
			}else {
				error = "username_password";
			}
		}else if(exception instanceof UsernameNotFoundException | exception instanceof BadCredentialsException) {
			error = "error";
		}
		//페이지가 리다이렉트되어서 아래와 같이 셋팅했을 경우
        //request에서 데이터를 읽을 수 있게 처리할 수 있음 
        final FlashMap flashMap = new FlashMap();
        flashMap.put("error", error);
        final FlashMapManager  flashMapManager = new SessionFlashMapManager();
        flashMapManager.saveOutputFlashMap(flashMap, request, response);
        
        setDefaultFailureUrl("/member/login");
        
        //부모 호출 -> 부모 메서드에는 실패 시 설정된 URL로 리다이렉트하는 로직이 이미 구현
        super.onAuthenticationFailure(request, response, exception);
	}
	
}
  