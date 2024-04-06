<%-- 
    Document   : sidebar_adduser_menu
    Author     : jongmin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>

<html lang="ko">
<head>
    <%@include file="../fragments/head.jspf" %>
    <title>JSP Page</title>
</head>
<body>
<br> <br>

<span style="color: indigo">
            <strong>사용자:  <sec:authentication property="principal.username"/> </strong>
        </span> <br> <br>

<p><a href="write_mail?sender="> 답장 하기 </a></p>
<p><a href="main_menu"> 이전 메뉴로 </a></p>
</body>
</html>
