import java.io.*;
import java.net.*;
import java.util.Scanner;

public class BankClient {
    public static void main(String[] args) throws IOException {

        // Set up the socket, in and out variables

        Socket bankSocket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        int socketNumber = 4444;
        String bankServerName = "localhost";
        String clientID = "";

        try {
            bankSocket = new Socket(bankServerName, socketNumber);
            in = new BufferedReader(new InputStreamReader(bankSocket.getInputStream()));
            out = new PrintWriter(bankSocket.getOutputStream(), true);
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + bankServerName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + socketNumber);
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromServer;
        String fromUser;

        System.out.println("Initialised " + clientID + " client and IO connections");

        // This is modified as it's the client that speaks first

        while (true) {

            fromUser = stdIn.readLine();
            if (fromUser != null) {
                System.out.println("Client " + clientID + " sending: \"" + fromUser + "\" to BankServer");
                out.println(fromUser);
            }
            fromServer = in.readLine();
            System.out.println("Client " + clientID + " received: \"" + fromServer + "\" from BankServer");
        }

        // Tidy up - not really needed due to true condition in while loop
        // out.close();
        // in.close();
        // stdIn.close();
        // bankSocket.close();
    }
}
