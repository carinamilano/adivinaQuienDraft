package algoritmos;

import entidades.Personaje;

/**
 * ALGORITMO: Divide y Conquista (Merge Sort).
 *
 * POR QUE LO USAMOS ACA:
 * La consigna dice que los personajes "empiezan ordenados unicamente segun
 * su genero" y que "es la maquina quien debe disponerlos en una lista
 * ordenada de forma autoincremental segun se agregan". Es decir: recibimos
 * un array desordenado por ID (agrupado solo por genero) y necesitamos
 * dejarlo ordenado 1..23 antes de arrancar el juego.
 *
 * COMO RESUELVE EL PROBLEMA (Divide y Conquista clasico):
 *  - DIVIDIR: se parte el array en dos mitades.
 *  - CONQUISTAR: se ordena cada mitad por separado, llamandose a si mismo
 *    recursivamente (caso base: un array de 0 o 1 elemento ya esta ordenado).
 *  - COMBINAR: se mezclan (merge) las dos mitades ya ordenadas comparando
 *    de a un elemento por vez, armando el resultado final ordenado.
 *
 * POR QUE NO USAMOS OTRO ALGORITMO ACA:
 * Podriamos haber usado Busqueda Binaria para "insertar ordenado" cada
 * personaje uno por uno (ordenamiento por insercion binaria), pero eso
 * seria O(n^2) en el peor caso por los corrimientos de elementos. Merge
 * Sort nos da O(n log n) real, que es el ejemplo de Divide y Conquista con
 * "combinar" que se vio en la teoria (a diferencia de la Busqueda Binaria,
 * que no combina nada, solo descarta una mitad).
 *
 * COMPLEJIDAD: O(n log n)
 */
public class OrdenadorMerge {

    /**
     * Punto de entrada publico: ordena el array completo por ID.
     */
    public void ordenar(Personaje[] personajes)
    {
        if (personajes == null || personajes.length <= 1)
        {
            return; // caso trivial, nada que ordenar
        }
        mergeSort(personajes, 0, personajes.length - 1);
    }

    /**
     * DIVIDIR + CONQUISTAR: parte el rango [inicio, fin] al medio y
     * ordena cada mitad recursivamente.
     */
    private void mergeSort(Personaje[] personajes, int inicio, int fin)
    {
        if (inicio >= fin)
        {
            return; // caso base: 0 o 1 elemento, ya esta "ordenado"
        }

        int medio = (inicio + fin) / 2;

        mergeSort(personajes, inicio, medio);       // ordena mitad izquierda
        mergeSort(personajes, medio + 1, fin);       // ordena mitad derecha

        combinar(personajes, inicio, medio, fin);    // combina ambas mitades
    }

    /**
     * COMBINAR: mezcla dos mitades ya ordenadas (izquierda y derecha) en
     * una sola secuencia ordenada por ID.
     */
    private void combinar(Personaje[] personajes, int inicio, int medio, int fin)
    {
        int tamIzquierda = medio - inicio + 1;
        int tamDerecha = fin - medio;

        Personaje[] izquierda = new Personaje[tamIzquierda];
        Personaje[] derecha = new Personaje[tamDerecha];

        for (int i = 0; i < tamIzquierda; i++)
        {
            izquierda[i] = personajes[inicio + i];
        }
        for (int j = 0; j < tamDerecha; j++)
        {
            derecha[j] = personajes[medio + 1 + j];
        }

        int i = 0;
        int j = 0;
        int k = inicio;

        // Comparo de a un elemento de cada mitad y coloco el de ID menor
        while (i < tamIzquierda && j < tamDerecha)
        {
            if (izquierda[i].getId() <= derecha[j].getId())
            {
                personajes[k] = izquierda[i];
                i++;
            }
            else
            {
                personajes[k] = derecha[j];
                j++;
            }
            k++;
        }

        // Si sobraron elementos de alguna mitad, se copian tal cual
        while (i < tamIzquierda)
        {
            personajes[k] = izquierda[i];
            i++;
            k++;
        }
        while (j < tamDerecha)
        {
            personajes[k] = derecha[j];
            j++;
            k++;
        }
    }
}