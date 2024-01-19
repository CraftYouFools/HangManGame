package com.remid.hangmangame.hangman_game.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.remid.hangmangame.R
import com.remid.hangmangame.databinding.FragmentHangmanGameBinding
import com.remid.hangmangame.hangman_game.presentation.viewstate.HangmanGameViewState
import com.remid.hangmangame.hangman_game.presentation.fragment.dialog.GameFinishedDialogFragment
import com.remid.hangmangame.hangman_game.presentation.viewmodel.HangManGameViewModel
import com.remid.hangmangame.shared.presentation.fragment.BaseFragment
import com.remid.hangmangame.shared.presentation.fragment.DialogClosedListener
import com.remid.hangmangame.shared.presentation.viewmodel.ViewModelFactory
import javax.inject.Inject

class HangmanFragment : BaseFragment(),DialogClosedListener {

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
                    binding.tvLeftNumber.text = String.format(
                        getString(R.string.tries_left),
                        hangmanGameviewState.content.leftTriesNumber.toString()
                    )
                }

                is HangmanGameViewState.GameWon -> {
                    viewModel.onDialogShown()
                    GameFinishedDialogFragment(this).show(childFragmentManager, GameFinishedDialogFragment.TAG)
                }

                is HangmanGameViewState.GameLost -> {
                    viewModel.onDialogShown()
                    GameFinishedDialogFragment(this).show(childFragmentManager, GameFinishedDialogFragment.TAG)
                }
            }
        }
        initListeners()
    }

    private fun initListeners() {
        binding.btnNextGuess.setOnClickListener {
            binding.etNextGuess.text.firstOrNull()?.let { letter ->
                viewModel.onGuessNewLetter(letter)
                binding.etNextGuess.text.clear()
            }
        }

        binding.resetBtn.setOnClickListener {
            viewModel.onReset()
        }
    }

    override fun onDialogClosed() {
        viewModel.onDialogClosed()
    }

    companion object {
        @JvmStatic
        fun newInstance() = HangmanFragment()

        private const val TAG = "HangmanFragment"
    }
}