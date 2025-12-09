class App() {

    val archives: MutableList<Archive> = mutableListOf()

    fun start() {
        val navigation = Navigation(this)
        navigation.start()
    }
}



