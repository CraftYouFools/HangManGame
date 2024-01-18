package com.remid.hangmangame.hangman_game.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.remid.hangmangame.databinding.FragmentHangmanGameBinding
import com.remid.hangmangame.hangman_game.presentation.viewmodel.HangManGameViewModel
import com.remid.hangmangame.shared.presentation.fragment.BaseFragment
import com.remid.hangmangame.shared.presentation.viewmodel.ViewModelFactory
import javax.inject.Inject

class HangmanFragment : BaseFragment() {

    private lateinit var binding : FragmentHangmanGameBinding

    @Inject
    lateinit var myViewModelFactory: ViewModelFactory


    private val viewModel: HangManGameViewModel by lazy {
        ViewModelProvider(this, myViewModelFactory)[HangManGameViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        injector.inject(this)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHangmanGameBinding.inflate(inflater, container, false)


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel
    }
    companion object {
        @JvmStatic
        fun newInstance() = HangmanFragment()

        private const val TAG = "HangmanFragment"
    }
}