<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>

    <link rel="icon" type="image/png" href="images/icon_img/favicon.png">

    <title>BOOKIES</title>
    <style type="text/css">
        .form-control:focus {
            box-shadow: none!important;
        }
        .agree {
            display: flex;
            align-items: center;
            margin-top: 20px;
        }
        #user_agree {
            margin-right: 10px;
        }
        .agree:after {display:block; clear:both; content:"";}
        .agree input[type="checkbox"] {
            display: none;
        }
        .agree input[type="checkbox"] + label {
            width:15px;
            height: 15px;
            background: #fff;
            border: 1px solid #d9d9d9;
            cursor: pointer;
            border-radius: 3px;
            float: left;
            display: flex;
            align-items: center;
        }
        .agree input[type="checkbox"]:checked + label {
            background: #FF7A00 no-repeat center/10px 10px;
            float: left
        }
        .agree input[type="checkbox"] + label span {
            position: absolute;
            padding-left: 30px;
            display: block;
            font-size: 20px;
        }
    </style>
</head>
<body>
    <div class="container">
		<%@ include file="banner.jsp" %>
    <div cass="container">
        <div class="container" style="max-width: 500px; margin-bottom : 100px; border-radius: 5px;padding: 50px 20px;">
            <p class="text-start fs-1 fw-bold" style="display: flex;justify-content: center; mrgin-bottom:0;margin-top:16px;">회원가입</p>

            <form action="/registerProc" method="post" style="display: flex;height: 80%;flex-direction: column; justify-content: space-evenly;">
                <div class="mb-3">
                  <label for="user_id" class="form-label fw-bold fs-4">아이디</label>
                  <input type="text" class="form-control" id="user_id" name="user_id" >
                </div>

                <div class="mb-3">
                  <label for="user_pw" class="form-label fw-bold fs-4">비밀번호</label>

                  <input type="password" class="form-control" id="user_pw" name="user_pw" >
                  <p style="color:#EA002C;">대문자, 소문자, 숫자, 특수문자 포함 8글자 이상</p>
                </div>

                <div class="mb-3">
                  <label for="re_user_pw" class="form-label fw-bold fs-4">비밀번호 확인</label>
                  <input type="password" class="form-control" id="re_user_pw" name="re_user_pw" >
                </div>



                <div class="mb-3">
                  <label for="user_phone" class="form-label fw-bold fs-4">전화번호</label>
                  <input type="text" class="form-control" maxlength="13" id="user_phone" name="user_phone" placeholder="숫자만 입력하세요." >
                </div>

                <div class="mb-3">
                  <label for="user_email" class="form-label fw-bold fs-4">이메일</label>
                  <input type="text" class="form-control" id="user_email" name="user_email" placeholder="someone@example.com" >
                </div>

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
                <input type="hidden" class="form-control" id="user_level" name="user_level" value=1>
                    <div class="mb-3 agree form-label fw-bold fs-4" style="margin-bottom:50px!important">
                        <input type="checkbox" id="user_agree" name="user_agree" value="1"> <label for="user_agree">
                        <span>개인정보 활용 동의</span></label>
                    </div>


                <div class="d-grid gap-2 col-6 mx-auto">

                  <button class="btn btn-primary" type="submit">회원가입</button>
                  <button class="btn btn-outline-primary" type="button" onclick="location.href='/index'">취소</button>
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
                if (session.getAttribute("status") != null){
                    String status = (String) session.getAttribute("status");
            //warn 변수에 값이 있으면, 리셋
            if(status.equals("1") ){
    %>
                <div class="alert alert-danger d-flex align-items-center" role="alert" style="max-width: 600px; margin-top: 30px;">
                    <svg class="bi flex-shrink-0 me-2" width="24" height="24" role="img" aria-label="Danger:"><use xlink:href="#exclamation-triangle-fill"/></svg>
                    <div> 이미 존재하는 아이디입니다. </div>
                </div>
                <%
            }
            else if(status.equals( "2")){
    %>			<div class="alert alert-danger d-flex align-items-center" role="alert" style="max-width: 600px; margin-top: 30px;">
                    <svg class="bi flex-shrink-0 me-2" width="24" height="24" role="img" aria-label="Danger:"><use xlink:href="#exclamation-triangle-fill"/></svg>
                    <div> 모든 정보를 입력해주세요. </div>
                </div>
                <%
            }
            else if (status.equals("3")) {
    %>          <div class="alert alert-danger d-flex align-items-center" role="alert" style="max-width: 600px; margin-top: 30px;">
                    <svg class="bi flex-shrink-0 me-2" width="24" height="24" role="img" aria-label="Danger:"><use xlink:href="#exclamation-triangle-fill"/></svg>
                    <div> 개인정보 활용 동의가 필요합니다. </div>
                </div>

            <%
            }
            else if(status.equals( "4")){
     %>			<div class="alert alert-danger d-flex align-items-center" role="alert" style="max-width: 600px; margin-top: 30px;">
                     <svg class="bi flex-shrink-0 me-2" width="24" height="24" role="img" aria-label="Danger:"><use xlink:href="#exclamation-triangle-fill"/></svg>
                     <div> 전화번호 형태를 확인하여 주십시오. </div>
                 </div>
                 <%
             }
             else if(status.equals( "5")){
           %>			<div class="alert alert-danger d-flex align-items-center" role="alert" style="max-width: 600px; margin-top: 30px;">
                           <svg class="bi flex-shrink-0 me-2" width="24" height="24" role="img" aria-label="Danger:"><use xlink:href="#exclamation-triangle-fill"/></svg>
                           <div> 비밀번호가 일치하지 않습니다. </div>
                       </div>
                       <%
                   }
               else if(status.equals( "8자")){
          %>			<div class="alert alert-danger d-flex align-items-center" role="alert" style="max-width: 600px; margin-top: 30px;">
                          <svg class="bi flex-shrink-0 me-2" width="24" height="24" role="img" aria-label="Danger:"><use xlink:href="#exclamation-triangle-fill"/></svg>
                          <div> 비밀번호는 8자 이상이어야 합니다. </div>
                      </div>
                      <%
                  }
                  else if(status.equals( "대문자")){
        %>			<div class="alert alert-danger d-flex align-items-center" role="alert" style="max-width: 600px; margin-top: 30px;">
                        <svg class="bi flex-shrink-0 me-2" width="24" height="24" role="img" aria-label="Danger:"><use xlink:href="#exclamation-triangle-fill"/></svg>
                        <div> 비밀번호는 대문자를 포함해야 합니다. </div>
                    </div>
                    <%
                }
                else if(status.equals( "소문자")){
          %>			<div class="alert alert-danger d-flex align-items-center" role="alert" style="max-width: 600px; margin-top: 30px;">
                          <svg class="bi flex-shrink-0 me-2" width="24" height="24" role="img" aria-label="Danger:"><use xlink:href="#exclamation-triangle-fill"/></svg>
                          <div> 비밀번호는 소문자를 포함해야 합니다. </div>
                      </div>
                      <%
                  }
                  else if(status.equals( "숫자")){
        %>			<div class="alert alert-danger d-flex align-items-center" role="alert" style="max-width: 600px; margin-top: 30px;">
                        <svg class="bi flex-shrink-0 me-2" width="24" height="24" role="img" aria-label="Danger:"><use xlink:href="#exclamation-triangle-fill"/></svg>
                        <div> 비밀번호는 숫자를 포함해야 합니다. </div>
                    </div>
                    <%
                }
                else if(status.equals( "특수문자")){
      %>			<div class="alert alert-danger d-flex align-items-center" role="alert" style="max-width: 600px; margin-top: 30px;">
                      <svg class="bi flex-shrink-0 me-2" width="24" height="24" role="img" aria-label="Danger:"><use xlink:href="#exclamation-triangle-fill"/></svg>
                      <div> 비밀번호는 특수문자를 포함해야 합니다. </div>
                  </div>
                  <%
              }
           }
        %>
        </div>
        <% session.removeAttribute("status");%>

    </div></div>

</body>
</html>