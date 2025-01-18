package com.hahn.erms.models;

import javax.swing.table.AbstractTableModel;

public class DataModel extends AbstractTableModel {
    private String[] columns = {"ID", "Name", "Age"};
    private Object[][] data = {
            {1, AuthManager.username, AuthManager.expiryDate},
            {2, "Bob", 25},
            {3, "Charlie", 35}
    };

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }
}
