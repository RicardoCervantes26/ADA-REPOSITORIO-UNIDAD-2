package Clases;

import java.util.Date;

public class AsignacionBRIDGES {
    private PedidoBRIDGES pedido;
    private RepartidorBRIDGES repartidor;
    private Date fechaAsignacion;
    private String estado; // Asignado, En camino, Entregado, Fallido

    // Constructor
    public AsignacionBRIDGES(PedidoBRIDGES pedido, RepartidorBRIDGES repartidor) {
        this.pedido = pedido;
        this.repartidor = repartidor;
        this.fechaAsignacion = new Date();
        this.estado = "Asignado";
    }

    // Getters y Setters
    public PedidoBRIDGES getPedido() {
        return pedido;
    }

    public void setPedido(PedidoBRIDGES pedido) {
        this.pedido = pedido;
    }

    public RepartidorBRIDGES getRepartidor() {
        return repartidor;
    }

    public void setRepartidor(RepartidorBRIDGES repartidor) {
        this.repartidor = repartidor;
    }

    public Date getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(Date fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    // Método para mostrar la asignación
    public void mostrarAsignacion() {
        System.out.println("Pedido " + pedido.getCodigo() +
                " asignado a " + repartidor.getNombre() +
                " | Estado: " + estado +
                " | Fecha: " + fechaAsignacion);
    }
}
