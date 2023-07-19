<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="d-flex justify-content-center">
	<div class="w-50">
		<h1>글쓰기</h1>
		
		<input type="text" id="subject" class="form-control" placeholder="제목을 입력하세요">
		<textarea id="content" class="form-control" rows="10" placeholder="내용을 입력하세요"></textarea>
		
		<div class="d-flex justify-content-end my-4">
			<input type="file" id="file" accept=".jpg, .jpeg, .png, .gif">
		</div>
		
		<div class="d-flex justify-content-between">
			<button type="button" id="postListBtn" class="btn btn-dark">목록</button>
			
			<div>
				<button type="button" id="clearBtn" class="btn btn-secondary">모두 지우기</button>
				<button type="button" id="saveBtn" class="btn btn-warning">저장</button>
			</div>
		</div>
	</div>
</div>


<script>

$(document).ready(function() {
	//목록버튼 클릭시 => 글목록 이동
	$('#postListBtn').on('click', function() {
		location.href = "/post/post_list_view";
	});
	
	//모두 지우기
	$('#clearBtn').on('click', function() {
		$('#subject').val("");
		$('#content').val("");
	});
	
	//저장하기
	$('#saveBtn').on('click', function() {
	   
	   let subject = $('#subject').val().trim();
	   let content = $('#content').val();
	   let file = $('#file').val(); //파일경로를 가지고 있음
	   
	   if (!subject) {
   	   	alert("제목을 입력하세요");
   		return;
   	   }
   	
       if (!content) {
       	alert("내용을 입력하세요");
   		return;
       }
       
       //파일이 업로드 된 경우에만 확장자 체크
       if (file != "") {
    	   // 확장자만 뽑은 후 소문자로 변경한다
    	   // stack에 있는 걸 pop으로 뽑아냄
    	   let ext = file.split(".").pop().toLowerCase();
    	   //alert(ext); jpeg, .png, .gif">
    	   if($.inArray(ext,['jpg','jpeg','png','gif']) == -1) {
    		   alert("이미지 파일만 업로드 할수 있습니다");
    		   $('#file').val(''); //파일을 비운다
    		   return;
    	   }
    	   
       }
       //파일이미지 업로드는 반드시 폼태그로 해야한다.requestbody에 넣어서 보내야한다, 그러니까 post방식으로 보내야함     
       //자바스크립으로 폼태그 처럼 보낼수 있다 
       let formData = new FormData();
       formData.append("subject", subject); // key는 폼태그의 name속성과 같고 RequestParm명이 된다.
       formData.append("content", content); // key는 폼태그의 name속성과 같고 RequestParm명이 된다.
       formData.append("file", $('#file')[0].files[0]); // 바이너리로 보내야한다. file태그의 0번째 하나만 보냄 
       
       
       $.ajax({
    	   // request
    	   type:"post"
    	   , url:"/post/create"
    	   , data:formData
    	   , enctype:"multipart/form-data" //파일업로드를 위한 필수설정
    	   , processData:false //파일업로드를 위한 필수설정
    	   , contentType:false //파일업로드를 위한 필수설정
    	  
    	   // response
    	   , success:function(data) {
    		   if (data.code == 1) {
    			   //글목록 화면으로 이동
    			   location.href = "/post/post_list_view";
    			   
    		   }else {
    			   //로직상 실패
    			   alert(data.errorMessage);
    		   }
    	   }
       	   , error:function(request, status, error) {
    	   	alert("글을 저장하는데 실패했습니다");
       	}
       });

	});

});
</script>