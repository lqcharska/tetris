package com.example.tetris.get_started


import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.tetris.R
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.tetris.databinding.GameFragmentBinding

class GameFragment : Fragment() {


    private lateinit var binding: GameFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val bitmap: Bitmap = Bitmap.createBitmap(700,1000, Bitmap.Config.ARGB_8888)
        val canvas: Canvas = Canvas(bitmap)
        val paint: Paint = Paint()
        paint.setColor(Color.MAGENTA)
        canvas.drawRect(10F,20F,30F,40F,paint)



        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.game_fragment,
            container,
            false
        )

        binding.endButton.setOnClickListener {}
        binding.pauseButton.setOnClickListener {}

        binding.imageView2.background = BitmapDrawable(resources, bitmap)
        return binding.root

    }

}