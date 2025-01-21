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
	<%
	request.setCharacterEncoding("utf-8");	//한글 안깨지게!!


	String userid = request.getParameter("user_id");
	String userpw = request.getParameter("user_pw");

	//JDBC를 이용한 DB 연동    -    DB 정보 하드코딩되어있어 수정 필요
	String id = "root";
	String password = "111111";
	String url = "jdbc:mysql://localhost:3306/jspdb_3315?characterEncoding=utf-8";

	int warn = 0;

	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	try {
		//1. 드라이버 로딩
		Class.forName("com.mysql.jdbc.Driver");

		//2. DB 연결
		conn = DriverManager.getConnection(url,id,password);

		//3. SQL문 완성
		String sql = "select * from membership";
		pstmt = conn.prepareStatement(sql);

		//4. SQL 실행
		rs = pstmt.executeQuery();
		int i = 1;
		while(rs.next()){
			if(rs.getString(1).equals(userid)){
				if(rs.getString(2).equals(userpw)){
					session.setAttribute("user_name", rs.getString(3));
					session.setAttribute("user_id", userid);
					conn.close();
					pstmt.close();
					rs.close();
					response.sendRedirect("index.jsp");
					break;
				} else {
					warn = 1;
				}
			} else {
				warn = 1;
			}

			i++;
		}
		if(warn == 1){
			response.sendRedirect("loginFormBoot.jsp?warn=1");
		}

	} catch (Exception e) {
		e.printStackTrace();
	}
	%>
</body>
</html>