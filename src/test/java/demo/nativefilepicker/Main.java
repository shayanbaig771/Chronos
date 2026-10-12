package demo.nativefilepicker;

import javax.swing.*;
import java.nio.file.Path;

import chronos.swing.JNativeFilePicker;

class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            JFrame frame = new JFrame("NativeFilePicker Demo");
            frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

            JButton openFile = new JButton("Open File");
            {
                openFile.addActionListener(e -> {
                    Path result = JNativeFilePicker.showOpenFileDialog("Select a file","*.txt", "*.pdf");
                    JOptionPane.showMessageDialog(null, "You selected: " + result);
                });
                frame.add(openFile);
            }

            JButton openFolder = new JButton("Open Folder");
            {
                openFolder.addActionListener(e -> {
                    Path result = JNativeFilePicker.showSelectFolderDialog("Select a folder");
                    JOptionPane.showMessageDialog(null, "You selected: " + result);
                });
                frame.add(openFolder);
            }

            JButton saveFile = new JButton("Save File");
            {
                saveFile.addActionListener(e -> {
                    Path result = JNativeFilePicker.showSaveFileDialog("Save a file","*.txt", "*.pdf");
                    JOptionPane.showMessageDialog(null, "You selected: " + result);
                });
                frame.add(saveFile);
            }


            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 200);
            frame.setVisible(true);
        });





    }
}