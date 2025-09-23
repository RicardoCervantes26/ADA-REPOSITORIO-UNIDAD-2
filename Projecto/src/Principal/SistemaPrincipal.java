package Principal;

import Clases.PedidoBRIDGES;
import Clases.RepartidorBRIDGES;
import Clases.AsignacionBRIDGES;
import Clases.EntregaBRIDGES;
import java.util.ArrayList;
import java.util.Scanner;

public class SistemaPrincipal {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // ✅ USANDO HASHMAP EN LUGAR DE ARRAYLIST
        RepartidorBRIDGES.MapaRepartidores mapaRepartidores = new RepartidorBRIDGES.MapaRepartidores();
        ArrayList<PedidoBRIDGES> listaPedidos = new ArrayList<>();
        ArrayList<AsignacionBRIDGES> listaAsignaciones = new ArrayList<>();
        ArrayList<EntregaBRIDGES> listaEntregas = new ArrayList<>();

        int opcion;
        do {
            System.out.println("\n====== MENÚ PRINCIPAL BRIDGES ======");
            System.out.println("1. Módulo de Pedidos");
            System.out.println("2. Módulo de Repartidores (HASHMAP)");
            System.out.println("3. Módulo de Asignaciones");
            System.out.println("4. Módulo de Entregas");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    moduloPedidos(sc, listaPedidos);
                    break;
                case 2:
                    moduloRepartidores(sc, mapaRepartidores);
                    break;
                case 3:
                    moduloAsignaciones(sc, listaPedidos, mapaRepartidores, listaAsignaciones);
                    break;
                case 4:
                    moduloEntregas(sc, listaAsignaciones, listaEntregas);
                    break;
                case 5:
                    System.out.println("Cerrando sistema BRIDGES...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 5);
    }

    // ==================== MÓDULO PEDIDOS ====================
    private static void moduloPedidos(Scanner sc, ArrayList<PedidoBRIDGES> listaPedidos) {
        int opcion;
        do {
            System.out.println("\n--- MÓDULO DE PEDIDOS ---");
            System.out.println("1. Registrar Pedido");
            System.out.println("2. Mostrar Pedidos");
            System.out.println("3. Ordenar Pedidos por Código (Inserción)");
            System.out.println("4. Buscar Pedido por Código");
            System.out.println("5. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese código del pedido: ");
                    String codP = sc.nextLine();
                    System.out.print("Ingrese distancia (km): ");
                    double dist = sc.nextDouble();
                    System.out.print("Ingrese peso (kg): ");
                    double peso = sc.nextDouble();
                    sc.nextLine();
                    System.out.print("Ingrese prioridad (Normal/Urgente/Crítico): ");
                    String prioridad = sc.nextLine();

                    // Validar prioridad
                    if (!prioridad.equalsIgnoreCase("Normal") && 
                        !prioridad.equalsIgnoreCase("Urgente") && 
                        !prioridad.equalsIgnoreCase("Crítico")) {
                        System.out.println("Prioridad inválida. Use: Normal, Urgente o Crítico");
                        break;
                    }

                    PedidoBRIDGES nuevoPedido = new PedidoBRIDGES(codP, dist, peso, prioridad);
                    PedidoBRIDGES.registrarPedido(listaPedidos, nuevoPedido);
                    break;

                case 2:
                    PedidoBRIDGES.mostrarPedidos(listaPedidos);
                    break;

                case 3:
                    PedidoBRIDGES.ordenarPorCodigoInsercion(listaPedidos);
                    break;

                case 4:
                    System.out.print("Ingrese código del pedido a buscar: ");
                    String codigoBuscar = sc.nextLine();
                    boolean encontrado = false;
                    for (PedidoBRIDGES p : listaPedidos) {
                        if (p.getCodigo().equalsIgnoreCase(codigoBuscar)) {
                            System.out.println("Pedido encontrado:");
                            System.out.println("Código: " + p.getCodigo() +
                                               " | Distancia: " + p.getDistancia() + " km" +
                                               " | Peso: " + p.getPeso() + " kg" +
                                               " | Prioridad: " + p.getPrioridad());
                            encontrado = true;
                            break;
                        }
                    }
                    if (!encontrado) {
                        System.out.println("Pedido no encontrado.");
                    }
                    break;

                case 5:
                    System.out.println("Volviendo al menú principal...");
                    break;

                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 5);
    }

    // ==================== MÓDULO REPARTIDORES (CON HASHMAP) ====================
    private static void moduloRepartidores(Scanner sc, RepartidorBRIDGES.MapaRepartidores mapaRepartidores) {
        int opcion;
        do {
            System.out.println("\n--- MÓDULO DE REPARTIDORES (HASHMAP) ---");
            System.out.println("1. Registrar Repartidor");
            System.out.println("2. Mostrar Repartidores");
            System.out.println("3. Buscar Repartidor por Código (HashMap O(1))");
            System.out.println("4. Eliminar Repartidor");
            System.out.println("5. Mostrar Estadísticas del HashMap");
            System.out.println("6. Ordenar por Capacidad (Inserción)");
            System.out.println("7. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese código del repartidor: ");
                    String codR = sc.nextLine();
                    System.out.print("Ingrese nombre: ");
                    String nombre = sc.nextLine();
                    System.out.print("Ingrese capacidad de carga (kg): ");
                    double capacidad = sc.nextDouble();
                    sc.nextLine();
                    System.out.print("¿Está disponible? (true/false): ");
                    boolean disp = sc.nextBoolean();
                    sc.nextLine();

                    RepartidorBRIDGES nuevoRepartidor = new RepartidorBRIDGES(codR, nombre, capacidad, disp);
                    if (mapaRepartidores.insertar(nuevoRepartidor)) {
                        System.out.println("Repartidor registrado con éxito.");
                    }
                    break;

                case 2:
                    mapaRepartidores.mostrarTodos();
                    break;

                case 3:
                    System.out.print("Ingrese código del repartidor a buscar: ");
                    String codigoBuscar = sc.nextLine();
                    RepartidorBRIDGES repartidor = mapaRepartidores.buscarPorCodigo(codigoBuscar);
                    if (repartidor != null) {
                        System.out.println("✅ Repartidor encontrado (Búsqueda O(1)):");
                        System.out.println("Código: " + repartidor.getCodigo());
                        System.out.println("Nombre: " + repartidor.getNombre());
                        System.out.println("Capacidad: " + repartidor.getCapacidadCarga() + " kg");
                        System.out.println("Disponible: " + (repartidor.isDisponible() ? "Sí" : "No"));
                    } else {
                        System.out.println("❌ Repartidor no encontrado.");
                    }
                    break;

                case 4:
                    System.out.print("Ingrese código a eliminar: ");
                    String codigoEliminar = sc.nextLine();
                    if (mapaRepartidores.eliminar(codigoEliminar)) {
                        System.out.println("Repartidor eliminado con éxito.");
                    } else {
                        System.out.println("Repartidor no encontrado.");
                    }
                    break;

                case 5:
                    mapaRepartidores.mostrarEstadisticas();
                    break;

                case 6:
                    ArrayList<RepartidorBRIDGES> todos = mapaRepartidores.obtenerTodos();
                    RepartidorBRIDGES.ordenarPorCapacidadInsercion(todos);
                    System.out.println("\n--- REPARTIDORES ORDENADOS POR CAPACIDAD ---");
                    for (RepartidorBRIDGES r : todos) {
                        System.out.println("Código: " + r.getCodigo() + " | Nombre: " + r.getNombre() + 
                                         " | Capacidad: " + r.getCapacidadCarga() + "kg");
                    }
                    break;

                case 7:
                    System.out.println("Volviendo al menú principal...");
                    break;

                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 7);
    }

    // ==================== MÓDULO ASIGNACIONES ACTUALIZADO ====================
    private static void moduloAsignaciones(Scanner sc, ArrayList<PedidoBRIDGES> listaPedidos,
                                           RepartidorBRIDGES.MapaRepartidores mapaRepartidores,
                                           ArrayList<AsignacionBRIDGES> listaAsignaciones) {
        int opcion;
        do {
            System.out.println("\n--- MÓDULO DE ASIGNACIONES ---");
            System.out.println("1. Crear Asignación (Pedido → Repartidor)");
            System.out.println("2. Mostrar Asignaciones");
            System.out.println("3. Buscar Asignación por Pedido");
            System.out.println("4. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    if (listaPedidos.isEmpty() || mapaRepartidores.cantidadRepartidores() == 0) {
                        System.out.println("Debe haber al menos un pedido y un repartidor registrado.");
                        break;
                    }

                    System.out.println("\nPedidos disponibles:");
                    for (int i = 0; i < listaPedidos.size(); i++) {
                        System.out.println((i + 1) + ". " + listaPedidos.get(i).getCodigo());
                    }
                    System.out.print("Seleccione un pedido: ");
                    int idxPedido = sc.nextInt() - 1;

                    // ✅ Obtener repartidores disponibles del HashMap
                    ArrayList<RepartidorBRIDGES> repartidoresDisponibles = mapaRepartidores.obtenerDisponibles();
                    
                    System.out.println("\nRepartidores disponibles:");
                    for (int i = 0; i < repartidoresDisponibles.size(); i++) {
                        RepartidorBRIDGES r = repartidoresDisponibles.get(i);
                        System.out.println((i + 1) + ". " + r.getNombre() + 
                                         " - Capacidad: " + r.getCapacidadCarga() + "kg");
                    }

                    if (repartidoresDisponibles.isEmpty()) {
                        System.out.println("No hay repartidores disponibles.");
                        break;
                    }

                    System.out.print("Seleccione un repartidor: ");
                    int idxRepartidor = sc.nextInt() - 1;

                    if (idxPedido >= 0 && idxPedido < listaPedidos.size() &&
                        idxRepartidor >= 0 && idxRepartidor < repartidoresDisponibles.size()) {

                        PedidoBRIDGES pedidoSeleccionado = listaPedidos.get(idxPedido);
                        RepartidorBRIDGES repartidorSeleccionado = repartidoresDisponibles.get(idxRepartidor);

                        // Validar capacidad
                        if (pedidoSeleccionado.getPeso() > repartidorSeleccionado.getCapacidadCarga()) {
                            System.out.println("Error: El peso del pedido excede la capacidad del repartidor");
                            break;
                        }

                        AsignacionBRIDGES nueva = new AsignacionBRIDGES(pedidoSeleccionado, repartidorSeleccionado);
                        listaAsignaciones.add(nueva);
                        repartidorSeleccionado.setDisponible(false);
                        
                        System.out.println("Asignación creada con éxito.");
                    } else {
                        System.out.println("Selección inválida.");
                    }
                    break;

                // ... resto del código igual
            }
        } while (opcion != 4);

    // ==================== MÓDULO ASIGNACIONES ====================
    private static void moduloAsignaciones(Scanner sc, ArrayList<PedidoBRIDGES> listaPedidos,
                                           RepartidorBRIDGES.TablaHashRepartidores tablaRepartidores,
                                           ArrayList<AsignacionBRIDGES> listaAsignaciones) {
        int opcion;
        do {
            System.out.println("\n--- MÓDULO DE ASIGNACIONES ---");
            System.out.println("1. Crear Asignación (Pedido → Repartidor)");
            System.out.println("2. Mostrar Asignaciones");
            System.out.println("3. Buscar Asignación por Pedido");
            System.out.println("4. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    if (listaPedidos.isEmpty() || tablaRepartidores.obtenerTodos().isEmpty()) {
                        System.out.println("Debe haber al menos un pedido y un repartidor registrado.");
                        break;
                    }

                    System.out.println("\nPedidos disponibles:");
                    for (int i = 0; i < listaPedidos.size(); i++) {
                        System.out.println((i + 1) + ". " + listaPedidos.get(i).getCodigo() +
                                           " - " + listaPedidos.get(i).getPrioridad() +
                                           " - " + listaPedidos.get(i).getPeso() + "kg");
                    }
                    System.out.print("Seleccione un pedido: ");
                    int idxPedido = sc.nextInt() - 1;

                    // Obtener solo repartidores disponibles
                    ArrayList<RepartidorBRIDGES> repartidoresDisponibles = new ArrayList<>();
                    ArrayList<RepartidorBRIDGES> todosRepartidores = tablaRepartidores.obtenerTodos();
                    
                    System.out.println("\nRepartidores disponibles:");
                    int contador = 1;
                    for (int i = 0; i < todosRepartidores.size(); i++) {
                        RepartidorBRIDGES r = todosRepartidores.get(i);
                        if (r.isDisponible()) {
                            System.out.println(contador + ". " + r.getNombre() +
                                               " - Capacidad: " + r.getCapacidadCarga() + "kg" +
                                               " - Disponible: Sí");
                            repartidoresDisponibles.add(r);
                            contador++;
                        }
                    }

                    if (repartidoresDisponibles.isEmpty()) {
                        System.out.println("No hay repartidores disponibles.");
                        break;
                    }

                    System.out.print("Seleccione un repartidor: ");
                    int idxRepartidor = sc.nextInt() - 1;

                    if (idxPedido >= 0 && idxPedido < listaPedidos.size() &&
                        idxRepartidor >= 0 && idxRepartidor < repartidoresDisponibles.size()) {

                        PedidoBRIDGES pedidoSeleccionado = listaPedidos.get(idxPedido);
                        RepartidorBRIDGES repartidorSeleccionado = repartidoresDisponibles.get(idxRepartidor);

                        // Validar capacidad del repartidor
                        if (pedidoSeleccionado.getPeso() > repartidorSeleccionado.getCapacidadCarga()) {
                            System.out.println("Error: El peso del pedido (" + pedidoSeleccionado.getPeso() + 
                                             "kg) excede la capacidad del repartidor (" + 
                                             repartidorSeleccionado.getCapacidadCarga() + "kg)");
                            break;
                        }

                        AsignacionBRIDGES nueva = new AsignacionBRIDGES(pedidoSeleccionado, repartidorSeleccionado);
                        listaAsignaciones.add(nueva);
                        
                        // Cambiar estado del repartidor a no disponible
                        repartidorSeleccionado.setDisponible(false);
                        
                        System.out.println("Asignación creada con éxito. Repartidor marcado como ocupado.");
                    } else {
                        System.out.println("Selección inválida.");
                    }
                    break;

                case 2:
                    if (listaAsignaciones.isEmpty()) {
                        System.out.println("No hay asignaciones registradas.");
                    } else {
                        System.out.println("\n--- ASIGNACIONES REGISTRADAS ---");
                        for (AsignacionBRIDGES a : listaAsignaciones) {
                            a.mostrarAsignacion();
                        }
                    }
                    break;

                case 3:
                    System.out.print("Ingrese código del pedido a buscar: ");
                    String codigoPedido = sc.nextLine();
                    boolean encontrado = false;
                    for (AsignacionBRIDGES a : listaAsignaciones) {
                        if (a.getPedido().getCodigo().equalsIgnoreCase(codigoPedido)) {
                            System.out.println("Asignación encontrada:");
                            a.mostrarAsignacion();
                            encontrado = true;
                            break;
                        }
                    }
                    if (!encontrado) {
                        System.out.println("No se encontró asignación para ese pedido.");
                    }
                    break;

                case 4:
                    System.out.println("Volviendo al menú principal...");
                    break;

                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 4);
    }

    // ==================== MÓDULO ENTREGAS ====================
    private static void moduloEntregas(Scanner sc, ArrayList<AsignacionBRIDGES> listaAsignaciones,
                                      ArrayList<EntregaBRIDGES> listaEntregas) {
        int opcion;
        do {
            System.out.println("\n--- MÓDULO DE ENTREGAS ---");
            System.out.println("1. Registrar Entrega");
            System.out.println("2. Mostrar Entregas");
            System.out.println("3. Buscar Entrega por Pedido");
            System.out.println("4. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    if (listaAsignaciones.isEmpty()) {
                        System.out.println("No hay asignaciones para registrar entregas.");
                        break;
                    }
                    
                    System.out.println("\nAsignaciones disponibles:");
                    for (int i = 0; i < listaAsignaciones.size(); i++) {
                        AsignacionBRIDGES a = listaAsignaciones.get(i);
                        System.out.println((i + 1) + ". Pedido: " + a.getPedido().getCodigo() + 
                                         " | Repartidor: " + a.getRepartidor().getNombre() +
                                         " | Estado actual: " + a.getEstado());
                    }
                    
                    System.out.print("Seleccione una asignación: ");
                    int idxAsignacion = sc.nextInt() - 1;
                    sc.nextLine();
                    
                    if (idxAsignacion >= 0 && idxAsignacion < listaAsignaciones.size()) {
                        AsignacionBRIDGES asignacion = listaAsignaciones.get(idxAsignacion);
                        
                        System.out.println("Estado actual: " + asignacion.getEstado());
                        System.out.print("Ingrese nuevo estado (En camino, Entregado, Fallido): ");
                        String nuevoEstado = sc.nextLine();
                        
                        // Validar estado
                        if (!nuevoEstado.equalsIgnoreCase("En camino") && 
                            !nuevoEstado.equalsIgnoreCase("Entregado") && 
                            !nuevoEstado.equalsIgnoreCase("Fallido")) {
                            System.out.println("Estado inválido. Use: En camino, Entregado o Fallido");
                            break;
                        }
                        
                        // Actualizar estado de la asignación
                        asignacion.setEstado(nuevoEstado);
                        
                        // Si la entrega es final (Entregado o Fallido), liberar al repartidor
                        if (nuevoEstado.equalsIgnoreCase("Entregado") || 
                            nuevoEstado.equalsIgnoreCase("Fallido")) {
                            asignacion.getRepartidor().setDisponible(true);
                            System.out.println("Repartidor liberado y marcado como disponible.");
                        }
                        
                        // Crear registro de entrega
                        EntregaBRIDGES nuevaEntrega = new EntregaBRIDGES(
                            asignacion.getPedido(),
                            asignacion.getRepartidor(),
                            nuevoEstado
                        );
                        
                        listaEntregas.add(nuevaEntrega);
                        System.out.println("Entrega registrada con éxito.");
                    } else {
                        System.out.println("Selección inválida.");
                    }
                    break;

                case 2:
                    if (listaEntregas.isEmpty()) {
                        System.out.println("No hay entregas registradas.");
                    } else {
                        System.out.println("\n--- HISTORIAL DE ENTREGAS ---");
                        for (EntregaBRIDGES entrega : listaEntregas) {
                            entrega.mostrarEntrega();
                        }
                    }
                    break;

                case 3:
                    System.out.print("Ingrese código del pedido a buscar: ");
                    String codigoPedido = sc.nextLine();
                    boolean encontrado = false;
                    for (EntregaBRIDGES e : listaEntregas) {
                        if (e.getPedido().getCodigo().equalsIgnoreCase(codigoPedido)) {
                            System.out.println("Entrega encontrada:");
                            e.mostrarEntrega();
                            encontrado = true;
                            break;
                        }
                    }
                    if (!encontrado) {
                        System.out.println("No se encontró entrega para ese pedido.");
                    }
                    break;

                case 4:
                    System.out.println("Volviendo al menú principal...");
                    break;

                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 4);
    }
}