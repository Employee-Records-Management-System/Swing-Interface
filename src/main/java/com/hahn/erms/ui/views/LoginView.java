package com.hahn.erms.ui.views;

import com.hahn.erms.dtos.LoginResponse;
import com.hahn.erms.models.UserModel;
import com.hahn.erms.services.AuthService;
import com.hahn.erms.services.impl.AuthServiceImpl;
import com.hahn.erms.ui.components.buttons.LoginButton;
import com.hahn.erms.ui.components.dialogs.ToastDialog;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginView extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel errorLabel;

    public LoginView() {
        initializeComponents();
        setupLayout();
        setupListeners();
    }

    private void initializeComponents() {
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        loginButton = new LoginButton();
        errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);
    }

    private void setupLayout() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(240, 240, 240));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Employee Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(titleLabel, gbc);

        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(usernameLabel, gbc);

        usernameField.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(passwordLabel, gbc);

        passwordField.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        loginButton.setPreferredSize(new Dimension(100, 35));
        loginButton.setBackground(new Color(70, 130, 180));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(loginButton, gbc);

        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(errorLabel, gbc);
        this.add(panel);
    }

    private void setupListeners() {
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            char[] password = passwordField.getPassword();

            if (username.isEmpty() || password.length == 0) {
                ToastDialog.showError(this, "Please enter both username and password.");
            } else {
                AuthService authService = new AuthServiceImpl();
                try{
                    authService.login(username, String.valueOf(password));

                    EmployeeManagementView employeeManagementView = new EmployeeManagementView();
                    employeeManagementView.setVisible(true);
                    this.setVisible(false);
                }
                catch(Exception ex){
                    System.out.println(ex.getLocalizedMessage());
                    ToastDialog.showError(this, "Crediential error.");
                    clearError();
                }
            }
        });
    }

    public void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }

    public void clearError() {
        errorLabel.setText("");
        errorLabel.setVisible(false);
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public char[] getPassword() {
        return passwordField.getPassword();
    }

    public void setLoginActionListener(java.awt.event.ActionListener listener) {
        loginButton.addActionListener(listener);
    }
}