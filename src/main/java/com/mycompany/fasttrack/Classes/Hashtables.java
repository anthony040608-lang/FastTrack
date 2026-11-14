/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.fasttrack.Classes;

/**
 *
 * @author antho
 */
public class Hashtables {
    
 public int crearhash(String code){
        char[] palabra = code.toCharArray();
        int largo = palabra.length;
        int suma = 0;
        Valorletras valores = new Valorletras();
        for (int letras = 0; letras < largo ; letras++){
            String letra = "" + palabra[letras];
            int valorletra = valores.letras(letra);
            valorletra = valorletra*(letras + 1);
            suma = suma + valorletra;
        }
        return suma%31;
        
    } 
    
}
