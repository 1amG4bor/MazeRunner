package ui;

import presenter.MenuPresenter;
import ui.model.MenuPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Menu extends JLayeredPane implements MenuPresenter.MenuView {
    private MenuPresenter presenter;
    public boolean isMainMenu;
    // gfx components
    private JLabel background;
    private JLabel header;
    private BufferedImage title = null;
    private BufferedImage bgImage = null;
    private BufferedImage btn = null;
    private BufferedImage btnActive = null;
    private int highlighted = 0;
    private List<JButton> buttons;

    public Menu() {
        presenter = new MenuPresenter(this);
        presenter.initMe();
        setLayout(null);
        setOpaque(true);
        buttons = new ArrayList<>();
        buttons.add(new JButton("New Game"));
        buttons.add(new JButton("Load Game"));
        buttons.add(new JButton("Save Game"));
        buttons.add(new JButton("Practice"));
        buttons.add(new JButton("Exit!"));
        App.getMenuPanel().setMenuID(0);
        App.getMenuPanel().setLastID(4);
        addElements();
        setActions();
        setVisible(true);
    }

    // region Getters
    public JButton getBtnByIndex(int index) {
        return buttons.get(index);
    }

    @Override
    public void lightItUp(JButton btnLight) {
        buttons.get(highlighted).setIcon(new ImageIcon(btn));
        btnLight.setIcon(new ImageIcon(btnActive));
        highlighted = getBtnIndex(btnLight);
    }

    @Override
    public int getSelected() {
        return highlighted;
    }

    @Override
    public void reDraw() {
        repaint();
    }
    // endregion

    private int getBtnIndex(JButton btn) {
        switch (btn.getText()) {
            case "New Game":
                return  0;
            case "Load Game":
                return  1;
            case "Save Game":
                return  2;
            case "Practice":
                return  3;
            case "Exit!":
                return  4;
            default: return 0;
        }
    }

    private void addElements() {
        // header + background
        int w = 1600; // App.getInstance().getAppW();
        int h = 900;  // App.getInstance().getAppH();
        loadImages();
        background = new JLabel(new ImageIcon(bgImage));
        background.setBounds(0,0,w, h);
        header = new JLabel(new ImageIcon(title));
        int x = Math.floorDiv(w, 2) - 600;
        header.setBounds(x,50, 1200, 200);
        // menu items
        setButton(buttons.get(0), 300);
        setButton(buttons.get(1), 400);
        setButton(buttons.get(2), 500);
        setButton(buttons.get(3), 600);
        setButton(buttons.get(4), 750);
        buttons.get(1).setForeground(Color.darkGray);
        buttons.get(2).setForeground(Color.darkGray);
        buttons.get(3).setForeground(Color.darkGray);
        // add to the container
        add(background, JLayeredPane.DEFAULT_LAYER);
        add(header, JLayeredPane.MODAL_LAYER);
        add(buttons.get(0), JLayeredPane.MODAL_LAYER);
        add(buttons.get(1), JLayeredPane.MODAL_LAYER);
        add(buttons.get(2), JLayeredPane.MODAL_LAYER);
        add(buttons.get(3), JLayeredPane.MODAL_LAYER);
        add(buttons.get(4), JLayeredPane.MODAL_LAYER);
    }

    private void loadImages() {
        try {
            bgImage = ImageIO.read(new File("images/menu/bg.png"));
            title = ImageIO.read(new File("images/menu/title.png"));
            btn = ImageIO.read(new File("images/menu/btn.png"));
            btnActive = ImageIO.read(new File("images/menu/btnActive.png"));
        } catch (IOException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }

    private void setButton(JButton button, int y) {
        int w = 320;
        int x = Math.floorDiv((1600 - w), 2);
        button.setBounds(x, y, w, 100);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Aegean", Font.BOLD, 42));
        button.setForeground(Color.white);
        button.setIcon(new ImageIcon(btn));
        button.setHorizontalTextPosition(JButton.CENTER);
        button.setVerticalTextPosition(JButton.CENTER);
        button.addMouseListener(myMouseListener(button));
        button.setFocusable(false);
        button.setFocusPainted(false);
    }

    private void setActions() {
        buttons.get(0).addActionListener(e -> { presenter.newGameClicked(); });
        buttons.get(1).addActionListener(e -> { presenter.loadGameClicked(); });
        buttons.get(2).addActionListener(e -> { presenter.saveGameClicked(); });
        buttons.get(3).addActionListener(e -> { presenter.randomGameClicked(); });
        buttons.get(4).addActionListener(e -> { presenter.exitClicked(); });

    }

    private MouseListener myMouseListener(JButton myBtn) {
        MouseListener myListener = new MouseListener() {
            @Override public void mouseClicked(MouseEvent e) { }
            @Override public void mousePressed(MouseEvent e) { }
            @Override public void mouseReleased(MouseEvent e) { }
            @Override public void mouseExited(MouseEvent e) { }

            @Override
            public void mouseEntered(MouseEvent e) {
                lightItUp(myBtn);
                highlighted = getBtnIndex(myBtn);
                App.getMenuPanel().setMenuID(highlighted);
                presenter.playBip();
            }
        };
        return myListener;
    }
}
