package com.example.tetris.game


import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.ViewModel
import com.example.tetris.get_started.GameFragment

class GameViewModel(imageView: ImageView, context: GameFragment) : ViewModel(){

    lateinit var bitmap : Bitmap
    lateinit var canvas: Canvas
    lateinit var paint: Paint
    var gameView = imageView
    var gameContext = context
    var gameBoard = arrayOf(
        arrayOf(0,0,0,0,0,0,0,0,0,0),
        arrayOf(0,0,0,0,0,0,0,1,0,0),
        arrayOf(0,0,1,0,0,0,0,0,0,0),
        arrayOf(0,0,0,0,1,0,0,0,0,0),
        arrayOf(0,0,0,0,0,0,0,0,0,0),
        arrayOf(0,0,1,0,0,0,0,0,0,1),
        arrayOf(0,0,0,1,0,0,0,0,0,0),
        arrayOf(0,0,0,0,1,0,0,0,0,0),
        arrayOf(0,0,0,0,0,1,0,0,0,0),
    )
    init {
        bitmap = Bitmap.createBitmap(700, 1000, Bitmap.Config.ARGB_8888)
        canvas = Canvas(bitmap)
        paint = Paint()
        paint.setColor(Color.MAGENTA)
//        canvas.drawRect(10F, 20F, 30F, 40F, paint)
        drawGameBoard()
//        gameBoard[0][5] = 1

    }

    private fun drawGameBoard(){
        var counter_i : Int = 0
        var counter_j : Int = 0
        var multipler : Float = 70.0F
        for(i in gameBoard){
            counter_j = 0
            for(j in i){
                if (j == 1){
                    canvas.drawRect(counter_j.toFloat()*multipler,
                        counter_i.toFloat()*multipler,
                        (counter_j+1).toFloat()*multipler, (counter_i+1).toFloat()*multipler, paint)
                }
                counter_j += 1
            }
            counter_i += 1
        }
        gameView.background = BitmapDrawable(gameContext.resources, bitmap)
    }


}