import javax.swing.*;

public class TCPServerGUI extends JFrame {

    private JTextArea messageArea;
    private JLabel statusLabel;

    public TCPServerGUI() {
        setTitle("Servidor TCP");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar ventana

        messageArea = new JTextArea();
        messageArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(messageArea);

        statusLabel = new JLabel("Estado: Esperando conexión...");

        add(scrollPane, "Center");
        add(statusLabel, "South");

        setVisible(true); // <-- ESTA LÍNEA ES MUY IMPORTANTE

        // Inicia el hilo del servidor
        ControlServer server = new ControlServer(this);
        server.start();
    }

    public void mostrarMensaje(String mensaje) {
        SwingUtilities.invokeLater(() -> {
            messageArea.append(mensaje + "\n");
        });
    }

    public void actualizarEstado(String estado) {
        SwingUtilities.invokeLater(() -> {
            statusLabel.setText("Estado: " + estado);
        });
    }

    public static void main(String[] args) {
        System.out.println("Iniciando ventana...");
        new TCPServerGUI();
    }
}

