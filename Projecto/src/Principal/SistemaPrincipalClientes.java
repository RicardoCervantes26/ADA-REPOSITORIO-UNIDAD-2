package Principal;

import Clases.ClienteBRIDGES;
import Clases.PedidoClienteBRIDGES;
import java.util.Scanner;
import java.util.ArrayList;

public class SistemaPrincipalClientes {
    private static ClienteBRIDGES clienteLogueado = null;
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("SISTEMA CLIENTES BRIDGES - CON HASH");
        System.out.println("Login mediante comparacion de hashes");
        
        // Bucle principal del programa
        while (true) {
            if (clienteLogueado == null) {
                mostrarMenuNoLogueado(); // Menu cuando no hay usuario logueado
            } else {
                mostrarMenuLogueado(); // Menu cuando hay usuario logueado
            }
        }
    }

    // Menu para usuarios no autenticados
    private static void mostrarMenuNoLogueado() {
        System.out.println("\nMENU PRINCIPAL");
        System.out.println("1. Registrarse");
        System.out.println("2. Login");
        System.out.println("3. Probar hash de contraseña");
        System.out.println("4. Salir");
        System.out.print("Seleccione opcion: ");
        
        int opcion = sc.nextInt();
        sc.nextLine();

        switch (opcion) {
            case 1:
                registrarCliente();
                break;
            case 2:
                loginCliente();
                break;
            case 3:
                probarHash();
                break;
            case 4:
                System.out.println("Saliendo...");
                System.exit(0);
            default:
                System.out.println("Opcion invalida");
        }
    }

    // Menu para usuarios autenticados
    private static void mostrarMenuLogueado() {
        System.out.println("\nBIENVENIDO " + clienteLogueado.getUsuario());
        System.out.println("Hash de contraseña: " + clienteLogueado.getHashContraseña());
        System.out.println("1. Realizar pedido");
        System.out.println("2. Ver mis pedidos");
        System.out.println("3. Cambiar contraseña");
        System.out.println("4. Cerrar sesion");
        System.out.print("Seleccione opcion: ");
        
        int opcion = sc.nextInt();
        sc.nextLine();

        switch (opcion) {
            case 1:
                realizarPedido();
                break;
            case 2:
                verMisPedidos();
                break;
            case 3:
                cambiarContraseña();
                break;
            case 4:
                clienteLogueado = null;
                System.out.println("Sesion cerrada");
                break;
            default:
                System.out.println("Opcion invalida");
        }
    }

    // Registrar nuevo cliente en el sistema
    private static void registrarCliente() {
        System.out.println("\nREGISTRO DE CLIENTE");
        System.out.print("Usuario: ");
        String usuario = sc.nextLine();
        System.out.print("Contraseña: ");
        String contraseña = sc.nextLine();

        // Mostrar el hash que se generará para transparency
        String hashGenerado = ClienteBRIDGES.HashUtil.generarHash(contraseña);
        System.out.println("Hash que se guardara: " + hashGenerado);
        
        if (ClienteBRIDGES.GestorClientes.registrarCliente(usuario, contraseña)) {
            System.out.println("Cliente registrado exitosamente");
            System.out.println("Hash guardado en lugar de la contraseña");
        }
    }

    // Iniciar sesion con verificacion por hash
    private static void loginCliente() {
        System.out.println("\nINICIO DE SESION CON HASH");
        System.out.print("Usuario: ");
        String usuario = sc.nextLine();
        System.out.print("Contraseña: ");
        String contraseña = sc.nextLine();

        System.out.println("\nPROCESO DE LOGIN");
        System.out.println("1. Se ingresa usuario y contraseña");
        System.out.println("2. Se genera hash de la contraseña ingresada");
        System.out.println("3. Se compara con el hash guardado");
        
        clienteLogueado = ClienteBRIDGES.GestorClientes.login(usuario, contraseña);
        
        if (clienteLogueado != null) {
            System.out.println("Login exitoso - Hashes coinciden");
        } else {
            System.out.println("Login fallido - Hashes no coinciden");
        }
    }

    // Funcion para probar el hashing con cualquier contraseña
    private static void probarHash() {
        System.out.println("\nPROBAR HASH DE CONTRASEÑA");
        System.out.print("Ingrese contraseña para ver su hash: ");
        String contraseña = sc.nextLine();
        ClienteBRIDGES.GestorClientes.mostrarHash(contraseña);
    }

    // Crear nuevo pedido para el cliente logueado
    private static void realizarPedido() {
        System.out.println("\nNUEVO PEDIDO");
        System.out.print("Codigo del pedido: ");
        String codigo = sc.nextLine();
        System.out.print("Descripcion del paquete: ");
        String descripcion = sc.nextLine();
        System.out.print("Peso (kg): ");
        double peso = sc.nextDouble();
        sc.nextLine();
        System.out.print("Direccion de entrega: ");
        String direccionEntrega = sc.nextLine();

        PedidoClienteBRIDGES nuevoPedido = new PedidoClienteBRIDGES(
            codigo, clienteLogueado.getUsuario(), descripcion, peso, direccionEntrega
        );

        if (PedidoClienteBRIDGES.GestorPedidos.crearPedido(nuevoPedido)) {
            System.out.println("Pedido registrado exitosamente");
        }
    }

    // Mostrar todos los pedidos del cliente logueado
    private static void verMisPedidos() {
        System.out.println("\nMIS PEDIDOS");
        ArrayList<PedidoClienteBRIDGES> misPedidos = 
            PedidoClienteBRIDGES.GestorPedidos.obtenerPedidosUsuario(clienteLogueado.getUsuario());
        
        if (misPedidos.isEmpty()) {
            System.out.println("No tienes pedidos registrados");
        } else {
            for (PedidoClienteBRIDGES pedido : misPedidos) {
                System.out.println("Codigo: " + pedido.getCodigo());
                System.out.println("Descripcion: " + pedido.getDescripcion());
                System.out.println("Peso: " + pedido.getPeso() + "kg");
                System.out.println("Direccion: " + pedido.getDireccionEntrega());
                System.out.println("Estado: " + pedido.getEstado());
                System.out.println("---");
            }
        }
    }

    // Cambiar contraseña del cliente logueado
    private static void cambiarContraseña() {
        System.out.println("\nCAMBIAR CONTRASEÑA");
        System.out.print("Nueva contraseña: ");
        String nuevaContraseña = sc.nextLine();

        String nuevoHash = ClienteBRIDGES.HashUtil.generarHash(nuevaContraseña);
        System.out.println("Nuevo hash que se guardara: " + nuevoHash);

        if (ClienteBRIDGES.GestorClientes.cambiarContraseña(clienteLogueado.getUsuario(), nuevaContraseña)) {
            // Actualizar referencia al cliente logueado
            clienteLogueado = ClienteBRIDGES.GestorClientes.login(clienteLogueado.getUsuario(), nuevaContraseña);
            System.out.println("Contraseña cambiada exitosamente");
        }
    }
}