import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartMenu {
    private JPanel startMenu;
    private JButton newGame;
    private JButton loadGame;

    // The JFrame created by the main method that houses the whole GUI
    private JFrame rootFrame;

    public StartMenu(JFrame frame)
    {
        rootFrame = frame;
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == newGame) {
                    NewGameMenu ngm = new NewGameMenu(rootFrame);
                    rootFrame.remove(startMenu);
                    rootFrame.add(ngm.getNewGame());
                    rootFrame.revalidate();
                    rootFrame.repaint();
                }
            }
        });
        loadGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoadMenu lm = new LoadMenu(rootFrame);
                rootFrame.remove(startMenu);
                rootFrame.add(lm.getLoadMenu());
                rootFrame.revalidate();
                rootFrame.repaint();
            }
        });
    }

    public JPanel getStartMenu() {
        return startMenu;
    }
}
