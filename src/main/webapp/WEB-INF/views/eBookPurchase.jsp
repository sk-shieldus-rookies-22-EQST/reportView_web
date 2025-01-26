<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>

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
            int userPoint = (int) request.getAttribute("userPoint");
        %>
        보유 포인트:
        <%
        if (userPoint>1000) {
        %>
            <%=userPoint/1000 %>,<%= String.format("%03d", userPoint % 1000) %>원
        <%
            } else {
        %>
            <%=userPoint/1000 %>원
        <%
            }
        %>
        - 총 금액: <%= total_price/1000 %>,<%= String.format("%03d", total_price % 1000) %>원
        </div>
        <div class="d-grid gap-2 col-6 mx-auto" style="margin-top:30px">
            <button id="purchaseProc" type="button">결제하기</button>
        </div>
        <script>
            document.getElementById('purchaseProc').addEventListener('click', function () {
                var form = document.createElement('form');
                form.method = 'POST';
                form.action = '/purchaseProc';

                document.body.appendChild(form);
                form.submit();
            });
        </script>
    </div>
</div>

</body>
</html>