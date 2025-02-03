<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>

    <link rel="icon" type="image/png" href="images/favicon.png">
    <title>Bookies eBook Detail</title>

    <script>
    var message = "${messageDetail}";
    if (message) {
        alert(message);
    }
</script>
</head>
<body>
<div class="container">
    <jsp:include page="banner.jsp" />

    <div class="container" style="max-width: 800px; margin-bottom: 100px; border-radius: 5px; padding: 50px 20px;">
        <table class="table" height="300px" style="text-align: left; vertical-align: middle; border: 1px solid #dddddd; padding: 15px;">
        <%@ page import="java.util.List" %>
        <%@ page import="com.skrookies.dahaezlge.controller.book.Dto.BookDto" %>

        <%
            BookDto eBook = (BookDto) request.getAttribute("bookInfo");
            String book_id = request.getParameter("book_id");
        %>

            <tr>
                <td  align="center" width="30%" rowspan="4">
                    <img
                        src="<%= eBook.getBook_img_path() %>"
                    />
                </td>
                <td align="center" style="width: 100px"> 제목 </td>
                <td> <%= eBook.getBook_title() %>  </td>
            </tr>
            <tr>
                <td align="center"> 작가 </td>
                <td> <%= eBook.getBook_auth() %>  </td>
            </tr>
            <tr>
                <td align="center"> 등록일 </td>
                <td> ${formattedDate} </td>
            </tr>
            <tr>
                <td align="center"> 가격 </td>
                <td> <%= String.format("%,d원", eBook.getBook_price()) %>  </td>
            </tr>
        </table>

        <div>
            <%= eBook.getBook_summary() %>
        </div>
        <div class="d-grid gap-2 col-6 mx-auto" style="margin-top:30px; width:700px">
            <div class="grid text-center" style="display:flex;justify-content: space-between;">
                    <!--<form method="POST" action="/addCart" class="g-col-6"  style="width:49%">
                        <input type="hidden" name="book_id" value="<%= book_id %>" />
                        <input type="hidden" name="book_price" value="<%= eBook.getBook_price() %>" />
                        <button class="btn btn-outline-primary" type="submit" style="width:100%;padding:20px;">장바구니</button>
                        <button type="button" style="width:100%;padding:20px;" class="btn btn-outline-primary add-to-cart-btn" data-book-id="<%= book_id %>">장바구니</button>
                    </form>-->
                    <form method="POST" action="/addCart" class="g-col-6" style="width:49%">
                        <button type="button" style="width:100%;padding:20px;" class="btn btn-outline-primary add-to-cart-btn"
                            data-book-id="<%= book_id %>" data-book-price="<%= eBook.getBook_price() %>">장바구니</button>
                    </form>

                <form method="POST" action="/eBookPurchaseItem" class="g-col-6" style="width:49%;">
                      <input type="hidden" name="book_id" value="<%= book_id %>" />
                      <button class="btn btn-primary" type="submit" style="width:100%;padding:20px;">결제</button>
                </form>
            </div>
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
            <% String previousPage = request.getHeader("referer"); %>
            <button class="btn btn-outline-primary" type="button"
                onclick="location.href='/index'" style="padding:20px;">목록으로</button>
        </div>

    </div>
</div>
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
</body>
</html>