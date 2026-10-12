package chronos.swing;

import javax.swing.*;
import java.awt.*;

public class JModalDialog extends JDialog {
    private JPanel footer, buttonBar;

    public void showDialog(String title, boolean modal, Dimension size, JComponent content, JButton defaultButton, JButton... buttons) {
        setTitle(title);
        setSize(size);
        setLayout(new BorderLayout());
        add(content, BorderLayout.CENTER);
        setModalityType(modal ? ModalityType.APPLICATION_MODAL : ModalityType.MODELESS);

        footer = new JPanel();
        {
            footer.setLayout(new BorderLayout());
            footer.add(new JSeparator(), BorderLayout.NORTH);
            {
                buttonBar = new JPanel();
                buttonBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
                for(JButton button : buttons) buttonBar.add(button);
                getRootPane().setDefaultButton(defaultButton);
                buttonBar.add(defaultButton);

            }
            footer.add(buttonBar, BorderLayout.CENTER);
            add(footer, BorderLayout.SOUTH);
        }


    }

}
