<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Войти в систему - Open Space Chat</title>
    <link href = "../css/style.css" rel = "stylesheet">
</head>
<body>
    <h1 align="center">ВОЙТИ В СИСТЕМУ</h1>

    <form class = "sign-in-form" id = "form" method="POST">

        <div class = "sign-in-elem">
            <label>Логин: </label>
            <input type = 'text' required name = "login">
        </div>

        <div class = "sign-in-elem">
            <label>Пароль: </label>
            <input type = 'password' required name = "password">
        </div>
        <br>

        <div align = "center">
            <button>Войти</button>
        </div>

    </form>

    <br>

    <form align = "center" action="/registration" method="GET">
        <input type="submit" value="Зарегистрироваться">
    </form>
    
    <br>
    <br>
    <br>

    <%@ include file="../html/copyright.html" %>
</body>
</html>
