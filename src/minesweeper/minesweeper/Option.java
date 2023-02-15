package minesweeper.minesweeper;

import javax.swing.*;

public class Option extends OptionAbstract {

    @Override
    public void optionDifficulty(int size) {
        levelChoice = 1;
        level = new Object[] {"Easy", "Moderate", "Hard"};
        levelChoice = JOptionPane.showOptionDialog(null,
                "What's your difficulty level ?", "Difficulty",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                level, level[1]);

        if (levelChoice == -1) {
            System.exit(0);
        }
        game = new GameFrame(size, levelChoice);
    }

    @Override
    public void optionRestart() {
        restartChoice = 1;
        restart = new Object[] {"Play Again", "EXIT"};
        restartChoice = JOptionPane.showOptionDialog(null,
                "Do You Want To Play Again?", "New Game",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null,
                restart, restart[0]);

        if(restartChoice == 1) {
            System.exit(0);
        }
        minesweeper = new Minesweeper();
        minesweeper.start();
    }
}

