package kr.spring.member.security;

import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import kr.spring.member.dao.MemberMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomAuthenticationProvider 
                        implements AuthenticationProvider{

	private PasswordEncoder passwordEncoder;
	private UserSecurityService userSecurityService;
	private HttpSession session;
	private MemberMapper memberMapper;
	
	
	
	public CustomAuthenticationProvider(PasswordEncoder passwordEncoder, UserSecurityService userSecurityService,
			HttpSession session, MemberMapper memberMapper) {
		super();
		this.passwordEncoder = passwordEncoder;
		this.userSecurityService = userSecurityService;
		this.session = session;
		this.memberMapper = memberMapper;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		log.debug("<<CustomAuthenticationProvider 로그인 체크>>");
		
		//Authentication 객체에서 username과 password를 꺼냄
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		
		if((username == null || username.isEmpty()) && (password == null || password.isEmpty())) {
			throw new IllegalUserException("USERNAME_PASSWORD");
		}
		if((username == null || password.isEmpty())){
			throw new IllegalUserException("USERNAME");
		}
		if((password == null || password.isEmpty())){
			throw new IllegalUserException("PASSWORD");
		}
		
		//UserDetailsService에서 UserDetails를 꺼냄
		UserDetails userDetails = userSecurityService.loadUserByUsername(username);
		
		//UserDetails의 password와 Authentication객체의 password를 비교
		if(!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException(
					"아이디 또는 비밀번호가 일치하지 않습니다.");
		}
		
		log.debug("<<CustomAuthenticationProvider 비밀번호 인증 통과>> : ");
		
		//username, password 일치시 토큰 생성
		return new UsernamePasswordAuthenticationToken(userDetails,password,userDetails.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
