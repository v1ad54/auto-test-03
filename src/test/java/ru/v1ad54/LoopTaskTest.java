package ru.v1ad54;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.*;
//import static ru.v1ad54.MyAssertions.*;


/**
 * Структура тестовых данных для параметризованного теста {@link LoopTasksTest#blastOffParametrizedTest(BlastOffTestData)}
 *
 * @param start          - стартовое число для параметра start
 * @param expectedResult - ожидаемый результат метода blastOff
 * @param assertMessage  - сообщение выводимое в случае fail
 */
record BlastOffTestData(
        int start,
        String expectedResult
        , String assertMessage
) {
}

/**
 * Структура тестовых данных для параметризованного теста {@link LoopTasksTest#sumToNParametrizedTest(SumToNTestData)}
 *
 * @param n                 - количество чисел для параметра n
 * @param expectedResult    - ожидаемый результат метода sumToN
 * @param expectedException - ожидаемое исключение при выполнении метода sumToN
 * @param assertMessage  - сообщение выводимое в случае fail
 */
record SumToNTestData(
        int n,
        int expectedResult,
        Class expectedException
        , String assertMessage
) {
}

/**
 * Структура тестовых данных для параметризованного теста {@link LoopTasksTest#hasBugParametrizedTest(HasBugTestData)}
 *
 * @param messages          - количество чисел для параметра messages
 * @param expectedResult    - ожидаемый результат метода hasBug
 * @param expectedException - ожидаемое исключение при выполнении метода hasBug
 * @param assertMessage  - сообщение выводимое в случае fail
 */
record HasBugTestData(
        String[] messages,
        boolean expectedResult,
        Class expectedException
        , String assertMessage
) {
}

/**
 * Структура тестовых данных для параметризованного теста {@link LoopTasksTest#getEvenInRangeParametrizedTest(GetEvenInRangeTestData)}
 *
 * @param start - начало диапазона для параметра start
 * @param end   - конец диапазона для параметра end
 * @param assertMessage  - сообщение выводимое в случае fail
 */
record GetEvenInRangeTestData(
        int start,
        int end,
        String expectedResult
        , String assertMessage
) {
}


class LoopTasksTest {

    /**
     * Бредогенератор
     *
     * @return - возвращает список случайного количества случайно нагенерированных строк
     */
    private static List<String> nonsenseGenerator() {
        List<String> result = new ArrayList<>();

        // Алфавит, из которого будут строиться слова
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        Random random = new Random();

        // Генерим случайное количество строк в списке (от 5 до 15 строк)
        for (int i = 0; i < random.nextInt(5, 16); i++) {
            // Случайная длина строки (от 5 до 12 символов)
            int stringLength = random.nextInt(5, 13);
            StringBuilder sb = new StringBuilder(stringLength);

            for (int j = 0; j < stringLength; j++) {
                // Выбираем из алфавита случайную букву
                int randomIndex = random.nextInt(characters.length());
                sb.append(characters.charAt(randomIndex));
            }

            // Добавляем сгенерированную строку в результирующий список
            result.add(sb.toString());
        }

        return result;
    }

    /**
     * Метод генерирует строку с последовательностью четных чисел
     *
     * @param start - начало диапазона
     * @param end   - конец диапазона
     * @return - строка с последовательностью четных чисел
     */
    private static String getEvenNumbersString(int start, int end) {
        return IntStream.rangeClosed(start, end) // Генерируем поток чисел от start до end включительно
                // Фильтруем только четные
                .filter(n -> n % 2 == 0)
                // Преобразуем числа в строки
                .mapToObj(String::valueOf)
                // Склеиваем через пробел
                .collect(Collectors.joining(" "));
    }


    /*====================[Простые тесты]====================*/

    @Test
    @DisplayName("Простые тесты метода blastOff")
    @Tag("SimpleTest")
    void blastOffTest() {
        Random random = new Random();
        String exeptedResult;

        // случайные числа (повторим тест случайное число раз)
        for (int i = 0; i < random.nextInt(1, 10); i++) {
            int value = random.nextInt(1, 10);
            // Получения строки с обратной последовательностью цифр с помощью библиотек
            String str = IntStream.range(0, value) // Генерируем поток чисел от 0 до (value - 1)
                    // Разворачиваем их в обратном порядке: от value до 1
                    .map(n -> value - n)
                    // Превращаем числа в строки
                    .mapToObj(String::valueOf)
                    // Склеиваем: разделитель — пробел, суффикс (в конце строки) — тоже пробел
                    .collect(Collectors.joining(" ", "", " "));
            exeptedResult = str + "Поехали!";
            assertEquals(exeptedResult, LoopTasks.blastOff(value), "Метод blastOff с аргументом " + value + " должен возвращать: " + exeptedResult);
        }

        // как в примере - с 5
        assertEquals("5 4 3 2 1 Поехали!", LoopTasks.blastOff(5), "Метод blastOff с аргументом 5 должен возвращать: 5 4 3 2 1 Поехали!");
        // с единицы
        assertEquals("1 Поехали!", LoopTasks.blastOff(1), "Метод blastOff с аргументом 1 должен возвращать: 1 Поехали!");
        // с нуля
        assertEquals("Поехали!", LoopTasks.blastOff(0), "Метод blastOff с аргументом 0 должен возвращать: Поехали!");
        // отрицательное значение
        assertEquals("Поехали!", LoopTasks.blastOff(-3), "Метод blastOff с аргументом -3 должен возвращать: Поехали!");
    }

    @Test
    @DisplayName("Простые тесты метода sumToN")
    @Tag("SimpleTest")
    void sumToNTest() {
        Random random = new Random();

        // положительные числа
        int value = random.nextInt(2, 10);
        long sum = LongStream.rangeClosed(1, value).sum();
        assertEquals((int) sum, LoopTasks.sumToN(value), "Сумма от 1 до " + value + " должна быть равна " + sum);
        assertEquals(1, LoopTasks.sumToN(1), "Сумма от 1 до 1 должна быть равна 1");

        // нулевые и отрицательные числа
        assertEquals(0, LoopTasks.sumToN(0), "Сумма от 1 до 0 должна быть равна 0");
        value = -random.nextInt(2, 10);
        assertEquals(0, LoopTasks.sumToN(value), "Сумма от 1 до " + value + " должна быть равна 0");

        // Исключение ArithmeticException при слишком большом значении
        assertThrows(ArithmeticException.class, () -> LoopTasks.sumToN(random.nextInt(77777, 88888)),
                "При большом значении должно возникнуть исключение ArithmeticException");
    }


    @Test
    @DisplayName("Простые тесты метода hasBug")
    @Tag("SimpleTest")
    void hasBugTest() {
        Random random = new Random();

        List<String> nonsenseList;

        // Bug есть - должен вернуть true
        nonsenseList = nonsenseGenerator();
        nonsenseList.add(0, "Bug");     // Bug в начале списка
        assertTrue(LoopTasks.hasBug(nonsenseList.toArray(String[]::new)), "Для списка " + nonsenseList + " метод должен вернуть true");

        nonsenseList = nonsenseGenerator();
        nonsenseList.add(nonsenseList.size() / 2, "Bug");   // Bug в середине списка
        assertTrue(LoopTasks.hasBug(nonsenseList.toArray(String[]::new)), "Для списка " + nonsenseList + " метод должен вернуть true");

        nonsenseList = nonsenseGenerator();
        nonsenseList.add("Bug");    // Bug в конце списка
        assertTrue(LoopTasks.hasBug(nonsenseList.toArray(String[]::new)), "Для списка " + nonsenseList + " метод должен вернуть true");

        // Bug нет - должен вернуть false
        nonsenseList = nonsenseGenerator();
        assertFalse(LoopTasks.hasBug(nonsenseList.toArray(String[]::new)), "Для списка " + nonsenseList + " метод должен вернуть false");

        // пустой массив - должен вернуть false
        assertFalse(LoopTasks.hasBug(new String[]{}), "Для списка " + nonsenseList + " метод должен вернуть false");

        // массив содержит элементы null - не должен падать с NPE
        //   Bug есть - должен вернуть true
        nonsenseList = nonsenseGenerator();
        nonsenseList.add(null);
        nonsenseList.add("Bug");
        assertTrue(LoopTasks.hasBug(nonsenseList.toArray(String[]::new)), "Для списка " + nonsenseList + " метод должен вернуть true");

        //   Bug нет - должен вернуть false
        nonsenseList = nonsenseGenerator();
        nonsenseList.add(null);
        assertFalse(LoopTasks.hasBug(nonsenseList.toArray(String[]::new)), "Для списка " + nonsenseList + " метод должен вернуть false");

        // при передаче null должно возникнуть исключение NullPointerException
        assertThrows(NullPointerException.class, () -> LoopTasks.hasBug(null),
                "при передаче null должно возникнуть исключение NullPointerException");
    }

    @Test
    @DisplayName("Простые тесты метода getEvenInRange")
    @Tag("SimpleTest")
    void getEvenInRangeTest() {
        Random random = new Random();

        int start, end;
        String expectedResult;

        // положительный диапазон (повторим тест случайное число раз)
        for (int i = 0; i < 10; i++) {
            start = random.nextInt(1, 10);
            end = start + random.nextInt(1, 10);
            expectedResult = getEvenNumbersString(start, end);

            assertEquals(expectedResult, LoopTasks.getEvenInRange(start, end),
                    "для диапазона от " + start + " до " + end + " метод должен вернуть: " + expectedResult);
        }

        // диапазон из одного четного числа (повторим тест случайное число раз)
        for (int i = 0; i < 20; i++) {
            start = random.nextInt(1, 10);
            if (start % 2 == 0) {
                expectedResult = getEvenNumbersString(start, start);

                assertEquals(expectedResult, LoopTasks.getEvenInRange(start, start),
                        "для диапазона от " + start + " до " + start + " метод должен вернуть: " + expectedResult);
            }
        }

        // диапазон из одного не четного числа (повторим тест случайное число раз)
        for (int i = 0; i < 20; i++) {
            start = random.nextInt(1, 10);
            if (start % 2 != 0) {
                expectedResult = getEvenNumbersString(start, start);

                assertEquals(expectedResult, LoopTasks.getEvenInRange(start, start),
                        "для диапазона от " + start + " до " + start + " метод должен вернуть: " + expectedResult);
            }
        }

        // границы не составляют диапазон (start > end)
        for (int i = 0; i < 10; i++) {
            start = random.nextInt(1, 10);
            end = start - random.nextInt(1, 10);
            expectedResult = getEvenNumbersString(start, end);

            assertEquals(expectedResult, LoopTasks.getEvenInRange(start, end),
                    "для диапазона от " + start + " до " + end + " метод должен вернуть: " + expectedResult);
        }

        // диапазон с отрицательными границами
        for (int i = 0; i < 10; i++) {
            start = -random.nextInt(1, 10);
            end = start + random.nextInt(1, 10);
            expectedResult = getEvenNumbersString(start, end);

            assertEquals(expectedResult, LoopTasks.getEvenInRange(start, end),
                    "для диапазона от " + start + " до " + end + " метод должен вернуть: " + expectedResult);
        }
    }


    /*====================[Повторяющиеся тесты]====================*/

    @RepeatedTest(value = 10, name = "тест {currentRepetition} из {totalRepetitions}")
    @DisplayName("Повторяющиеся тесты метода blastOff")
    @Tag("RepeatedTest")
    void blastOffRepeatedTest() {
        Random random = new Random();
        String exeptedResult;

        // случайные числа (повторим тест случайное число раз)
        for (int i = 0; i < random.nextInt(1, 10); i++) {
            int value = random.nextInt(1, 10);
            // Получения строки с обратной последовательностью цифр с помощью библиотек
            String str = IntStream.range(0, value) // Генерируем поток чисел от 0 до (value - 1)
                    // Разворачиваем их в обратном порядке: от value до 1
                    .map(n -> value - n)
                    // Превращаем числа в строки
                    .mapToObj(String::valueOf)
                    // Склеиваем: разделитель — пробел, суффикс (в конце строки) — тоже пробел
                    .collect(Collectors.joining(" ", "", " "));
            exeptedResult = str + "Поехали!";
            assertEquals(exeptedResult, LoopTasks.blastOff(value), "Метод blastOff с аргументом " + value + " должен возвращать: " + exeptedResult);
        }

        // как в примере - с 5
        assertEquals("5 4 3 2 1 Поехали!", LoopTasks.blastOff(5), "Метод blastOff с аргументом 5 должен возвращать: 5 4 3 2 1 Поехали!");
        // с единицы
        assertEquals("1 Поехали!", LoopTasks.blastOff(1), "Метод blastOff с аргументом 1 должен возвращать: 1 Поехали!");
        // с нуля
        assertEquals("Поехали!", LoopTasks.blastOff(0), "Метод blastOff с аргументом 0 должен возвращать: Поехали!");
        // отрицательное значение
        assertEquals("Поехали!", LoopTasks.blastOff(-3), "Метод blastOff с аргументом -3 должен возвращать: Поехали!");

    }

    @RepeatedTest(value = 10, name = "тест {currentRepetition} из {totalRepetitions}")
    @DisplayName("Повторяющиеся тесты метода sumToN")
    @Tag("RepeatedTest")
    void sumToNRepeatedTest() {
        Random random = new Random();

        // положительные числа
        int value = random.nextInt(2, 10);
        long sum = LongStream.rangeClosed(1, value).sum();
        assertEquals((int) sum, LoopTasks.sumToN(value), "Сумма от 1 до " + value + " должна быть равна " + sum);
        assertEquals(1, LoopTasks.sumToN(1), "Сумма от 1 до 1 должна быть равна 1");

        // нулевые и отрицательные числа
        assertEquals(0, LoopTasks.sumToN(0), "Сумма от 1 до 0 должна быть равна 0");
        value = -random.nextInt(2, 10);
        assertEquals(0, LoopTasks.sumToN(value), "Сумма от 1 до " + value + " должна быть равна 0");

        // Исключение ArithmeticException при слишком большом значении
        assertThrows(ArithmeticException.class, () -> LoopTasks.sumToN(random.nextInt(77777, 88888)),
                "При большом значении должно возникнуть исключение ArithmeticException");
    }

    @RepeatedTest(value = 10, name = "тест {currentRepetition} из {totalRepetitions}")
    @DisplayName("Повторяющиеся тесты метода hasBug")
    @Tag("RepeatedTest")
    void hasBugRepeatedTest() {
        Random random = new Random();

        List<String> nonsenseList;

        // Bug есть - должен вернуть true
        nonsenseList = nonsenseGenerator();
        nonsenseList.add(0, "Bug");     // Bug в начале списка
        assertTrue(LoopTasks.hasBug(nonsenseList.toArray(String[]::new)), "Для списка " + nonsenseList + " метод должен вернуть true");

        nonsenseList = nonsenseGenerator();
        nonsenseList.add(nonsenseList.size() / 2, "Bug");   // Bug в середине списка
        assertTrue(LoopTasks.hasBug(nonsenseList.toArray(String[]::new)), "Для списка " + nonsenseList + " метод должен вернуть true");

        nonsenseList = nonsenseGenerator();
        nonsenseList.add("Bug");    // Bug в конце списка
        assertTrue(LoopTasks.hasBug(nonsenseList.toArray(String[]::new)), "Для списка " + nonsenseList + " метод должен вернуть true");

        // Bug нет - должен вернуть false
        nonsenseList = nonsenseGenerator();
        assertFalse(LoopTasks.hasBug(nonsenseList.toArray(String[]::new)), "Для списка " + nonsenseList + " метод должен вернуть false");

        // пустой массив - должен вернуть false
        assertFalse(LoopTasks.hasBug(new String[]{}), "Для списка " + nonsenseList + " метод должен вернуть false");

        // массив содержит элементы null - не должен падать с NPE
        //   Bug есть - должен вернуть true
        nonsenseList = nonsenseGenerator();
        nonsenseList.add(null);
        nonsenseList.add("Bug");
        assertTrue(LoopTasks.hasBug(nonsenseList.toArray(String[]::new)), "Для списка " + nonsenseList + " метод должен вернуть true");

        //   Bug нет - должен вернуть false
        nonsenseList = nonsenseGenerator();
        nonsenseList.add(null);
        assertFalse(LoopTasks.hasBug(nonsenseList.toArray(String[]::new)), "Для списка " + nonsenseList + " метод должен вернуть false");

        // при передаче null должно возникнуть исключение NullPointerException
        assertThrows(NullPointerException.class, () -> LoopTasks.hasBug(null),
                "при передаче null должно возникнуть исключение NullPointerException");
    }

    @RepeatedTest(value = 10, name = "тест {currentRepetition} из {totalRepetitions}")
    @DisplayName("Повторяющиеся тесты метода getEvenInRange")
    @Tag("RepeatedTest")
    void getEvenInRangeRepeatedTest() {
        Random random = new Random();

        int start, end;
        String expectedResult;

        // положительный диапазон (повторим тест случайное число раз)
        for (int i = 0; i < 10; i++) {
            start = random.nextInt(1, 10);
            end = start + random.nextInt(1, 10);
            expectedResult = getEvenNumbersString(start, end);

            assertEquals(expectedResult, LoopTasks.getEvenInRange(start, end),
                    "для диапазона от " + start + " до " + end + " метод должен вернуть: " + expectedResult);
        }

        // диапазон из одного четного числа (повторим тест случайное число раз)
        for (int i = 0; i < 20; i++) {
            start = random.nextInt(1, 10);
            if (start % 2 == 0) {
                expectedResult = getEvenNumbersString(start, start);

                assertEquals(expectedResult, LoopTasks.getEvenInRange(start, start),
                        "для диапазона от " + start + " до " + start + " метод должен вернуть: " + expectedResult);
            }
        }

        // диапазон из одного не четного числа (повторим тест случайное число раз)
        for (int i = 0; i < 20; i++) {
            start = random.nextInt(1, 10);
            if (start % 2 != 0) {
                expectedResult = getEvenNumbersString(start, start);

                assertEquals(expectedResult, LoopTasks.getEvenInRange(start, start),
                        "для диапазона от " + start + " до " + start + " метод должен вернуть: " + expectedResult);
            }
        }

        // границы не составляют диапазон (start > end)
        for (int i = 0; i < 10; i++) {
            start = random.nextInt(1, 10);
            end = start - random.nextInt(1, 10);
            expectedResult = getEvenNumbersString(start, end);

            assertEquals(expectedResult, LoopTasks.getEvenInRange(start, end),
                    "для диапазона от " + start + " до " + end + " метод должен вернуть: " + expectedResult);
        }

        // диапазон с отрицательными границами
        for (int i = 0; i < 10; i++) {
            start = -random.nextInt(1, 10);
            end = start + random.nextInt(1, 10);
            expectedResult = getEvenNumbersString(start, end);

            assertEquals(expectedResult, LoopTasks.getEvenInRange(start, end),
                    "для диапазона от " + start + " до " + end + " метод должен вернуть: " + expectedResult);
        }
    }


    /*====================[Параметризованные тесты]====================*/

    /**
     * Метод предоставляет данные для параметризованного теста blastOff
     *
     * @return - возвращает список структур
     */
    static private List<BlastOffTestData> blastOffParametrizedTestData() {
        List<BlastOffTestData> dataList = new ArrayList<>();

        Random random = new Random();
        String exeptedResult;

        // случайные числа (повторим тест случайное число раз)
        for (int i = 0; i < random.nextInt(1, 10); i++) {
            int value = random.nextInt(1, 10);
            // Получения строки с обратной последовательностью цифр с помощью библиотек
            String str = IntStream.range(0, value) // Генерируем поток чисел от 0 до (value - 1)
                    // Разворачиваем их в обратном порядке: от value до 1
                    .map(n -> value - n)
                    // Превращаем числа в строки
                    .mapToObj(String::valueOf)
                    // Склеиваем: разделитель — пробел, суффикс (в конце строки) — тоже пробел
                    .collect(Collectors.joining(" ", "", " "));
            exeptedResult = str + "Поехали!";
            dataList.add(new BlastOffTestData(value, exeptedResult,
                    "Метод blastOff с аргументом " + value + " должен возвращать: " + exeptedResult));
        }

        // как в примере - с 5
        dataList.add(new BlastOffTestData(5, "5 4 3 2 1 Поехали!",
                "Метод blastOff с аргументом 5 должен возвращать: 5 4 3 2 1 Поехали!"));
        // с единицы
        assertEquals("1 Поехали!", LoopTasks.blastOff(1));
        dataList.add(new BlastOffTestData(1, "1 Поехали!",
                "Метод blastOff с аргументом 1 должен возвращать: 1 Поехали!"));
        // с нуля
        dataList.add(new BlastOffTestData(0, "Поехали!",
                "Метод blastOff с аргументом 0 должен возвращать: Поехали!"));
        // отрицательное значение
        dataList.add(new BlastOffTestData(-3, "Поехали!",
                "Метод blastOff с аргументом -3 должен возвращать: Поехали!"));

        return dataList;
    }

    /**
     * Параметризованный тест метода blastOff
     *
     * @param data - структура тестовых данных для параметризованного теста
     */
    @ParameterizedTest
    @MethodSource("blastOffParametrizedTestData")
    @DisplayName("Параметризованный тест метода blastOff")
    @Tag("ParameterizedTest")
    void blastOffParametrizedTest(BlastOffTestData data) {
        assertEquals(data.expectedResult(), LoopTasks.blastOff(data.start()), data.assertMessage());
    }

    /**
     * Метод предоставляет данные для параметризованного теста sumToN
     *
     * @return - возвращает список структур
     */
    static private List<SumToNTestData> sumToNParametrizedTestData() {
        List<SumToNTestData> dataList = new ArrayList<>();

        Random random = new Random();

        // положительные числа
        int value = random.nextInt(2, 10);
        long sum = LongStream.rangeClosed(1, value).sum();
        dataList.add(new SumToNTestData(value, (int) sum, null,
                "Сумма от 1 до " + value + " должна быть равна " + sum));
        dataList.add(new SumToNTestData(1, 1, null,
                "Сумма от 1 до 1 должна быть равна 1"));

        // нулевые и отрицательные числа
        dataList.add(new SumToNTestData(0, 0, null,
                "Сумма от 1 до 0 должна быть равна 0"));
        value = -random.nextInt(2, 10);
        dataList.add(new SumToNTestData(value, 0, null,
                "Сумма от 1 до " + value + " должна быть равна 0"));

        // Исключение ArithmeticException при слишком большом значении
        dataList.add(new SumToNTestData(77777, 88888, ArithmeticException.class,
                "При большом значении должно возникнуть исключение ArithmeticException"));

        return dataList;
    }

    /**
     * Параметризованный тест метода sumToN
     *
     * @param data - структура тестовых данных для параметризованного теста
     */
    @ParameterizedTest
    @MethodSource("sumToNParametrizedTestData")
    @DisplayName("Параметризованный тест метода sumToN")
    @Tag("ParameterizedTest")
    void sumToNParametrizedTest(SumToNTestData data) {
        if (data.expectedException() != null) {
            assertThrows(data.expectedException(), () -> LoopTasks.sumToN(data.n()), data.assertMessage());
        } else {
            assertEquals(data.expectedResult(), LoopTasks.sumToN(data.n()), data.assertMessage());
        }
    }

    /**
     * Метод предоставляет данные для параметризованного теста hasBug
     *
     * @return - возвращает список структур
     */
    static private List<HasBugTestData> hasBugParametrizedTestData() {
        List<HasBugTestData> dataList = new ArrayList<>();

        Random random = new Random();

        List<String> nonsenseList;

        // Bug есть - должен вернуть true
        nonsenseList = nonsenseGenerator();
        nonsenseList.add(0, "Bug");     // Bug в начале списка
        dataList.add(new HasBugTestData(nonsenseList.toArray(String[]::new), true, null,
                "Для списка " + nonsenseList + " метод должен вернуть true"));

        nonsenseList = nonsenseGenerator();
        nonsenseList.add(nonsenseList.size() / 2, "Bug");   // Bug в середине списка
        dataList.add(new HasBugTestData(nonsenseList.toArray(String[]::new), true, null,
                "Для списка " + nonsenseList + " метод должен вернуть true"));

        nonsenseList = nonsenseGenerator();
        nonsenseList.add("Bug");    // Bug в конце списка
        dataList.add(new HasBugTestData(nonsenseList.toArray(String[]::new), true, null,
                "Для списка " + nonsenseList + " метод должен вернуть true"));

        // Bug нет - должен вернуть false
        nonsenseList = nonsenseGenerator();
        dataList.add(new HasBugTestData(nonsenseList.toArray(String[]::new), false, null,
                "Для списка " + nonsenseList + " метод должен вернуть false"));

        // пустой массив - должен вернуть false
        dataList.add(new HasBugTestData(new String[]{}, false, null,
                "Для списка " + nonsenseList + " метод должен вернуть false"));

        // массив содержит элементы null - не должен падать с NPE
        //   Bug есть - должен вернуть true
        nonsenseList = nonsenseGenerator();
        nonsenseList.add(null);
        nonsenseList.add("Bug");
        dataList.add(new HasBugTestData(nonsenseList.toArray(String[]::new), true, null,
                "Для списка " + nonsenseList + " метод должен вернуть true"));

        //   Bug нет - должен вернуть false
        nonsenseList = nonsenseGenerator();
        nonsenseList.add(null);
        dataList.add(new HasBugTestData(nonsenseList.toArray(String[]::new), false, null,
                "Для списка " + nonsenseList + " метод должен вернуть false"));

        // при передаче null должно возникнуть исключение NullPointerException
        dataList.add(new HasBugTestData(null, false, NullPointerException.class,
                "при передаче null должно возникнуть исключение NullPointerException"));

        return dataList;
    }

    /**
     * Параметризованный тест метода hasBug
     *
     * @param data - структура тестовых данных для параметризованного теста
     */
    @ParameterizedTest
    @MethodSource("hasBugParametrizedTestData")
    @DisplayName("Параметризованный тест метода hasBug")
    @Tag("ParameterizedTest")
    void hasBugParametrizedTest(HasBugTestData data) {
        if (data.expectedException() != null) {
            assertThrows(data.expectedException(), () -> LoopTasks.hasBug(data.messages()), data.assertMessage());
        } else {
            assertEquals(data.expectedResult(), LoopTasks.hasBug(data.messages()), data.assertMessage());
        }
    }

    /**
     * Метод предоставляет данные для параметризованного теста getEvenInRange
     *
     * @return - возвращает список структур
     */
    static private List<GetEvenInRangeTestData> getEvenInRangeParametrizedTestData() {
        List<GetEvenInRangeTestData> dataList = new ArrayList<>();

        Random random = new Random();

        int start, end;
        String expectedResult;

        // положительный диапазон (повторим тест случайное число раз)
        for (int i = 0; i < 10; i++) {
            start = random.nextInt(1, 10);
            end = start + random.nextInt(1, 10);
            expectedResult = getEvenNumbersString(start, end);

            dataList.add(new GetEvenInRangeTestData(start, end, expectedResult,
                    "для диапазона от " + start + " до " + end + " метод должен вернуть: " + expectedResult));
        }

        // диапазон из одного четного числа (повторим тест случайное число раз)
        for (int i = 0; i < 20; i++) {
            start = random.nextInt(1, 10);
            if (start % 2 == 0) {
                expectedResult = getEvenNumbersString(start, start);

                dataList.add(new GetEvenInRangeTestData(start, start, expectedResult,
                        "для диапазона от " + start + " до " + start + " метод должен вернуть: " + expectedResult));
            }
        }

        // диапазон из одного не четного числа (повторим тест случайное число раз)
        for (int i = 0; i < 20; i++) {
            start = random.nextInt(1, 10);
            if (start % 2 != 0) {
                expectedResult = getEvenNumbersString(start, start);

                dataList.add(new GetEvenInRangeTestData(start, start, expectedResult,
                        "для диапазона от " + start + " до " + start + " метод должен вернуть: " + expectedResult));
            }
        }

        // границы не составляют диапазон (start > end)
        for (int i = 0; i < 10; i++) {
            start = random.nextInt(1, 10);
            end = start - random.nextInt(1, 10);
            expectedResult = getEvenNumbersString(start, end);

            dataList.add(new GetEvenInRangeTestData(start, end, expectedResult,
                    "для диапазона от " + start + " до " + end + " метод должен вернуть: " + expectedResult));
        }

        // диапазон с отрицательными границами
        for (int i = 0; i < 10; i++) {
            start = -random.nextInt(1, 10);
            end = start + random.nextInt(1, 10);
            expectedResult = getEvenNumbersString(start, end);

            dataList.add(new GetEvenInRangeTestData(start, end, expectedResult,
                    "для диапазона от " + start + " до " + end + " метод должен вернуть: " + expectedResult));
        }

        return dataList;
    }

    /**
     * Параметризованный тест метода getEvenInRange
     *
     * @param data - структура тестовых данных для параметризованного теста
     */
    @ParameterizedTest
    @MethodSource("getEvenInRangeParametrizedTestData")
    @DisplayName("Параметризованный тест метода getEvenInRange")
    @Tag("ParameterizedTest")
    void getEvenInRangeParametrizedTest(GetEvenInRangeTestData data) {
        assertEquals(data.expectedResult(), LoopTasks.getEvenInRange(data.start(), data.end()), data.assertMessage());
    }

}
