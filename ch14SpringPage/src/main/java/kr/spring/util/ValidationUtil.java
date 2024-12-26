package kr.spring.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import lombok.extern.slf4j.Slf4j;
//로그인시 
@Slf4j //로그 찍으려고 함
public class ValidationUtil {
	public static void printErrorFields(BindingResult result) {
		log.debug("<<=====유효성 체크시 에러 발생 필드 목록 시작=====>>");
		for(FieldError f : result.getFieldErrors()) {
			log.debug("<<에러 필드>> : " + f.getField());
		}
		log.debug("<<=====유효성 체크시 에러 발생 필드 목록 끝=====>>");
	}

}
