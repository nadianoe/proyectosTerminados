function imprimirTabla(fechas) {
    var tabla =
        "<tr>\n" +
        "<th>teams</th>\n" +
        "<th>date</th>\n" +
        "</tr>\n";

    tabla += "<tbody>\n";

    for (var i = 0; i < fechas.length; i++) {

        tabla += "<tr>\n";
        tabla += "<td>" + fechas[i].equipoUno + "-" +
            fechas[i].equipoDos + "</td>\n";
        tabla += "<td>" + fechas[i].mes + ", " +
            fechas[i].dia + "<br>";
        tabla += fechas[i].horario + ", " +
            fechas[i].lugar + "<br>"

        tabla +=

            "<a class='redirection' href='" + fechas[i].linkGoogleMaps + "'>" +
            "<img src='../imagenes/googleMaps.jpg' alt='HTML tutorial'>" + "</a>" + "                       " +

            "<a class='redirection' href='https://www.google.com/calendar'>\n" +
            "<img src='../imagenes/calendar.jpg' alt='HTML tutorial'>" +
            "</a>\n" +

            "<img class='redirection' src='../imagenes/iconoMen.jpg'" +
            "onclick=" + "showPage('chat')" + " alt='chat'>\n" +

            "</td>\n" +
            "</tr>";

    }

    tabla += "</tbody>\n";

    document.getElementById('tablaDeFechas').innerHTML = tabla;

}

function showPage(namePage) {

    if (namePage == "gameRules") {
        document.getElementById("contenidoPrincipal").innerHTML = gameRules;
    }

    if (namePage == "about") {
        document.getElementById("contenidoPrincipal").innerHTML = about;
    }

    if (namePage == "login") {
        document.getElementById("contenidoPrincipal").innerHTML = login;
    }

    if (namePage == "schedule") {
        document.getElementById("contenidoPrincipal").innerHTML = schedule;
    }

    if (namePage == "nextGame") {
        document.getElementById("contenidoPrincipal").innerHTML = nextGame;
    }

    if (namePage == "actualGame") {
        document.getElementById("contenidoPrincipal").innerHTML = actualGame;
    }

    if (namePage == "chat") {
        document.getElementById("contenidoPrincipal").innerHTML = chat;
    }
}

function ingresar() {

    var email = document.getElementById("email").value;
    var password = document.getElementById("password").value;
    loginUser(email, password);

    showPrivateInfo()
    return console.log('user ingresado');
}

function registrarse() {
    var email = document.getElementById("email").value;
    var password = document.getElementById("password").value;
    createUser(email, password);

    showPrivateInfo()
    return console.log('user registrado');
}

function createUser(email, password) {

    console.log('Creando el usuario con email ' + email);

    firebase.auth().createUserWithEmailAndPassword(email, password);
}

function loginUser(email, password) {

    console.log('Loging user ' + email);

    firebase.auth().signInWithEmailAndPassword(email, password);

}

function signoutUser() {
    firebase.auth().signOut();

    showLoginForm()
    returnconsole.log('No habemus user 😭');
}

function showPrivateInfo(user) {
    var loginForm = document.getElementById('formLogin');
    loginForm.style.display = 'none';

    var privateInfo = document.getElementById('privateInfo');
    privateInfo.style.display = 'block';
    privateInfo.innerHTML =

        "Información confidencial\n" +

        '<button type="button" name="logout" onclick="signoutUser()" >Logout</button><br>';
}

function showLoginForm() {
    var loginForm = document.getElementById('formLogin');
    loginForm.style.display = 'block';

    var privateInfo = document.getElementById('privateInfo');
    privateInfo.style.display = 'none';
    privateInfo.innerHTML = "Nada que mostrar, tienes que registrarte";
}

function enviarMensaje() {
    var mensaje = document.getElementById("mensaje").value;
    console.log(mensaje);
    document.getElementById("mensaje").value = " ";
}

function guardarMensaje() {
    var mensajeEnviado = document.getElementById("mensaje").value;
    baseDeDatosMensajes.ref("todosLosMensajes").push({
        mensaje: mensajeEnviado
    });
    console.log("se ha enviado un mensaje");
    document.getElementById("mensaje").value = " ";
}

