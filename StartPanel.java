import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class StartPanel extends JPanel {
    private JPanel titlePart;
    private JPanel rulePart;
    private JPanel startPart;

    private RulePanel rp;

    private JButton ruleButton;
    private JButton inputMyNumberButton;

    private JTextField myNumber;


    public StartPanel() {
        setLayout(new BorderLayout());
        setSize(500, 350);
        titlePart = new JPanel();
        rulePart = new JPanel();
        startPart = new JPanel();
        titlePart.setPreferredSize(new Dimension(500, 100));
        rulePart.setPreferredSize(new Dimension(500, 100));
        startPart.setPreferredSize(new Dimension(500, 100));

        titlePart.setBackground(Color.PINK);
        rulePart.setBackground(Color.YELLOW);

        ruleButton = new JButton("RULE");
        inputMyNumberButton = new JButton("GAME START");

        myNumber = new JTextField();
        myNumber.setPreferredSize(new Dimension(100, 20));

        rulePart.add(ruleButton);
        startPart.add(myNumber);
        startPart.add(inputMyNumberButton);
        add(titlePart, BorderLayout.NORTH);
        add(rulePart, BorderLayout.CENTER);
        add(startPart, BorderLayout.SOUTH);

        setBackground(Color.RED);
    }

    public void executeRulePanel() {
        rp = new RulePanel();
        rp.setVisible(true);
        requestFocusInWindow();
    }

    /* ^^^^^^^^^^^^各種メソッドまとめ^^^^^^^^^^^^ */


    /* -------------getメソッド------------- */
    public JButton getRuleButton() {
        return ruleButton;
    }

    public JButton getinputMyNumberButton() {
        return inputMyNumberButton;
    }

    public JTextField getMyNumber() {
        return myNumber;
    }

    /* -------------setメソッド------------- */
    public void setMyNumber(String str) {
        myNumber.setText(str);
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
        setSize(550, 160);
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