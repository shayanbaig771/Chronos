package chronos.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Rectangle2D;

import static chronos.swing.ToolWindowBar.Side.Left;
import static chronos.swing.ToolWindowBar.Side.Right;

public class ToolWindow extends JComponent {
    private String name;
    private ToolWindowTab tab;
    private boolean active;
    private int resizeMargin = 3;
    private ToolWindowBar.Side side;
    private Rectangle resizeHandle = new Rectangle();
    private boolean isResizing = false;


    public ToolWindow(String name) {
        this.name = name;
        tab = new ToolWindowTab(name);
        tab.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(SwingUtilities.isRightMouseButton(e)) {
                    JPopupMenu popupMenu = new JPopupMenu();
                    JMenuItem showAsWindowItem = new JMenuItem("Show as Window");



                    popupMenu.add(showAsWindowItem);
                    popupMenu.show(tab, e.getX(), e.getY());
                }
            }
        });
        setBorder(new ToolWindowBorder(this, resizeMargin));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if(resizeHandle.contains(e.getPoint())) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
                    isResizing = true;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setCursor(Cursor.getDefaultCursor());
                isResizing = false;
            }

        });

        addMouseMotionListener(new MouseMotionAdapter() {


            @Override
            public void mouseDragged(MouseEvent e) {
                if(isResizing) {
                    if(side == Left) setPreferredSize(new Dimension(e.getX(), getHeight()));
                    if(side == Right) setPreferredSize(new Dimension(getWidth() - e.getX(), getHeight()));

                    revalidate();
                    repaint();
                    setHandleBounds();
                }
            }
        });
    }

    public void setSide(ToolWindowBar.Side side) {
        this.side = side;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.RED);
        setHandleBounds();
        g.fillRect(resizeHandle.x, resizeHandle.y, resizeHandle.width, resizeHandle.height);

    }

    private void setHandleBounds() {

        if(side == Left) resizeHandle.setBounds(getWidth() - resizeMargin, 0, resizeMargin, getHeight());
        else if(side == Right) resizeHandle.setBounds(0, 0, resizeMargin, getHeight());

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
