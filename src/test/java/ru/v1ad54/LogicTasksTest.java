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

import static org.junit.jupiter.api.Assertions.*;
//import static ru.v1ad54.MyAssertions.*;


/**
 * Структура тестовых данных для параметризованного теста {@link LogicTasksTest#isEvenParametrizedTest(IsEvenTestData)}
 *
 * @param n              - проверяемое число для параметра n
 * @param expectedResult - ожидаемый результат метода isEven
 * @param assertMessage  - сообщение выводимое в случае fail
 */
record IsEvenTestData(
        int n,
        boolean expectedResult
        , String assertMessage
) {
}

/**
 * Структура тестовых данных для параметризованного теста {@link LogicTasksTest#checkAccessParametrizedTest(CheckAccessTestData)}
 *
 * @param age            - возраст для параметра age
 * @param expectedResult - ожидаемый результат метода checkAccess
 * @param assertMessage  - сообщение выводимое в случае fail
 */
record CheckAccessTestData(
        int age,
        String expectedResult
        , String assertMessage
) {
}

/**
 * Структура тестовых данных для параметризованного теста {@link LogicTasksTest#isPositiveParametrizedTest(IsPositiveTestData)}
 *
 * @param n              - проверяемое число для параметра n
 * @param expectedResult - ожидаемый результат метода isPositive
 * @param assertMessage  - сообщение выводимое в случае fail
 */
record IsPositiveTestData(
        int n,
        boolean expectedResult
        , String assertMessage
) {
}

/**
 * Структура тестовых данных для параметризованного теста {@link LogicTasksTest#getGradeParametrizedTest(GetGradeTestData)}
 *
 * @param score          - баллы от 0 до 100 для параметра score
 * @param expectedResult - ожидаемый результат метода getGrade
 * @param assertMessage  - сообщение выводимое в случае fail
 */
record GetGradeTestData(
        int score,
        String expectedResult
        , String assertMessage
) {
}


class LogicTasksTest {

    /*====================[Простые тесты]====================*/

    @Test
    @DisplayName("Простые тесты метода isEven")
    @Tag("SimpleTest")
    void isEvenTest() {
        Random random = new Random();
        int value;

        // Положительные числа
        value = random.nextInt(50) * 2; // случайное четное число
        assertTrue(LogicTasks.isEven(value), String.valueOf(value) + " должно быть четным");
        value = random.nextInt(50) * 2 + 1; // случайное не четное число
        assertFalse(LogicTasks.isEven(value), value + " не должно быть четным");

        // Ноль
        assertTrue(LogicTasks.isEven(0), "0 должен быть четным");

        // Отрицательные числа
        value = -random.nextInt(50) * 2; // случайное четное число
        assertTrue(LogicTasks.isEven(value), value + " должно быть четным");
        value = -random.nextInt(50) * 2 + 1; // случайное не четное число
        assertFalse(LogicTasks.isEven(value), value + " не должно быть четным");
    }

    @Test
    @DisplayName("Простые тесты метода checkAccess")
    @Tag("SimpleTest")
    void checkAccessTest() {
        Random random = new Random();
        int value;

        // Меньше 18
        value = random.nextInt(0, 18);
        assertEquals("Denied", LogicTasks.checkAccess(value), "Для " + value + " доступ должен быть Denied");

        // Граничное значение 18 (в условии строго больше 'age > 18')
        assertEquals("Denied", LogicTasks.checkAccess(18), "Для 18 доступ должен быть Denied");

        // Больше 18
        value = random.nextInt(19, 100);
        assertEquals("Allowed", LogicTasks.checkAccess(value), "Для " + value + " доступ должен быть Allowed");
    }

    @Test
    @DisplayName("Простые тесты метода isPositive")
    @Tag("SimpleTest")
    void isPositiveTest() {
        Random random = new Random();
        int value;

        // Положительные числа
        value = random.nextInt(1, 100);
        assertTrue(LogicTasks.isPositive(value), value + " должно быть positive");

        // Ноль (в условии 'больше или равно 0')
        assertTrue(LogicTasks.isPositive(0), "0 должен быть positive");

        // Отрицательные числа
        value = -random.nextInt(1, 100);
        assertFalse(LogicTasks.isPositive(value), value + " не должно быть positive");
    }

    @Test
    @DisplayName("Простые тесты метода getGrade")
    @Tag("SimpleTest")
    void getGradeTest() {
        Random random = new Random();
        int value;

        // Границы диапазона E (0-20)
        assertEquals("E", LogicTasks.getGrade(0));
        value = random.nextInt(1, 20);
        assertEquals("E", LogicTasks.getGrade(value));
        assertEquals("E", LogicTasks.getGrade(20));

        // Границы диапазона D (21-40)
        assertEquals("D", LogicTasks.getGrade(21));
        value = random.nextInt(22, 40);
        assertEquals("D", LogicTasks.getGrade(value));
        assertEquals("D", LogicTasks.getGrade(40));

        // Границы диапазона C (41-60)
        assertEquals("C", LogicTasks.getGrade(41));
        value = random.nextInt(42, 60);
        assertEquals("C", LogicTasks.getGrade(value));
        assertEquals("C", LogicTasks.getGrade(60));

        // Границы диапазона B (61-80)
        assertEquals("B", LogicTasks.getGrade(61));
        value = random.nextInt(62, 80);
        assertEquals("B", LogicTasks.getGrade(value));
        assertEquals("B", LogicTasks.getGrade(80));

        // Границы диапазона A (81-100)
        assertEquals("A", LogicTasks.getGrade(81));
        value = random.nextInt(82, 100);
        assertEquals("A", LogicTasks.getGrade(value));
        assertEquals("A", LogicTasks.getGrade(100));

//        // Исключение IllegalArgumentException при отрицательных баллах
//        assertThrows(IllegalArgumentException.class, () -> LogicTasks.getGrade(-1),
//                "Должно быть исключение IllegalArgumentException при отрицательных баллах");
//        // Исключение IllegalArgumentException при баллах больше 100
//        assertThrows(IllegalArgumentException.class, () -> LogicTasks.getGrade(101),
//                "Должно быть исключение IllegalArgumentException при баллах больше 100");
        // Если переданное число не входит в границы — вернуть строку Error.
        value = -random.nextInt(1, 100);
        assertEquals("Error", LogicTasks.getGrade(value));
        value = random.nextInt(101, 1000);
        assertEquals("Error", LogicTasks.getGrade(value));
    }


    /*====================[Повторяющиеся тесты]====================*/

    @RepeatedTest(value = 10, name = "тест {currentRepetition} из {totalRepetitions}")
    @DisplayName("Повторяющиеся тесты метода isEven")
    @Tag("RepeatedTest")
    void isEvenRepeatedTest() {
        Random random = new Random();
        int value;

        // Положительные числа
        value = random.nextInt(50) * 2; // случайное четное число
        assertTrue(LogicTasks.isEven(value), String.valueOf(value) + " должно быть четным");
        value = random.nextInt(50) * 2 + 1; // случайное не четное число
        assertFalse(LogicTasks.isEven(value), value + " не должно быть четным");

        // Ноль
        assertTrue(LogicTasks.isEven(0), "0 должен быть четным");

        // Отрицательные числа
        value = -random.nextInt(50) * 2; // случайное четное число
        assertTrue(LogicTasks.isEven(value), value + " должно быть четным");
        value = random.nextInt(50) * 2 + 1; // случайное не четное число
        assertFalse(LogicTasks.isEven(value), value + " не должно быть четным");
    }

    @RepeatedTest(value = 10, name = "тест {currentRepetition} из {totalRepetitions}")
    @DisplayName("Повторяющиеся тесты метода checkAccess")
    @Tag("RepeatedTest")
    void checkAccessRepeatedTest() {
        Random random = new Random();
        int value;

        // Меньше 18
        value = random.nextInt(0, 18);
        assertEquals("Denied", LogicTasks.checkAccess(value), "Для " + value + " доступ должен быть Denied");

        // Граничное значение 18 (в условии строго больше 'age > 18')
        assertEquals("Denied", LogicTasks.checkAccess(18), "Для 18 доступ должен быть Denied");

        // Больше 18
        value = random.nextInt(19, 100);
        assertEquals("Allowed", LogicTasks.checkAccess(value), "Для " + value + " доступ должен быть Allowed");
    }

    @RepeatedTest(value = 10, name = "тест {currentRepetition} из {totalRepetitions}")
    @DisplayName("Повторяющиеся тесты метода isPositive")
    @Tag("RepeatedTest")
    void isPositiveRepeatedTest() {
        Random random = new Random();
        int value;

        // Положительные числа
        value = random.nextInt(1, 100);
        assertTrue(LogicTasks.isPositive(value), value + " должно быть positive");

        // Ноль (в условии 'больше или равно 0')
        assertTrue(LogicTasks.isPositive(0), "0 должен быть positive");

        // Отрицательные числа
        value = -random.nextInt(1, 100);
        assertFalse(LogicTasks.isPositive(value), value + " не должно быть positive");
    }

    @RepeatedTest(value = 10, name = "тест {currentRepetition} из {totalRepetitions}")
    @DisplayName("Повторяющиеся тесты метода getGrade")
    @Tag("RepeatedTest")
    void getGradeRepeatedTest() {
        Random random = new Random();
        int value;

        // Границы диапазона E (0-20)
        assertEquals("E", LogicTasks.getGrade(0));
        value = random.nextInt(1, 20);
        assertEquals("E", LogicTasks.getGrade(value));
        assertEquals("E", LogicTasks.getGrade(20));

        // Границы диапазона D (21-40)
        assertEquals("D", LogicTasks.getGrade(21));
        value = random.nextInt(22, 40);
        assertEquals("D", LogicTasks.getGrade(value));
        assertEquals("D", LogicTasks.getGrade(40));

        // Границы диапазона C (41-60)
        assertEquals("C", LogicTasks.getGrade(41));
        value = random.nextInt(42, 60);
        assertEquals("C", LogicTasks.getGrade(value));
        assertEquals("C", LogicTasks.getGrade(60));

        // Границы диапазона B (61-80)
        assertEquals("B", LogicTasks.getGrade(61));
        value = random.nextInt(62, 80);
        assertEquals("B", LogicTasks.getGrade(value));
        assertEquals("B", LogicTasks.getGrade(80));

        // Границы диапазона A (81-100)
        assertEquals("A", LogicTasks.getGrade(81));
        value = random.nextInt(82, 100);
        assertEquals("A", LogicTasks.getGrade(value));
        assertEquals("A", LogicTasks.getGrade(100));

//        // Исключение IllegalArgumentException при отрицательных баллах
//        assertThrows(IllegalArgumentException.class, () -> LogicTasks.getGrade(-1),
//                "Должно быть исключение IllegalArgumentException при отрицательных баллах");
//        // Исключение IllegalArgumentException при баллах больше 100
//        assertThrows(IllegalArgumentException.class, () -> LogicTasks.getGrade(101),
//                "Должно быть исключение IllegalArgumentException при баллах больше 100");
        // Если переданное число не входит в границы — вернуть строку Error.
        value = -random.nextInt(1, 100);
        assertEquals("Error", LogicTasks.getGrade(value));
        value = random.nextInt(101, 1000);
        assertEquals("Error", LogicTasks.getGrade(value));
    }


    /*====================[Параметризованные тесты]====================*/

    /**
     * Метод предоставляет данные для параметризованного теста isEven
     *
     * @return - возвращает список структур
     */
    static private List<IsEvenTestData> isEvenParametrizedTestData() {
        List<IsEvenTestData> dataList = new ArrayList<>();

        Random random = new Random();
        int value;

        // Положительные числа
        value = random.nextInt(50) * 2; // случайное четное число
        dataList.add(new IsEvenTestData(value, true, String.valueOf(value) + " должно быть четным"));  // случайное четное число
        value = random.nextInt(50) * 2 + 1; // случайное не четное число
        dataList.add(new IsEvenTestData(value, false, value + " не должно быть четным"));  // случайное не четное число

        dataList.add(new IsEvenTestData(0, true, "0 должен быть четным"));  // Ноль

        // Отрицательные числа
        value = -random.nextInt(50) * 2; // случайное четное число
        dataList.add(new IsEvenTestData(value, true, value + " должно быть четным"));  // случайное четное число
        value = -random.nextInt(50) * 2 + 1; // случайное не четное число
        dataList.add(new IsEvenTestData(-random.nextInt(50) * 2 + 1, false, value + " не должно быть четным"));  // случайное не четное число

        return dataList;
    }

    /**
     * Параметризованный тест метода isEven
     *
     * @param data - структура тестовых данных для параметризованного теста
     */
    @ParameterizedTest
    @MethodSource("isEvenParametrizedTestData")
    @DisplayName("Параметризованный тест метода isEven")
    @Tag("ParameterizedTest")
    void isEvenParametrizedTest(IsEvenTestData data) {
        assertEquals(data.expectedResult(), LogicTasks.isEven(data.n()));
    }

    /**
     * Метод предоставляет данные для параметризованного теста checkAccess
     *
     * @return - возвращает список структур
     */
    static private List<CheckAccessTestData> checkAccessParametrizedTestData() {
        List<CheckAccessTestData> dataList = new ArrayList<>();

        Random random = new Random();
        int value;

        // Меньше 18
        value = random.nextInt(0, 18);
        dataList.add(new CheckAccessTestData(value, "Denied", "Для " + value + " доступ должен быть Denied"));

        // Граничное значение 18 (в условии строго больше 'age > 18')
        dataList.add(new CheckAccessTestData(18, "Denied", "Для 18 доступ должен быть Denied"));

        // Больше 18
        value = random.nextInt(19, 100);
        dataList.add(new CheckAccessTestData(random.nextInt(19, 100), "Allowed", "Для " + value + " доступ должен быть Allowed"));

        return dataList;
    }

    /**
     * Параметризованный тест метода checkAccess
     *
     * @param data - структура тестовых данных для параметризованного теста
     */
    @ParameterizedTest
    @MethodSource("checkAccessParametrizedTestData")
    @DisplayName("Параметризованный тест метода checkAccess")
    @Tag("ParameterizedTest")
    void checkAccessParametrizedTest(CheckAccessTestData data) {
        assertEquals(data.expectedResult(), LogicTasks.checkAccess(data.age()));
    }

    /**
     * Метод предоставляет данные для параметризованного теста isPositive
     *
     * @return - возвращает список структур
     */
    static private List<IsPositiveTestData> isPositiveParametrizedTestData() {
        List<IsPositiveTestData> dataList = new ArrayList<>();

        Random random = new Random();
        int value;

        // Положительные числа
        for(int i = 0; i < 5; i++) {
            value = random.nextInt(1, 100);
            dataList.add(new IsPositiveTestData(value, true, value + " должно быть positive"));
        }

        // Ноль (в условии 'больше или равно 0')
        dataList.add(new IsPositiveTestData(0, true, "0 должен быть positive"));

        // Отрицательные числа
        for(int i = 0; i < 5; i++) {
            value = -random.nextInt(1, 100);
            dataList.add(new IsPositiveTestData(value, false, value + " не должно быть positive"));
        }

        return dataList;
    }

    /**
     * Параметризованный тест метода isPositive
     *
     * @param data - структура тестовых данных для параметризованного теста
     */
    @ParameterizedTest
    @MethodSource("isPositiveParametrizedTestData")
    @DisplayName("Параметризованный тест метода isPositive")
    @Tag("ParameterizedTest")
    void isPositiveParametrizedTest(IsPositiveTestData data) {
        assertEquals(data.expectedResult(), LogicTasks.isPositive(data.n()));
    }

    /**
     * Метод предоставляет данные для параметризованного теста getGrade
     *
     * @return - возвращает список структур
     */
    static private List<GetGradeTestData> getGradeParametrizedTestData() {
        List<GetGradeTestData> dataList = new ArrayList<>();

        Random random = new Random();

        // Границы диапазона E (0-20)
        dataList.add(new GetGradeTestData(0, "E", "Границы диапазона E (0-20)"));
        dataList.add(new GetGradeTestData(random.nextInt(1, 20), "E", "Границы диапазона E (0-20)"));
        dataList.add(new GetGradeTestData(20, "E", "Границы диапазона E (0-20)"));

        // Границы диапазона D (21-40)
        dataList.add(new GetGradeTestData(21, "D", "Границы диапазона D (21-40)"));
        dataList.add(new GetGradeTestData(random.nextInt(22, 40), "D", "Границы диапазона D (21-40)"));
        dataList.add(new GetGradeTestData(40, "D", "Границы диапазона D (21-40)"));

        // Границы диапазона C (41-60)
        dataList.add(new GetGradeTestData(41, "C", "Границы диапазона C (41-60)"));
        dataList.add(new GetGradeTestData(random.nextInt(42, 60), "C", "Границы диапазона C (41-60)"));
        dataList.add(new GetGradeTestData(60, "C", "Границы диапазона C (41-60)"));

        // Границы диапазона B (61-80)
        dataList.add(new GetGradeTestData(61, "B", "Границы диапазона B (61-80)"));
        dataList.add(new GetGradeTestData(random.nextInt(62, 80), "B", "Границы диапазона B (61-80)"));
        dataList.add(new GetGradeTestData(80, "B", "Границы диапазона B (61-80)"));

        // Границы диапазона A (81-100)
        dataList.add(new GetGradeTestData(81, "A", "Границы диапазона A (81-100)"));
        dataList.add(new GetGradeTestData(random.nextInt(82, 100), "A", "Границы диапазона A (81-100)"));
        dataList.add(new GetGradeTestData(100, "A", "Границы диапазона A (81-100)"));

        // Если переданное число не входит в границы — вернуть строку Error.
        dataList.add(new GetGradeTestData(-random.nextInt(1, 100), "Error", "переданное число не входит в границы"));
        dataList.add(new GetGradeTestData(random.nextInt(101, 1000), "Error", "переданное число не входит в границы"));

        return dataList;
    }

    /**
     * Параметризованный тест метода getGrade
     *
     * @param data - структура тестовых данных для параметризованного теста
     */
    @ParameterizedTest
    @MethodSource("getGradeParametrizedTestData")
    @DisplayName("Параметризованный тест метода getGrade")
    @Tag("ParameterizedTest")
    void getGradeParametrizedTest(GetGradeTestData data) {
        assertEquals(data.expectedResult(), LogicTasks.getGrade(data.score()));
    }

}
