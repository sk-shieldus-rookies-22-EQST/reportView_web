<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.skrookies.dahaezlge.controller.qna.Dto.QnaDto" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
<title>QnA 게시판</title>
</head>
<body>
<div class="container">
<%@ include file="banner.jsp" %>

	<div class="container" style="max-width: 1200px;margin-bottom:100px;border-radius: 5px;padding: 50px 20px;">
		<p class="text-start fs-1 fw-bold" style="display: flex;justify-content: center; margin-bottom:30px;margin-top:16px">내 정보</p>

        <!-- 검색 폼 -->
        <div style="margin-top: 20px; display: flex; justify-content: flex-end;">
            <form method="get" action="/qnaSearch" style="display: flex; align-items: center; gap: 10px;">

                <div class="input-group mb-3">
                    <input type="text" name="keyword" class="form-control" placeholder="검색어를 입력하세요" value="${param.keyword}" style="width: 200px;box-shadow:none;" aria-describedby="button-addon2" onfocus="this.style.backgroundColor='#f9f9f9';" onblur="this.style.backgroundColor='';">
                  <button type="submit" class="btn btn-primary" id="button-addon2">검색</button>
                </div>

            </form>
        </div>

            <table class="table table-hover" style="text-align: center; border: 1px solid #dddddd">
                <thead>
                    <tr>
                        <th style="text-align: center;">번호</th>
                        <th style="text-align: center;">제목</th>
                        <th style="text-align: center;">작성자</th>
                        <th style="text-align: center;">날짜</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        // request에서 qnaList를 받아와 처리
                        List<QnaDto> qnaList = (List<QnaDto>) request.getAttribute("qnaList");
                        if (qnaList != null) {
                            for (QnaDto qna : qnaList) {
                    %>
                            <tr style="cursor:pointer;" onclick="window.location.href='/qnaDetail?qna_id=<%= qna.getQna_id() %>';">
                                <td><%= qna.getQna_id() %></td>
                                <td style="display:flex;justify-content: center;">
                                    <p style="margin:0;white-space: nowrap;overflow:hidden;width:500px;text-overflow:ellipsis;"><%= qna.getQna_title() %></p></td>
                                <td><%= qna.getQna_user_id() %></td>
                                <td><%= qna.getQna_created_at() %></td>
                            </tr>

                    <%
                            }
                        } else {
                    %>
                            <tr>
                                <td colspan="4">데이터가 없습니다.</td>
                            </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
            <nav class="mt-4" style="display: flex; justify-content: center;">
                <ul class="pagination">
                    <c:if test="${currentPage > 1}">
                        <li class="page-item">
                            <a class="page-link" href="/qnaList?page=${currentPage - 1}">이전</a>
                        </li>
                    </c:if>

                    <c:forEach begin="${startPage}" end="${endPage}" var="i">
                        <li class="page-item <c:if test='${i == currentPage}'>active</c:if>">
                            <a class="page-link" href="/qnaList?page=${i}">${i}</a>
                        </li>
                    </c:forEach>

                    <c:if test="${currentPage < totalPages}">
                        <li class="page-item">
                            <a class="page-link" href="/qnaList?page=${currentPage + 1}">다음</a>
                        </li>
                    </c:if>
                </ul>
            </nav>
            <a href="qnaWrite" class="btn btn-primary pull-right">글 쓰기</a>
    </div>
</div>
</body>
</html>
