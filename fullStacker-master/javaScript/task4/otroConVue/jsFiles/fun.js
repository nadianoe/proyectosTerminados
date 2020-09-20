function arregloDeEstados() {
    let estados = []
    for (let i = 0; i < members.length; i++) {
        estados.push(members[i].state);
    }
    estados = eliminoRepetidos(estados);
    return estados;
}

function eliminoRepetidos(arreglo) {
    let sinRepetidos = [];
    for (let i = 0; i < arreglo.length; i++) {
        if (!estaEnArreglo(sinRepetidos, arreglo[i])) {
            sinRepetidos.push(arreglo[i]);
        }
    }
    return sinRepetidos;
}

function estaEnArreglo(unArray, valor) {
    var encontrado = false;
    if (unArray.length == 0) {
        encontrado = false;
    } else {
        for (var p = 0; p < unArray.length && !encontrado; p++) {
            if (unArray[p] == valor) {
                encontrado = true;
            }
        }
    }
    return encontrado;
}

function imprimirLista(estados) {
    let lista = "<option value='Select'>Select</option>";
    lista = lista + "<option value='All'>All</option>";
    
    for (let i = 0; i < estados.length; i++) {
        lista = lista + "<option value='" + estados[i] + "'>" + estados[i] + "</option>";
    }
    document.getElementById("estados").innerHTML = lista;
}

function imprimirTablaConDropdownMenu() {
    destildarCheckboxes();
    let arregloConMiembros = [];
    let optionSelected = document.getElementById("estados").value;
    let estados = arregloDeEstados();

    for (let i = 0; i < estados.length; i++) {
        if (optionSelected == estados[i]) {
            arregloConMiembros = filtrarMiembrosPorEstadoOPartido(optionSelected);
            senateData.members = arregloConMiembros;
           
        }
    }

    if (optionSelected == "All") {
        senateData.members = members;
        
    }
}

function deseleccionarEstado() {
    let estados = document.getElementsByTagName("option");
    for (let i = 0; i < estados.length; i++) {
        estados[i].selected = false;
    }
}

function imprimirTablaConCheckboxes() {
    deseleccionarEstado();
    let arregloConMiembros = [];
    let checkboxDemocrata = document.getElementById('democrata-checkbox');
    let checkboxIndependiente = document.getElementById('independiente-checkbox');
    let checkboxRepublicanos = document.getElementById('republicano-checkbox');

    if (!checkboxDemocrata.checked && !checkboxIndependiente.checked &&
        !checkboxRepublicanos.checked) {
        senateData.members = members;
    }

    if (checkboxDemocrata.checked) {
        arregloConMiembros = filtrarMiembrosPorEstadoOPartido('D');
        senateData.members = arregloConMiembros;
    }

    if (checkboxIndependiente.checked) {
        arregloConMiembros = filtrarMiembrosPorEstadoOPartido('I');
        senateData.members = arregloConMiembros;
    }

    if (checkboxRepublicanos.checked) {
        arregloConMiembros = filtrarMiembrosPorEstadoOPartido('R');
        senateData.members = arregloConMiembros;
    }
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

function destildarCheckboxes() {
    let inputs = document.getElementsByName("party");
    for (let i = 0; i < inputs.length; i++) {
        inputs[i].checked = false;
    }
}

var senateData = new Vue({
    el: "#senate-data",
    data: {
        members : [],
    }
})
