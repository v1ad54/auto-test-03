package ru.v1ad54;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.LongStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

/**
 * Тестовый класс - домашнее задание к теме Assertion
 * <p>
 * Часть: ЛЕКЦИЯ
 * Задача 1: написать 4 автотеста, использующих в работе ассерты. Методы, которые будут проверяться автотестами,
 * можно написать самостоятельно или взять из предыдущих заданий. Требования к методам — хотя бы один метод:
 * - возвращает булево значение;
 * - должен возвращать список;
 * - должен вернуть неверное значение (ассерт должен падать).
 * При падении ассерты должны дать информацию, что ожидалось и что было получено в результате падения.
 */
public class LectureTest {

    /**
     * Тест метода {@link LogicTasks#isEven(int)}
     * Для положительных чисел метод isEven должны возвращать true, для отрицательных false
     */
    @Test
    @DisplayName("Тест метода isEven")
    void isEvenTest() {
        Random random = new Random();
        int checkedValue;
        boolean expectedResult;

        // Четные значения
        checkedValue = random.nextInt(0, 100) * 2;
        expectedResult = true;
        assertThat(LogicTasks.isEven(checkedValue))
                .as("isEven для " + checkedValue + " должен быть " + expectedResult)
                .isTrue();

        // Не четные значения
        checkedValue = random.nextInt(0, 100) * 2 + 1;
        expectedResult = true;
        assertThat(LogicTasks.isEven(checkedValue))
                .as("isEven для " + checkedValue + " должен быть " + expectedResult)
                .isFalse();
    }

    /**
     * Тест метода {@link ArrayTasks#removeSpecificName(List, String)}
     * Метод removeSpecificName принимает список и имя, которое нужно исключить.
     * Возвращает новый список, не содержащий указанного имени
     */
    @Test
    @DisplayName("Тест метода removeSpecificName")
    void removeSpecificNameTest() {
        Random random = new Random();
        List<String> list = new ArrayList<>();
        List<String> expectedList;
        String nameToRemove;

        // Создаем список случайных имен
        for (int i = 0; i < 10; i++) {
            list.add("Name-" + random.nextInt(1, 100));
        }

        nameToRemove = list.get(random.nextInt(list.size())); // Выбираем в списке имен имя, которое нужно исключить из списка
        String nameToRemove1 = nameToRemove;
        expectedList = list.stream().filter(name -> !name.equals(nameToRemove1)).toList();
        assertThat(ArrayTasks.removeSpecificName(list, nameToRemove))
                .as("Из списка " + list + " должно быть удалено имя " + nameToRemove)
                .isEqualTo(expectedList);

        // имя для удаления отсутствует в списке
        nameToRemove = "Такого_имени_точно_нет_в_списке";
        assertThat(ArrayTasks.removeSpecificName(list, nameToRemove))
                .as("В случае если удаляемого имени нет в списке, список должен остаться прежним.")
                .isEqualTo(list);

        // исключение NullPointerException при передаче null в параметр array
        assertThatThrownBy(() -> ArrayTasks.removeSpecificName(null, "Name-" + random.nextInt(1, 100)))
                .as("В случае передачи null списка, должен возникать NullPointerException")
                .isInstanceOf(NullPointerException.class);

        // исключение IllegalArgumentException при передаче null в параметр nameToRemove
        assertThatThrownBy(() -> ArrayTasks.removeSpecificName(list, null))
                .as("В случае передачи null в параметр nameToRemove, должен возникать IllegalArgumentException")
                .isInstanceOf(IllegalArgumentException.class);

        // исключение IllegalArgumentException, если элемент списка равен null
        list.add(null); // Добавим в список null-элемент
        String nameToRemove2 = nameToRemove;
        assertThatThrownBy(() -> ArrayTasks.removeSpecificName(list, nameToRemove2))
                .as("В случае если в списке будет null, должен возникать IllegalArgumentException")
                .isInstanceOf(IllegalArgumentException.class);
    }

    /**
     * Тест метода {@link ArrayTasks#calcAverage(List)}
     * Метод calcAverage вычисляет и возвращает среднее арифметическое всех чисел в списке
     */
    @Test
    @DisplayName("Тест метода calcAverage")
    void calcAverageTest() {
        Random random = new Random();
        List<Integer> list = new ArrayList<>();
        int expectedResult;

        // просто список чисел, заодно проверка на деление с потерей дробной части (целочисленный int)
        for (int i = 0; i < 10; i++) {
            list.add(random.nextInt(1, 100));
        }
        expectedResult = (int) list.stream().mapToInt(Integer::intValue).average().orElse(0.0);
        assertThat(ArrayTasks.calcAverage(list))
                .as("Среднеарифметическое из " + list + " должно быть " + expectedResult)
                .isEqualTo(expectedResult);
    }

    /**
     * Тест метода {@link LoopTasks#sumToN(int)}
     * Метод возвращает сумму всех целых чисел от 1 до n
     *
     * При проверке
     */
    @Test
    @DisplayName("Тест метода sumToN")
    void badTest() {
        Random random = new Random();

        // положительные числа
        int value = random.nextInt(2, 10);
        long sum = LongStream.rangeClosed(1, value).sum();
        assertThat(LoopTasks.sumToN(value))
                .as("Сумма от 1 до " + value + " должна быть равна " + sum)
                .isEqualTo((int) sum);

        // отрицательные числа
        value = -random.nextInt(2, 10);
        assertThat(LoopTasks.sumToN(value))
                .as("Сумма от 1 до " + value + " должна быть равна 0")
                .isEqualTo(0);

        // Bad тест - проверка суммы чисел от 1 до 0 должна быть 0, а не 1!
        assertThat(LoopTasks.sumToN(0))
                .as("Сумма от 1 до 0 должна быть равна 0")
                .isEqualTo(1);

    }

}
