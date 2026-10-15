package calcolatriceclient;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class CalcolatriceClient {
    private static final String HOST = "localhost";
    private static final int PORTA = 12345;

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            

            try (Socket socket = new Socket(HOST, PORTA);
                PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
                    BufferedReader input = new BufferedReader(
                            new InputStreamReader(socket.getInputStream()))) {
                
                System.out.println("Server connesso");

                System.out.print("Primo numero: ");
                String primo = scanner.nextLine().trim();

                System.out.print("Operatore (+, -, *, /): ");
                String operatore = scanner.nextLine().trim();

                System.out.print("Secondo numero: ");
                String secondo = scanner.nextLine().trim();

                String richiesta = primo + " " + operatore + " " + secondo;


                output.println(richiesta);

                String risposta = input.readLine();
                System.out.println("Risposta del server: " + risposta);
            }

        } catch (IOException e) {
            System.err.println("Errore di connessione: " + e.getMessage());
        }
    }
}
