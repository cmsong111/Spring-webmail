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

<table>
    <caption>메일</caption>
    <tr>
        <th>보낸 사람</th>
        <td>${message.headerBytes().from[0]}</td>
    </tr>
    <tr>
        <th>제목</th>
        <td>${message.headerBytes().subject}</td>
    </tr>
    <tr>
        <th>내용</th>
        <td>${message.mimeMessage()}</td>
    </tr>
    <tr>
        <th>보낸 시간</th>
        <td>${message.headerBytes().sentDate}</td>
    </tr>
</table>

<%@include file="../footer.jspf" %>
</body>
</html>
