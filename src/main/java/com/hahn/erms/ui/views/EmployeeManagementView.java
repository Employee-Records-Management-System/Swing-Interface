package com.hahn.erms.ui.views;

import com.hahn.erms.models.AuthManager;
import com.hahn.erms.models.Department;
import com.hahn.erms.models.Employee;
import com.hahn.erms.services.EmployeeService;
import com.hahn.erms.services.RapportService;
import com.hahn.erms.services.impl.EmployeeServiceImpl;
import com.hahn.erms.services.impl.RapportServiceImpl;
import com.hahn.erms.ui.components.TopPanel;
import com.hahn.erms.ui.components.dialogs.AddEmployeeDialog;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.List;

public class EmployeeManagementView extends JFrame {

 private final EmployeeService employeeService;
 private final RapportService rapportService;
 private JTable employeeTable;
 private DefaultTableModel tableModel;
 private TableRowSorter<DefaultTableModel> sorter;
 private List<Employee> visibleEmployees;
 public EmployeeManagementView() {
  this.employeeService = new EmployeeServiceImpl();
  this.rapportService = new RapportServiceImpl();
  this.visibleEmployees = employeeService.getAllEmployees();
  initializeFrame();
  createComponents();
  setupLayout();
  loadInitialData();
 }

 private void initializeFrame() {
  setTitle("Employee Management System");
  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  setSize(1200, 700);
  setLocationRelativeTo(null);
 }

 private void createComponents() {
  tableModel = createTableModel();
  employeeTable = new JTable(tableModel);
  sorter = new TableRowSorter<>(tableModel);
  employeeTable.setRowSorter(sorter);
 }

 private DefaultTableModel createTableModel() {
  String[] columns = {
          "ID", "Full Name", "Employee ID", "Job Title", "Department",
          "Hire Date", "Status", "Phone", "Email", "Address"
  };
  return new DefaultTableModel(columns, 0) {
   @Override
   public boolean isCellEditable(int row, int column) {
    return false;
   }
  };
 }

 private void setupLayout() {
  setLayout(new BorderLayout());

  // Header Panel
  TopPanel topPanel = new TopPanel(
          employeeService.getAllDepartments().stream().map(Department::getName).toList(),
          employeeService.getAllEmploymentStatus(),
          this::filterEmployeesAndLoad
  );
  add(topPanel, BorderLayout.NORTH);

  // Table
  JScrollPane scrollPane = new JScrollPane(employeeTable);
  add(scrollPane, BorderLayout.CENTER);

  // Button Panel
  JPanel buttonPanel = createButtonPanel();
  add(buttonPanel, BorderLayout.SOUTH);
 }

 private JPanel createButtonPanel() {
  JPanel panel = new JPanel(new MigLayout("fillx", "[]20[]20[]20[]", "[]"));

  if(AuthManager.account.getRole().getName().equals("ROLE_ADMIN") ||
          AuthManager.account.getRole().getName().equals("ROLE_HR")) {
   JButton addButton = new JButton("Add Employee");
   addButton.addActionListener(e -> addEmployee());
   panel.add(addButton);
  }

  JButton reportButton = new JButton("Generate PDF");
  reportButton.addActionListener(e -> generatePdfReport());
  JButton deleteButton = new JButton("Delete Employee");
  deleteButton.addActionListener(e -> deleteEmployee());
  panel.add(reportButton);
  panel.add(deleteButton);

  return panel;
 }

 private void loadInitialData() {
  List<Employee> employees = visibleEmployees;
  employees.forEach(this::addRowToTable);
 }

 private void addRowToTable(Employee employee) {
  tableModel.addRow(new Object[]{
          employee.getId(),
          employee.getFullName(),
          employee.getEmployeeId(),
          employee.getJobTitle(),
          employee.getDepartment().getName(),
          employee.getHireDate(),
          employee.getEmploymentStatus(),
          employee.getContactInfo().getPhoneNumber(),
          employee.getContactInfo().getEmail(),
          employee.getAddress()
  });
 }

 public void addEmployee() {
  new AddEmployeeDialog(this, tableModel, employeeService);

 }

 private void generatePdfReport() {
  JFileChooser fileChooser = new JFileChooser();
  fileChooser.setDialogTitle("Save PDF Report");
  fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

  fileChooser.setSelectedFile(new java.io.File("employee_report.pdf"));

  int userSelection = fileChooser.showSaveDialog(this);
  if (userSelection == JFileChooser.APPROVE_OPTION) {
   String filePath = fileChooser.getSelectedFile().getAbsolutePath();

   rapportService.generatePdf(visibleEmployees, filePath);
   JOptionPane.showMessageDialog(this, "PDF saved to: " + filePath);
  } else {
   JOptionPane.showMessageDialog(this, "File save operation cancelled.");
  }
 }
 private List<Employee> filterEmployees(String searchText, String department, String status){
   return employeeService.getAllEmployees().stream()
           .filter(employee -> (department.equals("All Departments") || employee.getDepartment().getName().equals(department)))
           .filter(employee -> (status.equals("All Status") || employee.getEmploymentStatus().equals(status)))
           .filter(employee -> employee.getFullName().toLowerCase().contains(searchText.toLowerCase()) ||
                   employee.getJobTitle().toLowerCase().contains(searchText.toLowerCase()))
           .toList();
 }
 private void filterEmployeesAndLoad(String searchText, String department, String status) {
  visibleEmployees = filterEmployees(searchText, department, status);

  loadTableData(visibleEmployees);
 }
 private void loadTableData(List<Employee> employees) {
  tableModel.setRowCount(0);
  for (Employee employee : employees) {
   tableModel.addRow(new Object[]{
           employee.getId(),
           employee.getFullName(),
           employee.getEmployeeId(),
           employee.getJobTitle(),
           employee.getDepartment().getName(),
           employee.getHireDate(),
           employee.getEmploymentStatus(),
           employee.getContactInfo().getPhoneNumber(),
           employee.getContactInfo().getEmail(),
           employee.getAddress()
   });
  }
 }
 private void deleteEmployee() {
  int selectedRow = employeeTable.getSelectedRow();

  if (selectedRow == -1) {
   JOptionPane.showMessageDialog(this, "Please select an employee to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
   return;
  }

  int confirmed = JOptionPane.showConfirmDialog(
          this,
          "Are you sure you want to delete this employee?",
          "Confirm Deletion",
          JOptionPane.YES_NO_OPTION
  );

  if (confirmed == JOptionPane.YES_OPTION) {
   long employeeId = (long) tableModel.getValueAt(employeeTable.convertRowIndexToModel(selectedRow), 0);
   try {
    employeeService.deleteEmployee(employeeId);

    tableModel.removeRow(employeeTable.convertRowIndexToModel(selectedRow));

    JOptionPane.showMessageDialog(this, "Employee deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
   } catch (Exception ex) {
    JOptionPane.showMessageDialog(this, "Error deleting employee: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
   }
  }
 }
}
