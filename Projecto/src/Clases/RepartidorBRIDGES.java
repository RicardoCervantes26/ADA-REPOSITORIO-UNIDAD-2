package Clases;

import java.util.ArrayList;
import java.util.HashMap;  // ✅ IMPORT CORRECTO
import java.util.Map;

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

    // ================= CLASE INTERNA PARA HASHMAP =================
    public static class MapaRepartidores {
        private HashMap<String, RepartidorBRIDGES> mapaRepartidores;
        
        // Constructor
        public MapaRepartidores() {
            this.mapaRepartidores = new HashMap<>();
        }
        
        // Insertar repartidor
        public boolean insertar(RepartidorBRIDGES repartidor) {
            if (mapaRepartidores.containsKey(repartidor.getCodigo().toUpperCase())) {
                System.out.println("Error: Ya existe un repartidor con código " + repartidor.getCodigo());
                return false;
            }
            
            mapaRepartidores.put(repartidor.getCodigo().toUpperCase(), repartidor);
            return true;
        }
        
        // BUSCAR REPARTIDOR POR CÓDIGO (USANDO HASHMAP - MÁS EFICIENTE)
        public RepartidorBRIDGES buscarPorCodigo(String codigo) {
            return mapaRepartidores.get(codigo.toUpperCase()); // ✅ O(1) - TIEMPO CONSTANTE
        }
        
        // Eliminar repartidor
        public boolean eliminar(String codigo) {
            if (mapaRepartidores.containsKey(codigo.toUpperCase())) {
                mapaRepartidores.remove(codigo.toUpperCase());
                return true;
            }
            return false;
        }
        
        // Mostrar todos los repartidores
        public void mostrarTodos() {
            System.out.println("\n=== LISTA DE REPARTIDORES (HASHMAP) ===");
            if (mapaRepartidores.isEmpty()) {
                System.out.println("No hay repartidores registrados.");
            } else {
                for (Map.Entry<String, RepartidorBRIDGES> entry : mapaRepartidores.entrySet()) {
                    RepartidorBRIDGES r = entry.getValue();
                    System.out.println("Código: " + r.getCodigo() + " | " + r.getNombre() + 
                                     " | Capacidad: " + r.getCapacidadCarga() + "kg" +
                                     " | Estado: " + (r.isDisponible() ? "Disponible" : "Ocupado"));
                }
            }
        }
        
        // Obtener todos los repartidores (para compatibilidad)
        public ArrayList<RepartidorBRIDGES> obtenerTodos() {
            return new ArrayList<>(mapaRepartidores.values());
        }
        
        // Obtener repartidores disponibles
        public ArrayList<RepartidorBRIDGES> obtenerDisponibles() {
            ArrayList<RepartidorBRIDGES> disponibles = new ArrayList<>();
            for (RepartidorBRIDGES r : mapaRepartidores.values()) {
                if (r.isDisponible()) {
                    disponibles.add(r);
                }
            }
            return disponibles;
        }
        
        // Estadísticas del HashMap
        public void mostrarEstadisticas() {
            System.out.println("\n=== ESTADÍSTICAS HASHMAP REPARTIDORES ===");
            System.out.println("Total repartidores: " + mapaRepartidores.size());
            System.out.println("Capacidad interna del HashMap: " + obtenerCapacidadInterna());
            System.out.println("Factor de carga: " + mapaRepartidores.size() + " / " + obtenerCapacidadInterna());
        }
        
        // Método para obtener capacidad interna (reflexión)
        private int obtenerCapacidadInterna() {
            try {
                // Usamos reflexión para obtener la capacidad real del HashMap
                java.lang.reflect.Field field = HashMap.class.getDeclaredField("table");
                field.setAccessible(true);
                Object[] table = (Object[]) field.get(mapaRepartidores);
                return table == null ? 0 : table.length;
            } catch (Exception e) {
                return mapaRepartidores.size(); // Fallback
            }
        }
        
        // Verificar si existe un repartidor
        public boolean existeRepartidor(String codigo) {
            return mapaRepartidores.containsKey(codigo.toUpperCase());
        }
        
        // Obtener cantidad de repartidores
        public int cantidadRepartidores() {
            return mapaRepartidores.size();
        }
    }

    // ================= MÉTODOS ESTÁTICOS (MANTENIDOS PARA COMPATIBILIDAD) =================

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