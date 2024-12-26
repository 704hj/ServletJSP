package kr.spring.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.security.web.csrf.MissingCsrfTokenException;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.FlashMapManager;
import org.springframework.web.servlet.support.SessionFlashMapManager;

import kr.spring.member.dao.MemberMapper;
import kr.spring.member.security.CustomAuthenticationProvider;
import kr.spring.member.security.UserSecurityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration //설정파일
@EnableWebSecurity //모든 요청 url이 스프링 시큐리티의 제어를 받도록 만드는 annotation, security를 활성화하는 역할
@EnableGlobalMethodSecurity(prePostEnabled = true)//Controller 메서드 레벨에서 권한을 체크할 수 있도록 설정. @PreAuthorize 사용시 추가
//final 필드와 @NotNull 필드에 대해 생성자를 자동 생성
@RequiredArgsConstructor
public class SecurityConfig{
	@Value("${dataconfig.rememberme-key}")
	private String rememberme_key;
	
	@Autowired
	@Qualifier("dataSource") //@Qualifier("이름")를 사용해 정확하게 특정 Bean을 지정
	private DataSource dataSource;
	
	private final AuthenticationConfiguration authenticationConfiguration;

    @Autowired
    private UserSecurityService userSecurityService;
	
	@Autowired
	private HttpSession session;
	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private AuthenticationFailureHandler customAuthenticationFailureHandler;
	@Autowired
	private AuthenticationSuccessHandler customAuthenticationSuccessHandler;
	
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		//인증되지 않은 모든 페이지의 요청을 허락한다는 의미.
		//따라서 로그인하지 않더라도 모든 페이지에 접근
		return http
				.authorizeHttpRequests(authorize-> authorize
						//롤 설정을 먼저하고 permitAll()를 호출해야 정상적으로 롤이 지정됨
						///main/admin : ROLE_ADMIN 권한 필요 지정
						.antMatchers("/main/admin").hasAuthority("ROLE_ADMIN")
						//기타 경로 : 누구나 접근 가능
						.antMatchers("/**").permitAll()
						//인증되지 않은 요청은 로그인 페이지로 리다이렉트됨
						.anyRequest().authenticated() 
						)
				.formLogin(login -> login //로그인 설정
						.loginPage("/member/login")
						.successHandler(customAuthenticationSuccessHandler)//성공시 호출
						.failureHandler(customAuthenticationFailureHandler)//실패시 호출
						.usernameParameter("id")
						.passwordParameter("passwd"))
				.logout(logout -> logout //로그아웃 설정
						.logoutUrl("/member/logout")
						.logoutSuccessUrl("/")
						.invalidateHttpSession(true)
						.deleteCookies("remember-me","JSESSIONID"))//로그인 상태 유지->로그아웃 시 해당 정보 전부 삭제
				.exceptionHandling(error -> error
						.accessDeniedHandler(new AccessDeniedHandler(){
							@Override
							public void handle(HttpServletRequest request, HttpServletResponse response,
									org.springframework.security.access.AccessDeniedException accessDeniedException)
									throws IOException, ServletException {
								log.error("<<예외 발생>> : " + accessDeniedException.toString());
								log.debug("<<예외 발생 페이지>> : " + request.getRequestURI());
								log.debug("<<x-csrf-token>> : " + request.getHeader("x-csrf-token"));
								
								if(accessDeniedException instanceof MissingCsrfTokenException || accessDeniedException instanceof InvalidCsrfTokenException) {
									//로그아웃 상태에서 로그아웃 주소를 호출하면 오류가 발생하기 때문에 아래와 같이 조건 체크해서 로그인 페이지가 보여지는 
									if(request.getRequestURI().equals("/member/logout")) {
										response.sendRedirect("/main/main");//로그인 시 main으로 
									}else if(request.getHeader("x-csrf-token")!=null){
										response.sendRedirect("/common/accessLogout");//동일 계정으로 로그인 한 창을 두개 띄우고 한 창에서 로그아웃을 하고 또 다른 창에서 로그인 한 채로 수행해야하는 작업을 시도하면 로그아웃 된 다른 창의 영향을 받아서 로그인 후 사용하세요 라는 문구가 뜨는데 그 이유가 여기로 진입해서임
									}else {
										final FlashMap flashMap = new FlashMap();
										flashMap.put("accessMsg","CSRF Token이 지정되지 않습니다.");
										final FlashMapManager flashMapManager = new SessionFlashMapManager();
										flashMapManager.saveOutputFlashMap(flashMap, request, response);
										response.sendRedirect("/common/accessDenied");
									}
								}else if(accessDeniedException instanceof AccessDeniedException) {
									response.sendRedirect("/common/accessDenied");
									
								}else {
									if(request.getRequestURI().equals("/member/logout")) {
										response.sendRedirect("/main/main");
									}else {
										response.sendRedirect("/member/login");
									}
								}
								
							}
						}))
				.rememberMe(me -> me
						.key(rememberme_key) //쿠키에 사용되는 값을 암호화하기 위한 키(KEY)값
						.tokenRepository(persistentTokenRepository())//토큰객체를 데이터베이스에 저장
						.tokenValiditySeconds(60*60*24*7)
						.userDetailsService(userSecurityService))
				//.csrf(csrf -> csrf.disable()) //주석 처리가 CSRF 보호 기능을 활성화 : 전송되는 정보 검증 -> 우리 사이트에서 생성된 정보 or 다른 사이트에서 생성된 정보?
				.build();	
	}
	
	
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public CustomAuthenticationProvider customAuthenticationProvider() {
		return new CustomAuthenticationProvider(passwordEncoder(),userSecurityService,session,memberMapper);
	}
	
	
	
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
		repo.setDataSource(dataSource);
		return repo;
	}
	
	
	/*
	 * 자동로그인 사용시 자동으로 생성되는 persistent_logins 테이블의 컬럼 설명
	 * series : 	사용자의 로그인 세션을 식별하는 고유한 값 
	 * username :   로그인한 사용자의 ID
	 * token : 		사용자의 브라우저에 저장되는 토큰 값
	 * 		  		(쿠키에 저장되는 암호화된 토큰 값)
	 * 		   		이 토큰을 통해 시스템은 사용자를 인증
	 * 		   		매번 로그인이 유지될 때마다 갱신
	 * 		   		토큰이 일치하지 않으면 Remember-Me session이 무효화
	 * last_used :  토큰이 마지막으로 사용된 시각.
	 * 				토큰의 유효 기간을 관리하는 데 사용
	 * */
	
	@Bean
	public AuthenticationManager authenticationManager() throws Exception{
		ProviderManager providerManager = (ProviderManager)authenticationConfiguration.getAuthenticationManager();
		providerManager.getProviders().add(customAuthenticationProvider());
		
		return authenticationConfiguration.getAuthenticationManager();
	
	}
	
	
	
	
}


























