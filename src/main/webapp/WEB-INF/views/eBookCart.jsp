<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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

<div class="modal fade" id="messageCartDeletedModal" tabindex="-1" aria-labelledby="messageCartDeletedModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="messageCartDeletedModalLabel">삭제</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body" id="messageCartDeleted"></div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
                </div>
            </div>
        </div>
    </div>

    <c:if test="${not empty sessionScope.messageCartDeleted}">
        <script type="text/javascript">
            // 모달에 메시지 설정
            document.getElementById("messageCartDeleted").innerText = "${sessionScope.messageCartDeleted}";

            // Bootstrap 5에서 모달 띄우기
            var myModal = new bootstrap.Modal(document.getElementById('messageCartDeletedModal'));
            myModal.show(); // 모달을 띄운다.

            // 세션에서 메시지 제거
            <c:remove var="messageCartDeleted" scope="session" />
        </script>
    </c:if>


<div class="modal fade" id="CartDeletedFailedModal" tabindex="-1" aria-labelledby="CartDeletedFailedModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="CartDeletedFailedModalLabel">알림</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body" id="messageCartDeletedFailed"></div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
                </div>
            </div>
        </div>
    </div>

    <c:if test="${not empty sessionScope.messageCartDeletedFailed}">
        <script type="text/javascript">
            // 모달에 메시지 설정
            document.getElementById("messageCartDeletedFailed").innerText = "${sessionScope.messageCartDeletedFailed}";

            // Bootstrap 5에서 모달 띄우기
            var myModal = new bootstrap.Modal(document.getElementById('CartDeletedFailedModal'));
            myModal.show(); // 모달을 띄운다.

            // 세션에서 메시지 제거
            <c:remove var="messageCartDeletedFailed" scope="session" />
        </script>
    </c:if>


<div class="container">
    <jsp:include page="banner.jsp" />

    <div class="container" style="max-width: 1200px; border-radius: 5px; padding: 50px 20px;">
        <p class="text-start fs-1 fw-bold" style="display: flex; justify-content: center; margin-bottom: 30px; margin-top: 16px">장바구니</p>

        <%@ page import="java.util.List" %>
        <%@ page import="com.skrookies.dahaezlge.controller.book.Dto.BookDto" %>

        <table class="table" style="text-align: center; border: 1px solid #dddddd">
            <thead>
                    <tr>
                        <th style="text-align: center;">도서 이미지</th>
                        <th style="text-align: center;">제목</th>
                        <th style="text-align: center;">가격</th>
                        <th style="text-align: center;">삭제</th>
                    </tr>
            </thead>
            <tbody>
        <%
            List<BookDto> bookList = (List<BookDto>) request.getAttribute("cartList");
            Integer total_price = 0;

            if (!bookList.isEmpty()) {
                for (BookDto book : bookList) {
                    total_price += book.getBook_price();
        %>
            <tr align="center">
                <td> <img width="200px" src="<%=book.getBook_img_path()%>"/> </td>
                <td style="width:500px; text-align: center; vertical-align: middle;">
                <p style="margin:0;white-space: nowrap;overflow:hidden;width:500px;text-overflow:ellipsis;"><%= book.getBook_title() %> </p></td>
                <td style=" text-align: center; vertical-align: middle;"> <%= String.format("%,d pt", book.getBook_price()) %> </td>
                <td style=" text-align: center; vertical-align: middle;">
                    <button class="btn btn-danger" type="button" onclick="submitForm(<%= book.getBook_id() %>)">삭제</button>
                </td>
            </tr>
        <%
                }
            } else {
        %>

            <tr>
                <td colspan="4" align="center">데이터가 없습니다.</td>
            </tr>
        <%
            }
        %>
            </tbody>
        </table>

    </div>
    <div class="container" style="max-width: 1200px; margin-bottom: 100px; border-radius: 5px; padding: 50px 20px;">
        <table class="table" height="100px" style="text-align: left; vertical-align: middle; border: 1px solid #dddddd;">
            <tr>
                <td align="center" style="width: 200px; background-color: #eeeeee; border-right: 1px solid #dddddd;"> 총 상품 금액 </td>
                <td style="padding: 0px 20px"> <%= String.format("%,d pt", total_price) %>  </td>
                <td  align="center" width="50%" rowspan="3">
                    <button disabled class="btn btn-primary pull-right add-to-purchase-btn" id="purchaseButton" type="submit" style="width: 100%; height: 100%;">
                        <span style="font-size: 25px; font-weight: bold;">
                            주문하기
                        </span>
                    </button>
                </td>
            </tr>
        </table>
        <%
        if (!bookList.isEmpty()) {
        %>
        <script>
            document.getElementById("purchaseButton").disabled = false;
        </script>
        <%
        } else {
        %>
        <script>
            document.getElementById("purchaseButton").disabled = true;
            document.getElementById("purchaseButton").classList.replace("btn-primary", "btn-secondary");
        </script>
        <%
        }
        %>
    </div>

    <script>
        function submitForm(bookId) {
            var form = document.createElement('form');
            form.method = 'POST';
            form.action = '/deleteCart';

            var hiddenField = document.createElement('input');
            hiddenField.type = 'hidden';
            hiddenField.name = 'book_id';
            hiddenField.value = bookId;

            form.appendChild(hiddenField);
            document.body.appendChild(form);
            form.submit();
        }

        document.getElementById('purchaseButton').addEventListener('click', function () {
            var form = document.createElement('form');
            form.method = 'POST';
            form.action = '/eBookPurchase';

            document.body.appendChild(form);
            form.submit();
        });
    </script>

</div>
</body>
</html>