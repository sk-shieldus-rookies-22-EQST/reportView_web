<%@page import="config.findResultDo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<% request.setCharacterEncoding("utf-8"); %>
	<jsp:useBean id="rdo" class="config.findResultDo">
		<jsp:setProperty name="rdo" property="*"/>
	</jsp:useBean>
	<jsp:useBean id="rdao" class="config.findResultDao" />
	<%
		String userid = (String)session.getAttribute("user_id");
		rdao.modifymyInfo(userid, rdo);
		response.sendRedirect("myInfo.jsp");
	%>
</body>
</html>