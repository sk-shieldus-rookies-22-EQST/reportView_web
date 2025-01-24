<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">

		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
		<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>


		<style type="text/css">
			.btn-outline-light:hover {
				color: #0d6efd !important;
			}

			/* point-color : #E3F321*/
		</style>
		<title>Insert title here</title>
	</head>

	<body>

		<% request.setCharacterEncoding("utf-8"); %>

		<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
			<div class="container-fluid">

				<a class="navbar-brand" href="/index">
					<img src="" alt="" width="35" class="d-inline-block align-text-top">
					<a class="navbar-brand" href="/index">
						<img src="" alt="" width="35" class="d-inline-block align-text-top">
						Bookies
					</a>
				</a>

					<button class="navbar-toggler" type="button" data-bs-toggle="collapse"
							data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
							aria-expanded="false" aria-label="Toggle navigation">
						<span class="navbar-toggler-icon"></span>
					</button>
                    <%
                             // JSP Scriptlet: EL 표현식 값을 Java 변수에 할당
                             String user_id = (String) session.getAttribute("user_id");
                             //System.out.println(user_id);
                             if(user_id == null) {
                     %>
					<div class=" collapse navbar-collapse" id="navbarSupportedContent">
						<ul class="navbar-nav me-auto mb-2 mb-lg-0">
							<li class="nav-item">
								<a class="nav-link active" type="hidden" aria-current="page" href="/loginForm">QnA</a>
							</li>
						</ul>
						<input class="btn btn-outline-light" type="button" value="로그인"
								onclick="location.href='/loginForm'" style="margin-left: 20px;">
						<input class="btn btn-outline-light" type="button" value="회원가입"
								onclick="location.href='/registerForm'" style="margin-left: 20px;">

							<% } else {
							    Integer point = (Integer) session.getAttribute("point");
							%>
						<div class="collapse navbar-collapse" id="navbarSupportedContent">
							<ul class="navbar-nav me-auto mb-2 mb-lg-0">
								<li class="nav-item">
									<a class="nav-link active" aria-current="page" href="/qnaList">QnA</a>
								</li>
							</ul>
							<ul class="nav justify-content-end">
							    <li style="color:white;display: flex;align-items: center;margin-right:20px">
                                            <%=point %> pt
                                </li>

								<li style="color:white;display: flex;align-items: center;margin-right:20px">
											<%=user_id %> 님
								</li>

								<li class="nav-item dropdown">
									<div class="btn-group">
										<button type="button" class="btn btn-outline-light dropdown-toggle"
											data-bs-toggle="dropdown" aria-expanded="false">
													마이페이지
										</button>
										<ul class="dropdown-menu">
											<li><a class="dropdown-item" href="/myInfo">회원정보</a></li>
											<li>
											    <a class="dropdown-item" href="#" onclick="document.getElementById('myCart').submit();">장바구니</a>
                                                <form id="myCart" action="/eBookCart" method="POST" style="display: none;"/>
											</li>
											<li><a class="dropdown-item" href="/myPurchase">결제 내역</a></li>
											<li><a class="dropdown-item" href="/myBook">내 서재</a></li>
										</ul>
									</div>
								</li>
							</ul>
							<input class="btn btn-outline-light" type="button" value="로그아웃"
										onclick="location.href='/logout'" style="margin-left: 20px;">
						</div>

								<% } %>
					</div>
				</div>
			</nav>

	</body>

</html>