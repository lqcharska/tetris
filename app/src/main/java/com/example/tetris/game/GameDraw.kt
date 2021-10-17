package com.example.tetris.game

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View
import androidx.lifecycle.ViewModel

class GameDraw(context: Context) : View(context) {

    fun DrawMatrix (matrix: Array<Array<Int>>, canvas: Canvas ){
        var paint: Paint = Paint()
        paint.setColor(Color.CYAN)
        canvas.drawRect(20F, 30F, 40F, 50F, paint)
    }

}