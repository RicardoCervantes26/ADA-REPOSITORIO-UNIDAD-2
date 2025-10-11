package Clientes;
import java.util.HashMap;
import java.io.*;

public class ClienteBRIDGES {
    private String usuario;
    private String hashContraseña;

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

        // Convierte contraseña en código hash de 3 dígitos
        public static String generarHash(String contraseña) {
            int hash = 0;
            for (char c : contraseña.toCharArray()) {
                hash = (hash * 31 + c) % 1000;
            }
            return String.format("%03d", Math.abs(hash));
        }

        // Encuentra hash único evitando colisiones
        public static String generarHashUnico(String contraseña, HashMap<String, ClienteBRIDGES> mapaClientes) {
            String hashBase = generarHash(contraseña);
            String hashFinal = hashBase;
            int contador = 1;

            // Buscar hash disponible si el base está ocupado
            while (existeHashEnSistema(hashFinal, mapaClientes)) {
                int valorHash = Integer.parseInt(hashFinal);
                valorHash = (valorHash + 1) % 1000;
                hashFinal = String.format("%03d", valorHash);
                contador++;

                if (contador > 1000) {
                    return null;
                }
            }

            return hashFinal;
        }

        // Verifica si hash ya está en uso
        private static boolean existeHashEnSistema(String hash, HashMap<String, ClienteBRIDGES> mapaClientes) {
            for (ClienteBRIDGES cliente : mapaClientes.values()) {
                if (cliente.getHashContraseña().equals(hash)) {
                    return true;
                }
            }
            return false;
        }
    }

    // Gestor principal de clientes
    public static class GestorClientes {
        private static HashMap<String, ClienteBRIDGES> mapaClientes = new HashMap<>();
        private static final String ARCHIVO = "clientes.txt";
        private static final String ARCHIVO_HASH = "hashes_demo.txt";

        // Cargar datos al iniciar
        static {
            cargarDesdeArchivo();
        }

        // Para acceso externo al mapa
        public static HashMap<String, ClienteBRIDGES> getMapaClientes() {
            return mapaClientes;
        }

        // Verifica si usuario ya existe (case-insensitive)
        public static boolean existeUsuario(String usuario) {
            return mapaClientes.containsKey(usuario.toLowerCase());
        }

        // Valida formato de usuario: solo letras y números, 3-20 caracteres
        private static boolean validarFormatoUsuario(String usuario) {
            if (usuario == null || usuario.trim().isEmpty()) {
                return false;
            }
            return usuario.matches("^[a-zA-Z0-9]{3,20}$");
        }

        // Valida longitud de contraseña
        private static boolean validarFortalezaContraseña(String contraseña) {
            return contraseña != null && contraseña.length() >= 4;
        }

        // REGISTRO: Verifica usuario único y genera hash único
        public static boolean registrarCliente(String usuario, String contraseña) {
            // Validar formato de usuario
            if (!validarFormatoUsuario(usuario)) {
                System.out.println("Error: Usuario debe tener 3-20 caracteres (solo letras y números)");
                return false;
            }

            // Validar contraseña
            if (!validarFortalezaContraseña(contraseña)) {
                System.out.println("Error: Contraseña debe tener al menos 4 caracteres");
                return false;
            }

            // VERIFICAR QUE USUARIO NO EXISTA - EVITA DUPLICADOS
            if (existeUsuario(usuario)) {
                System.out.println("Error: El usuario '" + usuario + "' ya existe");
                return false;
            }

            // Generar hash único para la contraseña
            String hashContraseña = HashUtil.generarHashUnico(contraseña, mapaClientes);
            if (hashContraseña == null) {
                System.out.println("Error: No hay hashes disponibles");
                return false;
            }

            // Crear y guardar nuevo cliente
            ClienteBRIDGES nuevoCliente = new ClienteBRIDGES(usuario, hashContraseña);
            mapaClientes.put(usuario.toLowerCase(), nuevoCliente);
            guardarEnArchivos();

            System.out.println("Usuario '" + usuario + "' registrado. Hash: " + hashContraseña);
            return true;
        }

        // LOGIN: Autentica usuario comparando hashes
        public static ClienteBRIDGES login(String usuario, String contraseña) {
            ClienteBRIDGES cliente = mapaClientes.get(usuario.toLowerCase());
            if (cliente != null) {
                String hashIngresado = HashUtil.generarHash(contraseña);
                String hashGuardado = cliente.getHashContraseña();

                if (hashIngresado.equals(hashGuardado)) {
                    System.out.println("Login exitoso: " + usuario);
                    return cliente;
                }
            }
            System.out.println("Login fallido: " + usuario);
            return null;
        }

        // Cambiar contraseña generando nuevo hash único
        public static boolean cambiarContraseña(String usuario, String nuevaContraseña) {
            if (!validarFortalezaContraseña(nuevaContraseña)) {
                System.out.println("Error: Nueva contraseña debe tener al menos 4 caracteres");
                return false;
            }

            ClienteBRIDGES cliente = mapaClientes.get(usuario.toLowerCase());
            if (cliente != null) {
                String nuevoHash = HashUtil.generarHashUnico(nuevaContraseña, mapaClientes);
                if (nuevoHash == null) {
                    System.out.println("Error: No hay hashes disponibles");
                    return false;
                }

                cliente.setHashContraseña(nuevoHash);
                guardarEnArchivos();
                System.out.println("Contraseña cambiada para '" + usuario + "'. Nuevo hash: " + nuevoHash);
                return true;
            }
            return false;
        }

        // Muestra información de hash para una contraseña
        public static void mostrarHash(String contraseña) {
            String hashBase = HashUtil.generarHash(contraseña);
            String hashUnico = HashUtil.generarHashUnico(contraseña, mapaClientes);
            System.out.println("Contraseña: '" + contraseña + "' -> Hash base: " + hashBase + " -> Hash único: " + hashUnico);
        }

        // Estadísticas del sistema
        public static void mostrarEstadisticas() {
            System.out.println("=== ESTADÍSTICAS SISTEMA ===");
            System.out.println("Total clientes: " + mapaClientes.size());

            HashMap<String, Integer> frecuenciaHashes = new HashMap<>();
            int colisionesTotales = 0;

            for (ClienteBRIDGES cliente : mapaClientes.values()) {
                String hash = cliente.getHashContraseña();
                frecuenciaHashes.put(hash, frecuenciaHashes.getOrDefault(hash, 0) + 1);
            }

            int colisiones = 0;
            for (int count : frecuenciaHashes.values()) {
                if (count > 1) {
                    colisiones++;
                    colisionesTotales += (count - 1);
                }
            }

            System.out.println("Hashes únicos: " + frecuenciaHashes.size());
            System.out.println("Colisiones de hash: " + colisiones);
            System.out.println("Colisiones resueltas: " + colisionesTotales);
            System.out.println("Eficiencia: " +
                    String.format("%.2f", ((double)(mapaClientes.size() - colisionesTotales) / mapaClientes.size() * 100)) + "%");
            System.out.println("===========================");
        }

        // Guardar datos en archivos
        private static void guardarEnArchivos() {
            guardarClientes();
            guardarHashesDemo();
        }

        // Guardar clientes en archivo
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

        // Guardar archivo de demostración
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

        // Cargar datos desde archivo
        private static void cargarDesdeArchivo() {
            File archivo = new File(ARCHIVO);
            if (!archivo.exists()) return;

            try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
                String linea;
                int contador = 0;
                while ((linea = reader.readLine()) != null) {
                    String[] datos = linea.split("\\|");
                    if (datos.length == 2) {
                        ClienteBRIDGES cliente = new ClienteBRIDGES(datos[0], datos[1]);
                        mapaClientes.put(datos[0].toLowerCase(), cliente);
                        contador++;
                    }
                }
                System.out.println("Cargados " + contador + " clientes desde archivo");
            } catch (IOException e) {
                System.out.println("Error cargando clientes: " + e.getMessage());
            }
        }
    }
}