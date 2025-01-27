<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>

</head>
<body>



<div class="container">
    <jsp:include page="banner.jsp" />

    <div class="container" style="max-width: 1200px; margin-bottom: 100px; border-radius: 5px; padding: 50px 20px;">
        <p class="text-start fs-1 fw-bold" style="display: flex; justify-content: center; margin-bottom: 30px; margin-top: 16px">결제 정보</p>

        <table class="table" style="text-align: center; border: 1px solid #dddddd">
            <thead>
                    <tr>
                        <th style="background-color: #eeeeee; text-align: center;">도서 이미지</th>
                        <th style="background-color: #eeeeee; text-align: center;">제목</th>
                        <th style="background-color: #eeeeee; text-align: center;">가격</th>
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
                <td> <img src="<%= book.getBook_img_path() %>"</td>
                <td align="left"> <%= book.getBook_title() %> </td>
                <td> <%= book.getBook_price()/1000 %>,<%= String.format("%03d", book.getBook_price() % 1000) %>원 </td>
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
        <div align="center" style="font-weight:bold; font-size:25px;">
        <%
            int userPoint = (int) session.getAttribute("point");
        %>
        보유 포인트: <%= String.format("%,d", userPoint) %>
        - 총 금액: <%= String.format("%,d원", total_price) %>
        </div>
        <div class="d-grid gap-2 col-6 mx-auto" style="margin-top:30px">
            <button id="purchaseProc" type="button">결제하기</button>
        </div>
        <script>
            var purchaseUrl = "${purchaseUrl}";

            document.getElementById('purchaseProc').addEventListener('click', function () {
                var form = document.createElement('form');
                form.method = 'POST';
                form.action = purchaseUrl;

                if (purchaseUrl === "/purchaseItemProc") {
                <%
                    if (bookList != null && !bookList.isEmpty()) {
                        BookDto book = bookList.get(0); // 첫 번째 책을 선택
                %>
                    var bookIdInput = document.createElement('input');
                    bookIdInput.type = 'hidden';
                    bookIdInput.name = 'book_id';
                    bookIdInput.value = '<%= book.getBook_id() %>';
                    form.appendChild(bookIdInput);
                <%
                    }
                %>
                }

                var total_book_price = document.createElement('input');
                total_book_price.type = 'hidden';
                total_book_price.name = 'total_book_price';
                total_book_price.value = <%=total_price%>; // 보내고자 하는 데이터

                form.appendChild(total_book_price);

                document.body.appendChild(form);
                form.submit();
            });
        </script>
    </div>
</div>
</body>
</html>