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
    private ToolWindowBar toolWindowBar;
    private boolean isWindow;



    public ToolWindow(String name) {
        this.name = name;
        tab = new ToolWindowTab(name);
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

        tab.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(SwingUtilities.isRightMouseButton(e)) {
                    JPopupMenu optionsPopupMenu = new JPopupMenu(name);
                    JMenuItem showAsWindowMenuItem = new JMenuItem("Show as Window");
                    optionsPopupMenu.add(showAsWindowMenuItem);

                    optionsPopupMenu.show(e.getComponent(), e.getX(), e.getY());
                    showAsWindowMenuItem.addActionListener(e1 -> showAsWindow());
                }
            }
        });
    }

    private void showAsWindow() {
        isWindow = true;
        toolWindowBar.removeToolWindow(this);
        setActive(false);
        setBorder(null);

        JDialog dialog = new JDialog();
        dialog.setTitle(name);
        dialog.add(this);
        dialog.pack();
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                isWindow = false;
                dialog.remove(ToolWindow.this);
                setBorder(new ToolWindowBorder(ToolWindow.this, resizeMargin));
                toolWindowBar.addToolWindow(ToolWindow.this);
            }
        });

        dialog.requestFocusInWindow();
        dialog.setVisible(true);

    }


    public ToolWindowBar getToolWindowBar() {
        return toolWindowBar;
    }

    public void setToolWindowBar(ToolWindowBar toolWindowBar) {
        this.toolWindowBar = toolWindowBar;
    }


    public void setSide(ToolWindowBar.Side side) {
        this.side = side;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if(!isWindow) setHandleBounds();
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
