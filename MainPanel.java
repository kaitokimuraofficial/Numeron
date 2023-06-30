import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class MainPanel extends JPanel {
    private JScrollPane scrollResult;
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
        
        resultArea = new JTextArea();
        resultArea.setFont(f);
        resultArea.setText("");
        resultArea.setEditable(true);
        resultArea.setLineWrap(true);

        scrollResult = new JScrollPane(resultArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        inputPart = new JPanel();
        resultPart = new JPanel();

        scrollResult.setPreferredSize(new Dimension(500, 100));
        inputPart.setPreferredSize(new Dimension(500, 200));
        resultPart.setPreferredSize(new Dimension(500, 150));

        inputPart.setBackground(Color.pink);
        resultPart.setBackground(Color.pink);

        LineBorder border = new LineBorder(Color.BLUE, 2, true);
        scrollResult.setBorder(border);

        expectedNumberField = new JTextField();
        expectedNumberField.setPreferredSize(new Dimension(100, 20));

        decideExpectNumberButton = new JButton("JUDGE");
        mainLabel = new JLabel("");

        inputPart.add(expectedNumberField);
        inputPart.add(decideExpectNumberButton);
        resultPart.add(mainLabel);

        add(scrollResult, BorderLayout.NORTH);
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
}
