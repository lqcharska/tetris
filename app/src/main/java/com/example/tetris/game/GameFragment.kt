package com.example.tetris.get_started


import android.os.Build
import android.os.Bundle
import android.os.SystemClock
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
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.tetris.R
import com.example.tetris.databinding.GameFragmentBinding
import com.example.tetris.game.GameViewModel

class GameFragment : Fragment() {


    private lateinit var binding: GameFragmentBinding
    private lateinit var gameModel : GameViewModel
    private lateinit var chronometer: Chronometer
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

//        gameModel = ViewModelProvider(this).get(GameViewModel::class.java)

        binding.endButton.setOnClickListener {
        }
        binding.pauseButton.setOnClickListener {
        }

        chronometer = binding.chronometer
        chronometer.format = "Time: %s"

        gameModel = GameViewModel(binding.gameSpaceView, this)
        startChronometer()
        chronometer.onChronometerTickListener = OnChronometerTickListener { chronometer ->
            if (SystemClock.elapsedRealtime() - chronometer.base >= 1000) {
                val time = SystemClock.elapsedRealtime()
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