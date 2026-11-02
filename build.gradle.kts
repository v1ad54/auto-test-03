plugins {
    id("java")
}

group = "ru.v1ad54"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // Source: https://mvnrepository.com/artifact/org.assertj/assertj-core
    implementation("org.assertj:assertj-core:3.27.7")
}

tasks.test {
    useJUnitPlatform()

    // Стандартные выводы внутри тестов направляем в консоль
    testLogging {
        showStandardStreams = true
    }
}

tasks.register<Test>("SimpleTest") {
    group = "my_test_tasks"
    description = "Задача запускает простые тесты."

    // Стандартные выводы внутри тестов направляем в консоль
    testLogging {
        showStandardStreams = true
    }

    useJUnitPlatform {
        includeTags("SimpleTest")
    }

    // Чтобы тесты всегда прогонялись заново, иначе не запускаются уже пройденные тесты
    outputs.upToDateWhen { false }

    // Зададим пути к тестовым и связанным классам
    val testSourceSet = sourceSets.test.get()
    testClassesDirs = testSourceSet.output.classesDirs
    classpath = testSourceSet.runtimeClasspath

}

tasks.register<Test>("RepeatedTest") {
    group = "my_test_tasks"
    description = "Задача запускает повторяющиеся тесты."

    // Стандартные выводы внутри тестов направляем в консоль
    testLogging {
        showStandardStreams = true
    }

    useJUnitPlatform {
        includeTags("RepeatedTest")
    }

    // Чтобы тесты всегда прогонялись заново, иначе не запускаются уже пройденные тесты
    outputs.upToDateWhen { false }

    // Зададим пути к тестовым и связанным классам
    val testSourceSet = sourceSets.test.get()
    testClassesDirs = testSourceSet.output.classesDirs
    classpath = testSourceSet.runtimeClasspath

}

tasks.register<Test>("ParameterizedTest") {
    group = "my_test_tasks"
    description = "Задача запускает параметризованные тесты."

    // Стандартные выводы внутри тестов направляем в консоль
    testLogging {
        showStandardStreams = true
    }

    useJUnitPlatform {
        includeTags("ParameterizedTest")
    }

    // Чтобы тесты всегда прогонялись заново, иначе не запускаются уже пройденные тесты
    outputs.upToDateWhen { false }

    // Зададим пути к тестовым и связанным классам
    val testSourceSet = sourceSets.test.get()
    testClassesDirs = testSourceSet.output.classesDirs
    classpath = testSourceSet.runtimeClasspath

}

tasks.register<Test>("AllTest") {
    group = "my_test_tasks"
    description = "Задача запускает все тесты."

    // Стандартные выводы внутри тестов направляем в консоль
    testLogging {
        showStandardStreams = true
    }

    useJUnitPlatform()

    // Чтобы тесты всегда прогонялись заново, иначе не запускаются уже пройденные тесты
    outputs.upToDateWhen { false }

    // Зададим пути к тестовым и связанным классам
    val testSourceSet = sourceSets.test.get()
    testClassesDirs = testSourceSet.output.classesDirs
    classpath = testSourceSet.runtimeClasspath

}
