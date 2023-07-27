<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<div class="d-flex justify-content-center">
	<div class="w-50">
		<h1>글상세</h1>		
		<input type="text" id="subject" class="form-control" value="${post.subject}">
		<textarea id="content" class="form-control" rows="10">${post.content}</textarea>
		
		<%--이미지가 있을때만 이미지 영역 추가 --%>
		<c:if test="${not empty post.imagePath}">
			<div class = "my-3">
			    <image src = "${post.imagePath}" width="300">
			</div>    
		</c:if> 
		
		<div class="d-flex justify-content-end my-4">
			<input type="file" id="file" accept=".jpg, .jpeg, .png, .gif">
		</div>
		
		
		<div class="d-flex justify-content-between">
			<button type="button" id="deleteBtn" class="btn btn-secondary" data-post-id="${post.id}">삭제</button>
			
			<div>
				<a href="/post/post_list_view" class="btn btn-dark">목록</a>
				<button type="button" id="updateBtn" class="btn btn-warning"  data-post-id="${post.id}">수정</button>
			</div>
		</div>
	</div>
</div>

<script>
$(document).ready(function() {
	// 수정
	//$('#clearBtn').on('click', function() {
	$('#updateBtn').on('click', function() {
		
		let subject  = $('#subject').val().trim();
		let content  = $('#content').val();
		let file = $('#file').val(); // 파일명
		
		if(!subject) {
			alert("제목을 입력하세요");
			return false;
		}
		if(!content) {
			alert("내용을 입력하세요");
			return false;
		}
		console.log(file);
		
		//파일 업로드 하는경우 파일 확장자 체크		
        if (file) {
        	let ext = file.split(".").pop().toLowerCase();
        	alert(ext);
        	if($.inArray(ext,['jpg','jpeg','gif','png']) == -1) {
        		alert("이미지 파일만 업로드할수 있습니다");
        		$('#file').val(""); //파일을 비운다
        		return;
        	}
        }
		
		// 폼태그를 스크립트로 만든다
        let postId = $(this).data('post-id');
		let formData = new FormData();//비어있는 폼태그
		formData.append("postId",postId);
		formData.append("subject",subject);
		formData.append("content",content);
		formData.append("file",$('#file')[0].files[0]);
		
		$.ajax({
		   //request
		   type:"put"
		   , url : "/post/update"
		   , data : formData
		   , enctype:"multipart/form-data"
		   ,processData: false
		   ,contentType: false
		   //response
		   , success:function(data) {
			   if (data.code == 1) {
				   alert("수정성공");
				   location.reload();
			   }else {
				   alert("")
			   }
		   
		   }
		   , error : function(requestg, status, error) {
			   alert("수정실패");
		   } 
		   
		});
	 });	
	 //삭제버튼
	$('#deleteBtn').on('click', function() {
			
		    let postId = $(this).data('post-id');
			
			$.ajax({
				   //request
				   type:"delete"
				   , url : "/post/delete"
				   , data : {"postId":postId}				 
				   , success:function(data) {
					   if (data.code == 1) {
						   alert("삭제성공");
						   location.href="/post/post_list_view"
					   }else {
						   alert(data.errorMessage)
					   }
				   }
				   , error : function(requestg, status, error) {
					   alert("삭제실패");
				   } 
				   
				});
	});
	
});
</script>