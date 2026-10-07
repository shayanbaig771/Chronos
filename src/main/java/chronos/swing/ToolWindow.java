package chronos.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class ToolWindow extends JComponent {
    private String name;
    private ToolWindowTab tab;
    private boolean active;

    private boolean resizing;

    public ToolWindow(String name) {
        this.name = name;
        tab = new ToolWindowTab(name);
        setBorder(new ToolWindowBorder(this));
    }

    public void setListeners(ToolWindowBar.Side side) {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
                resizing = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setCursor(Cursor.getDefaultCursor());
                resizing = false;
            }

        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (resizing) {


                    setPreferredSize(new Dimension(getWidth() + 10, getHeight()));
                    revalidate();
                    repaint();
                }
            }
        });
    }

    protected boolean isActive() {
        return active;
    }

    protected void setActive(boolean active) {
        this.active = active;
    }

    public ToolWindowTab getTab() {
        return tab;
    }

    @Override
    public String getName() {
        return name;
    }
}
