<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<form method="post" action="/user/sign_in">
					<div class="d-flex justify-content-center mt-2 ml-5">
						<div class="d-flex align-items-center ">
						    <span class="input-group-text"><img src="/static/image/id.png" width="30"></span>						
							<input type="text" class="form-control" name="userId"  placeholder="아이디를 입력하세요">
							<span><button type="submit" id="loginIdCheckBtn" class="btn btn-secondary btn-sm">중복확인</button></span>​
						</div>
			   		</div>

					<div class="d-flex justify-content-center mt-3">
						<div class="d-flex align-items-center">
							<span class="input-group-text"><img src="/static/image/pw.png" width="30"></span>
							<input type="text" class="form-control" name="passWord" placeholder="비밀번호를 입력하세요">		
				    	</div>
				    	<%-- 아이디 체크 결과 --%>
						<%-- d-none 클래스: display none (보이지 않게) --%>
						<div id="idCheckLength" class="small text-danger d-none">ID를 4자 이상 입력해주세요.</div>
						<div id="idCheckDuplicated" class="small text-danger d-none">이미 사용중인 ID입니다.</div>
						<div id="idCheckOk" class="small text-success d-none">사용 가능한 ID 입니다.</div>
					</div>
					
					<div class="d-flex justify-content-center mt-3">
						<div class="d-flex align-items-center">
							<span class="input-group-text"><img src="/static/image/pw.png" width="30"></span>
							<input type="text" class="form-control" name="passWordConfirm" placeholder="비밀번호 확인">		
				    	</div>
					</div>
					
					<div class="d-flex justify-content-center mt-3">
						<div class="d-flex align-items-center">
							<span class="input-group-text"><img src="/static/image/name.png" width="30"></span>
							<input type="text" class="form-control" name="name" placeholder="이름">		
				    	</div>
					</div>
					
					<div class="d-flex justify-content-center mt-3">
						<div class="d-flex align-items-center">
							<span class="input-group-text"><img src="/static/image/email.png" width="30"></span>
							<input type="text" class="form-control" name="email" placeholder="이메일주소">		
				    	</div>
					</div>
										
					<div class="d-flex justify-content-center mt-3">
						<div class="d-flex align-items-center">
						   <button type="submit" class="btn btn-primary btn-lg btn-block">가입하기</button>			
					    </div>
					</div>
				</form>
				<script>
				 $(document).ready(function() {
					 
					 $('#idCheckLength').addClass('d-none');
					 $('#idCheckDuplicated').addClass('d-none');
					 $('#idCheckOk').addClass('d-none');					 
					 
					 $("loginIdCheckBtn").on('click',function() {
						 let loginId = $('#loginId').val().trim();
						 
						 if (loginId.length < 4) {
							 $('#idCheckLength').removeClass('d-none');
							 return;
						 }
						 
						 // AJAX통신 - 중복확인
						 $.ajax({
							 //request 
							 //type생략=Get
							 url:"/user/is_duplicated_id"
							 ,data :{ "loginId": loginId}
							 ,success : function(data) {
								 if(data.isDuplicatedId) {
									//중복
									 $('#idCheckDuplicated').removeClass('d-none');
								 }else {
									 //중복아님-사용가능
									 $('#idCheckOk').removeClass('d-none');		
								 }
								 
							 }
							 ,error : fumction(request, status, error) {
								 alert("중복체크실패")
							 }
						 });
						 
					 });
					 
				 });
				 
				
				</script>