package demo.font;

import chronos.swing.JFontComboBox;

import javax.swing.*;
import java.awt.*;

class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (UnsupportedLookAndFeelException e) {
                throw new RuntimeException(e);
            }

            JFrame frame = new JFrame("Font Demo");
            frame.setLayout(new BorderLayout());
            JFontComboBox fontComboBox = new JFontComboBox(16f, GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts());
            frame.add(fontComboBox, BorderLayout.NORTH);

            fontComboBox.addActionListener(e -> {
                Font font = fontComboBox.getSelectedFont();
                fontComboBox.hidePopup();
                JOptionPane.showMessageDialog(frame, "Selected font: " + font.getName());
            });

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 200);
            frame.setVisible(true);
        });





    }
}