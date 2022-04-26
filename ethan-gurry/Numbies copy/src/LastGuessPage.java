import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *  On the last guess, players guess each digit one by one
 *  If a digit is correct, gamemaster responds yes (in our case number will be revealed on the gameboard)
 *  After each digit is guessed, gamemaster still responds up or down
 */
public class LastGuessPage {
    private JPanel screen;
    private JLabel potLabel;
    private JTextField guess;
    private JButton enterButton;
    private JLabel lastGuess;
    private JLabel responseLabel;
    private JLabel guessNumLabel;
    private JLabel currDigitLabel;
    private JLabel playerTitle;

    private JFrame rootFrame;
    private SaveFile sf;
    private Game game;
    private int[] lowerGuesses;
    private int[] upperGuesses;
    private int digits;
    private String stringGuess;

    // string representation of guessNum
    private String answer;

    // the current digit that a player is guessing
    private int currDigit;

    // the player who's turn it is
    private Player pTurn;
    private int turnIndex;

    public LastGuessPage(JFrame frame, SaveFile sf, Game g, String displayNum, String lGuess, String response) {
        rootFrame = frame;
        this.sf = sf;
        game = g;

        // set each guesses array to number of digits
        digits = String.valueOf(game.getGuessNum()).length();
        lowerGuesses = new int[digits];
        upperGuesses = new int[digits];

        // get first player & set them as the player who's turn it is
        pTurn = game.getPlayers()[1];
        turnIndex = 1;

        // initialize answer
        answer = String.valueOf(game.getGuessNum());

        // initialize the string guess to be empty string
        stringGuess = "";

        // set labels
        guessNumLabel.setText(displayNum);
        this.lastGuess.setText(lGuess);
        responseLabel.setText(response);
        playerTitle.setText(pTurn.getName() + "'s Last Guess");
        potLabel.setText("Pot: " + game.getPot());

        // enter button was pressed
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == enterButton) {
                    // make sure player hasn't guessed every digit yet
                    if (currDigit != digits) {
                        // get the digit guess & add it to string guess
                        String digitGuess = guess.getText();
                        stringGuess += digitGuess;

                        // check if guess is correct
                        char[] answerArray = answer.toCharArray();
                        if (answerArray[currDigit] == digitGuess.charAt(0)) {
                            // it is correct, so show the digit
                            revealDigit(currDigit);
                        }

                        // update which digit player is guessing
                        // (make sure it is in bounds)
                        currDigit += 1;
                        if (currDigit == digits) {
                            // guessed all digits, show response
                            System.out.println(stringGuess);
                            String response = game.makeGuess(Integer.parseInt(stringGuess), pTurn);
                            if (response.equals("correct")) {
                                // award points
                                game.awardPoints(pTurn);

                                // let player know they guessed correctly
                                JOptionPane.showMessageDialog(null,
                                        pTurn.getName() + ", you have guessed the number!!!",
                                        "Correct!", JOptionPane.ERROR_MESSAGE);

                                // go to continue screen
                                SaveScreen ss = new SaveScreen(frame, game, sf);
                                rootFrame.remove(screen);
                                rootFrame.add(ss.getSaveScreen());
                                rootFrame.revalidate();
                                rootFrame.repaint();
                                return;
                            }

                            // guess is incorrect
                            else {
                                responseLabel.setText("Response: " + response);
                                lastGuess.setText("Last Guess: " + stringGuess);

                                addToGuesses(stringGuess);
                                checkGuesses();
                            }

                            // update whose turn it is
                            nextTurn();
                        }
                        currDigitLabel.setText("Enter Guess For Digit " + (currDigit + 1));
                    }

                    // player has guessed every digit
                    else
                    {
                        nextTurn();
                    }
                }
            }
        });
    }

    public void nextTurn() {
        // reset guess string
        stringGuess = "";

        //reset which digit is currently being guessed
        currDigit = 0;
        currDigitLabel.setText("Guessing Digit " + (currDigit + 1));

        // change whose turn it is
        if (turnIndex == game.getPlayers().length - 1) {
            // last person just went, so end game

            // change order of players for next round
            game.changeOrderOfPlayers();

            // let them know game is over
            JOptionPane.showMessageDialog(null,
                    "No one guessed correctly, pot will be carried over to next round.",
                    "Sorry! :(", JOptionPane.ERROR_MESSAGE);
            // go to continue screen
            SaveScreen ss = new SaveScreen(rootFrame, game, sf);
            rootFrame.remove(screen);
            rootFrame.add(ss.getSaveScreen());
            rootFrame.revalidate();
            rootFrame.repaint();
        } else {
            turnIndex += 1;
            pTurn = game.getPlayers()[turnIndex];

            playerTitle.setText(pTurn.getName() + "'s Last Guess");
        }
    }

    /**
     * Checks if any of the digits in the guess are either 1 above or below corresponding digit in guessNum
     * If it is, add guess to guesses array
     * This way we can check later if a digit has been determined by a players guess
     */
    public void addToGuesses(String guess)
    {
        int num = Integer.parseInt(guess);
        for (int i = 0; i < digits-1; i++) {
            // represents a digit in guess
            int guessDigit = Integer.parseInt(guess.substring(i, i+1));

            // represents a digit in guessNum
            int gNumDigit = Integer.parseInt(String.valueOf(game.getGuessNum()).substring(i, i+1));

            // Check for upper bound on digit (include guess digit equals guessNum digit)
            if ((gNumDigit == guessDigit && num > game.getGuessNum()) || gNumDigit == guessDigit - 1) {
                upperGuesses[i] = num;
            }
            // Check for lower bound
            if ((gNumDigit == guessDigit && num < game.getGuessNum()) || gNumDigit == guessDigit + 1) {
                lowerGuesses[i] = num;
            }
        }
    }

    /**
     * Checks if a digit has an upper and lower bound, meaning it can be displayed
     * Must also have the digit revealed before it
     */
    public void checkGuesses()
    {
        for (int i =0; i < digits; i++) {
            if (lowerGuesses[i] == 0 || upperGuesses[i] == 0) {
                break;
            }
            else {
                // Reveal the corresponding digit
                revealDigit(i);
            }
        }
    }

    public void revealDigit(int index) {
        String onScreenNum = guessNumLabel.getText();
        char[] letters = onScreenNum.toCharArray();
        char [] gNumDigits = String.valueOf(game.getGuessNum()).toCharArray();
        letters[index*2] = gNumDigits[index];
        guessNumLabel.setText(String.valueOf(letters));
    }

    public JPanel getScreen() {
        return screen;
    }
}
