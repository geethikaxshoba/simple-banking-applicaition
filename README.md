# Simple Banking System

## Overview

The Simple Banking System is a Java-based desktop application that allows users to create bank accounts, deposit and withdraw funds, and check account balances. It provides a user-friendly graphical interface using Swing and connects to a MySQL database for data persistence.

## Features

- **Create Account**: Allows users to create a new bank account with an initial balance.
- **Select Account**: Users can select an existing account to perform transactions.
- **Deposit Funds**: Users can deposit money into their selected account.
- **Withdraw Funds**: Users can withdraw money, provided they have sufficient balance.
- **Check Balance**: Users can check the current balance of their selected account.

## Technologies Used

- **Java**: Core programming language used to build the application.
- **Swing**: GUI toolkit for creating the graphical user interface.
- **MySQL**: Database management system used to store account data.
- **JDBC**: Java Database Connectivity for connecting to the MySQL database.

## Requirements

- Java Development Kit (JDK) 8 or later
- MySQL Server
- MySQL Connector/J (JDBC driver)

## Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/yourusername/simple-banking-system.git
2. **Set up the MySQL database**:
- Create a database named banking_system.
- Create a table named accounts with the following structure:
  ```bash
  CREATE TABLE accounts (
    account_name VARCHAR(255) PRIMARY KEY,
    balance DECIMAL(10, 2) NOT NULL);
3. **Run the application:**:

- Open the project in your preferred IDE (e.g., IntelliJ IDEA, VS Code).
- Run the BankingSystem.java file.

## Outputs

## Create Account
<img src="https://github.com/user-attachments/assets/e61c4abc-9bd9-43c2-a5aa-bb7f0000341d" alt="Create Account" width="500"/>

## Select Account
<img src="https://github.com/user-attachments/assets/79a25b09-5df6-4468-8a41-edbfdb93968b" alt="Select Account" width="500"/>

## Deposit Amount
<img src="https://github.com/user-attachments/assets/c9e020d3-4c5a-42d7-b19b-e98aaf6b0473" alt="Deposit Amount" width="500"/>
<img src="https://github.com/user-attachments/assets/8c39e21e-8bd9-449c-bc0b-cf383c71f401" alt="Deposit Confirmation" width="500"/>

## Withdraw Amount
<img src="https://github.com/user-attachments/assets/2fb48d4d-ba74-435b-8aaf-af5e49f6fa3f" alt="Withdraw Amount" width="500"/>
<img src="https://github.com/user-attachments/assets/ee1a8e62-00ab-42c6-9285-479a5d830441" alt="Withdraw Confirmation" width="500"/>

## MySQL Workbench
<img src="https://github.com/user-attachments/assets/a20d668b-cf97-49b2-94c3-3cd37c0ec922" alt="Withdraw Confirmation" width="500"/>
