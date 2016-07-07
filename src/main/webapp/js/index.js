function User(id, login, password, name, surname, country) {
    this.id = id;
    this.login = login;
    this.password = password;
    this.name = name;
    this.surname = surname;
    this.country = country;
}

var user = null;

function addUser(id, login, password, name, surname, country) {
    user = new User(id, login, password, name, surname, country);
}

function sendMessage(login) {
    var message = document.getElementById('message');
    sendMessageServer(createJsonMessage(user, message.value));
    message.value = "";
}

function createJsonMessage(user, message) {
    return "{\"autor\":" + JSON.stringify(user) + ",\"message\":\"" + message + "\"}";
}