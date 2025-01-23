<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
<title>QnA 게시판</title>
</head>
<body>
<div class="container">
<%@ include file="banner.jsp" %>

	<div class="container" style="max-width: 700px;margin-bottom:100px;border-radius: 5px;padding: 50px 20px;">
		<p class="text-start fs-1 fw-bold" style="display: flex;justify-content: center; margin-bottom:30px;margin-top:16px">내 정보</p>
            <table class="table table-striped" style="text-align: center; border: 1px solid #dddddd">
                <thead>
                    <tr>
                        <th style="background-color: #eeeeee; text-align: center;">번호</th>
                        <th style="background-color: #eeeeee; text-align: center;">제목</th>
                        <th style="background-color: #eeeeee; text-align: center;">작성자</th>
                        <th style="background-color: #eeeeee; text-align: center;">날짜</th>
                    </tr>
                </thead>
                <tbody>
                    <c:if test"${empty qnaList}">
                        <p>데이터가 없습니다.</p>
                    </c:if>
                    <c:forEach var="qna" items="${qnaList}">
                        <tr>
                            <td>${qna.qna_id}</td>
                            <td>${qna.qna_title}</td>
                            <td>${qna.qna_user_id}</td>
                            <td>${qna.qna_created_at}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <a href="qnaWrite" class="btn btn-primary pull-right">글 쓰기</a>
    </div>
</div>
</body>
</html>