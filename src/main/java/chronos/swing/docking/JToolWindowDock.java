package chronos.swing.docking;

import javax.swing.*;
import java.awt.*;

public class JToolWindowDock extends JPanel {
    private JToolWindowBar left, right, bottom;
    public JToolWindowDock() {
        setLayout(new BorderLayout());
        left = new JToolWindowBar(JToolWindowBar.Side.Left);
        right = new JToolWindowBar(JToolWindowBar.Side.Right);
        bottom = new JToolWindowBar(JToolWindowBar.Side.Bottom);
        add(left, BorderLayout.WEST);
        add(right, BorderLayout.EAST);
        add(bottom, BorderLayout.SOUTH);
        setBackground(UIManager.getColor("Desktop.background"));


    }

    public JToolWindowBar getLeft() {
        return left;
    }

    public JToolWindowBar getRight() {
        return right;
    }

    public JToolWindowBar getBottom() {
        return bottom;
    }

    public ToolWindowDockState getToolWindowDockState() {


        ToolWindowDockState dockState = new ToolWindowDockState();

        JToolWindowBar[] toolWindowBars = {left, right, bottom};

        for(JToolWindowBar toolWindowBar : toolWindowBars) {
            for(JToolWindow toolWindow : toolWindowBar.getToolWindows()) {

                ToolWindowDockState.ToolWindowState toolWindowState = new ToolWindowDockState.ToolWindowState(toolWindowBar.getSide(), toolWindow.getPreferredSize());
                dockState.getToolWindowStates().put(toolWindow.getTitle(), toolWindowState);
            }
        }
        return dockState;
    }
}
