package com.hahn.erms.ui.components;

import com.hahn.erms.services.FilterEvent;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.util.List;

public class TopPanel extends JPanel {
    private final JComboBox<String> departmentFilter;
    private final JComboBox<String> statusFilter;
    private final JTextField searchField;
    private final FilterEvent filterEvent;


    public TopPanel(List<String> departments, List<String> status, FilterEvent filterEvent) {
        super(new MigLayout("insets 20", "[]10[]10[]10[]", "[]10[]"));
        this.filterEvent = filterEvent;
        this.setBorder(BorderFactory.createTitledBorder("Search and Filters"));
        departmentFilter = new JComboBox<>(departments.toArray(new String[]{"All Departments"}));
        statusFilter = new JComboBox<>(status.toArray(new String[]{"All Status"}));
        searchField = new JTextField(20);
        this.add(new JLabel("Search:"));
        this.add(searchField, "width 200!");
        this.add(new JLabel("Department:"));
        this.add(departmentFilter);
        this.add(new JLabel("Status:"));
        this.add(statusFilter, "wrap");
    }

    private void setupActions() {
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filterEvent.filter(searchField.getText(), "SEARCH", "ASC"); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filterEvent.filter(searchField.getText(), "SEARCH", "ASC"); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filterEvent.filter(searchField.getText(), "SEARCH", "ASC"); }
        });

        departmentFilter.addActionListener(e -> filterEvent.filter(departmentFilter.getName(), "DEPARTMENT", "ASC"));
        statusFilter.addActionListener(e -> filterEvent.filter(statusFilter.getName(), "STATUS", "ASC"));
    }

    private void filter() {
    }
}
