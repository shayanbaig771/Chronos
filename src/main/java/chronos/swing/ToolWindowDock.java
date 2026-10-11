package chronos.swing;

import javax.swing.*;
import java.awt.*;

public class ToolWindowDock extends JPanel {
    private ToolWindowBar left, right, bottom;
    public ToolWindowDock() {
        setLayout(new BorderLayout());
        left = new ToolWindowBar(ToolWindowBar.Side.Left);
        right = new ToolWindowBar(ToolWindowBar.Side.Right);
        bottom = new ToolWindowBar(ToolWindowBar.Side.Bottom);
        add(left, BorderLayout.WEST);
        add(right, BorderLayout.EAST);
        add(bottom, BorderLayout.SOUTH);
        setBackground(UIManager.getColor("Desktop.background"));


    }

    public ToolWindowBar getLeft() {
        return left;
    }

    public ToolWindowBar getRight() {
        return right;
    }

    public ToolWindowBar getBottom() {
        return bottom;
    }

    public ToolWindowDockState getToolWindowDockState() {


        ToolWindowDockState dockState = new ToolWindowDockState();

        ToolWindowBar[] toolWindowBars = {left, right, bottom};

        for(ToolWindowBar toolWindowBar : toolWindowBars) {
            for(ToolWindow toolWindow : toolWindowBar.getToolWindows()) {

                ToolWindowDockState.ToolWindowState toolWindowState = new ToolWindowDockState.ToolWindowState(toolWindowBar.getSide(), toolWindow.getPreferredSize());
                dockState.getToolWindowStates().put(toolWindow.getTitle(), toolWindowState);
            }
        }
        return dockState;
    }
}
