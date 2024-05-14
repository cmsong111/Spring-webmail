<%--
    Document   : sidebar_adduser_menu
    Author     : jongmin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<head>
    <link type="text/css" rel="stylesheet" href="../css/sidebar_previous_menu.css"/>
</head>
<html lang="ko">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>JSP Page</title>
</head>
<body>
<br> <br>

<span class="user_name">
                <sec:authentication property="principal.username" var="username"/>
                <strong>${username}<span style="font-size: 17px">님</span> </strong>
        </span> <br> <br>

<a href="mail" id="previous_link" class="btn btn-primary"> 돌아가기 </a>
</body>
</html>
