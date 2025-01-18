package com.hahn.erms.ui.components.fields;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class PasswordField extends JPasswordField{
    public PasswordField() {
        super(20);
        this.setPreferredSize(new Dimension(300, 35));
        this.setBorder(new CompoundBorder(
                new LineBorder(new Color(189, 189, 189)),
                new EmptyBorder(5, 10, 5, 10)));
        this.setFont(new Font("Arial", Font.PLAIN, 14));
    }
}
