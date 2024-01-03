import ui.DrawingPane;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        JFrame frame = new JFrame();
        frame.getContentPane().add(new DrawingPane());
        frame.setSize(640,480);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(
                dim.width / 2 - frame.getSize().width / 2,
                dim.height / 2 - frame.getSize().height / 2
        );
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}