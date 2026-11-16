/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.fasttrack.Classes;

/**
 *
 * @author antho
 */
public class AlgoritmosGrafo {

    private Grafoponderado grafo;

    public AlgoritmosGrafo(Grafoponderado grafo) {
        this.grafo = grafo;
    }

    private boolean existeVertice(String id) {
        return grafo.buscarverticePorIdPublico(id) != null;
    }

    private Vertices buscarVertice(String id) {
        return grafo.buscarverticePorIdPublico(id);
    }

    private int contarVertices() {
        int c = 0;
        Vertices v = grafo.getprimervertice();
        while (v != null) {
            c++;
            v = v.getsiguiente();
        }
        return c;
    }

    private String[] obtenerIdsVertices() {
        int n = contarVertices();
        String[] ids = new String[n];
        int i = 0;
        Vertices v = grafo.getprimervertice();
        while (v != null) {
            ids[i++] = v.getid();
            v = v.getsiguiente();
        }
        return ids;
    }

    private int indiceDe(String[] ids, String id) {
        for (int i = 0; i < ids.length; i++) {
            if (ids[i].equalsIgnoreCase(id)) return i;
        }
        return -1;
    }

    // ------------------- COMPONENTES CONEXOS (DFS) -------------------
    public String componentesConexosDFS() {
        String[] ids = obtenerIdsVertices();
        int n = ids.length;

        if (n == 0) return "📭 No hay estaciones cargadas.";

        boolean[] visitado = new boolean[n];
        StringBuilder sb = new StringBuilder();
        int comp = 1;

        for (int i = 0; i < n; i++) {
            if (!visitado[i]) {
                sb.append("Subred ").append(comp++).append(": ");

                int[] pila = new int[n];
                int top = 0;

                visitado[i] = true;
                pila[top++] = i;

                boolean primero = true;

                while (top > 0) {
                    int idx = pila[--top];
                    String idActual = ids[idx];

                    if (!primero) sb.append(", ");
                    sb.append(idActual);
                    primero = false;

                    Vertices v = buscarVertice(idActual);
                    if (v == null) continue;

                    Arista a = v.getprimerarista();
                    while (a != null) {
                        int j = indiceDe(ids, a.getid());
                        if (j >= 0 && !visitado[j]) {
                            visitado[j] = true;
                            pila[top++] = j;
                        }
                        a = a.getsiguientearista();
                    }
                }

                sb.append("\n");
            }
        }

        return sb.toString();
    }

    // -------------------------- DIJKSTRA ------------------------------
    public String dijkstraRuta(String origen, String destino) {
        if (!existeVertice(origen) || !existeVertice(destino)) {
            return "❌ Alguna estación no existe en el grafo.";
        }
        if (origen.equalsIgnoreCase(destino)) {
            return "🧭 Ruta mínima: " + origen + " -> (0 min)";
        }

        String[] ids = obtenerIdsVertices();
        int n = ids.length;

        final int INF = 1_000_000_000;

        int[] dist = new int[n];
        int[] prev = new int[n];
        boolean[] usado = new boolean[n];

        for (int i = 0; i < n; i++) {
            dist[i] = INF;
            prev[i] = -1;
            usado[i] = false;
        }

        int s = indiceDe(ids, origen);
        int t = indiceDe(ids, destino);

        dist[s] = 0;

        for (int iter = 0; iter < n; iter++) {
            int u = -1;
            int best = INF;

            for (int i = 0; i < n; i++) {
                if (!usado[i] && dist[i] < best) {
                    best = dist[i];
                    u = i;
                }
            }

            if (u == -1) break;
            usado[u] = true;

            Vertices vertU = buscarVertice(ids[u]);
            if (vertU == null) continue;

            Arista a = vertU.getprimerarista();
            while (a != null) {
                int v = indiceDe(ids, a.getid());
                if (v >= 0 && !usado[v]) {
                    int peso = a.getpeso();
                    if (dist[u] + peso < dist[v]) {
                        dist[v] = dist[u] + peso;
                        prev[v] = u;
                    }
                }
                a = a.getsiguientearista();
            }
        }

        if (dist[t] >= INF) {
            return "🚫 No existe ruta entre " + origen + " y " + destino + ".";
        }

        // reconstruir
        StringBuilder ruta = new StringBuilder();
        int cur = t;
        while (cur != -1) {
            if (ruta.length() == 0) ruta.insert(0, ids[cur]);
            else ruta.insert(0, ids[cur] + " -> " );
            cur = prev[cur];
        }

        return "🧭 Ruta mínima: " + ruta + "\n⏱️ Tiempo total: " + dist[t] + " min";
    }

    // ---------------------------- HUBS --------------------------------
    private int gradoDe(String id) {
        Vertices v = buscarVertice(id);
        if (v == null) return 0;
        int g = 0;
        Arista a = v.getprimerarista();
        while (a != null) {
            g++;
            a = a.getsiguientearista();
        }
        return g;
    }

    public String hubsPorGrado(int topN) {
        int n = contarVertices();
        if (n == 0) return "📭 No hay estaciones cargadas.";

        String[] ids = obtenerIdsVertices();
        int[] grados = new int[n];

        for (int i = 0; i < n; i++) {
            grados[i] = gradoDe(ids[i]);
        }

        int k = topN;
        if (k > n) k = n;

        boolean[] tomado = new boolean[n];
        StringBuilder sb = new StringBuilder();
        sb.append("⭐ Top ").append(k).append(" hubs por grado:\n");

        for (int rep = 0; rep < k; rep++) {
            int bestIdx = -1;
            int best = -1;

            for (int i = 0; i < n; i++) {
                if (!tomado[i] && grados[i] > best) {
                    best = grados[i];
                    bestIdx = i;
                }
            }

            if (bestIdx == -1) break;
            tomado[bestIdx] = true;

            sb.append(rep + 1)
              .append(". ")
              .append(ids[bestIdx])
              .append(" (grado: ")
              .append(grados[bestIdx])
              .append(")\n");
        }

        return sb.toString();
    }
}
