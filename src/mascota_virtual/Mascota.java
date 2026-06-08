package mascota_virtual;

public class Mascota {

    private final String nombre;
    private final String genero; // NUEVO: Atributo para guardar el género
    private int hambre;
    private int energia;
    private int felicidad;

    private int nivel;
    private int experiencia;

    private int monedas;
    private int dias;
    private boolean viva;

    private int vecesAlimentado;
    private int vecesJugado;
    private boolean durmioHoy;

    public Mascota(String nombre, String genero) {
        this.nombre = nombre;
        this.genero = genero.equalsIgnoreCase("H") ? "Hembra" : "Macho";

        // Valores iniciales equilibrados para evitar muertes instantáneas
        hambre = 30;
        energia = 70;
        felicidad = 60;

        nivel = 1;
        experiencia = 35;

        monedas = 125;
        dias = 1;
        viva = true;

        vecesAlimentado = 0;
        vecesJugado = 0;
        durmioHoy = false;
    }

    // ================= ACCIONES =================

    public void alimentar() {
        hambre = Math.max(0, hambre - 20);
        energia = Math.min(100, energia + 5);
        felicidad = Math.min(100, felicidad + 10);
        vecesAlimentado++;

        ganarExperiencia(15);
        ganarMonedas(10);
        verificarVida();
    }

    public void jugar() {
        felicidad = Math.min(100, felicidad + 25);
        energia = Math.max(0, energia - 15);
        hambre = Math.min(100, hambre + 10);
        vecesJugado++;

        ganarExperiencia(25);
        ganarMonedas(15);
        verificarVida();
    }

    public void dormir() {
        energia = Math.min(100, energia + 40);
        hambre = Math.min(100, hambre + 15);
        felicidad = Math.max(0, felicidad - 15);
        durmioHoy = true;

        ganarExperiencia(10);
        ganarMonedas(5);
        verificarVida();
    }

    // INTERACCIÓN: Métodos para consumir artículos comprados en la tienda
    public void usarItem(int tipoItem) {
        if (tipoItem == 1) { // Galleta Premium
            hambre = Math.max(0, hambre - 40);
            felicidad = Math.min(100, felicidad + 15);
        } else if (tipoItem == 2) { // Pelota Nueva
            felicidad = Math.min(100, felicidad + 40);
            energia = Math.max(0, energia - 5);
        } else if (tipoItem == 3) { // Poción Energética
            energia = Math.min(100, energia + 50);
        }
        ganarExperiencia(20);
        verificarVida();
    }

    // RECOMPENSAS: Se mejora esta sección para otorgar premios tangibles
    public void recibirRecompensaMisiones() {
        this.felicidad = Math.min(100, this.felicidad + 40);
        ganarMonedas(100);       // Recompensa en metálico
        ganarExperiencia(50);    // Recompensa de progreso
    }

    public void reiniciarMisionesDiarias() {
        this.vecesAlimentado = 0;
        this.vecesJugado = 0;
        this.durmioHoy = false;
        this.dias++; // Avanzar el día de paso
    }

    // ================= TIEMPO =================

    public void pasarTiempo() {
        hambre = Math.min(100, hambre + 10);
        energia = Math.max(0, energia - 8);
        felicidad = Math.max(0, felicidad - 5);
        verificarVida();
    }

    // ================= CORRECCIÓN BARRA DE VIDA =================
    // Se asegura de que la muerte ocurra de forma estricta y predecible bajo los límites correctos
    public void verificarVida() {
        if (hambre >= 100 || energia <= 0 || felicidad <= 0) {
            viva = false;
        }
    }

    public boolean estaViva() { return viva; }

    // ================= PROGRESO =================

    public void ganarExperiencia(int xp) {
        experiencia += xp;
        while (experiencia >= 100) {
            experiencia -= 100;
            nivel++;
        }
    }

    public void ganarMonedas(int cantidad) {
        monedas += cantidad;
    }

    public void gastarMonedas(int cantidad) {
        monedas -= cantidad;
    }

    // ================= ESTADO ADAPTADO AL GÉNERO =================
    public String getEstado() {
        boolean esHembra = genero.equals("Hembra");
        if (hambre >= 80) return esHembra ? "😫 Hambrienta" : "😫 Hambriento";
        if (energia <= 20) return esHembra ? "😴 Cansada " : "😴 Cansado ";
        if (felicidad <= 30) return esHembra ? "😢 Triste " : "😢 Triste ";
        if (felicidad >= 80) return "🤩 Muy Feliz ";
        return "😊 Normal ";
    }

    // ================= GETTERS =================
    public String getNombre() { return nombre; }
    public String getGenero() { return genero; }
    public int getHambre() { return hambre; }
    public int getEnergia() { return energia; }
    public int getFelicidad() { return felicidad; }
    public int getNivel() { return nivel; }
    public int getExperiencia() { return experiencia; }
    public int getMonedas() { return monedas; }
    public int getDias() { return dias; }
    public int getVecesAlimentado() { return vecesAlimentado; }
    public int getVecesJugado() { return vecesJugado; }
    public boolean getDurmioHoy() { return durmioHoy; }

	public boolean getCampanaAvisosEnergia() {
		// TODO Auto-generated method stub
		return false;
	}
}