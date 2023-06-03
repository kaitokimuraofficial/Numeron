import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Frame extends JFrame implements ActionListener{

    Container contentPane;
    StartPanel sp;
    LoadPanel lp;
    MainPanel mp;
    EndPanel ep;

    JPanel panel1;
    JPanel panel2;

    JButton ruleButton;
    JButton startButton;

    JButton inputButton;

    private String number;
    Boolean buttonEnabled;

    JLabel jl;

    String frameexpectNumber;
    String wait;

    public Frame() {

        contentPane = getContentPane();
        sp = new StartPanel();
        mp = new MainPanel();
        ruleButton = sp.getRuleButton();
        startButton = sp.getStartButton();
        inputButton = mp.getInputButton();

        ruleButton.addActionListener(this);
        startButton.addActionListener(this);
        inputButton.addActionListener(this);

        lp = new LoadPanel();
        mp = new MainPanel();
        ep = new EndPanel();

        panel1 = new JPanel();
        panel2 = new JPanel();
        panel1.setPreferredSize(new Dimension(500, 50));
        panel2.setPreferredSize(new Dimension(500, 350));
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

    public void executeRuleButton() {
        sp.executeRulePanel();
    }

    public void executeStartButton() {
        setNumber();
        changeIntoLoadPanel();
    }


    public void changeIntoLoadPanel() {
        jl.setText("LOAD");
        panel2.remove(sp);
        panel2.add(lp, BorderLayout.CENTER);
        contentPane.add(panel2, BorderLayout.CENTER);
    }

    public void setNumber() {
        number = sp.getJTextField();
        System.out.println(number);
    }

    public String getNumber() {
        return number;
    }

    public void changeIntoMainPanel(String Turn) {
        if (Turn.equals("GO")) {
            buttonEnabled = true;
            jl.setText("YOUR TURN");
        }
        else {
            buttonEnabled = false;
            jl.setText("WAIT");
        }
        panel2.remove(lp);
        panel2.add(mp, BorderLayout.CENTER);
        contentPane.add(panel2, BorderLayout.CENTER);
    }

    public void changeIntoEndPanel() {
        jl.setText("END");
        panel2.remove(mp);
        panel2.add(ep, BorderLayout.CENTER);
        contentPane.add(panel2, BorderLayout.CENTER);
    }

    public void executeInputButton() {
        if (buttonEnabled == false) {
            return;
        } else {
            frameexpectNumber = mp.getExpectNumber();
            wait = "OK";

        }
        wait = null;
    }
    
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
            bite = temp/10;
            eat = temp % 10;
            length = bite + eat;
  
            if (length == bite) {
                changeIntoEndPanel();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Boolean getButtonEnabled() {
        return buttonEnabled;
    }

    public String getWait() {
        return wait;
    }

    public String getExpectNumber() {
        return frameexpectNumber;
    }
    
    public void setButtonEnabled(Boolean buttonEnabled) {
        buttonEnabled = this.buttonEnabled;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ruleButton) {
            executeRuleButton();
        }
        else if (e.getSource() == startButton) {
            executeStartButton();
        }
        else if (e.getSource() == inputButton) {
            executeInputButton();
        }
    }
}
