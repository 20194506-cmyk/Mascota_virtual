package mascota_virtual;

import java.util.Scanner;
import java.util.ArrayList;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Juego {

    private Mascota mascota;
    private final Scanner scanner;
    private final ArrayList<String> registro;

    // Colores ANSI Estilizados
    private static final String RESET    = "\u001B[0m";
    private static final String MAGENTA  = "\u001B[38;5;201m";
    private static final String CIAN     = "\u001B[38;5;51m";
    private static final String AMARILLO = "\u001B[38;5;226m";
    private static final String MORADO   = "\u001B[38;5;135m";
    private static final String GRIS     = "\u001B[38;5;242m";
    private static final String ROJO     = "\u001B[38;5;196m";
    private static final String VERDE    = "\u001B[38;5;46m";
    
    private static final int ANCHO_IZQ = 36; 
    private static final int ANCHO_DER = 42; 
    private static final int ANCHO_TOTAL = 82; 

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
                System.out.println(AMARILLO + "⚠️ [Música] No se encontró 'Music_menu.wav'." + RESET);
            }
        } catch (Exception e) {
            System.out.println(ROJO + "⚠️ Error de audio: " + e.getMessage() + RESET);
        }
    }

    public void iniciar() {
        reproducirMusica();
        limpiarPantalla();
        mostrarPortada();

        // INTERACCIÓN: Preguntar Nombre
        System.out.print(MAGENTA + " 🐾 ¿Qué nombre le darás a tu mascota?: " + CIAN);
        String nombre = scanner.nextLine().trim();
        if (nombre.isEmpty()) nombre = "Pelusa";

        // INTERACCIÓN NUEVA: Preguntar Género
        System.out.print(MAGENTA + " ♀️/♂️ ¿Qué género prefieres? (M = Macho / H = Hembra): " + CIAN);
        String generoInput = scanner.nextLine().trim().toUpperCase();
        if (!generoInput.equals("M") && !generoInput.equals("H")) generoInput = "M";
        
        System.out.print(RESET);

        mascota = new Mascota(nombre, generoInput);
        String articuloArt = mascota.getGenero().equals("Hembra") ? "la" : "lo";
        agregarRegistro("🐾 ¡" + nombre + " ha nacido! Cuída" + articuloArt + " muy bien.");

        while (true) {
            mascota.verificarVida(); // Validar estado antes de renderizar la UI
            if (!mascota.estaViva()) {
                limpiarPantalla();
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
        System.out.println(MAGENTA + "╔══════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║ " + CIAN + "    ██████╗ ███████╗████████╗   ██╗    ██╗ ██████╗ ██████╗ ██╗     ██████╗   " + MAGENTA + "║");
        System.out.println("║ " + CIAN + "    ██╔══██╗██╔════╝╚══██╔══╝   ██║    ██║██╔═══██╗██╔══██╗██║     ██╔══██╗  " + MAGENTA + "║");
        System.out.println("║ " + MORADO + "    ██████╔╝█████╗      ██║      ██║ █╗ ██║██║   ██║██████╔╝██║     ██║  ██║  " + MAGENTA + "║");
        System.out.println("║ " + MORADO + "    ██╔═══╝ ██╔══╝      ██║      ██║███╗██║██║   ██║██╔══██╗██║     ██║  ██║  " + MAGENTA + "║");
        System.out.println("║ " + MORADO + "    ██║     ███████╗   ██║      ╚███╔███╔╝╚██████╔╝██║  ██║███████╗██████╔╝  " + MAGENTA + "║");
        System.out.println("║                                                                                  ║");
        System.out.println("║                      " + AMARILLO + "⭐ CUIDA A TU MASCOTA Y SUBE DE NIVEL ⭐" + MAGENTA + "                      ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════════════════════╝" + RESET);
        System.out.println();
    }

    private void mostrarPantallaCompleta() {
        System.out.println(MAGENTA + "╔══════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║   " + CIAN + "🐾 PET WORLD" + MORADO + " - Cuida a tu compañero, sube de nivel y gana misiones! 🐾         " + MAGENTA + "║");
        System.out.println("╚══════════════════════════════════════════════════════════════════════════════════╝" + RESET);

        mostrarFilaInfoYEstado();
        System.out.println();
        mostrarFilaAccionesYMisiones();
        System.out.println();
        mostrarRegistroEventos();
        System.out.println();
        
        String textoConsejo = "💡 DÍA " + mascota.getDias() + " | Género: " + mascota.getGenero() + " | ¡Completa tareas para ganar recompensas!";
        System.out.println(GRIS + "┌" + "─".repeat(ANCHO_TOTAL - 2) + "┐");
        System.out.println("│ " + RESET + formatearLinea(textoConsejo, ANCHO_TOTAL - 4) + GRIS + " │");
        System.out.println("└" + "─".repeat(ANCHO_TOTAL - 2) + "┘" + RESET);
    }

    private void mostrarFilaInfoYEstado() {
        String[] lineasIzq = {
            MAGENTA + "INFORMACIÓN DE TU MASCOTA" + MORADO,
            "─────────────────────────",
            CIAN + " " + asciiMascota(0) + "  Nivel      : " + AMARILLO + mascota.getNivel(),
            CIAN + " " + asciiMascota(1) + "  Estado     : " + AMARILLO + mascota.getEstado().trim(),
            CIAN + " " + asciiMascota(2) + "  Experiencia: " + AMARILLO + mascota.getExperiencia() + "/100",
            CIAN + " " + asciiMascota(3) + "  Nombre     : " + AMARILLO + mascota.getNombre(),
            CIAN + " " + asciiMascota(4) + "  Monedas    : " + AMARILLO + mascota.getMonedas() + " 🪙"
        };

        String[] lineasDer = {
            MAGENTA + "ESTADO DE TU MASCOTA" + CIAN,
            "────────────────────",
            ROJO + " 🍖 HAMBRE    " + barraColor(mascota.getHambre(), ROJO) + "  " + alertaHambre(),
            "",
            AMARILLO + " ⚡ ENERGÍA   " + barraColor(mascota.getEnergia(), AMARILLO) + "  " + alertaEnergia(),
            "",
            MORADO + " 😊 FELICIDAD " + barraColor(mascota.getFelicidad(), MORADO) + "  " + alertaFelicidad()
        };

        System.out.println(MORADO + "╔" + "═".repeat(ANCHO_IZQ) + "╗  " + CIAN + "╔" + "═".repeat(ANCHO_DER) + "╗" + RESET);

        for (int i = 0; i < Math.max(lineasIzq.length, lineasDer.length); i++) {
            String lIzq = i < lineasIzq.length ? lineasIzq[i] : "";
            String lDer = i < lineasDer.length ? lineasDer[i] : "";

            System.out.print(MORADO + "║ " + RESET + formatearLinea(lIzq, ANCHO_IZQ - 2) + MORADO + " ║  ");
            System.out.print(CIAN + "║ " + RESET + formatearLinea(lDer, ANCHO_DER - 2) + CIAN + " ║\n");
        }

        System.out.println(MORADO + "╚" + "═".repeat(ANCHO_IZQ) + "╝  " + CIAN + "╚" + "═".repeat(ANCHO_DER) + "╝" + RESET);
    }

    private void mostrarFilaAccionesYMisiones() {
        boolean mAlimentar = mascota.getVecesAlimentado() >= 3;
        boolean mJugar     = mascota.getVecesJugado() >= 3;
        boolean mDormir    = mascota.getDurmioHoy();

        boolean esHembra = mascota.getGenero().equals("Hembra");

        String[] lineasIzq = {
            CIAN + "MENÚ PRINCIPAL" + MAGENTA,
            "──────────────",
            AMARILLO + "1. 🍖 Alimentar    " + GRIS + "(Hambre -20)",
            AMARILLO + "2. 🎮 Jugar        " + GRIS + "(Diversión)",
            AMARILLO + "3. 💤 Dormir       " + GRIS + "(Energía +40)",
            AMARILLO + "4. 🛒 Tienda Virtual" + GRIS + " (¡NUEVO!)",
            AMARILLO + "5. 🚪 Salir"
        };

        String[] lineasDer = {
            MAGENTA + "MISIONES DIARIAS" + CIAN,
            "────────────────",
            AMARILLO + (esHembra ? "🍖 Alimentarla 3 veces" : "🍖 Alimentarlo 3 veces"),
            (mAlimentar ? VERDE + "   ✅ ¡COMPLETADO!" : "   " + barrasMision(mascota.getVecesAlimentado(), 3) + GRIS + " [ " + mascota.getVecesAlimentado() + " / 3 ]"),
            AMARILLO + (esHembra ? "🎮 Jugar con ella 3 veces" : "🎮 Jugar con él 3 veces"),
            (mJugar ? VERDE + "   ✅ ¡COMPLETADO!" : "   " + barrasMision(mascota.getVecesJugado(), 3) + GRIS + " [ " + mascota.getVecesJugado() + " / 3 ]"),
            AMARILLO + "💤 Mandarlo a dormir hoy",
            (mDormir ? VERDE + "   ✅ ¡COMPLETADO!" : GRIS + "   ⬜⬜⬜⬜⬜⬜⬜⬜")
        };

        System.out.println(MAGENTA + "╔" + "═".repeat(ANCHO_IZQ) + "╗  " + CIAN + "╔" + "═".repeat(ANCHO_DER) + "╗" + RESET);

        for (int i = 0; i < Math.max(lineasIzq.length, lineasDer.length); i++) {
            String lIzq = i < lineasIzq.length ? lineasIzq[i] : "";
            String lDer = i < lineasDer.length ? lineasDer[i] : "";

            System.out.print(MAGENTA + "║ " + RESET + formatearLinea(lIzq, ANCHO_IZQ - 2) + MAGENTA + " ║  ");
            System.out.print(CIAN + "║ " + RESET + formatearLinea(lDer, ANCHO_DER - 2) + CIAN + " ║\n");
        }

        System.out.println(MAGENTA + "╚" + "═".repeat(ANCHO_IZQ) + "╝  " + CIAN + "╚" + "═".repeat(ANCHO_DER) + "╝" + RESET);
    }

    private void mostrarRegistroEventos() {
        System.out.println(MAGENTA + "╔" + "═".repeat(ANCHO_TOTAL - 2) + "╗");
        System.out.println("║" + RESET + formatearCentrado(MAGENTA + "REGISTRO DE EVENTOS" + RESET, ANCHO_TOTAL - 2) + MAGENTA + "║");
        System.out.println("╠" + "═".repeat(ANCHO_TOTAL - 2) + "╣" + RESET);

        int inicio = Math.max(0, registro.size() - 4);
        int mostrados = 0;
        
        for (int i = inicio; i < registro.size(); i++) {
            System.out.println(MAGENTA + "║ " + RESET + formatearLinea(registro.get(i), ANCHO_TOTAL - 4) + MAGENTA + " ║" + RESET);
            mostrados++;
        }
        for (int i = mostrados; i < 4; i++) {
            System.out.println(MAGENTA + "║ " + " ".repeat(ANCHO_TOTAL - 4) + " ║" + RESET);
        }

        System.out.println(MAGENTA + "╚" + "═".repeat(ANCHO_TOTAL - 2) + "╝" + RESET);
    }

    private String formatearLinea(String texto, int anchoMaximo) {
        String textoLimpio = texto.replaceAll("\u001B\\[[;\\d]*m", "");
        int espacioRelleno = anchoMaximo - textoLimpio.length();
        if (espacioRelleno > 0) {
            return texto + " ".repeat(espacioRelleno);
        }
        return texto;
    }

    private String formatearCentrado(String texto, int anchoMaximo) {
        String textoLimpio = texto.replaceAll("\u001B\\[[;\\d]*m", "");
        int espacioTotal = anchoMaximo - textoLimpio.length();
        if (espacioTotal <= 0) return texto;
        int izq = espacioTotal / 2;
        int der = espacioTotal - izq;
        return " ".repeat(izq) + texto + " ".repeat(der);
    }

    private void procesarOpcion() {
        System.out.print(MAGENTA + "\n👉 Tu opción: " + CIAN);
        String op = scanner.nextLine().trim();
        System.out.print(RESET);

        boolean esHembra = mascota.getGenero().equals("Hembra");

        switch (op) {
            case "1":
                mascota.alimentar();
                agregarRegistro("🍖 Alimentaste a " + mascota.getNombre());
                mascota.pasarTiempo();
                break;

            case "2":
                if (mascota.getCampanaAvisosEnergia()) {
                     // Logica controlada
                }
                if (mascota.getEnergia() < 20) {
                    agregarRegistro("⚠️ " + mascota.getNombre() + (esHembra ? " está agotada. " : " está agotado. ") + "¡Hazlo dormir!");
                    break;
                }
                mascota.jugar();
                agregarRegistro("🎮 Jugaste con " + mascota.getNombre());
                mascota.pasarTiempo();
                break;

            case "3":
                mascota.dormir();
                agregarRegistro("💤 " + mascota.getNombre() + " se fue a dormir.");
                mascota.pasarTiempo();
                break;

            case "4":
                abrirTienda(); // Nueva sección interactiva
                break;

            case "5":
                System.out.println(CIAN + "\n👋 ¡Gracias por jugar PET WORLD!" + RESET);
                System.exit(0);
                break;

            default:
                agregarRegistro("⚠️ Opción inválida: \"" + op + "\".");
        }
    }

    // INTERACCIÓN ADICIONAL: Menú de Tienda Virtual interactiva
    private void abrirTienda() {
        limpiarPantalla();
        System.out.println(AMARILLO + "╔══════════════════════════════════════════╗");
        System.out.println("║          🛒 TIENDA VIRTUAL PET           ║");
        System.out.println("╠══════════════════════════════════════════╣");
        System.out.println("║ Tus Monedas actuales: " + mascota.getMonedas() + " 🪙            ║");
        System.out.println("║                                          ║");
        System.out.println("║ 1. 🍪 Galleta Premium  (Coste: 30 🪙)    ║");
        System.out.println("║ 2. ⚽ Pelota Nueva     (Coste: 50 🪙)    ║");
        System.out.println("║ 3. 🧪 Poción Energía   (Coste: 60 🪙)    ║");
        System.out.println("║ 4. ↩️ Volver al menú                     ║");
        System.out.println("╚══════════════════════════════════════════╝" + RESET);
        System.out.print(MAGENTA + "Selecciona un artículo para comprar: " + CIAN);
        
        String tOp = scanner.nextLine().trim();
        switch (tOp) {
            case "1":
                efectuarCompra(30, "🍪 Compraste una Galleta Premium", 1);
                break;
            case "2":
                efectuarCompra(50, "⚽ Compraste un Juguete nuevo", 2);
                break;
            case "3":
                efectuarCompra(60, "🧪 Compraste una Poción Energética", 3);
                break;
            default:
                agregarRegistro("🛒 Saliste de la tienda.");
                break;
        }
    }

    private void efectuarCompra(int costo, String mensajeExito, int tipoItem) {
        if (mascota.getMonedas() >= costo) {
            mascota.gastarMonedas(costo);
            mascota.usarItem(tipoItem);
            agregarRegistro("🎉 " + mensajeExito + " para " + mascota.getNombre());
        } else {
            agregarRegistro("❌ No tienes suficientes monedas para este artículo.");
        }
    }

    // RECOMPENSAS MEJORADAS: Ahora el usuario gana un boost masivo de Monedas y XP
    private void verificarMisiones() {
        if (mascota.getVecesAlimentado() >= 3 && mascota.getVecesJugado() >= 3 && mascota.getDurmioHoy()) {
            mascota.recibirRecompensaMisiones();
            agregarRegistro("🎁 ¡MISIÓN DIARIA COMPLETADA! Obtienes +100 Monedas y +50 XP.");
            mascota.reiniciarMisionesDiarias();
        }
    }

    private void mostrarGameOver() {
        System.out.println(ROJO + "╔══════════════════════════════════════╗");
        System.out.println("║         💀 GAME OVER 💀              ║");
        System.out.println("║   TU MASCOTA HA FALLECIDO...         ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.printf("║  🏆 Nivel alcanzado : %-14d  ║%n", mascota.getNivel());
        System.out.printf("║  💎 XP final        : %-14d  ║%n", mascota.getExperiencia());
        System.out.printf("║  💰 Monedas ganadas : %-14d  ║%n", mascota.getMonedas());
        System.out.println("╚══════════════════════════════════════╝" + RESET);
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
        StringBuilder barra = new StringBuilder(color);
        for (int i = 0; i < llenas; i++) barra.append("█");
        barra.append(GRIS);
        for (int i = llenas; i < 10; i++) barra.append("░");
        barra.append(RESET).append(" ").append(String.format("%3d", v)).append("%");
        return barra.toString();
    }

    private String barrasMision(int actual, int meta) {
        int llenas = Math.min(actual, meta);
        StringBuilder b = new StringBuilder(MORADO);
        for (int i = 0; i < llenas; i++) b.append("█");
        b.append(GRIS);
        for (int i = llenas; i < meta; i++) b.append("░");
        return b.append(RESET).toString();
    }

    private String asciiMascota(int linea) {
        String[] ascii = { " /\\_/\\ ", "(  o o )", " =( Y )=", "  )   ( ", " ()-() " };
        return ascii[linea];
    }

    private String alertaHambre() {
        int h = mascota.getHambre();
        if (h >= 80) return ROJO + "¡Peligro!" + RESET;
        if (h >= 50) return AMARILLO + "Cuidado" + RESET;
        return VERDE + "Bien   " + RESET;
    }

    private String alertaEnergia() {
        int e = mascota.getEnergia();
        if (e <= 20) return ROJO + "¡Agotado!" + RESET;
        if (e >= 60) return VERDE + "Activo   " + RESET;
        return AMARILLO + "Normal   " + RESET;
    }

    private String alertaFelicidad() {
        int f = mascota.getFelicidad();
        if (f <= 30) return ROJO + "¡Triste! " + RESET;
        if (f >= 70) return VERDE + "¡Feliz!  " + RESET;
        return AMARILLO + "Normal   " + RESET;
    }
}