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


    public Frame() {

        contentPane = getContentPane();
        sp = new StartPanel();
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

    // public void changeIntoStartPanel() {
    //     jl.setText("START");
    //     panel2.remove(1);
    //     panel2.add(sp, BorderLayout.CENTER);
    // }

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

    public void changeIntoMainPanel(int Turn) {
        jl.setText("MAIN");
        if (Turn == 1) {
            buttonEnabled = true;
        }
        else {
            buttonEnabled = false;
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
        }
        else {
            
        }
    }

    public Boolean getButtonEnabled() {
        return buttonEnabled;
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
