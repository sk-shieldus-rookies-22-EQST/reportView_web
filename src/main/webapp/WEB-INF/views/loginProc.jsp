<%@page import="java.io.Console"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>Insert title here</title>
</head>
<body>
	<%request.setCharacterEncoding("utf-8");	//한글 안깨지게!!

	String user_id = request.getParameter("user_id");
	String user_pw = request.getParameter("user_pw");%>

	<jsp:useBean id="rdo" class="config.findResultDo">
        <jsp:setProperty name="rdo" property="*"/>
    </jsp:useBean>
    <jsp:useBean id="rdao" class="config.findResultDao" />
<%
	int warn = 0;

	try {
        String user_id = (String)session.getAttribute("user_id");
        String user_pw = (String)session.getAttribute("user_pw");
        Boolean not_user = false;
        rdao.user_login(userid, user_pw, not_user);
        if(not_user == false){
                session.setAttribute("user_id", userid);
                response.sendRedirect("index.jsp");
                break;
        } else {
            response.sendRedirect("loginForm.jsp?warn=1");
        }
	} catch (Exception e) {
		e.printStackTrace();
	}
	%>
</body>
</html>