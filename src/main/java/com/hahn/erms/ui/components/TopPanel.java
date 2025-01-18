package com.hahn.erms.ui.components;

import com.hahn.erms.models.Account;
import com.hahn.erms.models.AuthManager;
import com.hahn.erms.services.FilterEvent;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.util.List;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TopPanel extends JPanel {
    private final JComboBox<String> departmentFilter;
    private final JComboBox<String> statusFilter;
    private final JTextField searchField;
    private final FilterEvent filterEvent;

    public TopPanel(List<String> departments, List<String> statuses, FilterEvent filterEvent) {
        super(new MigLayout("insets 20", "[grow,fill]10[grow,fill]10[grow,fill]", "[]10[]"));
        this.filterEvent = filterEvent;
        this.setBorder(BorderFactory.createTitledBorder("Search and Filters"));

        // Current User Info
        Account currentUser = AuthManager.account;

        JPanel userInfoPanel = new JPanel(new MigLayout("insets 5", "[fill]", "[]5[]5[]"));
        userInfoPanel.setBorder(BorderFactory.createTitledBorder("User Information"));

        JLabel userNameLabel = new JLabel("Name: " + currentUser.getEmployee().getFullName());
        JLabel userRoleLabel = new JLabel("Role: " + currentUser.getRole().getName().replace("ROLE_",""));
        JLabel userJobTitleLabel = new JLabel("Job Title: " + currentUser.getEmployee().getJobTitle());

        userInfoPanel.add(userNameLabel, "wrap");
        userInfoPanel.add(userRoleLabel, "wrap");
        userInfoPanel.add(userJobTitleLabel, "wrap");

        // Search and Filters
        departmentFilter = new JComboBox<>(departments.toArray(new String[0]));
        departmentFilter.insertItemAt("All Departments", 0);
        departmentFilter.setSelectedIndex(0);

        statusFilter = new JComboBox<>(statuses.toArray(new String[0]));
        statusFilter.insertItemAt("All Status", 0);
        statusFilter.setSelectedIndex(0);

        searchField = new JTextField(20);

        JPanel filterPanel = new JPanel(new MigLayout("insets 5", "[fill]10[fill]10[fill]", "[]5[]"));
        filterPanel.add(new JLabel("Search:"));
        filterPanel.add(searchField, "width 200!");
        filterPanel.add(new JLabel("Department:"));
        filterPanel.add(departmentFilter);
        filterPanel.add(new JLabel("Status:"));
        filterPanel.add(statusFilter, "wrap");

        // Add panels to TopPanel
        this.setLayout(new MigLayout("wrap 1", "[grow,fill]", "[]10[]"));
        this.add(userInfoPanel, "growx");
        this.add(filterPanel, "growx");

        setupActions();
    }

    private void setupActions() {
        departmentFilter.addActionListener(e -> filter());
        statusFilter.addActionListener(e -> filter());
        searchField.addActionListener(e -> filter());
    }

    private void filter() {
        String searchText = searchField.getText().trim();
        String selectedDepartment = (String) departmentFilter.getSelectedItem();
        String selectedStatus = (String) statusFilter.getSelectedItem();

        filterEvent.onFilter(searchText, selectedDepartment, selectedStatus);
    }

}
