<%--
  Created by IntelliJ IDEA.
  User: cmson
  Date: 24. 4. 10.
  Time: 오후 8:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<head>
    <link type="text/css" rel="stylesheet" href="../css/sign_up.css"/>
</head>
<!DOCTYPE html>
<html lang="ko">

<head>
    <%@ include file="../fragments/head.jspf" %>
    <style>
        .form-group {
            margin-bottom: 15px;
            position: relative;

            width: 80%;
            margin: auto;
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
        #requirements{
            font-size: 12px;
            opacity: 70%;
            display: inline;
        }

        .container {
            background-color: #fff;
            padding: 70px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            max-width: 600px;
            width: 100%;
            position: relative;
            top:180px;
        }
        .form-group {
            margin-bottom: 25px;
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

        h1 {
            text-align: center;
            margin-bottom: 45px;
            font-size: 28px;
        }
        button {
            width: 30%;
            margin: auto;
            position: relative;
            left:160px;
            height: 63px;
        }
        label{
            font-size: 15px;
            font-weight: bolder;
        }
    </style>
    <title>Title</title>
</head>
<body>

<div class="container">
    <div class="row">
        <div class="col-md-12">
            <h1>회원가입</h1>
            <form action="/auth/signup.do" method="post" id="signup-form">
                <sec:csrfInput />
                <div class="form-group col-md-6">
                    <label for="username">아이디</label>
                    <input type="text" class="form-control" id="username" name="username" placeholder="아이디를 입력하세요" oninput="validateUsername()">
                </div>

                <div class="form-group col-md-6">
                    <label for="password">비밀번호</label>
                    <div id="requirements">
                        <span id="uppercase" style="color: gray;">대문자</span>,
                        <span id="lowercase" style="color: gray;">소문자</span>,
                        <span id="number" style="color: gray;">숫자</span>,
                        <span id="special" style="color: gray;">특수문자</span>,
                        <span id="length" style="color: gray;">8자리 이상</span>
                    </div>
                    <input type="password" class="form-control" id="password" name="password" placeholder="비밀번호를 입력하세요" oninput="checkPasswordStrength(); checkPasswords(); checkPasswordStrengthtwo();">

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

        if (password.match(/[a-z]/)) {
            strength += 1;
        }
        if (password.match(/[A-Z]/)) {
            strength += 1;
        }
        if (password.match(/[0-9]/)) {
            strength += 1;
        }
        if (password.match(/[\W]/)) {
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
                strengthBar.style.width = '20%';
                strengthBar.style.backgroundColor = 'red';
                break;
            case 2:
                strengthBar.style.width = '40%';
                strengthBar.style.backgroundColor = 'orange';
                break;
            case 3:
                strengthBar.style.width = '60%';
                strengthBar.style.backgroundColor = 'yellow';
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

    function checkPasswordStrengthtwo() {
        var password = document.getElementById('password').value;
        var lowercaseSpan = document.getElementById('lowercase');
        var uppercaseSpan = document.getElementById('uppercase');
        var numberSpan = document.getElementById('number');
        var specialSpan = document.getElementById('special');
        var lengthSpan = document.getElementById('length');

        // 소문자 검사
        if (password.match(/[a-z]/)) {
            lowercaseSpan.style.color = 'blue';
        } else {
            lowercaseSpan.style.color = 'gray';
        }

        // 대문자 검사
        if (password.match(/[A-Z]/)) {
            uppercaseSpan.style.color = 'blue';
        } else {
            uppercaseSpan.style.color = 'gray';
        }

        // 숫자 검사
        if (password.match(/[0-9]/)) {
            numberSpan.style.color = 'blue';
        } else {
            numberSpan.style.color = 'gray';
        }

        // 특수문자 검사
        if (password.match(/[\W]/)) {
            specialSpan.style.color = 'blue';
        } else {
            specialSpan.style.color = 'gray';
        }

        // 길이 검사
        if (password.length >= 8) {
            lengthSpan.style.color = 'blue';
        } else {
            lengthSpan.style.color = 'gray';
        }
    }

</script>

</body>
</html>
