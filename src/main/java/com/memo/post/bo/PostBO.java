package com.memo.post.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.memo.common.FileManagerService;
import com.memo.post.dao.PostMapper;
import com.memo.post.domain.Post;

@Service
public class PostBO {
	@Autowired 
	private PostMapper postMapper; //Mapper라고 붙어 있으면 mybatis
	@Autowired
	private FileManagerService fileManager;
	
	// input : userId
	// ouput : List<Post>
	// 널이 들어올수 없으므로 int : int userId
	public List<Post> getPostListByUserId(int userId){
		
		return postMapper.selectPostListByUserId(userId);
	}
	
	public int addPost(int userId, String userLoginId, String subject, String content, MultipartFile file) {
		
		// 이미지가 있으면 업로드 후 imagepath받아옴
		String imagePath = null;
		
		//이미지가 잇으면 업로드 후 이미지패스 받아옴
		if(file != null) {
			imagePath = fileManager.saveFile(userLoginId, file);
		}
		
		return postMapper.insertPost(userId,subject,content,imagePath);
	}
	
    public Post getPostByPostIdAndUserId(int postId, int userId){
		
		return postMapper.selectPostByPostIdAndUserId(postId, userId);
	}
}