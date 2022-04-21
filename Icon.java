package minesweeper.minesweeper;

import javax.imageio.ImageIO;
import java.awt.*;

public class Icon {

    // ENCAPCULATION

    private Image smiley;
    private Image newSmiley;
    private Image flag;
    private Image newFlag;
    private Image mine;
    private Image newMine;
    private Image dead;
    private Image newDead;

    public Image getSmiley() {
        try {
            smiley = ImageIO.read(getClass().getResource("/Smiley.png"));
            newSmiley = smiley.getScaledInstance(GameFrame.MAGIC_SIZE, GameFrame.MAGIC_SIZE, java.awt.Image.SCALE_SMOOTH);
        }
        catch (Exception e){
        }
        return newSmiley;
    }

    public Image getDead() {
        try {
            dead = ImageIO.read(getClass().getResource("/dead.png"));
            newDead = dead.getScaledInstance(GameFrame.MAGIC_SIZE, GameFrame.MAGIC_SIZE, java.awt.Image.SCALE_SMOOTH);
        }
        catch (Exception e){
        }
        return newDead;
    }

    public Image getFlag() {
        try {
            flag = ImageIO.read(getClass().getResource("/flag.png"));
            newFlag = flag.getScaledInstance(GameFrame.MAGIC_SIZE, GameFrame.MAGIC_SIZE, java.awt.Image.SCALE_SMOOTH);
        }
        catch (Exception e){
        }
        return newFlag;
    }

    public Image getMine() {
        try {
            mine = ImageIO.read(getClass().getResource("/mine.png"));
            newMine = mine.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
        }
        catch (Exception e) {
        }
        return newMine;
    }

}
