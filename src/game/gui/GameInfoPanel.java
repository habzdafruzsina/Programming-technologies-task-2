package game.gui;

import game.logic.GameLogic;

import javax.swing.*;
import java.awt.*;

public class GameInfoPanel extends JPanel {

    private GameLogic logic;
    private static final JLabel ROUND_LABEL = new JLabel("A soron következő: ");
    private static final JLabel playerLabel = new JLabel("");

    public GameInfoPanel(GameLogic logic){
        this.logic = logic;
        updateLabelText();
        Font textFont = new Font("Arial", Font.BOLD,20);
        playerLabel.setFont(textFont);

        add(ROUND_LABEL);
        add(playerLabel);
    }

    /**
     * changes the label text according to the actualPlayer of the logic
     */
    public void updateLabelText(){
        playerLabel.setText(String.valueOf(logic.getActualPlayer()));
    }
}
