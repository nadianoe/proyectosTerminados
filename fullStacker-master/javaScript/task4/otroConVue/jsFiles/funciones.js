       
function setearNumeroDeRepresentantes() {

    let numDemocrats = filtrarMiembrosPorEstadoOPartido('D').length;
    statictis.nroReps.democratas = numDemocrats;

    let numRepublicans = filtrarMiembrosPorEstadoOPartido('R').length;
    statictis.nroReps.republicans = numRepublicans;

    let numIndependients = filtrarMiembrosPorEstadoOPartido('I').length;
    statictis.nroReps.independients = numIndependients;

    let numTotal = numDemocrats + numRepublicans + numIndependients;
    statictis.nroReps.total = numTotal;

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

function cantDiezPorciento(miembros) {
    let cantTotalMiembros = miembros.length;
    let diezPorciento = Math.floor(cantTotalMiembros / 100) * 10;
    return diezPorciento;
}

function miembroMenorValor(miembros,tipoDeValor) {
    
    let miembroBuscado = miembros[0];
    
    for (i = 0; i < miembros.length; i++) {
        
        if (tipoDeValor == "missed" && 
            miembroBuscado.missed_votes >= miembros[i].missed_votes){
             miembroBuscado = miembros[i];
        }
        
        if (tipoDeValor == "party" && 
            miembroBuscado.total_votes >= miembros[i].total_votes){
             miembroBuscado = miembros[i];
        }
    }
    return miembroBuscado;   
}

function miembrosConIgualValor(todosLosMiembros,miembro,tipoDeValor) {
    
    let miembrosBuscados = [];
    
    for (let i = 0; i < todosLosMiembros.length; i++) {
        
        if(tipoDeValor == "missed" &&
           miembro.missed_votes == todosLosMiembros[i].missed_votes) {
            miembrosBuscados.push(todosLosMiembros[i]);
        }
        
        if(tipoDeValor == "party" &&
           miembro.total_votes == todosLosMiembros[i].total_votes) {
            miembrosBuscados.push(todosLosMiembros[i]);
        }
    }
    return miembrosBuscados;
}

function esElemento(arreglo, elemento) {
    
    let siEsElemento = false;
    
    for (let i = 0; i < arreglo.length; i++) {
        if (arreglo[i] == elemento){
            siEsElemento = true;
            i = arreglo.length;
        }
    }
    return siEsElemento;  
}

function quitarElemento(lista,elemento) {
    
    for( let i = 0; i < lista.length; i++) {
        if (lista[i] == elemento) {
            lista.splice(i,1);
            i = lista.length;
        }
    }
    
    return lista;
}

function quitarMiembros (listaMiembros,miembrosAQuitar) {
    for (let i = 0; i < miembrosAQuitar.length; i++) {
        if (esElemento(listaMiembros,miembrosAQuitar[i])) {
           quitarElemento(listaMiembros,miembrosAQuitar[i]);
        }
    }
}

function agregarMiembros(listaMiembros,miembrosNuevos) {
    for (let i = 0; i < miembrosNuevos.length; i++){
        listaMiembros.push(miembrosNuevos[i]);
    }
    return listaMiembros;
}

function miembrosOrdenadosSegunValor(miembros,tipoDeValor) {
    
    let cantMiembros = miembros.length;
    let miembrosOrdenados = [];
    let miembroMenorMV;
    let miembrosIgualMV;
    
    
    while (miembrosOrdenados.length != cantMiembros) {
        
        miembroMenorMV = miembroMenorValor(miembros,tipoDeValor);
        miembrosIgualMV = miembrosConIgualValor(miembros,miembroMenorMV,tipoDeValor);
        quitarMiembros(miembros,miembrosIgualMV);
        agregarMiembros(miembrosOrdenados,miembrosIgualMV);
        
    }
    
    return miembrosOrdenados;
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
    statictis.votesPrc.democratas = promedioDemocratas;

    let republicans = filtrarMiembrosPorEstadoOPartido('R');
    let promedioRepublicans = promedioPorcentaje(republicans);
    statictis.votesPrc.republicans = promedioRepublicans;

    let independientes = filtrarMiembrosPorEstadoOPartido('I');
    let promedioIndependients = promedioPorcentaje(independientes);
    statictis.votesPrc.independients = promedioIndependients;

    statictis.votesPrc.total = promedioDemocratas + promedioRepublicans + promedioIndependients;

}

function setearEstadisticas(members,tipoDeValor) {
    
    let miembrosOrdenados = miembrosOrdenadosSegunValor(members, tipoDeValor);
    
    let miembrosBottom = getBottomTenPrc(miembrosOrdenados);
    
    if (tipoDeValor == "missed") {
        let emptyList = statictis.selectedMembers.leastEngaged; 
        agregarMiembros(emptyList,miembrosBottom);
    }
    
    if (tipoDeValor == "party") {
        let emptyList = statictis.selectedMembers.leastLoyal;
        agregarMiembros(emptyList,miembrosBottom);
    }

    let miembrosTop = getTopTenPrc(miembrosOrdenados);
    
    if (tipoDeValor == "missed") {
        let emptyList = statictis.selectedMembers.mostEngaged; 
        agregarMiembros(emptyList,miembrosTop);
    }
    
    if (tipoDeValor == "party") {
        let emptyList = statictis.selectedMembers.mostLoyal;
        agregarMiembros(emptyList,miembrosTop);
    }
    
}

function setearTodosLosDatos(members, tipoDeValor) {
    
    setearNumeroDeRepresentantes();
    setearPorcentajeVotes();
    setearEstadisticas(members,tipoDeValor);
    
}

function promedioPorcentaje(miembrosDeUnParty) {
    let sumaDePorcentajes = 0;
    let cantidadDeMiembros = miembrosDeUnParty.length;
    for (let i = 0; i < cantidadDeMiembros; i++) {
        sumaDePorcentajes += miembrosDeUnParty[i].votes_with_party_pct;
    }
    return sumaDePorcentajes / cantidadDeMiembros;
}

var tabla1 = new Vue({
    el: "#tabla1",
    data: {
        info : statictis,
    }
})

var tabla2 = new Vue({
    el : "#tabla2",
    data: {
        membersBottomAtt: statictis.selectedMembers.leastEngaged,
        membersBottomLoyal: statictis.selectedMembers.leastLoyal
    }
})

var tabla3 = new Vue({
    el : "#tabla3",
    data: {
        membersTopAtt: statictis.selectedMembers.mostEngaged,
        membersTopLoyal: statictis.selectedMembers.mostLoyal
    }
})


