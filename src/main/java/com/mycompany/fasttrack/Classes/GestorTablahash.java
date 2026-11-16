/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.fasttrack.Classes;


/**
 *Clase la cual va a crear la tabla hash para el orden de las estacione
 * @author Anthony Hurtado
 * @version 14/11/2026
 * @see Estaciones
 */
public class GestorTablahash {
    private Estaciones []tabla ;
    public Estaciones next;
    /**
     * Constructor de la tabla hash 
     * @param lenght es un int el cual determina el largo de la tabla que viene dado del hash maximo que se pueda
     */
    public GestorTablahash (int lenght){
        tabla = new Estaciones [lenght];
    }//cierre del constructor
    /**
     * Metodo para insertar una estacion dentro de la tabla
     * @param estacion es un objeto de clase Estaciones el cual es el que se quiere insertar
     */
    public void Insertar(Estaciones estacion){
        int indice = estacion.id;
        
        if (tabla [indice] == null){
            tabla[indice] = estacion;
        }else{
            estacion.siguiente = tabla[indice];
            tabla[indice] = estacion;
            
        }
    }
    /**
     * Metodo para eliminar una estacion de la tabla hash
     * @param id es el id de la estacion la cual busca ser eliminada
     */
    public void eliminar(int id) {
        if (id < 0 || id >= tabla.length) {
            return;
        }
        Estaciones anterior = null;
        Estaciones actual = tabla[id];

        while (actual != null) {
            if (actual.id == id) {
                if (anterior == null) {
                    tabla[id] = actual.siguiente;
                } else {
                    anterior.siguiente = actual.siguiente;
            }
            return;
        }
        anterior = actual;
        actual = actual.siguiente;
        }
    }
    /**
     * Metodo para buscar una estacion dentro de la tabla hash 
     * @param id es el codigo hash de la estacion
     * @return un objeto de tipo Estaciones el cual va ser el valor que se buscaba 
     */
    public Estaciones buscar (int id){
        if (id < 0 || id >= tabla.length){
            return null;
        }
        Estaciones actual = tabla[id];
        while (actual != null){
            if(actual.id == id){
                return actual;
            }
            actual = actual.siguiente;
        }
        return null;
    }
    
}
