/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.fasttrack.Classes;

/**
 * Clase la cual va crear el objeto Estaciones el cual es primordial para el programa 
 * @author Anthony Hurtado
 * @version 14/11/2026
 * @see Hashtables
 */
public class Estaciones {
    public int id;
    public String nombre;
    public String linea;
    public String zona;
    public int cap_pas_hora;
    public boolean abierto;
    private boolean hub;// si es un centro con muchas aristas 
    public Estaciones siguiente;
    /**
     * Constructor de la clase estaciones
     * @param estacion objeto de la clase Estaciones el cual va a realizar el el metodo
     * @param code es el codigo de la estacion a crear 
     * @param name es el nombre de la estacion a crear
     * @param line es la linea a la cual pertenece la estacion
     * @param zone es la zona en la cual se encuentra la estacion
     * @param cantidad_por_hora es la cantidad de pasajeros por hora
     * @param open si la estacion se encuentra abierta 
     * @param centro si al estacion es un centro por tener muchas conexiones 
     */
    public void CrearEstacion(Estaciones estacion,String code, String name, String line, String zone,int cantidad_por_hora, boolean open, boolean centro){
        Hashtables codificacion = new Hashtables();
        estacion.id = codificacion.crearhash(code);
        estacion.nombre = name;
        estacion.linea = line;
        estacion.zona = zone;
        estacion.cap_pas_hora = cantidad_por_hora;
        estacion.abierto = open;
        estacion.hub = centro;
        
        
    }//cierre del constructor 
}

