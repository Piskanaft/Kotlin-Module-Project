import java.util.Scanner

class Navigation(val app: App) {
    val scanner = Scanner(System.`in`)
    var state: State = State.ARCHIVES
    var selectedArchive: Int = 0
    var selectedNote: Int = 0

    fun start() {
        while (true) {
            printState()
            readInput()
        }
    }

    fun installState(newState: State) {
        if (newState == State.EXIT) {
            kotlin.system.exitProcess(0)
        }
        state = newState
    }

    fun getValidNumber(max: Int): Int {
        var choice: Int? = null
        do {
            choice = readNumber(max)
            if (choice == null) {
                printState()
            }
        } while (choice == null)
        return choice
    }

    fun readNumber(max: Int): Int? {
        print("> ")

        val n = scanner.nextLine()?.toIntOrNull()

        if (n == null) {
            println("Нужно ввести число")
            return null
        }
        if (n in 0..max) {
            return n
        } else {
            println("Введено неверное число. Введите ${if (max == 0) "0" else "число из диапазона 0..$max"}")
            return null
        }
    }

    fun getValidText(prompt: String): String {
        var text: String? = null
        do {
            println(prompt)
            text = readText()
            if (text == null) {
                printState()
            }
        } while (text == null)
        return text
    }

    fun readText(): String? {
        print("> ")
        val text = scanner.nextLine()?.trim()
        if (text != null && text.isNotBlank()) return text
        println("Поле не может быть пустым.")
        return null
    }

    fun readInput() {

        when (state) {
            State.ARCHIVES -> {
                val max = app.archives.size + 1
                val choice = getValidNumber(max)

                when (choice) {
                    0 -> installState(state.onBack)
                    max -> installState(State.ARCHIVE_CREATE)
                    else -> {
                        selectedArchive = choice - 1
                        installState(State.ARCHIVE_INSPECT)
                    }
                }
            }

            State.ARCHIVE_INSPECT -> {
                val max = app.archives[selectedArchive].notes.size + 1
                val choice = getValidNumber(max)

                when (choice) {
                    0 -> installState(state.onBack)
                    max -> installState(State.NOTE_CREATE)
                    else -> {
                        selectedNote = choice - 1; installState(State.NOTE_INSPECT)
                    }
                }
            }

            State.ARCHIVE_CREATE -> {
                val name = getValidText("Введите название архива")
                app.archives.add(Archive(name))
                installState(state.onBack)
            }

            State.NOTE_INSPECT -> {
                val choice = getValidNumber(0)
                if (choice == 0) installState(state.onBack)
            }

            State.NOTE_CREATE -> {
                val name = getValidText("Введите название заметки")
                val content = getValidText("Введите содержание заметки")
                app.archives[selectedArchive].notes.add(Note(name, content))
                println("Заметка создана")
                installState(state.onBack)
            }

            else -> ""
        }
    }

    fun printState() {
        println(state.title)

        when (state) {
            State.ARCHIVES -> {
                println("0. Выход")
                app.archives.forEachIndexed { index, archive ->
                    println("${index + 1}. ${archive.title}")
                }
                println("${app.archives.size + 1}. Создать архив")
            }

            State.ARCHIVE_INSPECT -> {
                println("0. Назад")
                val archive = app.archives[selectedArchive]
                archive.notes.forEachIndexed { index, note ->
                    println("${index + 1}. ${note.title}")
                }
                println("${archive.notes.size + 1}. Добавить заметку")
            }

            State.NOTE_INSPECT -> {
                println("0. Назад")
                val note = app.archives[selectedArchive].notes[selectedNote]
                println("Название: " + note.title)
                println("Содержание: " + note.content)
            }

            else -> {}
        }
    }
}
