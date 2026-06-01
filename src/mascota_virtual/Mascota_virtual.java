package mascota_virtual;

import java.util.Scanner;

public class Mascota_virtual {

    public static void main(String[] args) {

        Scanner teclado = new Scanner(System.in);

        System.out.println("=================================");
        System.out.println("      🐾 MASCOTA VIRTUAL 🐾");
        System.out.println("=================================");

        System.out.print("Ingrese el nombre de su mascota: ");
        String nombre = teclado.nextLine();

        Mascota mascota = new Mascota(nombre);

        System.out.println("\n🎉 ¡Bienvenido al juego!");
        System.out.println("Ahora cuidarás de " + nombre + ".");

        Juego juego = new Juego(mascota);

        juego.iniciar();
    }
}






	