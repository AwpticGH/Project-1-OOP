package minesweeper.minesweeper;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Random;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameFrame extends JFrame implements ActionListener {

    public JButton[][] buttons;  // The Grid buttons
    public JPanel mainPanel;
    public JPanel panel1;  // Top panel containing labels and a smile button
    public JPanel panel2;  // Bottom panel containing the grid of buttons
    public JLabel flagsLabel;  // Number of flags remaining to be used
    public JButton smileButton;  // The smile button ;-)
    public JLabel timeLabel;  // Label showing time elapsed

    public int noOfMines = 0;  // The no. of mines in the field
    public int[][] mineLand;  // 2-D array containing info for each block
    public boolean[][] revealed;  // Whether the button has been clicked
    public int noOfRevealed;  // How many of them?
    public boolean[][] flagged;  // Or the got flagged?

    private boolean smiling;

    public static final int MAGIC_SIZE = 30;


    public GameFrame(int size, int levelChoice) {
        Icon icon = new Icon();
        MyMouseListener myMouseListener = new MyMouseListener(this);

        noOfMines = size*(1 + levelChoice/2);
        this.setSize(size*MAGIC_SIZE, size*MAGIC_SIZE + 50);
        this.setTitle("Minesweeper");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        mainPanel = new JPanel();
        panel1 = new JPanel();
        panel2 = new JPanel();

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        BoxLayout g1 = new BoxLayout(panel1, BoxLayout.X_AXIS);
        panel1.setLayout(g1);

        JLabel jLabel1 = new JLabel(" Flags = ");
        jLabel1.setAlignmentX(Component.LEFT_ALIGNMENT);
        jLabel1.setHorizontalAlignment(JLabel.LEFT);
        flagsLabel = new JLabel(""+ this.noOfMines);

        smiling = true;
        smileButton = new JButton(new ImageIcon(icon.getSmiley()));
        smileButton.setPreferredSize(new Dimension(MAGIC_SIZE, MAGIC_SIZE));
        smileButton.setMaximumSize(new Dimension(MAGIC_SIZE, MAGIC_SIZE));
        smileButton.setBorderPainted(true);
        smileButton.setName("smileButton");
        smileButton.addActionListener(this);

        JLabel jLabel2 = new JLabel(" Time :");
        timeLabel = new JLabel("0");
        timeLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        timeLabel.setHorizontalAlignment(JLabel.RIGHT);

        panel1.add(jLabel1);
        panel1.add(flagsLabel);
        panel1.add(Box.createRigidArea(new Dimension((size-1)*15 - 80,50)));
        panel1.add(smileButton, BorderLayout.PAGE_START);
        panel1.add(Box.createRigidArea(new Dimension((size-1)*15 - 85,50)));
        panel1.add(jLabel2);
        panel1.add(timeLabel);

        GridLayout g2 = new GridLayout(size, size);
        panel2.setLayout(g2);

        buttons = new JButton[size][size];

        for (int i=0; i<size; i++) {
            for (int j=0; j<size ; j++ ) {
                buttons[i][j] = new JButton();
                buttons[i][j].setPreferredSize(new Dimension(12, 12));
                buttons[i][j].setBorder(new LineBorder(Color.BLACK));
                buttons[i][j].setBorderPainted(true);
                buttons[i][j].setName(i + " " + j);
                buttons[i][j].addActionListener(this);
                buttons[i][j].addMouseListener(myMouseListener);
                panel2.add(buttons[i][j]);
            }
        }

        // Both panels done
        mainPanel.add(panel1);
        mainPanel.add(panel2);
        setContentPane(mainPanel);
        setVisible(true);

        this.noOfRevealed = 0;

        this.revealed = new boolean[size][size];
        flagged = new boolean[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                revealed[i][j] = false;
                flagged[i][j] = false;
            }
        }

        // Algorithms
        setMines(size);

        // The timer
        TimeThread timer = new TimeThread(this);
        timer.start();
    }

    private void setMines(int size) {
        Random rand = new Random();

        mineLand = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                mineLand[i][j] = 0;
            }
        }

        int count = 0;
        int xPoint;
        int yPoint;
        while(count < noOfMines) {
            xPoint = rand.nextInt(size);
            yPoint = rand.nextInt(size);
            if (mineLand[xPoint][yPoint]!=-1) {
                mineLand[xPoint][yPoint]=-1;  // -1 represents bomb
                count++;
            }
        }

        // Fill boxes adjacent to mines with numbers
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (mineLand[i][j]==-1) {
                    for (int k = -1; k <= 1 ; k++) {
                        for (int l = -1; l <= 1; l++) {
                            // In boundary cases
                            try {
                                if (mineLand[i+k][j+l]!=-1) {
                                    mineLand[i+k][j+l] += 1;
                                }
                            }
                            catch (Exception e) {
                                // Do nothing
                            }
                        }
                    }
                }
            }
        }
    }

    // Increase timer every second
    public void timer() {
        String[] time = this.timeLabel.getText().split(" ");
        int time0 = Integer.parseInt(time[0]);
        time0++;
        this.timeLabel.setText(Integer.toString(time0) + " s");
    }

    // Change icon upon clicking smile Button
    public void changeSmile() {
        Icon icon = new Icon();

        if (smiling) {
            smiling=false;
            smileButton.setIcon(new ImageIcon(icon.getDead()));
        } else {
            smiling=true;
            smileButton.setIcon(new ImageIcon(icon.getSmiley()));
        }
    }

    // If a block is right clicked
    public void buttonRightClicked(int x, int y) {
        Icon icon = new Icon();

        if (!revealed[x][y]) {
            if (flagged[x][y]) {
                buttons[x][y].setIcon(null);
                flagged[x][y] = false;
                int old = Integer.parseInt(this.flagsLabel.getText());
                ++old;
                this.flagsLabel.setText(""+old);
            }
            else {
                if (Integer.parseInt(this.flagsLabel.getText())>0) {
                    buttons[x][y].setIcon(new ImageIcon(icon.getFlag()));
                    flagged[x][y] = true;
                    int old = Integer.parseInt(this.flagsLabel.getText());
                    --old;
                    this.flagsLabel.setText(""+old);
                }
            }
        }
    }


    private boolean gameWon() {
        // noOfRevealed + noOfMines must be equal to the total no. of boxes
        return (this.noOfRevealed) ==
                (Math.pow(this.mineLand.length, 2) - this.noOfMines);
    }

    // When a block is clicked
    public void buttonClicked(int x, int y) {
        Icon icon = new Icon();

        if(!revealed[x][y] && !flagged[x][y]) {
            revealed[x][y] = true;

            Option option = new Option();
            switch (mineLand[x][y]) {
                case -1:
                    try {
                        buttons[x][y].setIcon(new ImageIcon(icon.getMine()));
                    } catch (Exception e1) {
                    }
                    buttons[x][y].setBackground(Color.RED);
                    try {
                        smileButton.setIcon(new ImageIcon(icon.getDead()));
                    } catch (Exception e2) {
                    }

                    JOptionPane.showMessageDialog(this, "Game Over !", null,
                            JOptionPane.ERROR_MESSAGE);


                    dispose();
                    option.optionRestart();

                    break;

                case 0:
                    buttons[x][y].setBackground(Color.lightGray);
                    ++this.noOfRevealed;

                    if (gameWon()) {
                        JOptionPane.showMessageDialog(rootPane,
                                "Congratulations! You've Won");

                        dispose();
                        option.optionRestart();
                    } // Winning condition

                    // Else simply recurse around
                    for (int i = -1; i <= 1; i++) {
                        for (int j = -1; j <= 1; j++) {
                            try {
                                buttonClicked(x + i, y + j);
                            }
                            catch (Exception e3) {
                                // Do nothing
                            }
                        }
                    }

                    break;

                default:
                    buttons[x][y].setText(Integer.toString(mineLand[x][y]));
                    buttons[x][y].setBackground(Color.LIGHT_GRAY);
                    ++this.noOfRevealed;
                    if (gameWon()) {
                        JOptionPane.showMessageDialog(rootPane, "You Won !");

                        dispose();
                        option.optionRestart();
                    }

                    break;
            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object eventSource = e.getSource();
        JButton clickedButton = (JButton) eventSource;
        String name = clickedButton.getName();
        if (name.equals("smileButton")) {
            changeSmile();
        }
        else {
            String[] xy = clickedButton.getName().split(" ", 2);
            int x = Integer.parseInt(xy[0]);
            int y = Integer.parseInt(xy[1]);
            buttonClicked(x, y);
        }
    }

}

class MyMouseListener implements MouseListener {
    GameFrame gameFrame;

    public MyMouseListener(GameFrame gameFrame){
        this.gameFrame = gameFrame;
    }

    public void mouseExited(MouseEvent arg0){
    }
    public void mouseEntered(MouseEvent arg0){
    }
    public void mousePressed(MouseEvent arg0){
    }
    public void mouseClicked(MouseEvent arg0){
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        if(SwingUtilities.isRightMouseButton(arg0)){
            Object eventSource = arg0.getSource();
            JButton clickedButton = (JButton) eventSource;
            String[] xy = clickedButton.getName().split(" ", 2);
            int x = Integer.parseInt(xy[0]);
            int y = Integer.parseInt(xy[1]);
            gameFrame.buttonRightClicked(x, y);
        }
    }
}

class TimeThread implements Runnable {
    private Thread time;
    private GameFrame gameFrame;

    public TimeThread(GameFrame gameFrame){
        this.gameFrame = gameFrame;
    }

    public void run() {
        while(true) {
            try {
                Thread.sleep(1000);
                gameFrame.timer();
            }
            catch (InterruptedException e) {
                System.exit(0);
            }
        }
    }

    public void start() {
        if (time == null) {
            time = new Thread(this);
            time.start();
        }
    }
}