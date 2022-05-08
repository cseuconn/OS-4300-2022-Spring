import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class NewGameMenu {


    private JPanel newGame;
    private JButton save1;
    private JButton save2;
    private JButton save3;

    private JFrame rootFrame;
    private boolean s1;
    private boolean s2;
    private boolean s3;

    public NewGameMenu(JFrame frame) {
        rootFrame = frame;
        s1 = s2 = s3 = true;


        save1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == save1) {
                    // check for overwrite
                    checkForSave("save1", s1);
                }
            }
        });
        save2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // check for overwrite
                if (e.getSource() == save2) {
                    checkForSave("save2", s2);
                }
            }
        });
        save3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // check for overwrite
                if (e.getSource() == save3) {
                    checkForSave("save3", s3);
                }
            }
        });
    }

    public JPanel getNewGame() {
        return newGame;
    }

    public void checkForSave(String fileName, boolean s)
    {
        File f = new File(fileName);
        if (f.exists() && s) {
            // display warning message
            JOptionPane.showMessageDialog(null,
                    "WARNING, If same save is selected again save file will be overwritten!!",
                    "WARNING", JOptionPane.ERROR_MESSAGE);
            // update the corresponding save boolean value so next time it overwrites the file
            if (fileName.equals("save1")) {
                s1 = false;
            }
            else if (fileName.equals("save2")) {
                s2 = false;
            }
            else if (fileName.equals("save3")){
                s3 = false;
            }
        }
        // otherwise go to player menu
        else {
            goToPlayerMenu(fileName);
        }
    }

    public void goToPlayerMenu(String saveName) {
        SaveFile sf = new SaveFile(saveName);
        PlayerMenu pm = new PlayerMenu(rootFrame, sf);
        rootFrame.remove(newGame);
        rootFrame.add(pm.getPlayerMenu());
        rootFrame.revalidate();
        rootFrame.repaint();
    }
}


