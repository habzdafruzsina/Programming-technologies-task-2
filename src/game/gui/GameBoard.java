package game.gui;

import game.logic.Player;
import game.logic.GameLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameBoard extends JPanel {

    private final GameInfoPanel infoPanel;
    private final GameLogic logic;
    private final FieldButtonActionListener fieldButtonActionListener = new FieldButtonActionListener();

    public GameBoard(int size, GameLogic logic, GameInfoPanel infoPanel){
        this.logic = logic;
        this.infoPanel = infoPanel;
        setUpBoard(size);
    }

    /**
     * starts new game
     * @param size
     */
    public void newGame(int size){
        removeAll();
        setUpBoard(size);
        infoPanel.updateLabelText();
    }

    /**
     * adds "empty" buttons to the board
     * @param size
     */
    private void setUpBoard(int size) {
        setLayout(new GridLayout(size, size));
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                addButton(i, j);
            }
        }
    }

    /**
     * creates GameFieldButtons, and adds it to the board
     * @param i row
     * @param j column
     */
    private void addButton(int i, int j){
        final GameFieldButton button = new GameFieldButton(i, j);
        button.addActionListener(fieldButtonActionListener);
        button.setPreferredSize(new Dimension(45, 45));
        add(button);
    }

    /**
     * changes button texts according to the board signs of the logic
     */
    private void refreshButtonTexts(){
        for(Component component : getComponents()){
            GameFieldButton button = (GameFieldButton) component;
            int row = button.getRow();
            int column = button.getColumn();
            if (logic.getBoardSign(row, column) != Player.NOBODY){
                button.setText(String.valueOf(logic.getBoardSign(row, column)));
            } else {
                button.setText("");
            }
        }
    }

    class FieldButtonActionListener implements ActionListener {

        /**
         * imitates a step, and looks for the winner
         * @param e actionevent, which is used to get the button which was clicked
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            GameFieldButton button = (GameFieldButton) e.getSource();
            String winner = logic.step(button.getRow(), button.getColumn());
            refreshButtonTexts();
            infoPanel.updateLabelText();
            if (!winner.equals("")){
                showGameOverMessage(winner);
            }
        }
    }

    /**
     * Shows a message of the winner, and then calls logic.endGame() which starts new game
     * @param winner
     */
    private void showGameOverMessage(String winner) {
        JOptionPane.showMessageDialog(this, "A nyertes: " + winner);
        logic.endGame();
        refreshButtonTexts();
    }

}
