package com.hahn.erms.ui.components.dialogs;

import com.hahn.erms.ui.components.borders.RoundedBorder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ToastDialog {
    private static final int TOAST_DURATION = 1000; // Duration in milliseconds

    public static void showError(Component parentComponent, String message) {
        showToast(parentComponent, message, Color.WHITE, new Color(255, 51, 51));
    }

    public static void showSuccess(Component parentComponent, String message) {
        showToast(parentComponent, message, Color.WHITE, new Color(76, 175, 80)); // Success green color
    }

    private static void showToast(Component parentComponent, String message, Color backgroundColor, Color textColor) {
        JDialog dialog = new JDialog();
        dialog.setUndecorated(true);
        dialog.setAlwaysOnTop(true);


        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(backgroundColor);
        panel.setBorder(new RoundedBorder(50));

        JLabel label = new JLabel(message, SwingConstants.CENTER);
        label.setForeground(textColor);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(label, BorderLayout.CENTER);
        // Add mouse click listener to close the dialog
        panel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                dialog.dispose();
            }
        });

        dialog.add(panel);
        dialog.pack();

        if (parentComponent != null) {
            Point location = parentComponent.getLocationOnScreen();
            Dimension parentSize = parentComponent.getSize();
            Dimension dialogSize = dialog.getSize();

            int x = location.x + (parentSize.width - dialogSize.width) / 2;
            int y = location.y + (parentSize.height - dialogSize.height) / 2;

            dialog.setLocation(x, y);
        } else {
            dialog.setLocationRelativeTo(null);
        }

        Timer timer = new Timer(TOAST_DURATION, e -> dialog.dispose());
        timer.setRepeats(false);
        timer.start();

        dialog.setVisible(true);
    }

}
