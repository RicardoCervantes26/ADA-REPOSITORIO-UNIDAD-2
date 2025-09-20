
package Clases;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class EntregaBRIDGES {
    private PedidoBRIDGES pedido;
    private RepartidorBRIDGES repartidor;
    private LocalDateTime fechaEntrega;
    private String estado; // Pendiente, En camino, Entregado, Fallido

    // =================== Constructor ===================
    public EntregaBRIDGES(PedidoBRIDGES pedido, RepartidorBRIDGES repartidor, String estado) {
        this.pedido = pedido;
        this.repartidor = repartidor;
        this.fechaEntrega = LocalDateTime.now(); // se guarda el momento en que se registra la entrega
        this.estado = estado;
    }

    // =================== Getters ===================
    public PedidoBRIDGES getPedido() {
        return pedido;
    }

    public RepartidorBRIDGES getRepartidor() {
        return repartidor;
    }

    public LocalDateTime getFechaEntrega() {
        return fechaEntrega;
    }

    public String getEstado() {
        return estado;
    }

    // =================== Setters ===================
    public void setEstado(String estado) {
        this.estado = estado;
    }

    // =================== Métodos ===================
    public void mostrarEntrega() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        System.out.println("Pedido: " + pedido.getCodigo() +
                           " | Repartidor: " + repartidor.getNombre() +
                           " | Estado: " + estado +
                           " | Fecha: " + fechaEntrega.format(formatter));
    }
}
