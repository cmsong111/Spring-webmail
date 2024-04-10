<%-- 
    Document   : show_message.jsp
    Author     : jongmin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>

<html lang="ko">
<jsp:include page="../fragments/head.jspf"/>
<head>
    <title>메일 보기 화면</title>
</head>
<body>
<%@include file="../fragments/header.jspf" %>
<div class="container">
    <div class="row">
        <div class="col-3"> <!-- 왼쪽 사이드바 -->
            <jsp:include page="sidebar_read_menu.jsp"/>
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
                                    <td>${message.mailFrom()}</td>
                                </tr>
                                <tr>
                                    <th>보낸 시간</th>
                                    <td>${message.mailDate()}</td>
                                </tr>
                                <tr>
                                    <th>제목</th>
                                    <td>${message.subject()}</td>
                                </tr>
                                <c:if test="${message.attachments().size() > 0}">
                                    <tr>
                                        <th>첨부파일</th>
                                        <td>
                                            <c:forEach var="attachment" items="${message.attachments()}">
                                                <p>${attachment}</p>
                                                <br>
                                            </c:forEach>
                                        </td>
                                    </tr>
                                </c:if>
                                <tr>
                                    <th>내용</th>
                                    <td>${message.body()}</td>
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
