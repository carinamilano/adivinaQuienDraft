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
     */
    private void cargarPersonajes()
    {
        personajes = new Personaje[CANTIDAD_PERSONAJES];

        // --- Femeninos ---
        personajes[0]  = new Personaje(7,  GENERO_FEMENINO, "Wonder Woman",     true,  false, true,  true, true,  false, true,  false);
        personajes[1]  = new Personaje(14, GENERO_FEMENINO, "Supergirl",        true,  true,  true,  true, false, false, true,  false);
        personajes[2]  = new Personaje(3,  GENERO_FEMENINO, "Batgirl",          false, true,  true,  true, false, true,  false, true);
        personajes[3]  = new Personaje(21, GENERO_FEMENINO, "Gatubela",         false, false, true,  true, false, true,  false, true);
        personajes[4]  = new Personaje(9,  GENERO_FEMENINO, "Harley Quinn",     false, false, true,  true, false, false, false, true);
        personajes[5]  = new Personaje(12, GENERO_FEMENINO, "Gamora",           false, false, false, true, false, false, true,  false);
        personajes[6]  = new Personaje(5,  GENERO_FEMENINO, "Bruja Escarlata",  true,  true,  false, true, true,  false, false, true);
        personajes[7]  = new Personaje(18, GENERO_FEMENINO, "Capitana Marvel",  true,  false, false, true, false, false, true,  false);
        personajes[8]  = new Personaje(2,  GENERO_FEMENINO, "Viuda Negra",      false, false, false, true, false, false, false, true);
        personajes[9]  = new Personaje(23, GENERO_FEMENINO, "Spider-Gwen",      false, false, false, true, false, true,  true,  true);
        personajes[10] = new Personaje(11, GENERO_FEMENINO, "Hela",             false, true,  false, true, true,  true,  true,  false);
        personajes[11] = new Personaje(16, GENERO_FEMENINO, "Tormenta",         true,  true,  false, true, false, false, false, true);

        // --- Masculinos ---
        personajes[12] = new Personaje(1,  GENERO_MASCULINO, "Superman",        true,  true,  true,  false, false, false, true,  false);
        personajes[13] = new Personaje(19, GENERO_MASCULINO, "Batman",          false, true,  true,  false, false, true,  false, true);
        personajes[14] = new Personaje(8,  GENERO_MASCULINO, "Flash",           false, false, true,  false, false, true,  false, true);
        personajes[15] = new Personaje(4,  GENERO_MASCULINO, "Shazam",          true,  true,  true,  false, true,  false, true,  true);
        personajes[16] = new Personaje(20, GENERO_MASCULINO, "Aquaman",         false, false, true,  false, false, false, true,  false);
        personajes[17] = new Personaje(15, GENERO_MASCULINO, "Deadpool",        false, false, false, false, false, true,  false, true);
        personajes[18] = new Personaje(10, GENERO_MASCULINO, "Iron Man",        true,  false, false, false, false, true,  true,  true);
        personajes[19] = new Personaje(22, GENERO_MASCULINO, "Thor",            true,  true,  false, false, true,  false, true,  false);
        personajes[20] = new Personaje(6,  GENERO_MASCULINO, "Spiderman",       false, false, false, false, false, true,  true,  true);
        personajes[21] = new Personaje(17, GENERO_MASCULINO, "Doctor Strange",  true,  true,  false, false, true,  false, false, true);
        personajes[22] = new Personaje(13, GENERO_MASCULINO, "Hulk",            false, false, false, false, false, false, true,  true);
    }

    private void mostrarListado()
    {
        for (Personaje p : personajes)
        {
            System.out.println(p.mostrarInfo());
        }
    }
}