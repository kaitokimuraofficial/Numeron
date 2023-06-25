import javax.swing.*;
import java.awt.*;


public class RepeatPanel extends JPanel {
    private JPanel labelPart;
    private JPanel inputPart;
    private JPanel cancelPart;

    private JLabel repeatLabel;
    public JTextField nextSize;
    private JButton inputNextSizeButton;
    private JButton cancelButton;

    public RepeatPanel() {
        setLayout(new BorderLayout());
        setSize(500, 450);
        labelPart = new JPanel();
        inputPart = new JPanel();
        cancelPart = new JPanel();
        labelPart.setPreferredSize(new Dimension(500, 150));
        inputPart.setPreferredSize(new Dimension(500, 150));
        cancelPart.setPreferredSize(new Dimension(500, 150));

        repeatLabel = new JLabel("Enter new digit length");

        nextSize = new JTextField();
        nextSize.setPreferredSize(new Dimension(100, 20));

        inputNextSizeButton = new JButton("DECIDE NEW LENGTH");
        cancelButton = new JButton("Back");

        labelPart.add(repeatLabel);
        inputPart.add(nextSize);
        inputPart.add(inputNextSizeButton);
        cancelPart.add(cancelButton);

        add(labelPart, BorderLayout.NORTH);
        add(inputPart, BorderLayout.CENTER);
        add(cancelPart, BorderLayout.SOUTH);
    }

    /* ^^^^^^^^^^^^各種メソッドまとめ^^^^^^^^^^^^ */

    /* -------------getメソッド------------- */

    public JLabel getRepeatLabel() {
        return repeatLabel;
    }

    public String getNextSize() {
        return nextSize.getText();
    }

    public JButton getInputNextSizeButton() {
        return inputNextSizeButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    /* -------------setメソッド------------- */
    public void setRepeatLabel(String str) {
        repeatLabel.setText(str);
    }

}
