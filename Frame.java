import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    private Container contentPane;
    private StartPanel startPanel;
    private LoadPanel loadPanel;
    private MainPanel mainPanel;
    private EndPanel endPanel;
    private RepeatPanel repeatPanel;
    private WaitPanel waitPanel;

    public JButton ruleButton;                  /* StartPanelでヌメロンのルールを表示するボタン */
    public JButton inputMyNumberButton;         /* StartPanelで自分の数字を入力してServerに数字を送るボタン */
    public JButton decideExpectedNumberButton;    /* MainPanelで予想した相手の数字をServerに送信して判定してもらうためのボタン */
    public JButton endButton;                   /* EndPanelでclickするとWindowが閉じる */
    public JButton repeatButton;                /* EndPanelでclickすると再戦する */
    public JButton inputNextDigitButton;        /* repeatPanelでclickすると再戦の桁数を相手に送って同意を促す */
    public JButton backButton;                  /* repeatPanelでclickするendPanelに戻る */
    public JButton waitEndButton;               /* waitPanelでclickすると終了する */
    public JButton acceptButton;                /* waitPanelでclickすると再戦の桁数に同意する */

    public JLabel titleLabel;
    public JLabel mainLabel;
    private JLabel endLabel;                    /* EndPanelのYou Win/Loseを表示するJLabel */
    private JLabel repeatLabel;
    public JLabel waitLabel;
    
    private JTextField myNumberField;           /* StartPanelで自分の数字を入力するJTextField */
    private JTextField expectedNumberField;     /* MainPanelで相手の数字だと予想した数字を入力するJTField */
    private JTextField nextDigit;

    private JTextArea resultArea;               /* MainPanelでEatやBiteの結果をまとめるJTextArea */


    private JPanel panel1;                      /* 今表示されているpanel2が何なのかを表示する */
    private JPanel panel2;                      /* このJPanelで入力操作が行われたりメモが表示される */               

    private JLabel jl; /* 今表示されているpanel2が何かを表すJLabel */

    public String finalResult;


    public Frame() {
        contentPane = getContentPane();
        startPanel = new StartPanel();
        loadPanel = new LoadPanel();
        mainPanel = new MainPanel();
        endPanel = new EndPanel();
        repeatPanel = new RepeatPanel();
        waitPanel = new WaitPanel();
        
        initializeComponent();
        
        panel1 = new JPanel(new BorderLayout());
        panel2 = new JPanel(new BorderLayout());
        panel1.setPreferredSize(new Dimension(500, 50));
        panel2.setPreferredSize(new Dimension(500, 450));
        panel1.setBackground(Color.pink);
        panel2.setBackground(Color.pink);
        
        jl = new JLabel("START");
        jl.setHorizontalAlignment(SwingConstants.CENTER);
        
        panel1.add(jl, BorderLayout.CENTER);
        panel2.add(startPanel, BorderLayout.CENTER);
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane.setLayout(new BorderLayout());
        contentPane.add(panel1, BorderLayout.NORTH);
        contentPane.add(panel2, BorderLayout.CENTER);

        setVisible(true);
    }

    private void initializeComponent() {
        ruleButton = startPanel.getRuleButton();
        inputMyNumberButton = startPanel.getinputMyNumberButton();
        decideExpectedNumberButton = mainPanel.getdecideExpectNumberButton();
        endButton = endPanel.getEndButton();
        repeatButton = endPanel.getRepeatButton();
        waitEndButton = waitPanel.getEndButton();
        acceptButton = waitPanel.getAcceptButton();
        setAcceptButtonEnabaled(false);
        inputNextDigitButton = repeatPanel.getInputNextDigitButton();
        backButton = repeatPanel.getBackButton();

        titleLabel = startPanel.getTitleLabel();
        mainLabel = mainPanel.getMainLabel();
        endLabel = endPanel.getEndLabel();
        repeatLabel = repeatPanel.getRepeatLabel();
        waitLabel = waitPanel.getWaitLabel();

        myNumberField = startPanel.getMyNumberField();
        expectedNumberField = mainPanel.getExpectedNumberField();

        resultArea = mainPanel.getResultArea();

        nextDigit = repeatPanel.getNextDigit();
    }

    /* ^^^^^^^^^^^^各種メソッドまとめ^^^^^^^^^^^^ */

    /* -------------パネルを変更するメソッド------------- */

    public void changeIntoStartPanel() {
        jl.setText("START");
        panel2.removeAll();
        panel2.revalidate();
        contentPane.remove(panel2);
        panel2.add(startPanel);
        contentPane.add(panel2);
        contentPane.revalidate();
        contentPane.repaint();
    }

    public void changeIntoRepeatPanel() {
        jl.setText("REPEAT");
        panel2.removeAll();
        panel2.revalidate();
        contentPane.remove(panel2);
        panel2.add(repeatPanel);
        contentPane.add(panel2);
        contentPane.revalidate();
        contentPane.repaint();
    }

    public void changeIntoLoadPanel() {
        jl.setText("LOAD");
        panel2.removeAll();
        panel2.revalidate();
        contentPane.remove(panel2);
        panel2.add(loadPanel);
        contentPane.add(panel2);
        contentPane.revalidate();
        contentPane.repaint();
    }

    public void changeIntoMainPanel(String Turn) {
        jl.setText("MAIN");
        panel2.removeAll();
        panel2.revalidate();
        contentPane.remove(panel2);
        panel2.add(mainPanel);
        contentPane.add(panel2);
        if (Turn.equals("START")) {
            setDecideExpectedButtonEnabled(true);
            setMainLabel("Now is Your Turn. Input some number.");
        } else {
            setDecideExpectedButtonEnabled(false);
            setMainLabel("Now is not Your Turn. Wait for Seconds.");
        }
        contentPane.revalidate();
        contentPane.repaint();
    }

    public void changeIntoEndPanel(String Result) {
        finalResult = new String(Result);
        jl.setText("END");
        contentPane.remove(panel2);
        setEndLabel("YOU" + Result);
        panel2.removeAll();
        panel2.add(endPanel);
        contentPane.add(panel2);
        contentPane.repaint();
    }

    public void changeIntoEndPanel() {
        jl.setText(finalResult);
        panel2.removeAll();
        panel2.revalidate();
        contentPane.remove(panel2);
        panel2.add(endPanel);
        setEndLabel("YOU" + finalResult);
        contentPane.add(panel2);
        contentPane.revalidate();
        contentPane.repaint();
    }
    
    public void changeIntoWaitPanel() {
        jl.setText("WAIT");
        panel2.removeAll();
        panel2.revalidate();
        contentPane.remove(panel2);
        panel2.add(waitPanel);
        contentPane.add(panel2);
        contentPane.revalidate();
        contentPane.repaint();
    }

    /* -------------メソッド------------- */

    /* -------------executeメソッド------------- */
    public void executeRuleButton() {
        startPanel.executeRulePanel();
    }

    public void executeInputMyNumberButton() {
        changeIntoLoadPanel();
    }

    public void executeEndButton() {
        dispose();
        System.exit(0);
    }


    public void executeBackButton() {
        changeIntoEndPanel();
    }

    /* -------------getメソッド------------- */

    public String getMyNumber() {
        return myNumberField.getText();
    }

    public JLabel getTitleLabel() {
        return titleLabel;
    }

    public String getExpectedNumber() {
        return expectedNumberField.getText();
    }

    public JTextArea getResutArea() {
        return resultArea;
    }

    public String getNextDigit() {
        return nextDigit.getText();
    }

    /* -------------setメソッド------------- */

    public void setTitleLabel(String str) {
        titleLabel.setText(str);
    }
    
    public void setMainLabel(String str) {
        mainLabel.setText(str);
    }

    public void setEndLabel(String str) {
        endLabel.setText(str);
    }

    public void setRepeatLabel(String str) {
        repeatLabel.setText(str);
    }

    public void setWaitLabel(String str) {
        waitLabel.setText(str);
    }

    public void setMyNumberField(String str) {
        myNumberField.setText(str);;
    }

    public void setExpectedNumberField(String str) {
        expectedNumberField.setText(str);
    }

    public void setResultArea(String str) {
        resultArea.setText(str);
    }

    public void appendToResutlArea(String str) {
        resultArea.append(str);
    }

    public void setRuleButtonEnabled(Boolean bool) {
        ruleButton.setEnabled(bool);
    }
    
    public void setDecideExpectedButtonEnabled(Boolean bool) {
        decideExpectedNumberButton.setEnabled(bool);
    }

    public void setNextDigit(String str) {
        nextDigit.setText(str);
    }

    public void setInputNextDigitButtonEnabaled(Boolean bool) {
        inputNextDigitButton.setEnabled(bool);
    }

    public void setBackButtonEnabaled(Boolean bool) {
        backButton.setEnabled(bool);
    }

    public void setAcceptButtonEnabaled(Boolean bool) {
        acceptButton.setEnabled(bool);
    }
}