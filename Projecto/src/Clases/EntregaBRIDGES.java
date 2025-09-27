package Clases;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EntregaBRIDGES {
    private String codigoEntrega;
    private String codigoPedido;
    private LocalDateTime fechaEntrega;
    private String estado; // Pendiente, En camino, Entregado, Fallido
    private String observaciones;

    public EntregaBRIDGES() {}

    public EntregaBRIDGES(String codigoEntrega, String codigoPedido, String estado, String observaciones) {
        this.codigoEntrega = codigoEntrega;
        this.codigoPedido = codigoPedido;
        this.fechaEntrega = LocalDateTime.now();
        this.estado = estado;
        this.observaciones = observaciones;
    }

    // Getters y Setters
    public String getCodigoEntrega() { return codigoEntrega; }
    public String getCodigoPedido() { return codigoPedido; }
    public LocalDateTime getFechaEntrega() { return fechaEntrega; }
    public String getEstado() { return estado; }
    public String getObservaciones() { return observaciones; }
    public void setEstado(String estado) { this.estado = estado; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public void mostrarEntrega() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        System.out.println("Entrega: " + codigoEntrega + 
                         " | Pedido: " + codigoPedido + 
                         " | Estado: " + estado + 
                         " | Fecha: " + fechaEntrega.format(formatter) +
                         " | Obs: " + observaciones);
    }

    // Gestor de entregas
    public static class GestorEntregas {
        private static HashMap<String, EntregaBRIDGES> mapaEntregas = new HashMap<>();
        private static final String ARCHIVO = "entregas_empresa.txt";

        static {
            cargarDesdeArchivo();
        }

        public static boolean registrarEntrega(EntregaBRIDGES entrega) {
            if (mapaEntregas.containsKey(entrega.getCodigoEntrega())) {
                System.out.println("Error: Ya existe una entrega con ese código");
                return false;
            }
            mapaEntregas.put(entrega.getCodigoEntrega(), entrega);
            guardarEnArchivo();
            return true;
        }

        public static EntregaBRIDGES buscarPorPedido(String codigoPedido) {
            for (EntregaBRIDGES entrega : mapaEntregas.values()) {
                if (entrega.getCodigoPedido().equals(codigoPedido)) {
                    return entrega;
                }
            }
            return null;
        }

        public static ArrayList<EntregaBRIDGES> obtenerEntregasPorEstado(String estado) {
            ArrayList<EntregaBRIDGES> resultado = new ArrayList<>();
            for (EntregaBRIDGES entrega : mapaEntregas.values()) {
                if (entrega.getEstado().equals(estado)) {
                    resultado.add(entrega);
                }
            }
            return resultado;
        }

        public static ArrayList<EntregaBRIDGES> obtenerTodasEntregas() {
            return new ArrayList<>(mapaEntregas.values());
        }

        private static void guardarEnArchivo() {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO))) {
                for (EntregaBRIDGES entrega : mapaEntregas.values()) {
                    writer.write(entrega.getCodigoEntrega() + "|" + entrega.getCodigoPedido() + "|" + 
                               entrega.getFechaEntrega() + "|" + entrega.getEstado() + "|" + 
                               entrega.getObservaciones());
                    writer.newLine();
                }
            } catch (IOException e) {
                System.out.println("Error guardando entregas: " + e.getMessage());
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
                        EntregaBRIDGES entrega = new EntregaBRIDGES(
                            datos[0], datos[1], datos[3], datos[4]
                        );
                        entrega.fechaEntrega = LocalDateTime.parse(datos[2]);
                        mapaEntregas.put(datos[0], entrega);
                    }
                }
            } catch (IOException e) {
                System.out.println("Error cargando entregas: " + e.getMessage());
            }
        }
    }
}