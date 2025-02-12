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
				color: #FF7A00 !important;
			}
            .bg-primary{
                background-color: #FF7A00!important; /* 원하는 색으로 변경 */
            }
            .btn-outline-primary {
                border-color: #FF7A00!important;
                color: #FF7A00!important;
            }
            .btn-outline-primary:hover {
                background-color: #EA002C!important;
                border-color: #EA002C!important;
                color: white!important;
            }
            .dropdown-item:onclick{
                background-color: #EA002C!important;
            }


            .btn-primary {
                    background-color: #FF7A00!important; /* 원하는 색으로 변경 */
                    border-color: #FF7A00!important; /* 버튼 테두리 색상 변경 */
                }

            /* 버튼 호버 시 색상 변경 */
            .btn-primary:hover {
                background-color: #EA002C!important; /* 호버 시 색상 */
                border-color: #EA002C!important;
            }

            /* pagination 기본 색상 변경 */
             .pagination {
                 background-color: #ffffff; /* 배경색 변경 */
                 border-radius: 5px;
                 box-shadow:none;
             }

             .pagination .page-link {
                 color: #FF7A00; /* 페이지 링크 색상 */
             }

             .pagination .page-link:hover {
                 background-color: #f8f9fa; /* hover 시 배경색 */
                 color: #FF7A00; /* hover 시 글자 색상 */
                 box-shadow:none;
             }

             .pagination .page-item.active .page-link {
                 background-color: #FF7A00; /* 활성화된 페이지의 배경색 */
                 border-color: #FF7A00; /* 활성화된 페이지의 테두리 색 */
                 color : white!important;
             }

            input:focus {
                border-color: #ff7a00!important;
                box-shadow: none!important;
            }
            textarea:focus {
                resize: none;
                border-color: #ff7a00!important;
                box-shadow: none!important;
            }

            /* 드롭다운 항목 선택 시 색상 변경 */
            .nav-item .dropdown-menu .dropdown-item:focus {
                background-color: #FF7A00!important;  /* 원하는 색으로 변경 */
                color: white!important;               /* 항목 텍스트 색상 */
            }

		</style>
		<title>BOOKIES</title>
	</head>

	<body>

		<% request.setCharacterEncoding("utf-8"); %>


		<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
			<div class="container-fluid">
                <a class="navbar-brand" href="/index" style="margin-left:15px;">
                    <img src="/images/logo_img/bookies_logo3.svg" alt="Bookies logo" width="125" class="d-inline-block align-text-top">
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
								<input class="btn btn-outline-light" type="button" value="QnA" onclick="location.href='/qnaList'">
							</li>
						</ul>
						<input class="btn btn-outline-light" type="button" value="로그인"
								onclick="location.href='/loginForm'" style="margin-left: 20px;">
						<input class="btn btn-outline-light" type="button" value="회원가입"
								onclick="location.href='/registerForm'" style="margin-left: 20px;">

							<% } else {
							    Integer point = (Integer) session.getAttribute("point");
							    Integer user_level = (Integer) session.getAttribute("user_level");
							%>
						<div class="collapse navbar-collapse" id="navbarSupportedContent">
							<ul class="navbar-nav me-auto mb-2 mb-lg-0">
								<li class="nav-item">
									<input class="btn btn-outline-light" type="button" value="QnA" onclick="location.href='/qnaList'">
								</li>
							</ul>
							<ul class="nav justify-content-end">
							    <li style="color:white;display: flex;align-items: center;margin-right:20px">
							        <a class="link-offset-3 link-offset-3-hover link-underline link-underline-opacity-0 link-underline-opacity-75-hover" style="text-align:center;color:white;text-decoration:none;" href="/pointCharger">
                                        <%= String.format("%,d pt", point) %>
                                    </a>
                                </li>

								<li style="color:white; display: flex; align-items: center; margin-right: 20px;">
                                    <% if (user_level != null && user_level == 123) { %>
                                        관리자 님
                                    <% } else { %>
                                        <%= user_id %> 님
                                    <% } %>
                                </li>

								<li class="nav-item dropdown">
									<div class="btn-group">
										<button type="button" class="btn btn-outline-light dropdown-toggle"
											data-bs-toggle="dropdown" aria-expanded="false">
													마이페이지
										</button>
										<ul class="dropdown-menu">
											<li><a data-bs-toggle="modal" data-bs-target="#goToMyInfo" class="dropdown-item" href="/myInfo">회원정보</a></li>
											<li>
											    <a class="dropdown-item" href="#" onclick="document.getElementById('myCart').submit();">장바구니</a>
                                                <form id="myCart" action="/eBookCart" method="POST" style="display: none;"></form>
											</li>
											<li><a class="dropdown-item" href="/myPurchase">결제 내역</a></li>
											<li><a class="dropdown-item" href="/myBook">내 서재</a></li>
										</ul>
									</div>
								</li>
							</ul>
							<input class="btn btn-outline-light" type="button" value="로그아웃"
										onclick="location.href='/logoutForm'" style="margin-left: 20px;">
						</div>

								<% } %>
					</div>
				</div>
			</nav>
			<div class="modal fade" id="goToMyInfo" tabindex="-1" aria-labelledby="goToMyInfoLabel" aria-hidden="true">
                  <div class="modal-dialog modal-dialog-centered">
                      <div class="modal-content">
                          <div class="modal-header">
                              <h1 class="modal-title fs-5" id="goToMyInfoLabel">비밀번호를 입력하세요.</h1>
                              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="취소"></button>
                          </div>
                          <form id="gotoInfo" action="/goToMyInfo" method="POST"> <!-- POST로 전송 -->
                              <div class="modal-body">
                                      <div class="mb-3">
                                          <label for="password" class="col-form-label">비밀번호</label>
                                          <input type="password" class="form-control" id="password" name="password" required> <!-- 비밀번호 입력 -->
                                          <p style="color:#EA002C;text-align:center;font-size:0.7rem;margin:0;margin-top:10px"> 비밀번호 5회 오류 시 10분 간 계정 잠금 </p>
                                          <input type="hidden" class="form-control" id="encrypted_password" name="encrypted_password">
                                      </div>
                              </div>
                              <div class="modal-footer">
                                  <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" onclick="window.history.back()">취소</button>
                                  <button type="submit" class="btn btn-primary">확인</button> <!-- 전송 버튼 -->
                              </div>
                          </form>
                          <script src="/js/encrypt.js"></script>
                          <script>
                              async function getKeyAndEncrypt() {
                                  const response = await fetch("/security/getKey");
                                  const { aesKey, aesIv } = await response.json(); // aesKey와 aesIv를 객체로 받아옴

                                  const password = document.getElementById('password').value;

                                  const encryptedData = await encryptAES(aesKey, aesIv, password);

                                  console.log("Encrypted Data: ", encryptedData);

                                  // 암호화된 값을 폼에 설정
                                  document.getElementById('password').value = "";
                                  document.getElementById('encrypted_password').value = encryptedData;

                                  document.getElementById('gotoInfo').submit();
                              }
                              document.getElementById('gotoInfo').addEventListener('submit', async function (event) {
                                  event.preventDefault(); // 기본 제출 동작 방지
                                  await getKeyAndEncrypt(); // 암호화 후 폼 제출
                              });

                          </script>



                      </div>
                  </div>
              </div>

	</body>

</html>