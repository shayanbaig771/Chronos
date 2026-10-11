package chronos.swing;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ToolWindowDockState {
    private Map<String, ToolWindowState> toolWindowStates = new HashMap<>();

    public Map<String, ToolWindowState> getToolWindowStates() {
        return toolWindowStates;
    }

    public record ToolWindowState(ToolWindowBar.Side side, Dimension prefSize) {}
}
