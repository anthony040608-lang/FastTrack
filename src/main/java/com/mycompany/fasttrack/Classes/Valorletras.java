/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.fasttrack.Classes;

/**
 * Clase para darle valor a cada letra la cual se usa para generar el codigo hash para una busqueda mas eficiente
 * @author Anthony Hurtado
 * @version 14/11/2026
 */
public class Valorletras {
    /**
     * Metodo para cambiarle el valor a las letras del codigo de la estacion
     * @param letra un String el cual es una letra para darle un valor 
     * @return un int con el valor de la letra 
     */
    public int letras(String letra){
        int valor = 0;
        switch (letra.toLowerCase()){
            case "a" -> valor = 1;
            case "b" -> valor = 2;
            case "c" -> valor = 3;
            case "d" -> valor = 4;
            case "e" -> valor = 5;
            case "f" -> valor = 6;
            case "g" -> valor = 7;
            case "h" -> valor = 8;
            case "i" -> valor = 9;
            case "j" -> valor = 10;
            case "k" -> valor = 11;
            case "l" -> valor = 12;
            case "m" -> valor = 13;
            case "n" -> valor = 14;
            case "o" -> valor = 15;
            case "p" -> valor = 16;
            case "q" -> valor = 17;
            case "r" -> valor = 18;
            case "s" -> valor = 19;
            case "t" -> valor = 20;
            case "u" -> valor = 21;
            case "v" -> valor = 22;
            case "w" -> valor = 23;
            case "x" -> valor = 24;
            case "y" -> valor = 25;
            case "z" -> valor = 26;
            default -> valor = 27;// en el caso de no ser una letra devuelve este valor 
        }
        return valor;
    }
}

