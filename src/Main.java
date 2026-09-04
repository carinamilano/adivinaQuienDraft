import GUI.MiVentana;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

/**
 * Punto de entrada del programa. Inicializa la interfaz gráfica principal.
 */
public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Adivina Quién - Superhéroes");
        MiVentana ventana = new MiVentana();
        frame.setContentPane(ventana.getPanelPrincipal());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Tamaño ideal para que entren los 23 personajes y sus nombres, pero
        // sin superar el area util de la pantalla (deja afuera la barra de
        // tareas) para que en notebooks mas chicas no se corten los botones
        // de abajo (Preguntar/Arriesgar/Volver al Menu).
        Rectangle pantallaUtil = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        int ancho = Math.min(1000, pantallaUtil.width);
        int alto = Math.min(800, pantallaUtil.height);
        frame.setSize(new Dimension(ancho, alto));

        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setVisible(true);
    }
}
