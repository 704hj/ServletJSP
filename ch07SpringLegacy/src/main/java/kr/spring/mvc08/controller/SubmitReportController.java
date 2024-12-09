package kr.spring.mvc08.controller;

import java.io.File;
import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.itextpdf.text.log.SysoCounter;

import kr.spring.mvc08.vo.SubmitReportVO;

@Controller
public class SubmitReportController {
	//파일 업로드 경로 읽기 -> file.properties에 있는 경로를 써준다 "${경로}"
	@Value("${file_path}")
	private String path;
	
	
	//유효성 체크를 위한 자바빈(vo) 초기화
	@ModelAttribute
	public SubmitReportVO initCommand() {
		return new SubmitReportVO();
	}
	
	//폼 호출
	@GetMapping("/report/submitReport.do")
	public String form() {
		return "report/submitReportForm";
	}
	
	//전송된 데이터 처리
	@PostMapping("/report/submitReport.do")
	public String submit(@Valid SubmitReportVO vo, BindingResult result) throws IOException{
		System.out.println("전송된 데이터 : " + vo);
		
		//파일 업로드 필수 여부 체크->파일 관련 annotation이 없기 때문
		if(vo.getReportFile()==null || vo.getReportFile().isEmpty()) {
								//자바빈에 있는 필드, rejectValue에 내장된 오류코드 읽어오는 메서드-> 오류 메시지를 읽어옴
			result.rejectValue("reportFile", "required"); 
		}
		
		
		//전송된 데이터 유효성 체크 결과 오류가 있으면 폼 호출
		if(result.hasErrors()) {
			return form();
		}
		
		//파일이 저장될 절대 경로 정보를 갖는 File 객체 생성
		File file = new File(path + "/" + vo.getReportFile().getOriginalFilename());
		//지정한 경로에 파일 저장 
		vo.getReportFile().transferTo(file);
		
		System.out.println("주제 :" + vo.getSubject());
		System.out.println("업로드한 파일 : " + vo.getReportFile().getOriginalFilename());
		System.out.println("파일 용량 : " + vo.getReportFile().getSize());
		
		return "report/submittedReport";
	}
}



















