package chronos.swing;

import javax.swing.*;

import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.util.tinyfd.TinyFileDialogs;

import java.nio.file.Path;

import static org.lwjgl.system.MemoryStack.stackPush;


public class NativeFilePicker {
    public static Path showOpenFileDialog(String title, String... filters) {
        if(!SwingUtilities.isEventDispatchThread())
            throw new IllegalStateException("NativeFilePicker must be called on the EDT");

        try (MemoryStack stack = stackPush()) {
            PointerBuffer aFilterPatterns = stack.mallocPointer(filters.length);
            for(String filter : filters) aFilterPatterns.put(stack.UTF8(filter));
            aFilterPatterns.flip();
            String result = TinyFileDialogs.tinyfd_openFileDialog(title, "", aFilterPatterns, null, true);
            if(result != null) return Path.of(result);
        }

        return null;
    }
    public static Path showSaveFileDialog(String title, String... filters) {
        if(!SwingUtilities.isEventDispatchThread())
            throw new IllegalStateException("NativeFilePicker must be called on the EDT");

        try (MemoryStack stack = stackPush()) {
            PointerBuffer aFilterPatterns = stack.mallocPointer(filters.length);
            for(String filter : filters) aFilterPatterns.put(stack.UTF8(filter));
            aFilterPatterns.flip();
            String result = TinyFileDialogs.tinyfd_saveFileDialog(title, "", aFilterPatterns, null);
            if(result != null) return Path.of(result);
        }

        return null;
    }
    public static Path showSelectFolderDialog(String title) {
        if(!SwingUtilities.isEventDispatchThread())
            throw new IllegalStateException("NativeFilePicker must be called on the EDT");

        String result = TinyFileDialogs.tinyfd_selectFolderDialog(title, "");
        if(result != null) return Path.of(result);

        return null;
    }
}
