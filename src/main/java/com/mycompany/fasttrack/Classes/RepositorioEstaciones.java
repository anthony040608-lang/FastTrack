/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.fasttrack.Classes;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.swing.JFileChooser;

/**
 *Clase para crear los repositorios de las estaciones 
 * @author Anthony Hurtado
 * @version 14/11/2026
 * @see java.io.File, java.io.FileNotFoundException, java.io.PrintWriter, java.util.Scanner, javax.swing.JFileChooser,
 * GestorTablahash, Grafoponderado 
 */
public class RepositorioEstaciones {
    private String rutaArchivoActual = "archivo_maestro.txt";
    private GestorTablahash gestorHash;
    private Grafoponderado grafo;
    /**
     * Constructor de la Clase RepositorioEstaciones
     * @param gestorHash 
     * @param grafo 
     */
    public RepositorioEstaciones(GestorTablahash gestorHash, Grafoponderado grafo) {
        this.gestorHash = gestorHash;
        this.grafo = grafo;
    }//cierrre del constructor 
    /**
     * Metodo para cargar el archivo por defecto al iniciar
     */
    public void cargarArchivoPorDefecto() {
        leerArchivo(rutaArchivoActual);
    }
    /**
     * Metodo para cargar un archivo
     */
    public void cargarConJFileChooser() {
        JFileChooser selector = new JFileChooser();
        int opcion = selector.showOpenDialog(null);

        if (opcion == JFileChooser.APPROVE_OPTION) {
            File archivoElegido = selector.getSelectedFile();
            rutaArchivoActual = archivoElegido.getPath();
            leerArchivo(rutaArchivoActual);
        }
    }/**
     * Metodo para carga un CSV
     * @param archivo archivo a cargar 
     * @param gestorHash la tabla en la cual se va a guardar la informacion extraida del archivo
     * @param grafo grafo en le cual se van a crear los vertices y aristas
     */
    public void cargarCSVPrueba(java.io.File archivo, GestorTablahash gestorHash, Grafoponderado grafo) {
        if (archivo == null || !archivo.exists()) {
            return;
        }

        java.io.BufferedReader br = null;
        try {
            br = new java.io.BufferedReader(new java.io.FileReader(archivo));
            String linea;
            int modo = 0; // 0 = sin seccion, 1 = estaciones, 2 = aristas

                while ((linea = br.readLine()) != null) {
                    String limpia = linea.trim();
                    if (limpia.isEmpty()) {
                        continue;
                    }

                    // Detectar encabezados
                    if (limpia.startsWith("ID_Estacion,")) {
                        modo = 1;
                        continue;
                    }
                    if (limpia.startsWith("Estacion_Origen,")) {
                        modo = 2;
                    continue;
                    }

                    String[] partes = dividirComasManual(limpia);

                    if (modo == 1 && partes.length >= 5) {
                        String idEstacion = partes[0].trim();
                        String nombre = partes[1].trim();
                        String lineaMetro = partes[2].trim();
                        String zona = partes[3].trim();
                        int capacidad = convertirEnteroManual(partes[4].trim());

                        Estaciones est = new Estaciones();
                        est.CrearEstacion(est, idEstacion, nombre, lineaMetro, zona, capacidad, true, false);
                        gestorHash.Insertar(est);
                        grafo.agregarvertices(idEstacion, est.id);
                    } else if (modo == 2 && partes.length >= 3) {
                        String origen = partes[0].trim();
                        String destino = partes[1].trim();
                        int tiempo = convertirEnteroManual(partes[2].trim());

                        grafo.insertararista(origen, destino, tiempo);
                    }
                }
                } catch (java.io.IOException e) {
                    javax.swing.JOptionPane.showMessageDialog(
                    null,
                    "Error al leer el archivo CSV: " + e.getMessage(),
                    "Error de lectura",
                    javax.swing.JOptionPane.ERROR_MESSAGE
                );
            } finally {
                if (br != null) {
                    try {
                        br.close();
                } catch (java.io.IOException ex) {
                // Ignorar error al cerrar
            }
        }
    }
}
/**
 * Metodo para dividir las comas 
 * @param texto El texto del cual se le quieren dividir las comas 
 * @return texto con las comas divididas 
 */
private String[] dividirComasManual(String texto) {
    int comas = 0;
    int i = 0;
    while (i < texto.length()) {
        if (texto.charAt(i) == ',') {
            comas = comas + 1;
        }
        i = i + 1;
    }

    String[] resultado = new String[comas + 1];
    int inicio = 0;
    int idx = 0;
    i = 0;
    while (i <= texto.length()) {
        if (i == texto.length() || texto.charAt(i) == ',') {
            resultado[idx] = texto.substring(inicio, i);
            idx = idx + 1;
            inicio = i + 1;
        }
        i = i + 1;
    }
    return resultado;
}
/**
 * Metodo para convertir un String a un numero entero
 * @param texto el String que se quiere convertir en un int
 * @return un int que es el resultado de la conversion
 */
private int convertirEnteroManual(String texto) {
    if (texto == null || texto.isEmpty()) {
        return 0;
    }
    int signo = 1;
    int i = 0;
    if (texto.charAt(0) == '-') {
        signo = -1;
        i = 1;
    }
    int valor = 0;
    while (i < texto.length()) {
        char c = texto.charAt(i);
        if (c >= '0' && c <= '9') {
            valor = (valor * 10) + (c - '0');
        }
        i = i + 1;
    }
    return valor * signo;
}

    /**
     * Metodo que lee el archivo línea por línea
     * @param ruta es la ruta del archivo del cual se quiere leer
     */
    public void leerArchivo(String ruta) {
        File archivo = new File(ruta);

        if (!archivo.exists()) {
            return;
        }

        try {
            Scanner lector = new Scanner(archivo);

            while (lector.hasNextLine()) {
                String linea = lector.nextLine().trim();

                if (!linea.isEmpty()) {
                    procesarLinea(linea);
                }
            }

            lector.close();
        } catch (Exception e) {
        }
    }
/**
 * Metodo para leer una linea 
 * @param linea Linea la cual se quiere leer
 */
    private void procesarLinea(String linea) {
        String[] partes = linea.split(",");

        if (partes.length == 5) {
            String idEstacion = partes[0].trim();
            String nombre = partes[1].trim();
            String lineaMetro = partes[2].trim();
            String zona = partes[3].trim();
            int capacidad = Integer.parseInt(partes[4].trim());

            Estaciones estacion = new Estaciones();
            int convertidor = 0;
            int i = 0;
            while (i< idEstacion.length()){
                char id = idEstacion.charAt(i);
                int digito = id - 'o';
                convertidor = convertidor * 10 + digito;
                i++;
            }
            estacion.id = convertidor;
            estacion.nombre = nombre;
            estacion.linea = lineaMetro;
            estacion.zona = zona;
            estacion.cap_pas_hora = capacidad;

            gestorHash.Insertar(estacion);
            grafo.agregarvertices(idEstacion, estacion.id);
        }


        if (partes.length == 3) {
            String origen = partes[0].trim();
            String destino = partes[1].trim();
            int tiempoMinutos = Integer.parseInt(partes[2].trim());

            grafo.insertararista(origen, destino, tiempoMinutos);
        }
    }
    /**
     * Metodo para actualizar el repositorio
     */
    public void actualizarRepositorio() {
        try {
            PrintWriter escritor = new PrintWriter(rutaArchivoActual);

            //Escribir estaciones desde el grafo y consultarlas en GestorTablahash
            Vertices actual = grafo.getprimervertice();
            while (actual != null) {
                Estaciones est = gestorHash.buscar(actual.getidNumerico());
                if (est != null) {
                    escritor.println(
                        est.id + "," +
                        est.nombre + "," +
                        est.linea + "," +
                        est.zona + "," +
                        est.cap_pas_hora
                    );
                }
                actual = actual.getsiguiente();
            }

            //Escribir aristas (evitando duplicar si es bidireccional)
            Vertices v = grafo.getprimervertice();
            while (v != null) {
                Arista ar = v.getprimerarista();
                while (ar != null) {
                    if (v.getid().compareToIgnoreCase(ar.getid()) < 0) {
                        escritor.println(v.getid() + "," + ar.getid() + "," + ar.getpeso());
                    }
                    ar = ar.getsiguientearista();
                }
                v = v.getsiguiente();
            }

            escritor.close();
        } catch (FileNotFoundException e) {
        }
    }
}

