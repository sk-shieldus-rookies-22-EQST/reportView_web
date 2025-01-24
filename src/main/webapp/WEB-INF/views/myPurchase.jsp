<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <title>MyPurchase</title>
</head>
<body>
<div class="container">
    <jsp:include page="banner.jsp" />

    <div class="container" style="max-width: 1200px; margin-bottom: 100px; border-radius: 5px; padding: 50px 20px;">
        <p class="text-start fs-1 fw-bold" style="display: flex; justify-content: center; margin-bottom: 30px; margin-top: 16px">전체 도서 목록</p>

        <table class="table table-striped" style="text-align: center; border: 1px solid #dddddd">
            <thead>
            <tr>
                <th style="background-color: #eeeeee; text-align: center;">도서 제목</th>
                <th style="background-color: #eeeeee; text-align: center;">작가</th>
                <th style="background-color: #eeeeee; text-align: center;">가격</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="book" items="${books}">
                <tr onclick="location.href='/eBookDetail?book_id=${book['book_id']}'">
                    <td>
                        <c:choose>
                            <c:when test="${book['book_img_path'] != null}">
                                <img src="${book['book_img_path']}" alt="Book Image" style="width: 80px; height: 80px; object-fit: cover; border-radius: 8px;">
                            </c:when>
                            <c:otherwise>
                                <img src="/images/no-image.png" alt="No Image Available" style="width: 80px; height: 80px; object-fit: cover; border-radius: 8px;">
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>${book['book_auth']}</td>
                    <td>${book['book_price']}원</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <nav class="mt-4" style="display: flex; justify-content: center;">
            <ul class="pagination">
                <c:if test="${currentPage > 1}">
                    <li class="page-item">
                        <a class="page-link" href="/index?page=${currentPage - 1}">이전</a>
                    </li>
                </c:if>

                <c:forEach begin="${startPage}" end="${endPage}" var="i">
                    <li class="page-item <c:if test='${i == currentPage}'>active</c:if>">
                        <a class="page-link" href="/index?page=${i}">${i}</a>
                    </li>
                </c:forEach>

                <c:if test="${currentPage < totalPages}">
                    <li class="page-item">
                        <a class="page-link" href="/index?page=${currentPage + 1}">다음</a>
                    </li>
                </c:if>
            </ul>
        </nav>
    </div>
</div>
</body>
</html>
