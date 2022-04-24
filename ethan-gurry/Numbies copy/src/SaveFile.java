import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class SaveFile {

    private String saveName;
    private Game game;

    public SaveFile(String name)
    {
        saveName = name;
    }

    public void save(Game game)
    {
        try {
            //create a FileWriter object
            FileWriter writer = new FileWriter(saveName);

            // write game info
            writer.write(game.toString());

            //close the FileWriter object
            writer.close();
        }

        //catch any IOExceptions
        catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void loadSave()
    {
        //Create the file object
        File f = new File(saveName);

        //make a new scanner to read the file
        Scanner in;
        try {
            in = new Scanner(f);
        } catch (FileNotFoundException e) {
            // Don't attempt to load a game from a file that doesn't exist
            return;
        }

        // read first line to determine number of players
        String first = in.nextLine();
        int numPlayers = Integer.parseInt(first);

        // create array to keep track of players
        Player[] players = new Player[numPlayers];
        int pIndex = 0;

        // use for pot value
        int pot = 0;

        // scan each line while there is another line available
        while (in.hasNextLine()) {
            String line = in.nextLine();
            if (line.contains("Player")) {
                String []pArgs = line.split(", ");
                players[pIndex] = new Player(pArgs[1], Integer.parseInt(pArgs[2]));
                pIndex += 1;
            }
            else if (line.contains("Pot")) {
                String[] args = line.split(", ");
                pot = Integer.parseInt(args[1]);
            }
        }
        // close the scanner
        in.close();

        game = new Game(players, numPlayers);
        game.setPot(pot);
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
