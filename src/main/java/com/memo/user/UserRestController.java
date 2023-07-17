package com.memo.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.memo.common.EncryptUtils;
import com.memo.user.bo.UserBO;
import com.memo.user.entity.UserEntity;

@RequestMapping("/user")
@RestController
public class UserRestController {
    
	@Autowired
	private UserBO userBO;
	
	/**
	 * 중복확인 API
	 * @param loginId
	 * @return
	 */
	@RequestMapping("/is_duplicated_id")
	public Map<String, Object> isDuplicatedId(@RequestParam("loginId") String loginId) {

		Map<String, Object> result = new HashMap<>();
		UserEntity userEntity  =  userBO.getUserEntityByLogibnId(loginId);
		
		result.put("code", 1);
		result.put("isDuplicatedId", false);		
		
		if (userEntity != null) {
			
			result.put("isDuplicatedId", true);
		}

		return result;
		
	}
	/**
	 * 회원가입 API
	 * @param loginId
	 * @param passWord
	 * @param name
	 * @param email
	 * @return
	 */
	@PostMapping("/sign_up")
	public Map<String, Object> signUp(
			@RequestParam("loginId") String loginId,
			@RequestParam("passWord") String passWord,
			@RequestParam("name") String name,
			@RequestParam("email") String email)
	{   
		// 비밀번호 해싱 - md5 알고리즘 사용
		// static 메소드는 인스턴스 생성없이 바로 사용가능함 
		// 비밀번호 aaaa = > 74b8733745420d4d33f80c4663dc5e5
		String hashedPassword = EncryptUtils.md5(passWord);
		// 비밀번호 암호화
		
		// db insert
		Integer userId = userBO.addUser(loginId, hashedPassword, name, email);
		
		Map<String, Object> result = new HashMap<>();
		
		if (userId != null) { 
			result.put("code", 1);
			result.put("result", "성공");
		} else {
			result.put("code", 500);
			result.put("result", "회원가입시 실패했습니다");	
		}
		
		return result; 
		
	}

}
