<%-- 
    Document   : write_mail.jsp
    Author     : jongmin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>

<%-- @taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" --%>


<html lang="ko">
<%@include file="../fragments/head.jspf" %>
<head>
    <link type="text/css" rel="stylesheet" href="../css/mail_write.css"/>
    <title>메일 쓰기 화면</title>
    <script>
        // 임시 저장 버튼 클릭 시 이벤트 처리 "write_mail.temp"로 Post 요청
        function saveMailtoTemp() {
            const form = document.getElementById('write_mail_form');
            form.action = '/write_mail.temp';
            form.submit();
            console.log('임시 저장')
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
                    <form enctype="multipart/form-data" method="POST" action="/write_mail.do" id="write_mail_form">
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
                        <button type="submit" class="btn btn-primary">메일 보내기</button>
                        <button type="button" class="btn btn-secondary" onclick="saveMailtoTemp()">임시 보관</button>
                        <button type="reset" class="btn btn-secondary">다시 입력</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>


<!-- ${message.attachments} 첨부 파일 값이 전달 안되는 것을 확인함 -->

<div class="container">
    <div class="row">
        <div class="col-3"> <!-- 왼쪽 사이드바 -->
        </div>
        <div class="col-9"> <!-- 오른쪽 메일 보기 -->
            <div class="card">
                <div class="card-header">
                    <h3>메일 보기</h3>
                </div>
                <div class="card-body">
                    <div class="row">
                        <div class="col-md-12">
                            <a href="../mail" class="btn btn-primary">목록으로</a>
                            <a href="delete?id=${id}" class="btn btn-danger">삭제</a>
                        </div>
                    </div>
                    <br>
                    <div class="row">
                        <div class="col-md-12">
                            <table class="table table-bordered">
                                <caption>메일</caption>
                                <tr>
                                    <th>보낸 사람</th>
                                    <td>${message.mimeMessage.from[0]}</td>
                                </tr>
                                <tr>
                                    <th>보낸 시간</th>
                                    <td>${message.mailDate}</td>
                                </tr>
                                <tr>
                                    <th>제목</th>
                                    <td>${message.mimeMessage.subject}</td>
                                </tr>
                                <c:if test="${message.attachments.size() > 0}">
                                    <tr>
                                        <th>첨부파일</th>
                                        <td>
                                            <c:forEach var="attachment" items="${message.attachments}">
                                                <a href="${id}/download?filename=${attachment}">${attachment}</a>
                                                <br>
                                            </c:forEach>
                                        </td>
                                    </tr>
                                </c:if>
                                <tr>
                                    <th>내용</th>
                                    <td>${message.mailContent}</td>
                                </tr>
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
