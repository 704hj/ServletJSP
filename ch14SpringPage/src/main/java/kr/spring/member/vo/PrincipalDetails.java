package kr.spring.member.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Data;
/*
 * 시큐리티가 /member/login 주소 요청이 오면 낚아채서 로그인을 진행
 * 로그인 진행이 완료되면 시큐리티 session을 만듦 
 */


@Data
public class PrincipalDetails implements UserDetails, OAuth2User {
	private MemberVO memberVO; 
	private Map<String, Object> attributes;

	public PrincipalDetails(MemberVO memberVO) {
		this.memberVO=memberVO;
	}

	public PrincipalDetails(MemberVO memberVO, Map<String, Object> attributes) {
		this.memberVO=memberVO;
		this.attributes=attributes;
	}
	
	public MemberVO getMemberVO() {
		return memberVO;
	}

	public void setMemberVO(MemberVO memberVO) {
		this.memberVO = memberVO;
	}

	@Override 
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collect = new ArrayList<>();
		collect.add(new GrantedAuthority() {
			@Override
			public String getAuthority() {
				if(memberVO.getAuth()==9) {
					return UserRole.ADMIN.getValue();
				}else {
					return UserRole.USER.getValue();
				}
			}
		});
		return collect;
	}
	@Override // password
	public String getPassword() {
		return memberVO.getPasswd();
	}

	@Override // name
	public String getUsername() {
		return memberVO.getId();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override 
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override 
	public boolean isEnabled() {
		return true;
	}
	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@Override 
	public String getName() {
		return null;

	}
}