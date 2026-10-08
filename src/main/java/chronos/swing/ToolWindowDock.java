package chronos.swing;

import javax.swing.*;
import java.awt.*;

public class ToolWindowDock extends JPanel {
    private ToolWindowBar left, right;
    public ToolWindowDock() {
        setLayout(new BorderLayout());
        left = new ToolWindowBar(ToolWindowBar.Side.Left);
        right = new ToolWindowBar(ToolWindowBar.Side.Right);
        add(left, BorderLayout.WEST);
        add(right, BorderLayout.EAST);
        setBackground(UIManager.getColor("Desktop.background"));


    }

    public ToolWindowBar getLeft() {
        return left;
    }

    public ToolWindowBar getRight() {
        return right;
    }
}
