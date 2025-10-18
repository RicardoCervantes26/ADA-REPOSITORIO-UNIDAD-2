
package Ejemplo;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
    private ListaInvertida listaInvertida;
    private JFrame frame;
    private JTextArea textAreaResultados;
    private JTextField textFieldDocumento;
    private JTextField textFieldBusqueda;
    private JLabel labelEstadisticas;

    public Main() {
        listaInvertida = new ListaInvertida();
        inicializarGUI();
        actualizarEstadisticas();
    }

    private void inicializarGUI() {
        frame = new JFrame("Sistema de Lista Invertida");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setSize(900, 700);
        frame.setLayout(new BorderLayout(10, 10));

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                guardarYSalir();
            }
        });

        // Panel título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(70, 130, 180));
        JLabel labelTitulo = new JLabel("SISTEMA DE LISTA INVERTIDA PURA");
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        labelTitulo.setForeground(Color.WHITE);
        panelTitulo.add(labelTitulo);

        // Panel estadísticas
        JPanel panelEstadisticas = new JPanel();
        panelEstadisticas.setBorder(BorderFactory.createTitledBorder("Estadísticas"));
        labelEstadisticas = new JLabel();
        labelEstadisticas.setFont(new Font("Arial", Font.BOLD, 14));
        panelEstadisticas.add(labelEstadisticas);

        // Panel central con pestañas
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.PLAIN, 14));

        tabbedPane.addTab("Indexar Documentos", crearPanelIndexacion());
        tabbedPane.addTab("Buscar", crearPanelBusqueda());
        tabbedPane.addTab("Ver Índice", crearPanelVerIndice());

        // Área de resultados
        JPanel panelResultados = new JPanel(new BorderLayout());
        panelResultados.setBorder(BorderFactory.createTitledBorder("Resultados"));
        panelResultados.setPreferredSize(new Dimension(400, 0));

        textAreaResultados = new JTextArea(20, 50);
        textAreaResultados.setEditable(false);
        textAreaResultados.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollResultados = new JScrollPane(textAreaResultados);
        panelResultados.add(scrollResultados, BorderLayout.CENTER);

        // Panel botones
        JPanel panelBotones = new JPanel();
        JButton btnLimpiar = new JButton("Limpiar Resultados");
        JButton btnSalir = new JButton("Guardar y Salir");

        btnLimpiar.addActionListener(e -> textAreaResultados.setText(""));
        btnSalir.addActionListener(e -> guardarYSalir());

        panelBotones.add(btnLimpiar);
        panelBotones.add(btnSalir);

        // Organizar componentes
        frame.add(panelTitulo, BorderLayout.NORTH);
        frame.add(panelEstadisticas, BorderLayout.SOUTH);
        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.add(panelResultados, BorderLayout.EAST);
        frame.add(panelBotones, BorderLayout.WEST);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JPanel crearPanelIndexacion() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel labelInstrucciones = new JLabel("Ingrese el contenido del documento a indexar:");
        textFieldDocumento = new JTextField(40);

        JButton btnAgregar = new JButton("Indexar Documento");

        JPanel panelEntrada = new JPanel();
        panelEntrada.add(new JLabel("Contenido:"));
        panelEntrada.add(textFieldDocumento);

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnAgregar);

        btnAgregar.addActionListener(e -> indexarDocumento());
        textFieldDocumento.addActionListener(e -> indexarDocumento());

        panel.add(labelInstrucciones, BorderLayout.NORTH);
        panel.add(panelEntrada, BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearPanelBusqueda() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel labelInstrucciones = new JLabel("Ingrese un término para buscar:");
        textFieldBusqueda = new JTextField(30);

        JButton btnBuscar = new JButton("Buscar Término");

        JPanel panelEntrada = new JPanel();
        panelEntrada.add(new JLabel("Término:"));
        panelEntrada.add(textFieldBusqueda);
        panelEntrada.add(btnBuscar);

        btnBuscar.addActionListener(e -> buscarTermino());
        textFieldBusqueda.addActionListener(e -> buscarTermino());

        panel.add(labelInstrucciones, BorderLayout.NORTH);
        panel.add(panelEntrada, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelVerIndice() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JButton btnMostrarIndice = new JButton("Mostrar Diccionario");
        JButton btnExportar = new JButton("Exportar Índice");

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnMostrarIndice);
        panelBotones.add(btnExportar);

        btnMostrarIndice.addActionListener(e -> mostrarIndiceCompleto());
        btnExportar.addActionListener(e -> exportarIndice());

        panel.add(new JLabel("Visualización del diccionario de términos:"), BorderLayout.NORTH);
        panel.add(panelBotones, BorderLayout.CENTER);

        return panel;
    }

    private void indexarDocumento() {
        String contenido = textFieldDocumento.getText().trim();
        if (!contenido.isEmpty()) {
            listaInvertida.agregarDocumento(contenido);
            textAreaResultados.append("Documento indexado: \"" + contenido + "\"\n");
            textFieldDocumento.setText("");
            actualizarEstadisticas();
        }
    }

    private void buscarTermino() {
        String termino = textFieldBusqueda.getText().trim();
        if (!termino.isEmpty()) {
            java.util.List<Documento> resultados = listaInvertida.buscarDocumentos(termino);
            textAreaResultados.append("Búsqueda: '" + termino + "' -> " + resultados.size() + " documentos\n");
            for (Documento doc : resultados) {
                textAreaResultados.append("  " + doc + "\n");
            }
            textAreaResultados.append("\n");
            textFieldBusqueda.setText("");
        }
    }

    private void mostrarIndiceCompleto() {
        String resumen = listaInvertida.obtenerResumenIndice();
        textAreaResultados.setText(resumen);
    }

    private void exportarIndice() {
        listaInvertida.guardarIndice();
        JOptionPane.showMessageDialog(frame, "Índice guardado en lista_invertida.txt");
    }

    private void actualizarEstadisticas() {
        String stats = "Términos: " + listaInvertida.getTotalTerminos() + " | Documentos: " + listaInvertida.getTotalDocumentos();
        labelEstadisticas.setText(stats);
    }

    private void guardarYSalir() {
        int confirmacion = JOptionPane.showConfirmDialog(frame,
                "¿Guardar índice y salir?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            listaInvertida.guardarIndice();
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Main();
        });
    }
}