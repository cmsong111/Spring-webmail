<%-- 
    Document   : index
    Created on : 2022. 6. 10., 오후 2:19:43
    Author     : skylo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="ko">

<head>
    <link type="text/css" rel="stylesheet" href="../css/index.css"/>
    <%@include file="fragments/head.jspf" %>
    <title>로그인</title>
</head>
<body>
<%@include file="fragments/header.jspf" %>

<sec:authorize access="isAuthenticated()">
    <sec:authentication property="principal.username" var="username"/>

    <div class="container mt-4 d-flex justify-content-center">
        <div class="row">
            <div class="card" style="width: auto;">
                <dotlottie-player src="https://lottie.host/493aaf35-73e8-4e69-a2a4-b0d1875e082e/QX6Hs3wmqf.json" class="card-img-top" background="transparent" speed="1" style="width: 300px; height: 300px;" loop autoplay></dotlottie-player>
                <div class="card-body">
                    <p class="card-title fs-3 d-inline-flex p-2 justify-content-center">안녕하세요, <c:out value="${username}"/>님</p>
                    <a class="btn btn-outline-primary d-md-flex justify-content-center" href="<c:url value="/mail"/>">메일함으로 가기</a>
                </div>
            </div>
        </div>
    </div>

</sec:authorize>

<sec:authorize access="!isAuthenticated()">
    <div id="login_form" class="container">
        <form method="POST" action="<c:url value="/login.do"/>" class="needs-validation" novalidate>
            <sec:csrfInput/>
            <div class="mb-3" id="mb-3-style">
                <label for="userid" class="form-label">아이디</label>
                <input type="text" class="form-control" id="userid" name="userid" size="20" autofocus required>
                <div class="invalid-feedback">아이디를 입력해주세요.</div>
            </div>
            <div class="mb-3" id="mb-3-style">
                <label for="password" class="form-label">비밀번호</label>
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
<script src="https://unpkg.com/@dotlottie/player-component@latest/dist/dotlottie-player.mjs" type="module"></script>
</body>
</html>
