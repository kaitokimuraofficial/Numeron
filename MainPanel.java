import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel{
    private JPanel memoPart;
    private JPanel inputPart;
    private JPanel resultPart;
    
    private JButton decideExpectNumberButton;

    private JLabel mainLabel;

    public JTextField expectedNumberField;

    private Font f;
    private JTextArea resultArea;
    

    public MainPanel() {
        setLayout(new BorderLayout());
        setSize(500, 450);
        memoPart = new JPanel();
        inputPart = new JPanel();
        resultPart = new JPanel();

        memoPart.setPreferredSize(new Dimension(500, 150));
        inputPart.setPreferredSize(new Dimension(500, 150));
        resultPart.setPreferredSize(new Dimension(500, 150));

        memoPart.setBackground(Color.PINK);
        inputPart.setBackground(Color.pink);
        resultPart.setBackground(Color.pink);

        resultArea = new JTextArea(5, 50);
        resultArea.setFont(f);
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setText("");

        mainLabel = new JLabel("");

        memoPart.add(resultArea);

        expectedNumberField = new JTextField();
        expectedNumberField.setPreferredSize(new Dimension(100, 20));

        decideExpectNumberButton = new JButton("JUDGE");

        resultPart.add(mainLabel);
        inputPart.add(expectedNumberField);
        inputPart.add(decideExpectNumberButton);

        add(memoPart, BorderLayout.NORTH);
        add(inputPart, BorderLayout.CENTER);
        add(resultPart, BorderLayout.SOUTH);
    }


    /* ^^^^^^^^^^^^各種メソッドまとめ^^^^^^^^^^^^ */


    /* -------------getメソッド------------- */
    public JButton getdecideExpectNumberButton() {
        return decideExpectNumberButton;
    }

    public JLabel getMainLabel() {
        return mainLabel;
    }
    
    public JTextField getExpectedNumberField() {
        return expectedNumberField;
    }

    public JTextArea getResultArea() {
        return resultArea;
    }

    /* -------------setメソッド------------- */
    public void setMainLabel(String str) {
        mainLabel.setText(str);
    }
    
    public void setResultArea(String str) {
        resultArea.setText(str);
    }
    
}
