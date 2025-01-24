<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <title>회원가입</title>
    <style type="text/css">
        .form-control:focus {
            box-shadow: none!important;
        }
        .agree {
            display: flex;
            align-items: center;
            margin-top: 20px;
        }
        #useragree {
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
            background: #0d6efd no-repeat center/10px 10px;
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
                  <input type="text" class="form-control" id="user_id" name="user_id" required>
                </div>

                <div class="mb-3">
                  <label for="user_pw" class="form-label fw-bold fs-4">비밀번호</label>
                  <input type="text" class="form-control" id="user_pw" name="user_pw" required>
                </div>

                <div class="mb-3">
                  <label for="user_email" class="form-label fw-bold fs-4">이메일</label>
                  <input type="text" class="form-control" id="user_email" name="user_email" placeholder="someone@example.com">
                </div>

                <div class="mb-3">
                  <label for="user_phone" class="form-label fw-bold fs-4">전화번호</label>
                  <input type="text" class="form-control" maxlength="13" id="user_phone" name="user_phone" placeholder="숫자만 입력하세요.">
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
            <%
            String warn = request.getParameter("warn");
            String none = request.getParameter("none");
            String agree = request.getParameter("agree");
            //warn 변수에 값이 있으면, 리셋
            if(warn != null) {
    %>
                <div class="alert alert-danger d-flex align-items-center" role="alert" style="max-width: 600px; margin-top: 30px;">
                    <svg class="bi flex-shrink-0 me-2" width="24" height="24" role="img" aria-label="Danger:"><use xlink:href="#exclamation-triangle-fill"/></svg>
                    <div> 이미 존재하는 아이디입니다. </div>
                </div>
                <%
            }
            if(none != null){
    %>			<div class="alert alert-danger d-flex align-items-center" role="alert" style="max-width: 600px; margin-top: 30px;">
                    <svg class="bi flex-shrink-0 me-2" width="24" height="24" role="img" aria-label="Danger:"><use xlink:href="#exclamation-triangle-fill"/></svg>
                    <div> 아이디와 비밀번호를 설정해주세요. </div>
                </div>
                <%
            }
            if (agree != null) {
    %>          <div class="alert alert-danger d-flex align-items-center" role="alert" style="max-width: 600px; margin-top: 30px;">
                    <svg class="bi flex-shrink-0 me-2" width="24" height="24" role="img" aria-label="Danger:"><use xlink:href="#exclamation-triangle-fill"/></svg>
                    <div> 개인정보 활용 동의가 필요합니다. </div>
                </div>

            <%
            }
        %>
        </div>

    </div></div>

</body>
</html>