<%@page import="java.time.LocalDate"%>
<%@page import="finProj_flight.findResultDo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
.topic {
	border-right : 1px solid #212529!important;
}
</style>
</head>
<body>
<%
	Cookie[] cookies = request.getCookies();
	String goflightno = "";
	String goflightdate = "";
	String goflightairline = "";
	String backflightno = "";
	String backflightdate = "";
	String backflightairline = "";
	if (cookies != null) {
		for(int i=0; i< cookies.length; i++){
			if(cookies[i].getName().equals("goflightno")){
				goflightno = cookies[i].getValue();
				continue;
			} else if(cookies[i].getName().equals("goflightdate")){
				goflightdate = cookies[i].getValue();
				continue;
			} else if(cookies[i].getName().equals("goflightairline")){
				goflightairline = cookies[i].getValue();
				continue;
			} else if(cookies[i].getName().equals("backflightno")){
				backflightno = cookies[i].getValue();
				continue;
			} else if(cookies[i].getName().equals("backflightdate")){
				backflightdate = cookies[i].getValue();
				continue;
			} else if(cookies[i].getName().equals("backflightairline")){
				backflightairline = cookies[i].getValue();
				continue;
			}
		}
	}
%>
<jsp:useBean id="rdo" class="finProj_flight.findResultDo">
	<jsp:setProperty name="rdo" property="*"/>
</jsp:useBean>
<jsp:useBean id="rdao" class="finProj_flight.findResultDao" />
<div class="container">
		<%@ include file="nav.jsp" %>
<div cass="container">
	<div class="container" style="max-width: 800px;margin-bottom:200px;border-radius: 5px;padding: 50px 20px;">
		<p class="text-start fs-1 fw-bold" style="display: flex;justify-content: center; margin-bottom:30px;margin-top:16px">예약되었습니다</p>
		
		<form action="loginProcBoot.jsp" method="post">
			<div class="mb-3">
			<table>
				<tr>
				<td style="text-align:center"><label for="exampleInputEmail1" class="form-label fw-bold fs-4" >출국편</label></td>
				<td style="text-align:center"><label for="exampleInputEmail1" class="form-label fw-bold fs-4" >귀국편</label></td>
				</tr>
				<tr>
				<td><% 
						LocalDate now = LocalDate.now();
						String userid = (String)session.getAttribute("userid");
						findResultDo ado = rdao.selectedflight(goflightno, goflightdate);
					%>
			  <table border="1" >
				<tr height="60" align="center">
					<td width="200" class="topic"> 항공사 </td>
					<td width="500"> <%= ado.getAirline() %> </td>
				</tr>
				<tr height="60" align="center">
					<td width="200" class="topic"> 항공편명 </td>
					<td width="500" id="flightno"> <%= ado.getFlightairline() %> <%=ado.getFlightno() %> </td>
				</tr>
				<tr height="60" align="center">
					<td width="200" class="topic"> 출발지 </td>
					<td width="500"> <%= ado.getFlightfrom() %> </td>
				</tr>
				<tr height="60" align="center">
					<td width="200" class="topic"> 도착지 </td>
					<td width="500"> <%= ado.getFlightto() %> </td>
				</tr>
				<tr height="60" align="center">
					<td width="200" class="topic"> 운행 날짜 </td>
					<td width="500" id="flightdate"> 
						<%= goflightdate %>
					</td>
				</tr>
				<tr height="60" align="center">
					<td width="200" class="topic"> 비행시간 </td>
					<td width="500"> 
						<%= ado.getTakeofftime() %> ~ <%= ado.getLandingtime() %>
					</td>
				</tr>
				</table></td>
				<td><% 
			  	ado = rdao.selectedflight(backflightno, backflightdate);
			  %>
			  <table border="1" >
				<tr height="60" align="center">
					<td width="200" class="topic"> 항공사 </td>
					<td width="500"> <%= ado.getAirline() %> </td>
				</tr>
				<tr height="60" align="center">
					<td width="200" class="topic"> 항공편명 </td>
					<td width="500" id="flightno"> <%= ado.getFlightairline() %> <%=ado.getFlightno() %> </td>
				</tr>
				<tr height="60" align="center">
					<td width="200" class="topic"> 출발지 </td>
					<td width="500"> <%= ado.getFlightfrom() %> </td>
				</tr>
				<tr height="60" align="center">
					<td width="200" class="topic"> 도착지 </td>
					<td width="500"> <%= ado.getFlightto() %> </td>
				</tr>
				<tr height="60" align="center">
					<td width="200" class="topic"> 운행 날짜 </td>
					<td width="500" id="flightdate"> 
						<%= goflightdate %>
					</td>
				</tr>
				<tr height="60" align="center">
					<td width="200" class="topic"> 비행시간 </td>
					<td width="500"> 
						<%= ado.getTakeofftime() %> ~ <%= ado.getLandingtime() %>
					</td>
				</tr>
				</table></td>
				</tr>
			</table>
			</div>
			<div class="d-grid gap-2 col-6 mx-auto">
			  <button class="btn btn-primary" type="button" onclick="location.href='main.jsp'">확인</button>
			</div>
		</form>
	</div>
</div></div>
<%
	rdao.insertreservation(userid, now.toString(), goflightdate, goflightairline, goflightno, backflightdate, backflightairline, backflightno);
%>

</body>
</html>