import java.util.InputMismatchException
import java.util.Scanner

val scanner: Scanner = Scanner(System.`in`)
class NotesApp{
    private val archives: ArrayList<Archive> = ArrayList()
    private val menu: Printer = Printer()
    private val listener: Listener = Listener()
    fun start(){
        while (true){
            menu.printList("Список архивов:", "0. Создать архив", -1)
            var input = listener.listen()
            when (input){
                -1 -> {
                    scanner.nextLine()
                    continue
                }
                0 -> {
                    menu.printCreateArchive()
                    continue
                }
                archives.size+1 -> break
                else -> {
                    while (true) {
                        val pos: Int = input - 1
                        menu.printList("Список заметок для архива \"${archives[pos].name}\"", "0. Создать заметку", pos)
                        var input_ = listener.listen(archives[pos])
                        when (input_) {
                            -1 -> {
                                scanner.nextLine()
                                continue
                            }
                            0 -> {
                                menu.printCreateNote(archives[pos])
                                continue
                            }
                            archives[pos].notes.size+1 -> break
                            else ->{
                                val pos_ = input_ - 1
                                menu.printNote(archives[pos].notes[pos_])
                                continue
                            }
                        }
                    }
                }
            }
        }
    }

    private inner class Printer() : Printable{
        fun printList(header: String, first: String, pos: Int = -1){
            println(header)
            println(first)
            var i = 0
            if (pos == -1) i = this.printElements()
            else i = archives[pos].printElements()
            println("${i}. Выход")

        }


        fun printNote(note: Note){
            println("Содержание заметки \"${note.name}\": ")
            println(note.info)
        }


        fun printCreateArchive(){
            println("Создание нового архива:")
            var name: String = ""
            scanner.nextLine()
            while (true){
                println("Введите название нового архива: ")
                name = scanner.nextLine()
                if (name.isNullOrBlank()){
                    println("Нельзя создать архив с пустым именем. Попробуйте еще раз")
                    continue
                }
                if (archives.map { it.name }.contains(name)) {
                    println("Архив с таким названием уже существует. Попробуйте еще раз.")
                    continue
                }
                break
            }
            archives.add(Archive(name))
        }

        fun printCreateNote(archive: Archive){
            println("Создание новой заметки:")
            var name: String = ""
            scanner.nextLine()
            while (true){
                println("Введите название новой заметки: ")
                name = scanner.nextLine()
                if (name.isNullOrBlank()){
                    println("Нельзя создать заметку с пустым именем. Попробуйте еще раз")
                    continue
                }
                if (archive.notes.map { it.name }.contains(name)) {
                    println("Заметка с таким названием уже существует. Попробуйте еще раз.")
                    continue
                }
                break
            }
            var info: String = ""
            while (true){
                println("Введите содержание для заметки \"$name\"")
                info = scanner.nextLine()
                if (info.isNullOrBlank()){
                    println("Нельзя создать заметку с пустым содержанием. Попробуйте еще раз")
                    continue
                }
                break
            }
            archive.notes.add(Note(name, info))
        }

        override fun printElements(): Int {
            var i = 0
            for (n in archives) println("${++i}. ${n.name}")
            return ++i
        }

    }

    private inner class Listener(){

        fun listen(archive: Archive? = null): Int{
            while (!scanner.hasNextLine()){
            }
            try {
                val input = scanner.nextInt()
                if (archive != null){
                    if (input < 0 || (input > archive.notes.size+1)) {
                        println("Такого пункта меню не существует. Попробуйте еще раз.")
                        return -1
                    }
                }
                else if (input < 0 || (input > archives.size+1)) {
                    println("Такого пункта меню не существует. Попробуйте еще раз.")
                    return -1
                }
                return input
            }
            catch (e: InputMismatchException) {
                println("Для выбора пункта меню необходимо ввести число. Попробуйте еще раз.")
                return -1
            }
        }

    }



}