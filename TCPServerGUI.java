import javax.swing.*;

// Esta clase representa la interfaz gráfica del servidor TCP
// Aplica POO: Encapsulamiento, Herencia (de JFrame)
public class TCPServerGUI extends JFrame {

    // Atributos privados seria encapsulamiento Encapsulamiento
    private JTextArea messageArea;
    private JLabel statusLabel;

    public TCPServerGUI() {
        setTitle("Servidor TCP");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar ventana

        // Configura el área de mensajes
        messageArea = new JTextArea();
        messageArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(messageArea);

        statusLabel = new JLabel("Estado: Esperando conexión...");

        add(scrollPane, "Center");
        add(statusLabel, "South");

        setVisible(true); // Este método muestra la GUI

        // Crea un objeto de ControlServer (Instanciación)
        ControlServer server = new ControlServer(this);
        server.start(); // Herencia de Thread
    }

    // Método público para mostrar mensajes en la GUI
    public void mostrarMensaje(String mensaje) {
        SwingUtilities.invokeLater(() -> {
            messageArea.append(mensaje + "\n");
        });
    }

    //Método para actualizar el estado del servidor (estado de conexión)
    public void actualizarEstado(String estado) {
        SwingUtilities.invokeLater(() -> {
            statusLabel.setText("Estado: " + estado);
        });
    }

    public static void main(String[] args) {
        System.out.println("Iniciando ventana...");
        new TCPServerGUI(); // Instanciación de objeto principal del servidor
    }
}
