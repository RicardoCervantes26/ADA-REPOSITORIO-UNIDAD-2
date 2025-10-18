package MainClientes;

import Clientes.ClienteBRIDGES;
import Clientes.PedidoClienteBRIDGES;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SistemaPrincipalClientes {
    private static ClienteBRIDGES clienteLogueado = null;
    private static JFrame frame;
    private static JPanel cardPanel;
    private static CardLayout cardLayout;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            crearInterfaz();
        });
    }

    // Crear interfaz principal con CardLayout
    private static void crearInterfaz() {
        frame = new JFrame("Sistema Clientes BRIDGES - CON HASH ANTICOLISIONES");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Agregar todos los paneles
        cardPanel.add(crearPanelLogin(), "LOGIN");
        cardPanel.add(crearPanelRegistro(), "REGISTRO");
        cardPanel.add(crearPanelPrincipal(), "PRINCIPAL");
        cardPanel.add(crearPanelPedidos(), "PEDIDOS");
        cardPanel.add(crearPanelCambiarPassword(), "CAMBIAR_PASSWORD");

        frame.add(cardPanel);
        cardLayout.show(cardPanel, "LOGIN");
        frame.setVisible(true);
    }

    // Panel de inicio de sesión
    private static JPanel crearPanelLogin() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titulo = new JLabel("INICIO DE SESION CON HASH ANTICOLISIONES", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(titulo, gbc);

        // Campo usuario
        gbc.gridwidth = 1;
        gbc.gridy = 1; gbc.gridx = 0;
        panel.add(new JLabel("Usuario:"), gbc);
        JTextField txtUsuario = new JTextField(15);
        gbc.gridx = 1;
        panel.add(txtUsuario, gbc);

        // Campo contraseña
        gbc.gridy = 2; gbc.gridx = 0;
        panel.add(new JLabel("Contraseña:"), gbc);
        JPasswordField txtPassword = new JPasswordField(15);
        gbc.gridx = 1;
        panel.add(txtPassword, gbc);

        // Botones
        JButton btnLogin = new JButton("Login");
        JButton btnRegistrar = new JButton("Registrarse");
        JButton btnProbarHash = new JButton("Probar Hash");
        JButton btnEstadisticas = new JButton("Estadísticas");

        gbc.gridy = 3; gbc.gridx = 0;
        panel.add(btnLogin, gbc);
        gbc.gridx = 1;
        panel.add(btnRegistrar, gbc);

        gbc.gridy = 4; gbc.gridx = 0; gbc.gridwidth = 1;
        panel.add(btnProbarHash, gbc);
        gbc.gridx = 1;
        panel.add(btnEstadisticas, gbc);

        // Área de información
        JTextArea txtInfo = new JTextArea(8, 30);
        txtInfo.setEditable(false);
        JScrollPane scrollInfo = new JScrollPane(txtInfo);
        gbc.gridy = 5; gbc.gridwidth = 2;
        panel.add(scrollInfo, gbc);

        // Acción LOGIN
        btnLogin.addActionListener(e -> {
            String usuario = txtUsuario.getText();
            String contraseña = new String(txtPassword.getPassword());

            if (usuario.isEmpty() || contraseña.isEmpty()) {
                txtInfo.setText("Error: Debe completar todos los campos");
                return;
            }

            txtInfo.setText("PROCESO DE LOGIN:\n");
            clienteLogueado = ClienteBRIDGES.GestorClientes.login(usuario, contraseña);

            if (clienteLogueado != null) {
                String hashBase = ClienteBRIDGES.HashUtil.generarHash(contraseña);
                txtInfo.append("Hash base: " + hashBase + "\n");
                txtInfo.append("Hash guardado: " + clienteLogueado.getHashContraseña() + "\n");

                if (hashBase.equals(clienteLogueado.getHashContraseña())) {
                    txtInfo.append("Login exitoso - Hash base coincide\n");
                } else {
                    txtInfo.append("Login exitoso - Hash único asignado\n");
                }

                cardLayout.show(cardPanel, "PRINCIPAL");
            } else {
                txtInfo.append("Login fallido - Usuario o contraseña incorrectos\n");
            }
        });

        // Acción REGISTRAR
        btnRegistrar.addActionListener(e -> {
            cardLayout.show(cardPanel, "REGISTRO");
        });

        // Acción PROBAR HASH
        btnProbarHash.addActionListener(e -> {
            String contraseña = new String(txtPassword.getPassword());
            if (!contraseña.isEmpty()) {
                String hashBase = ClienteBRIDGES.HashUtil.generarHash(contraseña);
                String hashUnico = ClienteBRIDGES.HashUtil.generarHashUnico(contraseña,
                        ClienteBRIDGES.GestorClientes.getMapaClientes());

                txtInfo.setText("DEMOSTRACION HASH:\n");
                txtInfo.append("Contraseña: '" + contraseña + "'\n");
                txtInfo.append("Hash base: " + hashBase + "\n");
                txtInfo.append("Hash único: " + hashUnico + "\n\n");

                if (hashBase.equals(hashUnico)) {
                    txtInfo.append("No hay colisión - Se usa hash base\n");
                } else {
                    txtInfo.append("Colisión detectada - Se asigna hash único\n");
                }
            }
        });

        // Acción ESTADÍSTICAS
        btnEstadisticas.addActionListener(e -> {
            ClienteBRIDGES.GestorClientes.mostrarEstadisticas();
            txtInfo.setText("Ver consola para estadísticas del sistema");
        });

        return panel;
    }

    // Panel de registro de nuevos clientes
    private static JPanel crearPanelRegistro() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titulo = new JLabel("REGISTRO DE CLIENTE CON ANTICOLISIONES", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(titulo, gbc);

        // Campo usuario
        gbc.gridwidth = 1;
        gbc.gridy = 1; gbc.gridx = 0;
        panel.add(new JLabel("Usuario:"), gbc);
        JTextField txtUsuario = new JTextField(15);
        gbc.gridx = 1;
        panel.add(txtUsuario, gbc);

        // Campo contraseña
        gbc.gridy = 2; gbc.gridx = 0;
        panel.add(new JLabel("Contraseña:"), gbc);
        JPasswordField txtPassword = new JPasswordField(15);
        gbc.gridx = 1;
        panel.add(txtPassword, gbc);

        // Botones
        JButton btnRegistrar = new JButton("Registrar");
        JButton btnVolver = new JButton("Volver al Login");

        gbc.gridy = 3; gbc.gridx = 0;
        panel.add(btnRegistrar, gbc);
        gbc.gridx = 1;
        panel.add(btnVolver, gbc);

        // Área de información
        JTextArea txtInfo = new JTextArea(8, 30);
        txtInfo.setEditable(false);
        JScrollPane scrollInfo = new JScrollPane(txtInfo);
        gbc.gridy = 4; gbc.gridwidth = 2;
        panel.add(scrollInfo, gbc);

        // Acción REGISTRAR
        btnRegistrar.addActionListener(e -> {
            String usuario = txtUsuario.getText();
            String contraseña = new String(txtPassword.getPassword());

            if (usuario.isEmpty() || contraseña.isEmpty()) {
                txtInfo.setText("Error: Debe completar todos los campos");
                return;
            }

            // VERIFICAR SI USUARIO YA EXISTE
            if (ClienteBRIDGES.GestorClientes.existeUsuario(usuario)) {
                txtInfo.setText("ERROR: USUARIO YA EXISTENTE\n\n");
                txtInfo.append("El usuario '" + usuario + "' ya está registrado.\n");
                txtInfo.append("Por favor, elija un nombre de usuario diferente.");
                return;
            }

            String hashBase = ClienteBRIDGES.HashUtil.generarHash(contraseña);
            String hashUnico = ClienteBRIDGES.HashUtil.generarHashUnico(contraseña,
                    ClienteBRIDGES.GestorClientes.getMapaClientes());

            txtInfo.setText("INFORMACION DEL REGISTRO:\n");
            txtInfo.append("Usuario: " + usuario + "\n");
            txtInfo.append("Hash base: " + hashBase + "\n");
            txtInfo.append("Hash único asignado: " + hashUnico + "\n\n");

            if (!hashBase.equals(hashUnico)) {
                txtInfo.append("COLISION DE HASH DETECTADA Y RESUELTA\n");
                txtInfo.append("Hash base " + hashBase + " ya estaba en uso\n");
                txtInfo.append("Se asignó hash único " + hashUnico + "\n\n");
            }

            if (ClienteBRIDGES.GestorClientes.registrarCliente(usuario, contraseña)) {
                txtInfo.append("Cliente registrado exitosamente\n");
                txtInfo.append("Hash único guardado en lugar de la contraseña\n\n");
                txtInfo.append("Ahora puede iniciar sesion con sus credenciales");

                txtUsuario.setText("");
                txtPassword.setText("");
            } else {
                txtInfo.append("Error: No se pudo registrar el usuario\n");
            }
        });

        // Acción VOLVER
        btnVolver.addActionListener(e -> {
            cardLayout.show(cardPanel, "LOGIN");
        });

        return panel;
    }

    // Panel principal después del login
    private static JPanel crearPanelPrincipal() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel lblBienvenida = new JLabel("", JLabel.CENTER);
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(lblBienvenida, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel(new GridLayout(5, 1, 10, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JButton btnRealizarPedido = new JButton("Realizar Pedido");
        JButton btnVerPedidos = new JButton("Ver Mis Pedidos");
        JButton btnCambiarPassword = new JButton("Cambiar Contraseña");
        JButton btnEstadisticas = new JButton("Ver Estadísticas Sistema");
        JButton btnCerrarSesion = new JButton("Cerrar Sesion");

        panelBotones.add(btnRealizarPedido);
        panelBotones.add(btnVerPedidos);
        panelBotones.add(btnCambiarPassword);
        panelBotones.add(btnEstadisticas);
        panelBotones.add(btnCerrarSesion);

        panel.add(panelBotones, BorderLayout.CENTER);

        // Actualizar bienvenida al mostrar panel
        panel.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                if (clienteLogueado != null) {
                    lblBienvenida.setText("BIENVENIDO " + clienteLogueado.getUsuario() +
                            " - Hash: " + clienteLogueado.getHashContraseña());
                }
            }
        });

        // Acciones de botones
        btnRealizarPedido.addActionListener(e -> {
            cardLayout.show(cardPanel, "PEDIDOS");
        });

        btnVerPedidos.addActionListener(e -> {
            mostrarPedidosUsuario();
        });

        btnCambiarPassword.addActionListener(e -> {
            cardLayout.show(cardPanel, "CAMBIAR_PASSWORD");
        });

        btnEstadisticas.addActionListener(e -> {
            ClienteBRIDGES.GestorClientes.mostrarEstadisticas();
            JOptionPane.showMessageDialog(frame,
                    "Ver consola para estadísticas del sistema",
                    "Estadísticas",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        btnCerrarSesion.addActionListener(e -> {
            clienteLogueado = null;
            cardLayout.show(cardPanel, "LOGIN");
        });

        return panel;
    }

    // Panel para realizar pedidos
    private static JPanel crearPanelPedidos() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titulo = new JLabel("NUEVO PEDIDO", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(titulo, gbc);

        // Campos del formulario
        gbc.gridwidth = 1;
        gbc.gridy = 1; gbc.gridx = 0;
        panel.add(new JLabel("Codigo:"), gbc);
        JTextField txtCodigo = new JTextField(15);
        gbc.gridx = 1;
        panel.add(txtCodigo, gbc);

        gbc.gridy = 2; gbc.gridx = 0;
        panel.add(new JLabel("Descripcion:"), gbc);
        JTextField txtDescripcion = new JTextField(15);
        gbc.gridx = 1;
        panel.add(txtDescripcion, gbc);

        gbc.gridy = 3; gbc.gridx = 0;
        panel.add(new JLabel("Peso (kg):"), gbc);
        JTextField txtPeso = new JTextField(15);
        gbc.gridx = 1;
        panel.add(txtPeso, gbc);

        gbc.gridy = 4; gbc.gridx = 0;
        panel.add(new JLabel("Direccion:"), gbc);
        JTextField txtDireccion = new JTextField(15);
        gbc.gridx = 1;
        panel.add(txtDireccion, gbc);

        // Botones
        JButton btnCrearPedido = new JButton("Crear Pedido");
        JButton btnVolver = new JButton("Volver");

        gbc.gridy = 5; gbc.gridx = 0;
        panel.add(btnCrearPedido, gbc);
        gbc.gridx = 1;
        panel.add(btnVolver, gbc);

        // Área de información
        JTextArea txtInfo = new JTextArea(5, 30);
        txtInfo.setEditable(false);
        JScrollPane scrollInfo = new JScrollPane(txtInfo);
        gbc.gridy = 6; gbc.gridwidth = 2;
        panel.add(scrollInfo, gbc);

        // Acción CREAR PEDIDO
        btnCrearPedido.addActionListener(e -> {
            String codigo = txtCodigo.getText();
            String descripcion = txtDescripcion.getText();
            String pesoStr = txtPeso.getText();
            String direccion = txtDireccion.getText();

            if (codigo.isEmpty() || descripcion.isEmpty() || pesoStr.isEmpty() || direccion.isEmpty()) {
                txtInfo.setText("Error: Debe completar todos los campos");
                return;
            }

            try {
                double peso = Double.parseDouble(pesoStr);
                PedidoClienteBRIDGES nuevoPedido = new PedidoClienteBRIDGES(
                        codigo, clienteLogueado.getUsuario(), descripcion, peso, direccion
                );

                if (PedidoClienteBRIDGES.GestorPedidos.crearPedido(nuevoPedido)) {
                    txtInfo.setText("Pedido registrado exitosamente\n\n");
                    txtInfo.append("Codigo: " + codigo + "\n");
                    txtInfo.append("Descripcion: " + descripcion + "\n");
                    txtInfo.append("Peso: " + peso + "kg\n");
                    txtInfo.append("Direccion: " + direccion + "\n");
                    txtInfo.append("Estado: Pendiente\n\n");
                    txtInfo.append("Espere asignación de repartidor");

                    // Limpiar campos
                    txtCodigo.setText("");
                    txtDescripcion.setText("");
                    txtPeso.setText("");
                    txtDireccion.setText("");
                } else {
                    txtInfo.setText("Error: El codigo de pedido ya existe");
                }
            } catch (NumberFormatException ex) {
                txtInfo.setText("Error: El peso debe ser un numero valido");
            }
        });

        // Acción VOLVER
        btnVolver.addActionListener(e -> {
            cardLayout.show(cardPanel, "PRINCIPAL");
        });

        return panel;
    }

    // Panel para cambiar contraseña
    private static JPanel crearPanelCambiarPassword() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titulo = new JLabel("CAMBIAR CONTRASEÑA", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(titulo, gbc);

        // Campo nueva contraseña
        gbc.gridwidth = 1;
        gbc.gridy = 1; gbc.gridx = 0;
        panel.add(new JLabel("Nueva Contraseña:"), gbc);
        JPasswordField txtNuevaPassword = new JPasswordField(15);
        gbc.gridx = 1;
        panel.add(txtNuevaPassword, gbc);

        // Botones
        JButton btnCambiar = new JButton("Cambiar Contraseña");
        JButton btnVolver = new JButton("Volver");

        gbc.gridy = 2; gbc.gridx = 0;
        panel.add(btnCambiar, gbc);
        gbc.gridx = 1;
        panel.add(btnVolver, gbc);

        // Área de información
        JTextArea txtInfo = new JTextArea(6, 30);
        txtInfo.setEditable(false);
        JScrollPane scrollInfo = new JScrollPane(txtInfo);
        gbc.gridy = 3; gbc.gridwidth = 2;
        panel.add(scrollInfo, gbc);

        // Acción CAMBIAR CONTRASEÑA
        btnCambiar.addActionListener(e -> {
            String nuevaContraseña = new String(txtNuevaPassword.getPassword());

            if (nuevaContraseña.isEmpty()) {
                txtInfo.setText("Error: Debe ingresar una nueva contraseña");
                return;
            }

            String hashBase = ClienteBRIDGES.HashUtil.generarHash(nuevaContraseña);
            String hashUnico = ClienteBRIDGES.HashUtil.generarHashUnico(nuevaContraseña,
                    ClienteBRIDGES.GestorClientes.getMapaClientes());

            txtInfo.setText("INFORMACION DEL CAMBIO:\n");
            txtInfo.append("Hash base: " + hashBase + "\n");
            txtInfo.append("Hash único: " + hashUnico + "\n\n");

            if (!hashBase.equals(hashUnico)) {
                txtInfo.append("Colisión detectada - Se asignará hash único\n\n");
            }

            if (ClienteBRIDGES.GestorClientes.cambiarContraseña(clienteLogueado.getUsuario(), nuevaContraseña)) {
                clienteLogueado = ClienteBRIDGES.GestorClientes.login(clienteLogueado.getUsuario(), nuevaContraseña);
                txtInfo.append("Contraseña cambiada exitosamente\n");
                txtInfo.append("Sesion actualizada con nueva contraseña");
                txtNuevaPassword.setText("");
            } else {
                txtInfo.append("Error al cambiar la contraseña\n");
            }
        });

        // Acción VOLVER
        btnVolver.addActionListener(e -> {
            cardLayout.show(cardPanel, "PRINCIPAL");
        });

        return panel;
    }

    // Mostrar pedidos del usuario
    private static void mostrarPedidosUsuario() {
        PedidoClienteBRIDGES.GestorPedidos.recargarDatos();

        ArrayList<PedidoClienteBRIDGES> misPedidos =
                PedidoClienteBRIDGES.GestorPedidos.obtenerPedidosUsuario(clienteLogueado.getUsuario());

        StringBuilder sb = new StringBuilder();
        sb.append("MIS PEDIDOS - ").append(clienteLogueado.getUsuario()).append("\n\n");

        if (misPedidos.isEmpty()) {
            sb.append("No tienes pedidos registrados");
        } else {
            for (PedidoClienteBRIDGES pedido : misPedidos) {
                sb.append("Codigo: ").append(pedido.getCodigo()).append("\n");
                sb.append("Descripcion: ").append(pedido.getDescripcion()).append("\n");
                sb.append("Peso: ").append(pedido.getPeso()).append("kg\n");
                sb.append("Direccion: ").append(pedido.getDireccionEntrega()).append("\n");
                sb.append("Estado: ").append(pedido.getEstado()).append("\n");
                sb.append("---\n");
            }
        }

        JTextArea textArea = new JTextArea(20, 40);
        textArea.setText(sb.toString());
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);
        JOptionPane.showMessageDialog(frame, scrollPane, "Mis Pedidos", JOptionPane.INFORMATION_MESSAGE);
    }
}