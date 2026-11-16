/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.fasttrack.Classes;

/**
 * Esta clase define las aristas las cuales son las que usa el grafo para simular las conexiones entre cada vertice o estacion
 * @author Anthony Hurtado
 * @version 14/11/2026
 */
public class Arista {
    private String id;
    private int peso;
    private Arista siguientearista;//atributo para concatenar aristas 
    /**
     * Constructor para la clase Arista
     * @param id es un String el cual va a ser el codigo de la estacion al cual se va a enlazar
     * @param peso un int el cual da el peso (tiempo,distancia o algun valor numerico)
     */
    
    public Arista (String id, int peso){
        this.id = id;
        this.peso = peso;
        this.siguientearista = null;//se pone en null para asignarla despues 
    }//cierre del constructor 
    /**
     * Metodo para devolver el id de la estacion al cual esta asociada
     * @return un String el cual es el codigo de la estacion
     */
    public String getid(){
        return id;
    }
    /**
     * Metodo para devolver el peso de la arista 
     * @return retorna un int del peso de la estacion lo cual se asocia a varios valores dependiendo del uso 
     */
    public int getpeso(){
        return peso;
    }
    /**
     * Metodo para conseguir la arista la cual le sigue que esta conectada al mismo vertice
     * @return un objeto de clase Arista el cual es la arista la cual esta conectada al mismo vertice
     */
    public Arista getsiguientearista(){
        return siguientearista;
    }
    /**
     * Metodo para establacer cual es la siguiente arista, no es totalmente obligatoria en el caso de que el vertice 
     * solo tenga una conexion pero en el caso de tener es siempre usada, en el caso de un vertice se busca la 
     * primera arista y todas las demas aristas conectadas se van a conectar usando este metodo
     * @param siguientearista es un objeto de clase Arista la cual es la que le sigue a la conexion del vertice
     */
    public void setsiguiente(Arista siguientearista){
        this.siguientearista = siguientearista;
    }
}


