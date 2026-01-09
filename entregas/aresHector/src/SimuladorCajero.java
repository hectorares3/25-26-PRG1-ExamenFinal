import java.util.Scanner;

public class SimuladorCajero {

    
    private static final double SALDO_INICIAL_DEFECTO = 1000.0;
    private static final double COMISION_RETIRO = 1.0;
    private static final double LIMITE_DIARIO = 600.0;
    private static final int MAX_HISTORIAL = 10;
    
    
    private static final int I_SALDO = 0;
    private static final int I_RET_HOY = 1;
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        
        double[] estadoCuenta = { SALDO_INICIAL_DEFECTO, 0.0 };
        double[] estadisticas = { 0.0, 0.0, 0.0 }; 
        int[] contadores = { 0, 0 }; 
        
        String[] hTipos = new String[MAX_HISTORIAL];
        double[] hMontos = new double[MAX_HISTORIAL];

        boolean sesionActiva = true;

        System.out.println("--- PASO 3.1: HELPER HISTORIAL ---");

        while (sesionActiva) {
            mostrarMenu();
            int opcion = leerOpcion(sc);

            switch (opcion) {
                case 1:
                    System.out.printf("Saldo actual: %.2f EUR%n", estadoCuenta[I_SALDO]);
                    break;
                case 2:
                    
                    System.out.print("Cantidad a retirar: ");
                    double cr = sc.nextDouble();
                    if (cr > 0 && estadoCuenta[I_RET_HOY] + cr <= LIMITE_DIARIO && estadoCuenta[I_SALDO] >= cr + COMISION_RETIRO) {
                        estadoCuenta[I_SALDO] -= (cr + COMISION_RETIRO);
                        estadoCuenta[I_RET_HOY] += cr;
                        estadisticas[0] += cr;
                        estadisticas[2] += COMISION_RETIRO;
                        contadores[0]++;
                        
                        
                        registrarHistorial("Retiro", cr, contadores, hTipos, hMontos);
                        System.out.println("Retiro OK.");
                    } else {
                        System.out.println("Error en retiro.");
                    }
                    break;
                case 3:
                    
                    System.out.print("Cantidad a depositar: ");
                    double cd = sc.nextDouble();
                    if (cd > 0) {
                        estadoCuenta[I_SALDO] += cd;
                        estadisticas[1] += cd;
                        contadores[0]++;
                        
                        
                        registrarHistorial("Depósito", cd, contadores, hTipos, hMontos);
                        System.out.println("Depósito OK.");
                    } else {
                        System.out.println("Error en depósito.");
                    }
                    break;
                case 4:
                    mostrarEstadisticas(estadisticas, contadores);
                    break;
                case 5:
                    System.out.println("Saliendo...");
                    sesionActiva = false;
                    break;
                case 6:
                    mostrarHistorial(contadores, hTipos, hMontos);
                    break;
                case 7:
                    contadores[1] = 0;
                    System.out.println("Historial borrado.");
                    break;
                case 0:
                    estadoCuenta[I_RET_HOY] = 0.0;
                    System.out.println("Día avanzado.");
                    break;
                default:
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
        if (idx == MAX_HISTORIAL) { 
            for (int i = 0; i < MAX_HISTORIAL - 1; i++) {
                hT[i] = hT[i+1];
                hM[i] = hM[i+1];
            }
            idx--;
        }
        hT[idx] = tipo;
        hM[idx] = monto;
        if (conts[1] < MAX_HISTORIAL) conts[1]++;
    }
}