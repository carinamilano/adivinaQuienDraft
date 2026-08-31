import motor.GestorPartida;

/**
 * Punto de entrada del programa. Su unica responsabilidad es crear el
 * GestorPartida y arrancarlo.
 */

public class Main {
    public static void main(String[] args)
    {
        GestorPartida partida = new GestorPartida();
        partida.iniciar();
    }
}