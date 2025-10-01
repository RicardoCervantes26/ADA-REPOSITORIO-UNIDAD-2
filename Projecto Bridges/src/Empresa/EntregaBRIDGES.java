package Empresa;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;

public class EntregaBRIDGES {
    private String codigoPedido;
    private String codigoRepartidor;
    private LocalDateTime fechaEntrega;
    private String estado;

    public EntregaBRIDGES() {}

    public EntregaBRIDGES(String codigoPedido, String codigoRepartidor, String estado) {
        this.codigoPedido = codigoPedido;
        this.codigoRepartidor = codigoRepartidor;
        this.fechaEntrega = LocalDateTime.now();
        this.estado = estado;
    }

    public String getCodigoPedido() { return codigoPedido; }
    public void setCodigoPedido(String codigoPedido) { this.codigoPedido = codigoPedido; }
    public String getCodigoRepartidor() { return codigoRepartidor; }
    public void setCodigoRepartidor(String codigoRepartidor) { this.codigoRepartidor = codigoRepartidor; }
    public LocalDateTime getFechaEntrega() { return fechaEntrega; }
    public void setFechaEntrega(LocalDateTime fechaEntrega) { this.fechaEntrega = fechaEntrega; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public void mostrarEntrega() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        System.out.println("Pedido: " + codigoPedido + " | Repartidor: " + codigoRepartidor +
                " | Estado: " + estado + " | Fecha: " + fechaEntrega.format(formatter));
    }

    // Gestor de entregas con archivo TXT
    public static class GestorEntregas {
        private static HashMap<String, EntregaBRIDGES> mapaEntregas = new HashMap<>();
        private static final String ARCHIVO = "entregas.txt";

        static {
            cargarDesdeArchivo();
        }

        // Registrar nueva entrega
        public static boolean registrarEntrega(EntregaBRIDGES entrega) {
            if (mapaEntregas.containsKey(entrega.getCodigoPedido())) {
                System.out.println("Error: Ya existe una entrega para ese pedido");
                return false;
            }
            mapaEntregas.put(entrega.getCodigoPedido(), entrega);
            guardarEnArchivo();
            return true;
        }

        // Buscar entrega por pedido
        public static EntregaBRIDGES buscarPorPedido(String codigoPedido) {
            return mapaEntregas.get(codigoPedido);
        }

        // Obtener todas las entregas
        public static ArrayList<EntregaBRIDGES> obtenerTodas() {
            return new ArrayList<>(mapaEntregas.values());
        }

        // Obtener entregas por estado
        public static ArrayList<EntregaBRIDGES> obtenerPorEstado(String estado) {
            ArrayList<EntregaBRIDGES> resultado = new ArrayList<>();
            for (EntregaBRIDGES e : mapaEntregas.values()) {
                if (e.getEstado().equals(estado)) {
                    resultado.add(e);
                }
            }
            return resultado;
        }

        // Actualizar estado de entrega
        public static boolean actualizarEstado(String codigoPedido, String nuevoEstado) {
            EntregaBRIDGES entrega = mapaEntregas.get(codigoPedido);
            if (entrega != null) {
                entrega.setEstado(nuevoEstado);
                guardarEnArchivo();
                return true;
            }
            return false;
        }

        // Guardar en archivo TXT
        private static void guardarEnArchivo() {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO))) {
                for (EntregaBRIDGES e : mapaEntregas.values()) {
                    writer.write(e.getCodigoPedido() + "|" + e.getCodigoRepartidor() + "|" +
                            e.getFechaEntrega() + "|" + e.getEstado());
                    writer.newLine();
                }
            } catch (IOException e) {
                System.out.println("Error guardando entregas: " + e.getMessage());
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
                        EntregaBRIDGES entrega = new EntregaBRIDGES();
                        entrega.setCodigoPedido(datos[0]);
                        entrega.setCodigoRepartidor(datos[1]);
                        entrega.setFechaEntrega(LocalDateTime.parse(datos[2]));
                        entrega.setEstado(datos[3]);
                        mapaEntregas.put(datos[0], entrega);
                    }
                }
            } catch (IOException e) {
                System.out.println("Error cargando entregas: " + e.getMessage());
            }
        }
    }
}