package entidades;

/**
 * Clase de datos pura. Representa a un superheroe con sus atributos booleanos
 * (las "caracteristicas distinguibles" que pide la consigna).
 *
 * IMPORTANTE: esta clase NO contiene ningun algoritmo (ni Divide y Conquista,
 * ni Greedy). Solo almacena informacion. Toda la logica de ordenar, buscar
 * o decidir preguntas vive en el paquete "algoritmos", separada a proposito
 * (principio de responsabilidad unica, visto en SOLID).
 */
public class Personaje {

    private int id;
    private String genero; // "Femenino" o "Masculino"
    private String nombre;

    // Atributos booleanos: son el "material" que va a usar el algoritmo
    // Greedy (DecisorGreedy) para decidir que pregunta conviene hacer.
    private boolean vuela;
    private boolean usaCapa;
    private boolean esDC;              // si es false, es Marvel
    private boolean esMujer;
    private boolean tienePoderesMagicos;
    private boolean usaMascara;
    private boolean tieneSuperFuerza;
    private boolean esHumano;

    public Personaje(int id, String genero, String nombre,
                     boolean vuela, boolean usaCapa, boolean esDC, boolean esMujer,
                     boolean tienePoderesMagicos, boolean usaMascara,
                     boolean tieneSuperFuerza, boolean esHumano)
    {
        this.id = id;
        this.genero = genero;
        this.nombre = nombre;
        this.vuela = vuela;
        this.usaCapa = usaCapa;
        this.esDC = esDC;
        this.esMujer = esMujer;
        this.tienePoderesMagicos = tienePoderesMagicos;
        this.usaMascara = usaMascara;
        this.tieneSuperFuerza = tieneSuperFuerza;
        this.esHumano = esHumano;
    }

    // ===== Getters =====
    public int getId() { return id; }
    public String getGenero() { return genero; }
    public String getNombre() { return nombre; }
    public boolean isVuela() { return vuela; }
    public boolean isUsaCapa() { return usaCapa; }
    public boolean isEsDC() { return esDC; }
    public boolean isEsMujer() { return esMujer; }
    public boolean isTienePoderesMagicos() { return tienePoderesMagicos; }
    public boolean isUsaMascara() { return usaMascara; }
    public boolean isTieneSuperFuerza() { return tieneSuperFuerza; }
    public boolean isEsHumano() { return esHumano; }

    // ===== Info para mostrar en consola =====
    public String mostrarInfo()
    {
        return "ID " + id + " - " + nombre
                + " | Genero: " + genero
                + " | Vuela: " + vuela
                + " | Capa: " + usaCapa
                + " | DC: " + esDC
                + " | Mujer: " + esMujer
                + " | Magia: " + tienePoderesMagicos
                + " | Mascara: " + usaMascara
                + " | Fuerza: " + tieneSuperFuerza
                + " | Humano: " + esHumano;
    }
}