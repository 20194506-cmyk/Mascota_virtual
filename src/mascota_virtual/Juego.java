package mascota_virtual;

import java.util.Scanner;

public class Juego {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("¡Bienvenido al Simulador de Mascota Virtual!");
        System.out.print("Para empezar, ¿cómo se llama tu mascota?: ");

        String nombre = sc.nextLine();

        // Crear objeto de la clase Mascota
        Mascota miMascota = new Mascota(nombre, nombre, 0);

        boolean jugando = true;

        // Ciclo while
        while (jugando) {

            // Menú del juego
            System.out.println("\n--- MENÚ DEL JUEGO ---");
            System.out.println("1. Mostrar estado de la mascota");
            System.out.println("2. Alimentar");
            System.out.println("3. Jugar");
            System.out.println("4. Dormir");
            System.out.println("5. Salir del juego");
            System.out.print("Elige una opción (1-5): ");

            int opcion = sc.nextInt();

            // Opciones
            switch (opcion) {

                case 1:
                    miMascota.mostrarEstado();
                    break;

                case 2:
                    miMascota.alimentar();
                    break;

                case 3:
                    miMascota.jugar();
                    break;

                case 4:
                    miMascota.dormir();
                    break;

                case 5:
                    System.out.println("\n¡Gracias por jugar! "
                            + miMascota.getNombre()
                            + " te va a extrañar.");

                    jugando = false;
                    break;

                default:
                    System.out.println("\n[Error] Opción no válida.");
            }
        }

        sc.close();
    }
}
