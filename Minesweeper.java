package minesweeper.minesweeper;

public class Minesweeper {


    public void start() {
        new Input();
    }
    
    public static void main(String[] args) {
        Minesweeper minesweeper = new Minesweeper();
        minesweeper.start();
    }
}