import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

/* Serverクラスでは各プレイヤーがどんな数字を決めたかを保存する
 * Clientクラスからソケット経由で送られてきた予想結果をJudgeクラスにて
 * 判定してその結果とそれに付随する次の動作の命令をClientクラスに戻す
 */
public class Server extends Thread {

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

    private Thread thread1;
    private Thread thread2;

    private String tempNumber1;
    private String tempNumber2;

    private String expectedNumber1;
    private String expectedNumber2;

    private String judgeResult1; /* Client1がClient2の数字を予想した判定結果 */
    private String judgeResult2; /* Client2がClient1の数字を予想した判定結果 */


    private String que1;
    private String que2;

    private String eat1;
    private String bite1;

    private String eat2;
    private String bite2;
    private int endCondition;

    private CountDownLatch latch;

    Scanner scanner = new Scanner(System.in);

    /* サーバーソケットを立ち上げる
     * ゲームにて使用する数字の桁数(SIZE)を
     * decideSize()にて決定する
     */
    public Server(int num) {
        System.out.println("Connecting...  HOST: " + HOST + " PORT: " + PORT);
        endCondition = num;
        try {
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(HOST, PORT));
        } catch (Exception e) {
            e.printStackTrace();
        }
        latch = new CountDownLatch(2);
        decideSize();
    }

    /* プロンプト上で入力された数字を桁数(SIZE)として設定する
     * judgeを宣言する。judgeのコンストラクタではnubmer1,2の初期化が行われる
     */
    private void decideSize() {
        System.out.print("Enter the Number: ");

        try {
            SIZE = Integer.parseInt(scanner.nextLine());
            System.out.println("SIZE is " + SIZE);
            judge = new Judge(this, SIZE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        createThreadAndConnectSocket();
    }

    public void createThreadAndConnectSocket() {
        thread1 = createSocketThread("1");
        thread2 = createSocketThread("2");

        thread1.start();
        thread2.start();
    }

    /* ^^^^^^^^^^^^各種メソッドまとめ^^^^^^^^^^^^ */
    private Thread createSocketThread(String threadName) {
        return new Thread(() -> {
            if (threadName.equals("1")) {
                connectFirstSocket();
                decideFirstNumber();
                waitForReplyFromFirstSocket();
                
                while (true) {
                    try {
                        expectedNumber1 = bufferedReader1.readLine();
                        judgeResult1 = judge.startJudge(expectedNumber1, 2);
                        eat1 = String.valueOf(judgeResult1.charAt(0));
                        bite1 = String.valueOf(judgeResult1.charAt(1));

                        if (eat1.equals(Integer.toString(SIZE))) {
                            sendToBothSocket("a" + bite1, "b" + bite1);
                            endCondition = 1;
                        } else {
                            sendToBothSocket(judgeResult1, "b"+bite1);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else if (threadName.equals("2")) {
                connectSecondSocket();
                decideSecondNumber();
                waitForReplyFromSecondSocket();

                while (true) {
                    try {
                        expectedNumber2 = bufferedReader2.readLine();
                        judgeResult2 = judge.startJudge(expectedNumber2, 1);
                        eat2 = String.valueOf(judgeResult2.charAt(0));
                        bite2 = String.valueOf(judgeResult2.charAt(1));

                        if (endCondition == 0) {
                            if (eat2.equals(Integer.toString(SIZE))) {
                                sendToBothSocket("c" + bite2, "d" + bite2);
                                break;
                            } else {
                                sendToBothSocket("b"+bite2, judgeResult2);
                            }
                        } else if (endCondition == 1) {
                            if (eat2.equals(Integer.toString(SIZE))) {
                                sendToBothSocket("e" + bite2, "e" + bite2);
                                break;
                            } else {
                                sendToBothSocket("d" + bite2, "c" + bite2);
                                break;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    
    /* -------------ソケットの初期化メソッド------------- */
    /* 1つ目のソケットをconnectする
     * ソケット通信にて必要なものを初期化する
     * ソケットがつながったあとdecideFirstNumber()によって1つ目の
     * Clientが相手に予想させる数字を決める
     */
    private void connectFirstSocket() {
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
    }

    /* まず最初にSIZEをClient1に送信する
    * Client1はもらったSIZE桁の数字を決めたあと
    * Serverに送信し返すのでそれをnumber1[]に格納する
    */
    private void decideFirstNumber() {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void waitForReplyFromFirstSocket() {
        try {
            que1 = bufferedReader1.readLine();
            latch.countDown();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            latch.await();
            bufferedWriter1.write("START");
            bufferedWriter1.newLine();
            bufferedWriter1.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
  
    /* 2つ目のソケットをconnectする
     * ソケット通信にて必要なものを初期化する
     * ソケットがつながったあとdecideSecondNumber()によって2つ目の
     * Clientが相手に予想させる数字を決める
     */
    private void connectSecondSocket() {
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
    }

    /* まず最初にSIZEをClient2に送信する
    * Client2はもらったSIZE桁の数字を決めたあと
    * Serverに送信し返すのでそれをnumber2[]に格納する
    */
    private void decideSecondNumber() {
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
    }
    
    private void waitForReplyFromSecondSocket() {
        try {
            que2 = bufferedReader2.readLine();
            latch.countDown();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            latch.await();
            bufferedWriter2.write("SECOND");
            bufferedWriter2.newLine();
            bufferedWriter2.flush();
        } catch (Exception e) {
            e.printStackTrace();;
        }
    }
    

    /* -------------両方のソケットに送信するメソッド------------- */
    private void sendToBothSocket(String str1, String str2) {
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

    /* -------------getメソッド------------- */
    
    /* -------------setメソッド------------- */

    /* -------------Debug用メソッド------------- */

    /* -------------mainメソッド------------- */
    public static void main(String[] args) {
        Server server = new Server(0);
    }
}