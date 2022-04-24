import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayGame {
    private JPanel selectionScreen;
    private JLabel title;
    private JLabel rule;
    private JTextField guessNum;
    private JButton startButton;

    private JFrame rootFrame;

    public PlayGame(JFrame frame, SaveFile sf)
    {
        rootFrame = frame;

        // enter gamemaster's name in title
        Game game = sf.getGame();
        Player[] players = game.getPlayers();
        Player master = players[0];
        title.setText(master.getName() + ", Enter Your Number");


        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == startButton) {

                    // make sure number is between 1 & 7 digits & doesn't contain letters
                    String input = guessNum.getText();
                    boolean hasLetters = false;
                    for (char c: input.toCharArray()) {
                        if (Character.isAlphabetic(c)) hasLetters = true;
                    }

                    if (input.length() >= 8 || hasLetters) {
                        JOptionPane.showMessageDialog(null,
                                "Enter a number between 1 & 7 digits",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    else {
                        // set up the game
                        int num = Integer.parseInt(input);
                        game.setGuessNum(num);
                        game.detTurns();
                        game.calculatePoints();

                        // only 1 guess allowed per player, so go to last guess
                        if (game.getNumTurns() == 1 * game.getNumPlayers()) {
                            // check if it is a 1 digit game or 2 digit game
                            if (num < 10) {
                                // 1 digit game
                                LastGuessPage lpg = new LastGuessPage(rootFrame, sf, game, "_", "n/a", "n/a");
                                rootFrame.remove(selectionScreen);
                                rootFrame.add(lpg.getScreen());
                                rootFrame.revalidate();
                                rootFrame.repaint();
                            }
                            // 2 digit game
                            else {
                                LastGuessPage lpg = new LastGuessPage(rootFrame, sf, game, "_ _", "n/a", "n/a");
                                rootFrame.remove(selectionScreen);
                                rootFrame.add(lpg.getScreen());
                                rootFrame.revalidate();
                                rootFrame.repaint();
                            }
                        }
                        else {

                            // go to game board
                            GameBoard gb = new GameBoard(frame, sf, game);
                            rootFrame.remove(selectionScreen);
                            rootFrame.add(gb.getBoard());
                            rootFrame.revalidate();
                            rootFrame.repaint();
                        }
                    }
                }
            }
        });
    }

    public JPanel getSelectionScreen() {
        return selectionScreen;
    }
}
