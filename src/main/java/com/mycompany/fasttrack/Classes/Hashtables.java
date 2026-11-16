/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.fasttrack.Classes;

/**
 * Clase la cual va a generar el codigo hash de la estacion la cual sirve para la busqueda eficiente de esta 
 * @author Anthony Hurtado
 * @version 14/11/2026
 */
public class Hashtables {
    /**
     * Metodo el cual va a ejecutar la generacion del codigo hash
     * @param code es el codigo de la estacion a la cual se le quiere hacer el hash
     * @return es un int el cual va a dar el hash de la estacion
     */
 public int crearhash(String code){
        char[] palabra = code.toCharArray();
        int largo = palabra.length;
        int suma = 0;
        Valorletras valores = new Valorletras();
        for (int letras = 0; letras < largo ; letras++){
            String letra = "" + palabra[letras];//conversion de un int a String 
            int valorletra = valores.letras(letra);
            valorletra = valorletra*(letras + 1);
            suma = suma + valorletra;   
        }
        return suma%541;//valor maximo de la division por lo cual el hash maximo 
        
    } 
    
}
