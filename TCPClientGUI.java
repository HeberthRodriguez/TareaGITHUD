import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


// Aplica POO:Herencia, Encapsulamiento, Composición
public class TCPClientGUI extends JFrame {

    private JTextField inputField;
    private JTextArea messageArea;
    private JButton sendButton, clearButton;
    private JLabel statusLabel, counterLabel;

    // Relación de composición: el cliente "tiene un" controlador de red
    private ControlClient controlClient;

    // Encapsulamos al contador
    private int messageCounter = 0;

    public TCPClientGUI() {
        setTitle("Cliente TCP");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Interfaz principal (área de mensajes)
        messageArea = new JTextArea();
        messageArea.setEditable(false);
        add(new JScrollPane(messageArea), BorderLayout.CENTER);

        // Entrada de texto y botón de envío
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputField = new JTextField();
        sendButton = new JButton("Enviar");

        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        add(inputPanel, BorderLayout.NORTH);

        // Panel inferior: estado, contador y botón de limpieza
        JPanel bottomPanel = new JPanel(new GridLayout(1, 3));
        clearButton = new JButton("Limpiar");
        counterLabel = new JLabel("Mensajes enviados: 0");
        statusLabel = new JLabel("Estado: Desconectado");

        bottomPanel.add(clearButton);
        bottomPanel.add(counterLabel);
        bottomPanel.add(statusLabel);

        add(bottomPanel, BorderLayout.SOUTH);

        // Crea el controlador del cliente POO: Composición
        controlClient = new ControlClient(this);

        // Eventos del GUI
        sendButton.addActionListener(e -> enviarMensaje());
        inputField.addActionListener(e -> enviarMensaje());
        clearButton.addActionListener(e -> messageArea.setText(""));

        setVisible(true);
    }

    // Envía el mensaje al servidor usando el objeto de ControlClient
    private void enviarMensaje() {
        String mensaje = inputField.getText().trim();
        if (mensaje.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No puedes enviar un mensaje vacío.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        inputField.setText("");
        controlClient.enviarAlServidor(mensaje);
    }

    public void mostrarMensaje(String mensaje) {
        SwingUtilities.invokeLater(() -> messageArea.append(mensaje + "\n"));
    }

    public void actualizarEstado(String estado) {
        SwingUtilities.invokeLater(() -> statusLabel.setText("Estado: " + estado));
    }

    public void incrementarContador() {
        messageCounter++;
        counterLabel.setText("Mensajes enviados: " + messageCounter);
    }

    public static void main(String[] args) {
        new TCPClientGUI(); // Instancia principal
    }
}
