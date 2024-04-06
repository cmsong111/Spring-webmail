<%-- 
    Document   : show_message.jsp
    Author     : jongmin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE html>

<html lang="ko">
<jsp:include page="../fragments/head.jspf"/>
<head>
    <title>메일 보기 화면</title>
</head>
<body>
<%@include file="../fragments/header.jspf" %>

<div id="sidebar">
    <jsp:include page="sidebar_read_menu.jsp"/>
</div>

<div id="msgBody">
    ${msg}
</div>

<%@include file="../footer.jspf" %>
</body>
</html>
