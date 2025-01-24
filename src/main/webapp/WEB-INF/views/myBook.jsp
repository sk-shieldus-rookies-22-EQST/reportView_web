<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>

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
	<c:forEach var="book" items="${books_info}">
	<% for (int j = 0; j<((books_size/5)+1); j++) {%>
		<tr height="40%" align="center">
		<% for (int i = 0; i<5; i++) {%>
			<td width="20%" >
			    <div style="border:1px solid; margin: 10%;padding-top: 75%" class="image_container">
			        <img src="./test.jpg">
			    </div>

                    <p>Author: ${books_info['book_auth']}</p>
                    <p>Title: ${books_info.book_title}</p>

			</td>
		<%}%>
		</tr>
		<%}%>
    </c:forEach>
	</table>




</div>
</body>
</html>