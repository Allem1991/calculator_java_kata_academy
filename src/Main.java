import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        String line = in.nextLine();
        System.out.println(calc(line));

        in.close();
    }

    public static String calc(String input) {

        String[] parts = input.trim().split(" ");

        //проверка, является ли входная строка математической формулой
        if (parts.length < 3) {
          throw new IndexOutOfBoundsException("строка не является математической операцией");
        } else if (parts.length > 3) {
            throw new IndexOutOfBoundsException("формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }


        String leftOperandStr = parts[0];
        String operator = parts[1];
        String rightOperandStr = parts[2];

        int leftOperand, rightOperand;
        boolean isRomanNumerals = false;

        //проверка, арабские или римские числа
        if (isArabic(leftOperandStr, rightOperandStr)) {
            leftOperand = Integer.parseInt(leftOperandStr);
            rightOperand = Integer.parseInt(rightOperandStr);
            if (!allowedValue(leftOperand, rightOperand))  {
                throw new NumberFormatException("Допустимые значения чисел от 1 до 10");
            }
        } else if (isRoman(leftOperandStr, rightOperandStr)) {
            leftOperand = RomanNumeral.valueOf(leftOperandStr).getArabicValue();
            rightOperand = RomanNumeral.valueOf(rightOperandStr).getArabicValue();
            if (!allowedValue(leftOperand, rightOperand))  {
                throw new NumberFormatException("Допустимые значения чисел от 1 до 10");
            }
            isRomanNumerals = true;
        } else {
            throw new IllegalArgumentException("используются одновременно разные системы счисления");
        }


        // расчет результата
        int result;
        switch (operator) {
            case "+":
                result = leftOperand + rightOperand;
                break;
            case "-":
                result = leftOperand - rightOperand;
                break;
            case "/":
                if (rightOperand == 0) {
                    throw new ArithmeticException("деление на ноль недопустимо");
                }
                result = leftOperand / rightOperand;
                break;
            case "*":
                result = leftOperand * rightOperand;
                break;
            default:
                throw new IllegalArgumentException("неизвестная операция");
        }

        // вывод результата
        String resultStr;
        if(isRomanNumerals) {
            if (result < 1) {
                throw new NumberFormatException("в римской системе нет отрицательных чисел");
            }
            resultStr = RomanNumeral.getRomanNumeralFromArabic(result).toString();
        } else {
            resultStr = Integer.toString(result);
        }



        return resultStr;

    }

    // проверка на выявление арабских чисел
    public static boolean isArabic(String leftOperandStr, String rightOperandStr) {

        try {
            int firstNum = Integer.parseInt(leftOperandStr);
            int secondNum = Integer.parseInt(rightOperandStr);
            return true;
        }catch (IllegalArgumentException e) {
            return false;
        }

    }

    // проверка арабских чисел на разрешенные значения (от 1 до 10)
    public static boolean allowedValue(int leftOperand, int rightOperand) {

        boolean firstNum = false;
        boolean secondNum = false;

        if (leftOperand >= 1 && leftOperand <= 10) {
            firstNum = true;
        }
        if (rightOperand >= 1 && rightOperand <= 10) {
            secondNum = true;
        }

        return firstNum && secondNum;
    }

    // проверка на выявление римских чисел
    public static boolean isRoman(String leftOperandStr, String rightOperandStr) {
        boolean firstNum = false;
        boolean secondNum = false;
        try {
            for (RomanNumeral numeral : RomanNumeral.values()) {
                if (RomanNumeral.valueOf(leftOperandStr) == numeral) {
                    firstNum = true;
                }
            }
            for (RomanNumeral numeral : RomanNumeral.values()) {
                if (RomanNumeral.valueOf(rightOperandStr) == numeral) {
                    secondNum = true;
                }
            }

        } catch (IllegalArgumentException e) {
            return false;
        }

        return firstNum && secondNum;
    }

    // перечисления римских цифр и эквивалентные значения на арабском исчеслении
    // также был создан словарь, для вытаскивания римских чисел по арабскому эквиваленту
enum RomanNumeral {
        I(1), II(2), III(3), IV(4), V(5), VI(6), VII(7), VIII(8), IX(9), X(10),
        XI(11), XII(12), XIII(13), XIV(14), XV(15), XVI(16), XVII(17), XVIII(18), XIX(19), XX(20),
        XXI(21), XXII(22), XXIII(23), XXIV(24), XXV(25), XXVI(26), XXVII(27), XXVIII(28), XXIX(29), XXX(30),
        XXXI(31), XXXII(32), XXXIII(33), XXXIV(34), XXXV(35), XXXVI(36), XXXVII(37), XXXVIII(38), XXXIX(39), XL(40),
        XLI(41), XLII(42), XLIII(43), XLIV(44), XLV(45), XLVI(46), XLVII(47), XLVIII(48), XLIX(49), L(50),
        LI(51), LII(52), LIII(53), LIV(54), LV(55), LVI(56), LVII(57), LVIII(58), LIX(59), LX(60),
        LXI(61), LXII(62), LXIII(63), LXIV(64), LXV(65), LXVI(66), LXVII(67), LXVIII(68), LXIX(69), LXX(70),
        LXXI(71), LXXII(72), LXXIII(73), LXXIV(74), LXXV(75), LXXVI(76), LXXVII(77), LXXVIII(78), LXXIX(79), LXXX(80),
        LXXXI(81), LXXXII(82), LXXXIII(83), LXXXIV(84), LXXXV(85), LXXXVI(86), LXXXVII(87), LXXXVIII(88), LXXXIX(89), XC(90),
        XCI(91), XCII(92), XCIII(93), XCIV(94), XCV(95), XCVI(96), XCVII(97), XCVIII(98), XCIX(99), C(100);
        private final int arabicValue;
        RomanNumeral(int arabicValue) {
            this.arabicValue = arabicValue;
        }

        public static final Map<Integer, RomanNumeral> arabicToRomanMap = new HashMap<>();

        static {
            for (RomanNumeral numeral: RomanNumeral.values()) {
                arabicToRomanMap.put(numeral.getArabicValue(), numeral);
            }
        }

        public static RomanNumeral getRomanNumeralFromArabic(int arabicValue) {
            return arabicToRomanMap.get(arabicValue);
        }


        public int getArabicValue() {
            return arabicValue;
        }
}

}
