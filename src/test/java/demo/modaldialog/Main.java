package demo.modaldialog;

import javax.swing.*;

import chronos.swing.ModalDialog;
import com.formdev.flatlaf.FlatDarculaLaf;
import demo.LoremIpsum;

import java.awt.*;

class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            FlatDarculaLaf.setup();

            JFrame frame = new JFrame("Dialog Demo");
            frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

            JButton showDialog = new JButton("Show Dialog");
            {
                showDialog.addActionListener(e -> {
                    ModalDialog dialog = new ModalDialog();
                    dialog.showDialog(
                            "My Dialog",
                            true,
                            new Dimension(600, 400),
                            new JTextArea(LoremIpsum.LOREM_IPSUM),
                            new JButton("OK"),
                            new JButton("Cancel")
                    );
                    dialog.setVisible(true);
                });
                frame.add(showDialog);
            }




            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 200);
            frame.setVisible(true);
        });





    }
}