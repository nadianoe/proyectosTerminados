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
