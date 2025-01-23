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

<div class="container">
    <div class="container" style="max-width: 700px;margin-bottom:100px;border-radius: 5px;padding: 50px 20px;">
        <%
            List<Book> itemList = (List<Book>) request.getAttribute("cartList");
            Iterator<Book> iterator = itemList.iterator();
            while (iterator.hasNext()) {
                Book book = iterator.next();
        %>
        <li>
        <table align="center" border="1" style="width: 100%">
            <tr align="left">
                <td> <img src="<%=book.getBook_title()%>" </td>
                <td> <%= book.getBook_title() %> </td>
                <td> <%= book.getBook_price() %> </td>
                <td> 
                    <form name="eBookCartDelete" action="/eBookCartProc.jsp" method="post">
                        <input type="hidden" name="cart_id" value="<%= cartList.getCartId() %>">
                        <button type="submit">삭제</button>
                    </form> 
                </td>
            </tr>  
        </table>
        </li>
        <%
            }
        %>
        
        <div class="d-grid gap-2 col-6 mx-auto" style="margin-top:30px">
            <% String previousPage = request.getHeader("referer"); %>
            <button class="btn btn-primary" type="button" 
                onclick="location.href='<%= previousPage %>'">목록으로</button>
        </div>  
        
    </div>
</div>

</body>
</html>