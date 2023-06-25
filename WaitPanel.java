import javax.swing.*;
import java.awt.*;


public class WaitPanel extends JPanel {
    private JPanel labelPart;
    private JPanel buttonPart;

    private JLabel waitLabel;
    private JButton endButton;
    private JButton acceptButton;

    public WaitPanel() {
        setLayout(new BorderLayout());
        setSize(500, 450);
        labelPart = new JPanel();
        buttonPart = new JPanel();
        labelPart.setPreferredSize(new Dimension(500, 200));
        buttonPart.setPreferredSize(new Dimension(500, 250));

        waitLabel = new JLabel("");

        endButton = new JButton("END");
        acceptButton = new JButton("ACCEPT");

        labelPart.add(waitLabel);
        buttonPart.add(endButton);
        buttonPart.add(acceptButton);

        add(labelPart, BorderLayout.NORTH);
        add(buttonPart, BorderLayout.CENTER);
    }

    /* ^^^^^^^^^^^^各種メソッドまとめ^^^^^^^^^^^^ */


    /* -------------getメソッド------------- */
    public JLabel getWaitLabel() {
        return waitLabel;
    }

    public JButton getEndButton() {
        return endButton;
    }

    public JButton getAcceptButton() {
        return acceptButton;
    }

    /* -------------setメソッド------------- */
    public void setWaitLabel(String str) {
        waitLabel.setText(str);
    }
}
