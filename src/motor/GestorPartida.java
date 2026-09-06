package motor;

import entidades.Personaje;
import algoritmos.OrdenadorMerge;
import motor.humanoVsMaquina.PartidaHumanoVsMaquina;
import motor.maquinaVsMaquina.PartidaMaquinaVsMaquina;

import java.util.Scanner;

/**
 * GestorPartida actua como "arbitro" del juego. NO implementa ningun
 * algoritmo por si mismo, ni la logica de ningun modo de juego: su unico
 * trabajo es cargar los 23 personajes, ordenarlos, mostrar el menu, y
 * delegar cada modo de juego a su clase correspondiente
 * (PartidaHumanoVsMaquina o PartidaMaquinaVsMaquina).
 * Esta separacion es a proposito: cada modo de juego vive en su propia
 * clase, en su propio paquete, para que quede clara la responsabilidad
 * de cada una (principio de responsabilidad unica).
 */
public class GestorPartida {

    private static final String GENERO_FEMENINO = "Femenino";
    private static final String GENERO_MASCULINO = "Masculino";
    private static final int CANTIDAD_PERSONAJES = 23;

    private Personaje[] personajes;
    private OrdenadorMerge ordenadorMerge;
    private Scanner teclado;

    public GestorPartida()
    {
        ordenadorMerge = new OrdenadorMerge();
        teclado = new Scanner(System.in);
    }

    /**
     * Punto de entrada del juego: prepara los datos y muestra el menu.
     */
    public void iniciar()
    {
        cargarPersonajes();

        System.out.println("=== Personajes cargados (desordenados por ID, agrupados por genero) ===");
        mostrarListado();

        // --- ALGORITMO 1: Divide y Conquista (Merge Sort) ---
        // Se ejecuta UNA SOLA VEZ, antes de que arranque cualquier turno.
        System.out.println("\n=== Ordenando por ID con Merge Sort (Divide y Conquista) ===");
        ordenadorMerge.ordenar(personajes);

        System.out.println("=== Personajes ya ordenados por ID ===");
        mostrarListado();

        mostrarMenu();
    }

    private void mostrarMenu()
    {
        int opcion;

        do
        {
            System.out.println("\n===== ADIVINA QUIEN =====");
            System.out.println("1) Jugador vs Maquina");
            System.out.println("2) Maquina vs Maquina");
            System.out.println("0) Salir");
            System.out.print("Elegi una opcion: ");
            opcion = teclado.nextInt();
            teclado.nextLine(); // consumo el salto de linea pendiente

            switch (opcion)
            {
                case 1:
                    new PartidaHumanoVsMaquina(personajes, teclado).jugar();
                    break;
                case 2:
                    new PartidaMaquinaVsMaquina(personajes).jugar();
                    break;
                case 0:
                    System.out.println("Gracias por jugar!");
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }

        } while (opcion != 0);
    }

    /**
     * Carga los 23 personajes en el orden que definio el equipo:
     * agrupados solo por genero, con IDs mezclados a proposito para que
     * el Merge Sort tenga trabajo real que hacer.
     *
     * Atributos por personaje: esMujer, calvo, usaLentes, colorPelo
     * (Colorado/Negro/Amarillo) — son exactamente los filtros que pide la
     * consigna (Genero, Calvicie, Lentes, Color de pelo). Se asignaron
     * combinaciones distintas dentro de cada genero (2 x 2 x 3 = 12
     * combinaciones posibles) para que, sumado al genero, los 23 personajes
     * queden siempre distinguibles entre si sin depender del azar —
     * priorizando esto por sobre la fidelidad estetica exacta de cada
     * personaje (por eso algunos, como Gamora o Deadpool, coinciden con su
     * caracterizacion habitual, y otros no).
     */
    private void cargarPersonajes()
    {
        personajes = new Personaje[CANTIDAD_PERSONAJES];

        final boolean MUJER = true;
        final boolean VARON = false;
        final boolean CALVO = true;
        final boolean PELUDO = false;
        final boolean LENTES = true;
        final boolean SIN_LENTES = false;
        final String COLORADO = "Colorado";
        final String NEGRO = "Negro";
        final String AMARILLO = "Amarillo";

        // --- Femeninos (las 12 combinaciones de calvo x lentes x color, sin repetir) ---
        personajes[0]  = new Personaje(7,  GENERO_FEMENINO, "Wonder Woman",     MUJER, PELUDO, SIN_LENTES, NEGRO);
        personajes[1]  = new Personaje(14, GENERO_FEMENINO, "Supergirl",        MUJER, PELUDO, SIN_LENTES, AMARILLO);
        personajes[2]  = new Personaje(3,  GENERO_FEMENINO, "Batgirl",          MUJER, PELUDO, LENTES,     COLORADO);
        personajes[3]  = new Personaje(21, GENERO_FEMENINO, "Gatubela",         MUJER, PELUDO, LENTES,     NEGRO);
        personajes[4]  = new Personaje(9,  GENERO_FEMENINO, "Harley Quinn",     MUJER, PELUDO, LENTES,     AMARILLO);
        personajes[5]  = new Personaje(12, GENERO_FEMENINO, "Gamora",           MUJER, CALVO,  SIN_LENTES, NEGRO);
        personajes[6]  = new Personaje(5,  GENERO_FEMENINO, "Bruja Escarlata",  MUJER, PELUDO, SIN_LENTES, COLORADO);
        personajes[7]  = new Personaje(18, GENERO_FEMENINO, "Capitana Marvel",  MUJER, CALVO,  SIN_LENTES, AMARILLO);
        personajes[8]  = new Personaje(2,  GENERO_FEMENINO, "Viuda Negra",      MUJER, CALVO,  SIN_LENTES, COLORADO);
        personajes[9]  = new Personaje(23, GENERO_FEMENINO, "Spider-Gwen",      MUJER, CALVO,  LENTES,     AMARILLO);
        personajes[10] = new Personaje(11, GENERO_FEMENINO, "Hela",             MUJER, CALVO,  LENTES,     NEGRO);
        personajes[11] = new Personaje(16, GENERO_FEMENINO, "Tormenta",         MUJER, CALVO,  LENTES,     COLORADO);

        // --- Masculinos (11 de las 12 combinaciones, tambien sin repetir) ---
        personajes[12] = new Personaje(1,  GENERO_MASCULINO, "Superman",        VARON, PELUDO, SIN_LENTES, NEGRO);
        personajes[13] = new Personaje(19, GENERO_MASCULINO, "Batman",          VARON, PELUDO, LENTES,     NEGRO);
        personajes[14] = new Personaje(8,  GENERO_MASCULINO, "Flash",           VARON, PELUDO, SIN_LENTES, COLORADO);
        personajes[15] = new Personaje(4,  GENERO_MASCULINO, "Shazam",          VARON, PELUDO, SIN_LENTES, AMARILLO);
        personajes[16] = new Personaje(20, GENERO_MASCULINO, "Aquaman",         VARON, PELUDO, LENTES,     AMARILLO);
        personajes[17] = new Personaje(15, GENERO_MASCULINO, "Deadpool",        VARON, CALVO,  SIN_LENTES, NEGRO);
        personajes[18] = new Personaje(10, GENERO_MASCULINO, "Iron Man",        VARON, CALVO,  LENTES,     NEGRO);
        personajes[19] = new Personaje(22, GENERO_MASCULINO, "Thor",            VARON, CALVO,  LENTES,     AMARILLO);
        personajes[20] = new Personaje(6,  GENERO_MASCULINO, "Spiderman",       VARON, PELUDO, LENTES,     COLORADO);
        personajes[21] = new Personaje(17, GENERO_MASCULINO, "Doctor Strange",  VARON, CALVO,  SIN_LENTES, AMARILLO);
        personajes[22] = new Personaje(13, GENERO_MASCULINO, "Hulk",            VARON, CALVO,  LENTES,     COLORADO);
    }

    private void mostrarListado()
    {
        for (Personaje p : personajes)
        {
            System.out.println(p.mostrarInfo());
        }
    }

    // para que la GUI pueda leer los personajes
    public Personaje[] getPersonajes() {
        if (personajes == null) {
            cargarPersonajes();
            ordenadorMerge.ordenar(personajes);
        }
        return personajes;
    }
}