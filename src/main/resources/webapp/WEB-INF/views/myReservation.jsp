<%@page import="finProj_flight.findResultDo"%>
<%@page import="java.util.ArrayList"%>
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
		<%@ include file="nav.jsp" %>
		<% String userid = (String)session.getAttribute("userid");%>
		
	<div cass="container">
	<div class="container" style="margin-bottom:100px;border-radius: 5px;padding: 50px 20px;">
		<p class="text-start fs-1 fw-bold" style="display: flex;justify-content: center; margin-bottom:30px;margin-top:16px">내 예약</p>
		
			<table width="100%" border="1"><thead>
			<tr align="center">
				<td width="150"> 예약일 </td>
				<td>
					<table width="100%">
						<tr align="center">
							<td style="width:150px;"> 날짜 </td>
							<td style="width:150px;"> 출발지 </td>
							<td style="width:150px;"> 시간 </td>
							<td style="width:150px;"> 도착지 </td>
							<td style="width:150px;"> 항공편 </td>
						</tr>
					</table>
				</td>
			</tr></thead><tbody>
			<jsp:useBean id="adao" class="finProj_flight.findResultDao" />
	<%
	
		ArrayList<findResultDo> aList = adao.findMyReserves(userid);
		for(int i = 0; i<aList.size(); i++){
			findResultDo ado = aList.get(i);
			findResultDo bdo = adao.selectedflight(ado.getGoflightno(), ado.getGoflightdate());
			
		
	%>
		
		<tr align="center" height="50" style="border-top:1px solid #212529;">
			<td width="150" style="border-right:1px solid #212529;"> <%= ado.getReservedate() %>
			<button type="button" class="btn btn-outline-primary btn-sm" style="margin-top : 8px;" onclick="location.href='deleteReserv.jsp?no=<%=ado.getReserveno()%>'">취소하기</button>
			</td>
			<td>
				<table width="100%">
					<tr class="going" align="center" style="border-bottom:1px solid #212529;" >
						<td class="date" style="width:150px;"><%= ado.getGoflightdate() %></td>
						<td class="countryFrom" style="width:150px;"><%= adao.findCountry(bdo.getFlightfrom()) %></td>
						<td class="time" style="width:150px;"><%= bdo.getTakeofftime() %> ~ <%= bdo.getLandingtime() %></td>
						<td class="countryTo" style="width:150px;"><%= adao.findCountry(bdo.getFlightto()) %></td>
						<td class="flightinfo" style="width:150px;">
							<table width="100%">
							<tr align="center"><td class="airline" style="width:150px;"><%= bdo.getAirline() %></td></tr>
							<tr align="center"><td class="flight" style="width:150px;"><%= ado.getGoflightairline() %> <%= ado.getGoflightno() %></td></tr>
						</table>
						</td>
					</tr>
					<%
					 bdo = adao.selectedflight(ado.getBackflightno(), ado.getBackflightdate());
					%>
					<tr class="coming" align="center">
						<td class="date"style="width:150px;"><%= ado.getBackflightdate() %></td>
						<td class="countryFrom"style="width:150px;"><%= adao.findCountry(bdo.getFlightfrom()) %></td>
						<td class="time"style="width:150px;"><%= bdo.getTakeofftime() %> ~ <%= bdo.getLandingtime() %></td>
						<td class="countryTo"style="width:150px;"><%= adao.findCountry(bdo.getFlightto()) %></td>
						<td class="flightinfo"style="width:150px;">
						<table width="100%">
							<tr align="center"><td class="airline"style="width:150px;"><%= bdo.getAirline() %></td></tr>
							<tr align="center"><td class="flight"style="width:150px;"><%= ado.getBackflightairline() %> <%= ado.getBackflightno() %></td></tr>
						</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<%
		}
	%></tbody></table>
	<div class="d-grid gap-2 col-6 mx-auto" style="margin-top:50px!important">
			  <button class="btn btn-primary" type="button" onclick="location.href='main.jsp'">예약하러 가기</button>
			</div>
	</div>
	
	
</div>


		
		
		
	
		
	</div>

</body>
</html>