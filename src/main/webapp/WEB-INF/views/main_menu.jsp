<%-- 
    Document   : main_menu
    Created on : 2022. 6. 10., 오후 3:15:45
    Author     : skylo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <%@include file="fragments/head.jspf" %>
    <title>주메뉴 화면</title>
</head>
<body>
<%@include file="fragments/header.jspf" %>
<div id="sidebar">
    <jsp:include page="fragments/sidebar_menu.jsp"/>
</div>

<!-- 메시지 삭제 링크를 누르면 바로 삭제되어 실수할 수 있음. 해결 방법은? -->
<div id="main" class="container">
    <table class="table table-bordered">
        <caption>메일 목록</caption>
        <thead class="thead-dark">
        <tr>
            <th scope="col">번호</th>
            <th scope="col">제목</th>
            <th scope="col">보낸 사람</th>
            <th scope="col">읽음 여부</th>
            <th scope="col">수신 날짜</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="message" items="${messageList}" varStatus="status">
            <tr>
                <td>${message.mailUid()}</td>
                <td>${message.headerBytes().subject}</td>
                <td>${message.headerBytes().from[0]}</td>
                <td>${message.mailIsSeen()}</td>
                <td>${message.headerBytes().sentDate}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<footer>
    <%@include file="footer.jspf" %>
</footer>

</body>
</html>
