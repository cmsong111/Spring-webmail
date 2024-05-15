<%--
    Document   : main_menu
    Created on : 2022. 6. 10., 오후 3:15:45
    Author     : skylo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>


<!DOCTYPE html>
<html lang="ko">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link type="text/css" rel="stylesheet" href="../css/mail_main_menu.css"/>
    <%@include file="../fragments/head.jspf" %>
    <title>주메뉴 화면</title>
    <script>
        function deleteMail(mailboxUid, mailUid) {
            if (confirm("정말 삭제하시겠습니까?")) {
                location.href = "/mail/" + mailboxUid + "/" + mailUid + "/delete";
            }
        }
    </script>
</head>
<body>
<%@include file="../fragments/header.jspf" %>

<!-- 메시지 삭제 링크를 누르면 바로 삭제되어 실수할 수 있음. 해결 방법은? -->
<div  class="container mt-4">
    <div class="row">
        <div class="col-md-3">
            <jsp:include page="../fragments/sidebar_menu.jsp"/>
        </div>
        <div class="col-md-9">
            <div>
                <p>전체 메일 <a href="/mail/unread" style="color:black;">${unread}</a> / ${total}</p>
            </div>
            <table class="table table-bordered">
                <caption>메일 목록</caption>
                <thead class="thead-dark">
                <tr>
                    <th scope="col">번호</th>
                    <th scope="col">제목</th>
                    <th scope="col">보낸 사람</th>
                    <th scope="col">읽음 여부</th>
                    <th scope="col">메일 분류</th>
                    <th scope="col">수신 날짜</th>
                    <th scope="col">삭제</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="message" items="${messageList}" varStatus="status">
                    <tr>
                        <td>${message.mailUid}</td>
                        <c:choose>
                            <c:when test="${mailBoxType == '/mail/3'}">
                                <td><a href="${mailBoxType}/${message.mailUid}/draft">${message.mimeMessage.subject}</a></td>
                            </c:when>
                            <c:otherwise>
                                <td><a href="${mailBoxType}/${message.mailUid}">${message.mimeMessage.subject}</a></td>
                            </c:otherwise>
                        </c:choose>
                        <td>${message.mimeMessage.from[0]}</td>
                        <td>${message.mailIsSeen}</td>
                        <td>${message.mailMimeType}</td>
                        <td>${message.mailDate}</td>
                        <td><a href="javascript:deleteMail(${message.mailboxMailboxId}, ${message.mailUid})">삭제</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div> 페이지
                <c:forEach var="i" begin="1" end="${total / size +1 }" step="1">
                    <c:if test="${i == page}">
                        <a href="<c:url value="${pageContext.request.contextPath}${type}?page=${i}"/>" style="color:black;"><b>${i}</b></a>
                    </c:if>
                    <c:if test="${i != page}">
                        <a href="<c:url value="${pageContext.request.contextPath}${type}?page=${i}"/>">${i}</a>
                    </c:if>
                </c:forEach>
            </div>
        </div>
    </div>
</div>


<footer>
    <%@include file="../fragments/footer.jspf" %>
</footer>

</body>
</html>
