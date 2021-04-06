package game.gui;

import game.logic.Size;
import game.logic.GameLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameFrame extends JFrame {

    private final GameLogic logic;
    private final GameInfoPanel gameInfoPanel;
    private final GameMenuBar gameMenuBar;
    private GameBoard gameBoard;

    public GameFrame(final GameLogic gameLogic){
        this.logic = gameLogic;
        setFrameProps();

        gameMenuBar = new GameMenuBar(this);
        setJMenuBar(gameMenuBar);
        gameInfoPanel = new GameInfoPanel(logic);
        getContentPane().add(gameInfoPanel, BorderLayout.NORTH);
        gameBoard = new GameBoard(Size.SMALL, logic, gameInfoPanel);
        getContentPane().add(gameBoard, BorderLayout.CENTER);

        pack();
    }

    /**
     * sets the title, layout, location, close operation of the frame
     */
    private void setFrameProps(){
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                showExitConfirmDialog();
            }
        });
        setLocationRelativeTo(null);
        setTitle("TicTacToe Game");
        setLayout(new BorderLayout());
    }

    /**
     * starts new game in logic and in gameBoard
     * @param size
     */
    public void newGame(int size){
        logic.newGame(size);
        gameBoard.newGame(size);
        pack();
    }

    /**
     * exit dialog with yes-no option
     */
    private void showExitConfirmDialog(){
        int answer = JOptionPane.showConfirmDialog(this, "Valóban ki szeretne lépni?",
                "Kilépés", JOptionPane.YES_NO_OPTION);
        if (answer == JOptionPane.YES_OPTION) {
            this.dispose();
        }
    }
}
