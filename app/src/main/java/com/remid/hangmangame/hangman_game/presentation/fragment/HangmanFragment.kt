package com.remid.hangmangame.hangman_game.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.remid.hangmangame.databinding.FragmentHangmanGameBinding
import com.remid.hangmangame.shared.presentation.fragment.BaseFragment

class HangmanFragment : BaseFragment() {

    private lateinit var binding : FragmentHangmanGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        injector.inject(this)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHangmanGameBinding.inflate(inflater, container, false)


        return binding.root
    }



    companion object {
        @JvmStatic
        fun newInstance() = HangmanFragment()

        private const val TAG = "HangmanFragment"
    }
}