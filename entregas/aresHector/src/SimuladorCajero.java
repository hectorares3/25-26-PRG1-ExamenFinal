import java.util.Scanner;

public class SimuladorCajero {

    private static final double SALDO_INICIAL_DEFECTO = 1000.0;
    private static final double COMISION_RETIRO = 1.0;
    private static final double LIMITE_DIARIO = 600.0;
    private static final int MAX_HISTORIAL = 10;

  
    private static final int IDX_SALDO = 0;
    private static final int IDX_RET_HOY = 1;
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

    
      
        double[] estadoCuenta = { SALDO_INICIAL_DEFECTO, 0.0 }; 
        
        
        double[] estadisticas = { 0.0, 0.0, 0.0 };
        
       
        int[] contadores = { 0, 0 };

        String[] historialTipos = new String[MAX_HISTORIAL];
        double[] historialMontos = new double[MAX_HISTORIAL];

        boolean sesionActiva = true;

        System.out.println("--- CAJERO ESTRUCTURADO v2 (Arrays) ---");
        System.out.printf("Saldo inicial: %.2f EUR%n", estadoCuenta[IDX_SALDO]);

        while (sesionActiva) {
            System.out.println("\nSELECCIONE OPCION:");
            System.out.println("[1] Ver Saldo  | [2] Retirar      | [3] Depositar");
            System.out.println("[4] Estadisticas | [5] Salir        | [6] Ver Movimientos");
            System.out.println("[7] Borrar Hist  | [0] Siguiente Dia");
            System.out.print(">> ");
            
            if (!sc.hasNextInt()) {
                sc.next(); continue;
            }
            int opcion = sc.nextInt();

            if (opcion == 1) {
                
                System.out.printf("Saldo actual: %.2f EUR%n", estadoCuenta[IDX_SALDO]);
            
            } else if (opcion == 2) {
                System.out.print("Cantidad a retirar: ");
                double cantidad = sc.nextDouble();
                
                
                if (cantidad <= 0) {
                    System.out.println("Cantidad inválida.");
                } else if (estadoCuenta[IDX_RET_HOY] + cantidad > LIMITE_DIARIO) {
                    System.out.println("Error: Límite diario excedido.");
                } else if (estadoCuenta[IDX_SALDO] < cantidad + COMISION_RETIRO) {
                    System.out.println("Error: Saldo insuficiente.");
                } else {
                    
                    estadoCuenta[IDX_SALDO] -= (cantidad + COMISION_RETIRO);
                    estadoCuenta[IDX_RET_HOY] += cantidad;
                    estadisticas[0] += cantidad; 
                    estadisticas[2] += COMISION_RETIRO; 
                    contadores[0]++; 
                    
                    
                    int idx = contadores[1]; 
                    if (idx < MAX_HISTORIAL) {
                        historialTipos[idx] = "Retiro";
                        historialMontos[idx] = cantidad;
                        contadores[1]++; 
                    }
                    System.out.printf("Retiro exitoso. Nuevo saldo: %.2f EUR%n", estadoCuenta[IDX_SALDO]);
                }

            } else if (opcion == 3) {
                System.out.print("Cantidad a depositar: ");
                double cantidad = sc.nextDouble();
                
                if (cantidad > 0) {
                    estadoCuenta[IDX_SALDO] += cantidad;
                    estadisticas[1] += cantidad; 
                    contadores[0]++;
                    
                    int idx = contadores[1];
                    if (idx < MAX_HISTORIAL) {
                        historialTipos[idx] = "Deposito";
                        historialMontos[idx] = cantidad;
                        contadores[1]++;
                    }
                    System.out.printf("Depósito exitoso. Nuevo saldo: %.2f EUR%n", estadoCuenta[IDX_SALDO]);
                } else {
                    System.out.println("Cantidad inválida.");
                }

            } else if (opcion == 4) {
                System.out.println("--- ESTADISTICAS ---");
                System.out.println("Operaciones: " + contadores[0]);
                System.out.printf("Retirado Total: %.2f%n", estadisticas[0]);
                System.out.printf("Depositado Total: %.2f%n", estadisticas[1]);

            } else if (opcion == 5) {
                sesionActiva = false;

            } else if (opcion == 6) {
                System.out.println("--- ULTIMOS MOVIMIENTOS ---");
                for (int i = 0; i < contadores[1]; i++) {
                    System.out.printf("%d. %s: %.2f EUR%n", (i+1), historialTipos[i], historialMontos[i]);
                }

            } else if (opcion == 7) {
                contadores[1] = 0; 
                System.out.println("Historial borrado.");

            } else if (opcion == 0) {
                estadoCuenta[IDX_RET_HOY] = 0.0; 
                System.out.println("Día avanzado.");
            }
        }
        sc.close();
    }
}