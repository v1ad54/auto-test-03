package ru.v1ad54;

public class LoopTasks {

    /**
     * Метод принимает стартовое число (например 5) и возвращает строку со всеми числами до 1
     * и словом "Поехали!" в конце (например, "5 4 3 2 1 Поехали!")
     * @param start - стартовое число
     * @return - строка
     */
    public static String blastOff(int start){
        StringBuilder resultBuilder = new StringBuilder();

        for (int i = start; i > 0 ; i--){
            resultBuilder.append(i).append(" ");
        }
        resultBuilder.append("Поехали!");

        return resultBuilder.toString();
    }

    /**
     * Метод возвращает сумму всех целых чисел от 1 до n
     * @param n - количество чисел
     * @return - сумма всех целых чисел от 1 до n
     */
    public static int sumToN(int n){
        long result = 0;

        for (int i = 1; i <= n ; i ++) {
            result += i;
        }

        if (result > Integer.MAX_VALUE) {
            throw new ArithmeticException("Результат суммы всех чисел превышает максимальное значение типа int");
        }

        return (int)result;
    }

    /**
     * Метод принимает массив строк и возвращает true, если хотя бы одна строка равна Bug
     * @param messages - массив строк
     * @return - true, если хотя бы одна строка равна Bug или false в противном случае
     */
    public static boolean hasBug(String[] messages){
        if (messages == null) {
            throw new NullPointerException("Массив не должен быть null");
        }

        for (String item: messages){
            if ("Bug".equals(item)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Метод принимает границы диапазона и возвращает строку,
     * состоящую только из четных чисел внутри этого промежутка
     * @param start - начало диапазона
     * @param end - конец диапазона
     * @return - строка четных чисел
     */
    public static String getEvenInRange(int start, int end){
        StringBuilder resultBuilder = new StringBuilder();

        for (int i = start ; i <= end ; i++){
            if (i % 2 == 0) {
                resultBuilder.append(i).append(" ");
            }
        }

        return resultBuilder.toString().trim();
    }
}
