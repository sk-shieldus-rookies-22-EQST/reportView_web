<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>

    <link rel="icon" type="image/png" href="images/favicon.png">

    <title>Bookies Cart</title>

<script>
    // Flash Attribute를 사용하여 메시지 가져오기
    var message = "${messageCart}";
    if (message) {
        window.alert(message);
    }
</script>
</head>
<body>
<div class="container">
    <jsp:include page="banner.jsp" />

    <div class="container" style="max-width: 1200px; margin-bottom: 100px; border-radius: 5px; padding: 50px 20px;">
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

            if (bookList != null) {
                for (BookDto book : bookList) {
                    total_price += book.getBook_price();
        %>
            <tr align="center">
                <td> <img src="<%=book.getBook_img_path()%>" </td>
                <td style="width:500px; text-align: center; vertical-align: middle;">
                <p style="margin:0;white-space: nowrap;overflow:hidden;width:700px;text-overflow:ellipsis;"><%= book.getBook_title() %> </p></td>
                <td style=" text-align: center; vertical-align: middle;"> <%= String.format("%,d원", book.getBook_price()) %> </td>
                <td style=" text-align: center; vertical-align: middle;">
                    <button class="btn btn-danger" type="button" onclick="submitForm(<%= book.getBook_id() %>)">삭제</button>
                </td>
            </tr>  
        <%
                }
            } else {
        %>
            <tr>
                <td colspan="4">데이터가 없습니다.</td>
            </tr>
        <%
            }
        %>
            </tbody>
        </table>

        <div align="center" style="font-weight:bold; font-size:25px;">
        장바구니에 담긴 총 금액: <%= String.format("%,d원", total_price) %>
        </div>
        <div class="d-grid gap-2 col-6 mx-auto" style="margin-top:50px">
            <button id="purchaseButton" class="btn btn-primary pull-right type="button">결제하기</button>
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
</div>
</body>
</html>