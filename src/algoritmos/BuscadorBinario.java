package algoritmos;

import entidades.Personaje;

/**
 * ALGORITMO: Divide y Conquista (Busqueda Binaria).
 *
 * POR QUE LO USAMOS ACA:
 * La consigna dice que "a medida que avanza el juego los usuarios pueden
 * lanzar directamente su suposicion". Cuando un jugador (humano o maquina)
 * arriesga un ID puntual, necesitamos confirmar rapido si existe y
 * encontrarlo. Como el array YA esta ordenado por ID (gracias a
 * OrdenadorMerge, que corre antes de esto), podemos buscar dividiendo el
 * rango al medio en vez de recorrer todo el array uno por uno.
 *
 * COMO RESUELVE EL PROBLEMA (Divide y Conquista, variante "descartar"):
 *  - Se mira el elemento del medio del rango actual.
 *  - Si es el ID buscado, se encontro.
 *  - Si el ID buscado es mayor, se descarta toda la mitad izquierda y se
 *    sigue buscando solo en la derecha.
 *  - Si es menor, se descarta la mitad derecha y se sigue en la izquierda.
 *  - Caso base: el rango se cruza (no se encontro) o se encuentra el ID.
 *
 * Fijate que ACA NO HAY PASO DE "COMBINAR" (a diferencia de OrdenadorMerge):
 * simplemente se descarta una mitad entera en cada paso.
 *
 * POR QUE NO USAMOS BUSQUEDA LINEAL:
 * Recorrer el array uno por uno (O(n)) funcionaria igual con solo 23
 * personajes, pero no demostraria el uso de Divide y Conquista para este
 * paso, y con la lista ya ordenada no tiene sentido no aprovechar esa
 * ventaja.
 *
 * COMPLEJIDAD: O(log n)
 */
public class BuscadorBinario {

    /**
     * Busca un personaje por ID en un array YA ORDENADO por ID.
     * Devuelve el Personaje si lo encuentra, o null si no existe ese ID.
     */
    public Personaje buscarPorId(Personaje[] personajes, int idBuscado)
    {
        int inicio = 0;
        int fin = personajes.length - 1;

        while (inicio <= fin)
        {
            int medio = (inicio + fin) / 2;
            int idMedio = personajes[medio].getId();

            if (idMedio == idBuscado)
            {
                return personajes[medio]; // encontrado
            }
            else if (idBuscado > idMedio)
            {
                inicio = medio + 1; // descarto la mitad izquierda
            }
            else
            {
                fin = medio - 1; // descarto la mitad derecha
            }
        }

        return null; // no existe ese ID
    }
}