import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel{
    private JPanel memoPart;
    private JPanel inputPart;
    private JPanel resultPart;
    
    private JButton decideExpectNumberButton;

    private JLabel mainLabel;

    private JTextField expectedNumber;

    private Font f;
    private JTextArea resultArea;
    

    public MainPanel() {
        setLayout(new BorderLayout());
        setSize(500, 350);
        memoPart = new JPanel();
        inputPart = new JPanel();
        resultPart = new JPanel();

        memoPart.setPreferredSize(new Dimension(500, 100));
        inputPart.setPreferredSize(new Dimension(500, 100));
        resultPart.setPreferredSize(new Dimension(500, 100));

        memoPart.setBackground(Color.PINK);
        inputPart.setBackground(Color.YELLOW);
        resultPart.setBackground(Color.green);

        resultArea = new JTextArea(5, 50);
        resultArea.setFont(f);
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setText("");

        mainLabel = new JLabel("");

        memoPart.add(resultArea);

        expectedNumber = new JTextField();
        expectedNumber.setPreferredSize(new Dimension(100, 20));

        decideExpectNumberButton = new JButton("JUDGE");

        inputPart.add(expectedNumber);
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
    
    public JTextField getExpectedNumber() {
        return expectedNumber;
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
