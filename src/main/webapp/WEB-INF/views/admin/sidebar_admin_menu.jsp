<%-- 
    Document   : sidebar_admin_menu.jsp
    Author     : jongmin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<div class="d-flex flex-column flex-shrink-0 p-3 bg-body-tertiary" style="width: 280px;">
    <ul class="nav nav-pills flex-column mb-auto">
        <li class="nav-item">
            <a href="/add_user" class="nav-link active" aria-current="page">
                <svg class="bi pe-none me-2" width="16" height="16">
                    <use xlink:href="#home"/>
                </svg>
                사용자 추가
            </a>
        </li>
        <li>
            <a href="/delete_user" class="nav-link link-body-emphasis">
                <svg class="bi pe-none me-2" width="16" height="16">
                    <use xlink:href="#speedometer2"/>
                </svg>
                사용자 제거
            </a>
        </li>
        <li>
            <a href="/logout" class="nav-link link-body-emphasis">
                <svg class="bi pe-none me-2" width="16" height="16">
                    <use xlink:href="#speedometer2"/>
                </svg>
                로그아웃
            </a>
        </li>
    </ul>
</div>
