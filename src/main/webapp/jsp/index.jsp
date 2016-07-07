<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Главная - Open Space Chat</title>
    <script src = "../js/index.js"></script>
    <script src = "../js/websocketclient.js"></script>
</head>
<body>

    <script type="text/javascript">addUser(<%= request.getAttribute("id")%>,
            '<%= request.getAttribute("login")%>', '<%= request.getAttribute("password")%>',
            '<%= request.getAttribute("name")%>', '<%= request.getAttribute("surname")%>',
            '<%= request.getAttribute("country")%>')</script>

    <form align = "right" action="/sign_in" method="GET">
        <input type="submit" value="Выйти">
    </form>

    <h1 align="center">OPEN SPACE CHAT</h1>
    <br>
    <h2 align = "center">Пользователь: ${user_name}</h2>
    
    <div align = "center">
        <textarea cols="100" rows="25" readonly="readonly" name = "chat" id = "chat"></textarea>
        <br>
        <textarea cols="100" rows="3" name = "message" id = "message"></textarea>
        <br>
        <button onclick = "sendMessage('<%= request.getAttribute("login")%>')">Отправить</button>
    </div>
    
    <br>
    <br>
    <br>

    <%@ include file="../html/copyright.html" %>
</body>
</html>
