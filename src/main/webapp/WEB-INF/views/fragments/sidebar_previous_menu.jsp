<%--
    Document   : sidebar_adduser_menu
    Author     : jongmin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>

<html lang="ko">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>JSP Page</title>
</head>
<body>
<br> <br>

<span style="color: indigo">
                <sec:authentication property="principal.username" var="username"/>
                <strong>사용자: ${username} </strong>
        </span> <br> <br>

<a href="mail"> 이전 메뉴로 </a>
</body>
</html>
