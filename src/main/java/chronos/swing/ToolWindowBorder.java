package chronos.swing;

import com.formdev.flatlaf.util.HiDPIUtils;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;

public class ToolWindowBorder extends AbstractBorder {
    private ToolWindow toolWindow;
    private int margin = 3;
    private int resizeMargin;

    public ToolWindowBorder(ToolWindow toolWindow, int resizeMargin) {
        this.toolWindow = toolWindow;
        this.resizeMargin = resizeMargin;

    }

    @Override
    public Insets getBorderInsets(Component c) {
        if(!toolWindow.isActive()) return new Insets(0, 0, 0, 0);
        return new Insets(c.getFontMetrics(c.getFont()).getAscent() + resizeMargin + 2 * margin, resizeMargin, resizeMargin, resizeMargin);
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


        g2d.setColor(Color.WHITE);
        HiDPIUtils.drawStringWithYCorrection(toolWindow, g2d, toolWindow.getTitle(), margin, h + margin);


    }
}

