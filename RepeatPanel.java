import javax.swing.*;
import java.awt.*;

public class RepeatPanel extends JPanel {
    private JPanel labelPart;
    private JPanel inputPart;
    private JPanel backPart;

    private JLabel repeatLabel;
    public JTextField nextDigit;
    private JButton inputNextDigitButton;
    private JButton backButton;

    public RepeatPanel() {
        setLayout(new BorderLayout());
        setSize(500, 450);
        labelPart = new JPanel();
        inputPart = new JPanel();
        backPart = new JPanel();
        labelPart.setPreferredSize(new Dimension(500, 100));
        inputPart.setPreferredSize(new Dimension(500, 250));
        backPart.setPreferredSize(new Dimension(500, 150));

        repeatLabel = new JLabel("Enter new digit length");
        repeatLabel.setFont(new Font("Default", Font.PLAIN, 30));
        repeatLabel.setPreferredSize(new Dimension(500, 100));
        repeatLabel.setHorizontalAlignment(JLabel.CENTER);
        repeatLabel.setVerticalAlignment(JLabel.CENTER);

        labelPart.setBackground(Color.WHITE);
        inputPart.setBackground(Color.PINK);
        backPart.setBackground(Color.PINK);

        nextDigit = new JTextField();
        nextDigit.setPreferredSize(new Dimension(100, 20));

        inputNextDigitButton = new JButton("Decide new digits");
        backButton = new JButton("Back");

        labelPart.add(repeatLabel);
        inputPart.add(nextDigit);
        inputPart.add(inputNextDigitButton);
        backPart.add(backButton);

        add(labelPart, BorderLayout.NORTH);
        add(inputPart, BorderLayout.CENTER);
        add(backPart, BorderLayout.SOUTH);
    }

    /* ^^^^^^^^^^^^各種メソッドまとめ^^^^^^^^^^^^ */

    /* -------------getメソッド------------- */

    public JLabel getRepeatLabel() {
        return repeatLabel;
    }

    public JTextField getNextDigit() {
        return nextDigit;
    }

    public JButton getInputNextDigitButton() {
        return inputNextDigitButton;
    }

    public JButton getBackButton() {
        return backButton;
    }

}
