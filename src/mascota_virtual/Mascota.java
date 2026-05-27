package mascota_virtual;

public class Mascota {
    // 1. ATRIBUTOS (Propiedades de la mascota)
    private String nombre;
    private String especie;
    private int edad;
    private int energia; // Atributo extra para darle sentido a los métodos

    // 2. CONSTRUCTOR (Para crear a la mascota con sus datos iniciales)
    public Mascota(String nombre, String especie, int edad) {
        this.nombre = nombre;
        this.especie = especie;
        this.edad = edad;
        this.energia = 100; // Toda mascota nueva empieza con la energía al 100%
    }

    // 3. MÉTODOS DE COMPORTAMIENTO
    
    // Método comer
    public void comer() {
        System.out.println(nombre + " está comiendo. ¡Qué rico!");
        this.energia += 20; // Recupera energía
        if (this.energia > 100) this.energia = 100; // No puede pasar de 100
    }

    // Método jugar
    public void jugar() {
        if (this.energia >= 30) {
            System.out.println(nombre + " está jugando a la pelota. ¡Se está divirtiendo mucho!");
            this.energia -= 30; // Gastar energía al jugar
        } else {
            System.out.println(nombre + " está muy cansado(a) para jugar. Necesita dormir.");
        }
    }

    // Método dormir
    public void dormir() {
        System.out.println(nombre + " se ha dormido... Zzz... Zzz...");
        this.energia = 100; // Se recarga por completo
    }

    // 4. GETTERS Y SETTERS (Para consultar o modificar los atributos)

    // Nombre
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Especie
    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    // Edad
    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    // Energía (Solo Getter, no queremos que la modifiquen por fuera directamente)
    public int getEnergia() {
        return energia;
    }
}
