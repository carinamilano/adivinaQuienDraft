package motor.maquinaVsMaquina;

import entidades.Personaje;
import algoritmos.DecisorGreedy;
import algoritmos.Caracteristica;

import java.util.ArrayList;
import java.util.List;

/**
 * MODO 2: MAQUINA VS MAQUINA
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

        List<Personaje> vivosM1 = new ArrayList<>();
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

            // -----------------------------------------------------------
            // MAQUINA 1 pregunta sobre el secreto de la MAQUINA 2
            // -----------------------------------------------------------
            System.out.println("\n[MAQUINA 1 pregunta] Candidatos de M1: " + vivosM1.size());

            if (vivosM1.size() == 1)
            {
                Personaje adivinado = vivosM1.get(0);
                System.out.println("[MAQUINA 1] Arriesga que el secreto de M2 es: " + adivinado.getNombre());
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
                    // El Greedy se quedo sin preguntas que sirvan (candidatos
                    // "gemelos" en las caracteristicas del pool). Como ultimo
                    // recurso, M1 arriesga al azar entre los que quedan.
                    System.out.println("[MAQUINA 1] No quedan preguntas utiles, arriesga al azar entre "
                            + vivosM1.size() + " candidatos.");

                    Personaje adivinadoAlAzarM1 = vivosM1.get((int) (Math.random() * vivosM1.size()));
                    System.out.println("[MAQUINA 1] Arriesga que el secreto de M2 es: " + adivinadoAlAzarM1.getNombre());

                    if (adivinadoAlAzarM1 == secretoM2)
                    {
                        System.out.println(">>> MAQUINA 1 ADIVINO el secreto de MAQUINA 2! Gana M1. <<<");
                        terminado = true;
                        continue;
                    }
                    else
                    {
                        System.out.println("[MAQUINA 1] Se equivoco. Pierde M1, gana M2.");
                        terminado = true;
                        continue;
                    }
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
                    // Mismo criterio que M1: si no hay preguntas utiles,
                    // arriesga al azar en vez de trabarse.
                    System.out.println("[MAQUINA 2] No quedan preguntas utiles, arriesga al azar entre "
                            + vivosM2.size() + " candidatos.");

                    Personaje adivinadoAlAzarM2 = vivosM2.get((int) (Math.random() * vivosM2.size()));
                    System.out.println("[MAQUINA 2] Arriesga que el secreto de M1 es: " + adivinadoAlAzarM2.getNombre());

                    if (adivinadoAlAzarM2 == secretoM1)
                    {
                        System.out.println(">>> MAQUINA 2 ADIVINO el secreto de MAQUINA 1! Gana M2. <<<");
                        terminado = true;
                        continue;
                    }
                    else
                    {
                        System.out.println("[MAQUINA 2] Se equivoco. Pierde M2, gana M1.");
                        terminado = true;
                        continue;
                    }
                }
            }

            turno++;
        }
    }
}