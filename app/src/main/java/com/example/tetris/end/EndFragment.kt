package com.example.tetris.end

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.tetris.R
import com.example.tetris.databinding.GetStartedFragmentBinding

class EndFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        val binding: GetStartedFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.end_fragment, container, false
        )

        binding.startButton.setOnClickListener {
            Log.d("dupa", "dupa")
        }
        return binding.root
    }

}