<%-- 
    Document   : login_fail
    Created on : 2022. 6. 10., 오후 3:28:28
    Author     : skylo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>


<html lang="ko">
<head>
    <%@ include file="../fragments/head.jspf" %>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>로그인 실패</title>
    <link type="text/css" rel="stylesheet" href="css/main_style.css"/>
    <script type="text/javascript">
        function gohome() {
            window.location = "/"
        }
    </script>
</head>
<body onload="setTimeout('gohome()', 5000)">

<%@include file="../fragments/header.jspf" %>

<p class="text-center" id="login_fail">
    로그인에 실패하였습니다.

    올바른 사용자 ID와 암호를 사용하여 로그인하시기 바랍니다.

    5초 뒤 자동으로 초기 화면으로 돌아갑니다.

    자동으로 화면 전환이 일어나지 않을 경우
    <!-- 홈 화면으로 이동 하는 링크 -->
    <a href="/" title="초기 화면">초기 화면</a>을 선택해 주세요.

</p

<%@include file="../fragments/footer.jspf" %>

</body>
</html>
