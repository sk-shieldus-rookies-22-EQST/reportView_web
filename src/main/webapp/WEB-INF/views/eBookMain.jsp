<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>

    <link rel="icon" type="image/png" href="images/favicon.png">

    <title>BOOKIES</title>
    <style>
        #nav-bar {
            position: relative;
            z-index: 1044!important;  /* 높은 값을 설정하여 다른 요소들 위에 위치하도록 */
          }
        .add-to-cart-btn {
            width: 100%;
            height: 40px; /* 버튼 높이 설정 */
            background-image: url('/images/cart_icon.svg');
            border: none;
            background-repeat: no-repeat;
                background-position: center;
        }

        .add-to-cart-btn:hover {
            background-image: url('/images/cart_icon_white.svg'); /* hover시 다른 이미지 */
        }
    </style>
</head>
<body>

<div class="modal fade" id="delModal" tabindex="-1" aria-labelledby="delModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="delModalLabel">완료</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body" id="deletedMessage"></div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
                </div>
            </div>
        </div>
    </div>

    <c:if test="${not empty sessionScope.deletedMessage}">
        <script type="text/javascript">
            // 모달에 메시지 설정
            document.getElementById("deletedMessage").innerText = "${sessionScope.deletedMessage}";

            // Bootstrap 5에서 모달 띄우기
            var myModal = new bootstrap.Modal(document.getElementById('delModal'));
            myModal.show(); // 모달을 띄운다.

            // 세션에서 메시지 제거
            <c:remove var="deletedMessage" scope="session" />
        </script>
    </c:if>




<div class="container" id="nav-bar">
    <jsp:include page="banner.jsp" />
    </div><div class="container">

    <div class="container sticky-top" style="border-radius: 5px;padding: 50px 20px 10px 20px; background-color:white;">
        <p class="text-start fs-1 fw-bold" style="display: flex;justify-content: center; margin-bottom:30px;margin-top:16px">전체 도서 목록</p>


        <div style="margin-top: 20px; display: flex; justify-content: flex-end;">
            <form method="get" action="/index" style="display: flex; align-items: center; gap: 10px;">
                <div class="input-group mb-3">
                    <!-- 제목 입력 -->
                    <input
                            type="text"
                            class="form-control"
                            placeholder="제목 입력"
                            name="keyword"
                            value="${keyword}"
                            style="width: 200px;box-shadow:none;"
                            aria-describedby="button-addon2"
                            onfocus="this.style.backgroundColor='#f9f9f9';"
                            onblur="this.style.backgroundColor='';"
                    >
                </div>
                <div class="input-group mb-3">
                    <!-- 시작 날짜 입력 -->
                    <input
                            type="date"
                            class="form-control"
                            name="sdate"
                            value="${sdate}"
                            style="width: 150px;box-shadow:none;"
                            onfocus="this.style.backgroundColor='#f9f9f9';"
                            onblur="this.style.backgroundColor='';"
                    >
                </div>
                <div class="input-group mb-3">
                    <!-- 종료 날짜 입력 -->
                    <input
                            type="date"
                            class="form-control"
                            name="edate"
                            value="${edate}"
                            style="width: 150px;box-shadow:none;"
                            onfocus="this.style.backgroundColor='#f9f9f9';"
                            onblur="this.style.backgroundColor='';"
                    >
                </div>
                <div class="input-group mb-3">
                    <!-- 검색 버튼 -->
                    <button
                            type="submit"
                            class="btn btn-primary"
                            id="button-addon2"
                    >
                        검색
                    </button>
                </div>
            </form>
        </div>
</div><div class="container">
        <div class="container text-center">
                <div class="row row-cols-5" style="width=100%;">
                    <c:forEach var="book" items="${books}">
                        <!-- 각 col은 고정된 width로 5개가 들어감 -->
                        <div style="cursor:pointer;display: flex;align-items: center;flex-direction: column;margin-bottom: 5%;">

                            <div style="margin: 0% 10%;width:184px;"  onclick="location.href='/eBookDetail?book_id=${book['book_id']}'">
                                <!--<img class="image_container" src="${book['book_img_path']}" style="border:1px solid; position: relative;width: 100%;overflow: hidden;">-->
                                <img class="image_container" src="/images/test.jpg" style="border:1px solid;position: relative;width: 100%;overflow: hidden;">

                            </div>
                            <div style="margin-bottom:16px;display: flex;height: 170px;flex-direction: column;justify-content: space-between;">
                                <div style=" display: flex; flex-direction: column; justify-content: flex-start;"  onclick="location.href='/eBookDetail?book_id=${book['book_id']}'">

                                    <p class="fw-semibold" style="font-size: 1.2rem;margin:0;width:184px;display: -webkit-box;-webkit-line-clamp: 2;-webkit-box-orient: vertical;overflow: hidden;text-overflow: ellipsis;">${book['book_title']}</p>

                                    <p class="text-secondary" style="margin:0;font-size: 0.8rem;">${book['book_auth']}</p>
                                    <p class="col" style="padding: 0;margin:0;">${book['book_price']}원</p>
                                   </div>
                                    <form class="col" id="addToCartForm" method="post" action="/addCart" style="display: inline-block; width:100%;align-content: end;">
                                        <button type="button" class="btn btn-outline-primary add-to-cart-btn" data-book-id="${book['book_id']}" data-book-price="${book['book_price']}"></button>
                                    </form>

                                    <div class="modal fade" id="cartModal" tabindex="-1" aria-labelledby="cartModalLabel" aria-hidden="true">
                                        <div class="modal-dialog modal-dialog-centered">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h5 class="modal-title" id="cartModalLabel">알림</h5>
                                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                </div>
                                                <div class="modal-body" id="cartModalBody">
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
                                                    <a id="goToCartBtn" class="btn btn-primary" href="/eBookCart" style="display: none;">장바구니로 바로가기</a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                            </div>
                        </div>

                        <!-- 5개마다 새로운 row를 시작 -->
                        <c:if test="${(status.index + 1) % 4 == 0}">
                            </div><div class="row">
                        </c:if>
                    </c:forEach>
                </div>
            </div>
    </div>
</div>
</body>
<script>
    document.addEventListener('DOMContentLoaded', () => {
        const cartButtons = document.querySelectorAll('.add-to-cart-btn'); // 버튼에 클래스를 지정하세요
        const cartModal = new bootstrap.Modal(document.getElementById('cartModal'));
        const cartModalBody = document.getElementById('cartModalBody');
        const goToCartBtn = document.getElementById('goToCartBtn');

        cartButtons.forEach(button => {
            button.addEventListener('click', (event) => {
                const bookId = event.target.getAttribute('data-book-id');
                const bookPrice = event.target.getAttribute('data-book-price');

                fetch('/addCart', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({ book_id: bookId,  book_price: bookPrice }),
                })
                .then(response => response.json())
                .then(data => {
                    console.log('Response data:', data);

                    // 상태에 따른 메시지 처리 및 버튼 표시
                    switch (data.status) {
                        case 'purchased':
                            cartModalBody.textContent = data.message; // 이미 존재
                            goToCartBtn.style.display = 'inline-block'; // 버튼 표시
                            break;
                        case 'exists':
                            cartModalBody.textContent = data.message; // 이미 존재
                            goToCartBtn.style.display = 'inline-block'; // 버튼 표시
                            break;
                        case 'added':
                            cartModalBody.textContent = data.message; // 추가 성공
                            goToCartBtn.style.display = 'inline-block'; // 버튼 표시
                            break;
                        case 'error':
                            cartModalBody.textContent = data.message; // 일반 오류
                            goToCartBtn.style.display = 'none'; // 버튼 숨기기
                            break;
                        case 'login_required':
                            cartModalBody.textContent = data.message; // 로그인 필요
                            goToCartBtn.style.display = 'none'; // 버튼 숨기기
                            break;
                        default:
                            cartModalBody.textContent = '알 수 없는 상태입니다.';
                            goToCartBtn.style.display = 'none'; // 버튼 숨기기
                    }

                    cartModal.show(); // 팝업 표시
                })
                .catch(error => {
                    console.error('Fetch Error:', error);
                    cartModalBody.textContent = '오류가 발생했습니다. 다시 시도해주세요.';
                    goToCartBtn.style.display = 'none'; // 버튼 숨기기
                    cartModal.show();
                });
            });
        });

        // 팝업 닫힐 때 버튼 초기화
        cartModal.addEventListener('hidden.bs.modal', () => {
            goToCartBtn.style.display = 'none';
        });
    });

</script>
</html>
