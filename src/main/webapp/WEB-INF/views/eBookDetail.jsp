<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>

</head>
<body>
    <div class="container">
        <%@ include file="banner.jsp" %>
    </div>

    <div class="container">
        <div class="container" style="max-width: 700px;margin-bottom:100px;border-radius: 5px;padding: 50px 20px;">
            <table align="center" border="1" style="width: 100%">
            <%@ page import="java.util.List" %>
            <%@ page import="com.skrookies.dahaezlge.controller.book.Dto.BookDto" %>

            <%
                BookDto eBook = (BookDto) request.getAttribute("bookInfo");
            %>

                <tr align="left">
                    <td width="30%" rowspan="4">
                        <img
                            src="<%= eBook.getBook_img_path() %>"
                            width="200" height="310"
                        />
                    </td>
                    <td width="70%"> <%= eBook.getBook_title() %>  </td>
                </tr>
                <tr align="left">
                    <td width="70%"> <%= eBook.getBook_auth() %>  </td>
                </tr>
                <tr align="left">
                    <td width="70%"> <%= eBook.getBook_reg_date() %>  </td>
                </tr>
                <tr align="left">
                    <td width="70%"> <%= eBook.getBook_price() %>  </td>
                </tr>
            </table>
            <div>
                <span style="display: inline-block; float: right;">
                    <button action="/addCart">장바구니</button>
                    <button action="/Purchase">구매</button>
                </span>
            </div>
            <div>
                <%= eBook.getBook_summary() %>
            </div>
            <div class="d-grid gap-2 col-6 mx-auto" style="margin-top:30px">
                <% String previousPage = request.getHeader("referer"); %>
                <button class="btn btn-primary" type="button"
                    onclick="location.href='<%= previousPage %>'">목록으로</button>
            </div>

        </div>
    </div>

</body>
</html>