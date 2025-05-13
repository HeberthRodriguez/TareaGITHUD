import java.io.*;
import java.net.*;

// Clase que representa el hilo del servidor
// POO: Herencia (de Thread), Composición, Encapsulamiento
public class ControlServer extends Thread {
    private TCPServerGUI gui;        // Composición: el servidor tiene una GUI
    private ServerSocket serverSocket;

    public ControlServer(TCPServerGUI gui) {
        this.gui = gui;
        try {
            serverSocket = new ServerSocket(9999); // Puerto fijo
        } catch (IOException e) {
            gui.mostrarMensaje("Error al iniciar el servidor. Verifica si el puerto 6789 ya está en uso.");
            serverSocket = null; // Previene un mal inicio
        }
    }

    @Override
    public void run() {
        if (serverSocket == null) {
            gui.mostrarMensaje("No se pudo iniciar el servidor. Abortando...");
            return;
        }

        gui.mostrarMensaje(" Servidor iniciado en el puerto 6789.");

        while (true) {
            try {
                // Espera conexión
                Socket clientSocket = serverSocket.accept();
                gui.actualizarEstado("Cliente conectado desde " + clientSocket.getInetAddress());

                // Flujo de entrada/salida con el cliente
                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                DataOutputStream outToClient = new DataOutputStream(clientSocket.getOutputStream());

                String clientMessage = inFromClient.readLine(); // Recibe mensaje
                gui.mostrarMensaje("Cliente: " + clientMessage);

                // Procesamiento del mensaje (convierte a mayúsculas)
                String response = clientMessage.toUpperCase();
                outToClient.writeBytes(response + "\n"); // Responde al cliente
                gui.mostrarMensaje("Enviado: " + response);

                // Cierra conexión
                clientSocket.close();
                gui.actualizarEstado("Esperando conexiones...");

            } catch (IOException e) {
                gui.mostrarMensaje("Error al comunicar con el cliente: " + e.getMessage());
            }
        }
    }
}

