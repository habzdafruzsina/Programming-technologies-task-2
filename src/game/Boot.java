package game;
import game.gui.GameFrame;
import game.logic.Size;
import game.logic.GameLogic;

public class Boot {

    public static void main(String args[]){

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GameFrame(new GameLogic(Size.SMALL)).setVisible(true);
            }
        });
    }
}
