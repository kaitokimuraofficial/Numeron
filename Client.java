import java.net.*;
import java.io.*;

public class Client {
    private static String HOST = "127.0.0.1";
    private static int PORT = 1234;
    private static int SIZE;

    private Socket socket;
    private InputStreamReader inputStreamReader;
    private OutputStreamWriter outputStreamWriter;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    private String inputNumber;
    String receiveAnswer1;

    String Turn;
    String wait;
    String expectNumber;
    String result;

    Frame frame;
    
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
     * inputNumberはframeのStartPanelから入力された数字である。
     * inputNumberから数字を得たあとはソケット通信でそれを送る
     * sendQueToServerでこのclientが準備完了したことを伝える
    */
    public void decideAndSendMyNumber() {
        inputNumber = frame.getMyNumber();
        try {
            sendSthToServer(inputNumber);
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
        gameStart();
    }

    public void gameStart() {

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


    /* -------------mainメソッド------------- */
    public static void main(String[] args) {
        Client client = new Client();
    }
}
