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
                    <tf>
                        <td>1</td>
                        <td>제목입니당</td>
                        <td>심동진</td>
                        <td>2025-01-22</td>
                    </tf>
                </tbody>
            </table>
            <a href="qnaWrite.jsp" class="btn btn-primary pull-right">글 쓰기</a>
        </div>
    </div>
</body>
</html>
