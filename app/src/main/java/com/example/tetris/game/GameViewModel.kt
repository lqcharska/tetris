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
        INCREASE_SCORE
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

    class Block(){
        enum class BlockShape{
            I, T, O, L, J, S, Z
        }
        val coordinates : MutableMap<BlockShape, ArrayList<ArrayList<Int>>> = mutableMapOf(
            BlockShape.I to arrayListOf(
                arrayListOf(0, -1),
                arrayListOf(0, 1),
                arrayListOf(0,2)
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
        var coordinateX : Int = 4
        var coordinateY : Int = 2
        var ifFly : Boolean = false
        var ifLay : Boolean = false
        lateinit var currentShape : BlockShape //TODO co z tym zrobic
        lateinit var currentCoordinates : ArrayList<ArrayList<Int>>
        var shapeList: MutableList<BlockShape> = mutableListOf(
            BlockShape.I,
            BlockShape.T,
            BlockShape.O,
            BlockShape.L,
            BlockShape.J,
            BlockShape.S,
            BlockShape.Z
        )

        fun getOccupiedCoordinates() : ArrayList<ArrayList<Int>> {
            var occupiedCoordinates : ArrayList<ArrayList<Int>> = ArrayList()
            occupiedCoordinates.add(arrayListOf(coordinateX, coordinateY))
            currentCoordinates.forEach(){
                occupiedCoordinates.add(
                    arrayListOf(it[0] + coordinateX, it[1] + coordinateY)
                )
            }
            return occupiedCoordinates
        }


        fun getCoordinatesAfterRotate() : ArrayList<ArrayList<Int>>{
            val newCoordinates : ArrayList<ArrayList<Int>> = ArrayList()
            for (points in currentCoordinates){
                val xCoordinate : Int = points[1] * (-1)
                val yCoordinate : Int = points[0]
                val currentPoint : ArrayList<Int> = arrayListOf(xCoordinate, yCoordinate)
                newCoordinates.add(currentPoint)
            }
            return newCoordinates
        }

        fun showMe(){}

        fun rotateMe(){}
        fun moveMeRight(){}
        fun moveMeLeft(){}
        fun moveMeDown(){
            coordinateY += 1
        }

        fun ifCollision(){

        }


        fun canIRotate(){}
        fun canIMoveRight(){}
        fun canIMoveLeft(){}
        fun canIMoveDown(deadGameBoard : ArrayList<ArrayList<Int>>): Boolean {
            coordinateY += 1
            var collision : Boolean = false

            for(point in getOccupiedCoordinates()){
                if (point[1] >= deadGameBoard.size){
                    collision = true
                    break
                }
                if (deadGameBoard[point[1]][point[0]] > 0) {
                    collision = true
                    break
                }
            }
            coordinateY -= 1

            if (collision) return false

            return true
        }

        fun moveDown(deadGameBoard : ArrayList<ArrayList<Int>>) : Boolean {
            if(canIMoveDown(deadGameBoard)) {
                moveMeDown()
                return true
            } else {
                return false
            }
        }

        fun resetCoordinates(){
            coordinateX = 4
            coordinateY = 2
        }

        fun drawLots(){
            shapeList.shuffle()
            currentShape = shapeList[0]
            currentCoordinates = coordinates[currentShape]!!
        }

    }



    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "GameViewModel destroyed!")
    }

    fun oneTickGame(){
        _score.value = (score.value)?.plus(1)
        when(gameState){
            GameState.CREATE_NEW_BLOCK -> {
                block.drawLots()
                block.resetCoordinates()
                gameState = GameState.MOVE_DOWN_BLOCK
            }


            GameState.MOVE_DOWN_BLOCK -> {
                if (!block.moveDown(deadGameBoard)) gameState = GameState.INCREASE_SCORE
            }

            GameState.INCREASE_SCORE -> {
                Log.d("end", "end kupa")
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
                        deadGameBoard.remove(row)
                        deadGameBoard.add(0,arrayListOf(0,0,0,0,0,0,0,0,0,0))
                    }
                }
                block.resetCoordinates()
                gameState = GameState.CREATE_NEW_BLOCK
            }

        }

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

//        block.getOccupiedCoordinates() -> { (4,4), (4,3), ... }
        for(point in block.getOccupiedCoordinates()){
            temporaryGameBoard[point[1]][point[0]] = 1
        }

//        val temporaryGameBoard : ArrayList<ArrayList<Int>> = ArrayList(deadGameBoard)
        // draw block on temporary game board
//        temporaryGameBoard[score.value!!][4] = 1

        // assign temporary game board to main game board
        _gameBoard.value = temporaryGameBoard
    }

}