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
<title>BOOKIES</title>
<style>
.form-control:focus {
    box-shadow: none!important;
    border-color : #FF7A00!important;
}
</style>
</head>
<body>
	<div class="container" style="margin-bottom: 30px;">
		<%@ include file="banner.jsp" %>
	</div>

	<div class="container" style="max-width: 1200px;margin-bottom:100px;border-radius: 5px;padding: 50px 20px;">
    	<p class="text-start fs-1 fw-bold" style="display: flex;justify-content: center; margin-bottom:30px;margin-top:16px">QNA 게시판</p>
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

        <!-- 세션에서 user_id와 qna_user_id가 일치하면 수정/삭제 버튼을 표시 -->
        <c:if test="${sessionScope.user_id == qnaDetail.qna_user_id or sessionScope.user_level == 123}">
            <a href="qnaEdit?qna_id=${qnaDetail.qna_id}" class="btn btn-primary pull-right">수정</a>
            <a href="qnaDelete?qna_id=${qnaDetail.qna_id}" class="btn btn-primary pull-right">삭제</a>
        </c:if>

    </div>

    <!-- 답글 목록 -->
    <div class="container" style="max-width: 1200px;">
            <div class="mt-5">
                <h5>답글 목록</h5>
                <table class="table table-bordered" style="text-align:center;">
                    <thead>
                        <tr>
                            <th>작성자</th>
                            <th style="width: 700px;">내용</th> <!-- 고정된 너비 설정 -->
                            <th style="width: 200px;">작성일자</th>
                            <c:if test="${sessionScope.user_level == 123}">
                                <th style="width: 80px;">삭제</th>
                            </c:if>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="reply" items="${qnaReplies}">
                            <tr>
                                <td style="align-content:center;">관리자</td>
                                <td style="align-content:center;">${reply.qna_re_body}</td>
                                <td style="align-content:center;">${reply.qna_re_created_at}</td>
                                <c:if test="${sessionScope.user_level == 123}">
                                    <td style="align-content:center;">
                                        <a href="qnaReplyDelete?qna_re_id=${reply.qna_re_id}&qna_id=${qnaDetail.qna_id}" class="text-danger" style="font-size: 20px;text-decoration:none;">X</a>
                                    </td>
                                </c:if>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

            </div>

            <!-- 답글 작성 폼 -->
            <c:if test="${sessionScope.user_level == 123}">
                <div class="mt-4">
                    <h5>답글 작성</h5>
                    <form action="qnaReplyProcess" method="post">
                        <input type="hidden" name="qna_id" value="${qnaDetail.qna_id}">
                        <div class="mb-3">
                            <textarea name="qna_re_body" class="form-control" rows="4" placeholder="답글을 작성하세요." required></textarea>
                        </div>
                        <button type="submit" class="btn btn-primary">답글 달기</button>
                    </form>
                </div>
            </c:if>
        </div>



</body>
</html>
