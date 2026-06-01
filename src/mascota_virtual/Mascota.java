package mascota_virtual;

public class Mascota {

    // Atributos
    private String nombre;
    private int hambre;
    private int energia;
    private int felicidad;

    // Constructor
    public Mascota(String nombre) {
        this.nombre = nombre;
        this.hambre = 50;
        this.energia = 50;
        this.felicidad = 50;
    }

    // Alimentar mascota
    public void comer() {
        hambre -= 20;
        felicidad += 5;

        if (hambre < 0) {
            hambre = 0;
        }

        if (felicidad > 100) {
            felicidad = 100;
        }

        System.out.println("\n🍖 " + nombre + " ha comido.");
    }

    // Jugar con mascota
    public void jugar() {
        felicidad += 15;
        energia -= 10;
        hambre += 10;

        limitarValores();

        System.out.println("\n🎮 " + nombre + " se está divirtiendo.");
    }

    // Dormir
    public void dormir() {
        energia += 25;

        if (energia > 100) {
            energia = 100;
        }

        System.out.println("\n😴 " + nombre + " ha descansado.");
    }

    // Paso del tiempo
    public void pasarTiempo() {
        hambre += 5;
        energia -= 5;
        felicidad -= 3;

        limitarValores();
    }

    // Control de límites
    private void limitarValores() {

        if (hambre > 100) {
            hambre = 100;
        }

        if (energia < 0) {
            energia = 0;
        }

        if (felicidad < 0) {
            felicidad = 0;
        }
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public int getHambre() {
        return hambre;
    }

    public int getEnergia() {
        return energia;
    }

    public int getFelicidad() {
        return felicidad;
    }
}
