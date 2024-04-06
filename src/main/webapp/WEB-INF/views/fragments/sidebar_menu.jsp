<%-- 
    Document   : sidebar_menu
    Created on : 2022. 6. 10., 오후 3:25:30
    Author     : skylo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="d-flex flex-column flex-shrink-0 p-3 bg-body-tertiary" style="width: 280px;">
    <a href="/write_mail" class="d-flex align-items-center mb-3 mb-md-0 me-md-auto link-body-emphasis text-decoration-none">
        <svg class="bi pe-none me-2" width="40" height="32">
            <use xlink:href="#bootstrap"/>
        </svg>
        <span class="fs-4">편지 쓰기</span>
    </a>
    <hr>
    <ul class="nav nav-pills flex-column mb-auto">
        <li class="nav-item">
            <a href="/mail" class="nav-link active" aria-current="page">
                <svg class="bi pe-none me-2" width="16" height="16">
                    <use xlink:href="#home"/>
                </svg>
                받은 편지함
            </a>
        </li>
        <li>
            <a href="/mail" class="nav-link link-body-emphasis">
                <svg class="bi pe-none me-2" width="16" height="16">
                    <use xlink:href="#speedometer2"/>
                </svg>
                중요 편지함
            </a>
        </li>
        <li>
            <a href="/mail" class="nav-link link-body-emphasis">
                <svg class="bi pe-none me-2" width="16" height="16">
                    <use xlink:href="#table"/>
                </svg>
                보낸 편지함
            </a>
        </li>
        <li>
            <a href="/mail" class="nav-link link-body-emphasis">
                <svg class="bi pe-none me-2" width="16" height="16">
                    <use xlink:href="#grid"/>
                </svg>
                임시 보관함
            </a>
        </li>
        <li>
            <a href="/mail" class="nav-link link-body-emphasis">
                <svg class="bi pe-none me-2" width="16" height="16">
                    <use xlink:href="#people-circle"/>
                </svg>
                휴지통
            </a>
        </li>
    </ul>
</div>
