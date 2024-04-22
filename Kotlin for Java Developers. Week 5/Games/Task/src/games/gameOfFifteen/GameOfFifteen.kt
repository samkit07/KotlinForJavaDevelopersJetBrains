package games.gameOfFifteen

import board.Cell
import board.Direction
import board.GameBoard
import board.createGameBoard
import games.game.Game

/*
 * Implement the Game of Fifteen (https://en.wikipedia.org/wiki/15_puzzle).
 * When you finish, you can play the game by executing 'PlayGameOfFifteen'.
 */
fun newGameOfFifteen(initializer: GameOfFifteenInitializer = RandomGameInitializer()): Game = object:Game{
    private val board = createGameBoard<Int?>(4)
    override fun initialize() {
        board.initialize(initializer)
    }

    override fun canMove(): Boolean {
        return true
    }

    override fun hasWon(): Boolean {
        val values = board.getAllCells()
            .map { i -> board[i] }
        return (1..15).toMutableList().all { it == values[it - 1] }
    }

    override fun processMove(direction: Direction) {
        with(board){
            val openCell: Cell = find { it == null }!!
            openCell.getNeighbour(direction.reversed()).let {
                set(openCell, get(it!!))
                set(it, null)
            }
        }
    }

    override fun get(i: Int, j: Int): Int? = board[board.getCell(i, j)]


    private fun GameBoard<Int?>.initialize(initializer: GameOfFifteenInitializer){
        initializer.initialPermutation.forEachIndexed{ i, v ->
            val r = i/width + 1
            val c = i%width + 1
            this[Cell(r, c)] = v
        }
    }
}


