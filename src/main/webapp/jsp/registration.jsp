<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Регистрация - Open Space Chat</title>
    <link href = "../css/style.css" rel = "stylesheet">
</head>
<body>
    <h1 align="center">РЕГИСТРАЦИЯ</h1>

    <form class = "reg-form" id = "form" method = "POST">

        <div class = "reg-elem">
            <label>Логин: </label>
            <input type = 'text' required name = "login">
        </div>

        <div class = "reg-elem">
            <label>Пароль: </label>
            <input type = 'password' required name = "password">
        </div>

        <div class = "reg-elem">
            <label>Имя: </label>
            <input type = 'text' required name = "name">
        </div>

        <div class = "reg-elem">
            <label>Фамилия: </label>
            <input type = 'text' name = "surname">
        </div>

        <div class = "reg-elem">
            <label>Страна: </label>
            <input type = 'text' name = "country">
        </div>
        <br>

        <div align = "center">
            <button>Зарегестрироваться</button>
        </div>

    </form>
    
    <br>
    <br>
    <br>

    <%@ include file="../html/copyright.html" %>
</body>
</html>
