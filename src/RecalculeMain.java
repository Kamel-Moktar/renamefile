import gui.RecalculeGUI;

import javax.swing.*;
import java.awt.*;

public class RecalculeMain {

    public static void main(String[] arg) {
        RecalculeGUI gui = new RecalculeGUI();
        gui.setVisible(true);
        gui.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        gui.setSize(new Dimension(800, 500));
        gui.setLocation(400,200);
    }
}
