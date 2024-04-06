<%-- 
    Document   : sidebar_admin_menu.jsp
    Author     : jongmin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<!DOCTYPE html>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>웹메일 시스템 메뉴</title>
</head>
<body>
<br> <br>

<span style="color: indigo"> <strong>사용자: <sec:authentication property="principal.username"/> </strong> </span> <br>

<p><a href="add_user">사용자 추가</a></p>
<p><a href="delete_user"> 사용자 제거</a></p>
<p><a href="/logout">로그아웃</a></p>
</body>
</html>
