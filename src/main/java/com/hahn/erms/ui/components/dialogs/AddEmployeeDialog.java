package com.hahn.erms.ui.components.dialogs;

import com.hahn.erms.models.*;
import com.hahn.erms.services.EmployeeService;
import com.hahn.erms.utils.DateLabelFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Properties;

public class AddEmployeeDialog extends JDialog {

    private List<Department> departmentList;
    public AddEmployeeDialog(JFrame parent, DefaultTableModel tableModel, EmployeeService employeeService) {
        super(parent, "Add Employee", true);
        setSize(400, 600);
        setLayout(new BorderLayout());
        setLocationRelativeTo(parent);

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));

        // Form fields
        JTextField fullNameField = new JTextField();
        JTextField employeeIdField = new JTextField();
        JTextField jobTitleField = new JTextField();
        departmentList = employeeService.getAllDepartments();
        JComboBox<String> departmentField = new JComboBox<>(departmentList
                .stream()
                .map(Department::getName)
                .toArray(String[]::new));
        JTextField statusField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField addressField = new JTextField();

        // Gender Dropdown
        JComboBox<String> genderField = new JComboBox<>(new String[]{"Male", "Female", "Other"});

        // Contract Type Dropdown
        JComboBox<String> contractTypeField = new JComboBox<>(new String[]{"Full Time", "Part Time", "Contract"});

        // JDatePicker for Hire Date
        UtilDateModel dateModel = new UtilDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(dateModel, new Properties());
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

        // Add components to the form
        formPanel.add(new JLabel("Full Name:"));
        formPanel.add(fullNameField);
        formPanel.add(new JLabel("Employee ID:"));
        formPanel.add(employeeIdField);
        formPanel.add(new JLabel("Job Title:"));
        formPanel.add(jobTitleField);
        formPanel.add(new JLabel("Department:"));
        formPanel.add(departmentField);
        formPanel.add(new JLabel("Hire Date:"));
        formPanel.add(datePicker);
        formPanel.add(new JLabel("Status:"));
        formPanel.add(statusField);
        formPanel.add(new JLabel("Phone:"));
        formPanel.add(phoneField);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);
        formPanel.add(new JLabel("Address:"));
        formPanel.add(addressField);
        formPanel.add(new JLabel("Gender:"));
        formPanel.add(genderField);
        formPanel.add(new JLabel("Contract Type:"));
        formPanel.add(contractTypeField);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        // Add panels to dialog
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Button actions
        saveButton.addActionListener(e -> {
            String fullName = fullNameField.getText();
            String employeeId = employeeIdField.getText();
            String jobTitle = jobTitleField.getText();
            String department = (String) departmentField.getSelectedItem();
            String status = statusField.getText();
            String phone = phoneField.getText();
            String email = emailField.getText();
            String address = addressField.getText();
            String gender = (String) genderField.getSelectedItem();
            String contractType = (String) contractTypeField.getSelectedItem();
            java.util.Date selectedDate = (java.util.Date) datePicker.getModel().getValue();

            // Validate input
            if (fullName.isEmpty() || employeeId.isEmpty() || jobTitle.isEmpty() || department == null ||
                    selectedDate == null || status == null || phone.isEmpty() || email.isEmpty() || address.isEmpty() ||
                    gender == null || contractType == null) {
                JOptionPane.showMessageDialog(this, "All fields are required!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                LocalDate hireDate = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                Employee newEmployee = new Employee();
                newEmployee.setFullName(fullName);
                newEmployee.setEmployeeId(employeeId);
                newEmployee.setJobTitle(jobTitle);
                newEmployee.setDepartment(departmentList.stream().filter((d) -> d.getName().equals(department)).findFirst().get());
                newEmployee.setHireDate(hireDate);
                newEmployee.setEmploymentStatus(status);
                newEmployee.setGender(Gender.valueOf(gender.toUpperCase()));
                newEmployee.setContractType(ContractType.valueOf(contractType.toUpperCase().trim().replace(' ','_')));
                newEmployee.setContactInfo(new ContactInfo(phone, email));
                newEmployee.setAddress(address);

                Employee savedEmployee = employeeService.createEmployee(newEmployee);

                // Update the table model
                tableModel.addRow(new Object[]{
                        savedEmployee.getId(),
                        fullName,
                        employeeId,
                        jobTitle,
                        department,
                        hireDate,
                        status,
                        phone,
                        email,
                        address,
                        gender,
                        contractType
                });

                JOptionPane.showMessageDialog(this, "Employee added successfully!");
                dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error adding employee: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dispose());

        setVisible(true);
    }
}

