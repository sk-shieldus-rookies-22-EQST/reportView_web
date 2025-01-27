<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <title>eBookMain</title>
    <style>

    </style>
</head>
<body>
<div class="container">
    <jsp:include page="banner.jsp" />

    <div class="container" style="max-width: 1200px; margin-bottom: 100px; border-radius: 5px; padding: 50px 20px;">
        <p class="text-start fs-1 fw-bold" style="display: flex; justify-content: center; margin-bottom: 30px; margin-top: 16px">전체 도서 목록</p>

        <div style="margin-top: 20px; display: flex; justify-content: flex-end;">
            <form method="get" action="/index?page=${1}&keyword=${name}" style="display: flex; align-items: center; gap: 10px;">
                <div class="input-group mb-3">
                    <input type="text" class="form-control" placeholder="제목 입력" name="keyword" value="${keyword}" style="width: 200px;box-shadow:none;" aria-describedby="button-addon2" onfocus="this.style.backgroundColor='#f9f9f9';" onblur="this.style.backgroundColor='';">
                    <button type="submit" class="btn btn-primary" id="button-addon2">검색</button>
                </div>
            </form>
        </div>

        <table class="table table-hover" style="text-align: center; border: 1px solid #dddddd">
            <thead>
            <tr>
                <th style="text-align: center;">도서 이미지</th>
                <th style="text-align: center;">제목</th>
                <th style="text-align: center;">작가</th>
                <th style="text-align: center;">가격</th>
                <th style="text-align: center;">장바구니</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="book" items="${books}">
                <tr>
                    <td style="cursor:pointer;" onclick="location.href='/eBookDetail?book_id=${book['book_id']}'">
                        <c:choose>
                            <c:when test="${book['book_img_path'] != null}">
                                <img src="${book['book_img_path']}" alt="Book Image" style="width: 80px; height: 80px; object-fit: cover; border-radius: 8px;">
                            </c:when>
                            <c:otherwise>
                                <img src="/images/no-image.png" alt="No Image Available" style="width: 80px; height: 80px; object-fit: cover; border-radius: 8px;">
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td style="cursor:pointer;" onclick="location.href='/eBookDetail?book_id=${book['book_id']}'" style="width:500px; text-align: center; vertical-align: middle;">
                        <p style="margin:0;white-space: nowrap;overflow:hidden;width:500px;text-overflow:ellipsis;text-align:left;">
                                ${book['book_title']}
                        </p>
                    </td>

                    <td style="cursor:pointer;" onclick="location.href='/eBookDetail?book_id=${book['book_id']}'" style="text-align: center; vertical-align: middle;">${book['book_auth']}</td>
                    <td style="cursor:pointer;" onclick="location.href='/eBookDetail?book_id=${book['book_id']}'" style="text-align: center; vertical-align: middle;">${book['book_price']}원</td>
                    <td>
<%--                        <form method="post" action="/addCart">--%>
<%--                            <input type="hidden" name="book_id" value="${book['book_id']}">--%>
<%--                            <button type="submit" class="btn btn-primary" >장바구니</button>--%>
<%--                        </form>--%>
                        <form id="addToCartForm" method="post" action="/addCart" style="display: inline-block;">
                            <input type="hidden" name="book_id" value="${book['book_id']}">
                            <button type="button" class="btn btn-primary add-to-cart-btn" data-book-id="${book['book_id']}">장바구니</button>
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

                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <nav class="mt-4" style="display: flex; justify-content: center;">
            <ul class="pagination">
                <c:if test="${currentPage > 1}">
                    <li class="page-item">
                        <a class="page-link" href="/index?page=${currentPage - 1}&keyword=${keyword}">이전</a>
                    </li>
                </c:if>

                <c:forEach begin="${startPage}" end="${endPage}" var="i">
                    <li class="page-item <c:if test='${i == currentPage}'>active</c:if>">
                        <a class="page-link" href="/index?page=${i}&keyword=${keyword}">${i}</a>
                    </li>
                </c:forEach>

                <c:if test="${currentPage < totalPages}">
                    <li class="page-item">
                        <a class="page-link" href="/index?page=${currentPage + 1}&keyword=${keyword}">다음</a>
                    </li>
                </c:if>
            </ul>
        </nav>
    </div>
</div>
</body>
<script>
    document.addEventListener('DOMContentLoaded', () => {
        const cartButtons = document.querySelectorAll('.add-to-cart-btn');
        const cartModal = new bootstrap.Modal(document.getElementById('cartModal'));
        const cartModalBody = document.getElementById('cartModalBody');
        const goToCartBtn = document.getElementById('goToCartBtn');

        cartButtons.forEach(button => {
            button.addEventListener('click', (event) => {
                const bookId = event.target.getAttribute('data-book-id');

                fetch('/addCart', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({ book_id: bookId }),
                })
                    .then(response => {
                        if (!response.ok) {
                            throw new Error(`HTTP error! status: ${response.status}`);
                        }
                        return response.json();
                    })
                    .then(data => {
                        console.log('Response data:', data);

                        // 상태에 따른 메시지 처리 및 버튼 표시
                        switch (data.status) {
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
