<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
<%@ include file="banner.jsp" %>
<div class="container">
	<div class="container" style="max-width: 1200px;margin-bottom:100px;border-radius: 5px;padding: 50px 20px;">
		<p class="text-start fs-1 fw-bold" style="display: flex;justify-content: center; margin-bottom:30px;margin-top:16px">내 서재</p>

        <script>
            // 클릭 시 실행될 함수 정의
            function BookiesDRM(book_id) {
                var isPopupAppeared = false;
                window.addEventListener("blur", function () {
                    isPopupAppeared = true;
                });
                window.location.href = 'BookiesDRM://run?user_id=<%=user_id%>&book_id='+ book_id;
                setTimeout(function () {
                    if (!isPopupAppeared) {
                        window.location.href = 'https://example.com/설치페이지';
                    }
                }, 2000);
            }
        </script>


	<% Integer books_size = (Integer) request.getAttribute("books_size");
	    //List<BookDto> books_info = request.getAttribute("books_info");
	%>
	<div class="container text-center">
        <div class="row  row-cols-5" style="width=100%">
            <c:forEach var="book" items="${books_info}">


                <div id="BookiesDRM${book['book_id']}" style="cursor:pointer;display: flex;align-items: center;flex-direction: column;margin-bottom:5%;justify-content: flex-end" onclick="BookiesDRM(${book['book_id']})">

                    <div style="border:1px solid; margin: 0% 10%;width:184px;">
                        <img class="image_container" src="${book['book_img_path']}" style="position: relative;width: 100%;overflow: hidden;">

                    </div>
                    <div style="margin-bottom:16px; display:flex;flex-direction: column;">
                        <div style=" height:100px;display: flex;flex-direction: column;justify-content: flex-start;align-items: center;">

                            <p class="fs-5 fw-semibold" style="margin:0;width:184px;display: -webkit-box;-webkit-line-clamp: 2;-webkit-box-orient: vertical;overflow: hidden;text-overflow: ellipsis;">${book['book_title']}</p>

                            <p class="text-secondary fs-6" style="margin:0;">${book['book_auth']}</p>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>
</body>
</html>