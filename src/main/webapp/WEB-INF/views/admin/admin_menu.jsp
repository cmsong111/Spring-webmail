<%-- 
    Document   : admin_menu.jsp
    Author     : jongmin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<!DOCTYPE html>

<html lang="ko">
<%@include file="../fragments/head.jspf" %>
<head>
    <title>사용자 관리 메뉴</title>
</head>
<body>
<%@ include file="../fragments/header.jspf" %>

<div id="sidebar">
    <jsp:include page="sidebar_admin_menu.jsp"/>
</div>


<div id="main">
    <h2> 메일 사용자 목록 </h2>

    <table>
        <caption>사용자 목록</caption>
        <tr>
            <th>이름</th>
            <th>암호화 알고리즘</th>
            <th>비밀번호</th>
            <th>역할</th>
            <th>버전</th>
        </tr>
        <c:forEach items="${userList}" var="user">
            <tr>
                <td> ${user.getUsername()} </td>
                <td> ${user.passwordHashAlgorithm}</td>
                <td> ${user.password} </td>
                <td> ${user.role} </td>
                <td> ${user.version} </td>
            </tr>
        </c:forEach>
    </table>
</div>

<%@include file="../fragments/footer.jspf" %>
</body>
</html>
