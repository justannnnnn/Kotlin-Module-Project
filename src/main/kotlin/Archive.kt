class Archive(val name: String) : Printable {
    val notes: ArrayList<Note> = ArrayList()
    override fun printElements(): Int {
        var i = 0
        for (n in notes) println("${++i}. ${n.name}")
        return ++i
    }
}