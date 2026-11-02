package ru.v1ad54;

import java.util.ArrayList;
import java.util.List;

public class ArrayTasks {

    /**
     * Метод находит и возвращает самое большое число в переданном массиве
     *
     * @param arr - массив целых чисел
     * @return - максимальное число в массиве arr
     */
    public static int findMax(int[] arr) {
        if (arr == null) {
            throw new NullPointerException("Массив не должен быть null");
        }

        if (arr.length == 0) {
            throw new IllegalArgumentException("Массив не должен быть пустым");
        }

        int result = arr[0];
        for (int item : arr) {
            result = (result < item) ? item : result;
        }

        return result;
    }

    /**
     * Метод возвращает новый массив в котором элементы исходного массива расположены в обратном порядке
     *
     * @param arr - исходный массив строк
     * @return - массив строк в обратном порядке
     */
    public static String[] reverse(String[] arr) {
        if (arr == null) {
            throw new NullPointerException("Массив не должен быть null");
        }

        String[] result = new String[arr.length];

        for (int i = 0; i < arr.length; i++) {
            result[i] = arr[arr.length - 1 - i];
        }

        return result;
    }

    /**
     * Метод вычисляет и возвращает среднее арифметическое всех чисел в списке
     *
     * @param list - список чисел
     * @return - среднее арифметическое всех чисел списка
     */
    public static int calcAverage(List<Integer> list) {
        if (list == null) {
            throw new NullPointerException("Список не должен быть null");
        }

        if (list.isEmpty()) {
            throw new IllegalArgumentException("Список не должен быть пустым");
        }

        Integer summa = 0;
        for (Integer item : list) {
            if (item != null) {
                summa += item;
            } else {
                throw new IllegalArgumentException("Элементы списка не должны быть null");
            }
        }

        return summa / list.size();
    }

    /**
     * Метод принимает список и имя, которое нужно исключить. Возвращает новый список, не содержащий указанного имени
     *
     * @param list         - список имен
     * @param nameToRemove - имя которое нужно исключить из списка
     * @return - новый список без исключаемого имени
     */
    public static List<String> removeSpecificName(List<String> list, String nameToRemove) {
        if (list == null) {
            throw new NullPointerException("Список не должен быть null");
        }

        if (nameToRemove == null) {
            throw new IllegalArgumentException("Имя которое нужно исключить из списка не должно быть null");
        }

        List<String> result = new ArrayList<>();

        for (String item : list) {
            if (item == null) {
                throw new IllegalArgumentException("Элемент списка не должен быть null");
            }

            if (!item.equals(nameToRemove)) {
                result.add(item);
            }
        }

        return result;
    }
}
