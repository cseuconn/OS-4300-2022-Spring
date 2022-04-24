import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerMenu {
    private JPanel playerMenu;
    private JComboBox numPLabel;
    private JButton selectButton;
    private JTextField pName;
    private JButton enterButton;
    private JLabel pLabel;

    private JFrame rootFrame;
    private SaveFile sf;
    private int numPlayers;
    private int playerIndex;
    private Player[] players;

    public PlayerMenu(JFrame frame, SaveFile sf) {
        rootFrame = frame;
        this.sf = sf;

        // listen if select button has been pressed
        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == selectButton) {
                    // get rid of number of players option
                    numPlayers = Integer.parseInt((String)numPLabel.getSelectedItem());
                    playerMenu.remove(numPLabel);
                    playerMenu.remove(selectButton);
                    playerMenu.revalidate();
                    playerMenu.repaint();

                    players = new Player[numPlayers];
                    playerIndex = 0;
                }
            }
        });

        // listen if enter button has been pressed
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == enterButton) {
                    // add player to players array
                    players[playerIndex] = new Player(pName.getText(), 0);
                    playerIndex += 1;

                    //update label
                    pLabel.setText("Enter Player " + (playerIndex + 1) + "'s name:");

                    // check if all players have been added
                    if (playerIndex == numPlayers) {
                        // set the game for save file
                        Game game = new Game(players, numPlayers);
                        sf.setGame(game);

                        // go to play game screen
                        PlayGame pg = new PlayGame(rootFrame, sf);
                        rootFrame.remove(playerMenu);
                        rootFrame.add(pg.getSelectionScreen());
                        rootFrame.revalidate();
                        rootFrame.repaint();
                    }
                }
            }
        });
    }


    public JPanel getPlayerMenu() {
        return playerMenu;
    }
}
