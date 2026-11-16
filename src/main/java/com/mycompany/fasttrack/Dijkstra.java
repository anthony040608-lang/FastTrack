/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.fasttrack;
import com.mycompany.fasttrack.Classes.*;
/**
 Clase para hacer el Dijkstra
 * @author AnthonyHurtado
 * @version 14/11/2026
 * @see Grafoponderado, Vertices, Arista 
 */
public class Dijkstra {
    private Grafoponderado grafo;
    /**
     * Constructor del Dijkstra
     * @param grafo es el grafo el cual se va a usar en el Dijkstra
     */
    public Dijkstra(Grafoponderado grafo) {
        this.grafo = grafo;
    }//cierre del constructor 
    /**
     * Metodo para contar los vertices
     * @return un int con la cantidad de vertices
     */
    private int contarVertices() {
        int total = 0;
        Vertices actual = grafo.getprimervertice();
        while (actual != null) {
            total = total + 1;
            actual = actual.getsiguiente();
        }
        return total;
    }
    /**
     * Metodo para obtener los ids
     * @return un String con el id 
     */
    private String[] obtenerIds() {
        int total = contarVertices();
        String[] ids = new String[total];
        Vertices actual = grafo.getprimervertice();
        int i = 0;
        while (actual != null) {
            ids[i] = actual.getid();
            actual = actual.getsiguiente();
            i = i + 1;
        }
        return ids;
    }
    /**
     * Metodo para buscar indices 
     * @param ids la id que se va a usar para buscar la id 
     * @param buscado es un String para encontrar el inidice
     * @return un int con el indice encontrado
     */
    private int buscarIndice(String[] ids, String buscado) {
        int i = 0;
        while (i < ids.length) {
            if (ids[i] != null && ids[i].equals(buscado)) {
                return i;
            }
            i = i + 1;
        }
        return -1;
    }

    /**
     * Metodo para calcular la ruta mas rapida
     * @param origen estacion de origen
     * @param destino estacion de destino
     * @return un String donde dice la ruta mas rapida
     */
    public String calcularRutaMasRapida(String origen, String destino) {
        String[] ids = obtenerIds();
        int n = ids.length;
        int idxInicio = buscarIndice(ids, origen);
        int idxFin = buscarIndice(ids, destino);

        if (idxInicio == -1 || idxFin == -1) {
            return "Alguna de las estaciones no existe en el grafo.";
        }

        int infinito = 999999999;
        int[] dist = new int[n];
        int[] previo = new int[n];
        boolean[] visitado = new boolean[n];

        int i = 0;
        while (i < n) {
            dist[i] = infinito;
            previo[i] = -1;
            visitado[i] = false;
            i = i + 1;
        }
        dist[idxInicio] = 0;

        int paso = 0;
        while (paso < n) {
            int u = -1;
            int menor = infinito;
            int j = 0;
            while (j < n) {
                if (!visitado[j] && dist[j] < menor) {
                    menor = dist[j];
                    u = j;
                }
                j = j + 1;
            }

            if (u == -1) {
                break;
            }
            visitado[u] = true;

            Vertices vActual = grafo.getprimervertice();
            while (vActual != null && !vActual.getid().equals(ids[u])) {
                vActual = vActual.getsiguiente();
            }

            if (vActual != null) {
                Arista ar = vActual.getprimerarista();
                while (ar != null) {
                    int vIdx = buscarIndice(ids, ar.getid());
                    if (vIdx != -1 && !visitado[vIdx]) {
                        int nuevaDist = dist[u] + ar.getpeso();
                        if (nuevaDist < dist[vIdx]) {
                            dist[vIdx] = nuevaDist;
                            previo[vIdx] = u;
                        }
                    }
                    ar = ar.getsiguientearista();
                }
            }
            paso = paso + 1;
        }

        if (dist[idxFin] == infinito) {
            return "No hay ruta disponible entre " + origen + " y " + destino + ".";
        }

        // Reconstruir ruta en arreglo inverso
        String[] rutaInvertida = new String[n];
        int cantRuta = 0;
        int curr = idxFin;
        while (curr != -1) {
            rutaInvertida[cantRuta] = ids[curr];
            cantRuta = cantRuta + 1;
            curr = previo[curr];
        }

        String textoRuta = "";
        int k = cantRuta - 1;
        while (k >= 0) {
            textoRuta = textoRuta + rutaInvertida[k];
            if (k > 0) {
                textoRuta = textoRuta + " -> ";
            }
            k = k - 1;
        }

        return "Ruta optima: " + textoRuta + "\nTiempo total: " + dist[idxFin] + " min";//suta optima con tiempo que 
        //tarda 
    }

    /**
     * metodo para la deteccion de subredes
     * @return un String con las subredes que hay
     */ 
    public String detectarSubredes() {
        String[] ids = obtenerIds();
        int n = ids.length;
        if (n == 0) {
            return "No hay estaciones cargadas.";
        }

        boolean[] visitado = new boolean[n];
        int[] cola = new int[n];
        String resultado = "Subredes / Componentes Conexos:\n";
        int numSubred = 1;

        int i = 0;
        while (i < n) {
            if (!visitado[i]) {
                int frente = 0;
                int fin = 0;
                visitado[i] = true;
                cola[fin] = i;
                fin = fin + 1;

                resultado = resultado + "Subred " + numSubred + ": ";
                while (frente < fin) {
                    int actualIdx = cola[frente];
                    frente = frente + 1;
                    resultado = resultado + ids[actualIdx] + " ";

                    Vertices v = grafo.getprimervertice();
                    while (v != null && !v.getid().equals(ids[actualIdx])) {
                        v = v.getsiguiente();
                    }
                    if (v != null) {
                        Arista ar = v.getprimerarista();
                        while (ar != null) {
                            int vecIdx = buscarIndice(ids, ar.getid());
                            if (vecIdx != -1 && !visitado[vecIdx]) {
                                visitado[vecIdx] = true;
                                cola[fin] = vecIdx;
                                fin = fin + 1;
                            }
                            ar = ar.getsiguientearista();
                        }
                    }
                }
                resultado = resultado + "\n";
                numSubred = numSubred + 1;
            }
            i = i + 1;
        }
        return resultado;
    }
    /**
     * Metodo para identificar los hubs por centralidad de grado 
     * @return un String con los hubs
     */
    public String identificarHubs() {
        String[] ids = obtenerIds();
        int n = ids.length;
        if (n == 0) {
            return "No hay estaciones cargadas.";
        }

        int[] grados = new int[n];
        int i = 0;
        while (i < n) {
            Vertices v = grafo.getprimervertice();
            while (v != null && !v.getid().equals(ids[i])) {
                v = v.getsiguiente();
            }
            int grado = 0;
            if (v != null) {
                Arista ar = v.getprimerarista();
                while (ar != null) {
                    grado = grado + 1;
                    ar = ar.getsiguientearista();
                }
            }
            grados[i] = grado;
            i = i + 1;
        }

        // Ordenamiento
        int a = 0;
        while (a < n - 1) {
            int b = 0;
            while (b < n - 1 - a) {
                if (grados[b] < grados[b + 1]) {
                    int tempG = grados[b];
                    grados[b] = grados[b + 1];
                    grados[b + 1] = tempG;

                    String tempId = ids[b];
                    ids[b] = ids[b + 1];
                    ids[b + 1] = tempId;
                }
                b = b + 1;
            }
            a = a + 1;
        }

        String texto = "Hubs de Transferencia (Centralidad de Grado):\n";
        int k = 0;
        while (k < n) {
            texto = texto + (k + 1) + ". " + ids[k] + " -> Grado: " + grados[k] + "\n";
            k = k + 1;
        }
        return texto;
    }
}
