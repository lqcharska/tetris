package com.example.tetris.get_started


import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.MotionEventCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.tetris.R
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.tetris.databinding.GameFragmentBinding
import com.example.tetris.game.GameViewModel
import android.content.Context as Context

class GameFragment : Fragment() {


    private lateinit var binding: GameFragmentBinding
    lateinit var gameModel : GameViewModel
    private lateinit var moveDetector: GestureDetectorCompat


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
        binding.endButton.setOnClickListener {}
        binding.pauseButton.setOnClickListener {}
        gameModel = GameViewModel(binding.gameSpaceView, this)
        return binding.root
    }

}