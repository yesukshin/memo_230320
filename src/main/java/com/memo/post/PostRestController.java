package com.memo.post;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/post")
public class PostRestController {
	
	@PostMapping("/create")
	public Map<String, Object> create( 
			@RequestParam("subject") String subject,
			@RequestParam("content") String content,
			@RequestParam(value = "file", required = false) MultipartFile file,
			HttpSession session)
	{
		
		// 세션에서 유저정보 받아옴
		int userId = (Integer)session.getAttribute("userId");
		
		//db insert
		Map<String, Object> result = new HashMap<>();
		result.put("result", result);
		
		return result;
	}

}
