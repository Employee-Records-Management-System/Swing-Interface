package com.hahn.erms.ui.components.buttons;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginButton extends JButton {

    public LoginButton() {
        super("Log In");
        this.setPreferredSize(new Dimension(300, 40));
        this.setBackground(new Color(41, 128, 185));
        this.setForeground(Color.WHITE);
        this.setFont(new Font("Arial", Font.BOLD, 14));
        this.setBorder(new EmptyBorder(5, 10, 5, 10));
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.setFocusPainted(false);
    }
}
