package Empresa;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;

public class RepartidorBRIDGES {
    private String codigo;
    private String nombre;
    private double capacidadCarga;
    private boolean disponible;

    public RepartidorBRIDGES() {}

    public RepartidorBRIDGES(String codigo, String nombre, double capacidadCarga, boolean disponible) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.capacidadCarga = capacidadCarga;
        this.disponible = disponible;
    }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public double getCapacidadCarga() { return capacidadCarga; }
    public void setCapacidadCarga(double capacidadCarga) { this.capacidadCarga = capacidadCarga; }
    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }

    // Gestor con HashMap y archivo TXT
    public static class GestorRepartidores {
        private static HashMap<String, RepartidorBRIDGES> mapaRepartidores = new HashMap<>();
        private static final String ARCHIVO = "repartidores.txt";

        // Cargar datos al iniciar
        static {
            cargarDesdeArchivo();
        }

        // Registrar nuevo repartidor
        public static boolean registrarRepartidor(RepartidorBRIDGES repartidor) {
            if (mapaRepartidores.containsKey(repartidor.getCodigo())) {
                System.out.println("Error: Ya existe un repartidor con ese codigo");
                return false;
            }
            mapaRepartidores.put(repartidor.getCodigo(), repartidor);
            guardarEnArchivo();
            return true;
        }

        // Buscar repartidor por codigo
        public static RepartidorBRIDGES buscarPorCodigo(String codigo) {
            return mapaRepartidores.get(codigo);
        }

        // Obtener todos los repartidores
        public static ArrayList<RepartidorBRIDGES> obtenerTodos() {
            return new ArrayList<>(mapaRepartidores.values());
        }

        // Obtener repartidores disponibles
        public static ArrayList<RepartidorBRIDGES> obtenerDisponibles() {
            ArrayList<RepartidorBRIDGES> disponibles = new ArrayList<>();
            for (RepartidorBRIDGES r : mapaRepartidores.values()) {
                if (r.isDisponible()) {
                    disponibles.add(r);
                }
            }
            return disponibles;
        }

        // Mostrar todos los repartidores
        public static void mostrarTodos() {
            if (mapaRepartidores.isEmpty()) {
                System.out.println("No hay repartidores registrados.");
            } else {
                System.out.println("\n=== LISTA DE REPARTIDORES ===");
                for (RepartidorBRIDGES r : mapaRepartidores.values()) {
                    System.out.println("Codigo: " + r.getCodigo() + " | Nombre: " + r.getNombre() +
                            " | Capacidad: " + r.getCapacidadCarga() + "kg | Disponible: " +
                            (r.isDisponible() ? "Si" : "No"));
                }
            }
        }

        // Ordenar por capacidad usando insercion
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
        }

        // Guardar en archivo TXT
        private static void guardarEnArchivo() {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO))) {
                for (RepartidorBRIDGES r : mapaRepartidores.values()) {
                    writer.write(r.getCodigo() + "|" + r.getNombre() + "|" +
                            r.getCapacidadCarga() + "|" + r.isDisponible());
                    writer.newLine();
                }
            } catch (IOException e) {
                System.out.println("Error guardando repartidores: " + e.getMessage());
            }
        }

        // Cargar desde archivo TXT
        private static void cargarDesdeArchivo() {
            File archivo = new File(ARCHIVO);
            if (!archivo.exists()) return;

            try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    String[] datos = linea.split("\\|");
                    if (datos.length == 4) {
                        RepartidorBRIDGES repartidor = new RepartidorBRIDGES(
                                datos[0],
                                datos[1],
                                Double.parseDouble(datos[2]),
                                Boolean.parseBoolean(datos[3])
                        );
                        mapaRepartidores.put(datos[0], repartidor);
                    }
                }
            } catch (IOException e) {
                System.out.println("Error cargando repartidores: " + e.getMessage());
            }
        }
    }
}
