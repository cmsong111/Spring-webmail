<%--
  Created by IntelliJ IDEA.
  User: cmson
  Date: 24. 4. 10.
  Time: 오후 8:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <%@ include file="../fragments/head.jspf" %>
    <title>Title</title>
</head>
<body>
<%@ include file="../fragments/header.jspf" %>

<div class="container">
    <div class="row">
        <div class="col-md-12">
            <h1>회원가입</h1>
            <form action="/auth/signup.do" method="post">
                <div class="form-group form-group col-md-6">
                    <label for="username">아이디</label>
                    <input type="text" class="form-control" id="username" name="username" placeholder="아이디를 입력하세요">
                </div>

                <div class="form-group form-group col-md-6">
                    <label for="password">비밀번호</label>
                    <input type="password" class="form-control" id="password" name="password" placeholder="비밀번호를 입력하세요">
                </div>

                <button type="submit" class="btn btn-primary">회원가입</button>
            </form>
        </div>
    </div>
</div>

</body>
</html>
