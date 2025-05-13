import java.io.*;
import java.net.*;

// Controla la lógica de red del cliente
// ↪️ POO: Encapsulamiento, Composición
public class ControlClient {

    private TCPClientGUI gui; // Composición el controlador necesita acceso a la interfaz

    public ControlClient(TCPClientGUI gui) {
        this.gui = gui;
    }

    public void enviarAlServidor(String mensaje) {
        // Nueva hebra para no bloquear la interfaz gráfica
        new Thread(() -> {
            try {
                gui.actualizarEstado("Conectando...");
                Socket clientSocket = new Socket("localhost", 9999); // Conexión al servidor

                // Flujos de comunicación
                DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
                BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                outToServer.writeBytes(mensaje + '\n'); // Enviar mensaje
                gui.mostrarMensaje("Enviado: " + mensaje);

                String respuesta = inFromServer.readLine(); // Leer respuesta
                gui.mostrarMensaje("Respuesta: " + respuesta);

                clientSocket.close(); // Cierre
                gui.actualizarEstado("Desconectado");
                gui.incrementarContador(); // Actualiza contador

            } catch (IOException e) {
                gui.mostrarMensaje("Error: " + e.getMessage());
                gui.actualizarEstado("Desconectado");
            }
        }).start();
    }
}
