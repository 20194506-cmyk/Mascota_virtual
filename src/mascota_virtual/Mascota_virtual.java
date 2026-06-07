package mascota_virtual;

import java.util.Scanner;

public class Mascota_virtual {
    public static void main(String[] args) {
        Juego juego = new Juego();
        juego.iniciar();
        System.out.println("\nPresiona ENTER para salir del codigo...");
        Scanner pausa = new Scanner(System.in);
        pausa.nextLine();
        pausa.close();
        Scanner sc = new Scanner(System.in);
		sc.close();	
    }
}