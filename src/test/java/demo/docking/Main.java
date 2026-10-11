package demo.docking;

import chronos.swing.*;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import demo.LoremIpsum;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Path;

class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            FlatMacDarkLaf.setup();

            JFrame frame = new JFrame("xc1-tool");
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
                win2.add(new JButton("Bar"), BorderLayout.NORTH);

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
                win1.add(foo, BorderLayout.EAST);

                win2.setLayout(new BorderLayout());
                win2.add(new JButton("Bar"), BorderLayout.EAST);

                ToolWindowBar right = dock.getRight();
                right.addToolWindow(win1);
                right.addToolWindow(win2);


            }

            {
                ToolWindow main = new ToolWindow("Center Component");
                main.setLayout(new BorderLayout());
                {
                    JTabbedPane tabs = new JTabbedPane();
                    tabs.addTab("Tab 1", new JTextArea(LoremIpsum.LOREM_IPSUM));
                    tabs.addTab("Tab 2", new JTextArea(LoremIpsum.LOREM_IPSUM));
                    main.add(tabs, BorderLayout.CENTER);
                }
                dock.add(main, BorderLayout.CENTER);
            }



            {
                ToolWindow main = new ToolWindow("South Component");
                main.setLayout(new BorderLayout());
                {
                    JTabbedPane tabs = new JTabbedPane();
                    tabs.addTab("Tab 1", new JTextArea(LoremIpsum.LOREM_IPSUM));
                    tabs.addTab("Tab 2", new JTextArea(LoremIpsum.LOREM_IPSUM));
                    main.add(tabs, BorderLayout.CENTER);
                }

                ToolWindowBar bottom = dock.getBottom();
                bottom.addToolWindow(main);


            }


            frame.setContentPane(dock);
            frame.addWindowListener(new WindowAdapter(){
                @Override
                public void windowClosing(WindowEvent e) {
                    ToolWindowDockState toolWindowDockState = dock.getToolWindowDockState();

                    System.out.println();



                }
            });

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);
            frame.setVisible(true);
        });





    }
}