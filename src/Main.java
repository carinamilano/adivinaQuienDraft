import GUI.MiVentana;
import javax.swing.JFrame;

/**
 * Punto de entrada del programa. Inicializa la interfaz gráfica principal.
 */
public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Adivina Quién - Superhéroes");
        MiVentana ventana = new MiVentana();
        frame.setContentPane(ventana.getPanelPrincipal());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Tamaño amplio para que entren perfectamente los 23 personajes y sus nombres
        frame.setSize(1000, 800);

        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}