package com.example.tetris.get_started


import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.media.MediaPlayer
import android.opengl.ETC1.getHeight
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Chronometer
import android.widget.Chronometer.OnChronometerTickListener
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
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

    private lateinit var music : MediaPlayer


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

        music= MediaPlayer.create(activity, R.raw.tetris_theme_song)
        music.start()
        music.isLooping = true

        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        gameViewWidth = (displayMetrics.widthPixels * 0.85).toInt()
        gameViewHeight = (displayMetrics.heightPixels * 0.7).toInt()

        paint = Paint()
        paint.style = Paint.Style.STROKE
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeMiter = 5F
        paint.strokeWidth = 5F
        paint.color = Color.parseColor("#fccad7")

        gameModel = ViewModelProvider(this).get(GameViewModel::class.java)

        // Observe
        gameModel.gameFinishFlag.observe(viewLifecycleOwner, Observer <Boolean> { gameFinish ->
            if(gameFinish) onGameFinish()
        })
        // Observe
        gameModel.score.observe(viewLifecycleOwner, Observer { newScore ->
            binding.scoreText.text = newScore.toString()
        })
        // Observe
        gameModel.gameBoard.observe(viewLifecycleOwner, Observer { newGameBoard ->
            bitmap = Bitmap.createBitmap(gameViewWidth, gameViewHeight, Bitmap.Config.ARGB_8888)
            canvas = Canvas(bitmap)

            var counterI : Int = 0
            var counterJ : Int
            val multiplierI : Float = (gameViewWidth / 10).toFloat()
            val multiplierJ : Float = (gameViewHeight / 20).toFloat()
            for(i in newGameBoard){
                counterJ = 0
                for(j in i){
                    if (j == 1){
//                        val paint = Paint().apply {
//                            isAntiAlias = true
//                            style = Paint.Style.FILL
//                            // linear gradient shader
//                            shader = LinearGradient(
//                                counterJ.toFloat()*multiplierI,
//                                counterI.toFloat()*multiplierJ,
//                                (counterJ+1).toFloat()*multiplierI,
//                                (counterI+1).toFloat()*multiplierJ,
//                                // color0, sRGB color at the start of the gradient line
//                                Color.parseColor("#FFC6FF"),
//                                // color1, sRGB color at the end of the gradient line
//                                Color.parseColor("#BDB2FF"),
//                                // shader tiling mode
//                                Shader.TileMode.CLAMP
//                            )
//                        }
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
            onGameFinish()
        }
        binding.moveRightButton.setOnClickListener{
            gameModel.moveBlockRight()
        }
        binding.moveLeftButton.setOnClickListener{
            gameModel.moveBlockLeft()
        }
        binding.rotateButton.setOnClickListener {
            gameModel.rotateBlock()
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


    private fun onGameFinish(){
        Toast.makeText(activity, "Game has just finished", Toast.LENGTH_SHORT).show()
        music.stop()
        val action = GameFragmentDirections.actionGameToEnd()
        action.score = gameModel.score.value?:0
        NavHostFragment.findNavController(this).navigate(action)
        gameModel.onGameFinishComplete()

    }


    private fun startChronometer(){
        if(!running){
            chronometer.base = SystemClock.elapsedRealtime() - pauseOffset
            chronometer.start()
            running = true
        }
    }


}