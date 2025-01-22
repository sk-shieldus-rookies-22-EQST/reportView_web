<%@page import="finProj_flight.findResultDo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>QnA 게시판</title>
</head>
<body>
<div class="container">
<%@ include file="banner.jsp" %>

<jsp:useBean id="adao" class="finProj_flight.findResultDao" />
<%
    String userid = (String)session.getAttribute("user_id");
    findResultDo ado = adao.getmyInfo(userid);
%>

<div class="container">
	<div class="container" style="max-width: 700px;margin-bottom:100px;border-radius: 5px;padding: 50px 20px;">
		<p class="text-start fs-1 fw-bold" style="display: flex;justify-content: center; margin-bottom:30px;margin-top:16px">내 정보</p>
		<div class="container">
            <form method="post" action="qnaWriteProcess">
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
                        <tf>
                            <td><textarea class="form-control" placeholder="글 내용" name="qna_body" maxlength="2048" style="height: 350px;"></textarea></td>
                        </tr>
                    </tbody>
                </table>
                    <input type="submit" class="btn btn-primary pull-right" value="글 작성"></a>
            </form>
                    <a href="qnaList.jsp" class="btn btn-primary pull-right">취소</a>
        </div>
    </div>
</body>
</html>
