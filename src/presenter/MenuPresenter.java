package presenter;

import logic.GameLevels;
import ui.App;
import ui.Level;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class MenuPresenter {
    public interface MenuView {
        void reDraw();
        void lightItUp(JButton btn);
        JButton getPrevBtn();
        JButton getNextBtn();
    }

    private MenuView view;
    private Timer menuDraw;

    public MenuPresenter(MenuView view) {
        this.view = view;
    }

    public void initMe() {
        menuDraw = new Timer(500, e -> redrawScreen());
        menuDraw.start();
    }

    private void redrawScreen() {
        view.reDraw();
    }
    // Methods

    public void newGameClicked() {
        App.getInstance().getGamePanel().add(new Level(GameLevels.LEVEL_1));
        App.getInstance().getGamePanel().show();
        App.getInstance().getMenuPanel().hide();
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

    public void highlightUp(int highlightState) {
        view.lightItUp(view.getPrevBtn());
        playBip();
    }

    public void highlightDown(int highlightState) {
        view.lightItUp(view.getNextBtn());
        playBip();
    }

    public void selectOption(JButton btn) {
        btn.doClick();
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
                clip = null;
            }
        }).start();
    }

}
