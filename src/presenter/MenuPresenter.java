package presenter;

import logic.GameLevels;
import logic.model.Board;
import ui.App;
import ui.Level;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MenuPresenter {
    public interface MenuView {
        void reDraw();
        void lightItUp(JButton btn);
        int getSelected();
        JButton getBtnByIndex(int index);
    }

    private MenuView view;
    private Timer menuDraw;
    public MenuPresenter(MenuView view) {
        this.view = view;
    }

    public void initMe() {
        menuDraw = new Timer(100, e -> redrawScreen());
        menuDraw.start();
        MenuKeyListener();
    }

    private void redrawScreen() {
        view.reDraw();
    }

    private void MenuKeyListener() {
        Timer keyListener = new Timer(100, e -> {
            int i = App.getMenuPanel().getMenuID();
           if (i != view.getSelected()) {
               view.lightItUp(view.getBtnByIndex(i));
           }
           if (App.getMenuPanel().isPressed()) {
               App.getMenuPanel().setPressed(false);
               view.getBtnByIndex(i).doClick();
           }
        });
        keyListener.start();
    }
    // Methods

    public void newGameClicked() {
        App.getGamePanel().removeAll();
        App.getGamePanel().add(new Level(GameLevels.LEVEL_1));
        App.switchScreen(App.getMenuPanel(), App.getGamePanel());
    }

    public void loadGameClicked() {
        // todo: loadFromFile
    }

    public void saveGameClicked() {
        // todo: saveToFile
    }

    public void randomGameClicked() {
        // todo: generateRandomMap
    }

    public void exitClicked() {
        System.exit(0);
    }


    public void playBip() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Clip clip = null;
                try {
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("sound/btnHover.wav").getAbsoluteFile());
                    clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
                    e.printStackTrace();
                }
                assert clip != null;
                clip.start();
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                clip.stop();
                clip.close();
            }
        }).start();
    }

}
