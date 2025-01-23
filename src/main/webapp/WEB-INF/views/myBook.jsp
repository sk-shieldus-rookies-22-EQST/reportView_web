<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>

<title>Insert title here</title>
</head>
<body>
<div class="container">
<%@ include file="banner.jsp" %>
	<%
		String user_id = (String) session.getAttribute("iser_id");
	%>

<div class="container">
	<div class="container" style="max-width: 700px;margin-bottom:100px;border-radius: 5px;padding: 50px 20px;">
		<p class="text-start fs-1 fw-bold" style="display: flex;justify-content: center; margin-bottom:30px;margin-top:16px">내 정보</p>


	<table border="1"  style="width: 100%">
		<tr height="60" align="center">
			<td width="40%" style="border-right:1px solid #212529"> 아이디 </td>
			<td width="100%">
                    <%= user_id %>
			</td>
		</tr>
		<tr height="60" align="center">
			<td width="40%" style="border-right:1px solid #212529"> 비밀번호 </td>
		</tr>
		<tr height="60" align="center">
			<td width="40%" style="border-right:1px solid #212529"> 전화번호 </td>
		</tr>
		<tr height="60" align="center">
            <td width="40%" style="border-right:1px solid #212529"> 이메일 </td>
        </tr>
		<tr height="60" align="center">
			<td width="40%" style="border-right:1px solid #212529"> 개인정보 활용 동의 </td>
		</tr>
		</table>




</div>
</body>
</html>