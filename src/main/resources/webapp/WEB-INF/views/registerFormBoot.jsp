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
		<%@ include file="nav.jsp" %>
<div cass="container">
	<div class="container" style="max-width: 700px; margin-bottom : 100px; border-radius: 5px;padding: 50px 20px;">
		<p class="text-start fs-1 fw-bold" style="display: flex;justify-content: center; mrgin-bottom:0;margin-top:16px;">회원가입</p>
		
		<form action="registerProcBoot.jsp" method="post" style="display: flex;height: 80%;flex-direction: column; justify-content: space-evenly;">
			<div class="mb-3">
			  <label for="userid" class="form-label fw-bold fs-4">아이디</label>
			  <input type="text" class="form-control" id="userid" name="userid" required>
			</div>
			
			<div class="mb-3">
			  <label for="userpw" class="form-label fw-bold fs-4">비밀번호</label>
			  <input type="text" class="form-control" id="userpw" name="userpw" required>
			</div>
			
			<div class="mb-3">
			  <label for="username" class="form-label fw-bold fs-4">이름</label>
			  <input type="text" class="form-control" id="username" name="username" required>
			</div>
			
			<div class="mb-3">
			  <label for="usercountry" class="form-label fw-bold fs-4">국가</label>
			  <input type="text" class="form-control" id="usercountry" name="usercountry" style="text-transform: uppercase; " placeholder="KOREA">
			</div>
			
			<div class="mb-3">
			  <label for="userbirth" class="form-label fw-bold fs-4">생년월일</label>
			  <input type="text" class="form-control" id="userbirth" name="userbirth" placeholder="YYYY-MM-DD" maxlength="10">
			</div>
			
			<script type="text/javascript">
				let autoBirth = function(str){
				      str = str.replace(/[^0-9]/g, '');
				      let tmp = '';
				      if( str.length < 5){
				          return str;
				      }else if(str.length < 7){
				          tmp += str.substr(0, 4);
				          tmp += '-';
				          tmp += str.substr(4);
				          return tmp;
				      }else{              
				          tmp += str.substr(0, 4);
				          tmp += '-';
				          tmp += str.substr(4, 2);
				          tmp += '-';
				          tmp += str.substr(6);
				          return tmp;
				      }
				  
				      return str;
				}
	
	
				let userbirth = document.getElementById('userbirth');
	
				userbirth.onkeyup = function(){
				  console.log(this.value);
				  this.value = autoBirth( this.value ) ;  
				}
			</script>
			
			<div class="mb-3">
			  <label for="useremail" class="form-label fw-bold fs-4">이메일</label>
			  <input type="text" class="form-control" id="useremail" name="useremail" placeholder="someone@example.com">
			</div>
			
			<div class="mb-3">
			  <label for="usertel" class="form-label fw-bold fs-4">전화번호</label>
			  <input type="text" class="form-control" maxlength="13" id="usertel" name="usertel" placeholder="숫자만 입력하세요.">
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
	
	
				let usertel = document.getElementById('usertel');
	
				usertel.onkeyup = function(){
				  console.log(this.value);
				  this.value = autoTel( this.value ) ;  
				}
			</script>
			
				<div class="mb-3 agree form-label fw-bold fs-4" style="margin-bottom:50px!important">
                        <input type="checkbox" id="useragree" name="useragree" value="1"> <label for="useragree">
                        <span>개인정보 활용 동의</span></label>
                    </div>
			
			
		  	<div class="d-grid gap-2 col-6 mx-auto">
		  	
			  <button class="btn btn-primary" type="submit">회원가입</button>
			  <button class="btn btn-outline-primary" type="button" onclick="location.href='./main.jsp'"">취소</button>
			</div>
		</form>
		<%
		String warn = request.getParameter("warn");
		String none = request.getParameter("none");
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
	%>
	</div>
	
</div></div>

</body>
</html>