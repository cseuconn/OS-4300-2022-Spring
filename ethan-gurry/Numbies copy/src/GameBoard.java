import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameBoard {
    private JPanel board;
    private JLabel playerTitle;
    private JLabel potLabel;
    private JTextField guess;
    private JLabel guessNum;
    private JButton guessButton;
    private JLabel lastGuess;
    private JLabel responseLabel;
    private JLabel turnsLabel;

    private JFrame rootFrame;
    private SaveFile sf;
    private Game game;
    private int numRounds;

    // keep track of guesses to know when to reveal each digit
    private int[] lowerGuesses;
    private int[] upperGuesses;
    private int digits;

    // the player who's turn it is
    private Player pTurn;
    private int turnIndex;

    public GameBoard(JFrame frame, SaveFile sf, Game game)
    {
        rootFrame = frame;
        this.sf = sf;
        this.game = game;
        numRounds = game.getNumTurns()/ game.getNumPlayers();


        // get game board ready for play
        Player[] players = game.getPlayers();
        pTurn = players[1];
        turnIndex = 1;
        playerTitle.setText(pTurn.getName() + "'s Turn (Score: " + pTurn.getScore() + ")");
        potLabel.setText(String.valueOf("Pot: " + game.getPot()));
        turnsLabel.setText("Guesses Left: " + numRounds);

        // set up blanks for guessNum
        String num = "";
        digits = String.valueOf(game.getGuessNum()).length();
        for (int i = 0; i < digits; i++) {
            num += "_ ";
        }
        guessNum.setText(num);

        // set each guesses array to number of digits
        upperGuesses = new int[digits];
        lowerGuesses = new int[digits];



        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == guessButton)
                {
                    // make sure guess is between 1 & 7 digits & doesn't contain letters
                    String input = guess.getText();
                    boolean hasLetters = false;
                    for (char c: input.toCharArray()) {
                        if (Character.isAlphabetic(c)) hasLetters = true;
                    }

                    if (input.length() >= 8 || hasLetters) {
                        JOptionPane.showMessageDialog(null,
                                "Enter a number between 1 & 7 digits",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    // otherwise, make the guess
                    else
                    {
                        String response = game.makeGuess(Integer.parseInt(input), pTurn);
                        if (response.equals("correct")) {
                            // award points
                            game.awardPoints(pTurn);

                            // let player know they guessed correctly
                            JOptionPane.showMessageDialog(null,
                                    pTurn.getName() + ", you have guessed the number!!!",
                                    "Correct!", JOptionPane.ERROR_MESSAGE);

                            // go to continue screen
                            SaveScreen ss = new SaveScreen(frame, game, sf);
                            rootFrame.remove(board);
                            rootFrame.add(ss.getSaveScreen());
                            rootFrame.revalidate();
                            rootFrame.repaint();
                        }

                        // guess is incorrect
                        else {
                            responseLabel.setText("Response: " + response);
                            lastGuess.setText("Last Guess: " + guess.getText());

                            addToGuesses(input);
                            checkGuesses();

                            nextTurn(response);
                        }
                    }
                }
            }
        });
    }

    public void nextTurn(String response)
    {
        // change whose turn it is
        if (turnIndex == game.getPlayers().length -1) {
            turnIndex = 1;
            numRounds -= 1;
            turnsLabel.setText("Guesses Left: " + numRounds);
        }
        else {
            turnIndex += 1;
        }
        pTurn = game.getPlayers()[turnIndex];

        playerTitle.setText(pTurn.getName() + "'s Turn (Score: " + pTurn.getScore() + ")");

        if (numRounds == 1) {
            // go to last guess page
            LastGuessPage lpg = new LastGuessPage(rootFrame, sf, game, guessNum.getText(),
                    lastGuess.getText(), responseLabel.getText());
            rootFrame.remove(board);
            rootFrame.add(lpg.getScreen());
            rootFrame.revalidate();
            rootFrame.repaint();
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

            // Check for upper bound on digit
            if ((gNumDigit == guessDigit && num > game.getGuessNum()) ||
                    (checkRestDigits(guess, i, true) && gNumDigit == guessDigit-1)) {
                upperGuesses[i] = num;
            }
            // Check for lower bound
            if ((gNumDigit == guessDigit && num < game.getGuessNum()) ||
                    (checkRestDigits(guess, i, false) && gNumDigit == guessDigit+1)) {
                lowerGuesses[i] = num;
            }
        }
    }

    public boolean checkRestDigits(String guess, int startingIndex, boolean upper)
    {
        for (int i = startingIndex+1; i < digits; i++) {
            // represent a digit in guess & guessNum
            String guessDigit = "";
            if (i == digits -1)
            {
                guessDigit = guess.substring(i);
            }
            else {
                guessDigit = guess.substring(i, i + 1);
            }
            if (upper) {
                // checking for all 0's for upper
                if (!guessDigit.equals("0")) {
                    return false;
                }
            }
            else {
                // checking for all 9's for lower
                if (!guessDigit.equals("9")) {
                    return false;
                }
            }
        }

        return  true;
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
                String onScreenNum = guessNum.getText();
                char[] letters = onScreenNum.toCharArray();
                char [] gNumDigits = String.valueOf(game.getGuessNum()).toCharArray();
                letters[i*2] = gNumDigits[i];
                guessNum.setText(String.valueOf(letters));
            }
        }
    }

    public JPanel getBoard() {
        return board;
    }


}
