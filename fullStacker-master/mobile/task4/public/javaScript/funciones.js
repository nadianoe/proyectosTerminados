function showPage(namePage) {

    if (namePage == "gameRules") {

        gameRules.style.display = "block";
        about.style.display = "none";
        login.style.display = "none";
        schedule.style.display = "none";
        nextGame.style.display = "none";
        actualGame.style.display = "none";
        chat.style.display = "none";
        mapa.style.display = "none";
        preferencias.style.display = "none";

    }

    if (namePage == "about") {

        gameRules.style.display = "none";
        about.style.display = "block";
        login.style.display = "none";
        schedule.style.display = "none";
        nextGame.style.display = "none";
        actualGame.style.display = "none";
        chat.style.display = "none";
        mapa.style.display = "none";
        preferencias.style.display = "none";

    }

    if (namePage == "login") {

        gameRules.style.display = "none";
        about.style.display = "none";
        login.style.display = "block";
        schedule.style.display = "none";
        nextGame.style.display = "none";
        actualGame.style.display = "none";
        chat.style.display = "none";
        mapa.style.display = "none";
        preferencias.style.display = "none";

    }

    if (namePage == "schedule") {

        gameRules.style.display = "none";
        about.style.display = "none";
        login.style.display = "none";
        schedule.style.display = "block";
        nextGame.style.display = "none";
        actualGame.style.display = "none";
        chat.style.display = "none";
        mapa.style.display = "none";
        preferencias.style.display = "none";

    }

    if (namePage == "nextGame") {

        gameRules.style.display = "none";
        about.style.display = "none";
        login.style.display = "none";
        schedule.style.display = "none";
        nextGame.style.display = "block";
        actualGame.style.display = "none";
        chat.style.display = "none";
        mapa.style.display = "none";
        preferencias.style.display = "none";

    }

    if (namePage == "actualGame") {

        gameRules.style.display = "none";
        about.style.display = "none";
        login.style.display = "none";
        schedule.style.display = "none";
        nextGame.style.display = "none";
        actualGame.style.display = "block";
        chat.style.display = "none";
        mapa.style.display = "none";
        preferencias.style.display = "none";
    }

    if (namePage == "chat") {

        gameRules.style.display = "none";
        about.style.display = "none";
        login.style.display = "none";
        schedule.style.display = "none";
        nextGame.style.display = "none";
        actualGame.style.display = "none";
        chat.style.display = "block";
        mapa.style.display = "none";
        preferencias.style.display = "none";

    }

    if (namePage == "mapa") {

        gameRules.style.display = "none";
        about.style.display = "none";
        login.style.display = "none";
        schedule.style.display = "none";
        nextGame.style.display = "none";
        actualGame.style.display = "none";
        chat.style.display = "none";
        mapa.style.display = "block";
        preferencias.style.display = "none";

    }

    if (namePage == "preferencias") {

        gameRules.style.display = "none";
        about.style.display = "none";
        login.style.display = "none";
        schedule.style.display = "none";
        nextGame.style.display = "none";
        actualGame.style.display = "none";
        chat.style.display = "none";
        mapa.style.display = "block";
        preferencias.style.display = "block";

    }
}

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

            "<img class='redirection'  src='../imagenes/googleMaps.jpg' onclick=showPage('mapa') alt='HTML tutorial'>" +
            "                       " +

            "<a class='redirection' href='https://www.google.com/calendar'>" +
            "<img src='../imagenes/calendar.jpg' alt='HTML tutorial'>" +
            "</a>" +

            "<img class='redirection' src='../imagenes/iconoMen.jpg'" +
            "onclick=" + "showPage('chat')" + " alt='chat'>\n" +

            "</td>\n" +
            "</tr>";

    }

    tabla += "</tbody>\n";

    document.getElementById('tablaDeFechas').innerHTML = tabla;

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
    return console.log("el usuario ha salido");
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
    var mensajeEnviado = document.getElementById("mensaje").value;
    baseDeDatosMensajes.ref("todosLosMensajes").push({
        mensaje: mensajeEnviado
    });

    console.log("se ha enviado un mensaje");
    document.getElementById("mensaje").value = " ";
}

function almacenarMensaje() {

    var displayLaterMassage = document.getElementById("sendLater").checked;

    connectedRef.on("value", function (snap) {

        if (snap.val() === false && displayLaterMassage == true) {
            
            seEnvioMensajeOffline = true;
            
            nroMensaje = nroMensaje + 1;

            var idMensaje = "mensajeAlmacenado" + nroMensaje;

            var mensaje = document.getElementById("mensaje").value;

            localStorage.setItem(idMensaje, mensaje);  

            console.log("se ha almacenado un mensaje");

            document.getElementById("mensaje").value = " ";

        }
    });
}

function updateSendLater() {

    var valor = document.getElementById("sendLater").checked;

    localStorage.setItem("checkboxTurnedOn", valor);

}

function enviarMensajesAlmacenados() {
    
    for (var i = 1; i <= nroMensaje ; i++){
        
        var idMensaje = "mensajeAlmacenado" + i ;
        
        var mensaje = localStorage.getItem(idMensaje);
        localStorage.removeItem(idMensaje);
        nroMensaje = nroMensaje -1;
        
        baseDeDatosMensajes.ref("todosLosMensajes").push({
        mensaje: mensaje
    
    });
        console.log("mensaje almacenado enviado");
  }     
}

function offLine() {
    
     var displayLaterMassage = document.getElementById("sendLater").checked;
    
    var valorFrecuencia = localStorage.getItem("valorFrecuencia");
    
    valorFrecuencia = 60000 * valorFrecuencia;
    
        if (displayLaterMassage == true) {
            console.log("comienza time out");
           setTimeout(enviarMensajesAlmacenados, valorFrecuencia);

        }
   
    
}

function casoOffline() {

    var displayLaterMassage = document.getElementById("sendLater").checked;
    
    var valorFrecuencia = localStorage.getItem("valorFrecuencia");
    
    valorFrecuencia = 60000 * valorFrecuencia;
    
    connectedRef.on("value", function (snap) {

        if (snap.val() === false && displayLaterMassage == true) {
            
           setTimeout(enviarMensajesAlmacenados, valorFrecuencia);

        }
    });
}

function updateFrecuencia() {

    var valorFrecuencia = document.getElementById("minFrecuencia").value;
    
    localStorage.setItem("valorFrecuencia",valorFrecuencia);
    
}