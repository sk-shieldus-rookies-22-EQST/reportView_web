<%@page import="finProj_flight.findResultDo"%>
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
	<jsp:useBean id="rdo" class="finProj_flight.findResultDo">
		<jsp:setProperty name="rdo" property="*"/>
	</jsp:useBean>
	<jsp:useBean id="rdao" class="finProj_flight.findResultDao" />
	<%
		String userid = (String)session.getAttribute("userid");
		rdao.modifymyInfo(userid, rdo);
		response.sendRedirect("myInfo.jsp");
	%>
</body>
</html>