<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
<title>Insert title here</title>
<style type="text/css">
</style>
</head>
<body>
<div class="container">
		<%@ include file="banner.jsp" %>
<div cass="container">
	<div class="container" style="max-width: 500px;margin-bottom:100px;border-radius: 5px;padding: 50px 20px;">
		<p class="text-start fs-1 fw-bold" style="display: flex;justify-content: center; margin-bottom:0;margin-top:16px">로그인</p>

		<form action="/loginProc" method="post">
			<div class="mb-3">
			  <label for="user_id" class="form-label fw-bold fs-4">ID</label>
			  <input type="text" class="form-control" id="user_id" aria-describedby="emailHelp" name="user_id">
			</div>

			<div class="mb-3" style="margin-bottom : 50px!important;">
			  <label for="user_pw" class="form-label fw-bold fs-4">Password</label>
			  <input type="text" class="form-control" id="user_pw" aria-describedby="emailHelp" name="user_pw">
			</div>


			<div class="d-grid gap-2 col-6 mx-auto">
			  <button class="btn btn-primary" type="submit">로그인</button>
			  <button class="btn btn-outline-primary" type="button" onclick="location.href='/registerForm'">회원가입</button>
			</div>
		</form>
		<%
		String warn = request.getParameter("warn");

		//warn 변수에 값이 있으면, 리셋
		if(warn != null) {
%>
			<div class="alert alert-danger d-flex align-items-center" role="alert" style="max-width: 600px; margin-top: 30px;">
				<svg class="bi flex-shrink-0 me-2" width="24" height="24" role="img" aria-label="Danger:"><use xlink:href="#exclamation-triangle-fill"/></svg>
				<div> 아이디 또는 비밀번호를 잘못 입력하셨습니다. </div>
			</div>
			<%
		}
	%>
	</div>

</div></div>

</body>
</html>