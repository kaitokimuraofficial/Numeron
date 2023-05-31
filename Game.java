//This class works as judge.

public class Game {

    private static int SIZE;

    private static int TURN;

    public Game(int SIZE) {
        TURN = 1;
        SIZE = this.SIZE;
    }

    public int getTurn() {
        return TURN;
    }

    private void changeTurn() {
        TURN ^= 1;
    }
    

    public int[] calculateAll(int[] number1, int[] number2) {
        int answer[] = new int[2];
        answer[0] = calculateEat(number1, number2);
        answer[1] = calculateBite(number1, number2);
        
        return answer;
    }

    public int calculateEat(int[] number1, int[] number2) {
        int Eat = 0;
        for(int i=0; i<SIZE; i++){
            if (number1[i] == number2[i]) {
                Eat += 1;
            }
        }
        return Eat;
    }
//Expect that all number is different right now::2023/5/30
    public int calculateBite(int[] number1, int[] number2) {
        int Bite = 0;
        for(int i=0; i<SIZE; i++){
            for (int j = i; i < SIZE; j++) {
                if (number1[i] == number2[j] && i == j) {
                    break;
                }
                if (number1[i] == number2[j]) {
                    Bite += 1;
                    break;
                }
            }
        }
        return Bite;
    }
}