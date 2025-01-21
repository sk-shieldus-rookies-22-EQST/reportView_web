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
	<jsp:useBean id="rdo" class="finProj_flight.findResultDo">
		<jsp:setProperty name="rdo" property="*"/>
	</jsp:useBean>
	<jsp:useBean id="rdao" class="finProj_flight.findResultDao" />
	<%
		int warn = 0;
		try {
			if(rdo.getUserid().equals("")){
				throw new ArithmeticException();
			} else if(rdo.getUserpw().equals("")){
				throw new ArithmeticException();
			} else {
				if(rdao.findAtom(1,rdo.getUserid())){
					throw new Exception();
				}else{
					rdao.insertRegister(rdo);
					session.setAttribute("username", rdo.getUsername());
					session.setAttribute("userid", rdo.getUserid());
					response.sendRedirect("main.jsp");
				}
			}
		} catch (ArithmeticException e){
			response.sendRedirect("registerFormBoot.jsp?none=1");
		} catch(Exception e){
			response.sendRedirect("registerFormBoot.jsp?warn=1");
		}
	%>
</body>
</html>