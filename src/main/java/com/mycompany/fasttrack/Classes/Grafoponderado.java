/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.fasttrack.Classes;

/**
 * Clase la cual va a crear el gafo ponderado de la estaciones
 * @author Anthony Hurtado
 * @version 14/11/2026
 * @see Vertices, GestorTablaHash, Arista
 */
public class Grafoponderado {
    private Vertices primervertice;
    private GestorTablahash tablahash;
    /**
     * Constructor de la Clase Grafoponderado
     * @param tablahash la tabla hash de la cual se va crear el grafoponderado
     */
    public Grafoponderado (GestorTablahash tablahash){
        this.tablahash = tablahash;
        this.primervertice = null;
    }//cierre del constructor 
    /**
     * Metodo para buscar un vertice por id
     * @param id un String con el codigo de la estacion
     * @return un objeto de clase Vertices el cual es el que esta asociado con el id dado
     */
    public Vertices buscarverticePorIdPublico(String id){
        return buscarvertice(id);
    }
    
    /**
     * Metodo para agregar un vertice al grafo 
     * @param id es el id del vertice a agregar
     * @param idNumerico es el id numerico del vertice a agregar 
     */
    public void agregarvertices(String id, int idNumerico){
        Vertices verticenuevo = new Vertices(id, idNumerico);
        verticenuevo.setsiguiente(primervertice);
        primervertice = verticenuevo;
    }
    /**
     * Metodo para buscar un vertice dentro del grafo
     * @param id es el id del vertice a buscar
     * @return retorna un objeto de clase Vertices el cual va a ser el buscado
     */
    public Vertices buscarvertice(String id){
        Vertices verticebuscado = primervertice;
        while (verticebuscado != null){
            if (verticebuscado.getid().equals(id)){
                return verticebuscado;
            }
            verticebuscado = verticebuscado.getsiguiente();
        }      
        return null;
    }
    /**
     * Metodo para eliminar una estacion(vertice) del grafo
     * @param id id del Vertice a eliminar
     */
    public void eliminarestacion(String id) {
        Vertices prevV = null;
        Vertices curV = primervertice;

        while (curV != null) {
            if (curV.getid().equalsIgnoreCase(id)) {
                if (prevV == null) 
                    primervertice = curV.getsiguiente();
                    else prevV.setsiguiente(curV.getsiguiente());
                break;
                }
                prevV = curV;
                curV = curV.getsiguiente();
            }

            Vertices v = primervertice;
            while (v != null) {
                Arista prevA = null;
                Arista a = v.getprimerarista();

                while (a != null) {
                    if (a.getid().equalsIgnoreCase(id)) {
                        if (prevA == null) {
                            v.setprimerarista(a.getsiguientearista());
                            a = v.getprimerarista();
                        } else {
                            prevA.setsiguiente(a.getsiguientearista());
                            a = prevA.getsiguientearista();
                        }
                    } else {
                        prevA = a;
                        a = a.getsiguientearista();
                }
            }

            v = v.getsiguiente();
        }
    }
    /**
     * Metodo para insertar una arista en el grafo
     * @param origen estacion de origen de la arista
     * @param destino estacion a donde se quiere que llegue la arista
     * @param peso el peso de la arista
     */
    public void insertararista(String origen, String destino, int peso) {
        Vertices verticeorigen = buscarvertice(origen);
        Vertices verticedestino = buscarvertice(destino);
        if (verticeorigen == null || verticedestino == null) {
            return;
        }   

        Arista aristanueva1 = new Arista(destino, peso);
        aristanueva1.setsiguiente(verticeorigen.getprimerarista());
        verticeorigen.setprimerarista(aristanueva1);

        Arista aristanueva2 = new Arista(origen, peso);
        aristanueva2.setsiguiente(verticedestino.getprimerarista());
        verticedestino.setprimerarista(aristanueva2);
    }
    /**
     * Metodo para obtener el peso de una arista 
     * @param origen Estacion de origen de la arista
     * @param destino estacion del destino de la arista
     * @return int cualquiera debido a que en el return del if ya se devolvio el valor del peso
     */
    public int obetenerpeso (String origen, String destino){
        Vertices verticeorigen = buscarvertice(origen);
        Arista aristaorigen = verticeorigen.getprimerarista();
        while (aristaorigen != null){
            if (aristaorigen.getid().equals(destino)){
                return aristaorigen.getpeso();            
            }
            aristaorigen = aristaorigen.getsiguientearista();
        }
        return 6;
    }
    /**
     * Metodo para conseguir el primer vertice del grafo
     * @return un objeto de clase Vertice para objetener el primer vertice del grafo
     */
    public Vertices getprimervertice(){
        return primervertice;
    }
    
}
    
    
    
    

