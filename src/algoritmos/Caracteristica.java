package algoritmos;

import entidades.Personaje;

/**
 * Representa UNA pregunta posible del juego (ej: "vuela", "usaCapa").
 *
 * ANALOGIA: pensa esta clase como una "tarjeta de pregunta" del juego de
 * mesa Adivina Quien. Cada tarjeta trae escrita una sola pregunta (el
 * campo "nombre"). Cuando le "mostras" la tarjeta a un personaje puntual
 * (llamando a evaluar(p)), la tarjeta misma sabe que atributo de
 * Personaje tiene que consultar para responder si/no, sin que quien la
 * usa (DecisorGreedy) necesite saber esos detalles.
 *
 * Gracias a esto, DecisorGreedy puede tener un "mazo" de 7 tarjetas
 * (todasLasCaracteristicas) y repasarlas todas con el mismo codigo
 * (c.evaluar(p)), en vez de escribir un bloque de codigo distinto para
 * cada atributo booleano de Personaje.
 *
 * Internamente usa un switch: segun el texto guardado en "nombre",
 * decide a que metodo de Personaje llamar (isVuela, isEsDC, etc.).
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
            case "vuela":
                return p.isVuela();
            case "usaCapa":
                return p.isUsaCapa();
            case "esDC":
                return p.isEsDC();
            case "tienePoderesMagicos":
                return p.isTienePoderesMagicos();
            case "usaMascara":
                return p.isUsaMascara();
            case "tieneSuperFuerza":
                return p.isTieneSuperFuerza();
            case "esHumano":
                return p.isEsHumano();
            default:
                return false; // nombre no reconocido, no deberia pasar nunca
        }
    }
}