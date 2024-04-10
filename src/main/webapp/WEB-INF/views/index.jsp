<%-- 
    Document   : index
    Created on : 2022. 6. 10., 오후 2:19:43
    Author     : skylo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="ko">

<head>
    <%@include file="fragments/head.jspf" %>
    <title>로그인</title>
</head>
<body>
<%@include file="fragments/header.jspf" %>

<sec:authorize access="isAuthenticated()">
    <sec:authentication property="principal.username" var="username"/>
    <p>안녕하세요, <c:out value="${username}"/>님</p>
    <a href="<c:url value="/mail"/>">메일함으로 가기</a>
    <a href="<c:url value="/logout"/>">로그아웃</a>
</sec:authorize>

<sec:authorize access="!isAuthenticated()">
    <div id="login_form" class="container">
        <form method="POST" action="<c:url value="/login.do"/>" class="needs-validation" novalidate>
            <div class="mb-3">
                <label for="userid" class="form-label">사용자</label>
                <input type="text" class="form-control" id="userid" name="userid" size="20" autofocus required>
                <div class="invalid-feedback">사용자를 입력해주세요.</div>
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">암호</label>
                <input type="password" class="form-control" id="password" name="password" size="20" required>
                <div class="invalid-feedback">암호를 입력해주세요.</div>
            </div>
            <button type="submit" class="btn btn-primary" name="B1">로그인</button>
            <button type="button" class="btn btn-secondary" name="B4" onclick="location.href='/auth/signup'">회원가입</button>
            <button type="button" class="btn btn-secondary" name="B3" onclick="location.href='find'">아이디/비밀번호 찾기</button>
        </form>
    </div>

</sec:authorize>
<%@include file="fragments/footer.jspf" %>
</body>
</html>
