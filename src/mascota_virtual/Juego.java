package mascota_virtual;

import java.util.Scanner;

public class Juego {

    private Mascota mascota;
    private Scanner teclado;

    public Juego(Mascota mascota) {
        this.mascota = mascota;
        teclado = new Scanner(System.in);
    }

    // Iniciar juego
    public void iniciar() {

        int opcion;

        do {

            mostrarEstado();
            mostrarMenu();

            opcion = teclado.nextInt();

            switch (opcion) {

                case 1:
                    mascota.comer();
                    mascota.pasarTiempo();
                    break;

                case 2:
                    mascota.jugar();
                    mascota.pasarTiempo();
                    break;

                case 3:
                    mascota.dormir();
                    mascota.pasarTiempo();
                    break;

                case 4:
                    System.out.println("\n👋 Gracias por jugar.");
                    break;

                default:
                    System.out.println("\n❌ Opción inválida.");
            }

            verificarEstado();

        } while (opcion != 4);
    }

    // Menú
    public void mostrarMenu() {

        System.out.println("\n========= MENÚ =========");
        System.out.println("1. Alimentar");
        System.out.println("2. Jugar");
        System.out.println("3. Dormir");
        System.out.println("4. Salir");
        System.out.print("Seleccione una opción: ");
    }

    // Estado mascota
    public void mostrarEstado() {

        System.out.println("\n==============================");
        System.out.println("🐾 MASCOTA: " + mascota.getNombre());
        System.out.println("==============================");
        System.out.println("🍖 Hambre: " + mascota.getHambre() + "%");
        System.out.println("⚡ Energía: " + mascota.getEnergia() + "%");
        System.out.println("😊 Felicidad: " + mascota.getFelicidad() + "%");
    }

    // Alertas
    public void verificarEstado() {

        if (mascota.getHambre() >= 80) {
            System.out.println("⚠ Tu mascota tiene mucha hambre.");
        }

        if (mascota.getEnergia() <= 20) {
            System.out.println("⚠ Tu mascota está muy cansada.");
        }

        if (mascota.getFelicidad() <= 20) {
            System.out.println("⚠ Tu mascota está triste.");
        }

        if (mascota.getHambre() >= 100) {
            System.out.println("\n💀 Fin del juego.");
            System.out.println("Tu mascota sufrió por exceso de hambre.");
            System.exit(0);
        }
    }
}
