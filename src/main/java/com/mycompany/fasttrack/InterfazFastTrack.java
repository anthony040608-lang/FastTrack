/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.fasttrack;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import com.mycompany.fasttrack.Classes.*;
/**
 *Clase interfaz la es la principal del proyecto
 * @author Anthony Hurtado
 * @version 14/11/2026
 * @see GestorTablahash, Grafoponderado, RepositorioEstaciones, Dijkstra, PanelLienzoGrafo,java.awt.BorderLayout,java.awt.Color,
 * javax.swing.JButton, javax.swing.JFrame, javax.swing.JOptionPane, javax.swing.JPanel, javax.swing.JScrollPaneww, 
 * javax.swing.JTextArea
 */
public class InterfazFastTrack extends javax.swing.JFrame {
    private GestorTablahash gestorHash;
    private Grafoponderado grafo;
    private RepositorioEstaciones repo;
    private Dijkstra motorRutas;
    private PanelLienzoGrafo lienzo;
    private JTextArea areaResultados;
    
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(InterfazFastTrack.class.getName());

    /**
     * constructor de la clase IntergazFastTrack
     */
    public InterfazFastTrack() {
        initComponents();
        setTitle("UrbanRoute / FastTrack - Red de Transporte");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1150, 720);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8, 8));

        gestorHash = new GestorTablahash(600);
        grafo = new Grafoponderado(gestorHash);
        repo = new RepositorioEstaciones(gestorHash, grafo);
        motorRutas = new Dijkstra(grafo);

        repo.cargarArchivoPorDefecto();

        javax.swing.JPanel panelBotones = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 6, 6));
        panelBotones.setBackground(new java.awt.Color(30, 34, 42));

        javax.swing.JButton btnCargar = crearBoton("Cargar CSV");
        javax.swing.JButton btnGuardar = crearBoton("Actualizar Repositorio");
        javax.swing.JButton btnAgregarEst = crearBoton("Agregar Estacion");
        javax.swing.JButton btnAgregarCon = crearBoton("Agregar Conexion");
        javax.swing.JButton btnEliminarEst = crearBoton("Cerrar/Eliminar Estacion");
        javax.swing.JButton btnDijkstra = crearBoton("Ruta Menor Tiempo");
        javax.swing.JButton btnSubredes = crearBoton("Ver Subredes");
        javax.swing.JButton btnHubs = crearBoton("Ver Hubs");

        panelBotones.add(btnCargar);
        panelBotones.add(btnGuardar);
        panelBotones.add(btnAgregarEst);
        panelBotones.add(btnAgregarCon);
        panelBotones.add(btnEliminarEst);
        panelBotones.add(btnDijkstra);
        panelBotones.add(btnSubredes);
        panelBotones.add(btnHubs);
    

        getContentPane().add(panelBotones, java.awt.BorderLayout.NORTH);

        // Centro: lienzo del grafo
        lienzo = new PanelLienzoGrafo(grafo);
        javax.swing.JScrollPane scrollGrafo = new javax.swing.JScrollPane(lienzo);
        scrollGrafo.getViewport().setBackground(new java.awt.Color(28, 28, 30));
        getContentPane().add(scrollGrafo, java.awt.BorderLayout.CENTER);

        // Derecha: panel de resultados
        areaResultados = new javax.swing.JTextArea();
        areaResultados.setEditable(false);
        areaResultados.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 13));
        areaResultados.setBackground(new java.awt.Color(245, 247, 250));
        javax.swing.JScrollPane scrollTexto = new javax.swing.JScrollPane(areaResultados);
        scrollTexto.setPreferredSize(new java.awt.Dimension(360, 0));
        getContentPane().add(scrollTexto, java.awt.BorderLayout.EAST);

    // Conectar eventos de botones
    btnCargar.addActionListener(e -> {
        int resp = javax.swing.JOptionPane.showConfirmDialog(
            this,
            "¿Desea guardar los cambios actuales en memoria antes de cargar otro archivo?",
            "Alerta de Sobrescritura",
            javax.swing.JOptionPane.YES_NO_CANCEL_OPTION
        );
        if (resp == javax.swing.JOptionPane.CANCEL_OPTION || resp == javax.swing.JOptionPane.CLOSED_OPTION) {
            return;
        }
        if (resp == javax.swing.JOptionPane.YES_OPTION) {
            repo.actualizarRepositorio();
        }
        gestorHash = new GestorTablahash(600);
        grafo = new Grafoponderado(gestorHash);
        repo = new RepositorioEstaciones(gestorHash, grafo);
        motorRutas = new Dijkstra(grafo);
        javax.swing.JFileChooser selector = new javax.swing.JFileChooser();
        int opcion = selector.showOpenDialog(this);
        if (opcion == javax.swing.JFileChooser.APPROVE_OPTION) {
        java.io.File seleccionado = selector.getSelectedFile();
        repo.cargarCSVPrueba(seleccionado, gestorHash, grafo);
        lienzo.setGrafo(grafo);
        lienzo.refrescar();
        areaResultados.setText("Archivo cargado correctamente: " + seleccionado.getName());
    }
});

    btnGuardar.addActionListener(e -> {
        repo.actualizarRepositorio();
        areaResultados.setText("Repositorio actualizado en disco.");
    });

    btnAgregarEst.addActionListener(e -> mostrarDialogoAgregarEstacion());
    btnAgregarCon.addActionListener(e -> mostrarDialogoAgregarConexion());

    btnEliminarEst.addActionListener(e -> {
        String idBorrar = javax.swing.JOptionPane.showInputDialog(this, "ID de estacion a cerrar/eliminar:");
        if (idBorrar != null && !idBorrar.trim().isEmpty()) {
            eliminarEstacionEnMemoria(idBorrar.trim());
            lienzo.refrescar();
            areaResultados.setText("Estacion " + idBorrar.trim() + " eliminada de la red en memoria.");
        }
    });

    btnDijkstra.addActionListener(e -> {
        String origen = javax.swing.JOptionPane.showInputDialog(this, "Estacion Origen (ID):");
        String destino = javax.swing.JOptionPane.showInputDialog(this, "Estacion Destino (ID):");
        if (origen != null && destino != null) {
            areaResultados.setText(motorRutas.calcularRutaMasRapida(origen.trim(), destino.trim()));
        }
    });

    btnSubredes.addActionListener(e -> areaResultados.setText(motorRutas.detectarSubredes()));
    btnHubs.addActionListener(e -> areaResultados.setText(motorRutas.identificarHubs()));

    lienzo.refrescar();
    getContentPane().revalidate();
    getContentPane().repaint();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new InterfazFastTrack().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
private javax.swing.JButton crearBoton(String texto) {
        javax.swing.JButton btn = new javax.swing.JButton(texto);
        btn.setFocusPainted(false);
        btn.setBackground(new java.awt.Color(52, 73, 94));
        btn.setForeground(java.awt.Color.WHITE);
        return btn;
    }


    private void mostrarDialogoAgregarEstacion() {
        String code = javax.swing.JOptionPane.showInputDialog(this, "Codigo/ID de la nueva estacion:");
    if (code == null || code.trim().isEmpty()) {
        return;
    }
    String name = javax.swing.JOptionPane.showInputDialog(this, "Nombre de la estacion:");
    if (name == null || name.trim().isEmpty()) {
        name = code.trim();
    }
    String line = javax.swing.JOptionPane.showInputDialog(this, "Linea:");
    if (line == null) {
        line = "";
    }
    String zone = javax.swing.JOptionPane.showInputDialog(this, "Zona:");
    if (zone == null) {
        zone = "";
    }
    String capStr = javax.swing.JOptionPane.showInputDialog(this, "Capacidad de pasajeros por hora:");
    int cap = convertirEnteroSimple(capStr);

    int respAbierto = javax.swing.JOptionPane.showConfirmDialog(
        this, "¿La estacion esta abierta?", "Estado de estacion", javax.swing.JOptionPane.YES_NO_OPTION
    );
    boolean abierta = (respAbierto == javax.swing.JOptionPane.YES_OPTION);

    int respHub = javax.swing.JOptionPane.showConfirmDialog(
        this, "¿Es estacion Hub / centro?", "Tipo de estacion", javax.swing.JOptionPane.YES_NO_OPTION
    );
    boolean esHub = (respHub == javax.swing.JOptionPane.YES_OPTION);

    Estaciones nuevaEstacion = new Estaciones();
    nuevaEstacion.CrearEstacion(nuevaEstacion, code.trim(), name.trim(), line.trim(), zone.trim(), cap, abierta, esHub);

    gestorHash.Insertar(nuevaEstacion);
    grafo.agregarvertices(code.trim(), nuevaEstacion.id);
    lienzo.refrescar();
    areaResultados.setText("Estacion agregada: " + code.trim() + " (" + name.trim() + ")");

    }

    private void mostrarDialogoAgregarConexion() {
        String origen = javax.swing.JOptionPane.showInputDialog(this, "ID estacion origen:");
        if (origen == null || origen.trim().isEmpty()) {
            return;
        }
        String destino = javax.swing.JOptionPane.showInputDialog(this, "ID estacion destino:");
        if (destino == null || destino.trim().isEmpty()) {
            return;
        }
        String pesoStr = javax.swing.JOptionPane.showInputDialog(this, "Peso / tiempo de viaje:");
        int peso = convertirEnteroSimple(pesoStr);

        grafo.insertararista(origen.trim(), destino.trim(), peso);
        lienzo.refrescar();
        areaResultados.setText("Conexion agregada: " + origen.trim() + " <-> " + destino.trim() + " (" + peso + ")");
    }

    private void eliminarEstacionEnMemoria(String idBorrar) {
        Vertices v = grafo.getprimervertice();
        int idNum = -1;
        while (v != null) {
            if (v.getid().equals(idBorrar)) {
                idNum = v.getidNumerico();
                break;
            }
            v = v.getsiguiente();
        }

        grafo.eliminarestacion(idBorrar);
        if (idNum != -1) {
            gestorHash.eliminar(idNum);
        }
    }

    private int convertirEnteroSimple(String texto) {
        if (texto == null) {
            return 0;
        }
        String limpio = texto.trim();
        if (limpio.isEmpty()) {
            return 0;
        }
        int signo = 1;
        int i = 0;
        if (limpio.charAt(0) == '-') {
            signo = -1;
            i = 1;
        }
        int resultado = 0;
        while (i < limpio.length()) {
            char c = limpio.charAt(i);
            if (c >= '0' && c <= '9') {
                resultado = (resultado * 10) + (c - '0');
            }
            i = i + 1;
        }
        return resultado * signo;
    }
}
