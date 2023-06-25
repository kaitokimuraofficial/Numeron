import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class StartPanel extends JPanel {
    private JPanel titlePart;
    private JPanel rulePart;
    private JPanel startPart;

    private JLabel titleLabel;

    private RulePanel rulePanel;

    private JButton ruleButton;
    private JButton inputMyNumberButton;

    private JTextField myNumberField;


    public StartPanel() {
        setLayout(new BorderLayout());
        setSize(500, 450);
        titlePart = new JPanel();
        rulePart = new JPanel();
        startPart = new JPanel();
        titlePart.setPreferredSize(new Dimension(500, 150));
        rulePart.setPreferredSize(new Dimension(500, 150));
        startPart.setPreferredSize(new Dimension(500, 150));
        titleLabel = new JLabel("");

        titlePart.setBackground(Color.red);
        rulePart.setBackground(Color.pink);

        ruleButton = new JButton("RULE");
        inputMyNumberButton = new JButton("GAME START");

        myNumberField = new JTextField();
        myNumberField.setPreferredSize(new Dimension(100, 20));
        
        titlePart.add(titleLabel);
        rulePart.add(ruleButton);
        startPart.add(myNumberField);
        startPart.add(inputMyNumberButton);
        add(titlePart, BorderLayout.NORTH);
        add(rulePart, BorderLayout.CENTER);
        add(startPart, BorderLayout.SOUTH);

        setBackground(Color.pink);
    }

    public void executeRulePanel() {
        rulePanel = new RulePanel();
        rulePanel.setVisible(true);
        requestFocusInWindow();
    }

    /* ^^^^^^^^^^^^各種メソッドまとめ^^^^^^^^^^^^ */


    /* -------------getメソッド------------- */
    public JLabel getTitleLabel() {
        return titleLabel;
    }

    public JButton getRuleButton() {
        return ruleButton;
    }

    public JButton getinputMyNumberButton() {
        return inputMyNumberButton;
    }

    public JTextField getMyNumberField() {
        return myNumberField;
    }

    /* -------------setメソッド------------- */
    public void setMyNumberField(String str) {
        myNumberField.setText(str);
    }
}

class RulePanel extends JDialog {
    Container cp;
    JTextArea ta;
    Font f;
    JScrollPane scroll;

    RulePanel() {
        setTitle("RULE OF NUMERON");
        cp = getContentPane();
        cp.setLayout(new FlowLayout(FlowLayout.CENTER));
        cp.setBackground(new Color(220, 255, 220));
        f = new Font("MS 明朝", Font.PLAIN, 20);
        setSize(1000, 320);
        ta = new JTextArea(5, 50);
        ta.setFont(f);
        ta.setEditable(false);
        ta.setLineWrap(true);
        ta.setText("それぞれのプレイヤーが、\n");
        ta.append("0～9 までの数字が書かれたカードのうち3つを使って\n");
        ta.append("3 桁の番号を作成する0から始めても良い。\n");
        ta.append("ただし同じ数字を2つ以上使用した番号は作れない。\n");
        ta.append("先攻のプレイヤーは相手が作成したと番号を予想する");
        
        scroll = new JScrollPane(ta);
        cp.add(scroll);
        addWindowListener(new WinEnd());
    }
    
    class WinEnd extends WindowAdapter
	{
		public void windowClosing(WindowEvent e) {
			setVisible(false);
		}
	}
}