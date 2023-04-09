package com.examprj;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;

public class ChangeCredentialsScreen extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton saveButton;

    public ChangeCredentialsScreen() {
        // Set up the JFrame
        setTitle("Change Username and Password");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);
        setIconImage(Toolkit.getDefaultToolkit().getImage(ChangeCredentialsScreen.class.getResource("/com/examprj/icon.png"))); // Change the icon

        // Create a JPanel for the username and password fields
        JPanel fieldsPanel = new JPanel(new GridLayout(2, 2));
        fieldsPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create a label and text field for the username
        JLabel usernameLabel = new JLabel("New Username:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        usernameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        fieldsPanel.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setFont(new Font("Arial", Font.PLAIN, 16));
        fieldsPanel.add(usernameField);

        // Create a label and password field for the password
        JLabel passwordLabel = new JLabel("New Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        fieldsPanel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        fieldsPanel.add(passwordField);

        // Create a JPanel for the save button
        JPanel savePanel = new JPanel(new GridLayout(1, 1));
        savePanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create the save button
        saveButton = new JButton("Save");
        saveButton.setFont(new Font("Arial", Font.PLAIN, 16));
        saveButton.addActionListener(this);
        savePanel.add(saveButton);

        // Add the fields panel and save panel to the JFrame
        add(fieldsPanel, BorderLayout.CENTER);
        add(savePanel, BorderLayout.SOUTH);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveButton) {
            // Get the new username and password from the text fields
            String newUsername = usernameField.getText();
            String newPassword = new String(passwordField.getPassword());

            // Save the new credentials to a text file
            try {
                PrintWriter writer = new PrintWriter(new File(LoginScreen.class.getResource("/com/examprj/credentials.txt").getPath()));
                writer.write(newUsername + "\n");
                writer.write(newPassword + "\n");
                writer.close();
                JOptionPane.showMessageDialog(this, "Credentials saved successfully.");
                dispose();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error: Could not save credentials.");
                ex.printStackTrace();
            }
        }
    }

}

