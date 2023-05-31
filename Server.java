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
            game = new Game(SIZE);
            //In this method, Server receices both number1 and number2
            connect();
            System.out.println("EndPoint of decideSize");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void connect() {
        System.out.println("START OF CONNECT");
        connectFirstSocket();
        System.out.println("END OF CONNECT");
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
            System.out.println("Player1 decided its Number: " + tempNumber2);
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
            bufferedWriter1.write("GO");
            bufferedWriter1.newLine();
            bufferedWriter1.flush();
            bufferedWriter2.write("WAIT");
            bufferedWriter2.newLine();
            bufferedWriter2.flush();
            judge();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void judge() {
        
    }
    
    
    public static void main(String[] args) {
        Server server = new Server();
    }
}