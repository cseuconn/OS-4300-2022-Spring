public class Game {

    // Gamemaster is the first player in the array
    private Player[] players;
    private int numTurns;
    private int numPlayers;

    // the points available to win for this round
    private int pot;

    // the number each player is trying to guess
    private int guessNum;


    public Game(Player[] p, int numP)
    {
        numPlayers = numP;
        players = new Player[numP];
        for (int i = 0; i < numP; i++) {
            players[i] = p[i];
        }
    }

    // getters
    public int getGuessNum() {
        return guessNum;
    }

    public int getNumTurns() {
        return numTurns;
    }

    public Player[] getPlayers() {
        return players;
    }

    public int getPot() {
        return pot;
    }

    public int getNumPlayers() { return numPlayers;};

    // setters
    public void setPot(int pot) {
        this.pot = pot;
    }

    public void setGuessNum(int guessNum) {
        this.guessNum = guessNum;
        detTurns();
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    /**
     * @param num players guess
     * Return 'up' gamemaster says "up" (guess is less than gamemaster's number)
     * Return 'down' gamemaster says "down"
     * If player guesses correctly, award them points & return 'correct'
     */
    public String makeGuess(int num, Player p) {

        numTurns -= 1;
        if (num > guessNum) return "down";

        if (num == guessNum) {
            awardPoints(p);
            return "correct";
        }
        return "up";
    }

    public void awardPoints(Player p)
    {
        p.increaseScore(pot);
        pot = 0;
    }

    public void detTurns()
    {
        String number = Integer.toString(guessNum);
        int size = number.length();
        if (size <= 2) {
            numTurns = 1 * numPlayers;
        }
        else if (size == 3) {
            numTurns = 2 * numPlayers;
        }
        else if (size <= 6) {
            numTurns = 3 * numPlayers;
        }
        else if (size == 7) {
            numTurns = 4 * numPlayers;
        }
        else {
            numTurns = 5 * numPlayers;
        }
    }

    public boolean isLastTurn()
    {
        if (numTurns == 1) return true;
        return false;
    }

    public void changeOrderOfPlayers()
    {
        Player temp;
        temp = players[0];
        for (int i = 0; i < players.length-1; i++) {
            players[i] = players[i+1];
        }
        players[players.length-1] = temp;
    }

    public void newRound(int guessNum)
    {
        // update the number the gamemaster selected
        this.guessNum = guessNum;

        // calculate the new number of turns
        detTurns();
    }

    // determine how many points should be added to the pot based on the new guessNum
    public void calculatePoints()
    {
        String number = Integer.toString(guessNum);
        int size = number.length();
        if (size <= 3) {
            pot += 1;
        }
        else if (size <= 6) {
            pot += 2;
        }
        else if (size <= 7) {
            pot += 3;
        }
    }

    public String toString()
    {
        String returnString = numPlayers + "\n";

        // add player info to string
        for (Player player: players) {
            returnString += player.toString();
        }

        // add game info to string
        returnString += "Pot, " + pot + "\n";

        return returnString;
    }
}
