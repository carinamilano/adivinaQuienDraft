package motor.humanoVsMaquina;

import entidades.Personaje;
import algoritmos.BuscadorBinario;
import algoritmos.DecisorGreedy;
import algoritmos.Caracteristica;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * MODO 1: JUGADOR VS MAQUINA (simetrico: ambos preguntan y ambos
 * reducen su propio grupo de candidatos).

 * Usa BuscadorBinario (Divide y Conquista) para encontrar el personaje
 * elegido por ID al arrancar, y ademas para permitirle al jugador
 * "arriesgar" un ID directo en cualquier turno (tal como pide la
 * consigna: "los usuarios pueden lanzar directamente su suposicion").
 * Usa DecisorGreedy para que la maquina decida que preguntar. El
 * jugador, en cambio, elige manualmente su pregunta de un menu (no usa
 * el algoritmo greedy, ya que es una persona real decidiendo).
 */
public class PartidaHumanoVsMaquina {

    private Personaje[] personajes;
    private BuscadorBinario buscadorBinario;
    private DecisorGreedy decisorGreedy;
    private Scanner teclado;

    public PartidaHumanoVsMaquina(Personaje[] personajes, Scanner teclado)
    {
        this.personajes = personajes;
        this.teclado = teclado;
        this.buscadorBinario = new BuscadorBinario();
        this.decisorGreedy = new DecisorGreedy();
    }

    public void jugar()
    {
        System.out.println("\n--- Jugador vs Maquina ---");

        // El jugador elige su personaje secreto. La maquina NUNCA accede
        // directamente a esta variable, solo puede "preguntar" por
        // caracteristicas a traves del metodo evaluar de Caracteristica.
        System.out.print("Elegi el ID de tu personaje secreto (1-23): ");
        int idSecretoJugador = teclado.nextInt();
        teclado.nextLine();

        Personaje secretoJugador = buscadorBinario.buscarPorId(personajes, idSecretoJugador);

        if (secretoJugador == null)
        {
            System.out.println("Ese ID no existe. Volviendo al menu.");
            return;
        }

        // La maquina elige su propio secreto al azar entre todos.
        // El jugador tampoco accede directamente a esta variable: solo
        // puede consultarla a traves de sus propias preguntas o
        // arriesgando un ID (Busqueda Binaria) sin ver la variable en si.
        Personaje secretoMaquina = personajes[(int) (Math.random() * personajes.length)];
        System.out.println("(La maquina ya eligio su personaje secreto, no se muestra)");

        // Grupo que maneja la MAQUINA para adivinar el secreto del JUGADOR
        List<Personaje> vivosMaquina = new ArrayList<>();
        // Grupo que maneja el JUGADOR para adivinar el secreto de la MAQUINA
        List<Personaje> vivosJugador = new ArrayList<>();
        for (Personaje p : personajes)
        {
            vivosMaquina.add(p);
            vivosJugador.add(p);
        }

        List<String> preguntasHechasPorMaquina = new ArrayList<>();
        List<String> preguntasHechasPorJugador = new ArrayList<>();

        boolean terminado = false;
        int turno = 1;

        while (!terminado)
        {
            System.out.println("\n========== TURNO " + turno + " ==========");

            // -----------------------------------------------------------
            // PARTE 1: la maquina te pregunta a vos (intenta adivinar
            // tu secreto)
            // -----------------------------------------------------------
            System.out.println("\n--- La maquina te pregunta (candidatos de ella: "
                    + vivosMaquina.size() + ") ---");

            if (vivosMaquina.size() == 1)
            {
                Personaje adivinado = vivosMaquina.get(0);
                System.out.println("La maquina arriesga que tu personaje es: " + adivinado.getNombre());
                if (adivinado == secretoJugador)
                {
                    System.out.println(">>> La maquina ADIVINO tu personaje! Gana la maquina. <<<");
                    terminado = true;
                    continue;
                }
                else
                {
                    System.out.println("La maquina se equivoco.");
                }
            }
            else
            {
                // --- ALGORITMO 2: Greedy ---
                Caracteristica preguntaMaquina = decisorGreedy.elegirMejorPregunta(vivosMaquina, preguntasHechasPorMaquina);

                if (preguntaMaquina == null)
                {
                    System.out.println("La maquina no tiene mas preguntas utiles.");
                }
                else
                {
                    System.out.println("La maquina pregunta: Tu personaje " + preguntaMaquina.getNombre() + "?");
                    System.out.print("Respondes (s/n): ");
                    String respuestaTexto = teclado.nextLine();
                    boolean respuesta = respuestaTexto.equalsIgnoreCase("s");

                    preguntasHechasPorMaquina.add(preguntaMaquina.getNombre());

                    // --- ALGORITMO 1 (aplicado aca): Divide y Conquista / particion ---
                    vivosMaquina = decisorGreedy.reducirGrupo(vivosMaquina, preguntaMaquina, respuesta);
                }
            }

            // -----------------------------------------------------------
            // PARTE 2: tu turno. Podes arriesgar un ID directo en
            // cualquier momento (Busqueda Binaria), o preguntar una
            // caracteristica para seguir descartando de a poco.
            // -----------------------------------------------------------
            System.out.println("\n--- Tu turno (tus candidatos: " + vivosJugador.size() + ") ---");

            if (vivosJugador.size() == 1)
            {
                // Con un solo candidato, directamente se ofrece confirmar.
                Personaje unicoCandidato = vivosJugador.get(0);
                System.out.println("Solo te queda un candidato posible: " + unicoCandidato.getNombre());
                System.out.print("Queres arriesgar que ese es el personaje de la maquina? (s/n): ");
                String confirmar = teclado.nextLine();

                if (confirmar.equalsIgnoreCase("s"))
                {
                    if (unicoCandidato == secretoMaquina)
                    {
                        System.out.println(">>> Adivinaste el personaje de la maquina! Ganaste. <<<");
                        terminado = true;
                        continue;
                    }
                    else
                    {
                        System.out.println("No era ese. Raro, revisa tus respuestas anteriores.");
                    }
                }
            }
            else
            {
                // Con mas de un candidato, el jugador elige: arriesgar
                // un ID directo, o preguntar una caracteristica.
                System.out.print("Queres arriesgar un ID directo (1) o preguntar una caracteristica (2)? ");
                int eleccion = teclado.nextInt();
                teclado.nextLine();

                if (eleccion == 1)
                {
                    System.out.print("Ingresa el ID que arriesgas (1-23): ");
                    int idArriesgado = teclado.nextInt();
                    teclado.nextLine();

                    // --- ALGORITMO 1: Busqueda Binaria ---
                    // El jugador NUNCA ve la variable secretoMaquina, solo
                    // recibe el resultado de la comparacion.
                    Personaje candidatoArriesgado = buscadorBinario.buscarPorId(personajes, idArriesgado);

                    if (candidatoArriesgado == null)
                    {
                        System.out.println("Ese ID no existe. Perdiste el turno de pregunta.");
                    }
                    else if (candidatoArriesgado == secretoMaquina)
                    {
                        System.out.println(">>> Adivinaste el personaje de la maquina! Ganaste. <<<");
                        terminado = true;
                        continue;
                    }
                    else
                    {
                        System.out.println("No era " + candidatoArriesgado.getNombre() + ". Perdiste el turno de pregunta.");
                    }
                }
                else
                {
                    List<Caracteristica> disponibles = decisorGreedy.obtenerDisponibles(preguntasHechasPorJugador);

                    if (disponibles.isEmpty())
                    {
                        System.out.println("Ya no te quedan preguntas disponibles.");
                    }
                    else
                    {
                        System.out.println("Elegi que preguntarle a la maquina sobre su personaje secreto:");
                        for (int i = 0; i < disponibles.size(); i++)
                        {
                            System.out.println((i + 1) + ") " + disponibles.get(i).getNombre());
                        }
                        System.out.print("Opcion: ");
                        int opcionElegida = teclado.nextInt();
                        teclado.nextLine();

                        if (opcionElegida >= 1 && opcionElegida <= disponibles.size())
                        {
                            Caracteristica preguntaJugador = disponibles.get(opcionElegida - 1);

                            // El jugador NUNCA accede directamente a secretoMaquina.
                            // Solo obtiene la respuesta si/no a traves de evaluar().
                            boolean respuesta = preguntaJugador.evaluar(secretoMaquina);

                            System.out.println("Respuesta: el personaje de la maquina "
                                    + (respuesta ? "SI" : "NO") + " cumple '" + preguntaJugador.getNombre() + "'");

                            preguntasHechasPorJugador.add(preguntaJugador.getNombre());

                            // --- ALGORITMO 1 (aplicado aca): Divide y Conquista / particion ---
                            vivosJugador = decisorGreedy.reducirGrupo(vivosJugador, preguntaJugador, respuesta);
                        }
                        else
                        {
                            System.out.println("Opcion invalida, se pierde el turno de pregunta.");
                        }
                    }
                }
            }

            turno++;
        }
    }
}
