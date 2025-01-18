package com.hahn.erms;

import com.hahn.erms.dtos.LoginResponse;
import com.hahn.erms.models.Account;
import com.hahn.erms.services.AuthService;
import com.hahn.erms.services.EmployeeService;
import com.hahn.erms.services.impl.AuthServiceImpl;
import com.hahn.erms.services.impl.EmployeeServiceImpl;
import com.hahn.erms.ui.MainFrame;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        System.setProperty("BASE_URL", "http://127.0.0.1:8080/api/v1");

        SwingUtilities.invokeLater(MainFrame::new);
//        AuthService as = new AuthServiceImpl();
//        LoginResponse account = as.login("admin", "admin");
//        System.out.println(account);
//        EmployeeService es = new EmployeeServiceImpl();

    }
}