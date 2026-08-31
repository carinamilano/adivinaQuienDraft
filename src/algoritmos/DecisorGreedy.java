package algoritmos;

import entidades.Personaje;
import java.util.ArrayList;
import java.util.List;

/**
 * ALGORITMO: Greedy (voraz).

 * POR QUE LO USAMOS ACA:
 * En cada turno la maquina tiene que decidir QUE caracteristica preguntar
 * (vuela, usaCapa, esDC, etc.) para achicar lo mas posible el grupo de
 * personajes "vivos" (los que todavia podrian ser el personaje secreto).

 * ELEMENTOS DEL ALGORITMO GREEDY (mismo molde que "el problema del cambio"
 * de monedas, visto en la teoria):
 *  - CONJUNTO DE CANDIDATOS: las caracteristicas todavia no preguntadas.
 *  - FUNCION DE SELECCION: elegir la caracteristica que divide el grupo
 *    actual lo mas parejo posible (mas cerca de 50/50).
 *  - FUNCION DE FACTIBILIDAD: descartar caracteristicas ya usadas, o que
 *    no separan nada (si TODOS los vivos vuelan, o NINGUNO vuela,
 *    preguntar "vuela" no aporta informacion).
 *  - FUNCION SOLUCION: se llega a la solucion cuando queda 1 solo
 *    personaje candidato.

 * POR QUE "MEJOR PREGUNTA" = LA MAS PAREJA (no la que mas descarta ni la
 * que mas conserva):
 * No controlamos la respuesta que nos va a tocar, asi que hay que asumir
 * el PEOR caso posible (quedarnos con el subgrupo mas grande de los dos).
 * La pregunta que minimiza ese peor caso es la que divide mas parejo.
 * Por eso usamos Math.max(cantSi, cantNo) como "puntaje de peor caso", y
 * buscamos la caracteristica con el Math.max mas chico entre todas.
 *
 * POR QUE NO USAMOS OTROS ALGORITMOS GREEDY VISTOS EN CLASE:
 *  - Mochila fraccionaria: rankea por relacion valor/peso: no aplica
 *    porque nuestras "preguntas" no tienen costo/beneficio distinto.
 *  - Codigo de Huffman: arma un arbol completo de antemano conociendo
 *    todas las frecuencias fijas. Nuestro juego decide la siguiente
 *    pregunta SOBRE LA MARCHA turno a turno, no de antemano.
 *  - Matrimonios estables: resuelve un problema de emparejamiento con
 *    preferencias cruzadas entre dos conjuntos, no tiene relacion con
 *    elegir una pregunta.
 *
 * DONDE ENTRA DIVIDE Y CONQUISTA EN ESTA MISMA CLASE:
 * El metodo reducirGrupo() de aca abajo es el que, una vez que se sabe
 * la respuesta a la pregunta elegida, PARTE el grupo de vivos en dos
 * subgrupos (cumple / no cumple) y descarta el que no corresponde.
 */
public class DecisorGreedy {

    private List<Caracteristica> todasLasCaracteristicas;

    public DecisorGreedy()
    {
        todasLasCaracteristicas = new ArrayList<>();
        todasLasCaracteristicas.add(new Caracteristica("vuela"));
        todasLasCaracteristicas.add(new Caracteristica("usaCapa"));
        todasLasCaracteristicas.add(new Caracteristica("esDC"));
        todasLasCaracteristicas.add(new Caracteristica("tienePoderesMagicos"));
        todasLasCaracteristicas.add(new Caracteristica("usaMascara"));
        todasLasCaracteristicas.add(new Caracteristica("tieneSuperFuerza"));
        todasLasCaracteristicas.add(new Caracteristica("esHumano"));
        // Nota: "esMujer" NO se incluye aca. Ya se usa para el orden
        // inicial del array (agrupado por genero), no tiene sentido
        // volver a "preguntarla" durante la partida, ademas hay 12 que son mujer y 11 que no
        // entonces como es casi 50 y 50 la maquina a a hacer siempre esa primer pregunta
    }

    /**
     * PASO GREEDY: recorre las caracteristicas todavia no usadas y elige
     * la que divide el grupo de vivos lo mas parejo posible.
     *
     * Si dos o mas caracteristicas empatan en el mismo peor-caso, se
     * elige una al azar entre las empatadas (en vez de quedarse siempre
     * con la primera que aparece en la lista). Esto evita que la maquina
     * repita siempre la misma primera pregunta en todas las partidas.
     */
    public Caracteristica elegirMejorPregunta(List<Personaje> vivos, List<String> yaPreguntadas)
    {
        List<Caracteristica> empatadas = new ArrayList<>();
        int mejorPeorCaso = Integer.MAX_VALUE;

        for (Caracteristica c : todasLasCaracteristicas)
        {
            if (yaPreguntadas.contains(c.getNombre()))
            {
                continue;
            }

            int cantSi = 0;
            int cantNo = 0;

            for (Personaje p : vivos)
            {
                if (c.evaluar(p))
                {
                    cantSi++;
                }
                else
                {
                    cantNo++;
                }
            }

            if (cantSi == 0 || cantNo == 0)
            {
                continue;
            }

            int peorCaso = Math.max(cantSi, cantNo);

            System.out.println("   [Greedy] Evaluando '" + c.getNombre()
                    + "' -> Si:" + cantSi + " No:" + cantNo
                    + " (peor caso: " + peorCaso + ")");

            if (peorCaso < mejorPeorCaso)
            {
                // Encontramos una caracteristica estrictamente mejor:
                // reiniciamos la lista de empatadas con esta sola.
                mejorPeorCaso = peorCaso;
                empatadas.clear();
                empatadas.add(c);
            }
            else if (peorCaso == mejorPeorCaso)
            {
                // Empata con la mejor encontrada hasta ahora: se suma
                // a la lista de candidatas empatadas.
                empatadas.add(c);
            }
        }

        if (empatadas.isEmpty())
        {
            return null; // no hay ninguna caracteristica factible
        }

        if (empatadas.size() == 1)
        {
            return empatadas.get(0);
        }

        // Hay empate entre varias: elegimos una al azar para que la
        // maquina no repita siempre la misma pregunta en el mismo turno.
        int indiceAzar = (int) (Math.random() * empatadas.size());

        System.out.println("   [Greedy] Empate entre " + empatadas.size()
                + " caracteristicas, elegida al azar: " + empatadas.get(indiceAzar).getNombre());

        return empatadas.get(indiceAzar);
    }

    /**
     * PASO DIVIDE Y CONQUISTA: dado el grupo de vivos, la caracteristica
     * elegida y la respuesta obtenida, arma el nuevo subgrupo descartando
     * a los que no corresponden.
     */
    public List<Personaje> reducirGrupo(List<Personaje> vivos, Caracteristica c, boolean respuesta)
    {
        List<Personaje> nuevoGrupo = new ArrayList<>();

        for (Personaje p : vivos)
        {
            if (c.evaluar(p) == respuesta)
            {
                nuevoGrupo.add(p);
            }
        }

        return nuevoGrupo;
    }

    /**
     * Devuelve la lista de caracteristicas que todavia no se preguntaron,
     * SIN aplicar la logica greedy de seleccion. Se usa para mostrarle al
     * jugador humano un menu de opciones, ya que el es quien elige
     * manualmente que preguntar (a diferencia de la maquina, que decide
     * sola con el algoritmo greedy).
     */
    public List<Caracteristica> obtenerDisponibles(List<String> yaPreguntadas)
    {
        List<Caracteristica> disponibles = new ArrayList<>();

        for (Caracteristica c : todasLasCaracteristicas)
        {
            if (!yaPreguntadas.contains(c.getNombre()))
            {
                disponibles.add(c);
            }
        }

        return disponibles;
    }
}