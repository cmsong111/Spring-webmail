<%-- 
    Document   : index
    Created on : 2022. 6. 10., 오후 2:19:43
    Author     : skylo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="ko">
<%@include file="fragments/head.jspf" %>
<head>
    <title>로그인</title>
</head>
<body>
<%@include file="fragments/header.jspf" %>


<div id="login_form" class="container">
    <form method="POST" action="/login.do" class="needs-validation" novalidate>
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
        <button type="button" class="btn btn-secondary" name="B2" onclick="location.href='join'">회원가입</button>
        <button type="reset" class="btn btn-secondary" name="B2">다시 입력</button>
    </form>
</div>


<%@include file="footer.jspf" %>
</body>
</html>
