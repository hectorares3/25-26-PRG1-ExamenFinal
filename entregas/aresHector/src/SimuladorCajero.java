import java.util.Scanner;

public class SimuladorCajero {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        double s = 1000.0; 
        double si = s;
        int n = 0; 
        double tr = 0; 
        double td = 0; 
        
        
        double com = 1.0; 
        double lim_d = 600.0; 
        double r_hoy = 0.0; 
        double com_tot = 0.0; 
        
        
        String[] hist_t = new String[10]; 
        double[] hist_m = new double[10]; 
        int hist_idx = 0;

        System.out.println("Cajero Automatico - Banco Nacional");
        System.out.println("Saldo inicial: " + s + " euros");

        while (true) {
            System.out.println();
            System.out.println("[1] Consultar saldo");
            System.out.println("[2] Retirar dinero (comision: " + com + " eur)");
            System.out.println("[3] Depositar dinero");
            System.out.println("[4] Ver estadisticas");
            System.out.println("[5] Salir");
            System.out.println("[6] Ver ultimos movimientos");
            System.out.println("[7] Borrar historial");
            System.out.println("[0] Avanzar al siguiente dia (reinicia limite)");
            System.out.print("Seleccione opcion: ");
            int o = sc.nextInt();

            if (o == 1) {
                System.out.println("Tu saldo actual es: " + s + " euros");
            } else if (o == 2) {
                System.out.print("Cuanto deseas retirar? ");
                double c = sc.nextDouble();
                if (c <= 0) {
                    System.out.println("Cantidad invalida.");
                } else if (r_hoy + c > lim_d) {
                    System.out.println("ERROR: Has superado tu limite de retiro diario de " + lim_d + " euros.");
                    System.out.println("Has retirado hoy: " + r_hoy + " euros.");
                } else if (s < c + com) {
                    System.out.println("Saldo insuficiente (necesitas " + (c + com) + " euros).");
                } else {
                    s = s - (c + com);
                    tr = tr + c;
                    r_hoy = r_hoy + c;
                    com_tot = com_tot + com;
                    n++;
                    System.out.println("Operacion exitosa. Nuevo saldo: " + s + " euros");
                    if (hist_idx < 10) {
                        hist_t[hist_idx] = "Retiro";
                        hist_m[hist_idx] = c;
                        hist_idx++;
                    }
                }
            } else if (o == 3) {
                System.out.print("Cuanto deseas depositar? ");
                double c = sc.nextDouble();
                if (c > 0) {
                    s = s + c;
                    
                    n++;
                    System.out.println("Operacion exitosa. Nuevo saldo: " + s + " euros");
                    if (hist_idx < 10) {
                        hist_t[hist_idx] = "Deposito";
                        hist_m[hist_idx] = c;
                        hist_idx++;
                    }
                } else {
                    System.out.println("Cantidad invalida");
                }
            } else if (o == 4) {
                System.out.println();
                System.out.println("Estadisticas de tu cuenta:");
                System.out.println("- Operaciones realizadas: " + n);
                System.out.println("- Total retirado: " + tr + " euros");
                System.out.println("- Total depositado: " + td + " euros");
                System.out.println("- Total comisiones: " + com_tot + " euros");
                System.out.println("- Saldo neto (operaciones): " + (td - tr) + " euros");
                System.out.println("- Saldo inicial era: " + si + " euros");
                if (s == si + td - tr - com_tot) {
                    System.out.println("- Estado de cuenta: CORRECTO");
                } else {
                    System.out.println("- Estado de cuenta: INCONSISTENTE");
                }
            } else if (o == 5) {
                System.out.println();
                System.out.println("Gracias por usar nuestro cajero automatico");
                System.out.println("Hasta pronto!");
                break;
            }
            else if (o == 6) {
                System.out.println();
                System.out.println("Ultimos Movimientos");
                if (hist_idx == 0) {
                    System.out.println("(No hay movimientos recientes)");
                } else {
                    for (int i = 0; i < hist_idx; i++) {
                        System.out.println((i+1) + ". " + hist_t[i] + ": " + hist_m[i] + " euros");
                    }
                }
            } else if (o == 7) {
                hist_idx = 0;
                System.out.println("Historial de movimientos borrado.");
            } else if (o == 0) {
                r_hoy = 0.0;
                System.out.println("Simulando avance de dia. Tu limite de retiro se ha reiniciado.");
            } else {
                System.out.println("Opcion no valida. Intenta de nuevo.");
            }
        }
        sc.close();
    }
}