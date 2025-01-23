<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
<title>게시글 상세</title>
</head>
<body>
	<div class="container" style="margin-bottom: 30px;">
		<%@ include file="banner.jsp" %>
	</div>

	<div class="container" style="max-width: 700px;margin-bottom:100px;border-radius: 5px;padding: 50px 20px;">
    	<p class="text-start fs-1 fw-bold" style="display: flex;justify-content: center; margin-bottom:30px;margin-top:16px">내 정보</p>
        <table class="table table-striped" style="text-align: center; border: 1px solid #dddddd">
            <thead>
                <tr>
                    <th colspan="3" style="background-color: #eeeeee; text-align: center;">게시판 글 보기 양식</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td style="width: 20%">글 제목</td>
                    <!-- qnaDetail에서 qna_title을 출력 -->
                    <td colspan="2">${qnaDetail.qna_title}</td>
                </tr>
                <tr>
                    <td>글 번호</td>
                    <!-- qna_id 출력 -->
                    <td colspan="2">${qnaDetail.qna_id}</td>
                </tr>
                <tr>
                    <td>작성자</td>
                    <!-- qna_user_id 출력 -->
                    <td colspan="2">${qnaDetail.qna_user_id}</td>
                </tr>
                <tr>
                    <td>작성일자</td>
                    <!-- qna_created_at 출력 -->
                    <td colspan="2">${qnaDetail.qna_created_at}</td>
                </tr>
                <tr>
                    <td>내용</td>
                    <!-- qna_body 출력 -->
                    <td colspan="2" style="min-height: 200px; text-align: Left;">${qnaDetail.qna_body}</td>
                </tr>
            </tbody>
        </table>

        <a href="qnaList" class="btn btn-primary pull-right">목록</a>
    </div>

</body>
</html>
