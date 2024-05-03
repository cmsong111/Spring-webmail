<%--
  Created by IntelliJ IDEA.
  User: cmson
  Date: 24. 4. 10.
  Time: 오후 8:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="ko">

<head>
    <%@ include file="../fragments/head.jspf" %>
    <style>
        .form-group {
            margin-bottom: 15px;
        }
        .password-match, .password-not-match {
            font-size: 0.85em; /* Smaller font size */
            display: none;
        }
        .password-match {
            color: green;
        }
        .password-not-match {
            color: red;
        }
        .password-strength-bar {
            height: 10px;
            width: 0%;
            background-color: red;
            transition: width 0.3s;
        }
        #password_warning{
            font-size: 10px;
            opacity: 70%;
        }
    </style>
    <title>Title</title>
</head>
<body>
<%@ include file="../fragments/header.jspf" %>

<div class="container">
    <div class="row">
        <div class="col-md-12">
            <h1>회원가입</h1>
            <form action="/auth/signup.do" method="post" id="signup-form">
                <sec:csrfInput />
                <div class="form-group col-md-6">
                    <label for="username">아이디</label>
                    <input type="text" class="form-control" id="username" name="username" placeholder="아이디를 입력하세요">
                </div>

                <div class="form-group col-md-6">
                    <label for="password">비밀번호</label>
                    <label id="password_warning">대소문자,숫자,특수문자 포함 8자리이상</label>
                    <input type="password" class="form-control" id="password" name="password" placeholder="비밀번호를 입력하세요" oninput="checkPasswordStrength(); checkPasswords();">
                </div>

                <div class="form-group col-md-6">
                    <label for="confirm-password">비밀번호 확인</label>

                    <input type="password" class="form-control" id="confirm-password" name="confirm-password" placeholder="비밀번호를 다시 입력하세요" oninput="checkPasswords()">
                    <span class="password-match">비밀번호가 일치합니다</span>
                    <span class="password-not-match">비밀번호가 일치하지 않습니다</span>
                </div>

                <div class="form-group col-md-6">
                    <div class="password-strength-bar" id="password-strength-bar"></div>
                </div>

                <button type="submit" class="btn btn-primary" id="submit-button" disabled>회원가입</button>
            </form>
        </div>
    </div>
</div>

<script>
    function checkPasswordStrength() {
        var strengthBar = document.getElementById('password-strength-bar');
        var password = document.getElementById('password').value;
        var strength = 0;

        if (password.match(/[a-z]+/)) {
            strength += 1;
        }
        if (password.match(/[A-Z]+/)) {
            strength += 1;
        }
        if (password.match(/[0-9]+/)) {
            strength += 1;
        }
        if (password.match(/[\W]+/)) {
            strength += 1;
        }
        if (password.length >= 8) {
            strength += 1;
        }

        switch(strength) {
            case 0:
                strengthBar.style.width = '0%';
                strengthBar.style.backgroundColor = 'red';
                break;
            case 1:
            case 2:
                strengthBar.style.width = '40%';
                strengthBar.style.backgroundColor = 'red';
                break;
            case 3:
                strengthBar.style.width = '60%';
                strengthBar.style.backgroundColor = 'orange';
                break;
            case 4:
                strengthBar.style.width = '80%';
                strengthBar.style.backgroundColor = 'lightgreen';
                break;
            case 5:
                strengthBar.style.width = '100%';
                strengthBar.style.backgroundColor = 'green';
                break;
        }
    }

    function checkPasswords() {
        var password = document.getElementById("password").value;
        var confirmPassword = document.getElementById("confirm-password").value;
        var matchMessage = document.querySelector('.password-match');
        var notMatchMessage = document.querySelector('.password-not-match');
        var submitButton = document.getElementById("submit-button");

        if (password === confirmPassword && password.length >= 8 && password.match(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[\W]).+$/)) {
            matchMessage.style.display = 'block';
            notMatchMessage.style.display = 'none';
            submitButton.disabled = false;
        } else {
            matchMessage.style.display = 'none';
            notMatchMessage.style.display = 'block';
            submitButton.disabled = true;
        }
    }
</script>

</body>
</html>
