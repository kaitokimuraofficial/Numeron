import java.net.*;
import java.io.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;

public class Client implements ActionListener {
    private static String HOST = "127.0.0.1";
    private static int PORT = 1234;
    private static int DIGIT;

    private Socket socket;
    private InputStreamReader inputStreamReader;
    private OutputStreamWriter outputStreamWriter;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    private CountDownLatch latch;

    private Frame frame;

    private String myNumber;
    private String inputNextDigit;
    
    private Boolean endCondition;
    private String expectedNumber;
    private String judgeResult;

    private String eat;
    private String bite;

    private String status;

    private String initialOrder;

    private String nextDigitString;
    
    /* Serverとソケット通信をつなぐ
     * つないだあとFrameクラスのframeを初期化
     * receiveDigitで自分の数字の桁数を決める
     */
    public Client() {
        try {
            socket = new Socket(HOST, PORT);
            inputStreamReader = new InputStreamReader(socket.getInputStream());
            outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
            bufferedReader = new BufferedReader(inputStreamReader);
            bufferedWriter = new BufferedWriter(outputStreamWriter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        initialOrder = "INITIALSTATE";
        receiveDigit();
    }
    
    public void receiveDigit() {
        try {
            DIGIT = Integer.parseInt(bufferedReader.readLine());
            frame = new Frame();
            addSthToActionListener();
        } catch (Exception e) {
            e.printStackTrace();
        }
        decideAndSendMyNumber();
    }

    /* 自分の数字を決める 
     * inputMyNumberはframeのStartPanelから入力された数字である。
     * inputMyNumberから数字を得たあとはソケット通信でそれを送る
     * sendinitialOrderToServerでこのclientが準備完了したことを伝える
    */
    public void decideAndSendMyNumber() {
        while (true) {
            frame.setInputNextDigitButtonEnabaled(true);
            latch = new CountDownLatch(1);
            try {
                latch.await();
                sendSthToServer(myNumber);
                frame.setMyNumberField("");
                frame.setTitleLabel("");
                frame.changeIntoLoadPanel();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                initialOrder = bufferedReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            gameStart(initialOrder);
        }
    }

    public void gameStart(String initialOrder) {
        frame.changeIntoMainPanel(initialOrder);

        endCondition = false;
        while (!endCondition) {
            try {
                judgeResult = bufferedReader.readLine();
                eat = Character.toString(judgeResult.charAt(0));
                bite = Character.toString(judgeResult.charAt(1));
                if (eat.equals("a")) {
                     frame.appendToResutlArea(expectedNumber + " :: EAT : " + DIGIT + "  BITE : "
                            + 0 + "\n");
                    frame.setDecideExpectedButtonEnabled(false);
                    frame.setMainLabel("You made a prediction! If client2 makes a prediction, DRAW, if not, WIN!");
                } else if (eat.equals("b")) {
                    frame.setDecideExpectedButtonEnabled(true);
                    frame.setMainLabel("Now is Your Turn. Input some number.");
                } else if (eat.equals("c")) {
                    frame.setResultArea("");
                    frame.setExpectedNumberField("");
                    status = "LOSE";
                    frame.changeIntoEndPanel("LOSE");
                } else if (eat.equals("d")) {
                    frame.setResultArea("");
                    frame.setExpectedNumberField("");
                    status = "WIN";
                    frame.changeIntoEndPanel("WIN");
                } else if (eat.equals("e")) {
                    frame.setResultArea("");
                    frame.setExpectedNumberField("");
                    status = "DRAW";
                    frame.changeIntoEndPanel("DRAW");
                } else if (eat.equals("f")) {
                    endCondition = true;
                    frame.executeEndButton();
                    System.exit(0);
                } else if (eat.equals("g")) {
                    endCondition = true;
                    frame.executeEndButton();
                } else if (eat.equals("i")) {
                    DIGIT = Integer.valueOf(bufferedReader.readLine());
                    endCondition = true;
                    status = "";
                    frame.changeIntoStartPanel();
                    return;
                } else if (eat.equals("W")) {
                    frame.changeIntoWaitPanel();
                    inputNextDigit = bufferedReader.readLine();
                    if (inputNextDigit.equals("KK")) {
                        frame.changeIntoEndPanel(status);
                        continue;
                    } else {
                        frame.setWaitLabel("IS " + inputNextDigit + " OK?");
                        frame.setAcceptButtonEnabaled(true);
                    }
                } else if (eat.equals("R")) {
                    frame.changeIntoRepeatPanel();
                } else if (eat.equals("K")) {
                    frame.changeIntoEndPanel(status);
                } else {
                    frame.appendToResutlArea(expectedNumber + "   :: EAT : " + eat + "  BITE : "
                            + bite + "\n");
                    frame.setExpectedNumberField("");
                    frame.setDecideExpectedButtonEnabled(false);
                    frame.setMainLabel("Now is not Your Turn. Wait for Seconds.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    
    /* ^^^^^^^^^^^^各種メソッドまとめ^^^^^^^^^^^^ */
    public void sendSthToServer(String str) {
        try {
            bufferedWriter.write(str);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void addSthToActionListener() {
        frame.ruleButton.addActionListener(this);
        frame.inputMyNumberButton.addActionListener(this);
        
        frame.decideExpectedNumberButton.addActionListener(this);
        
        frame.endButton.addActionListener(this);
        frame.repeatButton.addActionListener(this);
        
        frame.backButton.addActionListener(this);
        frame.inputNextDigitButton.addActionListener(this);
        
        frame.waitEndButton.addActionListener(this);
        frame.acceptButton.addActionListener(this);
    }

    public int isvalid(String str) {
        for (int i = 0; i < str.length(); i++) {
            char currentChar = str.charAt(i);
            if (!Character.isDigit(currentChar)) {
                return 1;
            }
        }

        if (str.length() != DIGIT) {
            return 2;
        }

        for (int i = 0; i < DIGIT; i++) {
            char currentChar = str.charAt(i);
            if (str.indexOf(currentChar, i + 1) != -1) {
                return 3;
            }
        }
        return 0;
    }
    
    public int isvalid2(String str) {
        for (int i = 0; i < str.length(); i++) {
            char currentChar = str.charAt(i);
            if (!Character.isDigit(currentChar)) {
                return 1;
            }
        }
        
        if (str.length() != 1) {
            return 2;
        }
        return 0;
    }


    /* -------------mainメソッド------------- */
    public static void main(String[] args) {
        Client client = new Client();
    }

    /* -------------actionEventに関するメソッド------------- */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == frame.ruleButton) {
            frame.setRuleButtonEnabled(false);
            frame.executeRuleButton();
        } else if (e.getSource() == frame.inputMyNumberButton) {
            myNumber = frame.getMyNumber();
            Font font = frame.getTitleLabel().getFont();
            frame.getTitleLabel().setFont(font.deriveFont(25f));
            switch (isvalid(myNumber)) {
                case 0:
                    latch.countDown();
                    break;
                case 1:
                    frame.setTitleLabel("数字以外は入力できません");
                    break;
                case 2:
                    frame.setTitleLabel(Integer.toString(DIGIT) + "桁入力してください");
                    break;
                case 3:
                    frame.setTitleLabel("数字を重複して使うことはできません");
                    break;
            }
        } else if (e.getSource() == frame.decideExpectedNumberButton) {
            expectedNumber = frame.getExpectedNumber();
            switch (isvalid(expectedNumber)) {
                case 0:
                    sendSthToServer(expectedNumber);
                    break;
                    case 1:
                    frame.setMainLabel("数字以外は入力できません");
                    break;
                case 2:
                    frame.setMainLabel(Integer.toString(DIGIT) + "桁入力してください");
                    break;
                case 3:
                    frame.setMainLabel("数字を重複して使うことはできません");
                    break;
            }
        } else if (e.getSource() == frame.endButton) {
            sendSthToServer("end");
        } else if (e.getSource() == frame.repeatButton) {
            sendSthToServer("repeat");
        } else if (e.getSource() == frame.backButton) {
            sendSthToServer("back");
        } else if (e.getSource() == frame.inputNextDigitButton) {
            sendSthToServer("INS");
            nextDigitString = frame.getNextDigit();
            switch (isvalid2(nextDigitString)) {
                case 0:
                    sendSthToServer(nextDigitString);
                    frame.setBackButtonEnabaled(false);
                    frame.setInputNextDigitButtonEnabaled(false);
                    break;
                case 1:
                    frame.setRepeatLabel("数字以外は入力できません");
                    break;
                case 2:
                    frame.setRepeatLabel("1桁だけ入力してください");
                    break;
            }
        } else if (e.getSource() == frame.waitEndButton) {
            sendSthToServer("waitEnd");
        } else if (e.getSource() == frame.acceptButton) {
            sendSthToServer("accept");
        } 
    }
}