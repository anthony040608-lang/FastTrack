/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.fasttrack.Classes;

/**
 *Esta clase define los vertices los cuales usa el grafo para representar las estaciones ademas de la arista 
 principal de cada vertice
 * @author Anthony Hurtado
 * @version 14/11/20026
 * @see Arista
 */

public class Vertices {
    //campos de la clase 
    private String id;// nombre de la estacion 
    private Arista primerarista;// pirmera arista conectada al vertice 
    private Vertices siguiente;// siguiente vertice al cual esta conectado
    private int idNumerico;//es el codigo hash de la estacion
    /**
     * Constructor para la clase vertices
     * @param id el parametro id es el nombre de la estacion
     * @param idNumerico es el codigo de la estacion con el hash integrado
     */
    public Vertices(String id, int idNumerico){
        this.id = id;
        this.idNumerico = idNumerico;
        this.siguiente = null;
        this.primerarista = null;     
    }//cierre del constructor 
    /**
     * Metodo que devuelve la id (nombre) del vertice
     * @return el nombre de la estacion que guarda el vertice
     */
    public String getid(){
        return id;
    }
    /**
     * Metodo que retorna el codigo de la estacion
     * @return codigo del hash de la estacion
     */
    
    public int getidNumerico(){
        return idNumerico;
    }
    
    /**
     * Metodo que devuelve la primera arista del vertice
     * @return primera arista asociada al vertice 
     */
    public Arista getprimerarista(){
        return primerarista;
    }
    /**
     *Metodo que devuelve el vertice que le sigue 
     * @return el siguiente vertice que le sigue al actual
     */
    public Vertices getsiguiente(){
        return siguiente;
    }
    /**
     * Metodo que le da valor a la primera arista del vertice
     * @param arista es la arista central del vertice
     */
    public void setprimerarista (Arista arista){
        this.primerarista = arista;  
    }
    /**
     * Metodo para darle el valor al vertice que le sigue al vertice actual 
     * @param siguiente es un valor de clase Vertices 
     */
    public void setsiguiente(Vertices siguiente){
        this.siguiente = siguiente;
    }
}
    
    
    
    
    
    
    
    
    
    

