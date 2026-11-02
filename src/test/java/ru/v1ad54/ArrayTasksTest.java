package ru.v1ad54;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
//import static ru.v1ad54.MyAssertions.*;


/**
 * Структура тестовых данных для параметризованного теста {@link ArrayTasksTest#findMaxParametrizedTest(FindMaxTestData)}
 *
 * @param array          - массив чисел для параметра arr
 * @param expectedResult - ожидаемый результат метода findMax
 * @param assertMessage  - сообщение выводимое в случае fail
 */
record FindMaxTestData(
        int[] array,
        String expectedResult
        , String assertMessage
) {
}

/**
 * Структура тестовых данных для параметризованного теста {@link ArrayTasksTest#reverseParametrizedTest(ReverseTestData)}
 *
 * @param array             - массив чисел для параметра arr
 * @param expectedResult    - ожидаемый результат метода reverse
 * @param expectedException - ожидаемое исключение при выполнении метода reverse
 * @param assertMessage  - сообщение выводимое в случае fail
 */
record ReverseTestData(
        String[] array,
        String[] expectedResult,
        Class expectedException
        , String assertMessage
) {
}

/**
 * Структура тестовых данных для параметризованного теста {@link ArrayTasksTest#calcAverageParametrizedTest(CalcAverageTestData)}
 *
 * @param list              - список чисел для параметра list
 * @param expectedResult    - ожидаемый результат метода calcAverage
 * @param expectedException - ожидаемое исключение при выполнении метода reverse
 * @param assertMessage  - сообщение выводимое в случае fail
 */
record CalcAverageTestData(
        List<Integer> list,
        int expectedResult,
        Class expectedException
        , String assertMessage
) {
}

/**
 * Структура тестовых данных для параметризованного теста
 *
 * @param list              - список имен
 * @param nameToRemove      - имя которое нужно исключить из списка
 * @param expectedResult    - ожидаемый результат метода calcAverage
 * @param expectedException - ожидаемое исключение при выполнении метода reverse
 * @param assertMessage  - сообщение выводимое в случае fail
 */
record RemoveSpecificNameTestData(
        List<String> list,
        String nameToRemove,
        List<String> expectedResult,
        Class expectedException
        , String assertMessage
) {
}

public class ArrayTasksTest {

    /*====================[Простые тесты]====================*/

    /**
     * Простой тест метода findMax
     */
    @Test
    @DisplayName("Простые тесты метода findMax")
    @Tag("SimpleTest")
    void findMaxTest() {
        Random random = new Random();
        List<Integer> list = new ArrayList<>();
        int value;

        // массив с положительными и отрицательными числами
        for (int i = 0; i < 10; i++) {
            list.add(random.nextInt(-10, 10));
        }
        assertEquals(
                Collections.max(list), // Сравниваем с максимальным значением полученным стандартной библиотекой
                ArrayTasks.findMax(
                        list.stream()
                                .mapToInt(Integer::intValue)
                                .toArray()
                )
                , "В массиве " + list + "максимальное число: " + Collections.max(list)
        );

        // массив в котором все числа одинаковые
        value = random.nextInt(1, 10);
        list.clear();
        for (int i = 0; i < random.nextInt(1, 10); i++) {
            list.add(value);
        }
        assertEquals(
                value,
                ArrayTasks.findMax(list.stream()
                        .mapToInt(Integer::intValue)
                        .toArray()
                )
                , "В массиве " + list + "максимальное число: " + Collections.max(list)
        );

        // массив из одного элемента
        value = random.nextInt(1, 100);
        assertEquals(value, ArrayTasks.findMax(new int[]{value}), "В массиве " + list + "максимальное число: " + Collections.max(list));

        // исключение IllegalArgumentException при пустом массиве
        assertThrows(IllegalArgumentException.class, () -> ArrayTasks.findMax(new int[]{}), "При пустом массиве должно возникнуть исключение IllegalArgumentException");

        // исключение NullPointerException при передаче null
        assertThrows(NullPointerException.class, () -> ArrayTasks.findMax(null), "При IllegalArgumentException должно возникнуть исключение NullPointerException");
    }

    /**
     * Простой тест метода reverse
     */
    @Test
    @DisplayName("Простые тесты метода reverse")
    @Tag("SimpleTest")
    void reverseTest() {
        Random random = new Random();

        // Создаем список содержащий случайные строки
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("string-" + random.nextInt(1, 100));
        }

        // Создаем реверсированный список с помощью стандартной библиотеки
        List<String> reverseList = new ArrayList<>(list);
        Collections.reverse(reverseList);

        // Сравниваем реверсированный список с результатом который вернул метод ArrayTasks.reverse
        assertArrayEquals(reverseList.toArray(String[]::new), ArrayTasks.reverse(list.toArray(String[]::new)),
                "При вызове reverse(" + list + ") должны получить: " + reverseList.toArray(String[]::new));

        // пустой массив
        assertArrayEquals(new String[]{}, ArrayTasks.reverse(new String[]{}),
                "При вызове метода на пустом массиве, должны получить пустой массив");

        // исключение NullPointerException при передаче null
        assertThrows(NullPointerException.class, () -> ArrayTasks.reverse(null),
                "При вызове метода с null, должны получить исключение NullPointerException");
    }


    /**
     * Простой тест метода calcAverage
     */
    @Test
    @DisplayName("Простые тесты метода calcAverage")
    @Tag("SimpleTest")
    void calcAverageTest() {
        Random random = new Random();
        List<Integer> list = new ArrayList<>();
        int expectedResult;

        // просто список чисел, заодно проверка на деление с потерей дробной части (целочисленный int)
        for (int i = 0; i < 10; i++) {
            list.add(random.nextInt(1, 100));
        }
        expectedResult = (int) list.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);
        assertEquals(
                expectedResult,
                ArrayTasks.calcAverage(list),
                "Среднеарифметическое из " + list + " должно быть " + expectedResult
        );

        // исключение IllegalArgumentException на пустой список
        assertThrows(IllegalArgumentException.class, () -> ArrayTasks.calcAverage(new ArrayList<>()),
                "На пустом списке должно возникать исключение IllegalArgumentException");

        // исключение NullPointerException при передаче null
        assertThrows(NullPointerException.class, () -> ArrayTasks.calcAverage(null),
                "На null списке должно возникать исключение NullPointerException");

        // исключение IllegalArgumentException, если внутри списка есть null
        assertThrows(IllegalArgumentException.class, () -> ArrayTasks.calcAverage(Arrays.asList(1, 2, null, 4)),
                "Если в списке есть null элемент, должно возникать исключение IllegalArgumentException");
    }

    /**
     * Простой тест метода removeSpecificName
     */
    @Test
    @DisplayName("Простые тесты метода removeSpecificName")
    @Tag("SimpleTest")
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
        assertEquals(
                // Сравниваем с новым списком без nameFromList
                expectedList
                , ArrayTasks.removeSpecificName(list, nameToRemove1)
                , "Из списка " + list + " должно быть удалено имя " + nameToRemove
        );

        // имя для удаления отсутствует в списке
        assertEquals(
                list,
                ArrayTasks.removeSpecificName(list, "Такого_имени_точно_нет_в_списке"),
                "В случае если удаляемого имени нет в списке, список должен остаться прежним."
        );

        // исключение NullPointerException при передаче null в параметр array
        assertThrows(
                NullPointerException.class,
                () -> ArrayTasks.removeSpecificName(null, "Name-" + random.nextInt(1, 100)),
                "В случае передачи null списка, должен возникать NullPointerException"
        );

        // исключение IllegalArgumentException при передаче null в параметр nameFromList
        assertThrows(
                IllegalArgumentException.class,
                () -> ArrayTasks.removeSpecificName(list, null),
                "В случае передачи null в параметр nameToRemove, должен возникать IllegalArgumentException"
        );

        // исключение IllegalArgumentException, если элемент списка равен null
        list.add(null); // Добавим в список null-элемент
        String nameToRemove2 = nameToRemove;
        assertThrows(
                IllegalArgumentException.class,
                () -> ArrayTasks.removeSpecificName(list, nameToRemove2),
                "В случае если в списке будет null, должен возникать IllegalArgumentException"
        );
    }

    /*====================[Повторяющиеся тесты]====================*/

    /**
     * Повторяющийся тест метода findMax
     */
    @RepeatedTest(value = 10, name = "тест {currentRepetition} из {totalRepetitions}")
    @DisplayName("Повторяющиеся тесты метода findMax")
    @Tag("RepeatedTest")
    void findMaxRepeatedTest() {
        Random random = new Random();
        List<Integer> list = new ArrayList<>();
        int value;

        // массив с положительными и отрицательными числами
        for (int i = 0; i < 10; i++) {
            list.add(random.nextInt(-10, 10));
        }
        assertEquals(
                Collections.max(list), // Сравниваем с максимальным значением полученным стандартной библиотекой
                ArrayTasks.findMax(
                        list.stream()
                                .mapToInt(Integer::intValue)
                                .toArray()
                )
                , "В массиве " + list + "максимальное число: " + Collections.max(list)
        );

        // массив в котором все числа одинаковые
        value = random.nextInt(1, 10);
        list.clear();
        for (int i = 0; i < random.nextInt(1, 10); i++) {
            list.add(value);
        }
        assertEquals(
                value,
                ArrayTasks.findMax(list.stream()
                        .mapToInt(Integer::intValue)
                        .toArray()
                )
                , "В массиве " + list + "максимальное число: " + Collections.max(list)
        );

        // массив из одного элемента
        value = random.nextInt(1, 100);
        assertEquals(value, ArrayTasks.findMax(new int[]{value}), "В массиве " + list + "максимальное число: " + Collections.max(list));

        // исключение IllegalArgumentException при пустом массиве
        assertThrows(IllegalArgumentException.class, () -> ArrayTasks.findMax(new int[]{}), "При пустом массиве должно возникнуть исключение IllegalArgumentException");

        // исключение NullPointerException при передаче null
        assertThrows(NullPointerException.class, () -> ArrayTasks.findMax(null), "При IllegalArgumentException должно возникнуть исключение NullPointerException");
    }

    /**
     * Повторяющийся тест метода reverse
     */
    @RepeatedTest(value = 10, name = "тест {currentRepetition} из {totalRepetitions}")
    @DisplayName("Повторяющиеся тесты метода reverse")
    @Tag("RepeatedTest")
    void reverseRepeatedTest() {
        Random random = new Random();

        // Создаем список содержащий случайные строки
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("string-" + random.nextInt(1, 100));
        }

        // Создаем реверсированный список с помощью стандартной библиотеки
        List<String> reverseList = new ArrayList<>(list);
        Collections.reverse(reverseList);

        // Сравниваем реверсированный список с результатом который вернул метод ArrayTasks.reverse
        assertArrayEquals(reverseList.toArray(String[]::new), ArrayTasks.reverse(list.toArray(String[]::new)),
                "При вызове reverse(" + list + ") должны получить: " + reverseList.toArray(String[]::new));

        // пустой массив
        assertArrayEquals(new String[]{}, ArrayTasks.reverse(new String[]{}),
                "При вызове метода на пустом массиве, должны получить пустой массив");

        // исключение NullPointerException при передаче null
        assertThrows(NullPointerException.class, () -> ArrayTasks.reverse(null),
                "При вызове метода с null, должны получить исключение NullPointerException");
    }

    /**
     * Повторяющийся тест метода calcAverage
     */
    @RepeatedTest(value = 10, name = "тест {currentRepetition} из {totalRepetitions}")
    @DisplayName("Повторяющиеся тесты метода calcAverage")
    @Tag("RepeatedTest")
    void calcAverageRepeatedTest() {
        Random random = new Random();
        List<Integer> list = new ArrayList<>();
        int expectedResult;

        // просто список чисел, заодно проверка на деление с потерей дробной части (целочисленный int)
        for (int i = 0; i < 10; i++) {
            list.add(random.nextInt(1, 100));
        }
        expectedResult = (int) list.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);
        assertEquals(
                expectedResult,
                ArrayTasks.calcAverage(list),
                "Среднеарифметическое из " + list + " должно быть " + expectedResult
        );

        // исключение IllegalArgumentException на пустой список
        assertThrows(IllegalArgumentException.class, () -> ArrayTasks.calcAverage(new ArrayList<>()),
                "На пустом списке должно возникать исключение IllegalArgumentException");

        // исключение NullPointerException при передаче null
        assertThrows(NullPointerException.class, () -> ArrayTasks.calcAverage(null),
                "На null списке должно возникать исключение NullPointerException");

        // исключение IllegalArgumentException, если внутри списка есть null
        assertThrows(IllegalArgumentException.class, () -> ArrayTasks.calcAverage(Arrays.asList(1, 2, null, 4)),
                "Если в списке есть null элемент, должно возникать исключение IllegalArgumentException");
    }

    /**
     * Повторяющийся тест метода removeSpecificName
     */
    @RepeatedTest(value = 10, name = "тест {currentRepetition} из {totalRepetitions}")
    @DisplayName("Повторяющиеся тесты метода removeSpecificName")
    @Tag("RepeatedTest")
    void removeSpecificNameRepeatedTest() {
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
        assertEquals(
                // Сравниваем с новым списком без nameFromList
                expectedList
                , ArrayTasks.removeSpecificName(list, nameToRemove1)
                , "Из списка " + list + " должно быть удалено имя " + nameToRemove
        );

        // имя для удаления отсутствует в списке
        assertEquals(
                list,
                ArrayTasks.removeSpecificName(list, "Такого_имени_точно_нет_в_списке"),
                "В случае если удаляемого имени нет в списке, список должен остаться прежним."
        );

        // исключение NullPointerException при передаче null в параметр array
        assertThrows(
                NullPointerException.class,
                () -> ArrayTasks.removeSpecificName(null, "Name-" + random.nextInt(1, 100)),
                "В случае передачи null списка, должен возникать NullPointerException"
        );

        // исключение IllegalArgumentException при передаче null в параметр nameFromList
        assertThrows(
                IllegalArgumentException.class,
                () -> ArrayTasks.removeSpecificName(list, null),
                "В случае передачи null в параметр nameToRemove, должен возникать IllegalArgumentException"
        );

        // исключение IllegalArgumentException, если элемент списка равен null
        list.add(null); // Добавим в список null-элемент
        String nameToRemove2 = nameToRemove;
        assertThrows(
                IllegalArgumentException.class,
                () -> ArrayTasks.removeSpecificName(list, nameToRemove2),
                "В случае если в списке будет null, должен возникать IllegalArgumentException"
        );
    }

    /*====================[Параметризованные тесты]====================*/

    /**
     * Метод предоставляет данные для параметризованного теста findMaxParametrizedTest
     *
     * @return - возвращает список структур
     */
    static private List<FindMaxTestData> findMaxParametrizedTestData() {
        List<FindMaxTestData> dataList = new ArrayList<>();

        Random random = new Random();
        List<Integer> list = new ArrayList<>();
        int value;

        // массив с положительными и отрицательными числами
        for (int i = 0; i < 10; i++) {
            list.add(random.nextInt(-10, 10));
        }
        dataList.add(new FindMaxTestData(
                // Преобразуем список в массив
                list.stream().mapToInt(Integer::intValue).toArray(),
                // Сравнивать будем с максимальным значением полученным стандартной библиотекой
                Collections.max(list).toString()
                , "В массиве " + list + "максимальное число: " + Collections.max(list)
        ));

        // массив в котором все числа одинаковые
        value = random.nextInt(1, 10);
        list.clear();
        for (int i = 0; i < random.nextInt(1, 10); i++) {
            list.add(value);
        }
        dataList.add(new FindMaxTestData(
                // Преобразуем список в массив
                list.stream().mapToInt(Integer::intValue).toArray(),
                String.valueOf(value)
                , "В массиве " + list + "максимальное число: " + Collections.max(list)
        ));

        // массив из одного элемента
        value = random.nextInt(1, 100);
        dataList.add(new FindMaxTestData(
                new int[]{value},
                String.valueOf(value)
                , "В массиве " + list + "максимальное число: " + Collections.max(list)
        ));

        // исключение IllegalArgumentException при пустом массиве
        dataList.add(new FindMaxTestData(
                new int[]{},
                "IllegalArgumentException"
                , "При пустом массиве должно возникнуть исключение IllegalArgumentException"
        ));

        // исключение NullPointerException при передаче null
        dataList.add(new FindMaxTestData(
                null,
                "NullPointerException"
                , "При IllegalArgumentException должно возникнуть исключение NullPointerException"
        ));

        return dataList;
    }

    /**
     * Параметризованный тест метода findMax
     *
     * @param data - структура тестовых данных для параметризованного теста
     */
    @ParameterizedTest
    @MethodSource("findMaxParametrizedTestData")
    @DisplayName("Параметризованный тест метода findMax")
    @Tag("ParameterizedTest")
    void findMaxParametrizedTest(FindMaxTestData data) {
        switch (data.expectedResult()) {
            case "IllegalArgumentException" -> {
                assertThrows(IllegalArgumentException.class, () -> ArrayTasks.findMax(data.array()), data.assertMessage());
            }
            case "NullPointerException" -> {
                assertThrows(NullPointerException.class, () -> ArrayTasks.findMax(data.array()), data.assertMessage());
            }
            default -> {
                try {
                    assertEquals(Integer.parseInt(data.expectedResult()), ArrayTasks.findMax(data.array()), data.assertMessage());
                } catch (NumberFormatException e) {
                    fail("Ошибка данных. Ожидаемый результат не соответствует типу int: " + data.expectedResult());
                }
            }
        }
    }

    /**
     * Метод предоставляет данные для параметризованного теста reverseParametrizedTest
     *
     * @return - возвращает список структур
     */
    static private List<ReverseTestData> reverseParametrizedTestData() {
        List<ReverseTestData> dataList = new ArrayList<>();

        Random random = new Random();
        List<String> list = new ArrayList<>();
        String value;

        // Создаем список содержащий случайные строки
        for (int i = 0; i < 10; i++) {
            list.add("string-" + random.nextInt(1, 100));
        }

        // Создаем реверсированный список с помощью стандартной библиотеки
        List<String> reverseList = new ArrayList<>(list);
        Collections.reverse(reverseList);


        // Сравнение реверсированного списка с результатом который вернул метод ArrayTasks.reverse
        dataList.add(
                new ReverseTestData(list.toArray(String[]::new),
                        reverseList.toArray(String[]::new),
                        null,
                        "При вызове reverse(" + list + ") должны получить: " + reverseList.toArray(String[]::new)
                ));

        // пустой массив
        dataList.add(new ReverseTestData(new String[]{}, new String[]{}, null,
                "При вызове метода на пустом массиве, должны получить пустой массив"));

        // исключение NullPointerException при передаче null
        dataList.add(new ReverseTestData(null, null, NullPointerException.class,
                "При вызове метода с null, должны получить исключение NullPointerException"));

        return dataList;
    }

    /**
     * Параметризованный тест метода reverse
     *
     * @param data - структура тестовых данных для параметризованного теста
     */
    @ParameterizedTest
    @MethodSource("reverseParametrizedTestData")
    @DisplayName("Параметризованный тест метода reverse")
    @Tag("ParameterizedTest")
    void reverseParametrizedTest(ReverseTestData data) {
        if (data.expectedException() != null) {
            assertThrows(data.expectedException(), () -> ArrayTasks.reverse(data.array()), data.assertMessage());
        } else {
            assertArrayEquals(data.expectedResult(), ArrayTasks.reverse(data.array()), data.assertMessage());
        }
    }

    /**
     * Метод предоставляет данные для параметризованного теста calcAverageParametrizedTest
     *
     * @return - возвращает список структур
     */
    static private List<CalcAverageTestData> calcAverageParametrizedTestData() {
        List<CalcAverageTestData> dataList = new ArrayList<>();

        Random random = new Random();
        List<Integer> list = new ArrayList<>();
        int expectedResult;

        // просто список чисел, заодно проверка на деление с потерей дробной части (целочисленный int)
        for (int i = 0; i < 10; i++) {
            list.add(random.nextInt(1, 100));
        }
        expectedResult = (int) list.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);
        dataList.add(
                new CalcAverageTestData(
                        list,
                        expectedResult,
                        null,
                        "Среднеарифметическое из " + list + " должно быть " + expectedResult
                )
        );

        // исключение IllegalArgumentException на пустой список
        dataList.add(new CalcAverageTestData(new ArrayList<>(), 0, IllegalArgumentException.class,
                "На пустом списке должно возникать исключение IllegalArgumentException"));

        // исключение NullPointerException при передаче null
        dataList.add(new CalcAverageTestData(null, 0, NullPointerException.class,
                "На null списке должно возникать исключение NullPointerException"));

        // исключение IllegalArgumentException, если внутри списка есть null
        dataList.add(new CalcAverageTestData(Arrays.asList(1, 2, null, 4), 0, IllegalArgumentException.class,
                "Если в списке есть null элемент, должно возникать исключение IllegalArgumentException"));

        return dataList;
    }

    /**
     * Параметризованный тест метода calcAverage
     *
     * @param data - структура тестовых данных для параметризованного теста
     */
    @ParameterizedTest
    @MethodSource("calcAverageParametrizedTestData")
    @DisplayName("Параметризованный тест метода calcAverage")
    @Tag("ParameterizedTest")
    void calcAverageParametrizedTest(CalcAverageTestData data) {
        if (data.expectedException() != null) {
            assertThrows(data.expectedException(), () -> ArrayTasks.calcAverage(data.list()), data.assertMessage());
        } else {
            assertEquals(data.expectedResult(), ArrayTasks.calcAverage(data.list()), data.assertMessage());
        }
    }

    /**
     * Метод предоставляет данные для параметризованного теста removeSpecificNameParametrizedTest
     *
     * @return - возвращает список структур
     */
    static private List<RemoveSpecificNameTestData> removeSpecificNameParametrizedTestData() {
        List<RemoveSpecificNameTestData> dataList = new ArrayList<>();

        Random random = new Random();
        List<String> list = new ArrayList<>();
        List<String> expectedList;
        String nameToRemove;

        for (int i = 0; i < 10; i++) {
            list.add("Name-" + random.nextInt(1, 100));
        }
        nameToRemove = list.get(random.nextInt(list.size())); // Выбираем в списке имен имя, которое нужно исключить из списка
        String nameToRemove1 = nameToRemove;
        expectedList = list.stream().filter(name -> !name.equals(nameToRemove1)).toList();
        dataList.add(
                new RemoveSpecificNameTestData(
                        list,
                        nameToRemove1,
                        expectedList, // Сравниваем с новым списком без nameToRemove1
                        null, "Из списка " + list + " должно быть удалено имя " + nameToRemove
                )
        );

        // имя для удаления отсутствует в списке
        dataList.add(
                new RemoveSpecificNameTestData(
                        list,
                        "Такого_имени_точно_нет_в_списке",
                        list,
                        null,
                        "В случае если удаляемого имени нет в списке, список должен остаться прежним."
                )
        );

        // исключение NullPointerException при передаче null в параметр array
        dataList.add(
                new RemoveSpecificNameTestData(null,
                        "Name-" + random.nextInt(1, 100),
                        null,
                        NullPointerException.class,
                        "В случае передачи null списка, должен возникать NullPointerException"
                )
        );

        // исключение IllegalArgumentException при передаче null в параметр nameFromList
        dataList.add(
                new RemoveSpecificNameTestData(
                        list,
                        null,
                        null,
                        IllegalArgumentException.class,
                        "В случае передачи null в параметр nameToRemove, должен возникать IllegalArgumentException"
                )
        );

        // исключение IllegalArgumentException, если элемент списка равен null
        list = new ArrayList<>(list); // Создаем новый список на основе старого
        list.add(null); // Добавим в список null-элемент
        String nameToRemove2 = nameToRemove;
        dataList.add(
                new RemoveSpecificNameTestData(
                        list,
                        nameToRemove2,
                        null,
                        IllegalArgumentException.class,
                        "В случае если в списке будет null, должен возникать IllegalArgumentException"
                )
        );

        return dataList;
    }

    /**
     * Параметризованный тест метода removeSpecificName
     *
     * @param data - структура тестовых данных для параметризованного теста
     */
    @ParameterizedTest
    @MethodSource("removeSpecificNameParametrizedTestData")
    @DisplayName("Параметризованный тест метода removeSpecificName")
    @Tag("ParameterizedTest")
    void removeSpecificNameParametrizedTest(RemoveSpecificNameTestData data) {
        if (data.expectedException() != null) {
            assertThrows(data.expectedException(), () -> ArrayTasks.removeSpecificName(data.list(), data.nameToRemove()), data.assertMessage());
        } else {
            assertEquals(data.expectedResult(), ArrayTasks.removeSpecificName(data.list(), data.nameToRemove()), data.assertMessage());
        }
    }

}
