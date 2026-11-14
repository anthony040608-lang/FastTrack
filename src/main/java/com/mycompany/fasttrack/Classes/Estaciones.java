/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.fasttrack.Classes;

/**
 *
 * @author antho
 */
public class Estaciones {
    public int id;
    public String nombre;
    private String tipo;
    private boolean abierto;
    public boolean hub;
    
    public void CrearEstacion(Estaciones estacion, String name, String tipe, boolean open, boolean centro){
        Hashtables codificacion = new Hashtables();
        estacion.id = codificacion.crearhash(name);
        estacion.nombre = name;
        estacion.tipo = tipe;
        estacion.abierto = open;
        estacion.hub = centro;
        
        
    }
    public int leerid(Estaciones e){
        return e.id;
        
    }
    public String leernombre(Estaciones e){
        return e.nombre;
    }
    public String leertipo(Estaciones e){
        return e.tipo;
    }
    public boolean leerdispon (Estaciones e){
        return e.abierto;
    }
    public boolean leerhub (Estaciones e ){
        return e.hub;
    }
    
}

