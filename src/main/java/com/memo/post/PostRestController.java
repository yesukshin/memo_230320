package com.memo.post;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.memo.post.bo.PostBO;

@RestController
@RequestMapping("/post")
public class PostRestController {
	@Autowired
	private PostBO postBO;
	
	/**
	 * 글쓰기 API
	 * @param subject
	 * @param content
	 * @param file
	 * @param session
	 * @return
	 */
	@PostMapping("/create")
	public Map<String, Object> create( 
			@RequestParam("subject") String subject,
			@RequestParam("content") String content,
			@RequestParam(value = "file", required = false) MultipartFile file,
			HttpSession session)
	{
		
		// 세션에서 유저정보 받아옴, controller에서 세션관리
		int userId = (Integer)session.getAttribute("userId");
		String userLoginId = (String)session.getAttribute("userLoginId");
		
		//db insert
		int rowcnt = postBO.addPost(userId, userLoginId, subject, content, file);
		
		Map<String, Object> result = new HashMap<>();
		
		if (rowcnt > 0) {
			result.put("code", 1);
		}else {
			result.put("code", 500);
			result.put("errorMessage","글쓰기 저장중 오류발생");
		}
		
		return result;
	}

	/**
	 * 글수정 API
	 * @param postId
	 * @param subject
	 * @param content
	 * @param file
	 * @param session
	 * @return
	 */
	@PutMapping("/update")
    public Map<String, Object> update(
    		@RequestParam("postId") int postId,
    		@RequestParam("subject") String subject,
			@RequestParam("content") String content,
			@RequestParam(value = "file", required = false) MultipartFile file,
			HttpSession session) {
		
		// 세션에서 userId, userLogin ID
		int userId = (int)session.getAttribute("userId");
		String userLoginId = (String)session.getAttribute("userLoginId");
		
		// BO update
		postBO.updatePost(userId, userLoginId, postId, subject, content, file);
    	
		// 응답
		Map<String, Object> result = new HashMap<>();
		result.put("code", 1);
		result.put("result", "성공");
		
		return result; 
    }
	
	@DeleteMapping("/delete")
	public Map<String, Object> delete(
			@RequestParam("postId") int postId,
			HttpSession session) {
		
		int userId = (int)session.getAttribute("userId");
		
		postBO.deletePostByPostIdAndUserId(postId, userId);
		Map<String, Object> result = new HashMap<>();
		result.put("code", 1);
		result.put("result", "성공");
		return result; 
	
	}
    
}   
