<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>

	<link rel="icon" type="image/png" href="images/favicon.png">

	<title>BOOKIES</title>
<style type="text/css">
.link-offset-3:hover {
    color: #EA002C!important;
    text-decoration-color: #EA002C!important;
}
</style>
<script>
    var message = "${messageLoginForm}";
    if (message) {
        window.alert(message);
    }
        </script>
</head>
<body>
<div class="container">
		<%@ include file="banner.jsp" %>
<div cass="container">
	<div class="container" style="max-width: 500px;margin-bottom:100px;border-radius: 5px;padding: 50px 20px;">
		<p class="text-start fs-1 fw-bold" style="display: flex;justify-content: center; margin-bottom:0;margin-top:16px">로그인</p>

		<form action="/loginProc" method="post">
			<div class="mb-3">
			  <label for="user_id" class="form-label fw-bold fs-4">아이디</label>
			  <input type="text" class="form-control" id="user_id" aria-describedby="emailHelp" name="user_id">
			</div>

			<div class="mb-3" style="margin-bottom : 50px!important;">
			  <label for="user_pw" class="form-label fw-bold fs-4">비밀번호</label>
			  <input type="text" class="form-control" id="user_pw" aria-describedby="emailHelp" name="user_pw">
			</div>


			<div class="d-grid gap-2 col-6 mx-auto">
			  <button class="btn btn-primary" type="submit">로그인</button>
			  <button class="btn btn-outline-primary" type="button" onclick="location.href='/registerForm'">회원가입</button>
			  <a class="link-offset-3 link-offset-3-hover link-underline link-underline-opacity-0 link-underline-opacity-75-hover" style="text-align:center;color:#FF7A00;" href="/findUseridpw">
                아이디/비밀번호 찾기
              </a>
			</div>
		</form>
		<svg xmlns="http://www.w3.org/2000/svg" class="d-none">
                  <symbol id="check-circle-fill" viewBox="0 0 16 16">
                    <path d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zm-3.97-3.03a.75.75 0 0 0-1.08.022L7.477 9.417 5.384 7.323a.75.75 0 0 0-1.06 1.06L6.97 11.03a.75.75 0 0 0 1.079-.02l3.992-4.99a.75.75 0 0 0-.01-1.05z"/>
                  </symbol>
                  <symbol id="info-fill" viewBox="0 0 16 16">
                    <path d="M8 16A8 8 0 1 0 8 0a8 8 0 0 0 0 16zm.93-9.412-1 4.705c-.07.34.029.533.304.533.194 0 .487-.07.686-.246l-.088.416c-.287.346-.92.598-1.465.598-.703 0-1.002-.422-.808-1.319l.738-3.468c.064-.293.006-.399-.287-.47l-.451-.081.082-.381 2.29-.287zM8 5.5a1 1 0 1 1 0-2 1 1 0 0 1 0 2z"/>
                  </symbol>
                  <symbol id="exclamation-triangle-fill" viewBox="0 0 16 16">
                    <path d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"/>
                  </symbol>
                </svg>
		 <%
                 // JSP Scriptlet: EL 표현식 값을 Java 변수에 할당
                 String warn = (String) request.getAttribute("warn");
                 if(warn != null) {
         %>
            <div class="alert alert-danger d-flex align-it ems-center" role="alert" style="max-width: 600px; margin-top: 30px;">
                <svg class="bi flex-shrink-0 me-2" width="24" height="24" role="img" aria-label="Danger:"><use xlink:href="#exclamation-triangle-fill"/></svg>
            <div> 아이디 또는 비밀번호를 잘못 입력하셨습니다. </div>
            			</div>


            			<%
            		}
            	%>


</div></div>

</body>
</html>