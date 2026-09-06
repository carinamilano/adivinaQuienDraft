package entidades;

/**
 * Clase de datos pura. Representa a un superheroe con sus atributos
 * distinguibles (los "filtros aplicables" que pide la consigna: genero,
 * calvicie, lentes y color de pelo).
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

    // Atributos distinguibles: son el "material" que usa el algoritmo Greedy
    // (DecisorGreedy) para decidir que pregunta conviene hacer. Son
    // exactamente los filtros que pide la consigna.
    private boolean esMujer;           // filtro "Genero"
    private boolean calvo;             // filtro "Calvicie"
    private boolean usaLentes;         // filtro "Lentes"
    private String colorPelo;          // filtro "Color de pelo": Colorado, Negro o Amarillo

    public Personaje(int id, String genero, String nombre, boolean esMujer,
                     boolean calvo, boolean usaLentes, String colorPelo)
    {
        this.id = id;
        this.genero = genero;
        this.nombre = nombre;
        this.esMujer = esMujer;
        this.calvo = calvo;
        this.usaLentes = usaLentes;
        this.colorPelo = colorPelo;
    }

    // ===== Getters =====
    public int getId() { return id; }
    public String getGenero() { return genero; }
    public String getNombre() { return nombre; }
    public boolean isEsMujer() { return esMujer; }
    public boolean isCalvo() { return calvo; }
    public boolean isUsaLentes() { return usaLentes; }
    public String getColorPelo() { return colorPelo; }

    // ===== Info para mostrar en consola =====
    public String mostrarInfo()
    {
        return "ID " + id + " - " + nombre
                + " | Genero: " + genero
                + " | Calvo: " + calvo
                + " | Lentes: " + usaLentes
                + " | Color de pelo: " + colorPelo;
    }
}
