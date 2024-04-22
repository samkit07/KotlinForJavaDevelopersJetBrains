package board

import board.Direction.*

fun createSquareBoard(width: Int): SquareBoard = object : SquareBoard {

    private val allCell by lazy { getACells().toMutableList() }

    fun getACells(): Collection<Cell> {
        val cellList = mutableListOf<Cell>()
        for (row in 1..width){
            for (column in 1..width){
                cellList.add(Cell(row, column))
            }
        }
        return cellList
    }
    override val width: Int
        get() = width

    override fun getCellOrNull(i: Int, j: Int): Cell? {
        return if (Cell(i, j) in allCell) getCell(i, j) else null
    }

    override fun getCell(i: Int, j: Int): Cell {
        return allCell.elementAt(allCell.indexOf(Cell(i, j)))
    }

    override fun getAllCells(): Collection<Cell> {
        return allCell
    }

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
        val l = mutableListOf<Cell>()
        if (jRange.first <= jRange.last && Cell(i, jRange.first) in getAllCells()){
            for (j in jRange) {
                val a = getCellOrNull(i, j)
                if (a != null)
                    l.add(a)
            }
        } else if (jRange.first >= jRange.last && Cell(i, jRange.last) in getAllCells()){
            for (j in jRange) {
                val a = getCellOrNull(i, j)
                if (a != null)
                    l.add(a)
            }
        }
        return l
    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
        val l = mutableListOf<Cell>()
        if (iRange.first <= iRange.last && Cell(iRange.first, j) in getAllCells()){
            for (i in iRange) {
                val a = getCellOrNull(i, j)
                if (a != null)
                    l.add(a)
            }
        } else if (iRange.first >= iRange.last && Cell(iRange.last, j) in getAllCells()){
            for (i in iRange) {
                val a = getCellOrNull(i, j)
                if (a != null)
                    l.add(a)
            }
        }
        return l
    }

    override fun Cell.getNeighbour(direction: Direction): Cell? {
        return when(direction){
            UP -> getCellOrNull(this.i-1, this.j)
            DOWN -> getCellOrNull(this.i+1, this.j)
            RIGHT -> getCellOrNull(this.i, this.j+1)
            LEFT -> getCellOrNull(this.i, this.j-1)
        }
    }

}
fun <T> createGameBoard(width: Int): GameBoard<T> = object : GameBoard<T> {
    private val allCell by lazy { getACells().toMutableList() }
    var mapi = mutableMapOf<Cell,T?>()

    init {
        initMap()
    }

    fun initMap(){
        allCell.forEach {
            mapi[it] = null
        }
    }
    override fun get(cell: Cell): T? {
        return mapi[cell]
    }

    override fun all(predicate: (T?) -> Boolean): Boolean {
        return mapi.values.all(predicate)
    }

    override fun any(predicate: (T?) -> Boolean): Boolean {
        return mapi.values.any(predicate)
    }

    override fun find(predicate: (T?) -> Boolean): Cell? {
        return mapi.filterValues(predicate).keys.first()
    }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> {
        return mapi.filterValues(predicate).keys
    }

    override fun set(cell: Cell, value: T?) {
        mapi[cell] = value
    }

    fun getACells(): Collection<Cell> {
        val cellList = mutableListOf<Cell>()
        for (row in 1..width){
            for (column in 1..width){
                cellList.add(Cell(row, column))
            }
        }
        return cellList
    }
    override val width: Int
        get() = width

    override fun getCellOrNull(i: Int, j: Int): Cell? {
        return if (Cell(i, j) in allCell) getCell(i, j) else null
    }

    override fun getCell(i: Int, j: Int): Cell {
        return allCell.elementAt(allCell.indexOf(Cell(i, j)))
    }

    override fun getAllCells(): Collection<Cell> {
        return allCell
    }

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
        val l = mutableListOf<Cell>()
        if (jRange.first <= jRange.last && Cell(i, jRange.first) in getAllCells()){
            for (j in jRange) {
                val a = getCellOrNull(i, j)
                if (a != null)
                    l.add(a)
            }
        } else if (jRange.first >= jRange.last && Cell(i, jRange.last) in getAllCells()){
            for (j in jRange) {
                val a = getCellOrNull(i, j)
                if (a != null)
                    l.add(a)
            }
        }
        return l
    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
        val l = mutableListOf<Cell>()
        if (iRange.first <= iRange.last && Cell(iRange.first, j) in getAllCells()){
            for (i in iRange) {
                val a = getCellOrNull(i, j)
                if (a != null)
                    l.add(a)
            }
        } else if (iRange.first >= iRange.last && Cell(iRange.last, j) in getAllCells()){
            for (i in iRange) {
                val a = getCellOrNull(i, j)
                if (a != null)
                    l.add(a)
            }
        }
        return l
    }

    override fun Cell.getNeighbour(direction: Direction): Cell? {
        return when(direction){
            UP -> getCellOrNull(this.i-1, this.j)
            DOWN -> getCellOrNull(this.i+1, this.j)
            RIGHT -> getCellOrNull(this.i, this.j+1)
            LEFT -> getCellOrNull(this.i, this.j-1)
        }
    }

}

