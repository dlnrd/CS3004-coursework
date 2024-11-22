import java.net.*;
import java.io.*;

public class BankServer {
	public static void main(String[] args) throws IOException {

		ServerSocket bankSeverSocket = null;
		boolean listening = true;
		int socketNumber = 4444;

		double[] accounts = { 1000, 1000, 1000 };
		SharedBankState bankState = new SharedBankState(accounts);

		try {
			bankSeverSocket = new ServerSocket(socketNumber);
		} catch (IOException e) {
			System.err.println("Could not start BankServer on socket " + socketNumber);
			System.exit(-1);
		}
		System.out.println("BankServer started on socket " + socketNumber);

		while (listening) {
			// could identify the client when it connects to the server
			new BankServerThread(bankSeverSocket.accept(), "A", bankState).start();
			new BankServerThread(bankSeverSocket.accept(), "B", bankState).start();
			new BankServerThread(bankSeverSocket.accept(), "C", bankState).start();
		}
		bankSeverSocket.close();
	}
}
