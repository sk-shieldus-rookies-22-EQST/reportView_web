<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
<link rel="icon" type="image/png" href="images/icon_img/favicon.png">
<title>BOOKIES</title>
<style>
.form-control:focus {
    box-shadow: none!important;
    border-color : #FF7A00!important;
}
</style>
</head>
<body>


<div class="modal fade" id="messagedeleteReplyModal" tabindex="-1" aria-labelledby="messagedeleteReplyModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="messagedeleteReplyModalLabel">알림</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body" id="messagedeleteReply"></div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
                </div>
            </div>
        </div>
    </div>

    <c:if test="${not empty sessionScope.messagedeleteReply}">
        <script type="text/javascript">
            // 모달에 메시지 설정
            document.getElementById("messagedeleteReply").innerText = "${sessionScope.messagedeleteReply}";

            // Bootstrap 5에서 모달 띄우기
            var myModal = new bootstrap.Modal(document.getElementById('messagedeleteReplyModal'));
            myModal.show(); // 모달을 띄운다.

            // 세션에서 메시지 제거
            <c:remove var="messagedeleteReply" scope="session" />
        </script>
    </c:if>

	<div class="container" style="margin-bottom: 30px;">
		<%@ include file="banner.jsp" %>
	</div>



	<div class="container" style="max-width: 1200px;margin-bottom:100px;border-radius: 5px;padding: 50px 20px;">
    	<p class="text-start fs-1 fw-bold" style="display: flex;justify-content: center; margin-bottom:30px;margin-top:16px">QnA 게시판</p>
        <table class="table table-striped" style="text-align: center; border: 1px solid #dddddd">
            <thead>
                <tr>
                    <th colspan="3" style="background-color: #eeeeee; text-align: center;">게시판 글 보기 양식</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td style="width: 20%; text-align: center;">글 제목</td>
                    <td colspan="2" style="text-align: center;">
                        <c:choose>
                            <c:when test="${qnaDetail.secret == true}">
                                <span>🔒</span>
                            </c:when>
                            <c:otherwise>

                            </c:otherwise>
                        </c:choose>
                        ${qnaDetail.qna_title}
                    </td>
                </tr>
                <tr>
                    <td>글 번호</td>
                    <!-- qna_id 출력 -->
                    <td colspan="2" style="text-align: center;">${qnaDetail.qna_id}</td>
                </tr>
                <tr>
                    <td>작성자</td>
                    <!-- qna_user_id 출력 -->
                    <td colspan="2" style="text-align: center;">${qnaDetail.qna_user_id}</td>
                </tr>
                <tr>
                    <td>작성일자</td>
                    <td colspan="2" style="text-align: center;">${qnaDetail.formattedCreatedAt}</td>
                </tr>
                <tr>
                    <td>내용</td>
                    <!-- qna_body 출력 -->
                    <td colspan="2" style="min-height: 200px; text-align: Left;" >${qnaDetail.qna_body}</td>
                </tr>
                <tr>
                    <td>파일</td>
                    <td colspan="2" style="text-align: center;">
                        <c:choose>
                            <c:when test="${not empty qnaDetail.file_name}">
                                <a href="javascript:void(0);" onclick="downloadFile('${qnaDetail.file_name}', '${qnaDetail.file_path}')" class="fw-bold">
                                    ${qnaDetail.file_name}
                                </a>

                                <script>
                                function downloadFile(fileName, filePath) {
                                    // 파일 경로의 백슬래시를 슬래시로 변환
                                    var normalizedPath = filePath.replace(/\\/g, '/');
                                    // URL 인코딩
                                    var encodedPath = encodeURIComponent(normalizedPath);
                                    var encodedName = encodeURIComponent(fileName);
                                    window.location.href = '/download?file_name=' + encodedName + '&file_path=' + encodedPath;
                                }
                                </script>
                                <span class="text-muted" style="font-size: 0.85em;">
                                    (<c:choose>
                                        <c:when test="${qnaDetail.file_size >= 1024 * 1024}">
                                            <fmt:formatNumber value="${qnaDetail.file_size / (1024.0 * 1024.0)}" type="number" maxFractionDigits="2"/> MB
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:formatNumber value="${qnaDetail.file_size / 1024}" type="number" maxFractionDigits="0"/> KB
                                        </c:otherwise>
                                    </c:choose>)
                                </span>
                            </c:when>
                            <c:otherwise>
                                <!-- 파일이 없을 때 칸만 남기기 -->
                                &nbsp;
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>

            </tbody>
        </table>
        <div style="float:right;">
            <a href="qnaList" class="btn btn-primary pull-right">목록</a>

            <!-- 세션에서 user_id와 qna_user_id가 일치하면 수정/삭제 버튼을 표시 -->
            <c:if test="${sessionScope.user_id == qnaDetail.qna_user_id or sessionScope.user_level == 123}">
                <a href="qnaEdit?qna_id=${qnaDetail.qna_id}" class="btn btn-primary pull-right">수정</a>
                <a href="qnaDelete?qna_id=${qnaDetail.qna_id}" class="btn btn-primary pull-right">삭제</a>
            </c:if>
        </div>

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
                        <c:choose>
                            <c:when test="${not empty qnaReplies}">
                                <c:forEach var="reply" items="${qnaReplies}">
                                    <tr>
                                        <td style="align-content:center;">관리자</td>
                                        <td style="align-content:center;">${reply.qna_re_body}</td>
                                        <td style="align-content:center;">${reply.formattedCreatedAt}</td>
                                        <c:if test="${sessionScope.user_level == 123}">
                                            <td style="align-content:center;">
                                                <a href="qnaReplyDelete?qna_re_id=${reply.qna_re_id}&qna_id=${qnaDetail.qna_id}" class="text-danger" style="font-size: 20px;text-decoration:none;">X</a>
                                            </td>
                                        </c:if>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td colspan="4" style="text-align:center;">아직 등록된 답글이 없습니다.</td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
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
                            <textarea style="resize:none" maxlength="200" name="qna_re_body" class="form-control" rows="4" placeholder="답글을 작성하세요." required></textarea>
                        </div>
                        <button type="submit" class="btn btn-primary">답글 달기</button>
                    </form>
                </div>
            </c:if>
        </div>



</body>
</html>
