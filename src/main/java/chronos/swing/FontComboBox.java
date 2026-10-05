package chronos.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutionException;

public class FontComboBox extends JComboBox<FontComboBox.Pair> {
    public FontComboBox(float size, Font... fonts) {
        super();
        setRenderer(new ImageListCellRenderer());

        SwingWorker<Pair[], Void> worker = new SwingWorker<>() {
            @Override
            public Pair[] doInBackground() {
                Pair[] images = new Pair[fonts.length];

                for (int i = 0; i < fonts.length; i++) {
                    Font font = fonts[i].deriveFont(size);

                    int width, height, ascent;
                    BufferedImage temp = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
                    {
                        Graphics2D g = temp.createGraphics();
                        g.setFont(font);
                        FontMetrics fm = g.getFontMetrics();
                        width = fm.stringWidth(font.getName());
                        height = fm.getHeight();
                        ascent = fm.getAscent();
                        g.dispose();
                    }

                    if(width > 0 && height > 0) {
                        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                        {
                            Graphics2D g2d = image.createGraphics();
                            g2d.setFont(font);
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
                            g2d.drawString(font.getName(), 0, ascent);
                        }
                        images[i] = new Pair(image, font);
                    }
                    else images[i] = null;

                }
                return images;
            }

            @Override
            public void done() {
                try {
                    Pair[] images = get();
                    setModel(new DefaultComboBoxModel<>(images));
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        worker.execute();



    }

    public Font getSelectedFont() {
        Pair pair = (Pair) getSelectedItem();
        if (pair == null) return null;
        return pair.font;
    }

    protected static class Pair {
        public Image image;
        public Font font;

        public Pair(Image image, Font font) {
            this.image = image;
            this.font = font;
        }
    }



    private static class ImageListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            if(value == null) return new JLabel("Unknown");
            Pair pair = (Pair) value;
            return new JLabel(new ImageIcon(pair.image));
        }
    }


}
