package day1

fun main(args: Array<String>) {
    println("hello world")

    val filename = "input.txt"
    val resource = object {}.javaClass.getResource(filename)
    println(resource)
    val text = resource.readText()
    println(text)

//    Day1.javaClass.
//
//    getClass
//    val file = File("input.txt")
//    val text = file.readText()
//    println(text)
}