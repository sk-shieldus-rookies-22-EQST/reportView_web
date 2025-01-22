<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>

<style type="text/css">
.btn-outline-light:hover{
    color: #0d6efd !important ;
}
/* point-color : #E3F321*/
</style>
<title>Insert title here</title>
</head>
<body>

<%
	request.setCharacterEncoding("utf-8");
	String logout = request.getParameter("logout");

	//logout 변수에 값이 있으면, 리셋
	if(logout != null) {
		session.setAttribute("user_id", null);
		Cookie[] ck = request.getCookies();
		try{
			for(int i=0; i< ck.length; i++){
				ck[i].setMaxAge(0);
				response.addCookie(ck[i]);
			}
		}catch (Exception e) {
			out.print("쿠키가 사라지지 않았습니다");
		}
	}

	String user_id = (String)session.getAttribute("user_id");
	if(user_id == null) {
		user_id = " ";
	}
%>

<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
  <div class="container-fluid">

    <a class="navbar-brand" href="./index.jsp">
      <img src="" alt="" width="35" class="d-inline-block align-text-top">
      시스템 이름
    </a>

    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <a class="nav-link active" aria-current="page" href="./index.jsp">Home</a>
        </li>
      </ul>
      <%
      	if(user_id.equals(" ")){
	%>
			<input class="btn btn-outline-light" type="button" value="로그인" onclick="location.href='loginForm.jsp'" style="margin-left: 20px;">
			<input class="btn btn-outline-light" type="button" value="회원가입" onclick="location.href='registerForm.jsp'" style="margin-left: 20px;">
	<%
      	} else {
	%>
      		<div style="color:white;display: flex;">
                <div class="dropdown">
                  <button class="btn btn-outline-light dropdown-toggle" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                    <%=user_id %> 님
                  </button>


                  <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                      마이페이지
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                      <li><a class="dropdown-item" href="myInfo.jsp"></a>회원정보</li>
                      <li><a class="dropdown-item" href="myPurchase.jsp">결제 내역</a></li>
                      <li><a class="dropdown-item" href="myBook.jsp">내 서재</a></li>
                    </ul>
                  </li>
                </div>
      			<input class="btn btn-outline-light" type="button" value="로그아웃" onclick="location.href='index.jsp?logout=1'" style="margin-left: 20px;">
      		</div>

	<%
      	}
      %>
    </div>
  </div>
</nav>

</body>
</html>