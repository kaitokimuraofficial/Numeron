import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

/* Serverクラスでは各プレイヤーがどんな数字を決めたかを保存する
 * Clientクラスからソケット経由で送られてきた予想結果をJudgeクラスにて
 * 判定してその結果とそれに付随する次の動作の命令をClientクラスに送る
 */
public class Server extends Thread {

    private static final int NOTCHECKMATE = 0;
    private static final int CHECKMATE = 1;

    private static String HOST = "127.0.0.1";
    private static int PORT = 1234;
    private static int DIGIT;

    private String nextDigit;

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

    private Thread preThread1;
    private Thread preThread2;
    private Thread thread1;
    private Thread thread2;

    private Boolean isGameFinished;

    private String firstThreadNumber;
    private String secondThreadNumber;

    private String expectedNumber1;
    private String expectedNumber2;

    private String judgeResult1; /* Client1がClient2の数字を予想した判定結果 */
    private String judgeResult2; /* Client2がClient1の数字を予想した判定結果 */

    private String eat1;
    private String bite1;

    private String eat2;
    private String bite2;

    private int endCondition;

    private Boolean interrupted;

    public CountDownLatch latch;
    public CountDownLatch latch2;

    private Scanner scanner = new Scanner(System.in);

    /* サーバーソケットを立ち上げる */
    public Server() {
        System.out.println("Connecting...  HOST: " + HOST + " PORT: " + PORT);
        isGameFinished = false;
        nextDigit = "";
        try {
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(HOST, PORT));
        } catch (Exception e) {
            e.printStackTrace();
        }

        judge = new Judge(this);

        /* 1個のスレッドがコネクトされるごとに1つずつlatchが減る */
        latch = new CountDownLatch(2);
        decideDigit();
    }

    /* プロンプト上で入力された数字を桁数(DIGIT)として設定する */
    private void decideDigit() {
        System.out.print("Enter the number of digits : ");

        try {
            DIGIT = Integer.parseInt(scanner.nextLine());
            System.out.println("The number of digits is " + DIGIT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        connectSocket();
    }

    public void connectSocket() {
        preThread1 = connectEachSocket("1");
        preThread2 = connectEachSocket("2");

        /* ここで2つのソケットがコネクトされる */
        preThread1.start();
        preThread2.start();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        gameStart();
    }

    public void gameStart() {
        judgeInit();
        sendToBothSocket(Integer.toString(DIGIT), Integer.toString(DIGIT));

        /* それぞれの番号を決定する */
        
        while (!isGameFinished) {
            interrupted = false;
            latch = new CountDownLatch(2);
            thread1 = startEachThread("1");
            thread2 = startEachThread("2");
            latch2 = new CountDownLatch(1);
            thread1.start();
            thread2.start();
            try {
                latch2.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* ^^^^^^^^^^^^各種メソッドまとめ^^^^^^^^^^^^ */

    private Thread connectEachSocket(String threadName) {
        return new Thread(() -> {
            if (threadName.equals("1")) {
                connectFirstSocket();
            } else if (threadName.equals("2")) {
                connectSecondSocket();
            }
        });
    }

    private Thread startEachThread (String threadName) {
        return new Thread(() -> {
            if (threadName.equals("1")) {
                decideFirstNumber();
                try {
                    latch.await();
                    sendToBothSocket("START", "WAIT");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                listenFromClient1();
            } else if (threadName.equals("2")) {
                decideSecondNumber();
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                listenFromClient2();
            }
        });
    }


    public void listenFromClient1() {
        while (true) {
            try {
                expectedNumber1 = bufferedReader1.readLine();
                if (interrupted) {
                    break;
                } /* client1にてendPanelのendボタンが押された */
                if (expectedNumber1.equals("end")) {
                    sendToBothSocket("ff", "ff");
                    System.exit(0);
                } /* client1にてrepeatPanelのrepeatボタンが押された */
                else if (expectedNumber1.equals("repeat")) {
                    sendToBothSocket("RR", "WW");
                } /* client1にてrepeatPanelのbackボタンが押された */
                else if (expectedNumber1.equals("back")) {
                    sendToBothSocket("KK", "KK");
                } /* client2にてrepeatPanelのinputNextDigitボタンが押された */
                else if (expectedNumber1.equals("INS")) {
                    nextDigit = bufferedReader1.readLine();
                    bufferedWriter2.write(nextDigit);
                    bufferedWriter2.newLine();
                    bufferedWriter2.flush();
                    try {
                        latch2.await();
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } /* client1にてwaitPanelのendボタンが押された */
                else if (expectedNumber1.equals("waitEnd")) {
                    sendToBothSocket("gg", "gg");
                    isGameFinished = true;
                    System.exit(0);
                } /* client1にてwaitPanelのacceptボタンが押された */
                else if (expectedNumber1.equals("accept")) {
                    sendToBothSocket("ii", "ii");
                    sendToBothSocket(nextDigit, nextDigit);
                    latch = new CountDownLatch(2);
                    interrupted = true;
                    DIGIT = Integer.valueOf(nextDigit);
                    latch2.countDown();
                    return;
                } else {
                    decideNextAction1(expectedNumber1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void listenFromClient2() {
        while (true) {
            try {
                expectedNumber2 = bufferedReader2.readLine();
                if (interrupted) {
                    break;
                } /* client2にてendPanelのendボタンが押された */
                if (expectedNumber2.equals("end")) {
                    sendToBothSocket("ff", "ff");
                    System.exit(0);
                } /* client2にてrepeatPanelのrepeatボタンが押された */
                else if (expectedNumber2.equals("repeat")) {
                    sendToBothSocket("WW", "RR");
                } /* client2にてrepeatPanelのbackボタンが押された */
                else if (expectedNumber2.equals("back")) {
                    sendToBothSocket("KK", "KK");
                } /* client2にてrepeatPanelのinputNextDigitボタンが押された */
                else if (expectedNumber2.equals("INS")) {
                    nextDigit = bufferedReader2.readLine();
                    bufferedWriter1.write(nextDigit);
                    bufferedWriter1.newLine();
                    bufferedWriter1.flush();
                    try {
                        latch2.await();
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } /* client2にてwaitPanelのendボタンが押された */
                else if (expectedNumber2.equals("waitEnd")) {
                    sendToBothSocket("gg", "gg");
                    isGameFinished = true;
                    System.exit(0);
                } /* client2にてwaitPanelのacceptボタンが押された */
                else if (expectedNumber2.equals("accept")) {
                    sendToBothSocket("ii", "ii");
                    sendToBothSocket(nextDigit, nextDigit);
                    latch = new CountDownLatch(2);
                    interrupted = true;
                    DIGIT = Integer.valueOf(nextDigit);
                    latch2.countDown();
                    return;
                } 
                else {
                    decideNextAction2(expectedNumber2);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    /* このメソッドでjudgeのDIGITと配列を初期化する */
    public void judgeInit() {
        judge.setDigit(DIGIT);
        judge.init();
    }

    
    /* -------------ソケットの初期化メソッド------------- */ 
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
        latch.countDown();
    }

    /* まず最初にDIGITをClient1に送信する
    * Client1はもらったDIGIT桁の数字を決めたあと
    * Serverに送信し返すのでそれをnumber1[]に格納する
    */
    public void decideFirstNumber() {
        try {
            firstThreadNumber = bufferedReader1.readLine();
            System.out.println("Player1 decided its Number: " + firstThreadNumber);

            /* ここでjudgeにnumber1にClient1の数字を読み込ませる */
            judge.setNumber1(firstThreadNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }
        latch.countDown();
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
        latch.countDown();;
    }

    /* まず最初にDIGITをClient2に送信する
    * Client2はもらったDIGIT桁の数字を決めたあと
    * Serverに送信し返すのでそれをnumber2[]に格納する
    */
    public void decideSecondNumber() {
        try {
            secondThreadNumber = bufferedReader2.readLine();
            System.out.println("Player2 decided its Number: " + secondThreadNumber);

            /* ここでjudgeにnumber2にClient2の数字を読み込ませる */
            judge.setNumber2(secondThreadNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }
        latch.countDown();
    }
    
    private void decideNextAction1(String result) {
        judgeResult1 = judge.startJudge(result, 2);
        eat1 = String.valueOf(judgeResult1.charAt(0));
        bite1 = String.valueOf(judgeResult1.charAt(1));

        if (eat1.equals(Integer.toString(DIGIT))) {

            sendToBothSocket("a" + bite1, "b" + bite1);
            endCondition = CHECKMATE;
        } else {
            sendToBothSocket(judgeResult1, "b" + bite1);
            endCondition = NOTCHECKMATE;
        }
    }

    private void decideNextAction2(String result) {
        judgeResult2 = judge.startJudge(expectedNumber2, 1);
        eat2 = String.valueOf(judgeResult2.charAt(0));
        bite2 = String.valueOf(judgeResult2.charAt(1));

        if (endCondition == NOTCHECKMATE) {
            if (eat2.equals(Integer.toString(DIGIT))) {
                sendToBothSocket("c" + bite2, "d" + bite2);
            } else {
                sendToBothSocket("b" + bite2, judgeResult2);
            }
        } else if (endCondition == CHECKMATE) {
            if (eat2.equals(Integer.toString(DIGIT))) {
                sendToBothSocket("e" + bite2, "e" + bite2);
            } else {
                sendToBothSocket("d" + bite2, "c" + bite2);

            }
        }
    }
       

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


    /* -------------mainメソッド------------- */
    public static void main(String[] args) {
        Server server = new Server();
    }
}