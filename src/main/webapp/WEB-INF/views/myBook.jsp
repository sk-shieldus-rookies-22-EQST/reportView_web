<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>
<title>Insert title here</title>
<style>

.image_container img {
  max-width: 100%; /* 이미지를 프레임에 맞게 크기 조절 */
  max-height: 100%; /* 이미지를 프레임에 맞게 크기 조절 */
  object-fit: contain; /* 이미지 비율 유지 */
}
</style>
</head>
<body>
<div class="container">
<%@ include file="banner.jsp" %>
<div class="container">
	<div class="container" style="max-width: 1200px;margin-bottom:100px;border-radius: 5px;padding: 50px 20px;">
		<p class="text-start fs-1 fw-bold" style="display: flex;justify-content: center; margin-bottom:30px;margin-top:16px">내 서재</p>


	<table style="width: 100%">
	<% Integer books_size = (Integer) request.getAttribute("books_size");
	    //List<BookDto> books_info = request.getAttribute("books_info");
	%>
	<div class="container text-center">
        <div class="row">
            <c:forEach var="bookList" items="${books_info}">
                <c:forEach var="book" items="${bookList}" varStatus="status">
                    <!-- 각 col은 고정된 width로 5개가 들어감 -->
                    <div class="col-2">
                        <div style="border:1px solid; margin: 10%;padding-top: 75%; width:184px;">
                            img src="./test.jpg">
                            <p><strong>Title: </strong>${book['book_title']}</p>
                            <p><strong>Author: </strong>${book['book_auth']}</p>
                        </div>

                    </div>

                    <!-- 5개마다 새로운 row를 시작 -->
                    <c:if test="${(status.index + 1) % 5 == 0}">
                        </div><div class="row">
                    </c:if>
                </c:forEach>
            </c:forEach>
        </div>
    </div>

    </table>
</div>
</body>
</html>