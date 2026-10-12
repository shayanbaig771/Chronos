package chronos.swing.docking;

import com.formdev.flatlaf.util.HiDPIUtils;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;

public class ToolWindowBorder extends AbstractBorder {
    private JToolWindow toolWindow;
    private int margin = 3;
    private int resizeMargin;

    public ToolWindowBorder(JToolWindow toolWindow, int resizeMargin) {
        this.toolWindow = toolWindow;
        this.resizeMargin = resizeMargin;

    }

    @Override
    public Insets getBorderInsets(Component c) {
        Font font = c.getFont();
        if(c.getFont() == null)
            font = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts()[0];
        return new Insets(c.getFontMetrics(font).getAscent() + resizeMargin + 2 * margin, resizeMargin, resizeMargin, resizeMargin);
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        super.paintBorder(c, g, x, y, width, height);

        Graphics2D g2d = (Graphics2D) g;


        int h = c.getFontMetrics(c.getFont()).getAscent();
        Color color1 = UIManager.getColor("InternalFrame.activeTitleBackground");
        Color color2 = UIManager.getColor("Button.background");

        GradientPaint gradient = new GradientPaint(
                x, y, color1,
                x + width, y, color2
        );


        g2d.setPaint(gradient);
        g2d.fillRect(x, y, width, h + 2* margin);

        g2d.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON
        );

        g2d.setRenderingHint(
                RenderingHints.KEY_FRACTIONALMETRICS,
                RenderingHints.VALUE_FRACTIONALMETRICS_ON
        );

        Color textColor = UIManager.getColor("InternalFrame.activeTitleForeground");

        g2d.setColor(textColor);
        HiDPIUtils.drawStringWithYCorrection(toolWindow, g2d, toolWindow.getTitle(), margin, h + margin);

        Color outlineColor = UIManager.getColor("TabbedPane.light");
        g2d.setColor(outlineColor);
        g2d.drawRect(x, y, c.getWidth(), c.getHeight());


    }
}

