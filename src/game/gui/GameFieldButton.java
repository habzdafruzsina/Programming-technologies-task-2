package game.gui;

import javax.swing.*;

public class GameFieldButton extends JButton {

    private final int row;
    private final int column;

    public GameFieldButton(int i, int j){
        this.row = i;
        this.column = j;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
