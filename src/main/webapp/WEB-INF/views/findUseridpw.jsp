<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
<title>아이디 찾기</title>
<style type="text/css">
</style>
</head>
<body>
<div class="container">
		<%@ include file="banner.jsp" %>
<div cass="container" style="display: flex;">
	<div class="container" style="max-width: 500px;margin-bottom:100px;border-radius: 5px;padding: 50px 20px;">
		<p class="text-start fs-1 fw-bold" style="display: flex;justify-content: center; margin-bottom:0;margin-top:16px">아이디 찾기</p>

		<form action="/findUserProc" method="post">
            <input type="hidden" name="whatFind" id="findId" value=1>
			<div class="mb-3" style="margin-bottom : 50px!important;">
			  <label for="user_phone" class="form-label fw-bold fs-4">전화번호</label>
			  <input type="text" class="form-control" maxlength="13" id="user_phone" name="user_phone" placeholder="숫자만 입력하세요." required>
			  <script type="text/javascript">
                                  let autoTel = function(str){
                                        str = str.replace(/[^0-9]/g, '');
                                        let tmp = '';
                                        if( str.length < 4){
                                            return str;
                                        }else if(str.length < 7){
                                            tmp += str.substr(0, 3);
                                            tmp += '-';
                                            tmp += str.substr(3);
                                            return tmp;
                                        }else if(str.length < 11){
                                            tmp += str.substr(0, 3);
                                            tmp += '-';
                                            tmp += str.substr(3, 3);
                                            tmp += '-';
                                            tmp += str.substr(6);
                                            return tmp;
                                        }else{
                                            tmp += str.substr(0, 3);
                                            tmp += '-';
                                            tmp += str.substr(3, 4);
                                            tmp += '-';
                                            tmp += str.substr(7);
                                            return tmp;
                                        }
              
                                        return str;
                                  }
              
              
                                  let user_phone = document.getElementById('user_phone');
              
                                  user_phone.onkeyup = function(){
                                    console.log(this.value);
                                    this.value = autoTel( this.value ) ;
                                  }
                              </script>
			</div>

			<div class="mb-3" style="margin-bottom : 50px!important;">
              <label for="user_email" class="form-label fw-bold fs-4">email</label>
              <input type="text" class="form-control" id="user_email" aria-describedby="emailHelp" name="user_email" placeholder="someone@example.com" required>
            </div>

			<div class="d-grid gap-2 col-6 mx-auto">
			  <button class="btn btn-primary" type="submit">아이디 찾기</button>
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
		String warnid = request.getParameter("warnid");
		String errorid = request.getParameter("errorid");
		String warnpw = request.getParameter("warnpw");
		String errorpw = request.getParameter("errorpw");
		String foundId = request.getParameter("foundId");
		String success = request.getParameter("success");

		//warn 변수에 값이 있으면, 리셋
		if(warnid != null) {
%>
			<div class="alert alert-danger d-flex align-items-center" role="alert" style="max-width: 600px; margin-top: 30px;">
				<svg class="bi flex-shrink-0 me-2" width="24" height="24" role="img" aria-label="Danger:"><use xlink:href="#exclamation-triangle-fill"/></svg>
				<div> 존재하지 않는 회원입니다. </div>
			</div>
			<%
		}
		if(errorid != null) {
%>
            <div class="alert alert-danger d-flex align-items-center" role="alert" style="max-width: 600px; margin-top: 30px;">
                <svg class="bi flex-shrink-0 me-2" width="24" height="24" role="img" aria-label="Danger:"><use xlink:href="#exclamation-triangle-fill"/></svg>
                <div> 알 수 없는 오류가 발생했습니다. </div>
            </div>
            <%
        }
		if (foundId != null) {
%>          <div class="alert alert-success  d-flex align-items-center" role="alert" style="max-width: 600px; margin-top: 30px;">
             <svg class="bi flex-shrink-0 me-2" width="24" height="24" role="img" aria-label="Success:"><use xlink:href="#check-circle-fill"/></svg>
             <div> 아이디는 <%=foundId%>입니다. </div>
         </div>

    <%
    }
    %>
	</div>
	<div class="container" style="max-width: 500px;margin-bottom:100px;border-radius: 5px;padding: 50px 20px;">
	    <p class="text-start fs-1 fw-bold" style="display: flex;justify-content: center; margin-bottom:0;margin-top:16px">비밀번호 찾기</p>
	    <form action="findUserProc" method="post">
            <input type="hidden" name="whatFind" id="findPw" value=2>
            <div class="mb-3" style="margin-bottom : 50px!important;">
                          <label for="user_id" class="form-label fw-bold fs-4">아이디</label>
                          <input type="text" class="form-control" id="user_id" aria-describedby="emailHelp" name="user_id" required>
                        </div>
            <div class="mb-3" style="margin-bottom : 50px!important;">
              <label for="user_phone_pw" class="form-label fw-bold fs-4">전화번호</label>
              <input type="text" class="form-control" maxlength="13" id="user_phone_pw" name="user_phone_pw" placeholder="숫자만 입력하세요." required>
              <script type="text/javascript">
                let autoTelPw = function(str){
                      str = str.replace(/[^0-9]/g, '');
                      let tmp = '';
                      if( str.length < 4){
                          return str;
                      }else if(str.length < 7){
                          tmp += str.substr(0, 3);
                          tmp += '-';
                          tmp += str.substr(3);
                          return tmp;
                      }else if(str.length < 11){
                          tmp += str.substr(0, 3);
                          tmp += '-';
                          tmp += str.substr(3, 3);
                          tmp += '-';
                          tmp += str.substr(6);
                          return tmp;
                      }else{
                          tmp += str.substr(0, 3);
                          tmp += '-';
                          tmp += str.substr(3, 4);
                          tmp += '-';
                          tmp += str.substr(7);
                          return tmp;
                      }

                      return str;
                }


                let user_phone_pw = document.getElementById('user_phone_pw');

                user_phone_pw.onkeyup = function(){
                  this.value = autoTelPw( this.value ) ;
                }
            </script>
            </div>

            <div class="mb-3" style="margin-bottom : 50px!important;">
              <label for="user_email" class="form-label fw-bold fs-4">email</label>
              <input type="text" class="form-control" id="user_email" aria-describedby="emailHelp" name="user_email" placeholder="someone@example.com" required>
            </div>

            <div class="d-grid gap-2 col-6 mx-auto">
              <button class="btn btn-primary" type="submit">비밀번호 찾기</button>
            </div>
        </form>
        <% if(warnpw != null) {
%>

            <div class="alert alert-danger d-flex align-items-center" role="alert" style="max-width: 600px; margin-top: 30px;">
                <svg class="bi flex-shrink-0 me-2" width="24" height="24" role="img" aria-label="Danger:"><use xlink:href="#exclamation-triangle-fill"/></svg>
                <div> 존재하지 않는 회원입니다. </div>
            </div>
            <%
        }
        if(errorpw != null) {
%>
            <div class="alert alert-danger d-flex align-items-center" role="alert" style="max-width: 600px; margin-top: 30px;">
                <svg class="bi flex-shrink-0 me-2" width="24" height="24" role="img" aria-label="Danger:"><use xlink:href="#exclamation-triangle-fill"/></svg>
                <div> 알 수 없는 오류가 발생했습니다. </div>
            </div>
            <%
        }
        if (success != null) {
%>          <div class="alert alert-success  d-flex align-items-center" role="alert" style="max-width: 600px; margin-top: 30px;">
             <svg class="bi flex-shrink-0 me-2" width="24" height="24" role="img" aria-label="Success:"><use xlink:href="#check-circle-fill"/></svg>
             <div> 비밀번호가 변경되었습니다. </div>
         </div>

<%
        }
%>
    </div>

</div>
</div>

</body>
</html>