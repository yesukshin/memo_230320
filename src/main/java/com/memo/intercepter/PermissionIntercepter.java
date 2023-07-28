package com.memo.intercepter;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component  //일반적인 스트링 빈만들때 
public class PermissionIntercepter implements HandlerInterceptor {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
		// 요청 url path가져온다
		String uri = request.getRequestURI();
		
		logger.info("[**************]preHandle uri: {} ", uri);
		
		// 로그인 여부 확인 => session확인
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute("userId");
		
		// 비로그인 이면서 post로 온 경우 => 로그인 페이지로 이동, 컨트롤러 수행방지
		if(userId==null && uri.startsWith("/post")) {
			response.sendRedirect("/user/sign_in_view");
			return false; //컨트롤러 수행 안됨
		}
		
		// 로그인이면서 user로 온 경우 => 글목록 페이지로 인동 , 컨트롤러 수행방지
		if(userId!=null && uri.startsWith("/user")) {
			response.sendRedirect("/user/post_list_view"); // sendRedirect : 다른페이지로 보냄
			return false; //컨트롤러 수행 안됨
		}
		
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mav) {
		
		//뷰객체가 존재한다는건 아직 jsp가 html로 변환되기 전이다.
		logger.info("[###############]postHandler");
	}
	
	@Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		
		//jsp가 html로 최종변환된 후 
		logger.info("[@@@@@@@@@@@@@@@]afterCompletion");
	}
}
