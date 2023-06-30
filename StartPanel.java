import javax.swing.*;
import javax.swing.border.LineBorder;
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

        titlePart.setBackground(Color.white);
        rulePart.setBackground(Color.pink);

        ruleButton = new JButton("RULE");
        inputMyNumberButton = new JButton("GAME START");

        myNumberField = new JTextField();
        myNumberField.setPreferredSize(new Dimension(100, 20));
        

        LineBorder lineBorder = new LineBorder(Color.red, 3, true);
        titlePart.setBorder(lineBorder);
        titlePart.add(titleLabel);
        rulePart.add(ruleButton);
        startPart.add(myNumberField);
        startPart.add(inputMyNumberButton);
        add(titlePart, BorderLayout.NORTH);
        add(rulePart, BorderLayout.CENTER);
        add(startPart, BorderLayout.SOUTH);

        setBackground(Color.red);
    }

    public void executeRulePanel() {
        rulePanel = new RulePanel(this);
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
}

class RulePanel extends JDialog {
    Container cp;
    JTextArea ta;
    Font f;
    StartPanel startpanel;
    JButton rulebutton;
    JScrollPane scroll;

    RulePanel(StartPanel startpanel) {
        this.startpanel = startpanel;
        rulebutton = startpanel.getRuleButton();
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
        ta.append("0～9 までの数字を使って\n");
        ta.append("予め決めた桁数の数字を作成する。尚、0から始めても良い。\n");
        ta.append("また、入力する数字は重複してはいけない。\n");
        ta.append("先攻のプレイヤーは相手が作成した数字を予想する。\n");
        ta.append("先に相手の番号を当てたプレイヤーの勝ちとなる。");
        scroll = new JScrollPane(ta);
        cp.add(scroll);
        addWindowListener(new WinEnd());
    }
    
    class WinEnd extends WindowAdapter
	{
        public void windowClosing(WindowEvent e) {
            rulebutton.setEnabled(true);
			setVisible(false);
		}
	}
}