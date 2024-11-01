import java.net.*;
import java.io.*;

public class BankServerThread extends Thread {

	private Socket bankSocket = null;
	private SharedBankState bankState;
	private String clientName;

	// Setup the thread
	public BankServerThread(Socket bankSocket, String _clientName, SharedBankState _bankState) {

		// super(ActionServerThreadName);
		this.bankSocket = bankSocket;
		bankState = _bankState;
		clientName = _clientName;
	}

	public void run() {
		try {
			System.out.println(clientName + " initialising.");
			BufferedReader in = new BufferedReader(new InputStreamReader(bankSocket.getInputStream()));
			PrintWriter out = new PrintWriter(bankSocket.getOutputStream(), true);
			String inputLine, outputLine;

			while ((inputLine = in.readLine()) != null) {
				// Get a lock first
				System.out.println("Server recieved: \"" + inputLine + "\" from " + clientName);
				try {
					bankState.acquireLock();
					outputLine = bankState.processInput(clientName, inputLine);
					out.println(outputLine);
					bankState.releaseLock();
					System.out.println(); // empty line
				} catch (InterruptedException e) {
					System.err.println("Failed to get lock when reading:" + e);
				}
			}

			out.close();
			in.close();
			bankSocket.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
