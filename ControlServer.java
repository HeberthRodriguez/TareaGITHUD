import java.io.*;
import java.net.*;

public class ControlServer extends Thread {
    private TCPServerGUI gui;
    private ServerSocket serverSocket;

    public ControlServer(TCPServerGUI gui) {
        this.gui = gui;
        try {
            serverSocket = new ServerSocket(9999);
        } catch (IOException e) {
            gui.mostrarMensaje("❌ Error al iniciar el servidor. Verifica si el puerto 6789 ya está en uso.");
            serverSocket = null; // ← Aquí evitamos que el servidor arranque mal
        }
    }

    @Override
    public void run() {
        // ⚠️ Verifica si el serverSocket fue creado correctamente
        if (serverSocket == null) {
            gui.mostrarMensaje("🚫 No se pudo iniciar el servidor. Abortando...");
            return;
        }

        gui.mostrarMensaje("✅ Servidor iniciado en el puerto 6789.");

        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();

                gui.actualizarEstado("Cliente conectado desde " + clientSocket.getInetAddress());

                BufferedReader inFromClient = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
                DataOutputStream outToClient = new DataOutputStream(clientSocket.getOutputStream());

                String clientMessage = inFromClient.readLine();
                gui.mostrarMensaje("Cliente: " + clientMessage);

                String response = clientMessage.toUpperCase();
                outToClient.writeBytes(response + "\n");

                gui.mostrarMensaje("Enviado: " + response);

                clientSocket.close();
                gui.actualizarEstado("Esperando conexiones...");

            } catch (IOException e) {
                gui.mostrarMensaje("Error al comunicar con el cliente: " + e.getMessage());
            }
        }
    }
}
