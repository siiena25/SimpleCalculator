import java.util.*;

interface operation { //интерфейс с операциями над действительными числами
    Integer add(Integer firstNumber, Integer secondNumber);
    Integer sub(Integer firstNumber, Integer secondNumber);
    Integer mul(Integer firstNumber, Integer secondNumber);
    Integer div(Integer firstNumber, Integer secondNumber);
}

interface moduleOperation { //интерфейс с операциями над числами по модулю N
    Integer add(Integer firstNumber, Integer secondNumber, Integer moduleNumber);
    Integer sub(Integer firstNumber, Integer secondNumber, Integer moduleNumber);
    Integer mul(Integer firstNumber, Integer secondNumber, Integer moduleNumber);
    Integer div(Integer firstNumber, Integer secondNumber, Integer moduleNumber);
}

interface complexOperation { //интерфейс с операциями над комплексными числами
    ComplexNumber add(ComplexNumber firstNumber, ComplexNumber secondNumber);
    ComplexNumber sub(ComplexNumber firstNumber, ComplexNumber secondNumber);
    ComplexNumber mul(ComplexNumber firstNumber, ComplexNumber secondNumber);
    ComplexNumber div(ComplexNumber firstNumber, ComplexNumber secondNumber);
}

interface quaternionOperation { //интерфейс с операциями над кватернионами
    Quaternion add(Quaternion firstNumber, Quaternion secondNumber);
    Quaternion sub(Quaternion firstNumber, Quaternion secondNumber);
    Quaternion mul(Quaternion firstNumber, Quaternion secondNumber);
    Quaternion div(Quaternion firstNumber, Quaternion secondNumber);
}

class CalcNumbers { //класс - калькулятор всех видов чисел
    public Integer getValue(Integer firstNumber, Integer secondNumber, int operationForNumbers, operation op) { // получения значения для действительных чисел
        return switch (operationForNumbers) {
            case 1 -> op.add(firstNumber, secondNumber);
            case 2 -> op.sub(firstNumber, secondNumber);
            case 3 -> op.mul(firstNumber, secondNumber);
            case 4 -> op.div(firstNumber, secondNumber);
            default -> null;
        };
    }

    public Integer getModuleValue(Integer firstNumber, Integer secondNumber, Integer moduleNumber, int operationForNumbers, moduleOperation op) { // получения значения для чисел по модулю N
        return switch (operationForNumbers) {
            case 1 -> op.add(firstNumber, secondNumber, moduleNumber);
            case 2 -> op.sub(firstNumber, secondNumber, moduleNumber);
            case 3 -> op.mul(firstNumber, secondNumber, moduleNumber);
            case 4 -> op.div(firstNumber, secondNumber, moduleNumber);
            default -> null;
        };
    }

    public ComplexNumber getComplexValue(ComplexNumber firstNumber, ComplexNumber secondNumber, int operationForNumbers, complexOperation op) { // получения значения для комплексных чисел
        return switch (operationForNumbers) {
            case 1 -> op.add(firstNumber, secondNumber);
            case 2 -> op.sub(firstNumber, secondNumber);
            case 3 -> op.mul(firstNumber, secondNumber);
            case 4 -> op.div(firstNumber, secondNumber);
            default -> null;
        };
    }

    public Quaternion getQuaternionValue(Quaternion firstNumber, Quaternion secondNumber, int operationForNumbers, quaternionOperation op) { // получения значения для кватернионов
        return switch (operationForNumbers) {
            case 1 -> op.add(firstNumber, secondNumber);
            case 2 -> op.sub(firstNumber, secondNumber);
            case 3 -> op.mul(firstNumber, secondNumber);
            case 4 -> op.div(firstNumber, secondNumber);
            default -> null;
        };
    }
}

class ComplexNumber { // класс комплексного числа
    private final double re;
    private final double im;

    public ComplexNumber(double re, double im) { // конструктор
        this.re = re;
        this.im = im;
    }

    public double getRe() { // получить действительную часть числа
        return re;
    }

    public double getIm() { // получить мнимую часть числа
        return im;
    }

    public static ComplexNumber sum(ComplexNumber cn1, ComplexNumber cn2) { // вычисление суммы
        return new ComplexNumber(cn1.getRe() + cn2.getRe(), cn1.getIm() + cn2.getIm());
    }

    public static ComplexNumber multiply(ComplexNumber cn1, ComplexNumber cn2) { // вычисление произведения
        return new ComplexNumber(cn1.getRe() * cn2.getRe() - cn1.getIm() * cn2.getIm(), cn1.getRe() * cn2.getIm() + cn1.getIm() * cn2.getRe());
    }

    public static ComplexNumber subtract(ComplexNumber cn1, ComplexNumber cn2) { // вычисление вычитания
        return new ComplexNumber(cn1.getRe() - cn2.getRe(), cn1.getIm() - cn2.getIm());
    }

    public static ComplexNumber divide(ComplexNumber cn1, ComplexNumber cn2) {  // вычисление деления
        ComplexNumber temp = new ComplexNumber(cn2.getRe(), (-1) * cn2.getIm());
        temp = ComplexNumber.multiply(cn1, temp);
        double denominator = cn2.getRe() * cn2.getRe() + cn2.getIm() * cn2.getIm();
        return new ComplexNumber(temp.getRe() / denominator, temp.getIm() / denominator);
    }

    private String sign() { //вычисление знака
        if (im > 0) return " + ";
        else return " - ";
    }

    @Override
    public String toString() { //переопределение метода для вывода
        String string;
        if (im == 1 || im == -1) {
            if (re == 0) {
                string = sign() + "i";
            } else {
                string = re + sign() + "i";
            }
        } else {
            string = re + sign() + Math.abs(im) + "i";
        }
        return string;
    }

    @Override
    public boolean equals(Object obj) {
        return this.getClass() == obj.getClass();
    }
}

class Quaternion { // класс кватерниона
    private final double a, b, c, d; // коэффициенты кватерниона q = a + bi + cj + dk

    public Quaternion(double a, double b, double c, double d) { // конструктор
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public Quaternion conjug() { //сопряжение
        return new Quaternion(a, -b, -c, -d);
    }
    public double abs() { // квадрат
        return Math.sqrt(a * a  + b * b + c * c + d * d);
    }
    public Quaternion inverse() throws ArithmeticException { //обращение умножения (деление)
        return Quaternion.divide(this.conjug(), this.abs() * this.abs());
    }
    public boolean isZero() { // равен ли нулю
        return (a == 0 && b == 0 && c == 0 && d == 0);
    }

    public static Quaternion sum(Quaternion a, Quaternion b) { // сумма
        return new Quaternion(a.a + b.a, a.b + b.b, a.c + b.c, a.d + b.d);
    }
    public static Quaternion sub(Quaternion a, Quaternion b) { // вычитание
        return new Quaternion(a.a - b.a, a.b - b.b, a.c - b.c, a.d - b.d);
    }

    public static Quaternion mul(Quaternion a, Quaternion b) { // умножение
        return new Quaternion(a.a * b.a - a.b * b.b - a.c * b.c - a.d * b.d,
                a.a * b.b + a.b * b.a + a.c * b.d - a.d * b.c,
                a.a * b.c - a.b * b.d + a.c * b.a + a.d * b.b,
                a.a * b.d + a.b * b.c - a.c * b.b + a.d * b.a);
    }
    public static Quaternion divide(Quaternion a, double divider) throws ArithmeticException { // деление на число
        if (divider == 0) throw new ArithmeticException("Zero divider");
        return new Quaternion(a.a / divider, a.b / divider, a.c / divider, a.d / divider);
    }
    public static Quaternion divide(Quaternion a, Quaternion b) throws ArithmeticException { // деление на кватернион
        if (b.isZero()) throw new ArithmeticException("Zero divider");
        return Quaternion.mul(a, b.inverse());
    }

    public String toString() { //метод для вывода
        return a +
                ((b < 0) ? " - " : " + ") + Math.abs(b) + "*i" +
                ((c < 0) ? " - " : " + ") + Math.abs(c) + "*j" +
                ((d < 0) ? " - " : " + ") + Math.abs(d) + "*k\n";
    }
}

public class Task {
    static Scanner in = new Scanner(System.in);
    private static CalcNumbers calc;
    private static int firstNumber;
    private static int secondNumber;
    private static Integer moduleNumber;
    private static int operationForNumbers;

    public static ComplexNumber getComplexNumber(String str) { // функция для получения действительной и мнимой частей комплексного числа из строки
        int indOfSign = -1;
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                indOfSign = i;
                break;
            }
        }
        double re = Double.parseDouble(str.substring(0, indOfSign));
        double im = Double.parseDouble(str.substring(indOfSign, str.length() - 1));
        System.out.println("Re: " + re + "; Im: " + im);
        return new ComplexNumber(re, im);
    }

    private static Integer getValue() {
        return calc.getValue(firstNumber, secondNumber, operationForNumbers, new operation() {
            public Integer add(Integer a, Integer b) {
                return a + b;
            }

            public Integer sub(Integer a, Integer b) {
                return a - b;
            }

            public Integer mul(Integer a, Integer b) {
                return a * b;
            }

            public Integer div(Integer a, Integer b) {
                return a / b;
            }
        });
    }

    private static Integer getModuleValue() {
        return calc.getModuleValue(firstNumber, secondNumber, moduleNumber, operationForNumbers, new moduleOperation() {
            public Integer add(Integer a, Integer b, Integer m) {
                return ((a % m) + (b % m)) % m;
            }

            public Integer sub(Integer a, Integer b, Integer m) {
                return ((a % m) - (b % m)) % m;
            }

            public Integer mul(Integer a, Integer b, Integer m) {
                return ((a % m) * (b % m)) % m;
            }

            public Integer div(Integer a, Integer b, Integer m) {
                return ((a % m) / (b % m)) % m;
            }
        });
    }

    private static ComplexNumber getComplexValue(ComplexNumber firstComplexNumber, ComplexNumber secondComplexNumber) {
        return calc.getComplexValue(firstComplexNumber, secondComplexNumber, operationForNumbers, new complexOperation() {
            public ComplexNumber add(ComplexNumber a, ComplexNumber b) {
                return ComplexNumber.sum(a, b);
            }

            public ComplexNumber sub(ComplexNumber a, ComplexNumber b) {
                return ComplexNumber.subtract(a, b);
            }

            public ComplexNumber mul(ComplexNumber a, ComplexNumber b) {
                return ComplexNumber.multiply(a, b);
            }

            public ComplexNumber div(ComplexNumber a, ComplexNumber b) {
                return ComplexNumber.divide(a, b);
            }
        });
    }

    private static Quaternion getQuaternionValue(Quaternion firstQuaternion, Quaternion secondQuaternion) {
        return calc.getQuaternionValue(firstQuaternion, secondQuaternion, operationForNumbers, new quaternionOperation() {
            public Quaternion add(Quaternion a, Quaternion b) {
                return Quaternion.sum(a, b);
            }

            public Quaternion sub(Quaternion a, Quaternion b) {
                return Quaternion.sub(a, b);
            }

            public Quaternion mul(Quaternion a, Quaternion b) {
                return Quaternion.mul(a, b);
            }

            public Quaternion div(Quaternion a, Quaternion b) {
                return Quaternion.divide(a, b);
            }
        });
    }

    private static void calculateRealNumbers() { // функция калькулятора для действ чисел
        System.out.println("Введите первое число: ");
        firstNumber = in.nextInt();
        System.out.println("Введите второе число: ");
        secondNumber = in.nextInt();
        System.out.println("Введите ключевое слово, чтобы выполнить: ");
        System.out.println("1 - сложение");
        System.out.println("2 - вычитание");
        System.out.println("3 - умножение");
        System.out.println("4 - деление");
        operationForNumbers = in.nextInt();
        moduleNumber = null;
        Integer total = getValue();
        System.out.println("Ответ: " + total);
    }

    private static void calculateModuleNumbers() { // фукнция калькулятора для чисел по модулю N
        System.out.println("Введите первое число: ");
        firstNumber = in.nextInt();
        System.out.println("Введите второе число: ");
        secondNumber = in.nextInt();
        System.out.println("Введите ключевое слово, чтобы выполнить: ");
        System.out.println("1 - сложение");
        System.out.println("2 - вычитание");
        System.out.println("3 - умножение");
        System.out.println("4 - деление");
        operationForNumbers = in.nextInt();
        System.out.println("Введите модуль: ");
        moduleNumber = in.nextInt();
        boolean isPrime = true;
        int temp;
        for (int i = 2; i <= moduleNumber / 2; i++) {
            temp = moduleNumber % i;
            if (temp == 0) {
                isPrime = false;
                break;
            }
        }
        if (isPrime) {
            Integer total = getModuleValue();
            System.out.println("Ответ: " + total);
        }
        else {
            System.out.println("Ошибка! Модуль должен быть простым числом.");
        }
    }

    private static void calculateComplexNumbers() { // функция калькулятора для комплексных чисел
        in.nextLine();
        System.out.println("Введите первое число в виде \"действительная часть + комплексная часть\" (например, 1+2i) (если какая-то часть равна нулю, то пишем число как \"0+2i\" или \"1+0i\"): ");
        String firstComplexNumber = in.nextLine();
        System.out.println("Введите второе число в виде \"действительная часть + комплексная часть\" (например, 1+2i) (если какая-то часть равна нулю, то пишем число как \"0+2i\" или \"1+0i\"): ");
        String secondComplexNumber = in.nextLine();
        System.out.println("Введите ключевое слово, чтобы выполнить: ");
        System.out.println("1 - сложение");
        System.out.println("2 - вычитание");
        System.out.println("3 - умножение");
        System.out.println("4 - деление");
        operationForNumbers = in.nextInt();
        moduleNumber = null;
        ComplexNumber firstCN = getComplexNumber(firstComplexNumber);
        ComplexNumber secondCN = getComplexNumber(secondComplexNumber);
        ComplexNumber total = getComplexValue(firstCN, secondCN);
        System.out.println(total.toString());
    }

    private static void calculateQuaternionNumbers() { // функция калькулятора для кватернионов
        System.out.println("Введите последовательно коэффициенты a, b, c, d первого кватерниона q = a + bi + cj + dk: ");
        int firstA = in.nextInt();
        int firstB = in.nextInt();
        int firstC = in.nextInt();
        int firstD = in.nextInt();
        Quaternion firstQuaternion = new Quaternion(firstA, firstB, firstC, firstD);

        System.out.println("Введите последовательно коэффициенты a, b, c, d второго кватерниона q = a + bi + cj + dk: ");
        int secondA = in.nextInt();
        int secondB = in.nextInt();
        int secondC = in.nextInt();
        int secondD = in.nextInt();
        Quaternion secondQuaternion = new Quaternion(secondA, secondB, secondC, secondD);

        System.out.println("Введите ключевое слово, чтобы выполнить: ");
        System.out.println("1 - сложение");
        System.out.println("2 - вычитание");
        System.out.println("3 - умножение");
        System.out.println("4 - деление");
        operationForNumbers = in.nextInt();

        Quaternion total = getQuaternionValue(firstQuaternion, secondQuaternion);
        System.out.println(total.toString());
    }

    public static void main(String[] args) {
        boolean isContinue = true;
        while (isContinue) {
            System.out.println("\nВведите цифру, если вы хотите выполнить операцию над: ");
            System.out.println("1 - действительными числами");
            System.out.println("2 - комплексными числами");
            System.out.println("3 - кватернионами");
            System.out.println("4 - числами по модулю N");
            System.out.println("5 - закончить выполнение программы");
            int numberOfOperation = in.nextInt();
            calc = new CalcNumbers();

            switch (numberOfOperation) {
                case 1 -> calculateRealNumbers();
                case 2 -> calculateComplexNumbers();
                case 3 -> calculateQuaternionNumbers();
                case 4 -> calculateModuleNumbers();
                case 5 -> isContinue = false;
            }
        }
    }
}