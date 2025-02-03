<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    text-decoration-color: #EA002C!important;
}
</style>
</head>
<body>
    <div class="modal fade" id="errorModal" tabindex="-1" aria-labelledby="errorModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="errorModalLabel">오류</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body" id="errorMessage"></div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
                </div>
            </div>
        </div>
    </div>

    <c:if test="${not empty sessionScope.errorMessage}">
        <script type="text/javascript">
            // 모달에 메시지 설정
            document.getElementById("errorMessage").innerText = "${sessionScope.errorMessage}";

            // Bootstrap 5에서 모달 띄우기
            var myModal = new bootstrap.Modal(document.getElementById('errorModal'));
            myModal.show(); // 모달을 띄운다.

            // 세션에서 메시지 제거
            <c:remove var="errorMessage" scope="session" />
        </script>
    </c:if>

<div class="container">
<%@ include file="banner.jsp" %>
	<%
		String user_pw = (String) request.getAttribute("user_pw");
		String user_phone = (String) request.getAttribute("user_phone");
		String user_email = (String) request.getAttribute("user_email");
		String myInfoModifyForm = (String) request.getAttribute("myInfoModifyForm");
	%>

<div class="container">
	<div class="container" style="max-width: 1200px;margin-bottom:100px;border-radius: 5px;padding: 50px 20px;">
		<p class="text-start fs-1 fw-bold" style="display: flex;justify-content: center; margin-bottom:30px;margin-top:16px">내 정보</p>

		<form action="myInfoSave" method="post">
	<table border="1"  style="width: 100%">
		<tr height="60" align="center">
			<td width="40%" style="border-right:1px solid #212529"> 아이디 </td>
			<td width="100%">
                    <%= user_id %>
			</td>
		</tr>
		<tr height="60" align="center">
			<td width="40%" style="border-right:1px solid #212529"> 비밀번호 </td>
			<td width="100%" id="user_pw">
			    <%if (myInfoModifyForm == "0") {%>
                    <%= user_pw %>
                <%}else if (myInfoModifyForm == "1"){%>
                    <input type="text" class="form-control" style="width:50%;" name="user_pw" id="user_pw" value="<%= user_pw %>" required>
                <%}%>
			</td>
		</tr>
		<tr height="60" align="center">
			<td width="40%" style="border-right:1px solid #212529"> 전화번호 </td>
			<td width="100%"> 
				<%if (myInfoModifyForm == "0") {%>
                    <%= user_phone %>
                <%}else if (myInfoModifyForm == "1"){%>
                    <input type="text" class="form-control" style="width:50%;" name="user_phone" maxlength="13"  id="user_phone" value="<%= user_phone %>" required>
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


                                        let usertel = document.getElementById('user_phone');

                                        usertel.onkeyup = function(){
                                          console.log(this.value);
                                          this.value = autoTel( this.value ) ;
                                        }
                                    </script>
                <%}%>
			</td>
		</tr>
		<tr height="60" align="center">
            <td width="40%" style="border-right:1px solid #212529"> 이메일 </td>
            <td width="100%">
                <%if (myInfoModifyForm == "0") {%>
                    <%= user_email %>
                <%}else if (myInfoModifyForm == "1"){%>
                    <input type="text" class="form-control" style="width:50%;" name="user_email" id="user_email" value="<%= user_email %>" required>
                <%}%>
            </td>
        </tr>
		<tr height="60" align="center">
			<td width="40%" style="border-right:1px solid #212529"> 개인정보 활용 동의 </td>
			<td width="100%">동의함</td>
		</tr>
		</table>
		<div class="d-grid gap-2 col-6 mx-auto" style="margin-top:30px">
		    <%if (myInfoModifyForm == "0") {%>
                <button class="btn btn-primary" type="button" onclick="location.href='/myInfoModify'">수정하기</button>
            <%}else if (myInfoModifyForm == "1"){%>
                <button class="btn btn-primary" type="submit">저장하기</button>
            <%}%>
			  <button class="btn btn-outline-primary" type="button" onclick="location.href='/index'">홈으로</button>
			  <a data-bs-toggle="modal" data-bs-target="#exampleModal" class="link-offset-3 link-offset-3-hover link-underline link-underline-opacity-0 link-underline-opacity-75-hover" style="cursor:pointer;text-align:center;color:#EA002C;font-size:0.7rem">
                  회원 탈퇴
                </a>
			</div>
	</form>
    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered">
          <div class="modal-content">
              <div class="modal-header">
                  <h1 class="modal-title fs-5" id="exampleModalLabel">정말 탈퇴 하시겠습니까?</h1>
                  <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="취소"></button>
              </div>
              <form action="/delUser" method="POST"> <!-- POST로 전송 -->
                  <div class="modal-body">


                          <div class="mb-3">
                              <label for="password" class="col-form-label">비밀번호</label>
                              <input type="password" class="form-control" id="password" name="password" required> <!-- 비밀번호 입력 -->
                          </div>
                      <p style="color:#EA002C;text-align:center;font-size:0.7rem;margin:0"> 탈퇴를 진행하는 경우 되돌릴 수 없습니다. </p>
                  </div>
                  <div class="modal-footer">
                      <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" onclick="location.href='/myInfo'">취소</button>
                      <button type="submit" class="btn btn-primary">탈퇴하기</button> <!-- 전송 버튼 -->
                  </div>
              </form>
          </div>
      </div>
  </div>

</div>
</body>
</html>