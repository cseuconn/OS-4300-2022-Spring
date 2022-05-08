import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoadMenu {
    private JPanel loadMenu;
    private JLabel title;
    private JButton save1;
    private JButton save2;
    private JButton save3;

    private JFrame rootFrame;

    public LoadMenu(JFrame frame)
    {
        // set root frame
        rootFrame = frame;

        save1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == save1) {

                    // load the save file
                    SaveFile sf = new SaveFile("save1");
                    sf.loadSave();
                    goToGame(sf);
                }
            }
        });
        save2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == save2) {

                    // load the save file
                    SaveFile sf = new SaveFile("save2");
                    sf.loadSave();
                    goToGame(sf);
                }
            }
        });
        save3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == save3) {

                    // load the save file
                    SaveFile sf = new SaveFile("save3");
                    sf.loadSave();
                    goToGame(sf);
                }
            }
        });
    }

    public JPanel getLoadMenu() {
        return loadMenu;
    }

    public void goToGame(SaveFile sf)
    {
        PlayGame pg = new PlayGame(rootFrame, sf);
        rootFrame.remove(loadMenu);
        rootFrame.add(pg.getSelectionScreen());
        rootFrame.revalidate();
        rootFrame.repaint();
    }
}
