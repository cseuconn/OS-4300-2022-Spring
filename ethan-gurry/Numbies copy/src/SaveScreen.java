import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SaveScreen {
    private JPanel saveScreen;
    private JLabel title;
    private JButton saveButton;
    private JButton continueButton;

    private JFrame rootFrame;
    private Game game;
    private SaveFile sf;

    public SaveScreen(JFrame frame, Game game, SaveFile sf)
    {
        rootFrame = frame;
        this.game = game;
        this.sf = sf;

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == saveButton) {
                    // save the game and exit
                    sf.save(game);
                    rootFrame.dispose();
                }
            }
        });
        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.changeOrderOfPlayers();
                sf.setGame(game);

                // go to play game screen
                PlayGame pg = new PlayGame(rootFrame, sf);
                rootFrame.remove(saveScreen);
                rootFrame.add(pg.getSelectionScreen());
                rootFrame.revalidate();
                rootFrame.repaint();
            }
        });
    }

    public JPanel getSaveScreen() {
        return saveScreen;
    }
}
