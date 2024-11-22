import java.io.*;
import java.net.*;

public class BankClient {
    public static void main(String[] args) throws IOException {

        Socket bankSocket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        int socketNumber = 4444;
        String hostname = "localhost";
        String clientID = "";

        try {
            bankSocket = new Socket(hostname, socketNumber);
            in = new BufferedReader(new InputStreamReader(bankSocket.getInputStream()));
            out = new PrintWriter(bankSocket.getOutputStream(), true);
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + hostname);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + socketNumber);
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromServer;
        String fromUser;

        System.out.println("Initialised " + clientID + " client and IO connections");

        while (true) {
            fromUser = stdIn.readLine();
            if (fromUser != null) {
                System.out.println("Client " + clientID + " sending: \"" + fromUser + "\" to BankServer");
                out.println(fromUser);
            }
            fromServer = in.readLine();
            System.out.println("Client " + clientID + " received: \"" + fromServer + "\" from BankServer\n");
        }

        // Tidy up - not really needed due to true condition in while loop
        // out.close();
        // in.close();
        // stdIn.close();
        // bankSocket.close();
    }
}
