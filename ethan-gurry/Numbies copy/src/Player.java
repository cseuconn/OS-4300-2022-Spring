public class Player {

    private String name;
    private int score;

    public Player(String n, int s)
    {
        name = n;
        score = s;
    }

    // Getters
    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    // Setters
    public void setName(String n) {
        name = n;
    }

    public void setScore(int s) {
        score = s;
    }

    public void increaseScore(int s) {
        score += s;
    }

    // to String method
    public String toString() {
        return  "Player, " + name + ", " + score + "\n";
    }


}
