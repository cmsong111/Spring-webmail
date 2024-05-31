<%--
  Created by IntelliJ IDEA.
  User: cmson
  Date: 24. 5. 29.
  Time: 오전 6:59
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html lang="ko">
<head>

    <%@include file="../fragments/head.jspf" %>
</head>
<body>
<%@include file="../fragments/header.jspf" %>


<form method="post" action="/contacts/add" >
    <sec:csrfInput/>
    <input type="hidden" name="friendId" value="${contact.id}"/>
    <div class="container">
        <div class="row">
            <div class="col-9">
                <div class="card">
                    <div class="card-header">
                        <h3>연락처 추가</h3>
                    </div>
                    <div class="card-body">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="mb-3">
                                    <label for="nickname" class="form-label">별명</label>
                                    <input type="text" class="form-control" id="nickname" name="nickname" required value="${contact.nickname}"/>
                                    <div class="invalid-feedback">이름을 입력해주세요.</div>
                                </div>
                                <div class="mb-3">
                                    <label for="friend" class="form-label">이메일</label>
                                    <input type="text" class="form-control" id="friend" name="friend" required value="${contact.friend.userName}"/>
                                    <div class="invalid-feedback">이메일을 입력해주세요.</div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <button type="submit" class="btn btn-primary">추가</button>
                                <button type="button" class="btn btn-secondary" onclick="location.href='/contacts'">취소</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form>

<%@include file="../fragments/footer.jspf" %>
</body>
</html>
