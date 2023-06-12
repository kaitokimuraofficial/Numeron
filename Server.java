import java.net.*;
import java.io.*;
import java.util.Scanner;

/* Serverクラスでは各プレイヤーがどんな数字を決めたかを保存する
 * Clientクラスからソケット経由で送られてきた予想結果をJudgeクラスにて
 * 判定してその結果とそれに付随する次の動作の命令をClientクラスに戻す
 */
public class Server {

    private static String HOST = "127.0.0.1";
    private static int PORT = 1234;
    private static int SIZE;

    private static ServerSocket serverSocket;
    
    private static Socket socket1;
    private static Socket socket2;
    private static InputStreamReader inputStreamReader1;
    private static OutputStreamWriter outputStreamWriter1;
    private static InputStreamReader inputStreamReader2;
    private static OutputStreamWriter outputStreamWriter2;
 
    private static BufferedReader bufferedReader1;
    private static BufferedWriter bufferedWriter1;
    private static BufferedReader bufferedReader2;
    private static BufferedWriter bufferedWriter2;

    private Judge judge;
    private Server server;

    private String tempNumber1;
    private String tempNumber2;

    String judgeResult1To2;
    String judgeResult2To1;

    private String bite;
    private String eat;

    String que;

    Scanner scanner = new Scanner(System.in);

    /* サーバーソケットを立ち上げる
     * ゲームにて使用する数字の桁数(SIZE)を
     * decideSize()にて決定する
     */
    public Server() {
        System.out.println("Connecting...  HOST: " + HOST + " PORT: " + PORT);
        
        try {
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(HOST, PORT));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        decideSize();
    }

    /* プロンプト上で入力された数字を桁数(SIZE)として設定する
     * judgeを宣言する。judgeのコンストラクタではnubmer1,2の初期化が行われる
     */
    public void decideSize() {
        System.out.print("Enter the Number: ");

        try {
            SIZE = Integer.parseInt(scanner.nextLine());
            System.out.println("SIZE is " + SIZE);
            judge = new Judge(this, SIZE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        connectFirstSocket();
    }

    /* 1つ目のソケットをconnectする
     * ソケット通信にて必要なものを初期化する
     * ソケットがつながったあとdecideFirstNumber()によって1つ目の
     * Clientが相手に予想させる数字を決める
     */
    public void connectFirstSocket() {
        try {
            socket1 = serverSocket.accept();
            System.out.println("Connected 1st Socket!");

            inputStreamReader1 = new InputStreamReader(socket1.getInputStream());
            outputStreamWriter1 = new OutputStreamWriter(socket1.getOutputStream());
            bufferedReader1 = new BufferedReader(inputStreamReader1);
            bufferedWriter1 = new BufferedWriter(outputStreamWriter1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        decideFirstNumber();
    }

    /* まず最初にSIZEをClient1に送信する
    * Client1はもらったSIZE桁の数字を決めたあと
    * Serverに送信し返すのでそれをnumber1[]に格納する
    */
    public void decideFirstNumber() {
        try {
            bufferedWriter1.write(String.valueOf(SIZE));
            bufferedWriter1.newLine();
            bufferedWriter1.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            tempNumber1 = bufferedReader1.readLine();
            System.out.println("Player1 decided its Number: " + tempNumber1);

            /* ここでjudgeにnumber1にClient1の数字を読み込ませる */
            judge.setNumber1(tempNumber1);

            System.out.println("START CONNECTSECOND");
        } catch (Exception e) {
            e.printStackTrace();
        }
        waitForReplyFromFirstSocket();
    }

    public void waitForReplyFromFirstSocket() {
        try {
            que = bufferedReader1.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        connectSecondSocket();
    }
  
    /* 2つ目のソケットをconnectする
     * ソケット通信にて必要なものを初期化する
     * ソケットがつながったあとdecideSecondNumber()によって2つ目の
     * Clientが相手に予想させる数字を決める
     */
    public void connectSecondSocket() {
        try {
            socket2 = serverSocket.accept();
            System.out.println("Connected 2nd Socket!");

            inputStreamReader2 = new InputStreamReader(socket2.getInputStream());
            outputStreamWriter2 = new OutputStreamWriter(socket2.getOutputStream());
            bufferedReader2 = new BufferedReader(inputStreamReader2);
            bufferedWriter2 = new BufferedWriter(outputStreamWriter2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        decideSecondNumber();
    }

    /* まず最初にSIZEをClient2に送信する
    * Client2はもらったSIZE桁の数字を決めたあと
    * Serverに送信し返すのでそれをnumber2[]に格納する
    * 
    * gameStart()にてゲームをスタートする
    */
    public void decideSecondNumber() {
        try {
            bufferedWriter2.write(String.valueOf(SIZE));
            bufferedWriter2.newLine();
            bufferedWriter2.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        try {
            tempNumber2 = bufferedReader2.readLine();
            System.out.println("Player2 decided its Number: " + tempNumber2);

            /* ここでjudgeにnumber2にClient2の数字を読み込ませる */
            judge.setNumber2(tempNumber2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        waitForReplyFromFirstSocket();
    }
    
    public void waitForReplyFromSecondSocket() {
        try {
            que = bufferedReader2.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        gameStart();
    }
    
    public void gameStart() {
        
    }
    

    /* ^^^^^^^^^^^^各種メソッドまとめ^^^^^^^^^^^^ */

    /* -------------両方のソケットに送信するメソッド------------- */
    public void sendToBothSocket(String str1, String str2) {
        try {
            bufferedWriter1.write(str1);
            bufferedWriter1.newLine();
            bufferedWriter1.flush();
            bufferedWriter2.write(str2);
            bufferedWriter2.newLine();
            bufferedWriter2.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* -------------setメソッド------------- */
    
    public void setEat(int eat) {
        this.eat = Integer.toString(eat);
    }

    public void setBite(int bite) {
        this.bite = Integer.toString(bite);
    }

    /* -------------mainメソッド------------- */
    public static void main(String[] args) {
        Server server = new Server();
    }
}