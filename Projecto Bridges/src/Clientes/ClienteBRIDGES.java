package Clientes;
import java.util.HashMap;
import java.io.*;

public class ClienteBRIDGES {
    private String usuario;
    private String hashContraseña; // Se guarda el hash,osea la contraseña pero ya hasheada

    public ClienteBRIDGES() {}

    public ClienteBRIDGES(String usuario, String hashContraseña) {
        this.usuario = usuario;
        this.hashContraseña = hashContraseña;
    }

    public String getUsuario() { return usuario; }
    public String getHashContraseña() { return hashContraseña; }
    public void setHashContraseña(String hashContraseña) { this.hashContraseña = hashContraseña; }

    // Clase utilitaria para funciones de hash
    public static class HashUtil {
        // Metodo de hash por division (convierte contraseña a codigo numerico)
        public static String generarHash(String contraseña) {
            int hash = 0;
            for (char c : contraseña.toCharArray()) {
                hash = (hash * 31 + c) % 1000; // % 1000 para códigos de 3 dígitos
            }
            return String.format("%03d", Math.abs(hash)); // Formato de 3 dígitos
        }
    }

    // Gestor principal de clientes con HashMap
    public static class GestorClientes {
        private static HashMap<String, ClienteBRIDGES> mapaClientes = new HashMap<>();
        private static final String ARCHIVO = "clientes.txt";
        private static final String ARCHIVO_HASH = "hashes_demo.txt"; // Para mostrar hashes

        // Cargar datos del txt al iniciar el programa
        static {
            cargarDesdeArchivo();
        }

        // Registrar nuevo cliente con verificacion de usuario único
        public static boolean registrarCliente(String usuario, String contraseña) {
            if (mapaClientes.containsKey(usuario.toLowerCase())) {
                System.out.println("Error: El usuario ya existe");
                return false;
            }

            // Generar hash de la contraseña
            String hashContraseña = HashUtil.generarHash(contraseña);
            ClienteBRIDGES nuevoCliente = new ClienteBRIDGES(usuario, hashContraseña);

            mapaClientes.put(usuario.toLowerCase(), nuevoCliente);
            guardarEnArchivos();
            return true;
        }

        // Login: compara hash de contraseña ingresada con hash guardado
        public static ClienteBRIDGES login(String usuario, String contraseña) {
            ClienteBRIDGES cliente = mapaClientes.get(usuario.toLowerCase());
            if (cliente != null) {
                String hashIngresado = HashUtil.generarHash(contraseña);
                String hashGuardado = cliente.getHashContraseña();

                System.out.println("Hash ingresado: " + hashIngresado);
                System.out.println("Hash guardado: " + hashGuardado);

                // Comparacion de hashes (no de contraseñas en texto)
                if (hashIngresado.equals(hashGuardado)) {
                    return cliente;
                }
            }
            return null;
        }

        // Cambiar contraseña generando nuevo hash
        public static boolean cambiarContraseña(String usuario, String nuevaContraseña) {
            ClienteBRIDGES cliente = mapaClientes.get(usuario.toLowerCase());
            if (cliente != null) {
                String nuevoHash = HashUtil.generarHash(nuevaContraseña);
                cliente.setHashContraseña(nuevoHash);
                guardarEnArchivos();
                return true;
            }
            return false;
        }

        // Metodo para demostrar el funcionamiento del hash
        public static void mostrarHash(String contraseña) {
            String hash = HashUtil.generarHash(contraseña);
            System.out.println("Contraseña: '" + contraseña + "' -> Hash: " + hash);
        }

        // Guardar datos en archivos
        private static void guardarEnArchivos() {
            guardarClientes();
            guardarHashesDemo();
        }

        // Guardar datos de clientes para persistencia
        private static void guardarClientes() {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO))) {
                for (ClienteBRIDGES cliente : mapaClientes.values()) {
                    writer.write(cliente.getUsuario() + "|" + cliente.getHashContraseña());
                    writer.newLine();
                }
            } catch (IOException e) {
                System.out.println("Error guardando clientes: " + e.getMessage());
            }
        }

        // Guardar archivo de demostracion de hashes
        private static void guardarHashesDemo() {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_HASH))) {
                writer.write("Usuario|Hash");
                writer.newLine();
                writer.write("----------------");
                writer.newLine();

                for (ClienteBRIDGES cliente : mapaClientes.values()) {
                    writer.write(cliente.getUsuario() + "|" + cliente.getHashContraseña());
                    writer.newLine();
                }
            } catch (IOException e) {
                System.out.println("Error guardando hashes: " + e.getMessage());
            }
        }

        // Cargar datos desde archivo al iniciar el programa
        private static void cargarDesdeArchivo() {
            File archivo = new File(ARCHIVO);
            if (!archivo.exists()) return;

            try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    String[] datos = linea.split("\\|");
                    if (datos.length == 2) {
                        ClienteBRIDGES cliente = new ClienteBRIDGES(datos[0], datos[1]);
                        mapaClientes.put(datos[0].toLowerCase(), cliente);
                    }
                }
            } catch (IOException e) {
                System.out.println("Error cargando clientes: " + e.getMessage());
            }
        }
    }
}
