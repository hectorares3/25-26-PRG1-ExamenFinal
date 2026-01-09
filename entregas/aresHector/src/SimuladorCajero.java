import java.util.Scanner;

public class SimuladorCajero {

    
    private static final double SALDO_INICIAL_PREDETERMINADO = 1000.0;
    private static final double COMISION_POR_RETIRO = 1.0;
    private static final double LIMITE_DIARIO_RETIRO = 600.0;
    private static final int CAPACIDAD_MAXIMA_HISTORIAL = 10;
    
    
    private static final int INDICE_SALDO_ACTUAL = 0;
    private static final int INDICE_RETIRO_ACUMULADO_HOY = 1;
    private static final int INDICE_TOTAL_RETIRADO = 0;
    private static final int INDICE_TOTAL_DEPOSITADO = 1;
    private static final int INDICE_TOTAL_COMISIONES = 2;
    private static final int INDICE_CANTIDAD_OPERACIONES = 0;
    private static final int INDICE_CANTIDAD_HISTORIAL = 1;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        
        double[] estadoFinanciero = { SALDO_INICIAL_PREDETERMINADO, 0.0 };
        double[] estadisticasGlobales = { 0.0, 0.0, 0.0 }; 
        int[] contadoresSistema = { 0, 0 }; 
        
        String[] historialTiposMovimiento = new String[CAPACIDAD_MAXIMA_HISTORIAL];
        double[] historialMontosMovimiento = new double[CAPACIDAD_MAXIMA_HISTORIAL];

        boolean sesionActiva = true;

        System.out.println("--- COMMIT 5: FINAL CLEAN CODE ---");

        while (sesionActiva) {
            mostrarMenuPrincipal();
            int opcionSeleccionada = leerOpcionUsuario(scanner); 

            if (opcionSeleccionada == 1) {
                System.out.printf("Saldo disponible: %.2f EUR%n", estadoFinanciero[INDICE_SALDO_ACTUAL]);
            } else if (opcionSeleccionada == 2) {
                ejecutarProcesoRetiro(scanner, estadoFinanciero, estadisticasGlobales, contadoresSistema, historialTiposMovimiento, historialMontosMovimiento);
            } else if (opcionSeleccionada == 3) {
                ejecutarProcesoDeposito(scanner, estadoFinanciero, estadisticasGlobales, contadoresSistema, historialTiposMovimiento, historialMontosMovimiento);
            } else if (opcionSeleccionada == 4) {
                mostrarEstadisticasSesion(estadisticasGlobales, contadoresSistema);
            } else if (opcionSeleccionada == 5) {
                System.out.println("Cerrando sesion segura. Hasta pronto.");
                sesionActiva = false;
            } else if (opcionSeleccionada == 6) {
                mostrarHistorialMovimientos(contadoresSistema, historialTiposMovimiento, historialMontosMovimiento);
            } else if (opcionSeleccionada == 7) {
                contadoresSistema[INDICE_CANTIDAD_HISTORIAL] = 0;
                System.out.println("Historial de movimientos eliminado.");
            } else if (opcionSeleccionada == 0) {
                estadoFinanciero[INDICE_RETIRO_ACUMULADO_HOY] = 0.0;
                System.out.println("Fecha avanzada. Limite diario restaurado.");
            } else {
                System.out.println("Error: Opcion no valida.");
            }
        }
        scanner.close();
    }

    
    private static void mostrarMenuPrincipal() {
        System.out.println("\n[1] Consultar Saldo | [2] Retirar Efectivo | [3] Depositar Efectivo");
        System.out.println("[4] Ver Estadisticas | [5] Salir | [6] Ver Movimientos");
        System.out.println("[7] Borrar Historial | [0] Avanzar Dia");
        System.out.print(">> ");
    }

    private static int leerOpcionUsuario(Scanner scanner) {
        if (scanner.hasNextInt()) return scanner.nextInt();
        scanner.next(); return -1;
    }

    private static void mostrarEstadisticasSesion(double[] estadisticas, int[] contadores) {
        System.out.println("--- ESTADISTICAS DE LA SESION ---");
        System.out.printf("Operaciones: %d%n", contadores[INDICE_CANTIDAD_OPERACIONES]);
        System.out.printf("Retirado: %.2f | Depositado: %.2f | Comisiones: %.2f%n", 
            estadisticas[INDICE_TOTAL_RETIRADO], estadisticas[INDICE_TOTAL_DEPOSITADO], estadisticas[INDICE_TOTAL_COMISIONES]);
    }

    private static void mostrarHistorialMovimientos(int[] contadores, String[] tipos, double[] montos) {
        System.out.println("--- ULTIMOS MOVIMIENTOS ---");
        for (int i = 0; i < contadores[INDICE_CANTIDAD_HISTORIAL]; i++) {
            System.out.printf("%d. %s: %.2f EUR%n", (i+1), tipos[i], montos[i]);
        }
    }

    private static void registrarNuevoMovimiento(String tipoMovimiento, double montoMovimiento, int[] contadores, String[] tipos, double[] montos) {
        int indiceActual = contadores[INDICE_CANTIDAD_HISTORIAL];
        
        if (indiceActual == CAPACIDAD_MAXIMA_HISTORIAL) {
            for (int i = 0; i < CAPACIDAD_MAXIMA_HISTORIAL - 1; i++) {
                tipos[i] = tipos[i+1];
                montos[i] = montos[i+1];
            }
            indiceActual--;
        }
        
        tipos[indiceActual] = tipoMovimiento;
        montos[indiceActual] = montoMovimiento;
        
        if (contadores[INDICE_CANTIDAD_HISTORIAL] < CAPACIDAD_MAXIMA_HISTORIAL) {
            contadores[INDICE_CANTIDAD_HISTORIAL]++;
        }
    }

    
    private static void ejecutarProcesoRetiro(Scanner scanner, double[] estado, double[] estadisticas, int[] contadores, String[] tipos, double[] montos) {
        System.out.print("Ingrese cantidad a retirar: ");
        double cantidadRetiro = scanner.nextDouble(); 
        double costoTotal = cantidadRetiro + COMISION_POR_RETIRO;

        if (cantidadRetiro <= 0) {
            System.out.println("Error: La cantidad debe ser positiva.");
        } else if (estado[INDICE_RETIRO_ACUMULADO_HOY] + cantidadRetiro > LIMITE_DIARIO_RETIRO) {
            System.out.println("Error: Supera el limite diario.");
        } else if (estado[INDICE_SALDO_ACTUAL] < costoTotal) {
            System.out.println("Error: Fondos insuficientes.");
        } else {
            estado[INDICE_SALDO_ACTUAL] -= costoTotal;
            estado[INDICE_RETIRO_ACUMULADO_HOY] += cantidadRetiro;
            estadisticas[INDICE_TOTAL_RETIRADO] += cantidadRetiro;
            estadisticas[INDICE_TOTAL_COMISIONES] += COMISION_POR_RETIRO;
            contadores[INDICE_CANTIDAD_OPERACIONES]++;
            
            registrarNuevoMovimiento("Retiro", cantidadRetiro, contadores, tipos, montos);
            System.out.printf("Retiro exitoso. Nuevo saldo: %.2f EUR%n", estado[INDICE_SALDO_ACTUAL]);
        }
    }

    private static void ejecutarProcesoDeposito(Scanner scanner, double[] estado, double[] estadisticas, int[] contadores, String[] tipos, double[] montos) {
        System.out.print("Ingrese cantidad a depositar: ");
        double cantidadDeposito = scanner.nextDouble(); 

        if (cantidadDeposito > 0) {
            estado[INDICE_SALDO_ACTUAL] += cantidadDeposito;
            estadisticas[INDICE_TOTAL_DEPOSITADO] += cantidadDeposito;
            contadores[INDICE_CANTIDAD_OPERACIONES]++;
            
            registrarNuevoMovimiento("Depósito", cantidadDeposito, contadores, tipos, montos);
            System.out.printf("Depósito exitoso. Nuevo saldo: %.2f EUR%n", estado[INDICE_SALDO_ACTUAL]);
        } else {
            System.out.println("Error: La cantidad debe ser positiva.");
        }
    }
}