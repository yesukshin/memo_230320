<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div class="d-flex justify-content-center">
	<form id = "loginForm" method="post" action="/user/sign_in">
		<div class="d-flex justify-content-center mt-2">
			<div class="form-inline">
			    <span class="input-group-text"><img src="/static/image/id.png" width="30"></span>						
				<input type="text" class="form-control" name="loginId"  name="loginId"  placeholder="아이디를 입력하세요">​
			</div>
   		</div>
                 
		<div class="d-flex justify-content-center mt-3">
			<div class="d-flex align-items-center">
				<span class="input-group-text"><img src="/static/image/pw.png" width="30"></span>
				<input type="text" class="form-control" id="passWord" name="passWord" placeholder="비밀번호를 입력하세요">		
	    	</div>
		</div>
							
		<div class="d-flex justify-content-center mt-3">
			<div class="d-flex align-items-center">
			   <button type="submit" id="loginBtn"  class="btn btn-primary btn-block">로그인</button>			
		    </div>
		    <div class="d-flex align-items-center ml-3">
			  <a class="btn btn-block btn-dark" href="/user/sign_up_view">회원가입</a>
		    </div>
		</div>
	</form>
</div>

<script>
$(document).ready(function() {
	// 로그인
	$('#loginForm').on('submit', function(e) {
		e.preventDefault(); // form submit 중단
		
		let loginId = $('input[name=loginId]').val().trim();
		let password = $('#passWord').val();
		
		if (!loginId) {
			alert("아이디를 입력하세요");
			return false;
		}
		if (!password) {
			alert("비밀번호를 입력하세요");
			return false;
		} 
		
		let url = $(this).attr('action');
		console.log(url);
		let params = $(this).serialize(); // name 속성 반드시 있어야함
		console.log(params);
		
		$.post(url, params)   // request
		.done(function(data) {  // response
			if (data.code == 1) {
				// 성공
				location.href = "/post/post_list_view"; // 글목록으로 이동
			} else {
				// 로직 실패
				alert(data.errorMessage);
			}
		});
	});
});
</script>