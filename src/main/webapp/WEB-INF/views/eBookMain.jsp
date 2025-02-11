<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>

    <link rel="icon" type="image/png" href="images/icon_img/favicon.png">

    <title>BOOKIES</title>
    <style>
        .sticky-top{
            top: 30px!important;
        }
        #nav-bar {
            z-index: 1044!important;  /* 높은 값을 설정하여 다른 요소들 위에 위치하도록 */
          }
        .add-to-cart-btn {
            width: 100%;
            height: 40px; /* 버튼 높이 설정 */
            background-image: url('/images/icon_img/cart_icon.svg');
            border: none;
            background-repeat: no-repeat;
                background-position: center;
        }

        .add-to-cart-btn:hover {
            background-image: url('/images/icon_img/cart_icon_white.svg'); /* hover시 다른 이미지 */
        }

        /* 선택된 버튼에 대해 다른 배경색을 적용 */
        .btn-check:checked + .btn-outline-primary {
            background-color: #FF7A00!important; /* 원하는 색상으로 설정 */
            color: white!important;
        }

        /* 버튼에 hover가 될 때 색상 변경 */
        label.btn-outline-primary:hover {
            background-color: #FF7A00!important; /* 원하는 hover 색상 */
            border-color:#FF7A00!important;
            color: white!important;
        }

    </style>
</head>
<body>
<div class="container fixed-top" id="nav-bar">
    <jsp:include page="banner.jsp" />
</div>


<div class="modal fade" id="delModal" tabindex="-1" aria-labelledby="delModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
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

    <div class="container" style="margin-top:56px">

    <div id="carouselExampleAutoplaying" class="carousel slide" data-bs-ride="carousel">
      <div class="carousel-inner">
        <div class="carousel-item active">
          <img src="/images/common_img/test_ignited.png" class="d-block w-100" alt="Ignited Minds">
        </div>
        <div class="carousel-item">
          <img src="/images/common_img/test_happiness.png" class="d-block w-100" alt="Resisting Happiness">
        </div>
        <div class="carousel-item">
          <img src="/images/common_img/test_think.png" class="d-block w-100" alt="Think Straight">
        </div>
      </div>
      <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleAutoplaying" data-bs-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Previous</span>
      </button>
      <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleAutoplaying" data-bs-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Next</span>
      </button>
    </div>


    <div class="container sticky-top" style="padding:50px 20px 40px 20px; background-color:white;">
        <div class="justify-content-center" style="margin-top: 20px;display:flex;flex-direction: column;align-items: center;">

            <form class="row justify-content-center" method="get" action="/index" style="align-items: center; width: 80%; display: flex;">
                <div style="padding: 0; width: 100%; display: flex; gap:10px">
                    <!-- 기존 검색 관련 입력 필드 -->
                    <input
                        type="text"
                        class="form-control"
                        placeholder="제목 입력"
                        name="keyword"
                        value="${keyword}"
                        style="box-shadow:none; flex: 4;"
                        aria-describedby="button-addon2"
                        onfocus="this.style.backgroundColor='#f9f9f9';"
                        onblur="this.style.backgroundColor='';"
                    >
                    <input
                        type="date"
                        class="form-control"
                        name="sdate"
                        value="${sdate}"
                        style="box-shadow:none; flex: 2;"
                        onfocus="this.style.backgroundColor='#f9f9f9';"
                        onblur="this.style.backgroundColor='';"
                    >
                    <input
                        type="date"
                        class="form-control"
                        name="edate"
                        value="${edate}"
                        style="box-shadow:none; flex: 2;"
                        onfocus="this.style.backgroundColor='#f9f9f9';"
                        onblur="this.style.backgroundColor='';"
                    >
                    <!-- sort와 direction 값 유지 -->
                    <input type="hidden" name="sort" value="${param.sort}">
                    <input type="hidden" name="direction" value="${param.direction}">
                    <!-- 검색 버튼 -->
                    <button
                        type="submit"
                        class="btn btn-primary"
                        style="flex:0.5;"
                        id="button-addon2"
                    >
                        검색
                    </button>
                </div>
            </form>

            <div class="btn-group" role="group" aria-label="Basic radio toggle button group" style="width: 80%;display: flex;justify-content: flex-end;padding-top: 20px;">
              <form id="sortForm" method="get" action="/index">
                  <!-- 기존 검색, 날짜 값들 -->
                  <input type="hidden" name="keyword" value="${param.keyword}">
                  <input type="hidden" name="sdate" value="${param.sdate}">
                  <input type="hidden" name="edate" value="${param.edate}">

                  <!-- 각 라디오 버튼에 data-direction 속성을 추가 -->
                  <!-- 등록 날짜 순: sort는 book_reg_date, direction은 ASC -->
                    <input type="radio" class="btn-check" name="sort" value="date" id="sortDate" data-direction="ASC"
                           ${param.sort == 'date' ? 'checked' : ''} checked>
                    <label class="btn btn-outline-primary radio_btn" for="sortDate">최신순</label>

                  <input type="radio" class="btn-check" name="sort" value="title" id="sortTitle" data-direction="ASC"
                         ${param.sort == 'title' ? 'checked' : ''}>
                  <label class="btn btn-outline-primary radio_btn"  for="sortTitle" class="me-3">제목 순</label>

                  <!-- 낮은 가격 순: sort는 book_price, direction은 ASC -->
                  <input type="radio" class="btn-check" name="sort" value="price_asc" id="sortPriceAsc" data-direction="ASC"
                         ${param.sort == 'price_asc' && param.direction == 'ASC' ? 'checked' : ''}>
                  <label class="btn btn-outline-primary radio_btn" for="sortPriceAsc" class="me-3">낮은 가격 순</label>

                  <!-- 높은 가격 순: sort는 book_price, direction은 DESC -->
                  <input type="radio" class="btn-check" name="sort" value="price_desc" id="sortPriceDesc" data-direction="DESC"
                         ${param.sort == 'price_desc' && param.direction == 'DESC' ? 'checked' : ''}>
                  <label class="btn btn-outline-primary radio_btn" for="sortPriceDesc" class="me-3">높은 가격 순</label>



                  <!-- direction은 자바스크립트로 업데이트할 hidden 필드 -->
                  <input type="hidden" id="directionInput" name="direction" value="${param.direction}">
              </form>
            </div>
        </div>


        <script>
            // 라디오 버튼 클릭 시 data-direction 값을 hidden 필드에 설정 후 폼 제출
            document.querySelectorAll('input[name="sort"]').forEach(radio => {
                radio.addEventListener('change', function() {
                    var direction = this.getAttribute('data-direction');
                    document.getElementById('directionInput').value = direction;
                    document.getElementById('sortForm').submit();
                });
            });
        </script>
</div>
<div class="container">
        <div class="container text-center">
                <div class="row row-cols-5" style="width=100%;">
                <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
                    <c:forEach var="book" items="${books}">
                        <div style="cursor:pointer;display: flex;align-items: center;flex-direction: column;margin-bottom: 5%;justify-content: flex-end;">

                            <div style="margin: 0% 10%;width:184px;"  onclick="location.href='/eBookDetail?book_id=${book['book_id']}'">
                                <img class="image_container" src="${book['book_img_path']}" style="border:1px solid; position: relative;width: 100%;overflow: hidden;">

                            </div>
                            <div style="margin-bottom:16px;display: flex;height: 170px;flex-direction: column;justify-content: space-between;">
                                <div style=" display: flex; flex-direction: column; justify-content: flex-start;align-items: center;"  onclick="location.href='/eBookDetail?book_id=${book['book_id']}'">

                                    <p class="fw-semibold" style="font-size: 1.2rem;margin:0;width:184px;display: -webkit-box;-webkit-line-clamp: 2;-webkit-box-orient: vertical;overflow: hidden;text-overflow: ellipsis;">${book['book_title']}</p>

                                    <p class="text-secondary" style="margin:0;font-size: 0.8rem;width:184px;display: -webkit-box;-webkit-line-clamp: 2;-webkit-box-orient: vertical;overflow: hidden;text-overflow: ellipsis;">${book['book_auth']}</p>
                                    <p class="col" style="padding: 0;margin:0;"><fmt:formatNumber value="${book['book_price']}" type="number" groupingUsed="true" />원</p>
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
                            goToCartBtn.style.display = 'none'; // 버튼 표시
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
