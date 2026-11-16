/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.fasttrack;
import com.mycompany.fasttrack.Classes.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
/**
 *Clase para el Panel del grafo 
 * @author Anthony Hurtado
 * @version 14/11/2026
 */


public class PanelLienzoGrafo extends JPanel {
    
    private Grafoponderado grafo;
    public PanelLienzoGrafo() {
        setBackground(new Color(28, 28, 30));
        setPreferredSize(new Dimension(750, 1800));
    }// atributo con los colores y el tamaño para el grafo
    /**
     * Constructor de la clase 
     * @param grafo es el grafo el cual se va a dibujar
     */
    public PanelLienzoGrafo(Grafoponderado grafo) {
        this.grafo = grafo;
        setBackground(new Color(28, 28, 30));
        setPreferredSize(new Dimension(750, 1800));
    }//cierre del constructor 
    /**
     * Metodo para seleccionar el grafo 
     * @param grafo el grafo a seleccionar
     */
    public void setGrafo(Grafoponderado grafo) {
        this.grafo = grafo;
        refrescar();
    }
    /**
     * Metodo para refrescar el grafo
     */
    public void refrescar() {
        int total = contarVertices();
        int filas = (total / 4) + 2;
        setPreferredSize(new Dimension(750, filas * 130));
        revalidate();
        repaint();
    }
    /**
     * Metodo privado para contar lo vertices del grafo
     * @return devuelve el numero total de vertices del grafo
     */
    private int contarVertices() {
        if (grafo == null) {
            return 0;
        }
        int total = 0;
        Vertices actual = grafo.getprimervertice();
        while (actual != null) {
            total = total + 1;
            actual = actual.getsiguiente();
        }
        return total;
    }
    /**
     * Metodo para redibujar el panel 
     * @param g atributo para dibujar dentro del componente 
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int total = contarVertices();
        if (total == 0) {
            return;
        }

        int[] posX = new int[total];
        int[] posY = new int[total];
        int[] radios = new int[total];
        String[] ids = new String[total];

        int columnas = 4;
        int sepX = 165;
        int sepY = 115;

        Vertices actual = grafo.getprimervertice();
        int i = 0;
        while (actual != null) {
            int col = i % columnas;
            int fila = i / columnas;

            posX[i] = 110 + (col * sepX);
            posY[i] = 75 + (fila * sepY);
            ids[i] = actual.getid();

            int grado = 0;
            Arista aCount = actual.getprimerarista();
            while (aCount != null) {
                grado = grado + 1;
                aCount = aCount.getsiguientearista();
            }
            radios[i] = 22 + (grado * 4);
            if (radios[i] > 42) {
                radios[i] = 42;
            }

            actual = actual.getsiguiente();
            i = i + 1;
        }

        // Dibujar aristas y pesos
        actual = grafo.getprimervertice();
        int idxO = 0;
        while (actual != null) {
            Arista ar = actual.getprimerarista();
            while (ar != null) {
                int idxD = buscarIndiceLocal(ids, ar.getid());
                if (idxD != -1) {
                    g.setColor(new Color(180, 185, 195));
                    g.drawLine(posX[idxO], posY[idxO], posX[idxD], posY[idxD]);

                    int mx = (posX[idxO] + posX[idxD]) / 2;
                    int my = (posY[idxO] + posY[idxD]) / 2;
                    g.setColor(Color.WHITE);
                    g.drawString("" + ar.getpeso(), mx + 4, my - 4);
                }
                ar = ar.getsiguientearista();
            }
            actual = actual.getsiguiente();
            idxO = idxO + 1;
        }

        // Dibujar nodos azules con borde negro y etiqueta centrada
        int k = 0;
        while (k < total) {
            int r = radios[k];
            g.setColor(new Color(135, 206, 250));
            g.fillOval(posX[k] - r, posY[k] - r, r * 2, r * 2);

            g.setColor(Color.BLACK);
            g.drawOval(posX[k] - r, posY[k] - r, r * 2, r * 2);
            g.drawString(ids[k], posX[k] - (ids[k].length() * 3), posY[k] + 4);
            k = k + 1;
        }
    }
    /**
     * Metodo para buscar el indice local
     * @param arr arreglo en el cual se quiere buscar
     * @param val Objeto que se quiere buscar dentro del arreglo
     * @return  un int con el indice del objeto que se busco 
     */
    private int buscarIndiceLocal(String[] arr, String val) {
        int j = 0;
        while (j < arr.length) {
            if (arr[j] != null && arr[j].equals(val)) {
                return j;
            }
            j = j + 1;
        }
        return -1;
    }
}