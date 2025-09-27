package Clases;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class PedidoBRIDGES {
    private String codigo;
    private double distancia;
    private double peso;
    private String prioridad;
    private String estado; // Pendiente, Asignado, En camino, Entregado

    public PedidoBRIDGES() {}

    public PedidoBRIDGES(String codigo, double distancia, double peso, String prioridad) {
        this.codigo = codigo;
        this.distancia = distancia;
        this.peso = peso;
        this.prioridad = prioridad;
        this.estado = "Pendiente";
    }

    // Getters y Setters
    public String getCodigo() { return codigo; }
    public double getDistancia() { return distancia; }
    public double getPeso() { return peso; }
    public String getPrioridad() { return prioridad; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    // Gestor de pedidos con HashMap
    public static class GestorPedidos {
        private static HashMap<String, PedidoBRIDGES> mapaPedidos = new HashMap<>();
        private static final String ARCHIVO = "pedidos_empresa.txt";

        static {
            cargarDesdeArchivo();
        }

        public static boolean registrarPedido(PedidoBRIDGES pedido) {
            if (mapaPedidos.containsKey(pedido.getCodigo())) {
                System.out.println("Error: Ya existe un pedido con ese código");
                return false;
            }
            mapaPedidos.put(pedido.getCodigo(), pedido);
            guardarEnArchivo();
            return true;
        }

        public static PedidoBRIDGES buscarPorCodigo(String codigo) {
            return mapaPedidos.get(codigo);
        }

        public static ArrayList<PedidoBRIDGES> obtenerPedidosPendientes() {
            ArrayList<PedidoBRIDGES> pendientes = new ArrayList<>();
            for (PedidoBRIDGES pedido : mapaPedidos.values()) {
                if (pedido.getEstado().equals("Pendiente")) {
                    pendientes.add(pedido);
                }
            }
            return pendientes;
        }

        public static ArrayList<PedidoBRIDGES> obtenerTodosPedidos() {
            return new ArrayList<>(mapaPedidos.values());
        }

        public static void ordenarPorCodigoInsercion(ArrayList<PedidoBRIDGES> lista) {
            for (int i = 1; i < lista.size(); i++) {
                PedidoBRIDGES actual = lista.get(i);
                int j = i - 1;
                while (j >= 0 && lista.get(j).getCodigo().compareToIgnoreCase(actual.getCodigo()) > 0) {
                    lista.set(j + 1, lista.get(j));
                    j--;
                }
                lista.set(j + 1, actual);
            }
        }

        private static void guardarEnArchivo() {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO))) {
                for (PedidoBRIDGES pedido : mapaPedidos.values()) {
                    writer.write(pedido.getCodigo() + "|" + pedido.getDistancia() + "|" + 
                               pedido.getPeso() + "|" + pedido.getPrioridad() + "|" + pedido.getEstado());
                    writer.newLine();
                }
            } catch (IOException e) {
                System.out.println("Error guardando pedidos: " + e.getMessage());
            }
        }

        private static void cargarDesdeArchivo() {
            File archivo = new File(ARCHIVO);
            if (!archivo.exists()) return;

            try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    String[] datos = linea.split("\\|");
                    if (datos.length == 5) {
                        PedidoBRIDGES pedido = new PedidoBRIDGES(
                            datos[0], Double.parseDouble(datos[1]), 
                            Double.parseDouble(datos[2]), datos[3]
                        );
                        pedido.setEstado(datos[4]);
                        mapaPedidos.put(datos[0], pedido);
                    }
                }
            } catch (IOException e) {
                System.out.println("Error cargando pedidos: " + e.getMessage());
            }
        }
    }
}