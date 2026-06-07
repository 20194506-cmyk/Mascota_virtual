package mascota_virtual;

public class Mascota {

    private final String nombre;
    private int hambre;
    private int energia;
    private int felicidad;

    private int nivel;
    private int experiencia;

    private int monedas;
    private int dias;
    private final String hora;
    private boolean viva;

    private int vecesAlimentado;
    private int vecesJugado;
    private boolean durmioHoy;

    public Mascota(String nombre) {
        this.nombre = nombre;

        hambre    = 30;
        energia   = 70;
        felicidad = 60;

        nivel       = 1;
        experiencia = 35;

        monedas  = 125;
        dias     = 1; 
        hora     = "Mañana";
        viva     = true;

        vecesAlimentado = 0;
        vecesJugado     = 0;
        durmioHoy       = false;
    }

    // ================= ACCIONES =================

    public void alimentar() {
        hambre    = Math.max(0,   hambre    - 20);
        energia   = Math.min(100, energia   + 5);
        felicidad = Math.min(100, felicidad + 10); // Aumentado a +10 para ganarle al tiempo

        vecesAlimentado++;

        ganarExperiencia(15);
        ganarMonedas(10);
        verificarVida();
    }

    public void jugar() {
        felicidad = Math.min(100, felicidad + 25); // Aumentado a +25 para un empujón extra
        energia   = Math.max(0,   energia   - 15);
        hambre    = Math.min(100, hambre    + 10);

        vecesJugado++;

        ganarExperiencia(25);
        ganarMonedas(15);
        verificarVida();
    }

    public void dormir() {
        energia   = Math.min(100, energia   + 40);
        hambre    = Math.min(100, hambre    + 15);
        
        // CAMBIO AQUÍ: Ahora resta un 15% directo por dormir, tal como querías.
        felicidad = Math.max(0,   felicidad - 15);

        durmioHoy = true;

        ganarExperiencia(10);
        ganarMonedas(5);
        verificarVida();
    }

    public void recibirRecompensaMisiones() {
        this.felicidad = Math.min(100, this.felicidad + 40); 
    }

    public void reiniciarMisionesDiarias() {
        this.vecesAlimentado = 0;
        this.vecesJugado = 0;
        this.durmioHoy = false;
    }

    // ================= TIEMPO =================

    public void pasarTiempo() {
        hambre    = Math.min(100, hambre    + 10);
        energia   = Math.max(0,   energia   - 8);
        felicidad = Math.max(0,   felicidad - 5);
        verificarVida();
    }

    // ================= VIDA =================

    private void verificarVida() {
        if (hambre    >= 100) viva = false;
        if (energia   <= 0)   viva = false;
        if (felicidad <= 0)   viva = false;
    }

    public boolean estaViva() { return viva; }

    // ================= PROGRESO =================

    private void ganarExperiencia(int xp) {
        experiencia += xp;
        while (experiencia >= 100) {
            experiencia -= 100;
            nivel++;
        }
    }

    private void ganarMonedas(int cantidad) {
        monedas += cantidad;
    }

    // ================= ESTADO =================

    public String getEstado() {
        if (hambre    >= 80) return "😫 Hambrienta";
        if (energia   <= 20) return "😴 Cansada   ";
        if (felicidad <= 30) return "😢 Triste    ";
        if (felicidad >= 80) return "🤩 Muy Feliz ";
        return "😊 Normal    ";
    }

    // ================= SETTERS =================

    public void setFelicidad(int felicidad) {
        this.felicidad = Math.max(0, Math.min(100, felicidad));
        verificarVida();
    }

    // ================= GETTERS =================

    public String  getNombre()          { return nombre; }
    public int     getHambre()          { return hambre; }
    public int     getEnergia()         { return energia; }
    public int     getFelicidad()       { return felicidad; }
    public int     getNivel()           { return nivel; }
    public int     getExperiencia()     { return experiencia; }
    public int     getMonedas()         { return monedas; }
    public int     getDias()            { return dias; }
    public String  getHora()            { return hora; }
    public int     getVecesAlimentado() { return vecesAlimentado; }
    public int     getVecesJugado()     { return vecesJugado; }
    public boolean getDurmioHoy()       { return durmioHoy; }
}