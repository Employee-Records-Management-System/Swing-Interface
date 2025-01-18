package com.hahn.erms.ui.components.dialogs;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AddEmployeeDialog extends JDialog {
    public AddEmployeeDialog(JFrame parent, DefaultTableModel tableModel){
        super(parent, "Add Employee", true);
        setSize(400, 300);
        setLayout(new MigLayout("wrap 2", "[right][grow,fill]", "[]20[]20[]20[center]"));

        add(new JLabel("Name:"));
        JTextField nameField = new JTextField();
        add(nameField);

        add(new JLabel("Department:"));
        JTextField departmentField = new JTextField();
        add(departmentField);

        add(new JLabel("Position:"));
        JTextField positionField = new JTextField();
        add(positionField);

        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        add(saveButton, "span, split 2, center");
        add(cancelButton);
        JDialog dialog = this;
        saveButton.addActionListener(e -> {
                String name = nameField.getText();
                String department = departmentField.getText();
                String position = positionField.getText();

                if (!name.isEmpty() && !department.isEmpty() && !position.isEmpty()) {
                    tableModel.addRow(new Object[]{111, name, department, position});
                    JOptionPane.showMessageDialog(dialog, "Employee added successfully.");
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Please fill in all fields.");
                }
                JOptionPane.showMessageDialog(dialog, "Employee added:\nName: " + name + "\nDepartment: " + department + "\nPosition: " + position);
                dispose();
        });
        cancelButton.addActionListener(e -> dispose());

        setLocationRelativeTo(super.getParent());
        setVisible(true);
    }
}
