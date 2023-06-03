import java.net.*;
import java.io.*;


public class Client {
    private static String HOST = "127.0.0.1";
    private static int PORT = 1234;
    private static int SIZE;
    String tempSIZE;
    String tempNumber;
    String receiveAnswer1;
    String receiveAnswer2;

    String Turn;
    String wait;
    String expectNumber;
    String result;

    private Socket socket;
    private InputStreamReader inputStreamReader;
    private OutputStreamWriter outputStreamWriter;

    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    private int[] number;
    
    Frame frame;
    
    public static void main(String[] args) {
        Client client = new Client();
    }

    public Client() {
        try {
            socket = new Socket(HOST, PORT);
            inputStreamReader = new InputStreamReader(socket.getInputStream());
            outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
            bufferedReader = new BufferedReader(inputStreamReader);
            bufferedWriter = new BufferedWriter(outputStreamWriter);
            frame = new Frame();
            start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() {
        decideNumber();
    }

    public void decideNumber() {
        try {
            SIZE = Integer.valueOf(bufferedReader.readLine());
            System.out.println(SIZE);
            number = new int[SIZE];
            System.out.println("SIZE is " + SIZE);
            receiveNumber();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void receiveNumber() {
        while (tempNumber == null) {
            tempNumber = frame.getNumber();
        }
        System.out.println("Number is " + tempNumber);
        try {
            bufferedWriter.write(tempNumber);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < SIZE; i++) {
            number[i] = Character.getNumericValue(tempNumber.charAt(i));
        }

        waitGo();
    }
    
    public void waitGo() {
        try {
            Turn = bufferedReader.readLine();
            frame.changeIntoMainPanel(Turn);
        } catch (Exception e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                if (Turn == "GO") {
                    wait = frame.getWait();
                    while (wait == null) {
                        wait = frame.getWait();
                    }
                    //Send the number that client expects to the Server
                    expectNumber = frame.getExpectNumber();
                    System.out.println("SEND IT");
                    bufferedWriter.write(expectNumber);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();

                    String temp = bufferedReader.readLine();
                    frame.update(temp);
                    Turn = "WAIT";
                }
                else {
                    result = bufferedReader.readLine();
                    String temp = bufferedReader.readLine();
                    frame.update2(temp);
                    Turn = "GO";
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
