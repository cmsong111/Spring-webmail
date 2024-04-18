<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="deu.cse.spring_webmail.mail.dto.MailBoxType" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%--
    Document   : sidebar_menu
    Created on : 2022. 6. 10., 오후 3:25:30
    Author     : skylo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>


<%
    Map<String, String> menuMap = new HashMap<String, String>();
    for (MailBoxType type : MailBoxType.values()) {
        menuMap.put(type.getDescription(), "/mail/" + type.getValue());
    }
    pageContext.setAttribute("menuList", menuMap);
%>

<div class="d-flex flex-column flex-shrink-0 p-3 bg-body-tertiary" style="width: 280px;">
    <a href="/write_mail" class="d-flex align-items-center mb-3 mb-md-0 me-md-auto link-body-emphasis text-decoration-none">
        <svg class="bi pe-none me-2" width="40" height="32">
            <use xlink:href="#bootstrap"/>
        </svg>
        <span class="fs-4">편지 쓰기</span>
    </a>
    <hr>
    <ul class="nav nav-pills flex-column mb-auto">
        <c:forEach var="menu" items="${menuList}">
            <li class="nav-item">
                <c:if test="${menu.value eq  mailBoxType}">
                    <a href="${menu.value}" class="nav-link active" aria-current="page">
                            ${menu.key}
                    </a>
                </c:if>
                <c:if test="${menu.value ne mailBoxType}">
                    <a href="${menu.value}" class="nav-link link-body-emphasis">
                            ${menu.key}
                    </a>
                </c:if>
            </li>
        </c:forEach>
    </ul>
</div>
