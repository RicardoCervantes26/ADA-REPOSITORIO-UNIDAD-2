package Clases;

import java.util.ArrayList;

public class PedidoBRIDGES {
    // ================= Atributos =================
    private String codigo;        // Código único del pedido
    private double distancia;     // Distancia en km
    private double peso;          // Peso del paquete en kg
    private String prioridad;     // Normal, Urgente, Crítico

    // ================= Constructores =================
    public PedidoBRIDGES() {}

    public PedidoBRIDGES(String codigo, double distancia, double peso, String prioridad) {
        this.codigo = codigo;
        this.distancia = distancia;
        this.peso = peso;
        this.prioridad = prioridad;
    }

    // ================= Getters & Setters =================
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    // ================= Métodos estáticos =================

    // Registrar pedido validando que no se repita código
    public static boolean registrarPedido(ArrayList<PedidoBRIDGES> lista, PedidoBRIDGES nuevo) {
        for (PedidoBRIDGES p : lista) {
            if (p.getCodigo().equalsIgnoreCase(nuevo.getCodigo())) {
                System.out.println("Ya existe un pedido con el código: " + nuevo.getCodigo());
                return false;
            }
        }
        lista.add(nuevo);
        System.out.println(" Pedido registrado con código " + nuevo.getCodigo());
        return true;
    }

    // Mostrar pedidos
    public static void mostrarPedidos(ArrayList<PedidoBRIDGES> lista) {
        if (lista.isEmpty()) {
            System.out.println("No hay pedidos registrados.");
        } else {
            System.out.println("\n?Encargos pendientes (BRIDGES):");
            for (PedidoBRIDGES p : lista) {
                System.out.println("Código: " + p.getCodigo() +
                                   " | Distancia: " + p.getDistancia() + " km" +
                                   " | Peso:" + p.getPeso() + " kg" +
                                   " | Prioridad: " + p.getPrioridad());
            }
        }
    }

    // Ordenar pedidos por código usando Inserción
    public static void ordenarPorCodigoInsercion(ArrayList<PedidoBRIDGES> lista) {
        for (int i = 1; i < lista.size(); i++) {
            PedidoBRIDGES actual = lista.get(i);
            int j = i - 1;

            // Comparación alfabética de códigos
            while (j >= 0 && lista.get(j).getCodigo().compareToIgnoreCase(actual.getCodigo()) > 0) {
                lista.set(j + 1, lista.get(j));
                j--;
            }
            lista.set(j + 1, actual);
        }
        System.out.println(" Lista de pedidos ordenada por código (inserción).");
    }
}

