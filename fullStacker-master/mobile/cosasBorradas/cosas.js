function imprimirTablaPorMes() {
    var valorMes = document.getElementById('mes').value;
    if (valorMes != "select month" || valorMes != "all months") {
        document.getElementById('team').value = "select team";
        fechasBuscadas = filtrarMiembrosPorMes(valorMes);
        imprimirTabla(fechasBuscadas);
    }
    if (valorMes == "all months") {
        document.getElementById('team').value = "select team";
        imprimirTabla(fechas);
    }
}

function imprimirTablaPorTeam() {
    var valorTeam = document.getElementById('team').value;
    if (valorTeam != "all teams" || valorTeam != "select team") {
        document.getElementById('mes').value = "select month";
        fechasBuscadas = filtrarMiembrosPorTeam(valorTeam);
        imprimirTabla(fechasBuscadas);
    }
    if (valorTeam == "all teams") {
        document.getElementById('mes').value = "select month";
        imprimirTabla(fechas);
    }
}

function filtrarMiembrosPorMes(nameMes) {
    var fechasBuscadas = [];
    for (var i = 0; i < fechas.length; i++) {
        if (fechas[i].mes == nameMes) {
            fechasBuscadas.push(fechas[i]);
        }
    }
    return fechasBuscadas;
}

function filtrarMiembrosPorTeam(nameTeam) {
    var fechasBuscadas = [];
    for (var i = 0; i < fechas.length; i++) {
        if (fechas[i].equipoUno == nameTeam ||
            fechas[i].equipoDos == nameTeam) {
            fechasBuscadas.push(fechas[i]);
        }
    }
    return fechasBuscadas;
}


function previousPage() {

    var actualPage = document.getElementById("contenidoPrincipal").innerHTML;


    if (actualPage == schedule) {

        showPage("login");
    }

    if (actualPage == login) {

        showPage("about");
    }

    if (actualPage == about) {

        showPage("gameRules");
    }

    if (actualPage == gameRules) {

        showPage("actualGame");
    }

    if (actualPage == actualGame) {

        showPage("nextGame");
    }

    if (actualPage == nextGame) {

        showPage("schedule");
    }
    
    if (actualPage == chat) {
        showPage("schedule");
    }
}

function nextPage() {

    var actualPage = document.getElementById("contenidoPrincipal").innerHTML;


    if (actualPage == about) {

        showPage("login");
    }

    if (actualPage == gameRules) {

        showPage("about");
    }

    if (actualPage == actualGame) {

        showPage("gameRules");
    }

    if (actualPage == nextGame) {

        showPage("actualGame");
    }

    if (actualPage == schedule) {

        showPage("nextGame");
    }

    if (actualPage == login) {

        showPage("schedule");
    }
    
    if (actualPage == chat) {
        showPage("schedule");
    }
}
