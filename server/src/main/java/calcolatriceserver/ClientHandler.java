package calcolatriceserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (
                Socket socket = clientSocket;
                BufferedReader input = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                PrintWriter output = new PrintWriter(
                        socket.getOutputStream(), true)
        ) {
            String richiesta = input.readLine();
            String risposta = calcola(richiesta);

            output.println(risposta);

            System.out.println("Richiesta da "
                    + socket.getInetAddress() + ": " + richiesta);
            System.out.println("Risposta: " + risposta);

        } catch (IOException e) {
            System.err.println("Errore con il client: " + e.getMessage());
        }
    }

    private String calcola(String richiesta) {
        if (richiesta == null || richiesta.trim().isEmpty()) {
            return "Errore: richiesta vuota.";
        }

        String[] parti = richiesta.trim().split("\\s+");
        if (parti.length != 3) {
            return "Errore: usa il formato numero operatore numero, ad esempio 10 + 5.";
        }

        try {
            double primo = Double.parseDouble(parti[0]);
            String operatore = parti[1];
            double secondo = Double.parseDouble(parti[2]);
            double risultato;

            switch (operatore) {
                case "+":
                    risultato = primo + secondo;
                    break;
                case "-":
                    risultato = primo - secondo;
                    break;
                case "*":
                    risultato = primo * secondo;
                    break;
                case "/":
                    if (secondo == 0) {
                        return "Errore: divisione per zero.";
                    }
                    risultato = primo / secondo;
                    break;
                default:
                    return "Errore: operatore non riconosciuto. Usa +, -, * oppure /.";
            }

            return "Risultato: " + risultato;
        } catch (NumberFormatException e) {
            return "Errore: uno o entrambi gli operandi non sono numeri validi.";
        }
    }
}

