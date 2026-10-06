package chronos.swing;

import javax.swing.*;

public class ToolWindowBar extends JPanel {
    public ToolWindowBar() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    public void addTab(ToolWindowTab toolWindow) {
        add(toolWindow);
    }
}
