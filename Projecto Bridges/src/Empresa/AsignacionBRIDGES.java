package Empresa;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;
import java.util.Date;

public class AsignacionBRIDGES {
    private String codigoPedido;
    private String codigoRepartidor;
    private Date fechaAsignacion;
    private String estado;

    public AsignacionBRIDGES() {}

    public AsignacionBRIDGES(String codigoPedido, String codigoRepartidor) {
        this.codigoPedido = codigoPedido;
        this.codigoRepartidor = codigoRepartidor;
        this.fechaAsignacion = new Date();
        this.estado = "Asignado";
    }

    public String getCodigoPedido() { return codigoPedido; }
    public void setCodigoPedido(String codigoPedido) { this.codigoPedido = codigoPedido; }
    public String getCodigoRepartidor() { return codigoRepartidor; }
    public void setCodigoRepartidor(String codigoRepartidor) { this.codigoRepartidor = codigoRepartidor; }
    public Date getFechaAsignacion() { return fechaAsignacion; }
    public void setFechaAsignacion(Date fechaAsignacion) { this.fechaAsignacion = fechaAsignacion; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public void mostrarAsignacion() {
        System.out.println("Pedido: " + codigoPedido + " | Repartidor: " + codigoRepartidor +
                " | Estado: " + estado + " | Fecha: " + fechaAsignacion);
    }

    // Gestor de asignaciones con archivo TXT
    public static class GestorAsignaciones {
        private static HashMap<String, AsignacionBRIDGES> mapaAsignaciones = new HashMap<>();
        private static final String ARCHIVO = "asignaciones.txt";

        static {
            cargarDesdeArchivo();
        }

        // Crear nueva asignacion
        public static boolean crearAsignacion(AsignacionBRIDGES asignacion) {
            if (mapaAsignaciones.containsKey(asignacion.getCodigoPedido())) {
                System.out.println("Error: Ya existe una asignacion para ese pedido");
                return false;
            }
            mapaAsignaciones.put(asignacion.getCodigoPedido(), asignacion);
            guardarEnArchivo();
            return true;
        }

        // Buscar asignacion por pedido
        public static AsignacionBRIDGES buscarPorPedido(String codigoPedido) {
            return mapaAsignaciones.get(codigoPedido);
        }

        // Obtener todas las asignaciones
        public static ArrayList<AsignacionBRIDGES> obtenerTodas() {
            return new ArrayList<>(mapaAsignaciones.values());
        }

        // Actualizar estado de una asignacion ya sea entregado fallido etc
        public static boolean actualizarEstado(String codigoPedido, String nuevoEstado) {
            AsignacionBRIDGES asignacion = mapaAsignaciones.get(codigoPedido);
            if (asignacion != null) {
                asignacion.setEstado(nuevoEstado);
                guardarEnArchivo();
                return true;
            }
            return false;
        }

        // Obtener asignaciones por estado
        public static ArrayList<AsignacionBRIDGES> obtenerPorEstado(String estado) {
            ArrayList<AsignacionBRIDGES> resultado = new ArrayList<>();
            for (AsignacionBRIDGES a : mapaAsignaciones.values()) {
                if (a.getEstado().equals(estado)) {
                    resultado.add(a);
                }
            }
            return resultado;
        }

        // Guardar en archivo TXT
        private static void guardarEnArchivo() {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO))) {
                for (AsignacionBRIDGES a : mapaAsignaciones.values()) {
                    writer.write(a.getCodigoPedido() + "|" + a.getCodigoRepartidor() + "|" +
                            a.getFechaAsignacion().getTime() + "|" + a.getEstado());
                    writer.newLine();
                }
            } catch (IOException e) {
                System.out.println("Error guardando asignaciones: " + e.getMessage());
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
                        AsignacionBRIDGES asignacion = new AsignacionBRIDGES();
                        asignacion.setCodigoPedido(datos[0]);
                        asignacion.setCodigoRepartidor(datos[1]);
                        asignacion.setFechaAsignacion(new Date(Long.parseLong(datos[2])));
                        asignacion.setEstado(datos[3]);
                        mapaAsignaciones.put(datos[0], asignacion);
                    }
                }
            } catch (IOException e) {
                System.out.println("Error cargando asignaciones: " + e.getMessage());
            }
        }
    }

}
