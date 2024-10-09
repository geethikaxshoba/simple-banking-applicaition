import javax.swing.*; //GUI library
import java.awt.*; //Abstract Window Toolkit
import java.awt.event.ActionEvent; //ActionEvent class is used to create an event in the GUI
import java.awt.event.ActionListener; //ActionListener interface is used to handle the event
import java.io.*; //Input Output library
import java.util.HashMap; //HashMap class is used to store data in key-value pairs
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class BankingSystem extends JFrame {

    //MySQL server connection
    private Connection getConnection() throws Exception {
        String url = "jdbc:mysql://localhost:3306/banking_system";
        String user = "root";
        String password = "1234";
        return DriverManager.getConnection(url, user, password);
    }
    
    // GUI components
    private JTextField accountNameField; //to enter name
    private JTextField balanceField; //to enter initial balance
    private JTextField amountField; // to enter amount for operations
    private JLabel messageLabel; //to display messages

    // Account attributes
    private HashMap<String, Double> accounts = new HashMap<>();  // To store multiple accounts
    private String currentAccount = "";  // The currently selected account

    //GUI
    public BankingSystem() {
        // Frame settings
        setTitle("Simple Banking System");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel for the layout
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2, 10, 10));

        // Account name field
        panel.add(new JLabel("Account Name:"));
        accountNameField = new JTextField(10);
        panel.add(accountNameField);

        // Initial balance field for creating an account
        panel.add(new JLabel("Initial Balance:"));
        balanceField = new JTextField(10);
        panel.add(balanceField);

        // Deposit/Withdraw amount field
        panel.add(new JLabel("Amount:"));
        amountField = new JTextField(10);
        panel.add(amountField);

        // Message Label
        messageLabel = new JLabel();
        panel.add(messageLabel);

        // Buttons
        JButton createAccountBtn = new JButton("Create Account");
        JButton selectAccountBtn = new JButton("Select Account");
        JButton depositBtn = new JButton("Deposit");
        JButton withdrawBtn = new JButton("Withdraw");
        JButton balanceBtn = new JButton("Check Balance");

        // Add buttons to panel
        panel.add(createAccountBtn);
        panel.add(selectAccountBtn);
        panel.add(depositBtn);
        panel.add(withdrawBtn);
        panel.add(balanceBtn);

        // Action listeners for buttons
        createAccountBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAccount();
            }
        });

        selectAccountBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectAccount();
            }
        });

        depositBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                depositAmount();
            }
        });

        withdrawBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                withdrawAmount();
            }
        });

        balanceBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkBalance();
            }
        });

        // Add panel to frame
        add(panel);

        // Load data from the file if exists
        loadAccountData();
    }

    // Method to create an account
    private void createAccount() {
        String accountName = accountNameField.getText();
        try {
            double initialBalance = Double.parseDouble(balanceField.getText());
            if (accountName.isEmpty()) {
                messageLabel.setText("Please enter an account name.");
                return;
            }
    
            try (Connection conn = getConnection()) {
                String sql = "INSERT INTO accounts(account_name, balance) VALUES (?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, accountName);
                pstmt.setDouble(2, initialBalance);
                pstmt.executeUpdate();
                messageLabel.setText("Account Created: " + accountName + " with balance $" + initialBalance);
            }
        } catch (Exception e) {
            messageLabel.setText("Error: " + e.getMessage());
        }
    }    

    // Method to select an existing account
    private void selectAccount() {
        String accountName = accountNameField.getText();
        if (accountName.isEmpty()) {
            messageLabel.setText("Please enter an account name to select.");
            return;
        }
        try (Connection conn = getConnection()) {
            String sql = "SELECT balance FROM accounts WHERE account_name = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, accountName);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                currentAccount = accountName;
                messageLabel.setText("Selected account: " + currentAccount);
            } else {
                messageLabel.setText("Account does not exist.");
            }
        } catch (Exception e) {
            messageLabel.setText("Error: " + e.getMessage());
        }
    }    

    // Method to deposit an amount
    private void depositAmount() {
        if (currentAccount.equals("")) {
            messageLabel.setText("No account selected! Please select an account first.");
            return;
        }
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (amount <= 0) {
                messageLabel.setText("Amount must be positive.");
            } else {
                try (Connection conn = getConnection()) {
                    String sql = "UPDATE accounts SET balance = balance + ? WHERE account_name = ?";
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setDouble(1, amount);
                    pstmt.setString(2, currentAccount);
                    pstmt.executeUpdate();
                    messageLabel.setText("Deposited $" + amount + " successfully to " + currentAccount + "!");
                }
            }
        } catch (Exception e) {
            messageLabel.setText("Error: " + e.getMessage());
        }
    }    

    // Method to withdraw an amount
    private void withdrawAmount() {
        if (currentAccount.equals("")) {
            messageLabel.setText("No account selected! Please select an account first.");
            return;
        }
        try {
            double amount = Double.parseDouble(amountField.getText());
            try (Connection conn = getConnection()) {
                String checkBalanceSql = "SELECT balance FROM accounts WHERE account_name = ?";
                PreparedStatement checkStmt = conn.prepareStatement(checkBalanceSql);
                checkStmt.setString(1, currentAccount);
                ResultSet rs = checkStmt.executeQuery();
                
                if (rs.next()) {
                    double currentBalance = rs.getDouble("balance");
                    if (amount > currentBalance) {
                        messageLabel.setText("Insufficient balance.");
                    } else {
                        String sql = "UPDATE accounts SET balance = balance - ? WHERE account_name = ?";
                        PreparedStatement pstmt = conn.prepareStatement(sql);
                        pstmt.setDouble(1, amount);
                        pstmt.setString(2, currentAccount);
                        pstmt.executeUpdate();
                        messageLabel.setText("Withdrew $" + amount + " successfully from " + currentAccount + "!");
                    }
                }
            }
        } catch (Exception e) {
            messageLabel.setText("Error: " + e.getMessage());
        }
    }    

    // Method to check the balance
private void checkBalance() {
    if (currentAccount.equals("")) {
        messageLabel.setText("No account selected! Please select an account first.");
        return;
    }

    try (Connection conn = getConnection()) {
        String query = "SELECT balance FROM accounts WHERE account_name = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, currentAccount);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            double balance = resultSet.getDouble("balance");
            messageLabel.setText("Current balance for " + currentAccount + ": $" + balance);
        } else {
            messageLabel.setText("Account not found.");
        }
    } catch (SQLException e) {
        messageLabel.setText("Error retrieving balance: " + e.getMessage());
    } catch (Exception e) {
        messageLabel.setText("Unexpected error: " + e.getMessage());
    }
}


    // Method to load account data from a file
    private void loadAccountData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String accountName = parts[0];
                double balance = Double.parseDouble(parts[1]);
                accounts.put(accountName, balance);
            }
            messageLabel.setText("Loaded accounts successfully.");
        } catch (IOException | NumberFormatException e) {
            messageLabel.setText("No previous account data found.");
        }
    }

    // Main method to run the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BankingSystem().setVisible(true);
            }
        });
    }
}
