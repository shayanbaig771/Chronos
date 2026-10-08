package chronos.swing;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.ActionListener;

public class ToolWindowTab extends JButton {
    private ActionListener openToolWindowListener;
    private ToolWindowBar.Side side;
    public ToolWindowTab(String text) {
        super(text);
        setUI(new ChrButtonUI());
        setContentAreaFilled(false);
        setOpaque(false);
    }

    public ToolWindowBar.Side getSide() {
        return side;
    }

    public void setSide(ToolWindowBar.Side side) {
        this.side = side;
    }

    public ActionListener getOpenToolWindowListener() {
        return openToolWindowListener;
    }

    public void setOpenToolWindowListener(ActionListener openToolWindowListener) {
        this.openToolWindowListener = openToolWindowListener;
    }

    private class ChrButtonUI extends BasicButtonUI {
        private int padding = 2;


        @Override
        public void paint(Graphics g, JComponent c) {
            Graphics2D g2d = (Graphics2D) g.create();
            AbstractButton button = (AbstractButton) c;
            String text = button.getText();
            ButtonModel model = button.getModel();
            g2d.setColor(UIManager.getColor("Button.background"));

            if(model.isRollover()) g2d.setColor(UIManager.getColor("Button.hoverBackground"));
            if(model.isPressed() || model.isArmed())
                g2d.setColor(UIManager.getColor("Button.pressedBackground"));



            g2d.setRenderingHint(
                    RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON
            );
            g2d.setRenderingHint(
                    RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BICUBIC
            );
            g2d.setRenderingHint(
                    RenderingHints.KEY_FRACTIONALMETRICS,
                    RenderingHints.VALUE_FRACTIONALMETRICS_ON
            );
            g2d.fillRect(0, 0, c.getWidth(), c.getHeight());
            g2d.setColor(Color.WHITE);
            if(side != ToolWindowBar.Side.Bottom)
                g2d.rotate(Math.PI / 2);

            g2d.setColor(UIManager.getColor("Button.foreground"));
            if(model.isRollover()) g2d.setColor(UIManager.getColor("Button.hoverForeground"));
            if(model.isPressed() || model.isArmed())
                g2d.setColor(UIManager.getColor("Button.pressedForeground"));

            if(side != ToolWindowBar.Side.Bottom)
                g2d.drawString(text, 4 * padding, -padding*2);
            else {
                FontMetrics fm = c.getFontMetrics(c.getFont());
                g2d.drawString(text, 4 * padding, fm.getHeight());
            }
            g2d.dispose();
        }


        @Override
        public Dimension getPreferredSize(JComponent c) {
            FontMetrics fm = c.getFontMetrics(c.getFont());
            AbstractButton button = (AbstractButton) c;

            if(side != ToolWindowBar.Side.Bottom) {
                return new Dimension(fm.getHeight() + 2 * padding, fm.stringWidth(button.getText()) + 8 * padding);
            }
            else {
                return new Dimension(fm.stringWidth(button.getText()) + 8 * padding, fm.getHeight() + 2 * padding);
            }

        }


    }
}
