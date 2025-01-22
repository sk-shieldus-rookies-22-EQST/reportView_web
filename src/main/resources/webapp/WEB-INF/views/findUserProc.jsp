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
<% request.setCharacterEncoding("utf-8");
String whatFind = request.getParameter("whatFind");
    if (whatFind == "pw") {
        String user_id = request.getParameter("user_id");
    }
    String user_phone = request.getParameter("user_phone");
    String user_email = request.getParameter("user_email");

%>
	<jsp:useBean id="rdo" class="config.findResultDo">
		<jsp:setProperty name="rdo" property="*"/>
	</jsp:useBean>
	<jsp:useBean id="rdao" class="config.findResultDao" />
<%
	    if (whatFind == 'id') {
	        String findId = rdao.findUser_id(user_phone, user_email);
%>

        <jsp:forward page="findUserid.jsp">
            <jsp:param name="findId" value="<%=findId%>"/>
        </jsp:forward>


<%
	    } else if (whatFind == 'pw') {
            rdao.findUserpw(user_id, user_phone, user_email);
            response.sendRedirect("findUserpw.jsp?foundPw="+user_pw);
        }

	%>
</body>
</html>