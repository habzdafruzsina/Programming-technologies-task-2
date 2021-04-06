package game.gui;

import game.logic.Size;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class GameMenuBar extends JMenuBar {

    private final GameFrame frame;
    private final JMenu newGame;
    private final JMenuItem small;
    private final JMenuItem medium;
    private final JMenuItem large;

    public GameMenuBar(GameFrame frame){
        this.frame = frame;
        newGame = new JMenu("New Game");

        small = new JMenuItem(String.valueOf(Size.SMALL) + " x " + String.valueOf(Size.SMALL));
        medium = new JMenuItem(String.valueOf(Size.MEDIUM) + " x " + String.valueOf(Size.MEDIUM));
        large = new JMenuItem(String.valueOf(Size.LARGE) + " x " + String.valueOf(Size.LARGE));

        small.setAccelerator(KeyStroke.getKeyStroke('S', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        medium.setAccelerator(KeyStroke.getKeyStroke('M', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        large.setAccelerator(KeyStroke.getKeyStroke('L', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

        small.addActionListener(newGameAction);
        medium.addActionListener(newGameAction);
        large.addActionListener(newGameAction);

        newGame.add(small);
        newGame.add(medium);
        newGame.add(large);

        add(newGame);
    }

    private final Action newGameAction = new AbstractAction() {

        /**
         * according to the clicked jmenu item, starts a new game with choosen size
         * @param ae
         */
        @Override
        public void actionPerformed(ActionEvent ae){
            if (ae.getSource() == small) {
                frame.newGame(Size.SMALL);
            } else if (ae.getSource() == medium){
                frame.newGame(Size.MEDIUM);
            } else if (ae.getSource() == large){
                frame.newGame(Size.LARGE);
            }
        }

    };
}
