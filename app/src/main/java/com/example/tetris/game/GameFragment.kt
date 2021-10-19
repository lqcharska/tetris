package com.example.tetris.get_started


import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Chronometer
import android.widget.Chronometer.OnChronometerTickListener
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.tetris.R
import com.example.tetris.databinding.GameFragmentBinding
import com.example.tetris.game.GameViewModel

class GameFragment : Fragment() {


    private lateinit var binding: GameFragmentBinding
    private lateinit var gameModel : GameViewModel
    private lateinit var chronometer: Chronometer


    private lateinit var bitmap : Bitmap
    private lateinit var canvas : Canvas
    private lateinit var paint : Paint


    private var gameViewWidth : Int = 0
    private var gameViewHeight : Int = 0
    private var running : Boolean = false
    private var pauseOffset : Int = 0


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.game_fragment,
            container,
            false
        )
        gameViewWidth = binding.gameSpaceView.layoutParams.width
        gameViewHeight = binding.gameSpaceView.layoutParams.height

        // Creating space for game
        bitmap = Bitmap.createBitmap(gameViewWidth, gameViewHeight, Bitmap.Config.ARGB_8888)
        canvas = Canvas(bitmap)
        paint = Paint()
        paint.color = Color.MAGENTA
        paint.style = Paint.Style.STROKE

        gameModel = ViewModelProvider(this).get(GameViewModel::class.java)

        // Observe
        gameModel.score.observe(viewLifecycleOwner, Observer { newScore ->
            binding.scoreText.text = newScore.toString()
        })
        // Observe
        gameModel.gameBoard.observe(viewLifecycleOwner, Observer { newGameBoard ->
            var counterI : Int = 0
            var counterJ : Int = 0
            val multiplierI : Float = (gameViewWidth / 10).toFloat()
            val multiplierJ : Float = (gameViewHeight / 20).toFloat()
            for(i in newGameBoard){
                counterJ = 0
                for(j in i){
                    if (j == 1){
                        canvas.drawRect(counterJ.toFloat()*multiplierI,
                            counterI.toFloat()*multiplierJ,
                            (counterJ+1).toFloat()*multiplierI, (counterI+1).toFloat()*multiplierJ, paint)
                    }
                    counterJ += 1
                }
                counterI += 1
            }
            binding.gameSpaceView.setImageDrawable(BitmapDrawable(resources, bitmap))
        })


        // Button action
        binding.endButton.setOnClickListener {
        }
        binding.pauseButton.setOnClickListener {
        }

        // Chronometer
        chronometer = binding.chronometer
        chronometer.format = "Time: %s"
        startChronometer()
        chronometer.onChronometerTickListener = OnChronometerTickListener { chronometer ->
            if (SystemClock.elapsedRealtime() - chronometer.base >= 1000) {
                val time = SystemClock.elapsedRealtime()
                gameModel.oneTickGame()
                Log.d("Time check", "$time")
            }
        }

        return binding.root
    }




    private fun startChronometer(){
        if(!running){
            chronometer.base = SystemClock.elapsedRealtime() - pauseOffset
            chronometer.start()
            running = true
        }
    }
    fun pauseChronometer(){
        if(running){
            chronometer.stop()
            val pauseOffset = SystemClock.elapsedRealtime() - chronometer.base
            running = false
        }
    }

    fun resetChronometer(){
        chronometer.base = SystemClock.elapsedRealtime()
        pauseOffset = 0
    }




}