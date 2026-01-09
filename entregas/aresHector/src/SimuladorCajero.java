import java.util.Scanner;

public class SimuladorCajero {

    
    private static final double SALDO_INICIAL_PREDETERMINADO = 1000.0;
    private static final double COMISION_POR_RETIRO = 1.0;
    private static final double LIMITE_DIARIO_RETIRO = 600.0;
    private static final int CAPACIDAD_MAXIMA_HISTORIAL = 10;
    
    
    private static final int INDICE_SALDO_ACTUAL = 0;
    private static final int INDICE_RETIRO_ACUMULADO_HOY = 1;
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        
        double[] estadoCuenta = { SALDO_INICIAL_PREDETERMINADO, 0.0 };
        double[] estadisticas = { 0.0, 0.0, 0.0 }; 
        int[] contadores = { 0, 0 }; 
        
        String[] hTipos = new String[CAPACIDAD_MAXIMA_HISTORIAL];
        double[] hMontos = new double[CAPACIDAD_MAXIMA_HISTORIAL];

        boolean sesionActiva = true;

        System.out.println("--- COMMIT 4: CONSTANTES CLEAN CODE ---");

        while (sesionActiva) {
            mostrarMenu();
            int opcion = leerOpcion(sc);

            if (opcion == 1) {
                
                System.out.printf("Saldo actual: %.2f EUR%n", estadoCuenta[INDICE_SALDO_ACTUAL]);
            } else if (opcion == 2) {
                procesarRetiro(sc, estadoCuenta, estadisticas, contadores, hTipos, hMontos);
            } else if (opcion == 3) {
                procesarDeposito(sc, estadoCuenta, estadisticas, contadores, hTipos, hMontos);
            } else if (opcion == 4) {
                mostrarEstadisticas(estadisticas, contadores);
            } else if (opcion == 5) {
                System.out.println("Saliendo...");
                sesionActiva = false;
            } else if (opcion == 6) {
                mostrarHistorial(contadores, hTipos, hMontos);
            } else if (opcion == 7) {
                contadores[1] = 0;
                System.out.println("Historial borrado.");
            } else if (opcion == 0) {
                
                estadoCuenta[INDICE_RETIRO_ACUMULADO_HOY] = 0.0;
                System.out.println("Día avanzado.");
            } else {
                System.out.println("Opción no válida.");
            }
        }
        sc.close();
    }

    
    private static void mostrarMenu() {
        System.out.println("\n[1] Saldo | [2] Retiro | [3] Depósito | [4] Stats | [5] Salir | [6] Historial");
        System.out.print(">> ");
    }
    private static int leerOpcion(Scanner sc) {
        if (sc.hasNextInt()) return sc.nextInt();
        sc.next(); return -1;
    }
    private static void mostrarEstadisticas(double[] stats, int[] conts) {
        System.out.println("Ops: " + conts[0] + " | Ret: " + stats[0] + " | Dep: " + stats[1]);
    }
    private static void mostrarHistorial(int[] conts, String[] hT, double[] hM) {
        System.out.println("--- HISTORIAL ---");
        for (int i = 0; i < conts[1]; i++) System.out.printf("%d. %s: %.2f%n", (i+1), hT[i], hM[i]);
    }
    private static void registrarHistorial(String tipo, double monto, int[] conts, String[] hT, double[] hM) {
        int idx = conts[1];
        if (idx == CAPACIDAD_MAXIMA_HISTORIAL) { 
            for (int i = 0; i < CAPACIDAD_MAXIMA_HISTORIAL - 1; i++) {
                hT[i] = hT[i+1];
                hM[i] = hM[i+1];
            }
            idx--;
        }
        hT[idx] = tipo;
        hM[idx] = monto;
        if (conts[1] < CAPACIDAD_MAXIMA_HISTORIAL) conts[1]++;
    }

    private static void procesarRetiro(Scanner sc, double[] cuenta, double[] stats, int[] conts, String[] hT, double[] hM) {
        System.out.print("Cantidad a retirar: ");
        double cr = sc.nextDouble();
        
        if (cr > 0 && cuenta[INDICE_RETIRO_ACUMULADO_HOY] + cr <= LIMITE_DIARIO_RETIRO && cuenta[INDICE_SALDO_ACTUAL] >= cr + COMISION_POR_RETIRO) {
            cuenta[INDICE_SALDO_ACTUAL] -= (cr + COMISION_POR_RETIRO);
            cuenta[INDICE_RETIRO_ACUMULADO_HOY] += cr;
            stats[0] += cr;
            stats[2] += COMISION_POR_RETIRO;
            conts[0]++;
            registrarHistorial("Retiro", cr, conts, hT, hM);
            System.out.println("Retiro OK.");
        } else {
            System.out.println("Error: Fondos insuficientes o límite excedido.");
        }
    }

    private static void procesarDeposito(Scanner sc, double[] cuenta, double[] stats, int[] conts, String[] hT, double[] hM) {
        System.out.print("Cantidad a depositar: ");
        double cd = sc.nextDouble();
        if (cd > 0) {
            cuenta[INDICE_SALDO_ACTUAL] += cd; 
            stats[1] += cd;
            conts[0]++;
            registrarHistorial("Depósito", cd, conts, hT, hM);
            System.out.println("Depósito OK.");
        } else {
            System.out.println("Error: Cantidad inválida.");
        }
    }
}