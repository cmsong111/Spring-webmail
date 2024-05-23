<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>

<%-- @taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" --%>

<html lang="ko">
<%@include file="../fragments/head.jspf" %>
<head>
    <link type="text/css" rel="stylesheet" href="../css/mail_write.css"/>
    <title>임시 저장 메일 전송 화면</title>
    <script>
        // 임시 저장 버튼 클릭 시 이벤트 처리 "write_mail.temp"로 Post 요청
        function saveMailtoTemp() {
            const form = document.getElementById('write_mail_form');
            form.action = '/write_mail.temp';
            form.submit();
            console.log('임시 저장')
        }

        function removeFileName() {
            if (confirm("첨부 파일을 삭제하시겠습니까?")) {
                //id="fileName"인 hidden 값 공백 처리되는 거 확인했음
                document.getElementById('fileName').value = '';
            }
        }

    </script>
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
                    <form enctype="multipart/form-data" method="POST" action="/write_mail_edit.do" id="write_mail_form">
                        <sec:csrfInput/>
                        <div class="mb-3">
                            <label for="to" class="form-label">수신</label>
                            <input type="text" class="form-control" id="to" name="to" aria-describedby="toHelp" value="${message.mimeMessage.from[0]}">
                            <div id="toHelp" class="form-text">여러 수신자는 쉼표로 구분하세요.</div>
                        </div>
                        <div class="mb-3">
                            <label for="cc" class="form-label">참조</label>
                            <input type="text" class="form-control" id="cc" name="cc">
                        </div>
                        <div class="mb-3">
                            <label for="subj" class="form-label">메일 제목</label>
                            <input type="text" class="form-control" id="subj" name="subj" value="${message.mimeMessage.subject}">
                        </div>
                        <div class="mb-3">
                            <label for="body" class="form-label">본문</label>
                            <textarea class="form-control" id="body" name="body" rows="10">${message.mailContent}</textarea>
                        </div>
                        <div class="mb-3">
                            <label for="file1" class="form-label">첨부 파일</label>
                            <input type="file" class="form-control" id="file1" name="file1">
                        </div>
                        <div id="selectedFile"></div>
                        <input type="hidden" id="mailUid" name="mailUid" value="${message.mailUid}">
                        <input type="hidden" id="fileName" name="fileName" value="${message.attachments}">
                        <div class="mb-3">
                            <label class="form-label">첨부 파일 이름: ${message.attachments[1]}</label>
                            <a href="#" id="removeAttachment" onclick="removeFileName()">삭제</a>
                        </div>
                        <button type="submit" class="btn btn-primary">메일 보내기</button>
                        <button type="button" class="btn btn-secondary" onclick="saveMailtoTemp()">임시 보관</button>
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
