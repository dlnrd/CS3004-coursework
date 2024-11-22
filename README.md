# 2156359 CS3004 Coursework 
Coursework for CS3004 network computing

## Description of each class
### BankClient.java
Client's class, responsible for:
- Opening client socket
- Initiating I/O with server
- Reading client input
- Displaying server responses
### BankServer.java
Main server class, responsible for:
- Opening server socket 
- Initiating shared state
- Listening for client handshake request
- Starting BankServerThread for new client connections
### BankServerThread.java
Thread initiated from BankServer, responsible for:
- Finishes connecting with client I/O
- Listens for client requests
- Request/releases lock from shared state
- Interacts with shared state (process input)
### SharedBankState.java
Shared state class responsible for:
- Storing account values
- Locking implementation
- Processing client input
- Validate client input
- Account operations/business logic
