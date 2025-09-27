package Clases;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class RepartidorBRIDGES {
    private String codigo;
    private String nombre;
    private double capacidadCarga;
    private boolean disponible;
    private String vehiculo;

    public RepartidorBRIDGES() {}

    public RepartidorBRIDGES(String codigo, String nombre, double capacidadCarga, String vehiculo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.capacidadCarga = capacidadCarga;
        this.vehiculo = vehiculo;
        this.disponible = true;
    }

    // Getters y Setters
    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public double getCapacidadCarga() { return capacidadCarga; }
    public boolean isDisponible() { return disponible; }
    public String getVehiculo() { return vehiculo; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }

    // Gestor de repartidores
    public static class GestorRepartidores {
        private static HashMap<String, RepartidorBRIDGES> mapaRepartidores = new HashMap<>();
        private static final String ARCHIVO = "repartidores_empresa.txt";

        static {
            cargarDesdeArchivo();
        }

        public static boolean registrarRepartidor(RepartidorBRIDGES repartidor) {
            if (mapaRepartidores.containsKey(repartidor.getCodigo())) {
                System.out.println("Error: Ya existe un repartidor con ese código");
                return false;
            }
            mapaRepartidores.put(repartidor.getCodigo(), repartidor);
            guardarEnArchivo();
            return true;
        }

        public static RepartidorBRIDGES buscarPorCodigo(String codigo) {
            return mapaRepartidores.get(codigo);
        }

        public static ArrayList<RepartidorBRIDGES> obtenerRepartidoresDisponibles() {
            ArrayList<RepartidorBRIDGES> disponibles = new ArrayList<>();
            for (RepartidorBRIDGES repartidor : mapaRepartidores.values()) {
                if (repartidor.isDisponible()) {
                    disponibles.add(repartidor);
                }
            }
            return disponibles;
        }

        public static ArrayList<RepartidorBRIDGES> obtenerTodosRepartidores() {
            return new ArrayList<>(mapaRepartidores.values());
        }

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

        private static void guardarEnArchivo() {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO))) {
                for (RepartidorBRIDGES repartidor : mapaRepartidores.values()) {
                    writer.write(repartidor.getCodigo() + "|" + repartidor.getNombre() + "|" + 
                               repartidor.getCapacidadCarga() + "|" + repartidor.getVehiculo() + "|" + 
                               repartidor.isDisponible());
                    writer.newLine();
                }
            } catch (IOException e) {
                System.out.println("Error guardando repartidores: " + e.getMessage());
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
                        RepartidorBRIDGES repartidor = new RepartidorBRIDGES(
                            datos[0], datos[1], Double.parseDouble(datos[2]), datos[3]
                        );
                        repartidor.setDisponible(Boolean.parseBoolean(datos[4]));
                        mapaRepartidores.put(datos[0], repartidor);
                    }
                }
            } catch (IOException e) {
                System.out.println("Error cargando repartidores: " + e.getMessage());
            }
        }
    }
}