import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame extends JFrame implements ActionListener {
    private Client client;

    private Container contentPane;
    private StartPanel sp;
    private LoadPanel lp;
    private MainPanel mp;
    private EndPanel ep;

    private JButton ruleButton; /* StartPanelでヌメロンのルールを表示するボタン */
    private JButton inputMyNumberButton; /* StartPanelで自分の数字を入力してServerに数字を送るボタン */
    private JButton decideExpectNumberButton; /* MainPanelで予想した相手の数字をServerに送信して判定してもらうためのボタン */
    private JButton endButton; /* EndPanelでclickされるとWindowが閉じる */

    private JLabel loadLabel; /* LoadPanelでLoading...を表示するJLabel */
    private JLabel mainLabel; /* MainPanelで結果を表示するJLabel */
    private JLabel endLabel; /* EndPanelのYou Win/Loseを表示するJLabel */

    private JTextField myNumberField; /* StartPanelで自分の数字を入力するJTextField */
    private JTextField expectedNumberField; /* MainPanelで相手の数字だと予想した数字を入力するJTField */

    private String myNumber; /* StartPanelで自分の数字を入力するJTextFieldの中のString */
    private String expectedNumber; /* MainPanelで相手の数字だと予想した数字を入力するJTFieldの中のString */

    private JTextArea resultArea; /* MainPanelでEatやBiteの結果をまとめるJTextArea */

    private Boolean buttonEnabled; /* Buttonが機能するかどうかのBoolean */

    private JPanel panel1;
    private JPanel panel2;

    private JLabel jl;


    public Frame(Client client) {
        this.client = client;
        contentPane = getContentPane();
        sp = new StartPanel();
        lp = new LoadPanel();
        mp = new MainPanel();
        ep = new EndPanel();

        initializeComponent();

        panel1 = new JPanel();
        panel2 = new JPanel();
        panel1.setPreferredSize(new Dimension(500, 50));
        panel2.setPreferredSize(new Dimension(500, 450));
        panel1.setBackground(Color.yellow);
        panel2.setBackground(Color.BLUE);

        jl = new JLabel("START");

        panel1.add(jl);
        panel2.add(sp, BorderLayout.CENTER);
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane.add(panel1, BorderLayout.NORTH);
        contentPane.add(panel2, BorderLayout.CENTER);

        pack();
        setVisible(true);
    }

    private void initializeComponent() {
        ruleButton = sp.getRuleButton();
        inputMyNumberButton = sp.getinputMyNumberButton();
        decideExpectNumberButton = mp.getdecideExpectNumberButton();
        endButton = ep.getEndButton();

        ruleButton.addActionListener(this);
        inputMyNumberButton.addActionListener(this);
        decideExpectNumberButton.addActionListener(this);
        endButton.addActionListener(this);


        loadLabel = lp.getLoadLabel();
        mainLabel = mp.getMainLabel();
        endLabel = ep.getEndLabel();

        myNumberField = sp.getMyNumber();
        expectedNumberField = mp.getExpectedNumber();

        myNumber = myNumberField.getText();
        expectedNumber = expectedNumberField.getText();

        resultArea = mp.getResultArea();
    }

    /* ^^^^^^^^^^^^各種メソッドまとめ^^^^^^^^^^^^ */

    /* -------------パネルを変更するメソッド------------- */

    public void changeIntoLoadPanel() {
        jl.setText("LOAD");
        contentPane.remove(panel2);
        panel2.remove(sp);
        panel2.add(lp);
        contentPane.add(panel2);
    }

    public void changeIntoMainPanel(String Turn) {
        jl.setText("MAIN");
        contentPane.remove(panel2);
        panel2.remove(lp);
        panel2.add(mp);
        contentPane.add(panel2);
    }

    public void changeIntoEndPanel() {
        jl.setText("END");
        contentPane.remove(panel2);
        panel2.remove(mp);
        panel2.add(ep);
        contentPane.add(panel2);
    }
    
    /* -------------メソッド------------- */
    public void update(String judgeResult) {
        int bite;
        int eat;
        int temp;
        int length;
        try {
            temp = Integer.parseInt(judgeResult);
            bite = temp / 10;
            eat = temp % 10;
            length = bite + eat;
            mp.jta.setText("BITE: " + Integer.toString(bite) + " EAT: " + Integer.toString(eat) + "\n");
            if (length == bite) {
                changeIntoEndPanel();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void update2(String judgeResult) {
        int bite;
        int eat;
        int temp;
        int length;
        try {
            temp = Integer.parseInt(judgeResult);
            bite = temp / 10;
            eat = temp % 10;
            length = bite + eat;

            if (length == bite) {
                changeIntoEndPanel();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void sendExpectNumberToServer() {
        client.sendSthToServer(expectedNumber);
    }

    /* -------------executeメソッド------------- */
    public void executeRuleButton() {
        sp.executeRulePanel();
    }

    public void executeInputMyNumberButton() {
        changeIntoLoadPanel();
    }

    public void executedecideExpectNumberButton() {
        sendExpectNumberToServer();
    }

    public void executeEndButton() {
        dispose();
    }

    /* -------------getメソッド------------- */
    public JButton getRuleButton() {
        return ruleButton;
    }

    public JButton getInputMyNumberButton() {
        return inputMyNumberButton;
    }

    public JButton getDecideExpectNumberButton() {
        return decideExpectNumberButton;
    }

    public JButton getEndButton() {
        return endButton;
    }

    public JLabel getLoadLabel() {
        return loadLabel;
    }
    
    public JLabel getEndPanel() {
        return endLabel;
    }

    public JTextField getMyNumberField() {
        return myNumberField;
    }

    public JTextField getExpectedNumberField() {
        return expectedNumberField;
    }

    public String getMyNumber() {
        return myNumber;
    }

    public String getExpectedNumber() {
        return expectedNumber;
    }

    public JTextArea getResutArea() {
        return resultArea;
    }

    public Boolean getButtonEnabled() {
        return buttonEnabled;
    }

    
    /* -------------setメソッド------------- */
    public void setMyNumberField(String str) {
        myNumberField.setText(str);;
    }

    public void setExpectedNumberField(String str) {
        expectedNumberField.setText(str);
    }

    
    public void setButtonEnabled(Boolean bool) {
        this.buttonEnabled = bool;
    }

    /* -------------actionEventに関するメソッド------------- */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ruleButton) {
            executeRuleButton();
        }
        else if (e.getSource() == inputMyNumberButton) {
            executeInputMyNumberButton();
        }
        else if (e.getSource() == decideExpectNumberButton) {
            executedecideExpectNumberButton();
        }
        else if (e.getSource() == endButton) {
            executeEndButton();
        }
    }
}
