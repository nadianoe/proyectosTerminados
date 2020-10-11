
function displayInfoGames(arrayInfoGames, player) {

    var gameInfo = "<br>\n";

    for (var i = 0; i < arrayInfoGames.length; i++) {

        var gamePlayers = arrayInfoGames[i].gamePlayers;
        
        var usernamesPlayers = gamePlayers.map(function (aGamePlayer) {
            return aGamePlayer.player.username;
        });
            
        var time = arrayInfoGames[i].created;
        var date = new Date(time);
        
        if (player != null && usernamesPlayers.includes(player.username)){
            
            var gamePlayerId; 

            for (var aGamePlayer of gamePlayers){
                if(aGamePlayer.player.username == player.username){
                    gamePlayerId = aGamePlayer.id;
                }
            }  
            
            var link = "<p> <a href=' "
            link = link + "https://salvogameapp.herokuapp.com/";
            link = link + gamePlayerId + " '> ";

            var textLink = "gameDate: " + date.toString() + ", \n";
            textLink = textLink + "integrantes: " + usernamesPlayers.join(", ");

            link = link + textLink;
            link = link + "</a></p>";
            
            gameInfo = gameInfo + link;
            
        } else {
            
            var text = "<p>gameDate: " + date.toString() + ", \n";
            text = text + "integrantes: " + usernamesPlayers.join(", "); 
            text = text + "</p>";
            
            gameInfo = gameInfo + text;
            
        }
        
        if(gamePlayers.length == 1){
            
            gameInfo = gameInfo + "<input onclick='joinGame(this)'";
            gameInfo = gameInfo + "data-gameId=";
            gameInfo = gameInfo + arrayInfoGames[i].id;
            gameInfo = gameInfo + " type='button' value='JOIN GAME'>\n<br>";
            
        }

    }

    gamesList.innerHTML = gameInfo;
    
}

function loginState(arrayInfoGames, player) {

    loginForm.style.display = "none";
    logoutButton.style.display = "block";
    var saludo = "<p> Hi, " + player.username + ". You are logged in.</p>";
    infoGamePlayer.innerHTML = saludo;
    infoGamePlayer.style.display = "block";
    gamesList.style.display = "block";
    createGameButton.style.display = "block";
    displayInfoGames(arrayInfoGames,player);

}

function logoutState() {

    location.reload;
    loginForm.style.display = "block";
    infoGamePlayer.style.display = "none";
    logoutButton.style.display = "none";
    gamesList.style.display = "none";
    createGameButton.style.display = "none";

    if (window.location.href != "https://salvogameapp.herokuapp.com/games"){
        window.location.replace("https://salvogameapp.herokuapp.com/games");
    }
}

function singup() {

    var userNewPlayer = document.getElementById("username").value;
    var passNewPlayer = document.getElementById("password").value;

    $.ajax({
            url: "/api/players",
            type: 'POST',
            data: {
                username: userNewPlayer,
                password: passNewPlayer
            }
        })
        .done(function (data) {

            console.log(data);

            if (data.success) {
                console.log("usuario registrado!");
                console.log("iniciando sesión...");
                login();
            } else {
                console.log("username in use");
            }

        })
        .fail(function (qXHR, textStatus, errorThrown) {
            alert("no se pudo registrar el usuario laksdjfa")

        });
}

function login() {

    var userPlayer = document.getElementById("username").value;
    var passPlayer = document.getElementById("password").value;
    
    location.reload;

    $.ajax({
            url: "/api/login",
            type: 'POST',
            data: {
                username: userPlayer,
                password: passPlayer
            }
        })
        .done(function () {
            
            userPlayer = "";
            passPlayer = "";
        
            console.log("El usuario ha ingresado!");
            console.log("obteniendo datos de juegos ...")

            $.ajax({
                    url: "/api/games",
                    type: 'GET'
                })
                .done(function (data) {
                    console.log(data);
                
                    if (data.player != null) {
                        console.log("usuario conectado detectado..");
                        loginState(data.games, data.player);
                        console.log("listo");
                    }
                })
                .fail(function () {
                    console.log("error, no se pudo obtener datos");
                });

        })
        .fail(function (qXHR, textStatus, errorThrown) {
            alert("error, no se pudo iniciar sesión.");
        });

}

function logout() {
    
    $.ajax({
            url: "/api/logout",
            type: 'GET',
            datatype: 'json'
        })
        .done(function () {
            
                window.location.replace("https://salvogameapp.herokuapp.com/games");

                console.log("El usuario ha cerrado sesión!");
                $.ajax({
                    url: "/api/games",
                    type: 'GET'
                })
                .done(function (data) {

                        console.log("game data obtenida");
                        logoutState();

                    })
                .fail(function () {
                    console.log("error, no se pudo obtener datos");
                });

        })
.fail(function (qXHR, textStatus, errorThrown) {
    console.log("error, no se pudo cerrar sesión");
});
}

function createGame() {

    $.ajax({
            url: "/api/games",
            type: 'POST'
        })
        .done(function (data) {
            
            console.log("Se ha creado el juego!");
            console.log(data);
            var gpid = data.gpid;
            var url = "https://salvogameapp.herokuapp.com/" + gpid;
            window.location.href = url; 
            displayInfoGames(data.games, data.player);
            
        })
        .fail(function (qXHR, textStatus, errorThrown) {
            alert("error, no se pudo iniciar sesión.");
        });
    
}

function joinGame(elementoHtml){
    
    var gameId = parseInt(elementoHtml.getAttribute("data-gameId"));
    var url = "/api/games/" + gameId + "/players";
    
    $.ajax({
            url: url,
            type: 'POST',
            data: { gameIdnn: gameId }
        })
        .done(function(data) {
            
            console.log("Se ha agregado un game player!");
            var gpid = data.gpid;
            console.log("obteniendo datos de juegos ...");
        

            $.ajax({
                url: "/api/games",
                type: 'GET'
            })
            .done(function (data) {

                console.log(data);

                if (data.player != null) {
                    console.log("usuario conectado detectado..");
                    displayInfoGames(data.games, data.player);
                   window.location.replace("https://salvogameapp.herokuapp.com/" + gpid);
                }
            })
            .fail(function () {
                console.log("error, no se pudo obtener datos de juego");
            });

        })
        .fail(function (qXHR, textStatus, errorThrown) {
            alert("error, no se pudo agregar jugador al juego");
        });
    
}





