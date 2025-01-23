<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <title>eBookMain</title>
    <style>
        .pagination {
            display: flex;
            justify-content: center;
            list-style-type: none;
            padding: 0;
        }

        .pagination li {
            margin: 0 5px;
        }

        .pagination li a {
            text-decoration: none;
            padding: 5px 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            color: #007bff;
        }

        .pagination li a:hover {
            background-color: #f1f1f1;
        }

        .pagination li.active a {
            background-color: #007bff;
            color: white;
            border-color: #007bff;
        }
    </style>
</head>
<body>
<div class="container">
<!-- 배너 영역 -->
<jsp:include page="banner.jsp" />

<h1>전체 도서 목록 (페이징)</h1>

<table border="1" style="border-collapse: collapse; width: 100%; text-align: center;">
    <thead>
    <tr>
        <th>책 ID</th>
        <th>제목</th>
        <th>저자</th>
        <th>경로</th>
        <th>요약</th>
        <th>등록일</th>
        <th>이미지 경로</th>
    </tr>
    </thead>
    <tbody>
    <!-- 책 목록 출력 -->
    <c:forEach var="book" items="${books}">
        <tr>
            <td>${book['book_id']}</td>
            <td onclick="location.href='/eBookDetail?book_id=${book['book_id']}'">${book['book_title']}</td>
            <td>${book['book_auth']}</td>
            <td>${book['book_path']}</td>
            <td>${book['book_summary']}</td>
            <td>${book['book_reg_date']}</td>
            <td>${book['book_img_path']}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<!-- 페이지네이션 -->
<nav>
    <ul class="pagination">
        <!-- 이전 버튼 -->
        <c:if test="${currentPage > 1}">
            <li>
                <a href="/index?page=${currentPage - 1}">이전</a>
            </li>
        </c:if>

        <!-- 페이지 번호 -->
        <c:forEach begin="1" end="${totalPages}" var="i">
            <li class="<c:if test='${i == currentPage}'>active</c:if>">
                <a href="/index?page=${i}">${i}</a>
            </li>
        </c:forEach>

        <!-- 다음 버튼 -->
        <c:if test="${currentPage < totalPages}">
            <li>
                <a href="/index?page=${currentPage + 1}">다음</a>
            </li>
        </c:if>
    </ul>
</nav>
</div>
</body>
</html>
