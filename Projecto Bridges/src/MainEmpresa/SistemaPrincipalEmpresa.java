package MainEmpresa;

import Empresa.RepartidorBRIDGES;
import Empresa.AsignacionBRIDGES;
import Empresa.EntregaBRIDGES;
import Clientes.PedidoClienteBRIDGES;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SistemaPrincipalEmpresa {
    private static JFrame frame;
    private static JPanel cardPanel;
    private static CardLayout cardLayout;

    public static void main(String[] args) {
        // Ejecutar en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            crearInterfaz();
        });
    }

    // Metodo para crear la interfaz principal
    private static void crearInterfaz() {
        frame = new JFrame("Sistema Empresa BRIDGES - Gestion de Logistica");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 600);
        frame.setLocationRelativeTo(null); // Centrar ventana

        // Configurar CardLayout para cambiar entre paneles
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Crear y agregar todos los paneles
        cardPanel.add(crearPanelPrincipal(), "PRINCIPAL");
        cardPanel.add(crearPanelRepartidores(), "REPARTIDORES");
        cardPanel.add(crearPanelAsignaciones(), "ASIGNACIONES");
        cardPanel.add(crearPanelEntregas(), "ENTREGAS");
        cardPanel.add(crearPanelReportes(), "REPORTES");

        frame.add(cardPanel);
        // Mostrar panel principal al iniciar
        cardLayout.show(cardPanel, "PRINCIPAL");
        frame.setVisible(true);
    }

    // Panel principal del sistema empresa
    private static JPanel crearPanelPrincipal() {
        JPanel panel = new JPanel(new BorderLayout());

        // Titulo principal
        JLabel titulo = new JLabel("SISTEMA EMPRESA BRIDGES - GESTION DE LOGISTICA", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        panel.add(titulo, BorderLayout.NORTH);

        // Panel de botones principales
        JPanel panelBotones = new JPanel(new GridLayout(5, 1, 15, 15));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));

        JButton btnRepartidores = new JButton("Gestionar Repartidores");
        JButton btnAsignaciones = new JButton("Gestionar Asignaciones");
        JButton btnEntregas = new JButton("Gestionar Entregas");
        JButton btnReportes = new JButton("Ver Reportes");
        JButton btnSalir = new JButton("Salir del Sistema");

        // Configurar tamanio de botones
        Dimension buttonSize = new Dimension(200, 50);
        btnRepartidores.setPreferredSize(buttonSize);
        btnAsignaciones.setPreferredSize(buttonSize);
        btnEntregas.setPreferredSize(buttonSize);
        btnReportes.setPreferredSize(buttonSize);
        btnSalir.setPreferredSize(buttonSize);

        panelBotones.add(btnRepartidores);
        panelBotones.add(btnAsignaciones);
        panelBotones.add(btnEntregas);
        panelBotones.add(btnReportes);
        panelBotones.add(btnSalir);

        panel.add(panelBotones, BorderLayout.CENTER);

        // Acciones de los botones
        btnRepartidores.addActionListener(e -> {
            cardLayout.show(cardPanel, "REPARTIDORES");
        });

        btnAsignaciones.addActionListener(e -> {
            cardLayout.show(cardPanel, "ASIGNACIONES");
        });

        btnEntregas.addActionListener(e -> {
            cardLayout.show(cardPanel, "ENTREGAS");
        });

        btnReportes.addActionListener(e -> {
            cardLayout.show(cardPanel, "REPORTES");
        });

        btnSalir.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(frame,
                    "¿Está seguro que desea salir del sistema?",
                    "Confirmar Salida",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        return panel;
    }

    // Panel para gestionar repartidores
    private static JPanel crearPanelRepartidores() {
        JPanel panel = new JPanel(new BorderLayout());

        // Titulo
        JLabel titulo = new JLabel("GESTION DE REPARTIDORES", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panel.add(titulo, BorderLayout.NORTH);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        JButton btnRegistrar = new JButton("Registrar Repartidor");
        JButton btnMostrar = new JButton("Mostrar Todos");
        JButton btnBuscar = new JButton("Buscar por Codigo");
        JButton btnOrdenar = new JButton("Ordenar por Capacidad");
        JButton btnVolver = new JButton("Volver al Menu");

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnMostrar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnOrdenar);
        panelBotones.add(btnVolver);

        panel.add(panelBotones, BorderLayout.NORTH);

        // Area de texto para mostrar resultados
        JTextArea txtResultados = new JTextArea(20, 50);
        txtResultados.setEditable(false);
        JScrollPane scrollResultados = new JScrollPane(txtResultados);
        panel.add(scrollResultados, BorderLayout.CENTER);

        // Accion del boton Registrar Repartidor
        btnRegistrar.addActionListener(e -> {
            // Panel para ingresar datos del repartidor
            JPanel panelRegistro = new JPanel(new GridLayout(4, 2, 10, 10));

            JTextField txtCodigo = new JTextField();
            JTextField txtNombre = new JTextField();
            JTextField txtCapacidad = new JTextField();
            JCheckBox chkDisponible = new JCheckBox("Disponible", true);

            panelRegistro.add(new JLabel("Codigo:"));
            panelRegistro.add(txtCodigo);
            panelRegistro.add(new JLabel("Nombre:"));
            panelRegistro.add(txtNombre);
            panelRegistro.add(new JLabel("Capacidad (kg):"));
            panelRegistro.add(txtCapacidad);
            panelRegistro.add(new JLabel(""));
            panelRegistro.add(chkDisponible);

            int result = JOptionPane.showConfirmDialog(frame, panelRegistro,
                    "Registrar Nuevo Repartidor", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                try {
                    String codigo = txtCodigo.getText();
                    String nombre = txtNombre.getText();
                    double capacidad = Double.parseDouble(txtCapacidad.getText());
                    boolean disponible = chkDisponible.isSelected();

                    if (codigo.isEmpty() || nombre.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Debe completar todos los campos");
                        return;
                    }

                    RepartidorBRIDGES nuevo = new RepartidorBRIDGES(codigo, nombre, capacidad, disponible);
                    if (RepartidorBRIDGES.GestorRepartidores.registrarRepartidor(nuevo)) {
                        txtResultados.setText("Repartidor registrado exitosamente:\n");
                        txtResultados.append("Codigo: " + codigo + "\n");
                        txtResultados.append("Nombre: " + nombre + "\n");
                        txtResultados.append("Capacidad: " + capacidad + "kg\n");
                        txtResultados.append("Disponible: " + (disponible ? "Si" : "No"));
                    } else {
                        JOptionPane.showMessageDialog(frame, "Error: Ya existe un repartidor con ese codigo");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Error: La capacidad debe ser un numero valido");
                }
            }
        });

        // Accion del boton Mostrar Todos
        btnMostrar.addActionListener(e -> {
            ArrayList<RepartidorBRIDGES> repartidores = RepartidorBRIDGES.GestorRepartidores.obtenerTodos();
            if (repartidores.isEmpty()) {
                txtResultados.setText("No hay repartidores registrados.");
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("LISTA DE REPARTIDORES\n");
                sb.append("=====================\n\n");
                for (RepartidorBRIDGES r : repartidores) {
                    sb.append("Codigo: ").append(r.getCodigo()).append("\n");
                    sb.append("Nombre: ").append(r.getNombre()).append("\n");
                    sb.append("Capacidad: ").append(r.getCapacidadCarga()).append("kg\n");
                    sb.append("Disponible: ").append(r.isDisponible() ? "Si" : "No").append("\n");
                    sb.append("---\n");
                }
                txtResultados.setText(sb.toString());
            }
        });

        // Accion del boton Buscar por Codigo
        btnBuscar.addActionListener(e -> {
            String codigo = JOptionPane.showInputDialog(frame, "Ingrese codigo del repartidor:");
            if (codigo != null && !codigo.isEmpty()) {
                RepartidorBRIDGES repartidor = RepartidorBRIDGES.GestorRepartidores.buscarPorCodigo(codigo);
                if (repartidor != null) {
                    txtResultados.setText("REPARTIDOR ENCONTRADO:\n\n");
                    txtResultados.append("Codigo: " + repartidor.getCodigo() + "\n");
                    txtResultados.append("Nombre: " + repartidor.getNombre() + "\n");
                    txtResultados.append("Capacidad: " + repartidor.getCapacidadCarga() + "kg\n");
                    txtResultados.append("Disponible: " + (repartidor.isDisponible() ? "Si" : "No"));
                } else {
                    JOptionPane.showMessageDialog(frame, "Repartidor no encontrado");
                }
            }
        });

        // Accion del boton Ordenar por Capacidad
        btnOrdenar.addActionListener(e -> {
            ArrayList<RepartidorBRIDGES> todos = RepartidorBRIDGES.GestorRepartidores.obtenerTodos();
            if (todos.isEmpty()) {
                txtResultados.setText("No hay repartidores para ordenar.");
                return;
            }

            RepartidorBRIDGES.GestorRepartidores.ordenarPorCapacidadInsercion(todos);
            StringBuilder sb = new StringBuilder();
            sb.append("REPARTIDORES ORDENADOS POR CAPACIDAD (DESCENDENTE)\n");
            sb.append("==================================================\n\n");
            for (RepartidorBRIDGES r : todos) {
                sb.append("Codigo: ").append(r.getCodigo()).append(" | ");
                sb.append("Nombre: ").append(r.getNombre()).append(" | ");
                sb.append("Capacidad: ").append(r.getCapacidadCarga()).append("kg\n");
            }
            txtResultados.setText(sb.toString());
        });

        // Accion del boton Volver
        btnVolver.addActionListener(e -> {
            cardLayout.show(cardPanel, "PRINCIPAL");
        });

        return panel;
    }

    // Panel para gestionar asignaciones
    private static JPanel crearPanelAsignaciones() {
        JPanel panel = new JPanel(new BorderLayout());

        // Titulo
        JLabel titulo = new JLabel("GESTION DE ASIGNACIONES", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panel.add(titulo, BorderLayout.NORTH);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        JButton btnAsignar = new JButton("Asignar Pedido");
        JButton btnMostrar = new JButton("Mostrar Todas");
        JButton btnBuscar = new JButton("Buscar por Pedido");
        JButton btnActualizar = new JButton("Actualizar Estado");
        JButton btnVolver = new JButton("Volver al Menu");

        panelBotones.add(btnAsignar);
        panelBotones.add(btnMostrar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnVolver);

        panel.add(panelBotones, BorderLayout.NORTH);

        // Area de texto para mostrar resultados
        JTextArea txtResultados = new JTextArea(20, 50);
        txtResultados.setEditable(false);
        JScrollPane scrollResultados = new JScrollPane(txtResultados);
        panel.add(scrollResultados, BorderLayout.CENTER);

        // Accion del boton Asignar Pedido
        btnAsignar.addActionListener(e -> {
            // Obtener pedidos pendientes
            ArrayList<PedidoClienteBRIDGES> pedidosPendientes = PedidoClienteBRIDGES.GestorPedidos.cargarPedidos();
            if (pedidosPendientes.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No hay pedidos pendientes de asignacion.");
                return;
            }

            // Obtener repartidores disponibles
            ArrayList<RepartidorBRIDGES> repartidoresDisponibles = RepartidorBRIDGES.GestorRepartidores.obtenerDisponibles();
            if (repartidoresDisponibles.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No hay repartidores disponibles.");
                return;
            }

            // Crear arrays para los combobox
            String[] pedidosArray = new String[pedidosPendientes.size()];
            String[] repartidoresArray = new String[repartidoresDisponibles.size()];

            for (int i = 0; i < pedidosPendientes.size(); i++) {
                PedidoClienteBRIDGES p = pedidosPendientes.get(i);
                pedidosArray[i] = p.getCodigo() + " - " + p.getDescripcion() + " (" + p.getPeso() + "kg)";
            }

            for (int i = 0; i < repartidoresDisponibles.size(); i++) {
                RepartidorBRIDGES r = repartidoresDisponibles.get(i);
                repartidoresArray[i] = r.getCodigo() + " - " + r.getNombre() + " (" + r.getCapacidadCarga() + "kg)";
            }

            // Panel de seleccion
            JPanel panelSeleccion = new JPanel(new GridLayout(2, 2, 10, 10));
            JComboBox<String> cmbPedidos = new JComboBox<>(pedidosArray);
            JComboBox<String> cmbRepartidores = new JComboBox<>(repartidoresArray);

            panelSeleccion.add(new JLabel("Seleccione Pedido:"));
            panelSeleccion.add(cmbPedidos);
            panelSeleccion.add(new JLabel("Seleccione Repartidor:"));
            panelSeleccion.add(cmbRepartidores);

            int result = JOptionPane.showConfirmDialog(frame, panelSeleccion,
                    "Asignar Pedido a Repartidor", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                int idxPedido = cmbPedidos.getSelectedIndex();
                int idxRepartidor = cmbRepartidores.getSelectedIndex();

                PedidoClienteBRIDGES pedidoSeleccionado = pedidosPendientes.get(idxPedido);
                RepartidorBRIDGES repartidorSeleccionado = repartidoresDisponibles.get(idxRepartidor);

                // Verificar capacidad
                if (pedidoSeleccionado.getPeso() > repartidorSeleccionado.getCapacidadCarga()) {
                    JOptionPane.showMessageDialog(frame,
                            "Error: El peso del pedido (" + pedidoSeleccionado.getPeso() + "kg) " +
                                    "excede la capacidad del repartidor (" + repartidorSeleccionado.getCapacidadCarga() + "kg)");
                    return;
                }

                // Crear asignacion
                AsignacionBRIDGES nuevaAsignacion = new AsignacionBRIDGES(
                        pedidoSeleccionado.getCodigo(),
                        repartidorSeleccionado.getCodigo()
                );

                if (AsignacionBRIDGES.GestorAsignaciones.crearAsignacion(nuevaAsignacion)) {
                    // Marcar repartidor como no disponible
                    repartidorSeleccionado.setDisponible(false);
                    // Actualizar estado del pedido
                    PedidoClienteBRIDGES.GestorPedidos.actualizarEstado(pedidoSeleccionado.getCodigo(), "Aceptado");

                    txtResultados.setText("ASIGNACION CREADA EXITOSAMENTE\n\n");
                    txtResultados.append("Pedido: " + pedidoSeleccionado.getCodigo() + "\n");
                    txtResultados.append("Descripcion: " + pedidoSeleccionado.getDescripcion() + "\n");
                    txtResultados.append("Repartidor: " + repartidorSeleccionado.getNombre() + "\n");
                    txtResultados.append("Estado: Asignado\n");
                    txtResultados.append("Fecha: " + nuevaAsignacion.getFechaAsignacion());
                }
            }
        });

        // Accion del boton Mostrar Todas
        btnMostrar.addActionListener(e -> {
            ArrayList<AsignacionBRIDGES> asignaciones = AsignacionBRIDGES.GestorAsignaciones.obtenerTodas();
            if (asignaciones.isEmpty()) {
                txtResultados.setText("No hay asignaciones registradas.");
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("TODAS LAS ASIGNACIONES\n");
                sb.append("======================\n\n");
                for (AsignacionBRIDGES a : asignaciones) {
                    sb.append("Pedido: ").append(a.getCodigoPedido()).append("\n");
                    sb.append("Repartidor: ").append(a.getCodigoRepartidor()).append("\n");
                    sb.append("Estado: ").append(a.getEstado()).append("\n");
                    sb.append("Fecha: ").append(a.getFechaAsignacion()).append("\n");
                    sb.append("---\n");
                }
                txtResultados.setText(sb.toString());
            }
        });

        // Accion del boton Buscar por Pedido
        btnBuscar.addActionListener(e -> {
            String codigoPedido = JOptionPane.showInputDialog(frame, "Ingrese codigo del pedido:");
            if (codigoPedido != null && !codigoPedido.isEmpty()) {
                AsignacionBRIDGES asignacion = AsignacionBRIDGES.GestorAsignaciones.buscarPorPedido(codigoPedido);
                if (asignacion != null) {
                    txtResultados.setText("ASIGNACION ENCONTRADA:\n\n");
                    txtResultados.append("Pedido: " + asignacion.getCodigoPedido() + "\n");
                    txtResultados.append("Repartidor: " + asignacion.getCodigoRepartidor() + "\n");
                    txtResultados.append("Estado: " + asignacion.getEstado() + "\n");
                    txtResultados.append("Fecha: " + asignacion.getFechaAsignacion());
                } else {
                    JOptionPane.showMessageDialog(frame, "No existe asignacion para ese pedido.");
                }
            }
        });

        // Accion del boton Actualizar Estado
        btnActualizar.addActionListener(e -> {
            String codigoPedido = JOptionPane.showInputDialog(frame, "Codigo del pedido:");
            if (codigoPedido == null || codigoPedido.isEmpty()) return;

            AsignacionBRIDGES asignacion = AsignacionBRIDGES.GestorAsignaciones.buscarPorPedido(codigoPedido);
            if (asignacion == null) {
                JOptionPane.showMessageDialog(frame, "No existe asignacion para ese pedido.");
                return;
            }

            String[] estados = {"Asignado", "En camino", "Entregado", "Fallido"};
            String nuevoEstado = (String) JOptionPane.showInputDialog(frame,
                    "Estado actual: " + asignacion.getEstado() + "\nSeleccione nuevo estado:",
                    "Actualizar Estado",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    estados,
                    estados[0]);

            if (nuevoEstado != null) {
                if (AsignacionBRIDGES.GestorAsignaciones.actualizarEstado(codigoPedido, nuevoEstado)) {
                    txtResultados.setText("ESTADO ACTUALIZADO EXITOSAMENTE\n\n");
                    txtResultados.append("Pedido: " + codigoPedido + "\n");
                    txtResultados.append("Nuevo estado: " + nuevoEstado + "\n");

                    // Si el estado es final, crear entrega y liberar repartidor
                    if (nuevoEstado.equals("Entregado") || nuevoEstado.equals("Fallido")) {
                        RepartidorBRIDGES repartidor = RepartidorBRIDGES.GestorRepartidores.buscarPorCodigo(asignacion.getCodigoRepartidor());
                        if (repartidor != null) {
                            repartidor.setDisponible(true);
                            txtResultados.append("Repartidor liberado: " + repartidor.getNombre() + "\n");

                            // Crear registro de entrega
                            EntregaBRIDGES nuevaEntrega = new EntregaBRIDGES(
                                    asignacion.getCodigoPedido(),
                                    asignacion.getCodigoRepartidor(),
                                    nuevoEstado
                            );
                            EntregaBRIDGES.GestorEntregas.registrarEntrega(nuevaEntrega);
                            // Actualizar estado del pedido
                            PedidoClienteBRIDGES.GestorPedidos.actualizarEstado(codigoPedido, nuevoEstado);
                            txtResultados.append("Registro de entrega creado.");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Error al actualizar estado.");
                }
            }
        });

        // Accion del boton Volver
        btnVolver.addActionListener(e -> {
            cardLayout.show(cardPanel, "PRINCIPAL");
        });

        return panel;
    }

    // Panel para gestionar entregas
    private static JPanel crearPanelEntregas() {
        JPanel panel = new JPanel(new BorderLayout());

        // Titulo
        JLabel titulo = new JLabel("GESTION DE ENTREGAS", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panel.add(titulo, BorderLayout.NORTH);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        JButton btnMostrar = new JButton("Mostrar Todas");
        JButton btnBuscar = new JButton("Buscar por Pedido");
        JButton btnFiltrar = new JButton("Filtrar por Estado");
        JButton btnVolver = new JButton("Volver al Menu");

        panelBotones.add(btnMostrar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnFiltrar);
        panelBotones.add(btnVolver);

        panel.add(panelBotones, BorderLayout.NORTH);

        // Area de texto para mostrar resultados
        JTextArea txtResultados = new JTextArea(20, 50);
        txtResultados.setEditable(false);
        JScrollPane scrollResultados = new JScrollPane(txtResultados);
        panel.add(scrollResultados, BorderLayout.CENTER);

        // Accion del boton Mostrar Todas
        btnMostrar.addActionListener(e -> {
            ArrayList<EntregaBRIDGES> entregas = EntregaBRIDGES.GestorEntregas.obtenerTodas();
            if (entregas.isEmpty()) {
                txtResultados.setText("No hay entregas registradas.");
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("HISTORIAL DE ENTREGAS\n");
                sb.append("=====================\n\n");
                for (EntregaBRIDGES entrega : entregas) {
                    sb.append("Pedido: ").append(entrega.getCodigoPedido()).append("\n");
                    sb.append("Repartidor: ").append(entrega.getCodigoRepartidor()).append("\n");
                    sb.append("Estado: ").append(entrega.getEstado()).append("\n");
                    sb.append("Fecha: ").append(entrega.getFechaEntrega()).append("\n");
                    sb.append("---\n");
                }
                txtResultados.setText(sb.toString());
            }
        });

        // Accion del boton Buscar por Pedido
        btnBuscar.addActionListener(e -> {
            String codigoPedido = JOptionPane.showInputDialog(frame, "Ingrese codigo del pedido:");
            if (codigoPedido != null && !codigoPedido.isEmpty()) {
                EntregaBRIDGES entrega = EntregaBRIDGES.GestorEntregas.buscarPorPedido(codigoPedido);
                if (entrega != null) {
                    txtResultados.setText("ENTREGA ENCONTRADA:\n\n");
                    txtResultados.append("Pedido: " + entrega.getCodigoPedido() + "\n");
                    txtResultados.append("Repartidor: " + entrega.getCodigoRepartidor() + "\n");
                    txtResultados.append("Estado: " + entrega.getEstado() + "\n");
                    txtResultados.append("Fecha: " + entrega.getFechaEntrega());
                } else {
                    JOptionPane.showMessageDialog(frame, "No existe entrega para ese pedido.");
                }
            }
        });

        // Accion del boton Filtrar por Estado
        btnFiltrar.addActionListener(e -> {
            String[] estados = {"Entregado", "Fallido"};
            String estado = (String) JOptionPane.showInputDialog(frame,
                    "Seleccione estado a filtrar:",
                    "Filtrar Entregas",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    estados,
                    estados[0]);

            if (estado != null) {
                ArrayList<EntregaBRIDGES> entregas = EntregaBRIDGES.GestorEntregas.obtenerPorEstado(estado);
                if (entregas.isEmpty()) {
                    txtResultados.setText("No hay entregas con estado: " + estado);
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append("ENTREGAS ").append(estado.toUpperCase()).append("\n");
                    sb.append("================\n\n");
                    for (EntregaBRIDGES entrega : entregas) {
                        sb.append("Pedido: ").append(entrega.getCodigoPedido()).append("\n");
                        sb.append("Repartidor: ").append(entrega.getCodigoRepartidor()).append("\n");
                        sb.append("Fecha: ").append(entrega.getFechaEntrega()).append("\n");
                        sb.append("---\n");
                    }
                    txtResultados.setText(sb.toString());
                }
            }
        });

        // Accion del boton Volver
        btnVolver.addActionListener(e -> {
            cardLayout.show(cardPanel, "PRINCIPAL");
        });

        return panel;
    }

    // Panel para mostrar reportes del sistema
    private static JPanel crearPanelReportes() {
        JPanel panel = new JPanel(new BorderLayout());

        // Titulo
        JLabel titulo = new JLabel("REPORTES DEL SISTEMA", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panel.add(titulo, BorderLayout.NORTH);

        // Area de texto para mostrar reportes
        JTextArea txtReportes = new JTextArea(20, 50);
        txtReportes.setEditable(false);
        JScrollPane scrollReportes = new JScrollPane(txtReportes);
        panel.add(scrollReportes, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        JButton btnGenerar = new JButton("Generar Reporte");
        JButton btnVolver = new JButton("Volver al Menu");

        panelBotones.add(btnGenerar);
        panelBotones.add(btnVolver);
        panel.add(panelBotones, BorderLayout.SOUTH);

        // Accion del boton Generar Reporte
        btnGenerar.addActionListener(e -> {
            StringBuilder reporte = new StringBuilder();
            reporte.append("REPORTE DEL SISTEMA BRIDGES\n");
            reporte.append("===========================\n\n");

            // Reporte de repartidores
            ArrayList<RepartidorBRIDGES> repartidores = RepartidorBRIDGES.GestorRepartidores.obtenerTodos();
            reporte.append("REPARTIDORES:\n");
            reporte.append("- Total repartidores: ").append(repartidores.size()).append("\n");
            reporte.append("- Repartidores disponibles: ").append(RepartidorBRIDGES.GestorRepartidores.obtenerDisponibles().size()).append("\n\n");

            // Reporte de asignaciones
            ArrayList<AsignacionBRIDGES> asignaciones = AsignacionBRIDGES.GestorAsignaciones.obtenerTodas();
            reporte.append("ASIGNACIONES:\n");
            reporte.append("- Total asignaciones: ").append(asignaciones.size()).append("\n");

            // Asignaciones por estado
            reporte.append("- Asignaciones por estado:\n");
            reporte.append("  * Asignado: ").append(AsignacionBRIDGES.GestorAsignaciones.obtenerPorEstado("Asignado").size()).append("\n");
            reporte.append("  * En camino: ").append(AsignacionBRIDGES.GestorAsignaciones.obtenerPorEstado("En camino").size()).append("\n");
            reporte.append("  * Entregado: ").append(AsignacionBRIDGES.GestorAsignaciones.obtenerPorEstado("Entregado").size()).append("\n");
            reporte.append("  * Fallido: ").append(AsignacionBRIDGES.GestorAsignaciones.obtenerPorEstado("Fallido").size()).append("\n\n");

            // Reporte de entregas
            ArrayList<EntregaBRIDGES> entregas = EntregaBRIDGES.GestorEntregas.obtenerTodas();
            reporte.append("ENTREGAS:\n");
            reporte.append("- Total entregas registradas: ").append(entregas.size()).append("\n\n");

            // Reporte de pedidos
            ArrayList<PedidoClienteBRIDGES> pedidos = PedidoClienteBRIDGES.GestorPedidos.cargarPedidos();
            reporte.append("PEDIDOS DE CLIENTES:\n");
            reporte.append("- Total pedidos: ").append(pedidos.size()).append("\n");

            txtReportes.setText(reporte.toString());
        });

        // Accion del boton Volver
        btnVolver.addActionListener(e -> {
            cardLayout.show(cardPanel, "PRINCIPAL");
        });

        return panel;
    }
}