package com.hahn.erms.ui;

import com.hahn.erms.ui.views.LoginView;

import javax.swing.*;
import java.io.Serial;

public class MainFrame extends LoginView {

    @Serial
    private static final long serialVersionUID = 1L;


    public MainFrame() {
        this.setTitle("Login");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(700, 500);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

}
