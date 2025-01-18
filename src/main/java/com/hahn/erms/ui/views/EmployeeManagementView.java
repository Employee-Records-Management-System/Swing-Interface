package com.hahn.erms.ui.views;

import com.hahn.erms.models.*;
import com.hahn.erms.services.EmployeeService;
import com.hahn.erms.services.impl.EmployeeServiceImpl;
import com.hahn.erms.ui.components.TopPanel;
import com.hahn.erms.utils.PermissionHelper;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Vector;

public class EmployeeManagementView extends JFrame {
 private JTable employeeTable;
 private DefaultTableModel tableModel;
 private TableRowSorter<DefaultTableModel> sorter;
 private final List<Employee> employeeList;
 private final EmployeeService employeeService;
 private final Account currentUser;

 public EmployeeManagementView() {
  this.currentUser = AuthManager.account;
  initializeFrame();
  employeeList = new Vector<>();
  employeeService = new EmployeeServiceImpl();
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
  JPanel headerPanel = createHeaderPanel();
  add(headerPanel, BorderLayout.NORTH);

  // Table
  JScrollPane scrollPane = new JScrollPane(employeeTable);
  employeeTable.setFillsViewportHeight(true);
  add(scrollPane, BorderLayout.CENTER);

  // Buttons Panel
//  if (PermissionHelper.hasPermission(currentUser, e)) {
   JPanel buttonPanel = createButtonPanel();
   add(buttonPanel, BorderLayout.SOUTH);


  // Menu Bar
  JMenuBar menuBar = createMenuBar();
  setJMenuBar(menuBar);
 }

 private JPanel createHeaderPanel() {
  JPanel headerPanel = new JPanel(new MigLayout("wrap 1", "[50%]10[50%]", "[]"));
  JPanel userInfoPanel = new JPanel(new MigLayout("insets 10", "[fill]", "[]"));

  userInfoPanel.add(new JLabel("Role: " + currentUser.getRole().getName()), "wrap");
  userInfoPanel.add(new JLabel("Job Title: " + currentUser.getEmployee().getJobTitle()), "wrap");

  headerPanel.add(userInfoPanel);


   headerPanel.add(new TopPanel(
           employeeService.getAllDepartments().stream().map(Department::getName).toList(),
           employeeService.getAllEmploymentStatus(),
           this::filterEmployees
   ));

  return headerPanel;
 }

 private JPanel createButtonPanel() {
  JPanel buttonPanel = new JPanel(new MigLayout("insets 20", "[grow,fill]10[grow,fill]10[grow,fill]10[grow,fill]"));

  JButton addButton = createButton("Add Employee", this::addEmployeeAction);
  JButton editButton = createButton("Edit Employee", this::editEmployeeAction);
  JButton deleteButton = createButton("Delete Employee", this::deleteEmployeeAction);
  JButton reportButton = createButton("Generate Report", this::generateReportAction);

  buttonPanel.add(addButton);
  buttonPanel.add(editButton);
  buttonPanel.add(deleteButton);
  buttonPanel.add(reportButton);

  return buttonPanel;
 }

 private JButton createButton(String text, Runnable action) {
  JButton button = new JButton(text);
  button.addActionListener(e -> action.run());
  return button;
 }

 private JMenuBar createMenuBar() {
  JMenuBar menuBar = new JMenuBar();

  // File Menu
  JMenu fileMenu = new JMenu("File");
  fileMenu.setMnemonic(KeyEvent.VK_F);
  fileMenu.add(createMenuItem("Export Data...", this::exportDataAction));
  fileMenu.add(createMenuItem("Import Data...", this::importDataAction));
  fileMenu.addSeparator();
  fileMenu.add(createMenuItem("Exit", this::exitAction));

  // Reports Menu
  JMenu reportsMenu = new JMenu("Reports");
  reportsMenu.setMnemonic(KeyEvent.VK_R);
  reportsMenu.add(createMenuItem("Employee List", () -> generateReport("Employee List")));
  reportsMenu.add(createMenuItem("Department Summary", () -> generateReport("Department Summary")));
  reportsMenu.add(createMenuItem("Status Report", () -> generateReport("Status Report")));

  menuBar.add(fileMenu);
  menuBar.add(reportsMenu);
  return menuBar;
 }

 private JMenuItem createMenuItem(String text, Runnable action) {
  JMenuItem menuItem = new JMenuItem(text);
  menuItem.addActionListener(e -> action.run());
  return menuItem;
 }

 private void loadInitialData() {
  employeeService.getAllEmployees().forEach(this::addEmployee);
 }

 private void filterEmployees() {
  List<Employee> filtered = employeeService.getAllEmployees();
  tableModel.setRowCount(0);
  filtered.forEach(this::addEmployee);
 }

 public void addEmployee(Employee employee) {
  Object[] row = {
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
  };
  tableModel.addRow(row);
  employeeList.add(employee);
 }

 private void addEmployeeAction() {
   showError("You do not have permission to add employees.");
   return;

  // Logic to add an employee
 }

 private void editEmployeeAction() {
   showError("You do not have permission to edit employees.");
   return;

  // Logic to edit an employee
 }

 private void deleteEmployeeAction() {
   showError("You do not have permission to delete employees.");
   return;

  // Logic to delete an employee
 }

 private void generateReportAction() {
  generateReport("General");
 }

 private void exportDataAction() {
  // Logic to export data
 }

 private void importDataAction() {
  // Logic to import data
 }

 private void exitAction() {
  System.exit(0);
 }

 private void showError(String message) {
  JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
 }

 private void generateReport(String reportType) {
  JOptionPane.showMessageDialog(this, "Report generated: " + reportType);
 }
}
