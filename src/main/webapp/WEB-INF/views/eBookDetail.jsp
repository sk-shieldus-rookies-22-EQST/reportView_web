<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
<script>
    var message = "${message}";
    if (message) {
        alert(message);
    }
</script>
</head>
<body>
    <div class="container">
        <%@ include file="banner.jsp" %>
    </div>

    <div class="container">
        <div class="container" style="max-width: 1200px;margin-bottom:100px;border-radius: 5px;padding: 50px 20px;">
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
                    <td width="70%"> <%= String.format("%,d원", eBook.getBook_price()) %>  </td>
                </tr>
            </table>
    <!-- 메시지를 숨긴 상태로 HTML에 포함 -->
    <span id="message" style="display: none;">${message}</span>


            <div>
                <%= eBook.getBook_summary() %>
            </div>
            <div class="d-grid gap-2 col-6 mx-auto" style="margin-top:30px; width:700px">
                <div class="grid text-center" style="display:flex;justify-content: space-between;">
                    <form method="POST" action="/addCart" class="g-col-6"  style="width:49%">
                        <input type="hidden" name="book_id" value="<%= eBook.getBook_id() %>" />
                        <button class="btn btn-outline-primary" type="submit" style="width:100%;padding:20px;">장바구니</button>
                    </form>
                    <form method="POST" action="/purchase" class="g-col-6" style="width:49%;">
                          <input type="hidden" name="book_id" value="<%= eBook.getBook_id() %>" />
                          <button class="btn btn-primary" type="submit" style="width:100%;padding:20px;">결제</button>
                    </form>

                </div>
                <% String previousPage = request.getHeader("referer"); %>
                <button class="btn btn-primary" type="button"
                    onclick="location.href='<%= previousPage %>'" style="padding:20px;">목록으로</button>
            </div>

        </div>
    </div>

</body>
</html>