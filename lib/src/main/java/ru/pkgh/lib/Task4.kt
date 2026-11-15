package ru.pkgh.lab4

data class Lesson(
    val time: String,
    val subject: String
)

fun main() {
    // Порядок дней недели для корректной сортировки
    val daysOrder = listOf(
        "Понедельник", "Вторник", "Среда",
        "Четверг", "Пятница", "Суббота", "Воскресенье"
    )

    // Словарь: Ключ — день недели, Значение — список занятий на этот день
    val schedule = mutableMapOf<String, MutableList<Lesson>>()

    while (true) {
        println("\n=== МЕНЮ РАСПИСАНИЯ ===")
        println("1. Добавить занятие")
        println("2. Показать расписание на конкретный день")
        println("3. Показать всю неделю по порядку дней")
        println("0. Выход")
        print("Выберите действие: ")

        when (readlnOrNull()?.trim()) {
            "1" -> {
                val day = selectDay(daysOrder) ?: continue

                print("Введите время занятия (например, 09:00): ")
                val time = readlnOrNull()?.trim().orEmpty()
                if (time.isEmpty()) {
                    println("Ошибка: время не может быть пустым.")
                    continue
                }

                print("Введите название предмета: ")
                val subject = readlnOrNull()?.trim().orEmpty()
                if (subject.isEmpty()) {
                    println("Ошибка: название не может быть пустым.")
                    continue
                }

                // Добавляем занятие в список нужного дня
                val dayLessons = schedule.getOrPut(day) { mutableListOf() }
                dayLessons.add(Lesson(time, subject))
                println("Занятие добавлено: $day, $time — $subject")
            }

            "2" -> {
                if (schedule.isEmpty()) {
                    println("Расписание пока пустое.")
                    continue
                }
                val day = selectDay(daysOrder) ?: continue
                val lessons = schedule[day]

                if (lessons.isNullOrEmpty()) {
                    println("На день «$day» занятий нет.")
                } else {
                    println("\n--- Расписание на $day ---")
                    // Сортируем занятия по времени перед выводом
                    lessons.sortedBy { it.time }.forEach { lesson ->
                        println("${lesson.time} — ${lesson.subject}")
                    }
                }
            }

            "3" -> {
                if (schedule.isEmpty()) {
                    println("Расписание на всю неделю пустое.")
                    continue
                }
                println("\n=== РАСПИСАНИЕ НА НЕДЕЛЮ ===")
                // Идём строго по списку daysOrder, чтобы сохранить порядок дней
                for (day in daysOrder) {
                    val lessons = schedule[day]
                    if (!lessons.isNullOrEmpty()) {
                        println("\n[$day]")
                        lessons.sortedBy { it.time }.forEach { lesson ->
                            println("  ${lesson.time} — ${lesson.subject}")
                        }
                    }
                }
            }

            "0" -> {
                println("Выход из программы.")
                break
            }

            else -> println("Некорректный ввод. Введите цифру от 0 до 3.")
        }
    }
}

// Вспомогательная функция для выбора дня недели с валидацией
fun selectDay(daysOrder: List<String>): String? {
    println("Выберите день недели:")
    daysOrder.forEachIndexed { index, day ->
        println("${index + 1}. $day")
    }
    print("Номер дня (1-7): ")
    val input = readlnOrNull()?.trim()?.toIntOrNull()
    if (input == null || input !in 1..7) {
        println("Ошибка: нужно ввести число от 1 до 7.")
        return null
    }
    return daysOrder[input - 1]
}
