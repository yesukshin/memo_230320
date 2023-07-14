<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<form method="post" action="/user/sign_in">
					<div class="d-flex justify-content-center mt-2">
						<div class="form-inline">
						    <span class="input-group-text"><img src="/static/image/id.png" width="30"></span>						
							<input type="text" class="form-control" name="userId"  placeholder="아이디를 입력하세요">​
						</div>
			   		</div>
                    
					<div class="d-flex justify-content-center mt-3">
						<div class="d-flex align-items-center">
							<span class="input-group-text"><img src="/static/image/pw.png" width="30"></span>
							<input type="text" class="form-control" name="passWord" placeholder="비밀번호를 입력하세요">		
				    	</div>
					</div>
										
					<div class="d-flex justify-content-center mt-3">
						<div class="d-flex align-items-center">
						   <button type="submit" class="btn btn-primary btn-lg btn-block">로그인</button>			
					    </div>
					    <div class="d-flex align-items-center ml-3">
						  <button type="submit" class="btn btn-secondary btn-lg btn-block">회원가입</button>			
					    </div>
					</div>
					
				    
				</form>