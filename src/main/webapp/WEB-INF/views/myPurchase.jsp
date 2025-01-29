<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>

    <link rel="icon" type="image/png" href="images/favicon.png">

    <title>myPurchase</title>

    <script>
        var message = "${messageMypurchase}";
        if (message) {
            window.alert(message);
        }
    </script>
    </head>
</head>
<body>
<div class="container">
    <jsp:include page="banner.jsp" />

    <div class="container" style="max-width: 1200px; margin-bottom: 100px; border-radius: 5px; padding: 50px 20px;">
        <p class="text-start fs-1 fw-bold" style="display: flex; justify-content: center; margin-bottom: 30px; margin-top: 16px">구매 도서 목록</p>

        <table class="table" style="text-align: center; border: 1px solid #dddddd">
            <thead>
            <tr>
                <th style="background-color: #eeeeee; text-align: center;">제목</th>
                <th style="background-color: #eeeeee; text-align: center;">작가</th>
                <th style="background-color: #eeeeee; text-align: center;">가격</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="bookList" items="${books}">
                <c:forEach var="book" items="${bookList}" varStatus="status">
                <tr onclick="location.href='/eBookDetail?book_id=${book['book_id']}'">
                    <td style="width:700px; text-align: center; vertical-align: middle;">
                        <p style="margin:0;white-space: nowrap;overflow:hidden;width:700px;text-overflow:ellipsis;">${book['book_title']}</p>
                    </td>
                    <td>${book['book_auth']}</td>
                    <td>${book['book_price']}원</td>
                </tr>
                </c:forEach>
            </c:forEach>
            </tbody>
        </table>

    </div>
</div>
</body>
</html>
