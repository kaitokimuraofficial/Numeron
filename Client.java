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

    private String myNumber;
    private String inputNextSize;
    
    private Boolean endCondition;
    private String expectNumber;
    private String judgeResult;

    private String eat;
    private String bite;

    private String initialOrder;
    
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
        initialOrder = "INITIALSTATE";
        receiveSize();
    }
    
    public void receiveSize() {
        try {
            SIZE = Integer.parseInt(bufferedReader.readLine());
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
            latch = new CountDownLatch(1);
            System.out.println(latch);
            try {
                latch.await();
                sendSthToServer(myNumber);
                System.out.println(myNumber);
                frame.changeIntoLoadPanel();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                initialOrder = bufferedReader.readLine();
                System.out.println(initialOrder);
            } catch (IOException e) {
                e.printStackTrace();
            }
            gameStart(initialOrder);
        }
    }

    public void gameStart(String initialOrder) {
        System.out.println(initialOrder);
        frame.changeIntoMainPanel(initialOrder);

        endCondition = false;
        while (!endCondition) {
            System.out.println("gameStart");
            try {
                judgeResult = bufferedReader.readLine();
                eat = Character.toString(judgeResult.charAt(0));
                bite = Character.toString(judgeResult.charAt(1));
                System.out.println(judgeResult);
                if (eat.equals("a")) {
                    frame.setButtonEnabled(false);
                    frame.setMainLabel("You made a prediction! If client2 makes a prediction, DRAW, if not, WIN!");
                } else if (eat.equals("b")) {
                    frame.setButtonEnabled(true);
                    frame.setMainLabel("Now is Your Turn. Input some number.");
                } else if (eat.equals("c")) {
                    frame.changeIntoEndPanel("LOSE");
                } else if (eat.equals("d")) {
                    frame.changeIntoEndPanel("WIN");
                } else if (eat.equals("e")) {
                    frame.changeIntoEndPanel("DRAW");
                } else if (eat.equals("f")) {
                    endCondition = true;
                    frame.executeEndButton();
                    System.exit(0);
                } else if (eat.equals("g")) {
                    endCondition = true;
                    frame.executeEndButton();
                } else if (eat.equals("i")) {
                    SIZE = Integer.valueOf(bufferedReader.readLine());
                    endCondition = true;
                    frame.changeIntoStartPanel();
                    return;
                } else if (eat.equals("W")) {
                    frame.changeIntoWaitPanel();
                    inputNextSize = bufferedReader.readLine();
                    if (inputNextSize.equals("R")) {
                        frame.changeIntoRepeatPanel();
                        continue;
                    } else {
                        frame.waitLabel.setText("IS " + inputNextSize + " OK?");
                        frame.setAcceptButtonEnabaled(true);
                    }
                } else if (eat.equals("R")) {
                    frame.changeIntoRepeatPanel();
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
        frame.repeatButton.addActionListener(this);
        
        frame.cancelButton.addActionListener(this);
        frame.inputNextSizeButton.addActionListener(this);
        
        frame.waitEndButton.addActionListener(this);
        frame.acceptButton.addActionListener(this);
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


    /* -------------mainメソッド------------- */
    public static void main(String[] args) {
        Client client = new Client();
    }

    /* -------------actionEventに関するメソッド------------- */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == frame.ruleButton) {
            frame.executeRuleButton();
        } else if (e.getSource() == frame.inputMyNumberButton) {
            myNumber = frame.getMyNumber();
            switch (noOverlap(myNumber)) {
                case 0:
                    latch.countDown();
                    break;
                case 1:
                    frame.setTitleLabel("数字以外は入力できません");
                    break;
                case 2:
                    frame.setTitleLabel("入力した数字は桁数が不正です。" + Integer.toString(SIZE) + "桁入力してください");
                    break;
                case 3:
                    frame.setTitleLabel("数字を重複して使うことはできません");
                    break;
            }
        } else if (e.getSource() == frame.decideExpectNumberButton) {
            expectNumber = frame.getExpectedNumber();
            sendSthToServer(expectNumber);
        } else if (e.getSource() == frame.endButton) {
            sendSthToServer("end");
        } else if (e.getSource() == frame.repeatButton) {
            sendSthToServer("repeat");
        } else if (e.getSource() == frame.cancelButton) {
            sendSthToServer("cancel");
        } else if (e.getSource() == frame.inputNextSizeButton) {
            sendSthToServer("INS");
            sendSthToServer(frame.getNextSize());
            System.out.println(frame.getNextSize());
            frame.setCancelButtonEnabaled(false);
            frame.setInputNextSizeButtonEnabaled(false);
        } else if (e.getSource() == frame.waitEndButton) {
            sendSthToServer("waitEnd");
        } else if (e.getSource() == frame.acceptButton) {
            sendSthToServer("accept");
        } 
    }
}