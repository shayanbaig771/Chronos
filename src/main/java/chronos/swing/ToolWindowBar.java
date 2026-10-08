package chronos.swing;

import com.formdev.flatlaf.ui.FlatMenuBarBorder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ToolWindowBar extends JPanel {

    private JPanel tabs;
    private ToolWindow activeWindow;
    private List<ToolWindow> toolWindows;


    public enum Side {
        Left,
        Right,
        Bottom,
    }
    private Side side;


    public ToolWindowBar(Side side) {
        super();
        this.side = side;
        setLayout(new BorderLayout());
        setBorder(new FlatMenuBarBorder());
        tabs = new JPanel();
        tabs.setLayout(new BoxLayout(tabs, side == Side.Bottom ? BoxLayout.X_AXIS : BoxLayout.Y_AXIS));

        switch (side) {
            case Right, Left:
                add(tabs, side == Side.Left ? BorderLayout.WEST : BorderLayout.EAST);
                break;
            case Bottom:
                add(tabs, BorderLayout.SOUTH);
                break;
        }


        toolWindows = new ArrayList<>();
    }

    public Side getSide() {
        return side;
    }


    public void removeToolWindow(ToolWindow toolWindow) {
        ToolWindowTab tab = toolWindow.getTab();
        tab.setSide(null);
        toolWindows.remove(toolWindow);
        tabs.remove(tab);
        toolWindow.setSide(null);
        tab.removeActionListener(tab.getOpenToolWindowListener());
        toolWindow.setActive(false);
        revalidate();
        repaint();

    }

    public void addToolWindow(ToolWindow toolWindow) {
        ToolWindowTab tab = toolWindow.getTab();
        tab.setSide(side);
        toolWindow.setToolWindowBar(this);
        toolWindows.add(toolWindow);
        tabs.add(tab);
        toolWindow.setSide(side);
        revalidate();
        repaint();

        tab.setOpenToolWindowListener(e -> {
            boolean shouldOpen = !toolWindow.isActive();
            if(shouldOpen) {
                if(activeWindow != null) {
                    remove(activeWindow);
                    activeWindow.setActive(false);
                }
                add(toolWindow, BorderLayout.CENTER);
                activeWindow = toolWindow;
                toolWindow.setActive(true);
            }
            else {
                remove(activeWindow);
                activeWindow.setActive(false);
                activeWindow = null;
            }


            revalidate();
            repaint();


        });

        tab.addActionListener(tab.getOpenToolWindowListener());
    }

}
