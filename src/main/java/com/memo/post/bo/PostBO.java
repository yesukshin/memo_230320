package com.memo.post.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.memo.post.dao.PostMapper;
import com.memo.post.domain.Post;

@Service
public class PostBO {
	@Autowired 
	private PostMapper postMapper; //Mapper라고 붙어 있으면 mybatis
	
	// input : userId
	// ouput : List<Post>
	// 널이 들어올수 없으므로 int : int userId
	public List<Post> getPostListByUserId(int userId){
		
		return postMapper.selectPostListByUserId(userId);
	}
	
	public int addPost(int userId, String subject, String content, MultipartFile file) {
		
		// 이미지가 있으면 업로드 후 imagepath받아옴
		String imagePath = null;
		return 1;
		//return postMapper.
		//SNS-회원가입/로그인, 타임라인 postList
		//MEMO-글쓰기 ㅣ완료
		
	}

}
