<%-- 
    Document   : add_user.jsp
    Author     : jongmin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE html>

<html lang="ko">
<%@include file="../fragments/head.jspf" %>
<head>
    <title>사용자 추가 화면</title>
</head>
<body>
<%@ include file="../fragments/header.jspf" %>

<div id="sidebar">
    <jsp:include page="sidebar_admin_previous_menu.jsp"/>
</div>

<div id="main">
    추가로 등록할 사용자 ID와 암호를 입력해 주시기 바랍니다. <br> <br>

    <form name="AddUser" action="add_user.do" method="POST">
        <fieldset>
            <legend>사용자 추가 폼</legend>

            <div class="form-group">
                <label for="username">사용자 ID</label>
                <input type="text" id="username" name="username" class="form-control" required>
            </div>

            <div class="form-group">
                <label for="password">암호</label>
                <input type="password" id="password" name="password" class="form-control" required>
            </div>

            <button type="submit" class="btn btn-primary" name="register">등록</button>
            <button type="reset" class="btn btn-secondary" name="reset">초기화</button>
        </fieldset>
    </form>
</div>

<%@include file="../fragments/footer.jspf" %>
</body>
</html>
