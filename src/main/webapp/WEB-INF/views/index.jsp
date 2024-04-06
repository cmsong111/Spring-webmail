<%-- 
    Document   : index
    Created on : 2022. 6. 10., 오후 2:19:43
    Author     : skylo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE html>

<html>
<%@include file="fragments/head.jspf" %>
<head>
    <title>로그인</title>
</head>
<body>
<%@include file="fragments/header.jspf" %>


<div id="login_form">
    <form method="POST" action="login.do">
        사용자: <input type="text" name="userid" size="20" autofocus> <br/>
        암&nbsp;&nbsp;&nbsp;호: <input type="password" name="password" size="20"> <br/> <br/>
        <input type="submit" value="로그인" name="B1">&nbsp;&nbsp;&nbsp;
        <input type="reset" value="다시 입력" name="B2">
    </form>
</div>


<%@include file="footer.jspf" %>
</body>
</html>
