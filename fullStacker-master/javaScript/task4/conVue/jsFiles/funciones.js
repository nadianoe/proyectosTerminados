

$(function () {
    console.log(url);
    fetch(url, {
        method: 'GET',
        headers: new Headers({
            "X-API-Key": "9wGuG70NAkERZGmNj91kDDg1WExSHeqOucfg2Abx"
        })
    }).then(function (response) {
        return response.json();
    }).then(function (json) {
        pagina.members = json.results[0].members;
        console.log(members);

        setearNumeroDeRepresentantes();
        setearPorcentajeVotes();
        imprimirTablaUno("senate-at-a-glance");

        let miembrosOrdenados = miembrosOrdenadosSegunValor(members, "missed");

        let miembrosTabla2 = getBottomTenPrc(miembrosOrdenados);
        imprimirTabla("missed", miembrosTabla2, "leastEngaged");

        let miembrosTabla3 = getTopTenPrc(miembrosOrdenados);
        imprimirTabla("missed", miembrosTabla3, "mostEngaged");

    }).catch(function () {
        console.log("Fail");
    })
});



function setearNumeroDeRepresentantes() {

    let numDemocrats = filtrarMiembrosPorEstadoOPartido('D').length;
    statictis.numeroDeRepresentantes.democratas = numDemocrats;

    let numRepublicans = filtrarMiembrosPorEstadoOPartido('R').length;
    statictis.numeroDeRepresentantes.republicans = numRepublicans;

    let numIndependients = filtrarMiembrosPorEstadoOPartido('I').length;
    statictis.numeroDeRepresentantes.independients = numIndependients;

    let numTotal = numDemocrats + numRepublicans + numIndependients;
    statictis.numeroDeRepresentantes.total = numTotal;

}

function filtrarMiembrosPorEstadoOPartido(nombre) {
    let arregloFiltrado = [];
    for (let i = 0; i < members.length; i++) {
        if (members[i].party == nombre) {
            arregloFiltrado.push(members[i]);
        }
        if (members[i].state == nombre) {
            arregloFiltrado.push(members[i]);
        }
    }
    return arregloFiltrado;
}

function imprimirTablaUno(idLugar) {
    let tabla = "<thead class='thead-dark'>" +
        "<tr>" +
        "<th>Party</th>" +
        "<th>Number of Reps</th>" +
        "<th>% Voted with Party</th>" +
        "</thead>" +
        "<tbody>" +
        "<tr>" +
        "<td>Democrats</td>" +
        "<td>" + statictis.numeroDeRepresentantes.democratas + "</td>" +
        "<td>" + statictis.votesWithParty.democratas + " % </td>" +
        "</tr>" +
        "<td>Republicans</td>" +
        "<td>" + statictis.numeroDeRepresentantes.republicans + "</td>" +
        "<td>" + statictis.votesWithParty.republicans + " % </td>" +
        "</tr>" +
        "<td>Independients</td>" +
        "<td>" + statictis.numeroDeRepresentantes.independients + "</td>" +
        "<td>" + statictis.votesWithParty.independients + " % </td>" +
        "</tr>" +
        "<td>Total</td>" +
        "<td>" + statictis.numeroDeRepresentantes.total + "</td>" +
        "<td>" + statictis.votesWithParty.total + " % </td>" +
        "</tr>" +
        "</tbody>";

    document.getElementById(idLugar).innerHTML = tabla;

}

function cantDiezPorciento(miembros) {
    let cantTotalMiembros = miembros.length;
    let diezPorciento = Math.floor(cantTotalMiembros / 100) * 10;
    return diezPorciento;
}

function miembroMenorValor(miembros, tipoDeValor) {

    let miembroBuscado = miembros[0];

    for (i = 0; i < miembros.length; i++) {

        if (tipoDeValor == "missed" &&
            miembroBuscado.missed_votes >= miembros[i].missed_votes) {
            miembroBuscado = miembros[i];
        }

        if (tipoDeValor == "party" &&
            miembroBuscado.total_votes >= miembros[i].total_votes) {
            miembroBuscado = miembros[i];
        }
    }
    return miembroBuscado;
}

function miembrosConIgualValor(todosLosMiembros, miembro, tipoDeValor) {

    let miembrosBuscados = [];

    for (let i = 0; i < todosLosMiembros.length; i++) {

        if (tipoDeValor == "missed" &&
            miembro.missed_votes == todosLosMiembros[i].missed_votes) {
            miembrosBuscados.push(todosLosMiembros[i]);
        }

        if (tipoDeValor == "party" &&
            miembro.total_votes == todosLosMiembros[i].total_votes) {
            miembrosBuscados.push(todosLosMiembros[i]);
        }
    }
    return miembrosBuscados;
}

function esElemento(arreglo, elemento) {

    let siEsElemento = false;

    for (let i = 0; i < arreglo.length; i++) {
        if (arreglo[i] == elemento) {
            siEsElemento = true;
            i = arreglo.length;
        }
    }
    return siEsElemento;
}

function quitarElemento(lista, elemento) {

    for (let i = 0; i < lista.length; i++) {
        if (lista[i] == elemento) {
            lista.splice(i, 1);
            i = lista.length;
        }
    }

    return lista;
}

function quitarMiembros(listaMiembros, miembrosAQuitar) {
    for (let i = 0; i < miembrosAQuitar.length; i++) {
        if (esElemento(listaMiembros, miembrosAQuitar[i])) {
            quitarElemento(listaMiembros, miembrosAQuitar[i]);
        }
    }
}

function agregarMiembros(listaMiembros, miembrosNuevos) {
    for (let i = 0; i < miembrosNuevos.length; i++) {
        listaMiembros.push(miembrosNuevos[i]);
    }
    return listaMiembros;
}

function miembrosOrdenadosSegunValor(miembros, tipoDeValor) {

    let cantMiembros = miembros.length;
    let miembrosOrdenados = [];
    let miembroMenorMV;
    let miembrosIgualMV;


    while (miembrosOrdenados.length != cantMiembros) {

        miembroMenorMV = miembroMenorValor(miembros, tipoDeValor);
        miembrosIgualMV = miembrosConIgualValor(miembros, miembroMenorMV, tipoDeValor);
        quitarMiembros(miembros, miembrosIgualMV);
        agregarMiembros(miembrosOrdenados, miembrosIgualMV);

    }

    return miembrosOrdenados;
}

function mostrarValores(miembros) {
    let coso = [];
    for (let i = 1 - 1; i < miembros.length; i++) {
        coso.push(miembros[i].missed_votes);
    }
    return coso;
}

function getBottomTenPrc(miembrosOrdenados) {
    let cantidadMiembros = cantDiezPorciento(miembrosOrdenados);
    let miembrosBuscados = [];
    for (let i = 0; i < cantidadMiembros; i++) {
        miembrosBuscados.push(miembrosOrdenados[i]);
    }

    return miembrosBuscados;
}

function getTopTenPrc(miembrosOrdenados) {
    let cantDiezPrcMiembros = cantDiezPorciento(miembrosOrdenados);
    let maximoIndice = miembrosOrdenados.length - 1;
    let miembrosBuscados = [];
    for (let i = 1; i <= cantDiezPrcMiembros; i++) {
        miembrosBuscados.push(miembrosOrdenados[maximoIndice]);
        maximoIndice--;
    }
    return miembrosBuscados;
}

function setearPorcentajeVotes() {

    let democratas = filtrarMiembrosPorEstadoOPartido('D');
    let promedioDemocratas = promedioPorcentaje(democratas);
    statictis.votesWithParty.democratas = promedioDemocratas;

    let republicans = filtrarMiembrosPorEstadoOPartido('R');
    let promedioRepublicans = promedioPorcentaje(republicans);
    statictis.votesWithParty.republicans = promedioRepublicans;

    let independientes = filtrarMiembrosPorEstadoOPartido('I');
    let promedioIndependients = promedioPorcentaje(independientes);
    statictis.votesWithParty.independients = promedioIndependients;

    statictis.votesWithParty.total = promedioDemocratas + promedioRepublicans + promedioIndependients;

}

function promedioPorcentaje(miembrosDeUnParty) {
    let sumaDePorcentajes = 0;
    let cantidadDeMiembros = miembrosDeUnParty.length;
    for (let i = 0; i < cantidadDeMiembros; i++) {
        sumaDePorcentajes += miembrosDeUnParty[i].votes_with_party_pct;
    }
    return sumaDePorcentajes / cantidadDeMiembros;
}

function imprimirTabla(tipoDeValor, miembros, tableName) {
    let tabla = "<thead class='thead-dark'>" +
        "<tr>" +
        "<th>Name</th>" +
        "<th>Number " + tipoDeValor + " votes</th>" +
        "<th> % " + tipoDeValor + "</th>" +
        "</thead>" +
        "<tbody>";


    for (let i = 0; i < miembros.length; i++) {

        tabla = tabla +
            "<tr>" +
            "<td>" + miembros[i].first_name + " " + miembros[i].last_name +
            "</td>";

        if (tipoDeValor == "missed") {
            tabla = tabla +
                "<td>" + miembros[i].missed_votes + "</td>" +
                "<td>" + miembros[i].missed_votes_pct + " % </td>";
        } else {
            tabla = tabla +
                "<td>" + miembros[i].total_votes + "</td>" +
                "<td>" + miembros[i].votes_with_party_pct + " % </td>";
        }

        tabla = tabla + "</tr>";

    }
    tabla = tabla +
        "</tbody>";

    document.getElementById(tableName).innerHTML = tabla;

}


var pagina = new Vue({
    el: '#pagina',
    data: {
        members: [],
        info: statictis,
        states: []
    }
});

