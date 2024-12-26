package kr.spring.member.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kr.spring.member.dao.MemberMapper;
import kr.spring.member.service.MemberService;
import kr.spring.member.vo.MemberVO;
import kr.spring.member.vo.PrincipalDetails;
import kr.spring.member.vo.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
//클래스 내에서 final 또는 @NonNull로 선언된 필드에 대해 생성자를 자동으로 생성
@RequiredArgsConstructor
@Service
public class UserSecurityService 
                           implements UserDetailsService{
	//@RequiredArgsConstructor에 의해 의존성 주입됨->@Autowired 생략 가능?
	private final MemberService memberService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.debug("<<로그인 아이디>> : "+ username);
		MemberVO member = memberService.selectCheckMember(username);
		if(member==null || member.getAuth()==0) {
			log.debug("<<로그인 아이디가 없거나 탈퇴회원>>");
			throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
		}
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		if(member.getAuth()==9) {
			authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
		}else {
			authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
		}
		
		
		
		return new PrincipalDetails(member);
	}

}
