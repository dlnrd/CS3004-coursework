import java.net.*;
import java.io.*;

public class BankServer {
	public static void main(String[] args) throws IOException {

		ServerSocket bankSeverSocket = null;
		boolean listening = true;
		String bankServerName = "bankserver";
		int socketNumber = 4444;

		double[] accounts = { 1000, 1000, 1000 };

		// Create the shared object in the global scope...

		SharedBankState bankState = new SharedBankState(accounts);

		// Make the server socket

		try {
			bankSeverSocket = new ServerSocket(socketNumber);
		} catch (IOException e) {
			System.err.println("Could not start " + bankServerName + " on socket " + socketNumber);
			System.exit(-1);
		}
		System.out.println(bankServerName + " started on socket " + socketNumber);

		// Got to do this in the correct order with only four clients! Can automate
		// this...

		while (listening) {
			// could identify the client when it connects to the server
			new BankServerThread(bankSeverSocket.accept(), "A", bankState).start();
			new BankServerThread(bankSeverSocket.accept(), "B", bankState).start();
			new BankServerThread(bankSeverSocket.accept(), "C", bankState).start();
			// System.out.println("New " + bankServerName + " thread started.");
		}
		bankSeverSocket.close();
	}
}
