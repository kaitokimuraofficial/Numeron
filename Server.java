import java.net.*;
import java.io.*;
import java.util.Scanner;


public class Server {

    private static String HOST = "127.0.0.1";
    private static int PORT = 1234;
    private static int SIZE;

    Game game;

    String tempNumber1;
    String tempNumber2;

    String expectNumber1To2;
    String expectNumber2To1;

    int[] expectNumber1;
    int[] expectNumber2;

    String judgeResult1To2;
    String judgeResult2To1;


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
    
    private int[] number1;
    private int[] number2;

    Scanner scanner = new Scanner(System.in);


    public Server() {
        System.out.println("Connecting...  HOST: " + HOST + " PORT: " + PORT);
        try {
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(HOST, PORT));
        } catch (Exception e) {
            e.printStackTrace();
        }
        start();
    }

    public void start() {
        decideSize();
    }


    public void decideSize() {
        System.out.print("Enter the Number: ");
        try {
            SIZE = Integer.parseInt(scanner.nextLine());
            System.out.println("SIZE is " + SIZE);
            number1 = new int[SIZE];
            number2 = new int[SIZE];
            expectNumber1 = new int[SIZE];
            expectNumber2 = new int[SIZE];

            game = new Game(SIZE);
            //In this method, Server receices both number1 and number2
            connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void connect() {
        System.out.println("START OF CONNECT");
        connectFirstSocket();
    }
    
    public void connectFirstSocket() {
        try {
            socket1 = serverSocket.accept();
            System.out.println("Connected 1st Socket!");
            inputStreamReader1 = new InputStreamReader(socket1.getInputStream());
            outputStreamWriter1 = new OutputStreamWriter(socket1.getOutputStream());
            bufferedReader1 = new BufferedReader(inputStreamReader1);
            bufferedWriter1 = new BufferedWriter(outputStreamWriter1);
            decideFirstNumber();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void decideFirstNumber() {
        try {
            bufferedWriter1.write(String.valueOf(SIZE));
            bufferedWriter1.newLine();
            bufferedWriter1.flush();
            System.out.println("I send " + SIZE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            tempNumber1 = bufferedReader1.readLine();
            System.out.println("Player1 decided its Number: " + tempNumber1);
            for (int i = 0; i < tempNumber1.length(); i++) {
                number1[i] = Character.getNumericValue(tempNumber1.charAt(i));
            }
            System.out.println("START CONNECTSECOND");
            connectSecondSocket();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
  
    public void connectSecondSocket() {
        try {
            socket2 = serverSocket.accept();
            System.out.println("Connected 2nd Socket!");
            inputStreamReader2 = new InputStreamReader(socket2.getInputStream());
            outputStreamWriter2 = new OutputStreamWriter(socket2.getOutputStream());
            bufferedReader2 = new BufferedReader(inputStreamReader2);
            bufferedWriter2 = new BufferedWriter(outputStreamWriter2);
            decideSecondNumber();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void decideSecondNumber() {
        try {
            bufferedWriter2.write(String.valueOf(SIZE));
            bufferedWriter2.newLine();
            bufferedWriter2.flush();
            System.out.println("I send " + SIZE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            tempNumber2 = bufferedReader2.readLine();
            System.out.println("Player2 decided its Number: " + tempNumber2);
            for (int i = 0; i < tempNumber2.length(); i++) {
                number2[i] = Character.getNumericValue(tempNumber2.charAt(i));
            }
            getStart();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void getStart() {
        try {
            sendToBothSocket("GO", "WAIT");
            //expectNumber1To2 is the same as expectNumber in Client1
            expectNumber1To2 = bufferedReader1.readLine();
            System.out.println("Now, I got expectNumber1To2");
            for (int i = 0; i < SIZE; i++) {
                expectNumber1[i] = Character.getNumericValue(expectNumber1To2.charAt(i));
            }
            judgeResult1To2 = game.calculateAll(expectNumber1, number2);

            sendToBothSocket(judgeResult1To2, judgeResult1To2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            sendToBothSocket("WAIT", "GO");
            expectNumber2To1 = bufferedReader2.readLine();
            for (int i = 0; i < SIZE; i++) {
                expectNumber2[i] = Character.getNumericValue(expectNumber2To1.charAt(i));
            }
            judgeResult2To1 = game.calculateAll(expectNumber2, number1);

            sendToBothSocket(judgeResult2To1, judgeResult2To1);      
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
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
    
    public static void main(String[] args) {
        Server server = new Server();
    }
}