import javax.swing.*;
import java.awt.*;

public class EndPanel extends JPanel {
    private JPanel endPart;
    private JLabel endLabel;
    private JButton endButton;
    private JButton repeatButton;

    public EndPanel() {
        setLayout(new GridLayout(1,3));
        setSize(500, 450);

        endPart = new JPanel();

        endLabel = new JLabel("You ");
        endButton = new JButton("Close Window");
        repeatButton = new JButton("REPEAT");
        endButton.setPreferredSize(new Dimension(200, 50));
        repeatButton.setPreferredSize(new Dimension(200, 50));

        endPart.add(endLabel);
        endPart.add(endButton);
        endPart.add(repeatButton);
        
        add(endPart);
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

    /* -------------setメソッド------------- */
    public void setEndLabel(String str) {
        endLabel.setText(str);
    }
}