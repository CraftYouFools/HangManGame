package com.remid.hangmangame.hangman_game.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.remid.hangmangame.databinding.FragmentHangmanGameBinding
import com.remid.hangmangame.hangman_game.presentation.HangmanGameViewState
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
        Log.d(TAG, "viewModel hashCode :"+ viewModel.hashCode().toString())

        viewModel.viewState.observe(viewLifecycleOwner) {
            hangmanGameviewState ->

            when(hangmanGameviewState) {
                is HangmanGameViewState.Loading -> {
                    binding.pbHangman.visibility = View.VISIBLE
                    binding.tvError.visibility = View.GONE
                    binding.clGameContent.visibility = View.GONE
                }
                is HangmanGameViewState.Error -> {
                    binding.pbHangman.visibility = View.GONE
                    binding.tvError.visibility = View.VISIBLE
                    binding.clGameContent.visibility = View.GONE

                }
                is HangmanGameViewState.Content -> {

                    binding.pbHangman.visibility = View.GONE
                    binding.tvError.visibility = View.GONE
                    binding.clGameContent.visibility = View.VISIBLE

                    binding.tvVictories.text = hangmanGameviewState.content.victories.toString()
                    binding.tvNbGames.text = hangmanGameviewState.content.gameNumber.toString()
                    binding.tvWordToGuess.text = hangmanGameviewState.content.guessWord
                    binding.tvLeftNumber.text = hangmanGameviewState.content.leftTriesNumber.toString()

                }

                is HangmanGameViewState.GameWon -> {
                    Log.d(TAG, " game won ")
                    GameWonDialogFragment().show(childFragmentManager, GameWonDialogFragment.TAG)
                }

                is HangmanGameViewState.GamLost -> {
                    Log.d(TAG, " game Lost ")

                    GameLostDialogFragment().show(childFragmentManager, GameLostDialogFragment.TAG)
                }

            }
        }

        initListeners()
    }

    private fun initListeners() {
        binding.btnNextGuess.setOnClickListener { viewModel.onGuessNewLetter(binding.etNextGuess.text.first()) }
    }

    companion object {
        @JvmStatic
        fun newInstance() = HangmanFragment()

        private const val TAG = "HangmanFragment"
    }
}