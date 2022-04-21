package minesweeper.minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Input extends JFrame implements ActionListener {

    private Minesweeper minesweeper = new Minesweeper();
    private Option option = new Option();

    private int size;  // size given
    private JPanel panel;
    private JLabel label;
    private JTextField text;

    public Input() {
        setSize(400, 100);
        setTitle("Input");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panel = new JPanel();

        label = new JLabel("Enter grid size : ");
        panel.add(label);

        text = new JTextField(30);
        text.addActionListener(this);
        panel.add(text);

        setContentPane(panel);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        Object eventSource = evt.getSource();
        JTextField text = (JTextField) eventSource;
        String input = "0";
        int size = 0;

        while (true) {
            try {
                input = text.getText();
                size = Integer.parseInt(input);
                if (size <= 6) {
                    JOptionPane.showMessageDialog(null,
                            "Grid Size Must Be Greater Than 6", "Invalid Input!",
                            JOptionPane.ERROR_MESSAGE);
                    text.setText("");
                    break;
                }
                if (size > 15) {
                    JOptionPane.showMessageDialog(null,
                            "Grid Size Must Be Lower Than 6", "Invalid Input!",
                            JOptionPane.ERROR_MESSAGE);
                    text.setText("");
                    break;
                } else {
                    dispose();
                    option.optionDifficulty(size);
                }
                break;
            } catch (NumberFormatException | HeadlessException e) {
                JOptionPane.showMessageDialog(null,
                        "Enter a valid number!", "Invalid Input",
                        JOptionPane.ERROR_MESSAGE);
                text.setText("");
                break;
            }
        }
    }
}