import java.net.*;
import java.io.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;

public class Client implements ActionListener {
    private static String HOST = "127.0.0.1";
    private static int PORT = 1234;
    private static int SIZE;

    private Socket socket;
    private InputStreamReader inputStreamReader;
    private OutputStreamWriter outputStreamWriter;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    private CountDownLatch latch;

    private Frame frame;

    private String inputMyNumber;
    
    private Boolean endCondition;
    private String expectNumber;
    private String judgeResult;

    private String eat;
    private String bite;

    private String que;

    
    /* Serverとソケット通信をつなぐ
     * つないだあとFrameクラスのframeを初期化
     * receiveSizeで自分の数字の桁数を決める
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
        frame = new Frame();
        addSthToActionListener();
        receiveSize();
    }
    
    public void receiveSize() {
        try {
            SIZE = Integer.parseInt(bufferedReader.readLine());
        } catch (Exception e) {
            e.printStackTrace();
        }
        decideAndSendMyNumber();
    }

    /* 自分の数字を決める 
     * inputMyNumberはframeのStartPanelから入力された数字である。
     * inputMyNumberから数字を得たあとはソケット通信でそれを送る
     * sendQueToServerでこのclientが準備完了したことを伝える
    */
    public void decideAndSendMyNumber() {

        try {
            latch.await();
            sendSthToServer(inputMyNumber);
            frame.changeIntoLoadPanel();
        } catch (Exception e) {
            e.printStackTrace();
        }
        sendQueToServer();
    }

    public void sendQueToServer() {
        try {
            sendSthToServer("QUE");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            que = bufferedReader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        gameStart(que);
    }

    public void gameStart(String que) {
        frame.changeIntoMainPanel(que);
        endCondition = false;
        while (!endCondition) {
            try {
                judgeResult = bufferedReader.readLine();
                eat = Character.toString(judgeResult.charAt(0));
                bite = Character.toString(judgeResult.charAt(1));
                System.out.println(judgeResult);
                if (eat.equals("a") ) {
                    frame.setButtonEnabled(false);
                    frame.setMainLabel("You made a prediction! If client2 makes a prediction, DRAW, if not, WIN!");
                } else if (eat.equals("b")) {
                    frame.setButtonEnabled(true);
                    frame.setMainLabel("Now is Your Turn. Input some number.");
                } else if (eat.equals("c")) {
                    frame.changeIntoEndPanel("LOSE");
                    break;
                } else if (eat.equals("d")) {
                    frame.changeIntoEndPanel("WIN");
                    break;
                } else if (eat.equals("e")) {
                    frame.changeIntoEndPanel("DRAW");
                    break;
                } else {
                    frame.appendToResutlArea(judgeResult + " :: EAT : " + judgeResult.charAt(0) + "  BITE : "
                            + judgeResult.charAt(1) + "\n");
                    frame.setButtonEnabled(false);
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
        frame.decideExpectNumberButton.addActionListener(this);
        frame.endButton.addActionListener(this);

        latch = new CountDownLatch(1);
    }

    public int noOverlap(String str) {
        for (int i = 0; i < str.length(); i++) {
            char currentChar = str.charAt(i);
            if (!Character.isDigit(currentChar)) {
                return 1;
            }
        }
        
        if (str.length() != SIZE) {
            return 2;
        }

        for (int i = 0; i < SIZE; i++) {
            char currentChar = str.charAt(i);
            if (str.indexOf(currentChar, i + 1) != -1) {
                return 3;
            }
        }
        return 0;
 
    }

    /* -------------Debug用メソッド------------- */

    /* -------------mainメソッド------------- */
    public static void main(String[] args) {
        Client client = new Client();
    }

    /* -------------actionEventに関するメソッド------------- */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == frame.ruleButton) {
            frame.executeRuleButton();
        }
        else if (e.getSource() == frame.inputMyNumberButton) {
            inputMyNumber = frame.getMyNumberField();
            System.out.println(inputMyNumber);
            System.out.println(SIZE);
            switch (noOverlap(inputMyNumber)) {
                case 0:
                    latch.countDown();
                    break;

                case 1:
                    frame.titleLabel.setText("数字以外は入力できません");
                    break;
                case 2:
                    frame.titleLabel.setText("入力した数字は桁数が不正です。" + Integer.toString(SIZE) + "桁入力してください");
                    break;
                case 3:
                    frame.titleLabel.setText("数字を重複して使うことはできません");
                    break;
            }
        } else if (e.getSource() == frame.decideExpectNumberButton) {
            expectNumber = frame.getExpectedNumberField();
            sendSthToServer(expectNumber);
        } else if (e.getSource() == frame.endButton) {
            frame.executeEndButton();
        } 
    }
}