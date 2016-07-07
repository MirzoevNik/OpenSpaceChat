var webSocket = new WebSocket("ws://localhost:8080/websocket");

webSocket.onopen = function() {
    console.log("websocket is opened");
};

webSocket.onclose = function() {
    console.log("websocket is closed");
};

webSocket.onerror = function() {
    console.log("error websocket");
};

webSocket.onmessage = function(message) {
    var chat = document.getElementById('chat');
    chat.value += message.data;
};

function sendMessageServer(message) {
    webSocket.send(message);
}
