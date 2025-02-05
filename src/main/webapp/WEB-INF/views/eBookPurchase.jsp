<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>

    <link rel="icon" type="image/png" href="images/icon_img/favicon.png">
    <title>BOOKIES</title>

</head>
<body>

<div class="container">
    <jsp:include page="banner.jsp" />

    <div class="container" style="max-width: 1200px; border-radius: 5px; padding: 50px 20px;">
        <p class="text-start fs-1 fw-bold" style="display: flex; justify-content: center; margin-bottom: 30px; margin-top: 16px">결제 정보</p>

        <table class="table" style="text-align: center; border: 1px solid #dddddd">
            <thead>
                    <tr>
                        <th style="text-align: center;">도서 이미지</th>
                        <th style="text-align: center;">제목</th>
                        <th style="text-align: center;">가격</th>
                    </tr>
            </thead>
            <tbody>
        <%@ page import="java.util.List" %>
        <%@ page import="com.skrookies.dahaezlge.controller.book.Dto.BookDto" %>

        <%
            List<BookDto> bookList = (List<BookDto>) request.getAttribute("purchaseList");
            Integer total_price = 0;

            if (bookList != null) {
                for (BookDto book : bookList) {
                    total_price += book.getBook_price();
        %>
            <tr align="center">
                <td> <img width="200px" src="<%= book.getBook_img_path() %>"</td>
                <td style="width:500px; text-align: center; vertical-align: middle;"> <p style="margin:0;white-space: nowrap;overflow:hidden;width:500px;text-overflow:ellipsis;">
                <%= book.getBook_title() %> </p></td>
                <td style=" text-align: center; vertical-align: middle;"> <%= String.format("%,d pt", book.getBook_price()) %> </td>
            </tr>
        <%
                }
            } else {
        %>
            <tr>
                <td colspan="2">데이터가 없습니다.</td>
            </tr>
        <%
            }
        %>
            </tbody>
        </table>
    </div>

<!--
    <div align="center" style="font-weight:bold; font-size:25px;">
    <%
        int userPoint = (int) session.getAttribute("point");
    %>
    보유 포인트: <%= String.format("%,d", userPoint) %>
    - 총 금액: <%= String.format("%,d pt", total_price) %>
    </div>

    <div class="d-grid gap-2 col-6 mx-auto" style="margin-top:50px">
        <button class="btn btn-primary pull-right add-to-purchase-btn" id="purchaseProc" type="submit">결제하기</button>
    </div>
--!>

    <div class="container" style="max-width: 1200px; margin-bottom: 100px; border-radius: 5px; padding: 50px 20px;">
        <table class="table" height="300px" style="text-align: left; vertical-align: middle; border: 1px solid #dddddd;">
            <tr>
                <td align="center" style="width: 200px; background-color: #eeeeee; border-right: 1px solid #dddddd;"> 주문 상품 정보 </td>
                <td style="padding: 0px 20px"> 총 <%= bookList.size() %>권, <%= String.format("%,d pt", total_price) %>  </td>
                <td  align="center" width="25%" rowspan="3">
                    <button class="btn btn-primary pull-right add-to-purchase-btn" id="purchaseProc" type="submit" style="width: 100%; height: 100%;">
                        <span style="font-size: 25px; font-weight: bold;">
                            결제하기
                        </span>
                        <span style="font-size: 20px;">
                            <br>(<%= String.format("%,d pt", total_price) %>)
                        </span>
                    </button>
                </td>
            </tr>
            <tr>
                <td align="center" style="background-color: #eeeeee; border-right: 1px solid #dddddd;"> 보유 포인트 </td>
                <td style="padding: 0px 20px"> <%= String.format("%,d", userPoint) %>  </td>
            </tr>
            <tr>
                <td align="center" style="background-color: #eeeeee; border-right: 1px solid #dddddd;"> 구매 후 잔액 </td>
                <td style="padding: 0px 20px"> <%= String.format("%,d", userPoint-total_price) %>  </td>
            </tr>
        </table>
    </div>

    <div class="modal fade" id="purchaseModal" tabindex="-1" aria-labelledby="purchaseModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="purchaseModalLabel">알림</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body" id="purchaseModalBody">
                </div>
                <div class="modal-footer">
                    <% String previousPage = request.getHeader("referer"); %>

                    <a id="goToPreviousBtn" class="btn btn-primary" href="<%= previousPage %>" style="display: none;">장바구니로 바로가기</a>
                    <a id="goToChargeBtn" class="btn btn-primary" href="/pointCharger" style="display: none;">충전 페이지로 바로가기</a>
                    <a id="goToMainBtn" class="btn btn-primary" href="/index" style="display: none;">메인화면으로 가기</a>
                    <a id="goToMyPurchaseBtn" class="btn btn-primary" href="/myPurchase" style="display: none;">결제내역으로 바로가기</a>
                </div>
            </div>
        </div>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', () => {
            const purchaseButton = document.getElementById('purchaseProc'); // 단일 버튼
            const purchaseModal = new bootstrap.Modal(document.getElementById('purchaseModal'));
            const purchaseModalBody = document.getElementById('purchaseModalBody');
            const goToChargeBtn = document.getElementById('goToChargeBtn');
            const goToMainBtn = document.getElementById('goToMainBtn');
            const goToPreviousBtn = document.getElementById('goToPreviousBtn');
            const goToMyPurchaseBtn = document.getElementById('goToMyPurchaseBtn');

            var purchaseUrl = "${purchaseUrl}";

            purchaseButton.addEventListener('click', function () {
                var bookId = 0;
                var totalBookPrice = <%= total_price %>; // 총 금액

                var requestBody;
                var goToPreviousBtnText = '';  // 버튼 텍스트 변수
                var goToPreviousBtnHref = '';  // 버튼 href 변수

                if (purchaseUrl === "/purchaseItemProc") {
                <%
                    if (bookList != null && !bookList.isEmpty()) {
                        BookDto book = bookList.get(0); // 첫 번째 책을 선택
                %>
                        bookId = <%= book.getBook_id() %>;
                        requestBody = JSON.stringify({ bookId: bookId, totalBookPrice: totalBookPrice });

                        goToPreviousBtnText = '이전 페이지로 가기';
                        goToPreviousBtnHref = '/eBookDetail?book_id='+bookId;
                        goToPreviousBtn.setAttribute('style', 'background-color: #6c757d !important;border-color:#6c757d !important;');

                        // hover 효과를 위한 이벤트 리스너 추가
                        goToPreviousBtn.addEventListener('mouseenter', function () {
                            goToPreviousBtn.setAttribute('style', 'background-color: #5c636a !important;border-color:#5c636a !important; ');
                        });

                        goToPreviousBtn.addEventListener('mouseleave', function () {
                            goToPreviousBtn.setAttribute('style', 'background-color: #6c757d !important;border-color:#6c757d !important;'); // 기본 배경색으로 돌아가기
                        });

                <%
                    }
                %>
                } else {
                    requestBody = JSON.stringify({ totalBookPrice: totalBookPrice });
                    goToPreviousBtnText = '장바구니로 가기';
                    goToPreviousBtnHref = '/eBookCart';
                    goToPreviousBtn.setAttribute('style', 'background-color: #6c757d !important;border-color:#6c757d !important;');

                    // hover 효과를 위한 이벤트 리스너 추가
                    goToPreviousBtn.addEventListener('mouseenter', function () {
                        goToPreviousBtn.setAttribute('style', 'background-color: #5c636a !important;border-color:#5c636a !important; ');
                    });

                    goToPreviousBtn.addEventListener('mouseleave', function () {
                        goToPreviousBtn.setAttribute('style', 'background-color: #6c757d !important;border-color:#6c757d !important;'); // 기본 배경색으로 돌아가기
                    });

                }

                goToPreviousBtn.textContent = goToPreviousBtnText;
                goToPreviousBtn.href = goToPreviousBtnHref;

                fetch(purchaseUrl, { // 동적으로 URL 변경
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',  // JSON 데이터로 보냄
                    },
                    body: requestBody  // 선택된 데이터 전송
                })
                .then(response => response.json())
                .then(data => {
                    console.log('Response data:', data);

                    // 상태에 따른 메시지 처리 및 버튼 표시
                    switch (data.status) {
                        case 'purchased':
                            purchaseModalBody.textContent = data.message;
                            goToChargeBtn.style.display = 'none';
                            goToMainBtn.style.display = 'none';
                            goToPreviousBtn.style.display = 'inline-block';
                            goToMyPurchaseBtn.style.display = 'none';

                            break;
                            case 'charge':
                                purchaseModalBody.textContent = data.message;
                                goToChargeBtn.style.display = 'inline-block';
                                goToMainBtn.style.display = 'none';
                                goToPreviousBtn.style.display = 'inline-block';
                                goToMyPurchaseBtn.style.display = 'none';


                            break;
                        case 'exists':
                            purchaseModalBody.textContent = data.message;
                            goToChargeBtn.style.display = 'none';
                            goToMainBtn.style.display = 'none';
                            goToPreviousBtn.style.display = 'inline-block';
                            goToMyPurchaseBtn.style.display = 'inline-block';
                            break;
                        case 'purchase':
                            purchaseModalBody.textContent = data.message;
                            goToChargeBtn.style.display = 'none';
                            goToMainBtn.style.display = 'inline-block';
                            goToPreviousBtn.style.display = 'none';
                            goToMyPurchaseBtn.style.display = 'inline-block';
                            goToMainBtn.setAttribute('style', 'background-color: #6c757d !important;border-color:#6c757d !important;');

                            // hover 효과를 위한 이벤트 리스너 추가
                            goToMainBtn.addEventListener('mouseenter', function () {
                                goToMainBtn.setAttribute('style', 'background-color: #5c636a !important;border-color:#5c636a !important; ');
                            });

                            goToMainBtn.addEventListener('mouseleave', function () {
                                goToMainBtn.setAttribute('style', 'background-color: #6c757d !important;border-color:#6c757d !important;'); // 기본 배경색으로 돌아가기
                            });

                            break;
                        case 'error':
                            purchaseModalBody.textContent = data.message;
                            goToChargeBtn.style.display = 'none';
                            goToMainBtn.style.display = 'none';
                            goToPreviousBtn.style.display = 'inline-block';
                            goToMyPurchaseBtn.style.display = 'none';
                            break;
                        default:
                            purchaseModalBody.textContent = '알 수 없는 상태입니다.';
                            goToChargeBtn.style.display = 'none';
                            goToMainBtn.style.display = 'none';
                            goToPreviousBtn.style.display = 'inline-block';
                            goToMyPurchaseBtn.style.display = 'inline-block';
                    }

                    purchaseModal.show(); // 팝업 표시
                })
                .catch(error => {
                    console.error('Fetch Error:', error);
                    purchaseModalBody.textContent = '오류가 발생했습니다. 다시 시도해주세요.';
                    goToChargeBtn.style.display = 'none';
                    goToPreviousBtn.style.display = 'inline-block';
                    goToMyPurchaseBtn.style.display = 'none';
                    purchaseModal.show();
                });
            });

            // 팝업 닫힐 때 버튼 초기화
            purchaseModal.addEventListener('hidden.bs.modal', () => {
                goToChargeBtn.style.display = 'none';
                goToPreviousBtn.style.display = 'none';
                goToMyPurchaseBtn.style.display = 'none';
            });
        });
    </script>
</div>
</body>
</html>