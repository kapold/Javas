fun main() {
    var KING: ChessFigure = ChessFigure("KING", "WHITE", Positions.A1)

    KING.MoveUp();
    KING.MoveDown();
    KING.MoveDown();
    KING.MoveUp();
    KING.MoveUp();

    KING.figureCondition.isCanGo = true;
    KING.figureCondition.isBattle = true;

    KING.getFigureInfo();

    val a = A();
    a.display();
    a.converter("*");
}

fun ChessFigure.clearPosition(cf: ChessFigure) {
    cf.position = null;
}

interface IChessFigure {
    var color: String
    var position: Positions?
    fun MoveUp()
    fun MoveDown()
    fun StandartMove() = println("Standart Move")
}

class ChessFigure(_name: String) : IChessFigure {
    constructor(_name: String, _color: String, _position: Positions) : this(_name) {
        this.color = _color
        this.position = _position
    }

    var name: String
    override lateinit var color: String
    override var position: Positions? = null

    init {
        name = _name
    }

    var moveStepNumber: Int = 1
    var moveHistory: String = "Move History: ${System.lineSeparator()}"
    var figureCondition: FigureCondition = FigureCondition()

    override fun MoveUp() {
        println("Moving Up!")
        moveStepNumber++
        position = Positions.values().toList().shuffled().first();
        moveHistory += ("$moveStepNumber: Moving Up! - Position: $position ${System.lineSeparator()}")
    }

    override fun MoveDown() {
        println("Moving Down!")
        moveStepNumber++
        position = Positions.values().toList().shuffled().first();
        moveHistory += ("$moveStepNumber: Moving Down! - Position: $position ${System.lineSeparator()}")
    }

    fun getFigureInfo() {
        println("\t\t\tFigure Info:")

        println("Figure name: ${this.name} ${System.lineSeparator()}" +
                "Figure color: ${this.color} ${System.lineSeparator()}" +
                "Figure position: ${this.position} ")

        println("isBattle: ${figureCondition.isBattle} ${System.lineSeparator()}" +
                "isCanFight: ${figureCondition.isCanFight} ${System.lineSeparator()}" +
                "isCanGo: ${figureCondition.isCanGo} ${System.lineSeparator()}")

        println(moveHistory);
    }

    inner class FigureCondition {
        var isBattle: Boolean = false
        var isCanFight: Boolean = false
        var isCanGo: Boolean = false
    }
}

// 1
enum class Positions {
    A1, A2, A3, A4, A5, A6, A7, A8, A9, A10,
    B1, B2, B3, B4, B5, B6, B7, B8, B9, B10,
    C1, C2, C3, C4, C5, C6, C7, C8, C9, C10;
}

// 2
data class Player(val name:String, val category:Int, val wins:Int, val loses:Int)

// 3
class A{
    private lateinit var prop: String
    fun setUp() {
        prop = "100 + 200"
    }

    fun display() {
        if(::prop.isInitialized){
            println(prop)
        }
    }

    override operator fun equals(other: Any?) : Boolean{
        return true
    }

    fun converter (which: String): Unit? {
        fun plus(){println("Plus function")}
        fun minus() {println("Minus function")}
        fun divide() {println("Divide function")}
        fun multiply() {println("Multiple function")}

        if(which == "+")
            return plus()
        else if(which == "-")
            return minus()
        else if(which == "/")
            return divide()
        else if (which == "*")
            return multiply()
        else
            return null
    }
}