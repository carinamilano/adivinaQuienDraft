package motor.maquinaVsMaquina;

import entidades.Personaje;
import algoritmos.DecisorGreedy;
import algoritmos.Caracteristica;

import java.util.ArrayList;
import java.util.List;

/**
 * MODO 2: MAQUINA VS MAQUINA (simetrico, con todo el proceso a la
 * vista y etiquetado por cada maquina).
 * Es el modo que mas exhibe el algoritmo Greedy: ambas maquinas usan
 * DecisorGreedy para elegir que preguntar, y en cada turno se imprime
 * el detalle completo de la decision (que caracteristica se evaluo,
 * con que peor-caso, y cual se eligio finalmente).
 */
public class PartidaMaquinaVsMaquina {

    private Personaje[] personajes;
    private DecisorGreedy decisorGreedy;

    public PartidaMaquinaVsMaquina(Personaje[] personajes)
    {
        this.personajes = personajes;
        this.decisorGreedy = new DecisorGreedy();
    }

    public void jugar()
    {
        System.out.println("\n--- Maquina 1 vs Maquina 2 ---");

        Personaje secretoM1 = personajes[(int) (Math.random() * personajes.length)];
        Personaje secretoM2 = personajes[(int) (Math.random() * personajes.length)];

        System.out.println("(Los secretos de ambas maquinas se ocultan; solo se muestra el proceso de busqueda)");

        // Grupo que maneja la MAQUINA 1 para adivinar el secreto de la MAQUINA 2
        List<Personaje> vivosM1 = new ArrayList<>();
        // Grupo que maneja la MAQUINA 2 para adivinar el secreto de la MAQUINA 1
        List<Personaje> vivosM2 = new ArrayList<>();
        for (Personaje p : personajes)
        {
            vivosM1.add(p);
            vivosM2.add(p);
        }

        List<String> preguntasHechasM1 = new ArrayList<>();
        List<String> preguntasHechasM2 = new ArrayList<>();

        int turno = 1;
        boolean terminado = false;

        while (!terminado)
        {
            System.out.println("\n========== TURNO " + turno + " ==========");

            // Flags para saber si en ESTE turno cada maquina pudo hacer
            // algo util (o adivinar, o preguntar). Si ninguna de las dos
            // puede avanzar en el mismo turno, cortamos para no quedar
            // en loop infinito.
            boolean m1AvanzoAlgo = false;
            boolean m2AvanzoAlgo = false;

            // -----------------------------------------------------------
            // MAQUINA 1 pregunta sobre el secreto de la MAQUINA 2
            // -----------------------------------------------------------
            System.out.println("\n[MAQUINA 1 pregunta] Candidatos de M1: " + vivosM1.size());

            if (vivosM1.size() == 1)
            {
                Personaje adivinado = vivosM1.get(0);
                System.out.println("[MAQUINA 1] Arriesga que el secreto de M2 es: " + adivinado.getNombre());
                m1AvanzoAlgo = true;
                if (adivinado == secretoM2)
                {
                    System.out.println(">>> MAQUINA 1 ADIVINO el secreto de MAQUINA 2! Gana M1. <<<");
                    terminado = true;
                    continue;
                }
            }
            else
            {
                Caracteristica preguntaM1 = decisorGreedy.elegirMejorPregunta(vivosM1, preguntasHechasM1);

                if (preguntaM1 != null)
                {
                    m1AvanzoAlgo = true;
                    boolean respuesta = preguntaM1.evaluar(secretoM2);

                    System.out.println("[MAQUINA 1] Pregunta: '" + preguntaM1.getNombre()
                            + "' -> Respuesta real del secreto de M2: " + respuesta);

                    preguntasHechasM1.add(preguntaM1.getNombre());

                    int tamAntes = vivosM1.size();
                    vivosM1 = decisorGreedy.reducirGrupo(vivosM1, preguntaM1, respuesta);
                    System.out.println("[MAQUINA 1] Grupo reducido de " + tamAntes + " a " + vivosM1.size() + " candidatos.");
                }
                else
                {
                    System.out.println("[MAQUINA 1] No quedan preguntas utiles.");
                }
            }

            // -----------------------------------------------------------
            // MAQUINA 2 pregunta sobre el secreto de la MAQUINA 1
            // -----------------------------------------------------------
            System.out.println("\n[MAQUINA 2 pregunta] Candidatos de M2: " + vivosM2.size());

            if (vivosM2.size() == 1)
            {
                Personaje adivinado = vivosM2.get(0);
                System.out.println("[MAQUINA 2] Arriesga que el secreto de M1 es: " + adivinado.getNombre());
                m2AvanzoAlgo = true;
                if (adivinado == secretoM1)
                {
                    System.out.println(">>> MAQUINA 2 ADIVINO el secreto de MAQUINA 1! Gana M2. <<<");
                    terminado = true;
                    continue;
                }
            }
            else
            {
                Caracteristica preguntaM2 = decisorGreedy.elegirMejorPregunta(vivosM2, preguntasHechasM2);

                if (preguntaM2 != null)
                {
                    m2AvanzoAlgo = true;
                    boolean respuesta = preguntaM2.evaluar(secretoM1);

                    System.out.println("[MAQUINA 2] Pregunta: '" + preguntaM2.getNombre()
                            + "' -> Respuesta real del secreto de M1: " + respuesta);

                    preguntasHechasM2.add(preguntaM2.getNombre());

                    int tamAntes = vivosM2.size();
                    vivosM2 = decisorGreedy.reducirGrupo(vivosM2, preguntaM2, respuesta);
                    System.out.println("[MAQUINA 2] Grupo reducido de " + tamAntes + " a " + vivosM2.size() + " candidatos.");
                }
                else
                {
                    System.out.println("[MAQUINA 2] No quedan preguntas utiles.");
                }
            }

            // Corte de seguridad: si NINGUNA de las dos pudo avanzar en
            // este turno (ni preguntar, ni adivinar), no tiene sentido
            // seguir turnando - terminamos sin ganador claro.
            if (!m1AvanzoAlgo && !m2AvanzoAlgo)
            {
                System.out.println("\nNinguna maquina puede seguir avanzando. Fin de la partida sin ganador claro.");
                terminado = true;
            }

            turno++;
        }
    }
}