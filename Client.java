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
        frame = new Frame(this);
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
            inputMyNumber = frame.getMyNumberField().getText();
            System.out.println(inputMyNumber);
            System.out.println(SIZE); 
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            sendSthToServer(inputMyNumber);
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
            bufferedReader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        gameStart();
    }

    public void gameStart() {
        frame.changeIntoMainPanel(HOST);
        endCondition = false;
        while (!endCondition) {

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

    /* -------------Debug用メソッド------------- */
        private void print(String str) {
        System.out.println(str);
    }

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
            latch.countDown();
            frame.executeInputMyNumberButton();
        }
        else if (e.getSource() == frame.decideExpectNumberButton) {
            expectNumber = frame.getExpectedNumberField();
            System.out.println(expectNumber);
            sendSthToServer(expectNumber);
            try {
                judgeResult = bufferedReader.readLine();
                if (judgeResult.equals("WIN") || judgeResult.equals("LOSE")) {
                    frame.changeIntoEndPanel(judgeResult);
                } else {
                        frame.appendToResutlArea("EAT : " + judgeResult.charAt(0) + "  BITE : " + judgeResult.charAt(1) + "\n");
                }
            } catch (Exception exp) {
                exp.printStackTrace();
            }
        }
        else if (e.getSource() == frame.endButton) {
            frame.executeEndButton();
        }
    }
}
