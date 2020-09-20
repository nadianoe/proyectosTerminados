console.log('imprimo palabraaaaa');

var myName = 'Nadia';
console.log(myName);

var miAge = 24;
console.log(miAge);

var ignasiAge = 32;

var ageDiff = miAge - ignasiAge;
console.log(ageDiff);

if (miAge >= 21) {
    console.log('ya es mayor a 21');
} else {
    console.log('no tiene más de 21');
}

if (miAge > ignasiAge) {
    console.log('usted es mayor que Ignasi');
} else {
    if (miAge === ignasiAge) {
        console.log('usted tiene la misma edad que Ignasi');
    } else {
        console.log('usted es menor a Ignasi');
    }
}

//---------------------------------------------end task1

//ejercicio1

var names = ["Carla", "María", "Ricardo", "Leticia", "Román", "Nadia", "Sebastián", "Federico"];

console.log(names);

names.sort();
console.log("ahora, el arreglo es: " + names);
console.log("el primer nombre es: " + names[0]);
console.log("el último nombre es: " + names[names.length - 1]);

console.log("la lista de personas según el orden alfabético es: ");
for (var i = 0; i < names.length; i++) {
    console.log(names[i]);
}

//ejercicio2
var edades = [];
edades.push(21);
edades.push(22);
edades.push(37);
edades.push(24);
edades.push(32);

console.log("las edades de mis compañeros son: ");
var i = 0;
while (i < edades.length) {
    console.log(edades[i]);
    i++;
}

console.log("las edades pares son: ");
for (var i = 0; i < edades.length; i++) {
    if (edades[i] % 2 === 0) {
        console.log(edades[i]);
    }
}

//ejercicio3
function menorElemento(unArray) {
    var longitud = unArray.length;
    var menorElemento;

    if (longitud === 0) {

        return "el arreglo no contiene elementos. Por lo tanto, no tiene menor elemento.";

    } else {
        menorElemento = unArray[0];
        for (var i = 0; i < longitud; i++) {
            if (unArray[i] <= menorElemento) {
                menorElemento = unArray[i];
            }
        }

        return menorElemento;
    }
}


//otro submit



var arreglito2 = [];
console.log("con arreglo vacio: " + menorElemento(arreglito2));

var arreglito1 = [12, 23, 45, 23, 2, 2, 8, 9, 0, 22, 333];
console.log("el menor elemento del arreglo es: " + menorElemento(arreglito1));


//ejercicio4
function mayorElemento(unArray) {
    var longitud = unArray.length;
    var mayorElemento;

    if (longitud === 0) {

        return "el arreglo no contiene elementos. Por lo tanto, no tiene mayor elemento."
    } else {
        mayorElemento = unArray[0];
        for (var i = 0; i < longitud; i++) {
            if (unArray[i] >= mayorElemento) {
                mayorElemento = unArray[i];
            }
        }

        return mayorElemento;
    }
}

console.log("con arreglo vacio: " + mayorElemento(arreglito2));
console.log("el mayor de lo elementos del arreglo es: " + mayorElemento(arreglito1));

//ejercicio5
function oneBasedValue(unArray, unIndice) {

    for (var j = 0; j < unArray.length; j++) {
        if (j === unIndice - 1) {
            console.log(unArray[j]);
        }
    }
}

oneBasedValue(arreglito1, 1);
oneBasedValue(arreglito1, 2);
oneBasedValue(arreglito1, 11);

//ejercicio6



//funcion auxiliar1
function estaEnArreglo(unArray, valor) {

    var encontrado = false;

    if (unArray.length === 0) {
        encontrado = false;
    } else {
        for (var p = 0; p < unArray.length && !encontrado; p++) {
            if (unArray[p] === valor) {
                encontrado = true;
            }
        }

    }
    return encontrado;
}

var conRepetidos = [1, 2, 3, 4, 2, 4, 5, 6, 7, 7]; //los repetidos son: 2,4,7

function printRepetidos(unArray) {
    var cantidadRepetidos = 0;
    var losRepetidos = [];

    for (var k = 0; k < unArray.length; k++) {
        for (var l = 0; l < unArray.length; l++) {

            if (unArray[k] === unArray[l]) {
                cantidadRepetidos++;
            }
        }

        if (cantidadRepetidos > 1 && !estaEnArreglo(losRepetidos, unArray[k])) {
            losRepetidos.push(unArray[k]);
        }

        cantidadRepetidos = 0;
    }

    console.log("\'" + losRepetidos.join() + "\'");
}

console.log(conRepetidos.join());
printRepetidos(conRepetidos);

var otroConRepetidos = [1, 1, 1, 1, 3, 2, 2, 2, 2];
console.log(otroConRepetidos.join());
printRepetidos(otroConRepetidos);


//ejercicio7

var cosas = [];
cosas.push("casa");
cosas.push("botella");
cosas.push("teclado");
cosas.push("pantalla");
cosas.push("monitor");

console.log(cosas);

function arrayIntoAString(unArray) {

    console.log('\"' + unArray.join('\",\"') + '\"');

}

arrayIntoAString(cosas);

var myColor = [];
myColor.push("Red");
myColor.push("Green");
myColor.push("White");
myColor.push("Black");

arrayIntoAString(myColor);


//otro submit

//ejercicio1
function revertirNumero(numero) {

    var numInvertido;
    var stringDelNumeroIngresado = numero.toString();
    var arrayDeChars = [];
    
    arrayDeChars = stringToArrayDeChars(stringDelNumeroIngresado);

    arrayDeChars = invertirArray(arrayDeChars);

    var stringDelNumeroInvertdo = arrayDeChars.join("");

    numInvertido = parseInt(stringDelNumeroInvertdo);

    return numInvertido;
}

//funciones auxiliares
function invertirArray(unArray) {

    var arrayInvertido = [];

    for (var j = unArray.length - 1; j >= 0; j--) {
        arrayInvertido.push(unArray[j]);
    }

    return arrayInvertido;
}

function stringToArrayDeChars(unString) {
    var arrayDeChars = [];

    for (var j = 0; j < unString.length; j++) {
        arrayDeChars.push(unString.charAt(j));
    }
    
    return arrayDeChars;
}


console.log(revertirNumero(1234567));
console.log(revertirNumero(987654321));

//ejercicio2

function enOrdenAlfabetico(cadena) {
    var arrayDeChars = stringToArrayDeChars(cadena);
    arrayDeChars = arrayDeChars.sort();
    var stringOrdenado = arrayDeChars.join("");
    
    return stringOrdenado;
    
}

var ejemplo = "nadia";
console.log(enOrdenAlfabetico(ejemplo));
ejemplo = "ccccbbbbaaaaazzzz";
console.log(enOrdenAlfabetico(ejemplo));



//funciones auxiliares
function stringToArrayDeStrings(string){
    var arrayDeStrings = string.split(" ");
    return arrayDeStrings;
}


function capitalizarPrimerLetra(string){
    var primerLetra = string.charAt(0);
    primerLetra = primerLetra.toUpperCase();
    string = primerLetra + string.slice(1);

    return string;
}

// ejercicio 3
function wordsToUpperCase(stringConPalabras){

    var arrayDePalabras = stringToArrayDeStrings(stringConPalabras);

    for (var i = 0; i < arrayDePalabras.length; i++) {
        arrayDePalabras[i] = capitalizarPrimerLetra(arrayDePalabras[i]);
    }
 
    stringConPalabras = arrayDePalabras.join(" ");

    return stringConPalabras;

}


console.log(wordsToUpperCase("principa de persia"));
console.log(wordsToUpperCase("programar es divertido"));

//ejercicio4

function palabraMasLarga(stringConPalabras){

    var arrayDePalabras = stringToArrayDeStrings(stringConPalabras);
    var palabraMasLarga = arrayDePalabras[0];

    for (var i = 0; i < arrayDePalabras.length; i++) {

        if (arrayDePalabras[i].length >= palabraMasLarga.length){
            palabraMasLarga = arrayDePalabras[i];

        } 
    }

    var longitud = palabraMasLarga.length;

    if (palabraMasLarga[longitud-1]) {}

    return palabraMasLarga;
}

console.log(palabraMasLarga("Tutorial de Desarrollo Web"));
console.log(palabraMasLarga("Curso de programacion"));
