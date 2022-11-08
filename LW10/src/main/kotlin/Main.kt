import java.lang.Exception
import java.util.ArrayList

enum class Day(val holiday: String){
    NewYear("New Year"),
    WomanDay("Woman Day"),
    VictoryDay("Victory Day"),
}

fun main() {
    // Задание 1. Конвертировать
    //StringDemo.main();

    // Задание 2. Определение переменных и констант
    //Variables()

    // Задание 3. Функции, функции расширения
    println(IsValid("bad", "").toString())
    HolidaysFromEnum()
    val opResult: Double = doOperation(10, 6, '*')
    println(opResult)
    val max: Int? = indexOfMax(intArrayOf(1, 2, 3, 4, 5))
    println("Max Index: " + max)
    println("abcde".coincidence("abc"))

    // Задание 4. Циклы и диапазоны
    // с циклом и диапозоном
    //println(factorialLoopRange(5))
    // на основе индуктивного определения факториала
    //println(factorialInductive(5))
    //CheckPrime()

    // Задание 5. Коллекции и лямбды
    //listActions()
    //progressMap()
}

fun Variables() {
    val one = 1                   // Int
    val threeBillion = 3000000000 // Long
    val oneLong = 1L              // Long
    val oneByte: Byte = 1         // Byte
    val str: String = "123"       // String
    val intVal: Int = 12          // Int

    val toInt: Int = str.toInt()
    val toStr: String = intVal.toString()
    println("String is $str\nInt is $toInt")

    println("Введите значение: ")
    var newInt: Int?
    newInt = readLine().toString().toInt()
    println(newInt)
}

fun IsValid(login: String, password: String): Boolean {
    fun notNull() = login.length > 0 && password.length > 0

    if(!notNull()){
        println("Логин и пароль не должны быть пустыми")
        return false
    }
    if(password.length<6 || password.length>12){
        println("Пароль должен быть 6-12 символов")
        return false
    }
    if(password.contains(' ')){
        println("В пароле не допускаются пробелы")
        return false
    }
    if(!login.contains('@')){
        println("Не корректная почта")
        return false
    }
    return true
}

fun HolidaysFromEnum() {
    print("Введите дату: ")
    var Date: String? = readLine()
    var regEx = Regex("[0-3][0-9][.][0-2][1-9]")

    if(!Date.toString().matches(regEx)) {
        println("Не дата (формат XX.XX)")
        return
    }

    when(Date){
        "31.01" -> println(Day.NewYear.holiday)
        "08.03" -> println(Day.WomanDay.holiday)
        "09.05" -> println(Day.VictoryDay.holiday)
        "" -> println("Пустая строка")
        else -> println("Не праздник")
    }
}

fun doOperation (a:Int , b:Int, operation:Char): Double {
    when(operation){
        '+' -> return (a + b).toDouble()
        '-' -> return (a - b).toDouble()
        '*' -> return (a * b).toDouble()
        '/' -> return (a / b).toDouble()
        '%' -> return (a % b).toDouble()
        else -> throw Exception("Это не оператор")
    }
}

fun indexOfMax(a: IntArray): Int? {
    var max: Int = 0
    var maxIndex: Int = 0

    for(i in 1 until a.size){
        if(a[i] > max){
            max = a[i]
            maxIndex = i
        }
    }
    return maxIndex
}

fun IntArray.indexOfMaxExtension(): Int?{
    var max: Int = 0
    var maxIndex: Int = 0

    for(i in 1 until this.size){
        if(this[i]>max){
            max = this[i]
            maxIndex = i
        }
    }
    return maxIndex
}

fun String.coincidence(str :String): Int?{
    var num: Int = 0
    for(ch in str)
        if(this.contains(ch))
            num++
    return num
}

fun factorialLoopRange(n: Int): Double{
    var factorial: Double = 1.0
    val range = 1..n

    for(i in range)
        factorial *= i
    return factorial
}

fun factorialInductive(n: Int): Double = if (n < 2) 1.0 else n * factorialInductive(n - 1)

fun isPrime(value: Int): Boolean{
    for(i in 2..value - 1)
        if(value % i == 0)
            return false
    return true
}

fun CheckPrime(){
    var counter:Int = 0
    var arrayCounter = 0
    var firstElements = mutableListOf<Int>()
    val lastElements = arrayOfNulls<Int>(10)

    for(i in 2 until 10000){
        if(isPrime(i)) {
            if(counter<20){
                firstElements.add(i)
                counter++
            }
            else if (counter>=20 && arrayCounter<10 ){
                lastElements[arrayCounter] = i
                arrayCounter++
            }
        }
    }

    println(firstElements)
    for(i in 0..9)
        println(lastElements[i])
}

fun listActions(){
    val numbers : ArrayList<Int> = arrayListOf(1, 2, 3, 4, 5)
    numbers.add(6)
    numbers += 7

    for(i in numbers)
        println(i)
    val distinct = numbers.distinct().toList()
    println(distinct.filter(::isPrime))

    println(numbers.groupBy { it % 2 == 0 })
    println(numbers.find { it < 4 })
    println(numbers.all { it < 7 })
    println(numbers.any { it > 10 })

    val (num1, num2) = numbers
    println(num1)
    println(num2)
}

fun progressMap(){
    val Progress = mutableMapOf("Dima" to 38,
                                "Anton" to 1,
                                "Maksim" to 5,
                                "Ivan" to 20,
                                "Aleks" to 40);
    var minusMark:Int = 0

    for(person in Progress){
        if(person.value == 40)
            Progress.set(person.key, 10)

        else if(person.value == 39)
            Progress.set(person.key, 9)

        else if(person.value == 38)
            Progress.set(person.key, 8)

        else if(person.value <= 37 && person.value >= 35)
            Progress.set(person.key, 7)

        else if(person.value <= 34 && person.value >= 32)
            Progress.set(person.key, 6)

        else if(person.value <= 31 && person.value >= 29)
            Progress.set(person.key, 5)

        else if(person.value <= 28 && person.value >= 25)
            Progress.set(person.key, 4)

        else if(person.value <= 24 && person.value >= 22){
            Progress.set(person.key, 3)
            minusMark++
        }

        else if(person.value <= 21 && person.value >= 19) {
            Progress.set(person.key, 2)
            minusMark++
        }

        else if(person.value <= 18 && person.value >= 0){
            Progress.set(person.key, 1)
            minusMark++
        }
    }

    for (person in Progress)
        println(person.key + " Оценка: " + person.value)
    println("Кол-во неудов: " + minusMark)
}