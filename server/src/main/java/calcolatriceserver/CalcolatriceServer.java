package calcolatriceserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class CalcolatriceServer {
    private static final int PORTA = 12345;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORTA)) {
            System.out.println("Server in ascolto sulla porta " + PORTA);

            while (true) {
                Socket clientSocket = serverSocket.accept();

                System.out.println("Client connesso");

                Thread thread = new Thread(new ClientHandler(clientSocket));
                thread.start();
            }

        } catch (IOException e) {
            System.err.println("Impossibile avviare o mantenere attivo il server: "
                    + e.getMessage());
        }
    }
}
