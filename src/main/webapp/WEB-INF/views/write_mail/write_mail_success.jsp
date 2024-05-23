<%--
  Created by IntelliJ IDEA.
  User: wodud
  Date: 2024-05-23
  Time: 오후 4:30
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="ko">

<head>
    <link type="text/css" rel="stylesheet" href="../css/index.css"/>
    <%@include file="../fragments/head.jspf" %>
    <title>메인송신성공</title>
</head>
<body>
<%@include file="../fragments/header.jspf" %>

<%--<sec:authorize access="isAuthenticated()">--%>
<%--    <sec:authentication property="principal.username" var="username"/>--%>

<div class="container mt-4 d-flex justify-content-center">
    <div class="row" style="width: 100%; align-items: center">
        <div class="card" style="width: 100%; align-items: center" >
            <p class="card-title fs-3 d-inline-flex p-2 justify-content-center">메일을&nbsp;<span style="color: green">성공적</span>으로 보냈습니다.</p>
            <script src="https://unpkg.com/@dotlottie/player-component@latest/dist/dotlottie-player.mjs" type="module"></script>

            <dotlottie-player src="https://lottie.host/54b287b9-d4f7-4854-96d6-295b906b8343/jNt7W2bZh2.json" background="transparent" speed="1" style="width: 300px; height: 300px;" loop autoplay></dotlottie-player>
            <div class="card-body">
                <a class="btn btn-outline-primary d-md-flex justify-content-center" href="<c:url value="/mail/1"/>">받은메일함가기</a>
                <br>
                <a class="btn btn-outline-primary d-md-flex justify-content-center" href="<c:url value="/write_mail"/>">메일쓰기</a>

            </div>
        </div>
    </div>
</div>

<%--</sec:authorize>--%>

<%@include file="../fragments/footer.jspf" %>
<script src="https://unpkg.com/@dotlottie/player-component@latest/dist/dotlottie-player.mjs" type="module"></script>
</body>
</html>
