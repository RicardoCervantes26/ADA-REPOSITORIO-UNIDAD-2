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
        // Ejecutar en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            crearInterfaz();
        });
    }

    // Metodo para crear la interfaz
    private static void crearInterfaz() {
        frame = new JFrame("Sistema Clientes BRIDGES - CON HASH");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLocationRelativeTo(null); // Centrar ventana

        // Configurar CardLayout para cambiar entre paneles
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Crear y agregar todos los paneles
        cardPanel.add(crearPanelLogin(), "LOGIN");
        cardPanel.add(crearPanelRegistro(), "REGISTRO");
        cardPanel.add(crearPanelPrincipal(), "PRINCIPAL");
        cardPanel.add(crearPanelPedidos(), "PEDIDOS");
        cardPanel.add(crearPanelCambiarPassword(), "CAMBIAR_PASSWORD");

        frame.add(cardPanel);
        // Mostrar panel de login al iniciar
        cardLayout.show(cardPanel, "LOGIN");
        frame.setVisible(true);
    }

    // Panel de inicio de sesion
    private static JPanel crearPanelLogin() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Titulo del panel
        JLabel titulo = new JLabel("INICIO DE SESION CON HASH", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(titulo, gbc);

        // Campo de usuario
        gbc.gridwidth = 1;
        gbc.gridy = 1; gbc.gridx = 0;
        panel.add(new JLabel("Usuario:"), gbc);
        JTextField txtUsuario = new JTextField(15);
        gbc.gridx = 1;
        panel.add(txtUsuario, gbc);

        // Campo de contraseña
        gbc.gridy = 2; gbc.gridx = 0;
        panel.add(new JLabel("Contraseña:"), gbc);
        JPasswordField txtPassword = new JPasswordField(15);
        gbc.gridx = 1;
        panel.add(txtPassword, gbc);

        // Botones
        JButton btnLogin = new JButton("Login");
        JButton btnRegistrar = new JButton("Registrarse");
        JButton btnProbarHash = new JButton("Probar Hash");

        gbc.gridy = 3; gbc.gridx = 0;
        panel.add(btnLogin, gbc);
        gbc.gridx = 1;
        panel.add(btnRegistrar, gbc);

        gbc.gridy = 4; gbc.gridx = 0; gbc.gridwidth = 2;
        panel.add(btnProbarHash, gbc);

        // Area de informacion
        JTextArea txtInfo = new JTextArea(5, 30);
        txtInfo.setEditable(false);
        JScrollPane scrollInfo = new JScrollPane(txtInfo);
        gbc.gridy = 5;
        panel.add(scrollInfo, gbc);

        // Accion del boton Login
        btnLogin.addActionListener(e -> {
            String usuario = txtUsuario.getText();
            String contraseña = new String(txtPassword.getPassword());

            // Validar campos vacios
            if (usuario.isEmpty() || contraseña.isEmpty()) {
                txtInfo.setText("Error: Debe completar todos los campos");
                return;
            }

            // Mostrar proceso de login
            txtInfo.setText("PROCESO DE LOGIN:\n");
            txtInfo.append("1. Se ingresa usuario y contraseña\n");
            txtInfo.append("2. Se genera hash de la contraseña ingresada\n");
            txtInfo.append("3. Se compara con el hash guardado\n\n");

            // Intentar login usando la clase ClienteBRIDGES
            clienteLogueado = ClienteBRIDGES.GestorClientes.login(usuario, contraseña);

            if (clienteLogueado != null) {
                // Login exitoso
                txtInfo.append("Hash ingresado: " + ClienteBRIDGES.HashUtil.generarHash(contraseña) + "\n");
                txtInfo.append("Hash guardado: " + clienteLogueado.getHashContraseña() + "\n");
                txtInfo.append("Login exitoso - Hashes coinciden");
                // Cambiar al panel principal
                cardLayout.show(cardPanel, "PRINCIPAL");
            } else {
                // Login fallido
                txtInfo.append("Login fallido - Hashes no coinciden\n");
                txtInfo.append("Usuario o contraseña incorrectos");
            }
        });

        // Accion del boton Registrar
        btnRegistrar.addActionListener(e -> {
            cardLayout.show(cardPanel, "REGISTRO");
        });

        // Accion del boton Probar Hash
        btnProbarHash.addActionListener(e -> {
            String contraseña = new String(txtPassword.getPassword());
            if (!contraseña.isEmpty()) {
                // Mostrar demostracion del hashing
                ClienteBRIDGES.GestorClientes.mostrarHash(contraseña);
                String hash = ClienteBRIDGES.HashUtil.generarHash(contraseña);
                txtInfo.setText("DEMOSTRACION HASH:\n");
                txtInfo.append("Contraseña: '" + contraseña + "'\n");
                txtInfo.append("Hash generado: " + hash + "\n\n");
                txtInfo.append("Este hash es lo que se guarda en el sistema,\n");
                txtInfo.append("NO la contraseña en texto plano.");
            }
        });

        return panel;
    }

    // Panel de registro de nuevos clientes
    private static JPanel crearPanelRegistro() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titulo = new JLabel("REGISTRO DE CLIENTE", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(titulo, gbc);

        // Campo de usuario
        gbc.gridwidth = 1;
        gbc.gridy = 1; gbc.gridx = 0;
        panel.add(new JLabel("Usuario:"), gbc);
        JTextField txtUsuario = new JTextField(15);
        gbc.gridx = 1;
        panel.add(txtUsuario, gbc);

        // Campo de contraseña
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

        // Area de informacion
        JTextArea txtInfo = new JTextArea(6, 30);
        txtInfo.setEditable(false);
        JScrollPane scrollInfo = new JScrollPane(txtInfo);
        gbc.gridy = 4; gbc.gridwidth = 2;
        panel.add(scrollInfo, gbc);

        // Accion del boton Registrar
        btnRegistrar.addActionListener(e -> {
            String usuario = txtUsuario.getText();
            String contraseña = new String(txtPassword.getPassword());

            // Validar campos vacios
            if (usuario.isEmpty() || contraseña.isEmpty()) {
                txtInfo.setText("Error: Debe completar todos los campos");
                return;
            }

            // Generar y mostrar hash
            String hashGenerado = ClienteBRIDGES.HashUtil.generarHash(contraseña);
            txtInfo.setText("INFORMACION DEL REGISTRO:\n");
            txtInfo.append("Usuario: " + usuario + "\n");
            txtInfo.append("Hash que se guardara: " + hashGenerado + "\n\n");

            // Intentar registrar cliente
            if (ClienteBRIDGES.GestorClientes.registrarCliente(usuario, contraseña)) {
                txtInfo.append("Cliente registrado exitosamente\n");
                txtInfo.append("Hash guardado en lugar de la contraseña\n\n");
                txtInfo.append("Ahora puede iniciar sesion con sus credenciales");

                // Limpiar campos
                txtUsuario.setText("");
                txtPassword.setText("");
            } else {
                txtInfo.append("Error: El usuario ya existe");
            }
        });

        // Accion del boton Volver
        btnVolver.addActionListener(e -> {
            cardLayout.show(cardPanel, "LOGIN");
        });

        return panel;
    }

    // Panel principal despues del login
    private static JPanel crearPanelPrincipal() {
        JPanel panel = new JPanel(new BorderLayout());

        // Etiqueta de bienvenida
        JLabel lblBienvenida = new JLabel("", JLabel.CENTER);
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(lblBienvenida, BorderLayout.NORTH);

        // Panel de botones
        JPanel panelBotones = new JPanel(new GridLayout(4, 1, 10, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JButton btnRealizarPedido = new JButton("Realizar Pedido");
        JButton btnVerPedidos = new JButton("Ver Mis Pedidos");
        JButton btnCambiarPassword = new JButton("Cambiar Contraseña");
        JButton btnCerrarSesion = new JButton("Cerrar Sesion");

        panelBotones.add(btnRealizarPedido);
        panelBotones.add(btnVerPedidos);
        panelBotones.add(btnCambiarPassword);
        panelBotones.add(btnCerrarSesion);

        panel.add(panelBotones, BorderLayout.CENTER);

        // Actualizar bienvenida cuando se muestra el panel
        panel.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                if (clienteLogueado != null) {
                    lblBienvenida.setText("BIENVENIDO " + clienteLogueado.getUsuario() +
                            " - Hash: " + clienteLogueado.getHashContraseña());
                }
            }
        });

        // Acciones de los botones
        btnRealizarPedido.addActionListener(e -> {
            cardLayout.show(cardPanel, "PEDIDOS");
        });

        btnVerPedidos.addActionListener(e -> {
            mostrarPedidosUsuario();
        });

        btnCambiarPassword.addActionListener(e -> {
            cardLayout.show(cardPanel, "CAMBIAR_PASSWORD");
        });

        btnCerrarSesion.addActionListener(e -> {
            clienteLogueado = null;
            cardLayout.show(cardPanel, "LOGIN");
        });

        return panel;
    }

    // Panel para realizar nuevos pedidos
    private static JPanel crearPanelPedidos() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titulo = new JLabel("NUEVO PEDIDO", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(titulo, gbc);

        // Campos del formulario de pedido
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

        // Area de informacion
        JTextArea txtInfo = new JTextArea(5, 30);
        txtInfo.setEditable(false);
        JScrollPane scrollInfo = new JScrollPane(txtInfo);
        gbc.gridy = 6; gbc.gridwidth = 2;
        panel.add(scrollInfo, gbc);

        // Accion del boton Crear Pedido
        btnCrearPedido.addActionListener(e -> {
            String codigo = txtCodigo.getText();
            String descripcion = txtDescripcion.getText();
            String pesoStr = txtPeso.getText();
            String direccion = txtDireccion.getText();

            // Validar campos vacios
            if (codigo.isEmpty() || descripcion.isEmpty() || pesoStr.isEmpty() || direccion.isEmpty()) {
                txtInfo.setText("Error: Debe completar todos los campos");
                return;
            }

            try {
                double peso = Double.parseDouble(pesoStr);
                // Crear nuevo objeto pedido
                PedidoClienteBRIDGES nuevoPedido = new PedidoClienteBRIDGES(
                        codigo, clienteLogueado.getUsuario(), descripcion, peso, direccion
                );

                // Intentar guardar el pedido
                if (PedidoClienteBRIDGES.GestorPedidos.crearPedido(nuevoPedido)) {
                    txtInfo.setText("Pedido registrado exitosamente\n\n");
                    txtInfo.append("Codigo: " + codigo + "\n");
                    txtInfo.append("Descripcion: " + descripcion + "\n");
                    txtInfo.append("Peso: " + peso + "kg\n");
                    txtInfo.append("Direccion: " + direccion + "\n");
                    txtInfo.append("Estado: Pendiente\n\n");
                    txtInfo.append("Espere a que la empresa asigne un repartidor");

                    // Limpiar campos despues del registro exitoso
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

        // Accion del boton Volver
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

        // Campo para nueva contraseña
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

        // Area de informacion
        JTextArea txtInfo = new JTextArea(4, 30);
        txtInfo.setEditable(false);
        JScrollPane scrollInfo = new JScrollPane(txtInfo);
        gbc.gridy = 3; gbc.gridwidth = 2;
        panel.add(scrollInfo, gbc);

        // Accion del boton Cambiar Contraseña
        btnCambiar.addActionListener(e -> {
            String nuevaContraseña = new String(txtNuevaPassword.getPassword());

            if (nuevaContraseña.isEmpty()) {
                txtInfo.setText("Error: Debe ingresar una nueva contraseña");
                return;
            }

            // Generar y mostrar nuevo hash
            String nuevoHash = ClienteBRIDGES.HashUtil.generarHash(nuevaContraseña);
            txtInfo.setText("INFORMACION DEL CAMBIO:\n");
            txtInfo.append("Nuevo hash que se guardara: " + nuevoHash + "\n\n");

            // Intentar cambiar la contraseña
            if (ClienteBRIDGES.GestorClientes.cambiarContraseña(clienteLogueado.getUsuario(), nuevaContraseña)) {
                // Actualizar sesion con nueva contraseña
                clienteLogueado = ClienteBRIDGES.GestorClientes.login(clienteLogueado.getUsuario(), nuevaContraseña);
                txtInfo.append("Contraseña cambiada exitosamente\n");
                txtInfo.append("Sesion actualizada con nueva contraseña");
                txtNuevaPassword.setText("");
            } else {
                txtInfo.append("Error al cambiar la contraseña");
            }
        });

        // Accion del boton Volver
        btnVolver.addActionListener(e -> {
            cardLayout.show(cardPanel, "PRINCIPAL");
        });

        return panel;
    }

    // Metodo para mostrar los pedidos del usuario en un dialogo
    private static void mostrarPedidosUsuario() {
        // FORZAR RECARGA DE DATOS PARA OBTENER ESTADOS ACTUALIZADOS
        PedidoClienteBRIDGES.GestorPedidos.recargarDatos();

        // Obtener pedidos del usuario actual
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

        // Mostrar en un dialogo
        JTextArea textArea = new JTextArea(20, 40);
        textArea.setText(sb.toString());
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);
        JOptionPane.showMessageDialog(frame, scrollPane, "Mis Pedidos", JOptionPane.INFORMATION_MESSAGE);
    }
}