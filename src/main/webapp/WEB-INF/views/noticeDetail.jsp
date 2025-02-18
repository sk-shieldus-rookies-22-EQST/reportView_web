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
    	<p class="text-start fs-1 fw-bold" style="display: flex;justify-content: center; margin-bottom:30px;margin-top:16px">공지사항</p>
        <table class="table table-striped" style="text-align: center; border: 1px solid #dddddd">
            <thead>
                <tr>
                    <th colspan="3" style="background-color: #eeeeee; text-align: center;">게시글 상세 내용 확인</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td style="width: 20%; text-align: center;">글 제목</td>
                    <td colspan="2" style="text-align: center;">
                        ${noticeDetail.notice_title}
                    </td>
                </tr>
                <tr>
                    <td>글 번호</td>
                    <!-- notice_id 출력 -->
                    <td colspan="2" style="text-align: center;">${noticeDetail.notice_id}</td>
                </tr>
                <tr>
                    <td>작성자</td>
                    <!-- notice_user_id 출력 -->
                    <td colspan="2" style="text-align: center;">${noticeDetail.notice_user_id}</td>
                </tr>
                <tr>
                    <td>작성일자</td>
                    <td colspan="2" style="text-align: center;">${noticeDetail.formattedCreatedAt}</td>
                </tr>
                <tr>
                    <td>내용</td>
                    <!-- notice_body 출력 -->
                    <td colspan="2" style="min-height: 200px; text-align: Left;" value="">${noticeDetail.notice_body}</td>
                </tr>
                <tr>
                    <td>파일</td>
                    <td colspan="2" style="text-align: center;">
                        <c:choose>
                            <c:when test="${not empty noticeDetail.file_name}">
                                <a href="javascript:void(0);" onclick="downloadFile('${noticeDetail.file_name}', '${noticeDetail.file_path}')" class="fw-bold">
                                    ${noticeDetail.file_name}
                                </a>

                                <script>
                                function downloadFile(fileName, filePath) {
                                    // 파일 경로의 백슬래시를 슬래시로 변환
                                    var normalizedPath = filePath.replace(/\\/g, '/');
                                    // URL 인코딩
                                    var encodedPath = encodeURIComponent(normalizedPath);
                                    var encodedName = encodeURIComponent(fileName);
                                    window.location.href = '/admin/download?file_name=' + encodedName + '&file_path=' + encodedPath;
                                }
                                </script>
                                <span class="text-muted" style="font-size: 0.85em;">
                                    (<c:choose>
                                        <c:when test="${noticeDetail.file_size >= 1024 * 1024}">
                                            <fmt:formatNumber value="${noticeDetail.file_size / (1024.0 * 1024.0)}" type="number" maxFractionDigits="2"/> MB
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:formatNumber value="${noticeDetail.file_size / 1024}" type="number" maxFractionDigits="0"/> KB
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
            <a href="noticeList" class="btn btn-primary pull-right">목록</a>

            <!-- 세션에서 user_id와 notice_user_id가 일치하면 수정/삭제 버튼을 표시 -->
                <a href="/admin/noticeEdit?notice_id=${noticeDetail.notice_id}" class="btn btn-primary pull-right">수정</a>
                <a href="/admin/noticeDelete?notice_id=${noticeDetail.notice_id}" class="btn btn-primary pull-right">삭제</a>
        </div>

    </div>


</body>
</html>
