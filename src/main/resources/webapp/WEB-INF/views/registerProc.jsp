<%@page import="java.sql.SQLException"%>
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
<% request.setCharacterEncoding("utf-8"); %>
	<jsp:useBean id="rdo" class="config.findResultDo">
		<jsp:setProperty name="rdo" property="*"/>
	</jsp:useBean>
	<jsp:useBean id="rdao" class="config.findResultDao" />
	<%
		int warn = 0;
		try {
			if(rdo.getUser_id().equals("")){
				throw new ArithmeticException();
			} else if(rdo.getUser_pw().equals("")){
				throw new ArithmeticException();
			} else if(rdo.getUser_agree.equals("")) {
			    throw new NullPointerException();
			} else {
				if(rdao.findAtom(1,rdo.getUser_id())){
					throw new Exception();
				}else{
					rdao.insertRegister(rdo);
					session.setAttribute("user_id", rdo.getUser_id());
					response.sendRedirect("index.jsp");
				}
			}
		} catch (ArithmeticException e){
			response.sendRedirect("registerForm.jsp?none=1");
		} catch (NullPointerException e) {
			response.sendRedirect("registerForm.jsp?agree=1");
		} catch(Exception e){
			response.sendRedirect("registerForm.jsp?warn=1");
		}
	%>
</body>
</html>