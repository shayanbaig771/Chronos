package demo.docking;

import chronos.swing.ModalDialog;
import chronos.swing.ToolWindowBar;
import chronos.swing.ToolWindowDock;
import chronos.swing.ToolWindowTab;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import demo.LoremIpsum;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            FlatDarculaLaf.setup();

            JFrame frame = new JFrame("Docking Demo");
            frame.setLayout(new BorderLayout());

            ToolWindowDock dock = new ToolWindowDock();
            {
                ToolWindowTab tab = new ToolWindowTab("Synthesis");

                tab.addActionListener(e -> {
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

                ToolWindowBar left = dock.getLeft();
                left.addTab(tab);
                left.addTab(new ToolWindowTab("Compilation"));


            }


            {
                ToolWindowBar right = dock.getRight();

                right.addTab(new ToolWindowTab("Hello"));
                right.addTab(new ToolWindowTab("World"));



            }

            frame.setContentPane(dock);

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1200, 800);
            frame.setVisible(true);
        });





    }
}