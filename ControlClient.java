import java.io.*;
import java.net.*;

public class ControlClient {
    private TCPClientGUI gui;

    public ControlClient(TCPClientGUI gui) {
        this.gui = gui;
    }

    public void enviarAlServidor(String mensaje) {
        new Thread(() -> {
            try {
                gui.actualizarEstado("Conectando...");
                Socket clientSocket = new Socket("localhost", 9999);

                DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
                BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                outToServer.writeBytes(mensaje + '\n');
                gui.mostrarMensaje("Enviado: " + mensaje);

                String respuesta = inFromServer.readLine();
                gui.mostrarMensaje("Respuesta: " + respuesta);

                clientSocket.close();
                gui.actualizarEstado("Desconectado");

                gui.incrementarContador();

            } catch (IOException e) {
                gui.mostrarMensaje("Error: " + e.getMessage());
                gui.actualizarEstado("Desconectado");
            }
        }).start();
    }
}
