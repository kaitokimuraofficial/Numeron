import javax.swing.*;
import java.awt.*;

public class EndPanel extends JPanel {
    private JPanel labelPart;
    private JPanel buttonPart;
    private JLabel endLabel;
    private JButton endButton;
    private JButton repeatButton;

    public EndPanel() {
        setLayout(new BorderLayout());
        setSize(500, 450);
        labelPart = new JPanel();
        buttonPart = new JPanel();
        labelPart.setPreferredSize(new Dimension(500, 100));
        buttonPart.setPreferredSize(new Dimension(500, 350));

        labelPart.setBackground(Color.WHITE);
        buttonPart.setBackground(Color.PINK);        

        endLabel = new JLabel("You ");
        endButton = new JButton("End game");
        repeatButton = new JButton("REPEAT");
        endButton.setPreferredSize(new Dimension(200, 50));
        repeatButton.setPreferredSize(new Dimension(200, 50));

        endLabel.setPreferredSize(new Dimension(500, 100));
        endLabel.setHorizontalAlignment(JLabel.CENTER);
        endLabel.setVerticalAlignment(JLabel.CENTER);

        labelPart.add(endLabel);
        buttonPart.add(endButton);
        buttonPart.add(repeatButton);

        add(labelPart, BorderLayout.NORTH);
        add(buttonPart, BorderLayout.CENTER);
    }

    /* ^^^^^^^^^^^^各種メソッドまとめ^^^^^^^^^^^^ */


    /* -------------getメソッド------------- */
    public JLabel getEndLabel() {
        return endLabel;
    }

    public JButton getEndButton() {
        return endButton;
    }

    public JButton getRepeatButton() {
        return repeatButton;
    }
}