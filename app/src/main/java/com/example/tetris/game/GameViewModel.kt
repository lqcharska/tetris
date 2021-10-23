package com.example.tetris.game


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel() : ViewModel(){

    // The current score
    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score

    // Game finish
    private val _gameFinishFlag =MutableLiveData<Boolean>()
    val gameFinishFlag: LiveData<Boolean>
        get() = _gameFinishFlag

    // Game board - active
    private val _gameBoard = MutableLiveData<ArrayList<ArrayList<Int>>>()
    val gameBoard: LiveData<ArrayList<ArrayList<Int>>>
        get() = _gameBoard

    // Game board - dead
    var deadGameBoard : ArrayList<ArrayList<Int>>

    // Block
    var block : Block = Block()

    // Game states
    enum class GameState{
        CREATE_NEW_BLOCK,
        MOVE_DOWN_BLOCK,
        INCREASE_SCORE,
        END_GAME
    }
    var gameState : GameState = GameState.CREATE_NEW_BLOCK

    init {
        _score.value = 0
        deadGameBoard = arrayListOf(
            arrayListOf(0,0,0,0,0,0,0,0,0,0),
            arrayListOf(0,0,0,0,0,0,0,0,0,0),
            arrayListOf(0,0,0,0,0,0,0,0,0,0),
            arrayListOf(0,0,0,0,0,0,0,0,0,0),
            arrayListOf(0,0,0,0,0,0,0,0,0,0),
            arrayListOf(0,0,0,0,0,0,0,0,0,0),
            arrayListOf(0,0,1,0,0,0,0,0,0,0),
            arrayListOf(0,0,1,0,0,0,0,0,0,0),
            arrayListOf(0,0,1,0,0,0,0,0,0,0),
            arrayListOf(0,0,1,0,0,0,0,0,0,0),
            arrayListOf(0,0,1,0,0,0,0,0,0,0),
            arrayListOf(0,0,1,0,0,0,0,0,0,0),
            arrayListOf(0,0,1,0,0,0,0,0,0,0),
            arrayListOf(0,0,1,0,0,0,0,0,0,0),
            arrayListOf(0,0,0,0,0,0,0,0,0,0),
            arrayListOf(0,0,0,0,0,0,0,0,0,0),
            arrayListOf(0,0,1,0,0,0,0,0,0,1),
            arrayListOf(0,0,0,0,0,0,0,0,0,0),
            arrayListOf(0,0,0,0,0,0,0,0,0,0),
            arrayListOf(1,1,1,1,1,1,1,1,1,1),
        )
        _gameBoard.value = deadGameBoard
    }

    class Block() {
        enum class BlockShape {
            I, T, O, L, J, S, Z
        }

        val coordinates: MutableMap<BlockShape, ArrayList<ArrayList<Int>>> = mutableMapOf(
            BlockShape.I to arrayListOf(
                arrayListOf(0, -1),
                arrayListOf(0, 1),
                arrayListOf(0, 2)
            ),
            BlockShape.T to arrayListOf(
                arrayListOf(-1, 0),
                arrayListOf(1, 0),
                arrayListOf(0, 1)
            ),
            BlockShape.O to arrayListOf(
                arrayListOf(1, 0),
                arrayListOf(0, 1),
                arrayListOf(1, 1)
            ),
            BlockShape.L to arrayListOf(
                arrayListOf(0, -1),
                arrayListOf(0, 1),
                arrayListOf(1, 1)
            ),
            BlockShape.J to arrayListOf(
                arrayListOf(0, -1),
                arrayListOf(0, 1),
                arrayListOf(-1, 1)
            ),
            BlockShape.S to arrayListOf(
                arrayListOf(1, 0),
                arrayListOf(-1, 1),
                arrayListOf(0, 1)
            ),
            BlockShape.Z to arrayListOf(
                arrayListOf(-1, 0),
                arrayListOf(0, 1),
                arrayListOf(1, 1)
            )
        )
        var isCleared: Boolean = false
        var coordinateX: Int = 4
        var coordinateY: Int = 2
        lateinit var currentShape: BlockShape
        lateinit var currentCoordinates: ArrayList<ArrayList<Int>>
        var shapeList: MutableList<BlockShape> = mutableListOf(
            BlockShape.I,
            BlockShape.T,
            BlockShape.O,
            BlockShape.L,
            BlockShape.J,
            BlockShape.S,
            BlockShape.Z
        )

        fun getOccupiedCoordinates(): ArrayList<ArrayList<Int>> {
            var occupiedCoordinates: ArrayList<ArrayList<Int>> = ArrayList()
            if (!isCleared) {
                occupiedCoordinates.add(arrayListOf(coordinateX, coordinateY))
                currentCoordinates.forEach() {
                    occupiedCoordinates.add(
                        arrayListOf(it[0] + coordinateX, it[1] + coordinateY)
                    )
                }
            }
            return occupiedCoordinates
        }


        fun getCoordinatesAfterRotate(): ArrayList<ArrayList<Int>> {
            val newCoordinates: ArrayList<ArrayList<Int>> = ArrayList()
            for (points in currentCoordinates) {
                val xCoordinate: Int = points[1] * (-1)
                val yCoordinate: Int = points[0]
                val currentPoint: ArrayList<Int> = arrayListOf(xCoordinate, yCoordinate)
                newCoordinates.add(currentPoint)
            }
            return newCoordinates
        }


        fun rotateMe() {
            currentCoordinates = getCoordinatesAfterRotate()
        }

        fun moveMeRight() {
            coordinateX += 1
        }

        fun moveMeLeft() {
            coordinateX -= 1
        }

        fun moveMeDown() {
            coordinateY += 1
        }

        fun checkCollision(deadGameBoard: ArrayList<ArrayList<Int>>): Boolean {
            var collision: Boolean = false

            for (point in getOccupiedCoordinates()) {
                if (point[0] >= deadGameBoard[0].size) {
                    collision = true
                    break
                }
                if (point[0] < 0) {
                    collision = true
                    break
                }
                if (point[1] >= deadGameBoard.size) {
                    collision = true
                    break
                }
                if (deadGameBoard[point[1]][point[0]] > 0) {
                    collision = true
                    break
                }
            }
            return collision
        }

        fun canIRotate(deadGameBoard: ArrayList<ArrayList<Int>>): Boolean {
            var oldCoordinates: ArrayList<ArrayList<Int>> = ArrayList(ArrayList())
            for (point in currentCoordinates) {
                oldCoordinates.add(arrayListOf(point[0], point[1]))
            }
            currentCoordinates = getCoordinatesAfterRotate()

            val collision: Boolean = checkCollision(deadGameBoard)

            currentCoordinates = oldCoordinates

            return !collision

        }

        fun canIMoveRight(deadGameBoard: ArrayList<ArrayList<Int>>): Boolean {
            coordinateX += 1

            val collision: Boolean = checkCollision(deadGameBoard)

            coordinateX -= 1

            return !collision
        }

        fun canIMoveLeft(deadGameBoard: ArrayList<ArrayList<Int>>): Boolean {
            coordinateX -= 1

            val collision: Boolean = checkCollision(deadGameBoard)

            coordinateX += 1

            return !collision
        }

        fun canIMoveDown(deadGameBoard: ArrayList<ArrayList<Int>>): Boolean {
            coordinateY += 1

            val collision: Boolean = checkCollision(deadGameBoard)

            coordinateY -= 1

            return !collision
        }

        fun rotate(deadGameBoard: ArrayList<ArrayList<Int>>): Boolean {
            if (canIRotate(deadGameBoard)) {
                rotateMe()
                return true
            } else {
                return false
            }
        }

        fun moveDown(deadGameBoard: ArrayList<ArrayList<Int>>): Boolean {
            if (canIMoveDown(deadGameBoard)) {
                moveMeDown()
                return true
            } else {
                return false
            }
        }

        fun moveRight(deadGameBoard: ArrayList<ArrayList<Int>>): Boolean {
            if (canIMoveRight(deadGameBoard)) {
                moveMeRight()
                return true
            } else {
                return false
            }
        }

        fun moveLeft(deadGameBoard: ArrayList<ArrayList<Int>>): Boolean {
            if (canIMoveLeft(deadGameBoard)) {
                moveMeLeft()
                return true
            } else {
                return false
            }
        }

        private fun resetCoordinates() {
            coordinateX = 4
            coordinateY = 2
        }

        private fun drawLots() {
            shapeList.shuffle()
            currentShape = shapeList[0]
            currentCoordinates = coordinates[currentShape]!!
        }

        fun reset(deadGameBoard: ArrayList<ArrayList<Int>>) : Boolean{
            isCleared = false
            drawLots()
            resetCoordinates()
            return !checkCollision(deadGameBoard)
        }
    }



    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "GameViewModel destroyed!")
    }

    fun refreshGameBoard(){
        var temporaryGameBoard : ArrayList<ArrayList<Int>> = arrayListOf(
            arrayListOf(0,0,0,0,0,0,0,0,0,0),
            arrayListOf(0,0,0,0,0,0,0,0,0,0),
            arrayListOf(0,0,0,0,0,0,0,0,0,0),
            arrayListOf(0,0,0,0,0,0,0,0,0,0),
            arrayListOf(0,0,0,0,0,0,0,0,0,0),
            arrayListOf(0,0,0,0,0,0,0,0,0,0),
            arrayListOf(0,0,0,0,0,0,0,0,0,0),
            arrayListOf(0,0,0,0,0,0,0,0,0,0),
            arrayListOf(0,0,0,0,0,0,0,0,0,0),
            arrayListOf(0,0,0,0,0,0,0,0,0,0),
            arrayListOf(0,0,0,0,0,0,0,0,0,0),
            arrayListOf(0,0,0,0,0,0,0,0,0,0),
            arrayListOf(0,0,0,0,0,0,0,0,0,0),
            arrayListOf(0,0,0,0,0,0,0,0,0,0),
            arrayListOf(0,0,0,0,0,0,0,0,0,0),
            arrayListOf(0,0,0,0,0,0,0,0,0,0),
            arrayListOf(0,0,0,0,0,0,0,0,0,0),
            arrayListOf(0,0,0,0,0,0,0,0,0,0),
            arrayListOf(0,0,0,0,0,0,0,0,0,0),
            arrayListOf(0,0,0,0,0,0,0,0,0,0),
        )
        for (i in 0..deadGameBoard.lastIndex){
            for (j in 0..deadGameBoard[i].lastIndex) {
                temporaryGameBoard[i][j] = deadGameBoard[i][j]
            }
        }

        for(point in block.getOccupiedCoordinates()){
            temporaryGameBoard[point[1]][point[0]] = 1
        }
        _gameBoard.value = temporaryGameBoard
    }

    fun rotateBlock(){
        block.rotate(deadGameBoard)
        refreshGameBoard()
    }

    fun moveBlockRight(){
        block.moveRight(deadGameBoard)
        refreshGameBoard()
    }

    fun moveBlockLeft(){
        block.moveLeft(deadGameBoard)
        refreshGameBoard()
    }

    fun oneTickGame(){
        when(gameState){
            GameState.CREATE_NEW_BLOCK -> {
                gameState = if(block.reset(deadGameBoard)) GameState.MOVE_DOWN_BLOCK
                else GameState.END_GAME
            }


            GameState.MOVE_DOWN_BLOCK -> {
                if (!block.moveDown(deadGameBoard)) gameState = GameState.INCREASE_SCORE
            }

            GameState.INCREASE_SCORE -> {
                for (point in block.getOccupiedCoordinates()){
                    deadGameBoard[point[1]][point[0]] = 1
                }
                var shouldBeCleared : Boolean
                for (row in deadGameBoard){
                    shouldBeCleared = true
                    for(column in row){
                        if (column == 0){
                            shouldBeCleared = false
                        }
                    }
                    if (shouldBeCleared){
                        _score.value = (score.value)?.plus(10)
                        deadGameBoard.remove(row)
                        deadGameBoard.add(0,arrayListOf(0,0,0,0,0,0,0,0,0,0))
                    }
                }
                _score.value = (score.value)?.plus(1)
                block.isCleared = true
                gameState = GameState.CREATE_NEW_BLOCK
            }

            GameState.END_GAME -> {
                onGameFinish()
            }

        }
        refreshGameBoard()


    }

    fun onGameFinish() {
        _gameFinishFlag.value = true
    }

    fun onGameFinishComplete() {
        _gameFinishFlag.value = false
    }

}