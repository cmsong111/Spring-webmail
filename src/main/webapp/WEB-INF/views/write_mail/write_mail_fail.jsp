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
    <%@include file="../fragments/head.jspf" %>
    <title>메인송신실패</title>
</head>
<body>
<%@include file="../fragments/header.jspf" %>

<%--<sec:authorize access="isAuthenticated()">--%>
<%--    <sec:authentication property="principal.username" var="username"/>--%>

    <div class="container mt-4 d-flex justify-content-center">
        <div class="row" style="width: 100%; align-items: center">
            <div class="card" style="width: 100%; align-items: center" >
                <p class="card-title fs-3 d-inline-flex p-2 justify-content-center">메일 전송에&nbsp;<span style="color: green">실패</span>하였습니다.</p>
                <script src="https://unpkg.com/@dotlottie/player-component@latest/dist/dotlottie-player.mjs" type="module"></script>

                <dotlottie-player src="https://lottie.host/9f3cfb9d-bbd7-4a5b-8f12-e7e982211a8b/1SaP6xPWZj.json" background="transparent" speed="1" style="width: 300px; height: 300px;" loop autoplay></dotlottie-player>
                <div class="card-body">
                    <p class="card-title fs-3 d-inline-flex p-2 justify-content-center">아래와 같은 이유로 메일 전송에 실패 하였습니다.</p>
                    <p>${error}</p>
                    <a class="btn btn-outline-primary d-md-flex justify-content-center" href="<c:url value="/write_mail"/>">다시 작성하기</a>
                </div>
            </div>
        </div>
    </div>

<%--</sec:authorize>--%>

<%@include file="../fragments/footer.jspf" %>
<script src="https://unpkg.com/@dotlottie/player-component@latest/dist/dotlottie-player.mjs" type="module"></script>
</body>
</html>
