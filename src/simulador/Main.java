package simulador;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        // Toda interação com Swing deve acontecer na Event Dispatch Thread
        SwingUtilities.invokeLater(() -> new SimuladorFrame().setVisible(true));
    }
}
