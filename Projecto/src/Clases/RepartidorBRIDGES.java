package Clases;

import java.util.ArrayList;

public class RepartidorBRIDGES {
    // ================= Atributos =================
    private String codigo;          // Identificador único del repartidor
    private String nombre;          // Nombre del repartidor
    private double capacidadCarga;  // Capacidad máxima en kg
    private boolean disponible;     // Estado: true = libre, false = en misión

    // ================= Constructores =================
    public RepartidorBRIDGES() {}

    public RepartidorBRIDGES(String codigo, String nombre, double capacidadCarga, boolean disponible) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.capacidadCarga = capacidadCarga;
        this.disponible = disponible;
    }

    // ================= Getters & Setters =================
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getCapacidadCarga() {
        return capacidadCarga;
    }

    public void setCapacidadCarga(double capacidadCarga) {
        this.capacidadCarga = capacidadCarga;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    // ================= Métodos estáticos =================

    // Registrar repartidor validando que no se repita el código
    public static boolean registrarRepartidor(ArrayList<RepartidorBRIDGES> lista, RepartidorBRIDGES nuevo) {
        for (RepartidorBRIDGES r : lista) {
            if (r.getCodigo().equalsIgnoreCase(nuevo.getCodigo())) {
                System.out.println(" Ya existe un repartidor con el código: " + nuevo.getCodigo());
                return false;
            }
        }
        lista.add(nuevo);
        System.out.println("Repartidor registrado con código " + nuevo.getCodigo());
        return true;
    }

    // Mostrar repartidores
    public static void mostrarRepartidores(ArrayList<RepartidorBRIDGES> lista) {
        if (lista.isEmpty()) {
            System.out.println("No hay repartidores registrados.");
        } else {
            System.out.println("\n Lista de Repartidores BRIDGES:");
            for (RepartidorBRIDGES r : lista) {
                System.out.println("Código: " + r.getCodigo() +
                                   " | Nombre: " + r.getNombre() +
                                   " | Capacidad de carga: " + r.getCapacidadCarga() + " kg" +
                                   " | Estado: " + (r.isDisponible() ? "Disponible" : "En misión"));
            }
        }
    }

    // Buscar repartidor por código
    public static RepartidorBRIDGES buscarPorCodigo(ArrayList<RepartidorBRIDGES> lista, String codigo) {
        for (RepartidorBRIDGES r : lista) {
            if (r.getCodigo().equalsIgnoreCase(codigo)) {
                return r;
            }
        }
        return null;
    }

    // Ordenar repartidores por capacidad de carga usando Inserción
    public static void ordenarPorCapacidadInsercion(ArrayList<RepartidorBRIDGES> lista) {
        for (int i = 1; i < lista.size(); i++) {
            RepartidorBRIDGES actual = lista.get(i);
            int j = i - 1;

            while (j >= 0 && lista.get(j).getCapacidadCarga() < actual.getCapacidadCarga()) {
                lista.set(j + 1, lista.get(j));
                j--;
            }
            lista.set(j + 1, actual);
        }
        System.out.println("Lista de repartidores ordenada por capacidad de carga (inserción).");
    }
}