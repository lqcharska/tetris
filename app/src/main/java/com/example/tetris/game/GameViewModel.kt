package com.example.tetris.game


import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.widget.Chronometer
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tetris.get_started.GameFragment

class GameViewModel() : ViewModel(){

//    var bitmap : Bitmap
//    var canvas: Canvas
//    var paint: Paint
//    var gameView = imageView
//    var gameContext = context

    // The current score
    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score

    // Game board
    private val _gameBoard = MutableLiveData<ArrayList<ArrayList<Int>>>()
    val gameBoard: LiveData<ArrayList<ArrayList<Int>>>
        get() = _gameBoard

//
//    private val gameViewWidth : Int = gameView.layoutParams.width
//    private var gameViewHeight : Int = gameView.layoutParams.height
//    var gameBoard = arrayOf(
//        arrayOf(0,0,0,0,0,0,0,0,0,0),
//        arrayOf(0,0,0,0,0,0,0,1,0,0),
//        arrayOf(0,0,1,0,0,0,0,0,0,0),
//        arrayOf(0,0,1,0,0,0,0,0,0,0),
//        arrayOf(0,0,1,0,0,0,0,0,0,0),
//        arrayOf(0,0,1,0,0,0,0,0,0,0),
//        arrayOf(0,0,1,0,0,0,0,0,0,0),
//        arrayOf(0,0,1,0,0,0,0,0,0,0),
//        arrayOf(0,0,1,0,0,0,0,0,0,0),
//        arrayOf(0,0,1,0,0,0,0,0,0,0),
//        arrayOf(0,0,1,0,0,0,0,0,0,0),
//        arrayOf(0,0,1,0,0,0,0,0,0,0),
//        arrayOf(0,0,1,0,0,0,0,0,0,0),
//        arrayOf(0,0,1,0,0,0,0,0,0,0),
//        arrayOf(0,0,0,0,1,0,0,0,0,0),
//        arrayOf(0,0,0,0,0,0,0,0,0,0),
//        arrayOf(0,0,1,0,0,0,0,0,0,1),
//        arrayOf(0,0,0,1,0,0,0,0,0,0),
//        arrayOf(0,0,0,0,1,0,0,0,0,0),
//        arrayOf(1,1,1,1,1,1,1,1,1,1),
//    )
    init {
        _score.value = 0
        _gameBoard.value = arrayListOf(
            arrayListOf(0,0,0,0,0,0,0,0,0,0),
            arrayListOf(0,0,0,0,0,0,0,1,0,0),
            arrayListOf(0,0,1,0,0,0,0,0,0,0),
            arrayListOf(0,0,1,0,0,0,0,0,0,0),
            arrayListOf(0,0,1,0,0,0,0,0,0,0),
            arrayListOf(0,0,1,0,0,0,0,0,0,0),
            arrayListOf(0,0,1,0,0,0,0,0,0,0),
            arrayListOf(0,0,1,0,0,0,0,0,0,0),
            arrayListOf(0,0,1,0,0,0,0,0,0,0),
            arrayListOf(0,0,1,0,0,0,0,0,0,0),
            arrayListOf(0,0,1,0,0,0,0,0,0,0),
            arrayListOf(0,0,1,0,0,0,0,0,0,0),
            arrayListOf(0,0,1,0,0,0,0,0,0,0),
            arrayListOf(0,0,1,0,0,0,0,0,0,0),
            arrayListOf(0,0,0,0,1,0,0,0,0,0),
            arrayListOf(0,0,0,0,0,0,0,0,0,0),
            arrayListOf(0,0,1,0,0,0,0,0,0,1),
            arrayListOf(0,0,0,1,0,0,0,0,0,0),
            arrayListOf(0,0,0,0,1,0,0,0,0,0),
            arrayListOf(1,1,1,1,1,1,1,1,1,1),
        )
//        var gameView = imageView
//
//        bitmap = Bitmap.createBitmap(gameViewWidth, gameViewHeight, Bitmap.Config.ARGB_8888)
//        canvas = Canvas(bitmap)
//        paint = Paint()
//        paint.setColor(Color.MAGENTA)
//        paint.setStyle(Paint.Style.STROKE)
//
//        drawGameBoard()
////        gameBoard[0][5] = 1


    }

//    fun drawGameBoard(){
//        var counter_i : Int = 0
//        var counter_j : Int = 0
//        val multiplier_i : Float = (gameViewWidth / 10).toFloat()
//        val multilier_j : Float = (gameViewHeight / 20).toFloat()
//        for(i in gameBoard){
//            counter_j = 0
//            for(j in i){
//                if (j == 1){
//                    canvas.drawRect(counter_j.toFloat()*multiplier_i,
//                        counter_i.toFloat()*multilier_j,
//                        (counter_j+1).toFloat()*multiplier_i, (counter_i+1).toFloat()*multilier_j, paint)
//                }
//                counter_j += 1
//            }
//            counter_i += 1
//        }
////        gameView.background = BitmapDrawable(gameContext.resources, bitmap)
//        gameView.setImageDrawable(BitmapDrawable(gameContext.resources, bitmap))
//    }


    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "GameViewModel destroyed!")
    }

    fun oneTickGame(){
        _score.value = (score.value)?.plus(1)

    }

}