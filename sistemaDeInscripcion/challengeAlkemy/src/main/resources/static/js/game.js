
var currentURL = window.location.href;
var valorActual = currentURL.replace("https://salvogameapp.herokuapp.com/","");
var urlBuscada = "/api/game_view/" + valorActual;
var gamePlayerId;
var barcosColocados;
var barcosPorColocar;
var jugadorQueDisparaPrimero;
var jugadorDeTurno;


$.ajax({
            url: urlBuscada,
            type: 'GET',
            datatype: 'json'
        })
        .done(function(data) {

            console.log(data);

            userLoggedIn = data.userLoggedIn;
            userOpponent = data.userOpponent;

            jugadorDeTurno = data.infoGameState.jugadorDeTurno;

            gamePlayerId = valorActual;
            nroTurno = ultimoTurnoRealizado(data.infoGame.salvoes);

            var hitsToMe = data.infoGame.hits.toMe;
            var hitsLocationsToMe = data.infoGame.hits.hitLocationsToMe;
            var sinksToMe = data.infoGame.sinks.toMe;

            var hitsToOpponent = data.infoGame.hits.toOpponent;
            var hitsLocationsToOpponent = data.infoGame.hits.hitLocationsToOpponent;
            var sinksToOpponent = data.infoGame.sinks.toOpponent;

            aparecerCreateGameButton();
            imprimirInfoPlayers(data.infoGame.gamePlayers,userLoggedIn);
            var titulo = "own ships and salvoes received";
            dibujarGrilla("ownShipsSalvoesReceived",titulo,"upperCaseId");
            pintarBarquitos(data.infoGame.ships);
            barcosColocados = data.infoGameState.myShipsPlaced;
            setBarcosPorColocar();

            if(data.infoGameState.allShipsArePlaced == true){

                dibujarGrilla("salvoesFired","salvoes fired","lowerCaseId");
                pintarDisparosRecibidos(data.infoGame.salvoes);
                pintarDisparosRealizados(data.infoGame.salvoes,hitsLocationsToOpponent);
                createInfoTable(hitsToMe,sinksToMe,userLoggedIn,"tableGamePlayer1");
                createInfoTable(hitsToOpponent,sinksToOpponent,userOpponent,"tableGamePlayer2");
                
                if (data.infoGameState.theGameIsOver == false){

                        if(jugadorDeTurno == userLoggedIn){

                            mensaje.innerHTML = "Es su turno. Presione disparar para comenzar a hacerlo!";
                            botonDisparar.style.display = "block";
                        
                        }else{

                            botonDisparar.style.display = "none";
                            mensaje.innerHTML = "Es el turno de su oponente. Espere diez segundos para que su oponente dispare";
                            setTimeout(function(){ 
                                document.location.reload(); 
                            }, 10000);
                        }

                }else{

                    var ganador = data.infoGameState.ganador;
                    alert("El juego ha terminado!! El ganador es: " + ganador);
                    mensaje.innerHTML = "El juego ha terminado!! El ganador es: " + ganador;
                    botonDisparar.style.display = "none";
                    shipsButton.style.display = "none";
                }

            }else if(data.infoGameState.placingShips == true){
                shipsButton.style.display = "block";
                alert("coloque todos sus barcos!!");

            }else if(userOpponent != null){

                shipsButton.style.display = "none";
                mensaje.innerHTML = " esperando que el oponente termine de colocar sus barcos";
                setTimeout(function(){
                    document.location.reload(); 
                }, 17000);

            }else{

                shipsButton.style.display = "none";
                mensaje.innerHTML = " esperando que se conecte un oponente.";
                setTimeout(function(){
                    document.location.reload(); 
                }, 5000);
            }


            })
            .fail(function(jqXHR, textStatus, errorThrown) {
            
                setTimeout(function(){ 
                            document.location.reload(); 
                            }, 3000);
            });

function agregarSalvoVacioPorPerdidaDeTurno(){
    var arregloTurnoPerdido = [];
    arregloTurnoPerdido.push("turnoPerdido");
    arregloTurnoPerdido.push("turnoPerdido");
    arregloTurnoPerdido.push("turnoPerdido");
    arregloTurnoPerdido.push("turnoPerdido");
    arregloTurnoPerdido.push("turnoPerdido");
    
    actualSalvo = newSalvo(nroTurno,arregloTurnoPerdido);
    addSalvoToGamePlayer(actualSalvo,valorActual);
    locations = [];
    actualSalvo = {};
    typeOfPrint = "";
    botonDone.style.display = "none";
    infoDisparos.style.display = "none";
}
function setBarcosPorColocar(){
    var todosLosBarcos = ["CARRIER", "BATTLESHIP", "SUBMARINE","DESTROYER","PATROLBOAT"];
    barcosPorColocar = eliminarElementos(todosLosBarcos,barcosColocados);
}
function dibujarGrilla(idLugar,tituloGrilla,typeLetterCaseId){

	var titulo = "<h3>" + tituloGrilla + "</h3>\n";

	var grilla = titulo + "<div class='row border border-info'>\n";
    grilla = grilla + "<div class='col-sm border border-dark cuadrito'></div>\n";

	for (var i = 0; i < 10; i++) {
		grilla = grilla + "<div class='col-sm border border-dark cuadrito'>";
        grilla = grilla + (i+1) + "</div>\n";	
	}

	grilla = grilla + "</div>\n";

	var letras = ["A","B","C","D","E","F","G","H","I","J"];

	for (var i = 0; i < 10; i++) {

        var letraId;

        if (typeLetterCaseId == "lowerCaseId") {
            letraId = letras[i].toLowerCase();
        } else {
            letraId =  letras[i];
        }

        grilla = grilla + "<div class='row border border-dark' ";
        grilla = grilla + "style='border-style:solid;border-width:thick'>\n";
        grilla = grilla + "<div class='col-sm border border-dark cuadrito' ";
        grilla = grilla + "style='border-style:solid;border-width:thick'>\n";
        grilla = grilla + letras[i] + "</div>\n";
        
		for (var j = 0; j < 10; j++) {

			grilla = grilla + "<div class='col-sm border border-dark cuadrito'";
            grilla = grilla + "style='background-color:skyblue;";
            grilla = grilla + "border-style:solid;border-width:thick' ";
            grilla = grilla + "onclick='paintSquare(this)' ";
            grilla = grilla + "id='" + letraId + (j + 1) + "'></div>\n";
            
		}
		
		grilla = grilla + "</div>\n";

	}

	document.getElementById(idLugar).innerHTML = grilla;
}
function pintarBarquitos(ships) {

	for (var i = 0; i < ships.length; i++) {
		for (var j = 0; j < ships[i].locations.length; j++) {
			var cuadrito = document.getElementById(ships[i].locations[j]);
			cuadrito.style.backgroundColor = "darkslategrey";
		}
	}
}
function pintarDisparosRecibidos(salvoes){
	for (var i = 0; i < salvoes.length; i++) {
		for (var j = 0; j < salvoes[i].locations.length; j++) {

            var idCuadrito = salvoes[i].locations[j];

            if(salvoes[i].gamePlayerId != valorActual && idCuadrito != "turnoPerdido"){

    			var cuadrito = document.getElementById(idCuadrito);
    			var colorCuadrito = cuadrito.style.backgroundColor;

                if(colorCuadrito == "darkslategrey"){

                    cuadrito.style.backgroundColor = "red";
                    cuadrito.innerHTML = "✖️";

                }else{

                    cuadrito.style.backgroundColor = "red";
                    cuadrito.innerHTML = salvoes[i].turn;

                }
            }
		}
	}
}
function pintarDisparosRealizados(salvoes,hitPlacesToOpponent){
    for (var i = 0; i < salvoes.length; i++) {
        for (var j = 0; j < salvoes[i].locations.length; j++) {

            var idCuadrito = salvoes[i].locations[j];

            if(salvoes[i].gamePlayerId == valorActual && (idCuadrito != "turnoPerdido")){

                idCuadrito = idCuadrito.toLowerCase();
                var cuadrito = document.getElementById(idCuadrito);

                if(hitPlacesToOpponent.includes(salvoes[i].locations[j])){

                    cuadrito.style.backgroundColor = "red";
                    cuadrito.innerHTML = "✖️";

                }else{

                    cuadrito.style.backgroundColor = "red";
                    cuadrito.innerHTML = salvoes[i].turn;

                }
            }
        }
    }
}

function imprimirInfoPlayers(gamePlayers, userLoggedIn){

	var info = "<p> Who is playing this game: </p>\n";

	for (var i = 0; i < gamePlayers.length; i++) {
        
		info = info + "<h3> player: ";
        info = info + gamePlayers[i].player.username; 
        
		if (gamePlayers[i].player.username == userLoggedIn) {
			info = info + " (you) </h3>\n";
		} else {
			info = info + "</h3>\n";
		}
	}

	document.getElementById("gameInfo").innerHTML = info;
}
/*

var salvo1 = newSalvo(5,["J1","J2","J3","J4","J5"]);
addSalvoToGamePlayer(salvo1,1);

*/
function newSalvo(turn,locationsList){
    var salvoObject = { turn : turn , locations : locationsList };
    return salvoObject;
}
function newShip(type,locationsList){
    var shipObject = { type : type , locations : locationsList };
    return shipObject;
}
function addShipListToGamePlayer(shipObjects,gamePlayerId) {
    
    var url = "/api/games/players/" + gamePlayerId + "/ships";
    var stringShipObjects = JSON.stringify(shipObjects);
    
    $.ajax({
            url: url,
            type: 'POST',
            data: stringShipObjects,
            dataType: "text",
            contentType: "application/json"
        })
        .done(function(){
            console.log("ships agregados al gamePlayerId: " + gamePlayerId);
            document.location.reload();
        })
        .fail(function (qXHR, textStatus, errorThrown) {
            console.log("error, no se pudo actualizar game view");
        });
}
function addSalvoToGamePlayer(salvoObject,gamePlayerId) {
    
    var url = "/api/games/players/" + gamePlayerId + "/salvoes";
    var stringSalvoObject = JSON.stringify(salvoObject);
    
    $.ajax({
            url: url,
            type: 'POST',
            data: stringSalvoObject,
            dataType: "text",
            contentType: "application/json"
        })
        .done(function(){
            console.log("salvo agregado y game view actualizado!");
        })
        .fail(function (qXHR, textStatus, errorThrown) {
            console.log("error, no se pudo actualizar game view");
            console.log(qXHR);
        });
}
function realizarDisparos(htmlElement) {

    mensaje.innerHTML = "tiene diez segundos para disparar o perderá su turno";
    
    typeOfPrint = "salvo";
    numberOfShots = 5;

    nroTurno++;

    document.getElementById("nroDisparosFaltantes").innerHTML = numberOfShots;
    document.getElementById("nroTurno").innerHTML = nroTurno;

    botonDisparar.style.display = "none";

    var aviso = "seleccione los cuatritos a los que desea disparar. ";
    aviso += "Pueden ser hasta 5 disparos.";
    alert(aviso);

    setTimeout(function(){ 

        if(locations.length < 5 ){
            mensaje.innerHTML = "ha perdido su turno!! Es el turno de su oponente!";
            botonDisparar.style.display = "none";
            agregarSalvoVacioPorPerdidaDeTurno();
        }
        
        if(locations.length == 5 ){
            mensaje.innerHTML = "no llegaste a apretar el botón done. Perdiste tu turno";
            botonDisparar.style.display = "none";
            agregarSalvoVacioPorPerdidaDeTurno();
        }

        document.location.reload();

    }, 10000);
}
function salvoesDone() {

    actualSalvo = newSalvo(nroTurno,locations);
    
    addSalvoToGamePlayer(actualSalvo,valorActual);
    mensaje.innerHTML = "";

    locations = [];
    actualSalvo = {};
    typeOfPrint = "";
    botonDone.style.display = "none";
    infoDisparos.style.display = "none";

    document.location.reload();
}
function paintSquare(htmlElement) {

    var placeId = htmlElement.id;

    if(typeOfPrint != ""){

        if (typeOfPrint == "ship") {

            if(isALocationPainted(placeId)){

                changeColorSquare(htmlElement);
                var indexOfPlaceId = locations.indexOf(placeId);
                locations.splice(indexOfPlaceId,1);
                numberOfPrints++;

            }else if (isAcorrectPlaceToPaintShip(locations,placeId)
                        && numberOfPrints >= 1){

                changeColorSquare(htmlElement);
                locations.push(placeId);
                numberOfPrints--;

            }else{

                alert("lugar incorrecto para colocar barco. Elija Otro!");
            }

            if(numberOfPrints == 0){

                if(numberOfShips >= 1){
                    numberOfShips--;
                    var divShip = document.getElementById(actualShip.type);
                    divShip.style.visibility = "hidden";
                    actualShip.locations = locations;
                    shipList.push(actualShip);
                    locations = [];
                    typeOfPrint = "";
                }

                if(numberOfShips == 0){
                    addShipListToGamePlayer(shipList,valorActual);
                    locations = [];
                    actualShip = {};
                    shipList = [];
                    typeOfPrint = "";
                    shipsToPrint.style.display = "none";
                    console.log("ha terminado de pintar todos los barcos!");
                }
            }
        }

        if (typeOfPrint == "salvo") {

            var disparosFaltantes = document.getElementById("nroDisparosFaltantes");
            var infoDisparos = document.getElementById("infoDisparos");

            if(isALocationPainted(placeId.toUpperCase())){

                changeColorSquare(htmlElement);
                agregarValorACuadrito(placeId, "");
                var indexOfPlaceId = locations.indexOf(placeId);
                locations.splice(indexOfPlaceId,1);
                numberOfShots++;
                disparosFaltantes.innerHTML = numberOfShots;

            }else if (isAcorrectPlaceToPaintShot(locations,placeId)
                        && numberOfShots >= 1){

                changeColorSquare(htmlElement);
                agregarValorACuadrito(placeId, nroTurno);
                locations.push(placeId.toUpperCase());
                numberOfShots--;
                disparosFaltantes.innerHTML = numberOfShots;

            }else{
                alert("lugar incorrecto para disparar. Elija Otro!");
            }

            if (numberOfShots == 0) {

                botonDone.style.display = "block";
                var frase = "Ha terminado de disparar en éste turno. ";
                frase += "Presione el botón 'Done'";
                mensaje.innerHTML = frase;

            } 
        }    
    } else {

         alert("no eligió qué pintar!");

    }
}
function isInTheExtremOfShip(locationList,placeId) {
    var isInTheExtrem = false;
    for (var location of locationList) {
        if (isStartOrEndOfShip(locationList,location) &&
            areConsecutivesPlaces(location,placeId)){
            isInTheExtrem = true;
        }
    }
    return isInTheExtrem;
}
function areConsecutivesPlaces(placeA,placeB) {
    var areConsecutives = placeBisNextPlace(placeA,placeB) ||
                         placeBisPreviousPlace(placeA,placeB);
    return areConsecutives;
}
function placeBisNextPlace(placeA,placeB) {

    var columnA = getColumnNumber(placeA);
    var rowA = getRowLetter(placeA);

    var columnB = getColumnNumber(placeB);
    var rowB = getRowLetter(placeB);

    var theyHasGotSameColumn = columnA == columnB;
    var theyHasGotSameRow = rowA == rowB;
    var placeBHasGotNextCol = nextColumn(placeA) == columnB;
    var placeBHasGotNextRow = nextRow(placeA) == rowB;

    var isNextPlace = (theyHasGotSameColumn && placeBHasGotNextRow) ||
                      (theyHasGotSameRow && placeBHasGotNextCol);
    return isNextPlace;
}
function placeBisPreviousPlace(placeA,placeB) {

    var columnA = getColumnNumber(placeA);
    var rowA = getRowLetter(placeA);

    var columnB = getColumnNumber(placeB);
    var rowB = getRowLetter(placeB);

    var theyHasGotSameColumn = columnA == columnB;
    var theyHasGotSameRow = rowA == rowB;
    var placeBHasGotPreviousCol = previousColumn(placeA) == columnB;
    var placeBHasGotPreviousRow = previousRow(placeA) == rowB;

    var isPreviousPlace = (theyHasGotSameColumn && placeBHasGotPreviousRow)
                          || (theyHasGotSameRow && placeBHasGotPreviousCol);

    return isPreviousPlace;
}
function isStartOrEndOfShip(locationList,placeId){

    var sizeLocations = locationList.length;
    var isStartOrEnd = false;

    switch (sizeLocations) {
        case 0:
            isStartOrEnd = true;
            break;
        case 1:
            if (locationList[0] == placeId) isStartOrEnd = true;
            break;
        default:
            let start = locationList[0];
            let end = locationList[sizeLocations-1];
            if (placeId == start || placeId == end){
                isStartOrEnd = true;
            }
    }

    return isStartOrEnd;
}
function previousRow(placeId) {
    var actualRow = getRowLetter(placeId);
    var prevRow = getNextOrPreviousRow("previous",actualRow);
    return prevRow;
}
function nextRow(placeId) {
    var actualRow = getRowLetter(placeId);
    var nextRow = getNextOrPreviousRow("next",actualRow);
    return nextRow;
}
function nextColumn(placeId) {
    var actualColumn = getColumnNumber(placeId);
    var nextCol = getNextOrPreviousColumn("next",actualColumn);
    return nextCol;
}
function previousColumn(placeId) {
    var actualColumn = getColumnNumber(placeId);
    var previousCol = getNextOrPreviousColumn("previous",actualColumn);
    return previousCol;
}
function getNextOrPreviousColumn(typeNum,num) {
    var searchedNum = "has no " + typeNum;
    var numbers = ["1","2","3","4","5","6","7","8","9","10"];

    for(var actualNum of numbers){
        if(actualNum == num){
            var indice = numbers.indexOf(num);

            if (typeNum == "next" &&
                actualNum != "10") searchedNum = numbers[indice + 1];
            if (typeNum == "previous" &&
                actualNum != "1") searchedNum= numbers[indice - 1];
        }
    }
    return searchedNum;
}
function getNextOrPreviousRow(typeLetter,letter) {
    var searchedLetter = "has no " + typeLetter;
    var letras = ["A","B","C","D","E","F","G","H","I","J"];

    for(var letraActual of letras){
        if(letraActual == letter){
            var indice = letras.indexOf(letter);

            if (typeLetter == "next" &&
                letraActual != "J") searchedLetter = letras[indice + 1];
            if (typeLetter == "previous" &&
                letraActual != "A") searchedLetter = letras[indice - 1];
        }
    }
    return searchedLetter;
}
function allInTheSameRow(locationList,placeId) {
    
    var sameRow = true;
    var nameRow = getRowLetter(locationList[0]);
    
    for(var location of locationList){
        if(getRowLetter(location) == nameRow &&
           getRowLetter(placeId) == nameRow){
            sameRow = sameRow && true;
        }else{
            sameRow = sameRow && false;
        }
    }
 
    return sameRow;
}
function allInTheSameColumn(locationList,placeId) {
    
    var sameColumn = true;
    var nameCol = getColumnNumber(locationList[0]);

    for (var location of locationList){
        if(getColumnNumber(location) == nameCol &&
           getColumnNumber(placeId) == nameCol){
            sameColumn = sameColumn && true;
        }else{
            sameColumn = sameColumn && false;
        }
    }

    return sameColumn;
}
function getColumnNumber(placeId) {
    
    var searchedNumber = 0;
    
    if(placeId.length == 3){
        searchedNumber = "10";
    }else{
        searchedNumber = placeId.charAt(1);
    }
    
    return searchedNumber;
}
function getRowLetter(placeId){
    var letter = placeId.charAt(0);
    return letter;
}
function isAcorrectPlaceToPaintShot(locationsList,placeId){

    var isACorrectPlace = false;

    if (locationsList.length == 0){
        isAcorrectPlace = true;
    }
    
    if(isANewLocationToPaint(placeId) ||
        isALocationPainted(placeId)) {

        isACorrectPlace = true;

    }

    if(isACorrectPlace == false){
        var aviso = "Eligió un lugar incorrecto.";
        aviso += "No puede disparar donde ya lo hizo";
        console.log(aviso);
    }

    return isACorrectPlace;

}
function agregarValorACuadrito(placeId,valor){
    document.getElementById(placeId).innerHTML = valor;
}
function isAcorrectPlaceToPaintShip(locationList,placeId){
  
    var isACorrectPlace = false;
    
    if (locationList.length == 0 && 
        isANewLocationToPaint(placeId)){
        
        isACorrectPlace = true;

    }else{

        if(isALocationPainted(placeId)){
            isACorrectPlace = true;
        }

        if(isANewLocationToPaint(placeId) &&
           isInTheSameLine(locations,placeId) &&
           isInTheExtremOfShip(locationList,placeId)){

            isACorrectPlace = true;
        }
    }

    if(isACorrectPlace == false){
        var aviso = "Eligió un lugar incorrecto.";
        aviso += "Los barcos sólo pueden estar acomodados";
        aviso += "de forma horizontal o vertical";
        console.log(aviso);
    }

    return isACorrectPlace;
}
function isANewLocationToPaint(placeId) {
    var hasCorrectColorToPrint = false;
    var divToPrint = document.getElementById(placeId);
    var actualColor = divToPrint.style.backgroundColor;
    
    if (actualColor == "skyblue") {
        hasCorrectColorToPrint = true;
    }

    return hasCorrectColorToPrint;
}
function isALocationPainted(placeId){
    var inLocationChoosed = locations.includes(placeId);
    return inLocationChoosed;   
}            
function isInTheSameLine(locationList,placeId) {
        
    var sizeList = locationList.length;
    var sameLine = allInTheSameRow(locationList,placeId) ||
                   allInTheSameColumn(locationList,placeId);
    return sameLine;
}
function changeColorSquare(htmlElement){
 
    var actualColor = htmlElement.style.backgroundColor;
    
    switch (actualColor){
        case "skyblue":
            switch (typeOfPrint) {
                case "ship":
                    htmlElement.style.backgroundColor = "darkslategray";
                    break;
                case "salvo":
                    htmlElement.style.backgroundColor = "red";
            }
            break;
        case "darkslategray":
            if(typeOfPrint == "ship"){
                htmlElement.style.backgroundColor = "skyblue";
            }
            break;
        case "red":
            if(typeOfPrint == "salvo"){ 
                htmlElement.style.backgroundColor = "skyblue"; 
            }
                 
    } 
}       
function chooseShip(htmlElement) {
    
    typeOfPrint = "ship";

    var typeOfShip = htmlElement.getAttribute("data-type-ship");
    console.log(typeOfShip);
    
    actualShip = newShip(typeOfShip,[]);
    
    switch (typeOfShip) {
        case "CARRIER": 
            numberOfPrints = 5;
            break;
        case "BATTLESHIP":
            numberOfPrints = 4;
            break;
        case "SUBMARINE":
            numberOfPrints = 3;
            break;
        case "DESTROYER":
            numberOfPrints = 3;
            break;
        case "PATROLBOAT":
            numberOfPrints = 2;
    }
    
    var aviso = "clickee los cuadritos necesarios para ";
    aviso += "crear al barco elegido. Los mismos ";
    aviso += "deben ser cuadritos consecutivos y ";
    aviso += "orientados de manera horizontal o vertical.";
    
    alert(aviso);
}
function aparecerColocarShipsButton(){
    shipsButton.style.display = "block";
}
function aparecerCreateGameButton(){
    createGameButton.style.display = "block";
}
function aparecerBotonDisparar(){
    botonDisparar.style.display = "block";
}
function colocarShips() {
    
    typeOfPrint = "ship";
    numberOfShips = barcosPorColocar.length;
    
    var button = document.getElementById("colocarShipsButton");
    button.style.display = "none";

    if(barcosColocados.length != 5){
        for(var nombreDeBarco of barcosColocados ){
            var imgBarco = document.getElementById(nombreDeBarco);
            imgBarco.style.visibility = "hidden";
        }
    }

    var shipList = document.getElementById("shipsToPrint");
    shipList.style.display = "block";
    
}

function ultimoTurnoRealizado(salvoesList){
    var ultimoTurno = 0;

    for(var aSalvo of salvoesList){
        if(aSalvo.turn >= ultimoTurno && 
           aSalvo.gamePlayerId  == gamePlayerId){

            ultimoTurno = aSalvo.turn;
        }
    }

    return ultimoTurno;
}

function getIdPlayerOfActualGamePlayer(gamePlayersList){
    var idSearched;
    for(var aGamePlayer of gamePlayersList){
        if(aGamePlayer.id == valorActual){
            idSearched = aGamePlayer.player.id;
        }
    }
    return idSearched;
}
function eliminarRepetidos(arreglo) {
    var sinRepetidos = [];
    for (var elemento of arreglo) {
        if(!sinRepetidos.includes(elemento)) {
            sinRepetidos.push(elemento);
        }
    }
    return sinRepetidos;
}
function createInfoTable(hits,sinks,username,lugarId){

    var barcosHundidos = eliminarRepetidos(sinks);
    var todosLosBarcos = ["CARRIER", "BATTLESHIP", "SUBMARINE","DESTROYER","PATROLBOAT"];
    var barcosPorHundir = eliminarElementos(todosLosBarcos,barcosHundidos);

    var tabla = "<table class='table-bordered'>";

    tabla += "<tr>";
    tabla += "<th colspan='4'> Barcos hundidos: " + barcosHundidos.join(", ") + "</th>";
    tabla += "</tr>";

    tabla += "<tr>";
    tabla += "<th colspan='4'> Barcos por hundir: " + barcosPorHundir.join(", ") + "</th>";
    tabla += "</tr>";


    tabla += "<tr>";
    tabla += "<th colspan='4'> hits to " + username + "</th>";
    tabla += "</tr>";

    tabla += "<tr>";
    tabla += "<th> turn </th>";
    tabla += "<th> ship </th>";
    tabla += "<th> #hits </th>";
    tabla += "<th> quedan </th>"
    tabla += "</tr>";

    for (var shipHinted of hits){

        var turn = shipHinted.turn;
        var type = shipHinted.type;
        var hits = shipHinted.numberOfHits;
        var hitsQueRestan = cantQueQuedanPorGolpear(type,hits);

        tabla += "<tr>";
        tabla += "<td>" + turn + "</td>";
        tabla += "<td>" + type + "</td>";
        tabla += "<td>" + hits + "</td>";
        tabla += "<td>" + hitsQueRestan + "</td>";
        tabla += "</tr>";
    }

    tabla += "</table>";

    document.getElementById(lugarId).innerHTML = tabla;
}
function eliminarElementos(arreglo,arrayElementosASacar) {
    for(var elementoActual of arrayElementosASacar){
        if(arreglo.includes(elementoActual)){
            var indice = arreglo.indexOf(elementoActual);
            arreglo.splice(indice,1);
        }
    }
    return arreglo;
}
function cantQueQuedanPorGolpear(typeShip,hits){

    var cantidad = 0;

    switch(typeShip){
        case "CARRIER":
            cantidad = 5 - hits;
            break;
        case "BATTLESHIP":
            cantidad = 4 - hits;
            break;
        case "SUBMARINE":
            cantidad = 3 - hits;
            break;
        case "DESTROYER":
            cantidad = 3 - hits;
            break;
        case "PATROLBOAT":
            cantidad = 2 - hits;
    }

    return cantidad;
}



/*

$.ajax({
            url: urlBuscada,
            type: 'GET',
            datatype: 'json'
        })
        .done(function(data) {

            console.log(data);

            userLoggedIn = data.userLoggedIn;
            userOpponent = data.userOpponent;

            playerId = getIdPlayerOfActualGamePlayer(data.infoGame.gamePlayers);
            nroTurno = ultimoTurnoRealizado(data.infoGame.salvoes);

            var hitsToMe = data.infoGame.hits.toMe;
            var hitsLocationsToMe = data.infoGame.hits.hitLocationsToMe;
            var sinksToMe = data.infoGame.sinks.toMe;

            var hitsToOpponent = data.infoGame.hits.toOpponent;
            var hitsLocationsToOpponent = data.infoGame.hits.hitLocationsToOpponent;
            var sinksToOpponent = data.infoGame.sinks.toOpponent;

            imprimirInfoPlayers(data.infoGame.gamePlayers,userLoggedIn);
            agregarBotones();
            var titulo = "own ships and salvoes received"
            dibujarGrilla("ownShipsSalvoesReceived",titulo,"upperCaseId");
            dibujarGrilla("salvoesFired","salvoes fired","lowerCaseId")
            pintarBarquitos(data.infoGame.ships);
            pintarDisparosRecibidos(data.infoGame.salvoes);
            pintarDisparosRealizados(data.infoGame.salvoes,hitsLocationsToOpponent);
            createInfoTable(hitsToMe,sinksToMe,userLoggedIn,"tableGamePlayer1");
            createInfoTable(hitsToOpponent,sinksToOpponent,userOpponent,"tableGamePlayer2");
            alert("tenés diez segundos para acomodar los barcos");

            })
            .fail(function(jqXHR, textStatus, errorThrown) {
                console.log(textStatus);
                alert("esperando que se conecte un oponente");
            });

*/