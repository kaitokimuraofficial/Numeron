import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainPanel extends JPanel{
    JPanel memoPart;
    JPanel inputPart;
    JPanel resultPart;

    Font f;
    JTextArea jta;
    JTextField expectNumber;
    JButton inputButton;

    JLabel jb;


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

        jta = new JTextArea(5, 50);
        jta.setFont(f);
        jta.setEditable(false);
        jta.setLineWrap(true);
        jta.setText("");

        jb = new JLabel("");

        memoPart.add(jta);


        expectNumber = new JTextField();
        expectNumber.setPreferredSize(new Dimension(100, 20));

        inputButton = new JButton("JUDGE");

        inputPart.add(expectNumber);
        inputPart.add(inputButton);

        add(memoPart, BorderLayout.NORTH);
        add(inputPart, BorderLayout.CENTER);
        add(resultPart, BorderLayout.SOUTH);
    }
    
    public String getExpectNumber() {
        return expectNumber.getText();
    }

    public JButton getInputButton() {
        return inputButton;
    }
}
