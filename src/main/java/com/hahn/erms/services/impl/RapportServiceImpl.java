package com.hahn.erms.services.impl;

import com.hahn.erms.models.Employee;
import com.hahn.erms.services.RapportService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.util.List;

public class RapportServiceImpl implements RapportService {

    public void generatePdf(List<Employee> employees, String filePath) {

        try {
            // Create a new Document
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream(filePath));

            // Open the document
            document.open();

            // Add title
            addTitle(document);

            // Add table
            PdfPTable table = createTable(employees);
            document.add(table);

            // Close the document
            document.close();
            System.out.println("PDF generated successfully: " + filePath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generatePdf(Employee employees, String filePath) {

    }

    public void generateXlsx(List<Employee> employees, String filePath) {

    }



    private void addTitle(Document document) throws DocumentException {
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLUE);
        Paragraph title = new Paragraph("Employee Report", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);
    }

    private PdfPTable createTable(List<Employee> employees) {
        // Define column widths
        float[] columnWidths = {1, 2, 2, 2, 2, 3};
        PdfPTable table = new PdfPTable(columnWidths);
        table.setWidthPercentage(100);

        // Add header
        addTableHeader(table);

        // Add rows
        for (Employee employee : employees) {
            addTableRow(table, employee);
        }

        return table;
    }

    private void addTableHeader(PdfPTable table) {
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE);
        BaseColor headerBackground = new BaseColor(0, 102, 204);

        table.addCell(createHeaderCell("ID", headerFont, headerBackground));
        table.addCell(createHeaderCell("Name", headerFont, headerBackground));
        table.addCell(createHeaderCell("Department", headerFont, headerBackground));
        table.addCell(createHeaderCell("Job Title", headerFont, headerBackground));
        table.addCell(createHeaderCell("Status", headerFont, headerBackground));
        table.addCell(createHeaderCell("Email", headerFont, headerBackground));
    }

    private PdfPCell createHeaderCell(String content, Font font, BaseColor backgroundColor) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setBackgroundColor(backgroundColor);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(8);
        return cell;
    }

    private void addTableRow(PdfPTable table, Employee employee) {
        table.addCell(String.valueOf(employee.getId()));
        table.addCell(employee.getFullName());
        table.addCell(employee.getDepartment().getName());
        table.addCell(employee.getJobTitle());
        table.addCell(employee.getEmploymentStatus());
        table.addCell(employee.getContactInfo().getEmail());
    }
}
