package com.example.tetris.end

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.tetris.R
import com.example.tetris.databinding.EndFragmentBinding
import com.example.tetris.databinding.GetStartedFragmentBinding

class EndFragment : Fragment() {


    private lateinit var viewModel: EndViewModel
    private lateinit var viewModelFactory: EndViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate view and obtain an instance of the binding class.
        val binding: EndFragmentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.end_fragment,
            container,
            false
        )

        viewModelFactory = EndViewModelFactory(EndFragmentArgs.fromBundle(requireArguments()).score)

        // Observer
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(EndViewModel::class.java)
        // Add observer for score
        viewModel.score.observe(viewLifecycleOwner, Observer { newScore ->
            binding.scoreEndText.text = newScore.toString()
        })
        viewModel.eventPlayAgain.observe(viewLifecycleOwner, Observer { playAgain ->
            if (playAgain) {
                findNavController().navigate(EndFragmentDirections.actionReturnToGame())
                viewModel.onPlayAgainComplete()
            }
        })

        // Binding
        binding.playAgainButton.setOnClickListener {
            viewModel.onPlayAgain()
        }

        return binding.root
    }
}