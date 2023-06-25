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
    public JButton decideExpectNumberButton;    /* MainPanelで予想した相手の数字をServerに送信して判定してもらうためのボタン */
    public JButton endButton;                   /* EndPanelでclickされるとWindowが閉じる */
    public JButton repeatButton;                /* EndPanelでclickされると再戦する */
    public JButton inputNextSizeButton;
    public JButton cancelButton;
    public JButton waitEndButton;
    public JButton acceptButton;

    public JLabel titleLabel;
    private JLabel endLabel;                    /* EndPanelのYou Win/Loseを表示するJLabel */
    private JLabel repeatLabel;
    public JLabel waitLabel;
    
    private JTextField myNumberField;           /* StartPanelで自分の数字を入力するJTextField */
    private JTextField expectedNumberField;     /* MainPanelで相手の数字だと予想した数字を入力するJTField */

    private String myNumber;                    /* StartPanelで自分の数字を入力するJTextFieldの中のString */
    private String expectedNumber;              /* MainPanelで相手の数字だと予想した数字を入力するJTFieldの中のString */
    private String nextSize;


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
        decideExpectNumberButton = mainPanel.getdecideExpectNumberButton();
        endButton = endPanel.getEndButton();
        repeatButton = endPanel.getRepeatButton();
        waitEndButton = waitPanel.getEndButton();
        acceptButton = waitPanel.getAcceptButton();
        setAcceptButtonEnabaled(false);
        inputNextSizeButton = repeatPanel.getInputNextSizeButton();
        cancelButton = repeatPanel.getCancelButton();

        titleLabel = startPanel.getTitleLabel();
        endLabel = endPanel.getEndLabel();
        repeatLabel = repeatPanel.getRepeatLabel();
        waitLabel = waitPanel.getWaitLabel();

        myNumberField = startPanel.getMyNumberField();
        expectedNumberField = mainPanel.getExpectedNumberField();

        expectedNumber = expectedNumberField.getText();
        resultArea = mainPanel.getResultArea();

        nextSize = repeatPanel.getNextSize();
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
            setButtonEnabled(true);
            mainPanel.setMainLabel("Now is Your Turn. Input some number.");
        } else {
            setButtonEnabled(false);
            mainPanel.setMainLabel("Now is not Your Turn. Wait for Seconds.");
        }
        contentPane.revalidate();
        contentPane.repaint();
    }

    public void changeIntoEndPanel(String Result) {
        finalResult = new String(Result);
        jl.setText("END");
        contentPane.remove(panel2);
        endLabel.setText("YOU" + Result);
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
        endLabel.setText("YOU" + finalResult);
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
    }


    public void executeCancelButton() {
        changeIntoEndPanel();
    }

    /* -------------getメソッド------------- */

    public String getMyNumber() {
        return myNumberField.getText();
    }

    public String getExpectedNumber() {
        return expectedNumberField.getText();
    }

    public JTextArea getResutArea() {
        return resultArea;
    }

    public String getNextSize() {
        return repeatPanel.nextSize.getText();
    }

    /* -------------setメソッド------------- */

    public void setTitleLabel(String str) {
        titleLabel.setText(str);
    }

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
        mainPanel.setMainLabel(str);
    }
    
    public void setButtonEnabled(Boolean bool) {
        decideExpectNumberButton.setEnabled(bool);
    }

    public void setInputNextSizeButtonEnabaled(Boolean bool) {
        inputNextSizeButton.setEnabled(bool);
    }

    public void setCancelButtonEnabaled(Boolean bool) {
        cancelButton.setEnabled(bool);
    }

    public void setAcceptButtonEnabaled(Boolean bool) {
        acceptButton.setEnabled(bool);
    }
}