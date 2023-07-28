package com.memo.post.bo;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.memo.common.FileManagerService;
import com.memo.post.dao.PostMapper;
import com.memo.post.domain.Post;

@Service
public class PostBO {
	private Logger logger = LoggerFactory.getLogger(this.getClass()); 
	//private Logger logger = LoggerFactory.getLogger(PostBO.class); 
	
	// 로그레벨
	// local => dev(개발) => stage계(리얼환경과 동일 , 리얼계 직전) => real계
	// dev(개발) = > debug이후만 로그 다본다
	// stage계 = > info이후만 본다
	// real계 = > warning이후만 본다 등
    private static final int POST_MAX_SIZE = 3;			
			
	@Autowired 
	private PostMapper postMapper; //Mapper라고 붙어 있으면 mybatis
	@Autowired
	private FileManagerService fileManager;
	
	// input : userId
	// ouput : List<Post>
	// 널이 들어올수 없으므로 int : int userId
	public List<Post> getPostListByUserId(int userId, Integer prevId, Integer nextId){
		
		// 14 13 12   11 10 9  8 7 6   5
		// 만약 876페이지에 있을때
		// 1) 다음일때 6보다 작은 3개 desc정렬
		// 2) 이전일때 8번보다 큰것중 asc정렬로 가져와서 BO에서 list reverse시킴-> 11,10,9
		// 3) 첫페이지 일때 (이전, 다음 없음) desc 3개
		String direction = null ;// 방향
		Integer standardId = null;//기준 postId
		
		if (prevId != null) {
			//이전
			direction = "prev";
			standardId = prevId;
			// get list
			List<Post> postList = postMapper.selectPostListByUserId(userId,direction,standardId,POST_MAX_SIZE);
			// reverse 5,6,7->7,6,5
			Collections.reverse(postList);// 리벌스하고 postList에 저장까지 해준다
			// return
			return postList;
		} else if (nextId != null) {
			//다음
			direction = "next";
			standardId = nextId;
		} 
		
		return postMapper.selectPostListByUserId(userId,direction,standardId,POST_MAX_SIZE);
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
    
    public void updatePost(int userId, String userLoginId, 
    		int postId, String subject, String content, MultipartFile file) {
    	
    	// 글 수정하기 전에 수정할 대상 가져오기, 이후 이미지 제거위해서도 파일 패스 필요함
    	Post post = postMapper.selectPostByPostIdAndUserId(postId, userId);
    	//logger.warn("[글 수정] post is null postId:{},userID:{}", postId, userId);
    	if (post == null) {
    		logger.warn("[글 수정] post is null postId:{},userID:{}", postId, userId);
    		return;
    	}
    	
    	// 파일이 비어있지 않다면 업로드후 imagePath얻어오기
    	// 업로드가 성공하면 기존 이미지 제거
    	String imagePath = null;
    	
    	if (file != null) {
    		// 실제 업로드
    		imagePath = fileManager.saveFile(userLoginId, file); // 서버에 업로드 및 경로 돌려줌
    		logger.info("***imagePath:{}", imagePath);
    		logger.info("***post.getImagePath():{}", post.getImagePath());
    		// 이미지제거
    		// =>기존 이미지 있고 업로드 성공했을때
    		if(imagePath != null && post.getImagePath() != null) {
    			// 이미지 제거
                // fileManager엥게 요청
    			fileManager.deleteFile(post.getImagePath());
    		}
    	}
    	
    	// 글 업데이트
    	postMapper.updatePostByPostIdAndUserId(postId, userId, subject, content, imagePath);
    }
    
    public void deletePostByPostIdAndUserId(int postId, int userId){
		
    	// 기존글을 가져와서 이미지 삭제한다
    	Post post = postMapper.selectPostByPostIdAndUserId(postId, userId);
    	
    	if (post == null) {
    		logger.error("[글 삭제] post is null postId:{},userID:{}", postId, userId);
    		return;
    	}
    	//post안에 이미지path가 있으면 삭제
    	String imagePath = post.getImagePath();
    	
    	if (imagePath != null) {
    		// 이미지 제거
            // fileManager에게 요청
			fileManager.deleteFile(imagePath);
    	}
    	
		postMapper.deletePostByPostIdAndUserId(postId,userId);
	}
    
    // 이전방향의 끝인지 확인
    // input : prevId, userId
    // output : boolean
    public boolean isPrevLastPage(int prevId, int UserId) {
    	
    	 int postId = postMapper.selectPostIdByUserIdAndSort(UserId, "DESC");
    	 
    	 return prevId == postId; // 같으면 끝, 아니면 끝아님
    }
    
    // 다음 방향의 끝인지 확인
    // input : nextId, userId
    // output : boolean
    public boolean isNextLastPage(int prevId, int userId) {
    	
    	// postMapper.selectPostIdByUserIdAndSort(UserId, "ASC");
    	 
    	 return prevId == postMapper.selectPostIdByUserIdAndSort(userId, "ASC"); // 같으면 끝, 아니면 끝아님
    }
}