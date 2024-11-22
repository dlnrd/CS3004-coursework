public class SharedBankState {

	private double[] accounts;
	private boolean accessing = false; // true a thread has a lock, false otherwise

	SharedBankState(double[] accounts) {
		this.accounts = accounts;
	}

	public synchronized void acquireLock() throws InterruptedException {
		Thread me = Thread.currentThread(); // get a ref to the current thread
		System.out.println(me.getName() + " is attempting to acquire a lock!");
		while (accessing) { // while someone else is accessing or threadsWaiting > 0
			System.out.println(me.getName() + " waiting to get a lock as someone else is accessing...");
			// wait for the lock to be released - see releaseLock() below
			wait();
		}
		// nobody has got a lock so get one
		accessing = true;
		System.out.println(me.getName() + " got a lock!");
	}

	public synchronized void releaseLock() {
		// release the lock and tell everyone
		accessing = false;
		notifyAll();
		Thread me = Thread.currentThread(); // get a ref to the current thread
		System.out.println(me.getName() + " released a lock!");
	}

	public boolean validInput(String input) {
		String pattern = "([Aa]dd ([0-9]+|[0-9]+\\.[0-9]+))|([Ss]ubtract ([0-9]+|[0-9]+\\.[0-9]+))|([Tt]ransfer ([0-9]+|[0-9]+\\.[0-9]+) [A-C|a-c])";
		return input.matches(pattern);
	}

	public boolean validThreadName(String input) {
		return input.matches("[ABC]");
	}

	public int accountToIndex(String account) {
		switch (account) {
			case "a":
			case "A":
				return 0;
			case "b":
			case "B":
				return 1;
			case "c":
			case "C":
				return 2;
			default:
				return -1;
		}
	}

	public synchronized void add_money(String account, double value) {
		accounts[accountToIndex(account)] += value;
	}

	public synchronized void subtract_money(String account, double value) {
		accounts[accountToIndex(account)] -= value;
	}

	public synchronized void transfer_money(String account1, String account2, double value) {
		if (account1.equals(account2)) {
			return; // doesn't need to exist but would help if state was persistent and program
					// crashed after subtract
		}
		subtract_money(account1, value);
		add_money(account2, value);
	}

	private String getAccounts() {
		return "Account A: " + accounts[0] + "\nAccount B: " + accounts[1] + "\nAccount C: " + accounts[2];
	}

	public synchronized String processInput(String clientName, String input) {
		String outputToClient = null;

		if (!validInput(input)) {
			outputToClient = ("ERR: Invalid command entered");
		} else if (!validThreadName(clientName)) {
			outputToClient = ("ERR: Wrong threadname/clientname");
		} else {
			String[] arguments = input.split(" ");
			double value = Double.parseDouble(arguments[1]);

			if (arguments[0].matches("[Aa]dd")) {
				add_money(clientName, value);
				outputToClient = value + " units have been added to " + clientName;
			} else if (arguments[0].matches("[Ss]ubtract")) {
				subtract_money(clientName, value);
				outputToClient = value + " units have been subtract to " + clientName;
			} else if (arguments[0].matches("[Tt]ransfer")) {
				String toAccount = arguments[2];
				transfer_money(clientName, toAccount, value);
				outputToClient = value + " units have been transfered from " + clientName + " to " + toAccount;
			} else {
				// shouldn't be possible as input has been validated
				System.out.println("ERR: Developer made oopsie");
			}
		}

		// return the output message to the BankThread
		System.out.println("Server sending: \"" + outputToClient + "\" to " + clientName);
		System.out.println(getAccounts());

		return outputToClient;
	}
}
