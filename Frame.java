import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    private Container contentPane;
    private StartPanel sp;
    private LoadPanel lp;
    private MainPanel mp;
    private EndPanel ep;

    public JButton ruleButton;                  /* StartPanelでヌメロンのルールを表示するボタン */
    public JButton inputMyNumberButton;         /* StartPanelで自分の数字を入力してServerに数字を送るボタン */
    public JButton decideExpectNumberButton;    /* MainPanelで予想した相手の数字をServerに送信して判定してもらうためのボタン */
    public JButton endButton;                   /* EndPanelでclickされるとWindowが閉じる */
    public JButton repeatButton;                /* EndPanelでclickされると再戦する */


    public JLabel titleLabel;
    private JLabel loadLabel;                   /* LoadPanelでLoading...を表示するJLabel */
    private JLabel mainLabel;                   /* MainPanelで結果を表示するJLabel */
    private JLabel endLabel;                    /* EndPanelのYou Win/Loseを表示するJLabel */

    private JTextField myNumberField;           /* StartPanelで自分の数字を入力するJTextField */
    private JTextField expectedNumberField;     /* MainPanelで相手の数字だと予想した数字を入力するJTField */

    private String myNumber;                    /* StartPanelで自分の数字を入力するJTextFieldの中のString */
    private String expectedNumber;              /* MainPanelで相手の数字だと予想した数字を入力するJTFieldの中のString */

    private JTextArea resultArea;               /* MainPanelでEatやBiteの結果をまとめるJTextArea */


    private JPanel panel1;                      /* 今表示されているpanel2が何なのかを表示する */
    private JPanel panel2;                      /* このJPanelで入力操作が行われたりメモが表示される */               

    private JLabel jl;                          /* 今表示されているpanel2が何かを表すJLabel */


    public Frame() {
        contentPane = getContentPane();
        sp = new StartPanel();
        lp = new LoadPanel();
        mp = new MainPanel();
        ep = new EndPanel();
        
        initializeComponent();
        
        panel1 = new JPanel(new BorderLayout());
        panel2 = new JPanel(new BorderLayout());
        panel1.setPreferredSize(new Dimension(500, 50));
        panel2.setPreferredSize(new Dimension(500, 450));
        panel1.setBackground(Color.yellow);
        panel2.setBackground(Color.BLUE);
        
        jl = new JLabel("START");
        jl.setHorizontalAlignment(SwingConstants.CENTER);
        
        panel1.add(jl, BorderLayout.CENTER);
        panel2.add(sp, BorderLayout.CENTER);
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane.setLayout(new BorderLayout());
        contentPane.add(panel1, BorderLayout.NORTH);
        contentPane.add(panel2, BorderLayout.CENTER);

        // pack();
        setVisible(true);
    }

    private void initializeComponent() {
        ruleButton = sp.getRuleButton();
        inputMyNumberButton = sp.getinputMyNumberButton();
        decideExpectNumberButton = mp.getdecideExpectNumberButton();
        endButton = ep.getEndButton();
        repeatButton = ep.getRepeatButton();

        titleLabel = sp.getTitleLabel();
        loadLabel = lp.getLoadLabel();
        mainLabel = mp.getMainLabel();
        endLabel = ep.getEndLabel();

        myNumberField = sp.getMyNumber();
        expectedNumberField = mp.getExpectedNumber();

        expectedNumber = expectedNumberField.getText();

        resultArea = mp.getResultArea();
    }

    /* ^^^^^^^^^^^^各種メソッドまとめ^^^^^^^^^^^^ */

    /* -------------パネルを変更するメソッド------------- */

    public void changeIntoStartPanel() {
        jl.setText("START");
        contentPane.remove(panel2);
        panel2.remove(ep);
        panel2.add(sp);
        contentPane.add(panel2);
    }

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
        if (Turn.equals("START")) {
            setButtonEnabled(true);
            mp.setMainLabel("Now is Your Turn. Input some number.");
        } else {
            setButtonEnabled(false);
            mp.setMainLabel("Now is not Your Turn. Wait for Seconds.");
        }
    }

    public void changeIntoEndPanel(String Result) {
        jl.setText("END");
        contentPane.remove(panel2);
        endLabel.setText("YOU" + Result);
        panel2.remove(mp);
        panel2.add(ep);
        contentPane.add(panel2);
    }
    
    /* -------------メソッド------------- */

    /* -------------executeメソッド------------- */
    public void executeRuleButton() {
        sp.executeRulePanel();
    }

    public void executeInputMyNumberButton() {
        changeIntoLoadPanel();
    }

    public void executeEndButton() {
        dispose();
    }

    public void executeRepeatButton() {
        changeIntoStartPanel();
    }

    /* -------------getメソッド------------- */

    public String getMyNumberField() {
        return myNumberField.getText();
    }

    public String getExpectedNumberField() {
        return mp.expectedNumber.getText();
    }

    public String getExpectedNumber() {
        return expectedNumber;
    }

    public JTextArea getResutArea() {
        return resultArea;
    }

    /* -------------setメソッド------------- */
    public void setMyNumberField(String str) {
        myNumberField.setText(str);;
    }

    public void setExpectedNumberField(String str) {
        expectedNumberField.setText(str);
    }

    public void setResultArea(String str) {
        this.resultArea.setText(str);
    }

    public void appendToResutlArea(String str) {
        resultArea.append(str);
    }

    public void setMainLabel(String str) {
        mp.setMainLabel(str);
    }
    
    public void setButtonEnabled(Boolean bool) {
        decideExpectNumberButton.setEnabled(bool);
    }
}