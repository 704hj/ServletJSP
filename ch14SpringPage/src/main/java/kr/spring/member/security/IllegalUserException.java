package kr.spring.member.security;

import org.springframework.security.core.AuthenticationException;

public class IllegalUserException 
                      extends AuthenticationException{

	public IllegalUserException(String msg) {
		super(msg);
	}

}
