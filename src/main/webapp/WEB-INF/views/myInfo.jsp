<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<div class="container">
<%@ include file="banner.jsp" %>
	<%
		String user_pw = (String) request.getAttribute("user_pw");
		String user_phone = (String) request.getAttribute("user_phone");
		String user_email = (String) request.getAttribute("user_email");
		String myInfoModifyForm = (String) request.getAttribute("myInfoModifyForm");
	%>

<div class="container">
	<div class="container" style="max-width: 700px;margin-bottom:100px;border-radius: 5px;padding: 50px 20px;">
		<p class="text-start fs-1 fw-bold" style="display: flex;justify-content: center; margin-bottom:30px;margin-top:16px">내 정보</p>

		<form action="myInfoSave" method="post">
	<table border="1"  style="width: 100%">
		<tr height="60" align="center">
			<td width="40%" style="border-right:1px solid #212529"> 아이디 </td>
			<td width="100%">
                    <%= user_id %>
			</td>
		</tr>
		<tr height="60" align="center">
			<td width="40%" style="border-right:1px solid #212529"> 비밀번호 </td>
			<td width="100%" id="user_pw">
			    <%if (myInfoModifyForm == "0") {%>
                    <%= user_pw %>
                <%}else if (myInfoModifyForm == "1"){%>
                    <input type="text" name="user_pw" id="user_pw" value="<%= user_pw %>" required>
                <%}%>
			</td>
		</tr>
		<tr height="60" align="center">
			<td width="40%" style="border-right:1px solid #212529"> 전화번호 </td>
			<td width="100%"> 
				<%if (myInfoModifyForm == "0") {%>
                    <%= user_phone %>
                <%}else if (myInfoModifyForm == "1"){%>
                    <input type="text" name="user_phone" id="user_phone" value="<%= user_phone %>" required>
                <%}%>
			</td>
		</tr>
		<tr height="60" align="center">
            <td width="40%" style="border-right:1px solid #212529"> 이메일 </td>
            <td width="100%">
                <%if (myInfoModifyForm == "0") {%>
                    <%= user_email %>
                <%}else if (myInfoModifyForm == "1"){%>
                    <input type="text" name="user_email" id="user_email" value="<%= user_email %>" required>
                <%}%>
            </td>
        </tr>
		<tr height="60" align="center">
			<td width="40%" style="border-right:1px solid #212529"> 개인정보 활용 동의 </td>
			<td width="100%">동의함</td>
		</tr>
		</table>
		<div class="d-grid gap-2 col-6 mx-auto" style="margin-top:30px">
		    <%if (myInfoModifyForm == "0") {%>
                <button class="btn btn-primary" type="button" onclick="location.href='/myInfoModify'">수정하기</button>
            <%}else if (myInfoModifyForm == "1"){%>
                <button class="btn btn-primary" type="submit">저장하기</button>
            <%}%>
			  <button class="btn btn-outline-primary" type="button" onclick="location.href='/index'">홈으로</button>
			</div>
	
	</form>


</div>
</body>
</html>