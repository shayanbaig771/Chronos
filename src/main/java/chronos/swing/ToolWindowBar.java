package chronos.swing;

import com.formdev.flatlaf.ui.FlatMenuBarBorder;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ToolWindowBar extends JPanel {

    private JPanel tabs;
    private ToolWindow activeWindow;
    private List<ToolWindow> toolWindows;
    public enum Side {
        Left,
        Right
    }
    private Side side;


    public ToolWindowBar(Side side) {
        super();
        this.side = side;
        setLayout(new BorderLayout());
        setBorder(new FlatMenuBarBorder());
        tabs = new JPanel();
        tabs.setLayout(new BoxLayout(tabs, BoxLayout.Y_AXIS));
        add(tabs, side == Side.Left ? BorderLayout.WEST : BorderLayout.EAST);

        toolWindows = new ArrayList<>();
    }

    public Side getSide() {
        return side;
    }

    public void addToolWindow(ToolWindow toolWindow) {
        ToolWindowTab tab = toolWindow.getTab();
        toolWindows.add(toolWindow);
        tabs.add(tab);
        toolWindow.setSide(side);

        tab.addActionListener(e -> {
            boolean opening = !toolWindow.isActive();


            if(opening) {
                if(activeWindow != null) {
                    remove(activeWindow);
                    activeWindow.setActive(false);
                }

                add(toolWindow, BorderLayout.CENTER);
                activeWindow = toolWindow;
            }
            else {
                remove(activeWindow);
                activeWindow = null;
            }
            toolWindow.setActive(opening);
            revalidate();
            repaint();


        });
    }

}
