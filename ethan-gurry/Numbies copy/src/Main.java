import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args)
    {
        // Initialize the interface
        JFrame frame = new JFrame("Numbies Game");
        frame.setMinimumSize(new Dimension(900, 800));
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Load the main menu
        StartMenu startMenu = new StartMenu(frame);
        frame.add(startMenu.getStartMenu());

        // Repaint so the frame isn't blank
        frame.revalidate();
        frame.repaint();
    }
}
