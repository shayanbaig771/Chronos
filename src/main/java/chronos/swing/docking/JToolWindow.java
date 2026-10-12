package chronos.swing.docking;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static chronos.swing.docking.JToolWindowBar.Side.*;

public class JToolWindow extends JComponent {
    private String name;
    private JToolWindowTab tab;
    private boolean active;
    private int resizeMargin = 3;
    private JToolWindowBar.Side side;
    private Rectangle resizeHandle = new Rectangle();
    private boolean isResizing = false;
    private JToolWindowBar toolWindowBar;
    private boolean isWindow;



    public JToolWindow(String name) {
        this.name = name;
        tab = new JToolWindowTab(name, this);
        setBorder(new ToolWindowBorder(this, resizeMargin));


        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if(!isWindow) {
                    if (resizeHandle.contains(e.getPoint())) {
                        isResizing = true;
                        {
                            Cursor cursor = null;
                            switch (side) {
                                case Left -> cursor = Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR);
                                case Right -> cursor = Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR);
                                case Bottom -> cursor = Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR);
                            }
                            setCursor(cursor);
                        }


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
                    if(side == Bottom) setPreferredSize(new Dimension(getWidth(), getHeight() - e.getY()));

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

        JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(this));
        dialog.setTitle(name);
        dialog.add(this);
        dialog.pack();
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                isWindow = false;
                dialog.remove(JToolWindow.this);
                setBorder(new ToolWindowBorder(JToolWindow.this, resizeMargin));
                toolWindowBar.addToolWindow(JToolWindow.this);
            }
        });

        dialog.requestFocusInWindow();
        dialog.setVisible(true);

    }


    public JToolWindowBar getToolWindowBar() {
        return toolWindowBar;
    }

    public void setToolWindowBar(JToolWindowBar toolWindowBar) {
        this.toolWindowBar = toolWindowBar;
    }


    public void setSide(JToolWindowBar.Side side) {
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
        else if(side == Bottom) resizeHandle.setBounds(0, 0, getWidth(), resizeMargin);

    }

    protected boolean isActive() {
        return active;
    }

    protected void setActive(boolean active) {
        this.active = active;
    }

    public JToolWindowTab getTab() {
        return tab;
    }

    public String getTitle() {
        return name;
    }
}
