package demo.docking;

import chronos.swing.*;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLightLaf;
import demo.LoremIpsum;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;

class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            FlatDarculaLaf.setup();



            JFrame frame = new JFrame("Docking Demo");
            frame.setLayout(new BorderLayout());

            JMenuBar menuBar = new JMenuBar();
            {
                JMenu fileMenu = new JMenu("File");
                menuBar.add(fileMenu);
                JMenu editMenu = new JMenu("Edit");
                menuBar.add(editMenu);
                JMenu viewMenu = new JMenu("View");
                menuBar.add(viewMenu);
                frame.setJMenuBar(menuBar);
            }
            ToolWindowDock dock = new ToolWindowDock();
            {
                ToolWindow win1 = new ToolWindow("Window 1");
                ToolWindow win2 = new ToolWindow("Window 2");

                win1.setLayout(new BorderLayout());
                win1.add(new JTextArea(LoremIpsum.LOREM_IPSUM), BorderLayout.EAST);

                win2.setLayout(new BorderLayout());
                win2.add(new JButton("Bar"), BorderLayout.EAST);

                ToolWindowBar left = dock.getLeft();
                left.addToolWindow(win1);
                left.addToolWindow(win2);
            }


            {
                ToolWindow win1 = new ToolWindow("Window 1");
                ToolWindow win2 = new ToolWindow("Window 2");

                win1.setLayout(new BorderLayout());
                JButton foo = new JButton("Foo");
                foo.addActionListener(e -> {
                    Path result = NativeFilePicker.showOpenFileDialog("Select a file","*.txt", "*.pdf");
                    JOptionPane.showMessageDialog(null, "You selected: " + result);
                });
                win1.add(foo, BorderLayout.EAST);

                win2.setLayout(new BorderLayout());
                win2.add(new JButton("Bar"), BorderLayout.EAST);

                ToolWindowBar right = dock.getRight();
                right.addToolWindow(win1);
                right.addToolWindow(win2);
            }


            frame.setContentPane(dock);

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1200, 800);
            frame.setVisible(true);
        });





    }
}