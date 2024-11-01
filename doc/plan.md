# Introduction
Short overview of this assignment

# Requirements
- Create a multi-threaded, client-server banking system
- Three (seperate?) client applications
    - Clients can call 3 operations:
        1. add_money(account, value)
        2. subtract_money(account, value)
        3. transfer_money(account1, account2, value)
- One multithreaded server application
    - Stores 3 values, one for each client
        - Starts at 1000 units
        - Unspecified type but can be negative so double
        - Locks on account variables to stop concurrency issues (lost update, dirty reads, etc)
    - (Optional) add logging to show what server is recieving/doing 

# Design

# Implementation

# Testing

# Conclusions

# Apendix A: Code

# Help
Synchronized makes sure only one occurance of the function can be run at once across any object
