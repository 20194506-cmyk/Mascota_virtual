package mascota_virtual;

import java.util.Scanner;
import java.util.ArrayList;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Juego {

    private Mascota mascota;
    private Scanner scanner;
    private ArrayList<String> registro;

    private static final String RESET    = "\u001B[0m";
    private static final String MAGENTA  = "\u001B[38;5;201m";
    private static final String CIAN     = "\u001B[38;5;51m";
    private static final String AMARILLO = "\u001B[38;5;226m";
    private static final String MORADO   = "\u001B[38;5;135m";
    private static final String GRIS     = "\u001B[38;5;240m";
    private static final String ROJO     = "\u001B[38;5;196m";
    private static final String VERDE    = "\u001B[38;5;46m";
    private static final String NARANJA  = "\u001B[38;5;208m";

    private static final int ANCHO_REGISTRO = 74;

    public Juego() {
        scanner  = new Scanner(System.in);
        registro = new ArrayList<>();
    }

    private void reproducirMusica() {
        try {
            java.net.URL urlMusica = getClass().getResource("/mascota_virtual/Music_menu.wav");

            if (urlMusica != null) {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(urlMusica);
                Clip clipMusica = AudioSystem.getClip();
                clipMusica.open(audioStream);
                clipMusica.loop(Clip.LOOP_CONTINUOUSLY);
                clipMusica.start();
            } else {
                System.out.println(AMARILLO + "⚠️ Alerta: No se encontró 'Music_menu.wav' dentro del paquete mascota_virtual." + RESET);
            }
        } catch (Exception e) {
            System.out.println(ROJO + "⚠️ Error al reproducir la música: " + e.getMessage() + RESET);
        }
    }

    public void iniciar() {
        reproducirMusica();
        mostrarPortada();

        System.out.print(MAGENTA + "🐾 Nombre de tu mascota: " + CIAN);
        String nombre = scanner.nextLine().trim();
        if (nombre.isEmpty()) nombre = "Pelusa";
        System.out.print(RESET);

        mascota = new Mascota(nombre);
        agregarRegistro("🐾 ¡" + nombre + " ha llegado al mundo!");

        while (true) {
            if (!mascota.estaViva()) {
                mostrarGameOver();
                break;
            }
            limpiarPantalla();
            mostrarPantallaCompleta();
            procesarOpcion();
            verificarMisiones();
        }
    }

    private void mostrarPortada() {
        System.out.println(MAGENTA +
            "╔══════════════════════════════════════════════════╗\n" +
            "║                                                  ║\n" +
            "║   " + CIAN + "██████╗ ███████╗████████╗" +
            MORADO + " ██╗    ██╗ ██████╗ ██████╗ ██╗     ██████╗" + MAGENTA + "  ║\n" +
            "║   " + CIAN + "██╔══██╗██╔════╝╚══██╔══╝" +
            MORADO + " ██║    ██║██╔═══██╗██╔══██╗██║     ██╔══██╗" + MAGENTA + " ║\n" +
            "║   " + CIAN + "██████╔╝█████╗     ██║   " +
            MORADO + " ██║ █╗ ██║██║   ██║██████╔╝██║     ██║  ██║" + MAGENTA + " ║\n" +
            "║   " + CIAN + "██╔═══╝ ██╔══╝     ██║   " +
            MORADO + " ██║███╗██║██║   ██║██╔══██╗██║     ██║  ██║" + MAGENTA + " ║\n" +
            "║   " + CIAN + "██║     ███████╗   ██║   " +
            MORADO + " ╚███╔███╔╝╚██████╔╝██║  ██║███████╗██████╔╝" + MAGENTA + " ║\n" +
            "║                                                  ║\n" +
            "║       " + AMARILLO + "⭐ CUIDA A TU MASCOTA, SUBE DE NIVEL ⭐" + MAGENTA + "       ║\n" +
            "╚══════════════════════════════════════════════════╝" +
            RESET);
        System.out.println();
    }

    private void mostrarPantallaCompleta() {
        System.out.println(MAGENTA +
            "╔══════════════════════════════════════════════════════════════════════════╗");
        System.out.println(
            "║  " + CIAN + "🐾🐾  " +
            MORADO + "██████╗ ███████╗████████╗    ██╗    ██╗ ██████╗ ██████╗ ██╗     ██████╗" +
            CIAN + "  🐾🐾" + MAGENTA + "  ║");
        System.out.println(
            "║        " +
            AMARILLO + "★ CUIDA A TU MASCOTA, SÚBELA DE NIVEL Y CONVIÉRTETE EN SU MEJOR AMIGO ★" +
            MAGENTA + "        ║");
        System.out.println(
            "╚══════════════════════════════════════════════════════════════════════════╝" + RESET);

        System.out.println();
        mostrarFilaInfoYEstado();
        System.out.println();
        mostrarFilaInventarioMenuMisiones();
        System.out.println();
        mostrarRegistroEventos();
        System.out.println();

        System.out.println(MORADO +
            "┌──────────────────────────────────────────────────────────────────────────┐");
        System.out.printf(
            "│ 💡 CONSEJO: Mantén equilibrados el hambre, energía y felicidad de %-8s │%n",
            mascota.getNombre());
        System.out.println(
            "└──────────────────────────────────────────────────────────────────────────┘"
            + RESET);
    }

    private void mostrarFilaInfoYEstado() {
        // Se removió la línea correspondiente a la 'Hora' del arreglo visual
        String[] infoLineas = {
            MORADO + "╔═══════════════════════════════╗" + RESET,
            MORADO + "║  " + MAGENTA + "INFORMACIÓN DE TU MASCOTA" + MORADO + "   ║" + RESET,
            MORADO + "╠═══════════════════════════════╣" + RESET,
            MORADO + "║  " + RESET + asciiMascota(0) + CIAN + "  ⭐ Nivel      : " + AMARILLO + mascota.getNivel()       + MORADO + "      ║" + RESET,
            MORADO + "║  " + RESET + asciiMascota(1) + CIAN + "  😊 Estado     : " + AMARILLO + mascota.getEstado()      + MORADO + " ║" + RESET,
            MORADO + "║  " + RESET + asciiMascota(2) + CIAN + "  💎 Experiencia: " + AMARILLO + mascota.getExperiencia() + "/100" + MORADO + " ║" + RESET,
            MORADO + "║  " + RESET + asciiMascota(3) + CIAN + "  📅 Día        : " + AMARILLO + mascota.getDias()        + MORADO + "      ║" + RESET,
            MORADO + "║  " + RESET + asciiMascota(4) + "                             " + MORADO + "║" + RESET,
            MORADO + "║  " + MAGENTA + " Nombre: " + AMARILLO + mascota.getNombre() + MORADO + "               ║" + RESET,
            MORADO + "╚═══════════════════════════════╝" + RESET
        };

        String[] estadoLineas = {
            CIAN + "╔══════════════════════════════════════════╗" + RESET,
            CIAN + "║        " + MAGENTA + "ESTADO DE TU MASCOTA" + CIAN + "               ║" + RESET,
            CIAN + "╠══════════════════════════════════════════╣" + RESET,
            CIAN + "║  " + ROJO    + "🍖 HAMBRE  " + barraColor(mascota.getHambre(),    ROJO)    + "  " + alertaHambre()    + CIAN + " ║" + RESET,
            CIAN + "║                                          ║" + RESET,
            CIAN + "║  " + AMARILLO + "⚡ ENERGÍA " + barraColor(mascota.getEnergia(),  AMARILLO) + "  " + alertaEnergia()   + CIAN + " ║" + RESET,
            CIAN + "║                                          ║" + RESET,
            CIAN + "║  " + MORADO  + "😊 FELICID " + barraColor(mascota.getFelicidad(), MORADO)   + "  " + alertaFelicidad() + CIAN + " ║" + RESET,
            CIAN + "║                                          ║" + RESET,
            CIAN + "╚══════════════════════════════════════════╝" + RESET
        };

        for (int i = 0; i < Math.max(infoLineas.length, estadoLineas.length); i++) {
            String izq = i < infoLineas.length  ? infoLineas[i]  : "";
            String der = i < estadoLineas.length ? estadoLineas[i] : "";
            System.out.println(izq + "  " + der);
        }
    }

    private void mostrarFilaInventarioMenuMisiones() {
        String[] menu = {
            MAGENTA + "╔══════════════════════════════╗" + RESET,
            MAGENTA + "║        " + CIAN + "MENÚ PRINCIPAL" + MAGENTA + "          ║" + RESET,
            MAGENTA + "╠══════════════════════════════╣" + RESET,
            MAGENTA + "║  " + AMARILLO + "1. 🍖 Alimentar" + GRIS + "   (Aumentar)    " + MAGENTA + "║" + RESET,
            MAGENTA + "║  " + AMARILLO + "2. 🎮 Jugar    " + GRIS + "   (Divertir)    " + MAGENTA + "║" + RESET,
            MAGENTA + "║  " + AMARILLO + "3. 💤 Dormir   " + GRIS + "   (+ Energía)   " + MAGENTA + "║" + RESET,
            MAGENTA + "║  " + AMARILLO + "4. 🚪 Salir                    " + MAGENTA + "║" + RESET,
            MAGENTA + "║                              ║" + RESET,
            MAGENTA + "║                              ║" + RESET,
            MAGENTA + "║                              ║" + RESET,
            MAGENTA + "║  " + CIAN + "👉 Selecciona una opción: _    " + MAGENTA + "║" + RESET,
            MAGENTA + "╚══════════════════════════════╝" + RESET
        };

        boolean mAlimentar = mascota.getVecesAlimentado() >= 3;
        boolean mJugar     = mascota.getVecesJugado() >= 3;
        boolean mDormir    = mascota.getDurmioHoy();

        // Se removió el texto de la recompensa para dejar los espacios vacíos y simétricos
        String[] mis = {
            CIAN + "╔══════════════════════════════╗" + RESET,
            CIAN + "║     " + MAGENTA + "MISIONES DIARIAS" + CIAN + "          ║" + RESET,
            CIAN + "╠══════════════════════════════╣" + RESET,
            CIAN + "║  " + AMARILLO + "🍖 Alimentar 3 veces" + CIAN + "           ║" + RESET,
            CIAN + "║  " + (mAlimentar ? VERDE + "   ✅ ¡COMPLETADO!      " : barrasMision(mascota.getVecesAlimentado(), 3) + GRIS + " [ " + mascota.getVecesAlimentado() + " / 3 ]") + CIAN + "  ║" + RESET,
            CIAN + "║                              ║" + RESET,
            CIAN + "║  " + AMARILLO + "🎮 Jugar 3 veces" + CIAN + "               ║" + RESET,
            CIAN + "║  " + (mJugar ? VERDE + "   ✅ ¡COMPLETADO!      " : barrasMision(mascota.getVecesJugado(), 3) + GRIS + " [ " + mascota.getVecesJugado() + " / 3 ]") + CIAN + "  ║" + RESET,
            CIAN + "║                              ║" + RESET,
            CIAN + "║  " + AMARILLO + "💤 Dormir hoy" + CIAN + "                  ║" + RESET,
            CIAN + "║  " + (mDormir ? VERDE + "   ✅ ¡COMPLETADO!      " : GRIS + "   ⬜⬜⬜⬜⬜⬜⬜⬜      ") + CIAN + "║" + RESET,
            CIAN + "║                              ║" + RESET,
            CIAN + "║                              ║" + RESET,
            CIAN + "╚══════════════════════════════╝" + RESET
        };

        int max = Math.max(menu.length, mis.length);
        for (int i = 0; i < max; i++) {
            String c2 = i < menu.length ? menu[i] : "";
            String c3 = i < mis.length  ? mis[i]  : "";
            System.out.println("            " + c2 + "  " + c3);
        }
    }

    private void mostrarRegistroEventos() {
        System.out.println(MAGENTA +
            "╔══════════════════════════════════════════════════════════════════════════╗");
        System.out.println(
            "║                      REGISTRO DE EVENTOS                                 ║");
        System.out.println(
            "╠══════════════════════════════════════════════════════════════════════════╣"
            + RESET);

        int inicio = Math.max(0, registro.size() - 4);
        int mostrados = 0;
        for (int i = inicio; i < registro.size(); i++) {
            String linea = registro.get(i);
            String sinAnsi = linea.replaceAll("\u001B\\[[;\\d]*m", "");
            int pad = ANCHO_REGISTRO - sinAnsi.length();
            if (pad < 0) pad = 0;
            System.out.println(GRIS + "║  " + linea + " ".repeat(pad) + GRIS + "║" + RESET);
            mostrados++;
        }
        for (int i = mostrados; i < 4; i++) {
            System.out.println(GRIS + "║  " + " ".repeat(ANCHO_REGISTRO) + "║" + RESET);
        }

        System.out.println(MAGENTA +
            "╚══════════════════════════════════════════════════════════════════════════╝"
            + RESET);
    }

    private void procesarOpcion() {
        System.out.print(MAGENTA + "\n👉 Tu opción: " + CIAN);
        String op = scanner.nextLine().trim();
        System.out.print(RESET);

        switch (op) {

            case "1":
                mascota.alimentar();
                agregarRegistro("🍖 Alimentaste a " + mascota.getNombre() + "  +15 XP  +10💰  Hambre -30");
                if (mascota.getHambre() <= 20)
                    agregarRegistro("✅ " + mascota.getNombre() + " está satisfecho.");
                mascota.pasarTiempo();
                break;

            case "2":
                if (mascota.getEnergia() < 20) {
                    agregarRegistro("⚠️  " + mascota.getNombre() + " está agotado. ¡Hazlo dormir primero!");
                    break;
                }
                mascota.jugar();
                agregarRegistro("🎮 Jugaste con " + mascota.getNombre() + "  +25 XP  +15💰  Energia -10");
                mascota.pasarTiempo();
                break;

            case "3":
                mascota.dormir();
                agregarRegistro("💤 " + mascota.getNombre() + " durmió y recuperó energía.  +10 XP  +5💰");
                mascota.pasarTiempo();
                break;

            case "4":
                System.out.println(CIAN + "\n👋 ¡Gracias por jugar PET WORLD!" + RESET);
                System.exit(0);
                break;

            default:
                agregarRegistro("⚠️  Opción inválida: \"" + op + "\".");
        }
    }

    private void verificarMisiones() {
        if (mascota.getVecesAlimentado() >= 3 && mascota.getVecesJugado() >= 3 && mascota.getDurmioHoy()) {
            mascota.recibirRecompensaMisiones();
            agregarRegistro("🎉 ¡COMPLETADO! Terminaste todas las misiones. Premio: +100 😊");
            mascota.reiniciarMisionesDiarias();
        }
    }

    private void mostrarGameOver() {
        System.out.println(ROJO +
            "\n╔══════════════════════════════════════╗\n" +
            "║          💀 GAME OVER 💀             ║\n" +
            "║   TU MASCOTA HA FALLECIDO...         ║\n" +
            "╠══════════════════════════════════════╣\n" +
            "║  🏆 Nivel alcanzado : " + String.format("%-15d", mascota.getNivel())        + " ║\n" +
            "║  💎 XP final        : " + String.format("%-15d", mascota.getExperiencia()) + " ║\n" +
            "║  💰 Monedas         : " + String.format("%-15d", mascota.getMonedas())     + " ║\n" +
            "╚══════════════════════════════════════╝" + RESET);
    }

    private void limpiarPantalla() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private void agregarRegistro(String msg) {
        registro.add(msg);
    }

    private String barraColor(int valor, String color) {
        int v = Math.max(0, Math.min(100, valor));
        int llenas = v / 10;
        String barra = color;
        for (int i = 0; i < llenas; i++)  barra += "█";
        barra += GRIS;
        for (int i = llenas; i < 10; i++) barra += "░";
        barra += RESET + " " + String.format("%3d", v) + "%";
        return barra;
    }

    private String barrasMision(int actual, int meta) {
        int llenas = Math.min(actual, meta);
        String b = MORADO;
        for (int i = 0; i < llenas; i++)  b += "█";
        b += GRIS;
        for (int i = llenas; i < meta; i++) b += "░";
        return b + RESET;
    }

    private String asciiMascota(int linea) {
        String[] ascii = {
            CIAN + " /\\_/\\ " + RESET,
            CIAN + "(  o o  )" + RESET,
            CIAN + " =( Y )= " + RESET,
            CIAN + "  )   (  " + RESET,
            CIAN + " ()-() " + RESET
        };
        return linea < ascii.length ? ascii[linea] : "         ";
    }

    private String alertaHambre() {
        int h = mascota.getHambre();
        if (h >= 80) return ROJO     + "¡Peligro!   ";
        if (h >= 50) return AMARILLO + "Ten cuidado ";
        return CIAN                  + "Bien        ";
    }

    private String alertaEnergia() {
        int e = mascota.getEnergia();
        if (e <= 20) return ROJO     + "¡Agotado!   ";
        if (e >= 60) return CIAN     + "¡Con energía!";
        return AMARILLO              + "Normal      ";
    }

    private String alertaFelicidad() {
        int f = mascota.getFelicidad();
        if (f <= 30) return ROJO     + "¡Triste!    ";
        if (f >= 70) return CIAN     + "¡Feliz!     ";
        return AMARILLO              + "Normal      ";
    }
}