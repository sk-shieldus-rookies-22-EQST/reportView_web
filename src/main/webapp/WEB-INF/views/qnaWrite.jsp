<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>

    <link rel="icon" type="image/png" href="images/favicon.png">

    <title>QnA 작성</title>
</head>
<body>
<div class="container">
<%@ include file="banner.jsp" %>

    <c:if test="${not empty message}">
        <script>
            alert("${message}");  // 경고창 띄우기
        </script>
    </c:if>
    <div class="container" style="max-width: 1200px;margin-bottom:100px;border-radius: 5px;padding: 50px 20px;">
        <p class="text-start fs-1 fw-bold" style="display: flex;justify-content: center; margin-bottom:30px;margin-top:16px">QNA 게시판</p>

        <form method="post" action="qnaWriteProcess" enctype="multipart/form-data">
            <!-- 세션에서 user_id 값을 바로 사용 -->
            <input type="hidden" name="qna_user_id" value="${sessionScope.user_id}" />
            <table class="table table-striped" style="text-align: center; border: 1px solid #dddddd">
                <thead>
                    <tr>
                        <th colspan="2" style="background-color: #eeeeee; text-align: center;">게시판 글쓰기 양식</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td><input type="text" class="form-control" placeholder="글 제목" name="qna_title" maxlength="50"></td>
                    </tr>
                    <tr>
                        <td><textarea class="form-control" placeholder="글 내용" name="qna_body" maxlength="2048" style="height: 350px;"></textarea></td>
                    </tr>
                    <tr>
                        <td><input type="file" name="qna_file" id="qna_file"></td>
                    </tr>
                </tbody>
            </table>
            <input type="submit" class="btn btn-primary pull-right" value="글 작성" />
        </form>
        <a href="qnaList" class="btn btn-primary pull-right">취소</a>
    </div>
</div>
</body>
</html>
