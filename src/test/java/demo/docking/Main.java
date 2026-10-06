package demo.docking;

import chronos.swing.ModalDialog;
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

            JPanel contentPane = new JPanel();
            contentPane.setLayout(new BorderLayout());
            {
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
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

                panel.add(tab);
                panel.add(new ToolWindowTab("Compilation"));

                contentPane.add(panel, BorderLayout.WEST);

            }


            {
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

                panel.add(new ToolWindowTab("Synthesis"));
                panel.add(new ToolWindowTab("Compilation"));

                contentPane.add(panel, BorderLayout.EAST);

            }

            {
                JPanel panel = new JPanel();
                panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
                JProgressBar progressBar = new JProgressBar();

                progressBar.setIndeterminate(true);
                progressBar.setMinimum(0);
                progressBar.setMaximum(100);
                progressBar.setValue(50);
                panel.add(progressBar);
                contentPane.add(panel, BorderLayout.SOUTH);

            }

            contentPane.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
            frame.setContentPane(contentPane);

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(640, 480);
            frame.setVisible(true);
        });





    }
}