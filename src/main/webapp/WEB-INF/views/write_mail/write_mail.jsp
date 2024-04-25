<%-- 
    Document   : write_mail.jsp
    Author     : jongmin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE html>

<%-- @taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" --%>


<html lang="ko">
<%@include file="../fragments/head.jspf" %>
<head>
    <title>메일 쓰기 화면</title>
</head>
<body>
<%@include file="../fragments/header.jspf" %>

<div class="container mt-4">
    <div class="row">
        <div class="col-md-3">
            <jsp:include page="../fragments/sidebar_previous_menu.jsp"/>
        </div>
        <div class="col-md-9">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">메일 쓰기</h5>
                    <form enctype="multipart/form-data" method="POST" action="write_mail.do">
                        <sec:csrfInput/>
                        <div class="mb-3">
                            <label for="to" class="form-label">수신</label>
                            <input type="text" class="form-control" id="to" name="to" aria-describedby="toHelp">
                            <div id="toHelp" class="form-text">여러 수신자는 쉼표로 구분하세요.</div>
                        </div>
                        <div class="mb-3">
                            <label for="cc" class="form-label">참조</label>
                            <input type="text" class="form-control" id="cc" name="cc">
                        </div>
                        <div class="mb-3">
                            <label for="subj" class="form-label">메일 제목</label>
                            <input type="text" class="form-control" id="subj" name="subj">
                        </div>
                        <div class="mb-3">
                            <label for="body" class="form-label">본문</label>
                            <textarea class="form-control" id="body" name="body" rows="10"></textarea>
                        </div>
                        <div class="mb-3">
                            <label for="file1" class="form-label">첨부 파일</label>
                            <input type="file" class="form-control" id="file1" name="file1">
                        </div>
                        <button type="submit" class="btn btn-primary">메일 보내기</button>
                        <button type="reset" class="btn btn-secondary">다시 입력</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<%@include file="../fragments/footer.jspf" %>
</body>
</html>
