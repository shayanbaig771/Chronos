package chronos.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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
    private JDialog window;
    private ToolWindowBar toolWindowBar;
    private boolean isWindow;



    public ToolWindow(String name) {
        this.name = name;
        tab = new ToolWindowTab(name);
        window = new JDialog();
        tab.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(SwingUtilities.isRightMouseButton(e)) {
                    JPopupMenu popupMenu = new JPopupMenu();
                    JMenuItem showAsWindowItem = new JMenuItem("Show as Window");
                    showAsWindowItem.addActionListener(e1 -> showAsWindow());

                    popupMenu.add(showAsWindowItem);
                    popupMenu.show(tab, e.getX(), e.getY());
                }
            }
        });
        setBorder(new ToolWindowBorder(this, resizeMargin));


        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if(!isWindow) {
                    if (resizeHandle.contains(e.getPoint())) {
                        isResizing = true;
                        setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
                    }
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if(isResizing && !SwingUtilities.isLeftMouseButton(e)) {
                    isResizing = false;
                    setCursor(Cursor.getDefaultCursor());
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isResizing = false;
                setCursor(Cursor.getDefaultCursor());
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



    public ToolWindowBar getToolWindowBar() {
        return toolWindowBar;
    }

    public void setToolWindowBar(ToolWindowBar toolWindowBar) {
        this.toolWindowBar = toolWindowBar;
    }

    private void showAsWindow() {
        isWindow = true;
        toolWindowBar.removeToolWindow(this);
        setBorder(null);
        window.setTitle(name);
        window.setSize(getSize());
        window.add(this);

        window.setVisible(true);
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dockWindow();
            }
        });
        window.requestFocusInWindow();
    }

    private void dockWindow() {
        isWindow = false;
        setBorder(new ToolWindowBorder(this, resizeMargin));
        toolWindowBar.addToolWindow(this);
    }

    public void setSide(ToolWindowBar.Side side) {
        this.side = side;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if(!isWindow) {
            g.setColor(Color.RED);
            setHandleBounds();
        }
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
