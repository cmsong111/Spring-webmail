<%--
    Document   : contacts.jsp
    Author     : Namju Kim
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>

<html lang="ko">
<head>
    <%@include file="../fragments/head.jspf" %>
</head>
<body>
<%@include file="../fragments/header.jspf" %>
<div class="container">
    <div class="row">
        <div class="col-9">
            <div class="card">
                <div class="card-header">
                    <h3>연락처</h3>
                </div>
                <div class="card-body">
                    <div class="row">
                        <div class="col-md-12">
                            <a href="contacts/add" class="btn btn-primary">새 연락처</a>
                        </div>
                    </div>
                    <br>
                    <div class="row">
                        <div class="col-md-12">
                            <table class="table table-bordered">
                                <caption>연락처</caption>
                                <tr>
                                    <th>별명</th>
                                    <th>이메일</th>
                                    <th>수정</th>
                                    <th>삭제</th>
                                    <th>메일 전송</th>
                                </tr>
                                <c:forEach items="${contacts}" var="contact">
                                    <tr>
                                        <td>${contact.nickname}</td>
                                        <td>${contact.friend.userName}</td>
                                        <td><a href="contacts/edit?id=${contact.id}" class="btn btn-warning">수정</a></td>
                                        <td><a href="contacts/delete?id=${contact.id}" class="btn btn-danger">삭제</a></td>
                                        <td><a href="write_mail?mail=${contact.friend.userName}" class="btn btn-primary">메일 전송</a></td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="../fragments/footer.jspf" %>
</body>
</html>
