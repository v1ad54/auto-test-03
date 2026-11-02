package ru.v1ad54;

public class LogicTasks {

    /**
     * Метод возвращает true, если число четное и false — если нечётное.
     *
     * @param n - проверяемое число
     * @return - true, если число четное, иначе false
     */
    public static boolean isEven(int n) {
        return n % 2 == 0;
    }

    /**
     * Метод возвращает Allowed, если age больше 18, иначе - Denied
     *
     * @param age - возраст
     * @return - Allowed, если age больше 18, иначе - Denied
     */
    public static String checkAccess(int age) {
        return (age > 18) ? "Allowed" : "Denied";
    }

    /**
     * Метод проверяет знак через тернарный оператор:
     * возвращает true, если число больше или равно 0 и false, если меньше
     *
     * @param n - проверяемое число
     * @return - возвращает true, если число больше или равно 0 и false, если меньше
     */
    public static boolean isPositive(int n) {
        return (n >= 0) ? true : false; // Тут тернарный оператор не нужен, но таково задание...
    }

    /**
     * Метод преобразует баллы (0-100) в символы оценки (A, B, C, D, E)
     * Если переданное число не входит в границы — вернуть строку Error.
     *
     * @param score - баллы от 0 до 100
     * @return - строковая оценка
     */
    public static String getGrade(int score) {
        if (score < 0 || score > 100) {
//            throw new IllegalArgumentException("Баллы должны быть в диапазоне от 0 до 100");
            return "Error";
        }

        if (score <= 20) {
            return "E";
        } else if (score <= 40) {
            return "D";
        } else if (score <= 60) {
            return "C";
        } else if (score <= 80) {
            return "B";
        } else {
            return "A";
        }
    }
}
