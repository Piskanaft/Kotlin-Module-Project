enum class State(val title: String) {
    EXIT(""),
    ARCHIVES("Список архивов"),
    ARCHIVE_INSPECT("Список заметок"),
    ARCHIVE_CREATE("Создание архива"),
    NOTE_INSPECT("Обзор заметки"),
    NOTE_CREATE("Создание новой заметки");

    val onBack: State
        get() = when (this) {
            ARCHIVES -> EXIT
            ARCHIVE_INSPECT -> ARCHIVES
            ARCHIVE_CREATE -> ARCHIVES
            NOTE_INSPECT -> ARCHIVE_INSPECT
            NOTE_CREATE -> ARCHIVE_INSPECT
            else -> EXIT

        }
}


