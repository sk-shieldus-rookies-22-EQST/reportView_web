<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>

    <link rel="icon" type="image/png" href="images/icon_img/favicon.png">

    <title>BOOKIES</title>
    <style>
            .secret {
                display: flex;
                align-items: center;
                margin:0!important;
                background: none!important;
                border: none!important;
                padding:0!important;
            }
            .secret:after {display:block; clear:both; content:"";}
            .secret input[type="checkbox"] {
                display: none;
            }
            .secret input[type="checkbox"] + label {
                background: #fff;
                border: 1px solid #ff7a00;
                color: #ff7a00;
                cursor: pointer;
                border-radius: 3px;
                float: left;
                display: flex;
                align-items: center;
                margin: 0 5px;
                padding: 2px 5px;
            }
            .secret input[type="checkbox"]:checked + label {
                background: #FF7A00;
                border-color: #white;
                color: white;
                float: left
            }
            .secret input[type="checkbox"] + label span {
                font-size: 1rem;
            }
        </style>
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
                            <th colspan="2" style="background-color: #eeeeee; text-align: center;">게시글 수정</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr><td>
                            <div class="input-group">
                                  <input type="text" class="form-control" value="${qnaDetail.qna_title}" name="qna_title" maxlength="30" >
                                  <div class="secret form-label input-group-text">
                                      <input class="form-check-input mt-0" id="secret" type="checkbox" name="secret" value="1" ${qnaDetail.secret == true ? 'checked' : ''}>
                                      <label for="secret"><span>비밀글 설정</span></label>
                                  </div>
                                  <!-- 비밀글 체크박스를 선택하지 않으면 자동으로 '0'으로 설정 -->
                                  <input type="hidden" name="secret" value="0">
                            </div>
                            </td>
                        </tr>
                        <tr>
                            <td><textarea style="resize:none; width: 100%; height: 400px; border: none;" class="form-control" name="qna_body" maxlength="2048" >${qnaDetail.qna_body}</textarea></td>
                        </tr>
                        <tr>
                            <td><input type="file" name="qna_file" id="qna_file"></td>
                        </tr>
                    </tbody>
                </table>
                <div style="float:right;">
                    <a href="/qnaDetail?qna_id=${qnaDetail.qna_id}" class="btn btn-secondary pull-right">취소</a>
                    <input type="submit" class="btn btn-primary pull-right" value="글 수정" />
                </div>
            </form>

    </div>
</div>
</body>
</html>