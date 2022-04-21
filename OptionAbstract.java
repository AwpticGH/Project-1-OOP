package minesweeper.minesweeper;

public abstract class OptionAbstract {

    // ABSTRACTION

    Minesweeper minesweeper;
    GameFrame game;

    public int levelChoice;
    public int restartChoice;
    public Object[] level;
    public Object[] restart;

    public abstract void optionDifficulty(int size);

    public abstract void optionRestart();
}

