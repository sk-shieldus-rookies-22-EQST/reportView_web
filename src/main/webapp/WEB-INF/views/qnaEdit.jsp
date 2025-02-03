<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>

    <link rel="icon" type="image/png" href="images/favicon.png">

    <title>BOOKIES</title>
</head>
<body>
<div class="container" style="margin-bottom: 30px;">
		<%@ include file="banner.jsp" %>
	</div>

	<div class="modal fade" id="errorModal" tabindex="-1" aria-labelledby="errorModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="errorModalLabel">오류</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body" id="errorMessage"></div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
                </div>
            </div>
        </div>
    </div>

    <c:if test="${not empty sessionScope.errorMessage}">
        <script type="text/javascript">
            // 모달에 메시지 설정
            document.getElementById("errorMessage").innerText = "${sessionScope.errorMessage}";

            // Bootstrap 5에서 모달 띄우기
            var myModal = new bootstrap.Modal(document.getElementById('errorModal'));
            myModal.show(); // 모달을 띄운다.

            // 세션에서 메시지 제거
            <c:remove var="errorMessage" scope="session" />
        </script>
    </c:if>

	<div class="container" style="max-width: 1200px;margin-bottom:100px;border-radius: 5px;padding: 50px 20px;">
		<p class="text-start fs-1 fw-bold" style="display: flex;justify-content: center; margin-bottom:30px;margin-top:16px">QnA 게시판</p>

            <form method="post" action="qnaUpdateProcess" enctype="multipart/form-data">
            <input type="hidden" name="qna_id" value="${qnaDetail.qna_id}">
                <table class="table table-striped" style="text-align: center; border: 1px solid #dddddd">
                    <thead>
                        <tr>
                            <th colspan="2" style="background-color: #eeeeee; text-align: center;">게시판 글 수정 양식</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td><input type="text" class="form-control" value="${qnaDetail.qna_title}"  name="qna_title" maxlength="50"></td>
                        </tr>
                        <label>
                            <input type="checkbox" name="secret" value="1" ${qnaDetail.secret == true ? 'checked' : ''}> 비밀글 설정
                        </label>
                        <!-- 체크박스가 해제되면 hidden 값을 전송하여 0으로 처리 -->
                        <input type="hidden" name="secret" value="0">
                        <tf>
                            <td><textarea class="form-control" name="qna_body" maxlength="2048" style="height: 350px;">${qnaDetail.qna_body}</textarea></td>
                        </tr>
                        <tr>
                            <td><input type="file" name="qna_file" id="qna_file"></td>
                        </tf>
                    </tbody>
                </table>
                    <input type="submit" class="btn btn-primary pull-right" value="글 수정"></a>
            </form>
                    <a href="qnaList" class="btn btn-primary pull-right">취소</a>
    </div>
</div>
</body>
</html>