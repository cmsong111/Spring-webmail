<%--
    Document   : main_menu
    Created on : 2022. 6. 10., 오후 3:15:45
    Author     : skylo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link type="text/css" rel="stylesheet" href="../css/mail_main_menu.css"/>
    <%@include file="../fragments/head.jspf" %>
    <title>주메뉴 화면</title>
    <script>
        function deleteMail(mailBoxType, mailUid) {
            if (confirm("정말 삭제하시겠습니까?")) {
                location.href = mailBoxType + "/" + mailUid + "/delete";
            }
        }

        function clearMail(mailboxUid, mailUid) {
            if (confirm("영구 삭제하시겠습니까?")) {
                location.href = mailboxUid + "/" + mailUid + "/clear";
            }
        }

        function restoreMail(mailboxUid, mailUid) {
            if (confirm("메일을 복구하시겠습니까?")) {
                location.href = "/mail/" + mailboxUid + "/" + mailUid + "/restore";
            }
        }

        function formatDate(dateStr) {
            var date = new Date(dateStr);

            var year = date.getFullYear();
            var month = ("0" + (date.getMonth() + 1)).slice(-2);
            var day = ("0" + date.getDate()).slice(-2);
            var hours = ("0" + date.getHours()).slice(-2);
            var minutes = ("0" + date.getMinutes()).slice(-2);

            return year + "-" + month + "-" + day + " " + hours + ":" + minutes;
        }

    </script>
</head>
<body>
<%@include file="../fragments/header.jspf" %>

<!-- 메시지 삭제 링크를 누르면 바로 삭제되어 실수할 수 있음. 해결 방법은? -->
<div class="container-lg mt-4">
    <div class="row">
        <div class="col-md-3" style="width: 20%;">
            <jsp:include page="../fragments/sidebar_menu.jsp"/>
        </div>
        <div class="col-md-9" style="width: 80%;">
            <div>
                <p>전체 메일 <a href="/mail/unread" style="color:black;">${unread}</a> / ${total}</p>
            </div>
            <table class="table table-bordered">
                <caption>메일 목록</caption>
                <thead class="thead-dark">
                <tr>
                    <c:choose>
                        <c:when test="${mailBoxType == '/mail/4'}">
                            <th scope="col" style="text-align: center; width: 8%;">번호</th>
                            <th scope="col" style="text-align: center; width: 19%;">보낸 사람</th>
                            <th scope="col" style="text-align: center; width: 30%;">제목</th>
                            <th scope="col" style="text-align: center; width: 12%;">읽음 여부</th>
                            <th scope="col" style="text-align: center; width: 17%;">수신 날짜</th>
                            <th scope="col" style="text-align: center; width: 7%;">삭제</th>
                        </c:when>
                        <c:otherwise>
                            <th scope="col" style="text-align: center; width: 8%;">번호</th>
                            <th scope="col" style="text-align: center; width: 19%;">보낸 사람</th>
                            <th scope="col" style="text-align: center; width: 30%;">제목</th>
                            <th scope="col" style="text-align: center; width: 12%;">읽음 여부</th>
                            <th scope="col" style="text-align: center; width: 22%;">수신 날짜</th>
                            <th scope="col" style="text-align: center; width: 9%;">삭제</th>
                        </c:otherwise>
                    </c:choose>
                    <!-- 휴지통 화면에서 복구 버튼 목록 활성화 -->
                    <c:choose>
                        <c:when test="${mailBoxType == '/mail/4'}">
                            <th scope="col" style="text-align: center; width: 7%;">복구</th>
                        </c:when>
                    </c:choose>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="message" items="${messageList}" varStatus="status">
                    <tr>
                        <td style="text-align: center;">${message.mailUid}</td>
                        <td>${message.mimeMessage.from[0]}</td>
                        <!-- 임시 보관함 화면에서 메일 작성 페이지로 이동 활성화 -->
                        <c:choose>
                            <c:when test="${mailBoxType == '/mail/3'}">
                                <td><a href="${mailBoxType}/${message.mailUid}/draft">${message.mimeMessage.subject}</a></td>
                            </c:when>
                            <c:otherwise>
                                <td><a href="${mailBoxType}/${message.mailUid}">${message.mimeMessage.subject}</a></td>
                            </c:otherwise>
                        </c:choose>
                        <td style="text-align: center;">${message.mailIsSeen}</td>
                        <td style="text-align: center;">
                            <script>document.write(formatDate("${message.mailDate}"));</script>
                        </td>

                        <!-- 휴지통 화면에서 메일 영구 삭제 버튼 활성화 -->
                        <c:choose>
                            <c:when test="${mailBoxType == '/mail/1'}">
                                <td style="text-align: center;"><a href="javascript:deleteMail('${mailBoxType}', ${message.mailUid})">삭제</a></td>
                            </c:when>
                            <c:when test="${mailBoxType == '/mail/2'}">
                                <td style="text-align: center;"><a href="javascript:clearMail(2, ${message.mailUid})">영구 삭제</a></td>
                            </c:when>
                            <c:when test="${mailBoxType == '/mail/3'}">
                                <td style="text-align: center;"><a href="javascript:clearMail(3, ${message.mailUid})">영구 삭제</a></td>
                            </c:when>
                            <c:when test="${mailBoxType == '/mail/4'}">
                                <td style="text-align: center;"><a href="javascript:clearMail(4, ${message.mailUid})">영구 삭제</a></td>
                            </c:when>
                        </c:choose>
                        <!-- 휴지통 화면에서 복구 버튼 활성화 -->
                        <c:choose>
                            <c:when test="${mailBoxType == '/mail/4'}">
                                <td style="text-align: center;"><a href="javascript:restoreMail(${message.mailboxMailboxId}, ${message.mailUid})">복구</a></td>
                            </c:when>
                        </c:choose>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div> 페이지
                <%-- page 수는 total/size의 나머지가 0 이면 +1 안하고, 0이 아니면 +1 해준다. --%>

                <c:forEach var="i" begin="1" end="${ (total % size == 0) ? total / size : total / size + 1}" step="1">
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
