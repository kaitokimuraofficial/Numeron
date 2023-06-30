public class Judge {

    Server server;

    private int DIGIT;

    private int[] number1;
    private int[] number2;

    private String biteInAnswer;
    private String eatInAnswer;

    char[] result1;
    char[] result2;
    

    /* 自分自身を持っているServerクラスのserverを持つ */
    public Judge(Server server) {
        this.server = server;
    }

    /* ^^^^^^^^^^^^各種メソッドまとめ^^^^^^^^^^^^ */

    public void init(int digit) {
        number1 = new int[digit];
        number2 = new int[digit];
        result1 = new char[digit];
        result2 = new char[digit];
    }
    

    /* ^^^^^^^^^^^^判定に使うメソッドまとめ^^^^^^^^^^^^ */
    /* -------------相手の数として予想したものとtargetClientを入力して判定してもらう------------ */
    public String startJudge(String inputNumber, int targetClientNumber) {
        if (targetClientNumber == 1) {
            return calculateAll(inputNumber, number1);
        }
        else {
            return calculateAll(inputNumber, number2);
        }
    }

    /* -------------EatとBiteの数を調べてまとめて返す------------ */
    public String calculateAll(String inputNumber, int[] targetNumber) {
        String answer;
        eatInAnswer = Integer.toString(calculateEat(inputNumber, targetNumber));
        biteInAnswer = Integer.toString(calculateBite(inputNumber, targetNumber)-calculateEat(inputNumber, targetNumber));

        answer = eatInAnswer + biteInAnswer;

        return answer;
    }

    /* -------------Eatの数を調べる------------- */
    public int calculateEat(String inputNumber, int[] correctNumber) {
        int eat = 0;
        int[] expectedNumber = new int[DIGIT];

        for (int i = 0; i < DIGIT; i++) {
            char digitChar = inputNumber.charAt(i);
            int digit = Character.getNumericValue(digitChar);
            expectedNumber[i] = digit;
        }

        for (int i = 0; i < DIGIT; i++) {
            if (expectedNumber[i] == correctNumber[i]) {
                eat += 1;
            }
        }

        return eat;
    }
    
    /* -------------Biteの数を調べる------------- */
    public int calculateBite(String inputNumber, int[] correctNumber) {
        int bite = 0;
        
        int[] expectedNumber = new int[DIGIT];

        for (int i = 0; i < DIGIT; i++) {
            char digitChar = inputNumber.charAt(i);
            int digit = Character.getNumericValue(digitChar);
            expectedNumber[i] = digit;
        }

        for (int i = 0; i < DIGIT; i++) {
            for (int j = 0; j < DIGIT; j++) {
                if (expectedNumber[i] == correctNumber[j]) {
                    bite += 1;
                    continue;
                }
            }
        }
        return bite;
    }


    /* ^^^^^^^^^^^^getメソッド^^^^^^^^^^^^ */

    public int[] getNumber1() {
        return number1;
    }

    public int[] getNumber2() {
        return number2;
    }


    /* ^^^^^^^^^^^^setメソッド^^^^^^^^^^^^ */

    public void setDigit(int digit) {
        DIGIT = digit;
    }

    public void setNumber1(String tempNumber1) {
        for (int i = 0; i < DIGIT; i++) {
            number1[i] = Character.getNumericValue(tempNumber1.charAt(i));
        }
    }

    public void setNumber2(String tempNumber2) {
        for (int i = 0; i < DIGIT; i++) {
            number2[i] = Character.getNumericValue(tempNumber2.charAt(i));
        }
    }
}