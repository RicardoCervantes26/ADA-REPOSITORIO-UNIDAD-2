/*package Clases;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Date;

public class AsignacionBRIDGES {
    private String codigoAsignacion;
    private String codigoPedido;
    private String codigoRepartidor;
    private Date fechaAsignacion;
    private String estado; // Asignado, En camino, Entregado, Fallido

    public AsignacionBRIDGES() {}

    public AsignacionBRIDGES(String codigoAsignacion, String codigoPedido, String codigoRepartidor) {
        this.codigoAsignacion = codigoAsignacion;
        this.codigoPedido = codigoPedido;
        this.codigoRepartidor = codigoRepartidor;
        this.fechaAsignacion = new Date();
        this.estado = "Asignado";
    }

    // Getters y Setters
    public String getCodigoAsignacion() { return codigoAsignacion; }
    public String getCodigoPedido() { return codigoPedido; }
    public String getCodigoRepartidor() { return codigoRepartidor; }
    public Date getFechaAsignacion() { return fechaAsignacion; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    // Gestor de asignaciones
    public static class GestorAsignaciones {
        private static HashMap<String, AsignacionBRIDGES> mapaAsignaciones = new HashMap<>();
        private static final String ARCHIVO = "asignaciones_empresa.txt";

        static {
            cargarDesdeArchivo();
        }

        public static boolean crearAsignacion(AsignacionBRIDGES asignacion) {
            if (mapaAsignaciones.containsKey(asignacion.getCodigoAsignacion())) {
                System.out.println("Error: Ya existe una asignación con ese código");
                return false;
            }
            mapaAsignaciones.put(asignacion.getCodigoAsignacion(), asignacion);
            guardarEnArchivo();
            return true;
        }

        public static AsignacionBRIDGES buscarPorPedido(String codigoPedido) {
            for (AsignacionBRIDGES asignacion : mapaAsignaciones.values()) {
                if (asignacion.getCodigoPedido().equals(codigoPedido)) {
                    return asignacion;
                }
            }
            return null;
        }

        public static ArrayList<AsignacionBRIDGES> obtenerAsignacionesPorEstado(String estado) {
            ArrayList<AsignacionBRIDGES> resultado = new ArrayList<>();
            for (AsignacionBRIDGES asignacion : mapaAsignaciones.values()) {
                if (asignacion.getEstado().equals(estado)) {
                    resultado.add(asignacion);
                }
            }
            return resultado;
        }

        public static ArrayList<AsignacionBRIDGES> obtenerTodasAsignaciones() {
            return new ArrayList<>(mapaAsignaciones.values());
        }

        public static void ordenarPorFechaInsercion(ArrayList<AsignacionBRIDGES> lista) {
            for (int i = 1; i < lista.size(); i++) {
                AsignacionBRIDGES actual = lista.get(i);
                int j = i - 1;
                while (j >= 0 && lista.get(j).getFechaAsignacion().after(actual.getFechaAsignacion())) {
                    lista.set(j + 1, lista.get(j));
                    j--;
                }
                lista.set(j + 1, actual);
            }
        }

        private static void guardarEnArchivo() {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO))) {
                for (AsignacionBRIDGES asignacion : mapaAsignaciones.values()) {
                    writer.write(asignacion.getCodigoAsignacion() + "|" + asignacion.getCodigoPedido() + "|" + 
                               asignacion.getCodigoRepartidor() + "|" + asignacion.getFechaAsignacion().getTime() + "|" + 
                               asignacion.getEstado());
                    writer.newLine();
                }
            } catch (IOException e) {
                System.out.println("Error guardando asignaciones: " + e.getMessage());
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
                        AsignacionBRIDGES asignacion = new AsignacionBRIDGES(
                            datos[0], datos[1], datos[2]
                        );
                        asignacion.setFechaAsignacion(new Date(Long.parseLong(datos[3])));
                        asignacion.setEstado(datos[4]);
                        mapaAsignaciones.put(datos[0], asignacion);
                    }
                }
            } catch (IOException e) {
                System.out.println("Error cargando asignaciones: " + e.getMessage());
            }
        }
    }
}
*/