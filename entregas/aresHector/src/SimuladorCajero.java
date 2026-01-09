import java.util.Scanner;

public class SimuladorCajero {

    
    private static final double SALDO_INICIAL_DEFECTO = 1000.0;
    private static final double COMISION_RETIRO = 1.0;
    private static final double LIMITE_DIARIO = 600.0;
    private static final int MAX_HISTORIAL = 10;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        
        double saldoActual = SALDO_INICIAL_DEFECTO;
        double saldoInicialDia = SALDO_INICIAL_DEFECTO;
        
        double retiradoHoy = 0.0;
        double totalRetirado = 0.0;
        double totalDepositado = 0.0;
        double totalComisiones = 0.0;
        int contadorOperaciones = 0;

        
        String[] historialTipos = new String[MAX_HISTORIAL];
        double[] historialMontos = new double[MAX_HISTORIAL];
        int indiceHistorial = 0;

        boolean sesionActiva = true; 

        System.out.println("--- CAJERO ESTRUCTURADO v1 ---");
        System.out.printf("Saldo inicial: %.2f EUR%n", saldoActual);

        while (sesionActiva) {
            System.out.println("\nSELECCIONE OPCION:");
            System.out.println("[1] Ver Saldo  | [2] Retirar      | [3] Depositar");
            System.out.println("[4] Estadisticas | [5] Salir        | [6] Ver Movimientos");
            System.out.println("[7] Borrar Hist  | [0] Siguiente Dia");
            System.out.print(">> ");
            
            
            if (!sc.hasNextInt()) {
                sc.next(); 
                System.out.println("Error: Entrada no numérica.");
                continue; 
            }
            int opcion = sc.nextInt();

          
            if (opcion == 1) {
                System.out.printf("Saldo actual: %.2f EUR%n", saldoActual);
            
            } else if (opcion == 2) {
                System.out.print("Cantidad a retirar: ");
                double cantidad = sc.nextDouble();
                
                if (cantidad <= 0) {
                    System.out.println("Cantidad inválida.");
                } else if (retiradoHoy + cantidad > LIMITE_DIARIO) {
                    System.out.println("Error: Límite diario excedido.");
                } else if (saldoActual < cantidad + COMISION_RETIRO) {
                    System.out.println("Error: Saldo insuficiente.");
                } else {
                    saldoActual -= (cantidad + COMISION_RETIRO);
                    retiradoHoy += cantidad;
                    totalRetirado += cantidad;
                    totalComisiones += COMISION_RETIRO;
                    contadorOperaciones++;
                    
                   
                    if (indiceHistorial < MAX_HISTORIAL) {
                        historialTipos[indiceHistorial] = "Retiro";
                        historialMontos[indiceHistorial] = cantidad;
                        indiceHistorial++;
                    }
                    System.out.printf("Retiro exitoso. Nuevo saldo: %.2f EUR%n", saldoActual);
                }

            } else if (opcion == 3) {
                System.out.print("Cantidad a depositar: ");
                double cantidad = sc.nextDouble();
                
                if (cantidad > 0) {
                    saldoActual += cantidad;
                    totalDepositado += cantidad; 
                    contadorOperaciones++;
                    
                    if (indiceHistorial < MAX_HISTORIAL) {
                        historialTipos[indiceHistorial] = "Deposito";
                        historialMontos[indiceHistorial] = cantidad;
                        indiceHistorial++;
                    }
                    System.out.printf("Depósito exitoso. Nuevo saldo: %.2f EUR%n", saldoActual);
                } else {
                    System.out.println("Cantidad inválida.");
                }

            } else if (opcion == 4) {
                System.out.println("--- ESTADISTICAS ---");
                System.out.println("Operaciones: " + contadorOperaciones);
                System.out.printf("Retirado Total: %.2f%n", totalRetirado);
                System.out.printf("Depositado Total: %.2f%n", totalDepositado);
                System.out.printf("Comisiones: %.2f%n", totalComisiones);

            } else if (opcion == 5) {
                System.out.println("Cerrando sesión...");
                sesionActiva = false; 

            } else if (opcion == 6) {
                System.out.println("--- ULTIMOS MOVIMIENTOS ---");
                for (int i = 0; i < indiceHistorial; i++) {
                    System.out.printf("%d. %s: %.2f EUR%n", (i+1), historialTipos[i], historialMontos[i]);
                }

            } else if (opcion == 7) {
                indiceHistorial = 0;
                System.out.println("Historial borrado.");

            } else if (opcion == 0) {
                retiradoHoy = 0.0;
                System.out.println("Día avanzado. Límite restaurado.");

            } else {
                System.out.println("Opción no reconocida.");
            }
        }
        sc.close();
    }
}