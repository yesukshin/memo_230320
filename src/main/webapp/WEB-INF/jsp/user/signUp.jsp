<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="d-flex justify-content-center mt-2 ml-5">
	<form id = "signUpForm" method="post" action="/user/sign_up">
		<div class="d-flex justify-content-center mt-3">
			<div class="d-flex align-items-center ">
				<span class="input-group-text"><img src="/static/image/id.png"	width="30"></span> 
				<input type="text" class="form-control"	id="loginId" name="loginId" placeholder="아이디를 입력하세요">
				<button type="button" id="loginIdCheckBtn" class="btn btn-secondary btn-sm">중복확인</button><br>
			</div>
		</div>	
		<%-- 아이디 체크 결과 --%>
		<%-- d-none 클래스: display none (보이지 않게) --%>
		<div id="idCheckLength" class="small text-danger d-none">ID를 4자이상 입력해주세요.</div>
		<div id="idCheckDuplicated" class="small text-danger d-none">이미 사용중인 ID입니다.</div>
		<div id="idCheckOk" class="small text-success d-none">사용 가능한 ID입니다.</div>

        <div class="d-flex justify-content-center mt-3">
			<div class="d-flex align-items-center">
			  <span class="input-group-text"><img src="/static/image/pw.png" width="30"></span> 
			  <input type="text" class="form-control"id="passWord" name="passWord"  placeholder="비밀번호를 입력하세요">
	       </div>
	    </div>

		<div class="d-flex justify-content-center mt-3">
			<div class="d-flex align-items-center">
				<span class="input-group-text"><img src="/static/image/pw.png" width="30"></span> 
				<input type="text" class="form-control"	id="passWordConfirm"  name="passWordConfirm" placeholder="비밀번호 확인">
			</div>
		</div>

		<div class="d-flex justify-content-center mt-3">
			<div class="d-flex align-items-center">
				<span class="input-group-text"><img	src="/static/image/name.png" width="30"></span> 
				<input type="text" class="form-control" id="name" name="name" placeholder="이름">
			</div>
		</div>

		<div class="d-flex justify-content-center mt-3">
			<div class="d-flex align-items-center">
				<span class="input-group-text"><img src="/static/image/email.png" width="30"></span> 
				<input type="text" class="form-control" id="email" name="email" placeholder="이메일주소">
		</div>

		<div class="d-flex justify-content-center mt-3">
			<div class="d-flex align-items-center">
				<button type="submit" class="btn btn-primary btn-lg btn-block">가입하기</button>
			</div>
		</div>
	</form>
</div>

<script>
	$(document).ready(function() {
		// 중복확인 버튼 클릭
		$("#loginIdCheckBtn").on('click', function() {
			// 경고 문구 초기화
			$('#idCheckLength').addClass('d-none');
			$('#idCheckDuplicated').addClass('d-none');
			$('#idCheckOk').addClass('d-none');

			let loginId = $('#loginId').val().trim();
			if (loginId.length < 4) {
				$('#idCheckLength').removeClass('d-none');
				return;
			}

			// AJAX 통신 - 중복확인
			$.ajax({
				// request
				url : "/user/is_duplicated_id",
				data : {
					"loginId" : loginId
				}

				// response
				,
				success : function(data) {
					if (data.isDuplicatedId) {
						// 중복
						$('#idCheckDuplicated').removeClass('d-none');
					} else {
						// 중복 아님 => 사용 가능
						$('#idCheckOk').removeClass('d-none');
					}
				},
				error : function(request, status, error) {
					alert("중복확인에 실패했습니다.");
				}
			});
		});
	    
		// 회원가입
	    $("#signUpForm").on('submit',function(e) {
	    	e.preventDefault(); //서브밋 기능중단
	    	
	    	//validation체크
	    	let loginId = $('input[name=loginId]').val().trim();
	    	let password = $('#passWord').val();
	    	let passwordConfirm = $('#passWordConfirm').val();
	    	let name = $('#name').val().trim();
	    	let email = $('#email').val().trim();
	    	
	    	if (!loginId) {
	    		alert("아이디를 입력하세요");
	    		return false;
	    	}
	    	
            if (!password || !passwordConfirm) {
            	alert("비밀번호를 입력하세요");
	    		return false;
	    	}
            
            if (password != passwordConfirm) {
            	alert("비밀번호가 일치하지 않습니다");
	    		return false;
	    	}
            
            if (!name) {
	    		alert("이름을 입력하세요");
	    		return false;
	    	}
            
            if (!email) {
	    		alert("이메일을 입력하세요");
	    		return false;
	    	}
            // 아이디 중복확인 완료되었는지 확인 - > idCheckOk d-none 이 있으면 얼럿을 띄워야함
            if ($('#idCheckOk').hasClass('d-none')) {
	    		alert("id를 확인하세요");
    			return false;	    	
	        }
	        //서버로 보내는 방법 두가지
	        
	        //1) form submit을 자바스크립트로 진행시킴
	        //$(this)[0].submit(); // 화면이동을 반드시 해야한다(컨트롤러가 redirect 하든 또는 jsp로 이동)
	        
	        //2)Ajax호출 컨트롤러가 json을 리턴해줌
	        let url = $(this).attr('action');
	        console.log(url);
	        let params = $(this).serialize(); //form태그의 name속성과 값들로 파라미터 구성
	        console.log(params);
	        $.post(url,params) // request정의
	        .done(function(data) {
	        	//response
	        	if(data.code == 1) {
	        		//로그인 화면으로 이동
	        	   alert("회원가입을 축하합니다 로그인을 해주세요.");
	        	   location.href = "/user/sign_in_view"; 	//로그인 화면으로 이동
	        		
	        	}else {
	        		//로직상 실패
        		   alert(data.errorMessage);
	        	}
	        });
	        
	    });
	});
</script>