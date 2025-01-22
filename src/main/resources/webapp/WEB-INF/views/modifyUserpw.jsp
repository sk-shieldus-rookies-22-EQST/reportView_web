<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
<title>아이디 찾기</title>
<style type="text/css">
</style>
</head>
<body>
<div class="container">
		<%@ include file="banner.jsp" %>
<div cass="container">
	<div class="container" style="max-width: 500px;margin-bottom:100px;border-radius: 5px;padding: 50px 20px;">
		<p class="text-start fs-1 fw-bold" style="display: flex;justify-content: center; margin-bottom:0;margin-top:16px">비밀번호 변경</p>

		<form action="findUserProc.jsp" method="post">
		<input type="hidden" name="whatFind" id="whatFind" value='pw'>
			<div class="mb-3" style="margin-bottom : 50px!important;">
			  <label for="user_phone" class="form-label fw-bold fs-4">새로운 비밀번호</label>
			  <input type="text" class="form-control" id="user_phone" aria-describedby="emailHelp" name="user_phone" required>
			</div>

			<div class="mb-3" style="margin-bottom : 50px!important;">
              <label for="user_phone_re" class="form-label fw-bold fs-4">새로운 비밀번호 확인</label>
              <input type="text" class="form-control" id="user_phone_re" aria-describedby="emailHelp" name="user_phone_re" required>
            </div>

			<div class="d-grid gap-2 col-6 mx-auto">
			  <button class="btn btn-primary" type="submit">비밀번호 변경</button>
			</div>
		</form>
		<%
		String warn = request.getParameter("warn");

		//warn 변수에 값이 있으면, 리셋
		if(warn != null) {
%>
			<div class="alert alert-danger d-flex align-items-center" role="alert" style="max-width: 600px; margin-top: 30px;">
				<svg class="bi flex-shrink-0 me-2" width="24" height="24" role="img" aria-label="Danger:"><use xlink:href="#exclamation-triangle-fill"/></svg>
				<div> 존재하지 않는 회원입니다. </div>
			</div>
			<%
		}
	%>
	</div>

</div></div>

</body>
</html>