package com.memo.post;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.memo.post.bo.PostBO;
import com.memo.post.domain.Post;

@RequestMapping("/post")
@Controller
public class PostController {

	@Autowired
	private PostBO postBO;

	@GetMapping("/post_list_view")
	public String postListView(
			 @RequestParam(value="prevId", required=false) Integer prevIdParam
			,@RequestParam(value="nextId", required=false) Integer nextIdParam
			,HttpSession session, Model model) {
		// 로그인 여부 조회
		Integer userId = (Integer) session.getAttribute("userId");
		if (userId == null) {
			// 비로그인이면 로그인 페이지로 이동
			return "redirect:/user/sign_in_view";
		}

		// DB 글 목록 조회
		List<Post> postList = postBO.getPostListByUserId(userId, prevIdParam, nextIdParam);
        int nextId = 0;
        int prevId = 0;
        
        if (postList.isEmpty() == false) {
        	// postList가 비어있을때([]) 오류방지
        	 nextId = postList.get(postList.size() - 1).getId(); // 가져온 리스트의 가장 작은 id
        	 prevId = postList.get(0).getId(); // 가져온 리스트의 가장 큰 id
        	 
        	 // 이전방향의 끝인가?
        	 // prevId와 post테이블의 가장큰값과 같으면 이전 페이지 없다
        	 if (postBO.isPrevLastPage(prevId,userId)) {// 끝이면
        		 prevId = 0;
        	 }
        	 // 다음방향의 끝인가?
        	 // nextId와 post테이블의 가장작은값과 같으면 다음 페이지 없다
        	 if (postBO.isNextLastPage(nextId,userId)) {// 끝이면
        		 nextId = 0;
        	 }
        }
        
        model.addAttribute("nextId", nextId);
        model.addAttribute("prevId", prevId);
		model.addAttribute("postList", postList);
		model.addAttribute("view", "post/postList");
		return "template/layout";
	}

	/**
	 * 글쓰기 화면
	 * @param model
	 * @return
	 */
	@GetMapping("/post_create_view")
	public String postCreateView(Model model) {
		model.addAttribute("view", "post/postCreate");
		return "template/layout";
	}

	@GetMapping("/post_detail_view")
	public String postDetailView(
		@RequestParam("postId") int postId, 
		Model model,
		HttpSession session)
		{
			
			int userId = (int)session.getAttribute("userId");
			
			// post select postId, userId
			Post post = postBO.getPostByPostIdAndUserId(postId, userId);
					
			model.addAttribute("view","post/postDetail" );
			model.addAttribute("post",post);
		
			return "template/layout";
	    }
}