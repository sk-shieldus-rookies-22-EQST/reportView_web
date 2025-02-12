<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>

	<link rel="icon" type="image/png" href="images/icon_img/favicon.png">

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

		<form id="myInfoSave" action="myInfoSave" method="post">
        <table border="1"  style="width: 100%">
            <tr height="60" align="center">
                <td width="40%" style="border-right:1px solid #212529"> 아이디 </td>
                <td width="100%">
                        <%= user_id %>
                </td>
            </tr>
            <%if (myInfoModifyForm == "0") {%>
            <%}else if (myInfoModifyForm == "1"){%>
            <tr height="60" align="center">
                <td width="40%" style="border-right:1px solid #212529"> 새로운 비밀번호 </td>
                <td width="100%">
                    <input type="password" class="form-control" style="width:50%;" name="user_pw" id="user_pw" value="" required>
                    <p style="color:#EA002C;">대문자, 소문자, 숫자, 특수문자 포함 8글자 이상</p>
                </td>
            </tr>
            <tr height="60" align="center">
                <td width="40%" style="border-right:1px solid #212529"> 새로운 비밀번호 확인 </td>
                <td width="100%">
                <input type="password" class="form-control" style="width:50%;" name="re_user_pw" id="re_user_pw" value="" required>
                </td>
            </tr>
            <%}%>
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
            <input type="hidden" class="form-control" id="user_encrypted" name="user_encrypted" >

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
                        if (session.getAttribute("status") != null){
                            String status = (String) session.getAttribute("status");
                    //warn 변수에 값이 있으면, 리셋
                    if(status.equals("1") ){
            %>
                        <div class="d-grid gap-2 col-6 mx-auto alert alert-danger d-flex align-items-center" role="alert" style="max-width: 600px; margin-top: 30px;">
                            <svg class="bi flex-shrink-0 me-2" width="24" height="24" role="img" aria-label="Danger:"><use xlink:href="#exclamation-triangle-fill"/></svg>
                            <div> 비밀번호가 일치하지 않습니다. </div>
                        </div>
                        <%
                    }
                    else if(status.equals("2")){
            %>
                        <div class="d-grid gap-2 col-6 mx-auto alert alert-danger d-flex align-items-center" role="alert" style="max-width: 600px; margin-top: 30px;">
                            <svg class="bi flex-shrink-0 me-2" width="24" height="24" role="img" aria-label="Danger:"><use xlink:href="#exclamation-triangle-fill"/></svg>
                            <div> 전화번호 형태를 확인해주세요. </div>
                        </div>
                        <%
                    }
                    else if(status.equals( "8자")){
            %>
                        <div class="d-grid gap-2 col-6 mx-auto alert alert-danger d-flex align-items-center" role="alert" style="max-width: 600px; margin-top: 30px;">
                            <svg class="bi flex-shrink-0 me-2" width="24" height="24" role="img" aria-label="Danger:"><use xlink:href="#exclamation-triangle-fill"/></svg>
                            <div> 비밀번호는 8자 이상이어야 합니다. </div>
                        </div>
                        <%
                    } else if(status.equals("대문자")) {
             %>
                       <div class="alert alert-danger d-flex align-items-center" role="alert" style="max-width: 600px; margin-top: 30px;">
                           <svg class="bi flex-shrink-0 me-2" width="24" height="24" role="img" aria-label="Danger:"><use xlink:href="#exclamation-triangle-fill"/></svg>
                           <div> 비밀번호는 대문자를 포함해야 합니다. </div>
                       </div>
                       <%
                   } else if(status.equals("소문자")) {
            %>
                      <div class="alert alert-danger d-flex align-items-center" role="alert" style="max-width: 600px; margin-top: 30px;">
                          <svg class="bi flex-shrink-0 me-2" width="24" height="24" role="img" aria-label="Danger:"><use xlink:href="#exclamation-triangle-fill"/></svg>
                          <div> 비밀번호는 소문자를 포함해야 합니다. </div>
                      </div>
                      <%
                  } else if(status.equals("숫자")) {
           %>
                     <div class="alert alert-danger d-flex align-items-center" role="alert" style="max-width: 600px; margin-top: 30px;">
                         <svg class="bi flex-shrink-0 me-2" width="24" height="24" role="img" aria-label="Danger:"><use xlink:href="#exclamation-triangle-fill"/></svg>
                         <div> 비밀번호는 숫자를 포함해야 합니다. </div>
                     </div>
                     <%
                 } else if(status.equals("특수문자")) {
          %>
                    <div class="alert alert-danger d-flex align-items-center" role="alert" style="max-width: 600px; margin-top: 30px;">
                        <svg class="bi flex-shrink-0 me-2" width="24" height="24" role="img" aria-label="Danger:"><use xlink:href="#exclamation-triangle-fill"/></svg>
                        <div> 비밀번호는 특수문자를 포함해야 합니다. </div>
                    </div>
                    <%
                }
            }%>
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

        <script src="/js/encrypt.js"></script>
        <script>
            async function getKeyAndEncrypt() {
                const response = await fetch("/security/getKey");
                const { aesKey, aesIv } = await response.json(); // aesKey와 aesIv를 객체로 받아옴

                // 데이터 객체 생성
                const myInfoData = {
                    user_pw: document.getElementById('user_pw').value,
                    re_user_pw: document.getElementById('re_user_pw').value,
                    user_phone: document.getElementById('user_phone').value,
                    user_email: document.getElementById('user_email').value
                };

                // 객체를 &&&& 구분자로 결합
                const myInfoDataString = Object.values(myInfoData).join("&&&&");

                // AES로 아이디와 비밀번호를 결합하여 암호화
                const encryptedData = await encryptAES(aesKey, aesIv, myInfoDataString);

                console.log("Encrypted Data: ", encryptedData);

                // 암호화된 값을 폼에 설정
                document.getElementById('myInfoSave').reset();

                document.getElementById('user_encrypted').value = encryptedData;

                document.getElementById('myInfoSave').submit();
            }
            document.getElementById('myInfoSave').addEventListener('submit', async function (event) {
                event.preventDefault(); // 기본 제출 동작 방지
                await getKeyAndEncrypt(); // 암호화 후 폼 제출
            });

        </script>


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
                              <label for="del_password" class="col-form-label">비밀번호</label>
                              <input type="password" class="form-control" id="del_password" name="del_password" required> <!-- 비밀번호 입력 -->
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
<% session.removeAttribute("status");%>
</body>
</html>