package demo.docking;

import chronos.swing.*;
import chronos.swing.docking.JToolWindow;
import chronos.swing.docking.JToolWindowBar;
import chronos.swing.docking.JToolWindowDock;
import chronos.swing.docking.ToolWindowDockState;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import demo.LoremIpsum;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
            JToolWindowDock dock = new JToolWindowDock();
            {
                JToolWindow win1 = new JToolWindow("Window 1");
                JToolWindow win2 = new JToolWindow("Window 2");

                win1.setLayout(new BorderLayout());
                win1.add(new JTextArea(LoremIpsum.LOREM_IPSUM), BorderLayout.EAST);

                win2.setLayout(new BorderLayout());
                win2.add(new JButton("Bar"), BorderLayout.NORTH);

                JToolWindowBar left = dock.getLeft();
                left.addToolWindow(win1);
                left.addToolWindow(win2);
            }


            {
                JToolWindow win1 = new JToolWindow("Window 1");
                JToolWindow win2 = new JToolWindow("Window 2");

                win1.setLayout(new BorderLayout());
                JButton foo = new JButton("Foo");
                foo.addActionListener(e -> {
                    JModalDialog dialog = new JModalDialog();
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

                JToolWindowBar right = dock.getRight();
                right.addToolWindow(win1);
                right.addToolWindow(win2);


            }

            {
                JToolWindow main = new JToolWindow("Center Component");
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
                JToolWindow main = new JToolWindow("South Component");
                main.setLayout(new BorderLayout());
                {
                    JTabbedPane tabs = new JTabbedPane();
                    tabs.addTab("Tab 1", new JTextArea(LoremIpsum.LOREM_IPSUM));
                    tabs.addTab("Tab 2", new JTextArea(LoremIpsum.LOREM_IPSUM));
                    main.add(tabs, BorderLayout.CENTER);
                }

                JToolWindowBar bottom = dock.getBottom();
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