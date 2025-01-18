package com.hahn.erms.ui.components;

import com.hahn.erms.models.DataModel;

import javax.swing.*;
import java.awt.*;

public class DataPanel extends JPanel {
    public JTable table;

    public DataPanel(DataModel dataModel) {
        setLayout(new BorderLayout());
        table = new JTable(dataModel);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }
}
