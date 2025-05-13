import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TCPClientGUI extends JFrame {
    private JTextField inputField;
    private JTextArea messageArea;
    private JButton sendButton, clearButton;
    private JLabel statusLabel, counterLabel;

    private ControlClient controlClient;
    private int messageCounter = 0;

    public TCPClientGUI() {
        setTitle("Cliente TCP");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Área para mostrar mensajes
        messageArea = new JTextArea();
        messageArea.setEditable(false);
        add(new JScrollPane(messageArea), BorderLayout.CENTER);

        // Campo de entrada y botón de envío
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputField = new JTextField();
        sendButton = new JButton("Enviar");

        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        add(inputPanel, BorderLayout.NORTH);

        // Panel inferior con botones y etiquetas
        JPanel bottomPanel = new JPanel(new GridLayout(1, 3));

        clearButton = new JButton("Limpiar");
        counterLabel = new JLabel("Mensajes enviados: 0");
        statusLabel = new JLabel("Estado: Desconectado");

        bottomPanel.add(clearButton);
        bottomPanel.add(counterLabel);
        bottomPanel.add(statusLabel);

        add(bottomPanel, BorderLayout.SOUTH);

        // Controlador de conexión
        controlClient = new ControlClient(this);

        // Eventos
        sendButton.addActionListener(e -> enviarMensaje());
        inputField.addActionListener(e -> enviarMensaje());
        clearButton.addActionListener(e -> messageArea.setText(""));

        setVisible(true);
    }

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
        new TCPClientGUI();
    }
}

