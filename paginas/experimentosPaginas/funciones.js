var nombreDePackActual = "ninguno";

function goHome() {
    $("#volverAlInicio").hide();
    $("#agregarPack").show();
    $("#packsRegistrados").show();
    $("#agregarPagina").hide();
    $("#paginasRegistradas").hide();
}

function obtenerPacks(){
    $.ajax({
        url: "http://localhost:8080/api/datos/packs",
        type: 'GET'
    })
        .done(function (data) {

            var packs = data.nombresDePacks;

            for (var i = 0; i < packs.length; i++) {
                var nombre = packs[i];
                var nuevaFila = "<tr>\n";
                nuevaFila += "<td>" + nombre + "</td>";
                nuevaFila += "<td>";
                var nombreFuncion = "verPack('" + nombre + "')";
                nuevaFila += "<button role='button' class='btn btn-dark' onclick=" + nombreFuncion+ "> ver </button>";
                nuevaFila += "</td>\n";
                nuevaFila += "</tr>";
                $("#packsRegistrados").append(nuevaFila);
            }

            $("#packsRegistrados").show();

        })
        .fail(function (jqXHR, textStatus, errorThrown) {
            console.log("error, no se pudo obtener datos");
            console.log(jqXHR);
            console.log(textStatus);
            console.log(errorThrown);
        });
}

function verPack(nombreDePack) {
    $("#volverAlInicio").show();
    $("#agregarPack").hide();
    $("#packsRegistrados").hide();
    $("#agregarPagina").show();
    obtenerPaginas(nombreDePack);
    $("#paginasRegistradas").show();
    nombreDePackActual = nombreDePack;
}

function obtenerPaginas(nombrePack){
    $.ajax({
        url: "http://localhost:8080/api/datos/" + nombrePack + "/paginas",
        type: 'GET'
    })
        .done(function (data) {
            var paginas = data.paginas;

console.log("se pudoooo! " + paginas.length);

            for (var i = 0; i < paginas.length; i++) {
                var nombreIngresado = paginas[i].nombre;
                var linkIngresado = paginas[i].link;
                var nuevaFila = "<tr>\n";
                nuevaFila += "<td>" + nombreIngresado + "</td>";
                nuevaFila += "<td>";
                nuevaFila += "<button class='btn btn-dark' href='" + linkIngresado + "'> ir </a>";
                nuevaFila += "</td>\n";
                nuevaFila += "</tr>";
                $("#paginasRegistradas").append(nuevaFila);
            }


        })
        .fail(function (jqXHR, textStatus, errorThrown) {
            console.log("error, no se pudo obtener datos");
            console.log(jqXHR);
            console.log(textStatus);
            console.log(errorThrown);
        });
}

function mostrarFormNuevaPagina(){
    $("#agregarPagina").hide();
    $("#formNuevaPagina").show();
}

function mostrarFormNuevoPack(){
    $("#agregarPack").hide();
    $("#formNuevoPack").show();
}

function agregarPagina(){
    var linkIngresado = $("#link").val();
    var nombreIngresado = $("#nombreDePagina").val();
    $.ajax({
        data: JSON.stringify({ nombre : nombreIngresado, link : linkIngresado }),
        contentType: "application/json",
        type: 'PUT',
        url: "http://localhost:8080/api/datos/" + nombreDePackActual + "/paginas"
    })
        .done(function (data) {
            $("#formNuevaPagina").hide();
            $("#agregarPagina").show();
            var nuevaFila = "<tr>\n";
            nuevaFila += "<td>" + nombreIngresado + "</td>";
            nuevaFila += "<td>";
            nuevaFila += "<a role='button' class='btn btn-dark' href='" + linkIngresado + "'> ir </a>";
            nuevaFila += "</td>\n";
            nuevaFila += "</tr>";
            $("#link").val("");
            $("#nombreDePagina").val("");
            $("#paginasRegistradas").append(nuevaFila);

        })
        .fail(function (jqXHR, textStatus, errorThrown) {
            console.log("error, no se pudo ingresar los nuevos datos");
            console.log(jqXHR);
            console.log(textStatus);
            console.log(errorThrown);
        });
}

function agregarPackDePaginas(){

    var nombrePack = $("#nombreDePack").val();
    $.ajax({
        data: JSON.stringify({ nombre : nombrePack }),
        contentType: "application/json",
        type: 'PUT',
        url: "http://localhost:8080/api/datos/packs"
    })
        .done(function (data) {
            $("#formNuevoPack").hide();
            $("#agregarPack").show();
            var nuevaFila = "<tr>\n";
            nuevaFila += "<td>" + nombrePack + "</td>";
            nuevaFila += "<td>";
            var nombreFuncion = "verPack('" + nombrePack + "')";
            nuevaFila += "<button role='button' class='btn btn-dark' onclick=" + nombreFuncion+ "> ver </button>";
            nuevaFila += "</td>\n";
            nuevaFila += "</tr>";
            $("#link").val("");
            $("#nombreDePagina").val("");
            $("#packsRegistrados").append(nuevaFila);

        })
        .fail(function (jqXHR, textStatus, errorThrown) {
            console.log("error, no se pudo ingresar los nuevos datos");
            console.log(jqXHR);
            console.log(textStatus);
            console.log(errorThrown);
        });
}

//cambiar paginas.html a carpetaproyTerminados