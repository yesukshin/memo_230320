<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<!-- bootstrap, jquery  -->
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css"
	integrity="sha384-xOolHFLEh07PJGoPkLv1IbcEPTNtaed2xpHsD9ESMhqIYd0nLMwNLD69Npy4HI+N"
	crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.7.0.js"
	integrity="sha256-JlqSTELeR4TLqP0OG9dxM7yDPqX1ox/HfgiSLBj8+kM="
	crossorigin="anonymous"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-Fy6S3B9q64WdZWQUiU+q4/2Lc9npb8tCaSX9FK7E8HnRr0Jz8D6OP9dO5Vg3Q9ct"
	crossorigin="anonymous"></script>
<!-- 나의 css  -->
<link rel="stylesheet" type="text/css" href="/css/sns/style.css">
</head>
<body>
	<div class="container" id="wrap">
	<header class="d-flex justify-content-center align-items-center mt-5">
			<h1>메모 게시판 로그인</h1>
		</header>
	<div class="goods-box">
		
		<section class="justify-content-center align-items-center">
		
				<form method="post" action="/user/sign_in">
					<div class="d-flex justify-content-center mt-2">
						<div class="form-inline">
						    <span class="input-group-text"><img src="/image/memo/id.png" width="30"></span>						
							<input type="text" class="form-control" name="userId"  placeholder="아이디를 입력하세요">
							<button type="button" id="dupChkBtn" class="btn btn-info">중복확인</button>​
						</div>
			   		</div>
                    
					<div class="d-flex justify-content-center mt-3">
						<div class="d-flex align-items-center">
							<span class="input-group-text"><img src="/image/memo/pw.png" width="30"></span>
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
		
		</section>
		</div>
		
		    <!--  
			<footer class="d-flex align-items-center">
				<div class="footer-logo ml-4">
					<img class="foot-logo-image"
						src="https://www.weather.go.kr/w/resources/image/foot_logo.png"
						width="120">
				</div>
				<div class="copyright ml-4">
					<small class="text-secondary"> (07062) 서울시 동작구 여의대방로16길 61
						<br> Copyright@2023 KMA. All Rights RESERVED.
					</small>
				</div>
			</footer>
			-->
		</div>
		
</body>
</html>