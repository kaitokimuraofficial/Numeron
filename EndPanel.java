import javax.swing.*;
import java.awt.*;

public class EndPanel extends JPanel {
    private JLabel endLabel;
    private JButton endButton;

    public EndPanel() {
        setLayout(new BorderLayout());
        setSize(500, 350);

        endLabel = new JLabel("You ");
        endButton = new JButton("Close Window");

        add(endLabel);
        add(endButton);
    }

    /* ^^^^^^^^^^^^各種メソッドまとめ^^^^^^^^^^^^ */


    /* -------------getメソッド------------- */
    public JLabel getEndLabel() {
        return endLabel;
    }

    public JButton getEndButton() {
        return endButton;
    }

    /* -------------setメソッド------------- */
    public void setEndLabel(String str) {
        endLabel.setText(str);
    }
}