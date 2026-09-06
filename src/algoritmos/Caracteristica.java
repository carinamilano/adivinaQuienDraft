package algoritmos;

import entidades.Personaje;

/**
 * Representa UNA pregunta posible del juego (ej: "calvo", "usaLentes",
 * "peloNegro"). Cubre exactamente los filtros que pide la consigna: genero,
 * calvicie, lentes y color de pelo (colorado, negro, amarillo). Como el
 * color de pelo tiene 3 valores posibles (no es si/no), se modela como 3
 * preguntas booleanas separadas ("tiene el pelo colorado?", "...negro?",
 * "...amarillo?"), una por color, para que encajen con el mismo esquema
 * de pregunta si/no que usa el resto del juego.
 *
 * ANALOGIA: pensa esta clase como una "tarjeta de pregunta" del juego de
 * mesa Adivina Quien. Cada tarjeta trae escrita una sola pregunta (el
 * campo "nombre"). Cuando le "mostras" la tarjeta a un personaje puntual
 * (llamando a evaluar(p)), la tarjeta misma sabe que atributo de
 * Personaje tiene que consultar para responder si/no, sin que quien la
 * usa (DecisorGreedy) necesite saber esos detalles.
 *
 * Gracias a esto, DecisorGreedy puede tener un "mazo" de 6 tarjetas
 * (todasLasCaracteristicas) y repasarlas todas con el mismo codigo
 * (c.evaluar(p)), en vez de escribir un bloque de codigo distinto para
 * cada atributo de Personaje.
 *
 * Internamente usa un switch: segun el texto guardado en "nombre",
 * decide a que dato de Personaje consultar.
 */

public class Caracteristica {

    private String nombre;

    public Caracteristica(String nombre)
    {
        this.nombre = nombre;
    }

    public String getNombre()
    {
        return nombre;
    }

    /**
     * Evalua esta caracteristica sobre un personaje puntual.
     * Segun el nombre guardado, consulta el atributo correspondiente.
     */
    public boolean evaluar(Personaje p)
    {
        switch (nombre)
        {
            case "esMujer":
                return p.isEsMujer();
            case "calvo":
                return p.isCalvo();
            case "usaLentes":
                return p.isUsaLentes();
            case "peloColorado":
                return "Colorado".equals(p.getColorPelo());
            case "peloNegro":
                return "Negro".equals(p.getColorPelo());
            case "peloAmarillo":
                return "Amarillo".equals(p.getColorPelo());
            default:
                return false; // nombre no reconocido, no deberia pasar nunca
        }
    }
}
