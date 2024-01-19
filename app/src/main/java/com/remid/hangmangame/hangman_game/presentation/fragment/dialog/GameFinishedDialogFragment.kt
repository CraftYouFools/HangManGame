package com.remid.hangmangame.hangman_game.presentation.fragment.dialog

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.remid.hangmangame.R
import com.remid.hangmangame.hangman_game.presentation.viewmodel.HangManGameWonViewModel
import com.remid.hangmangame.shared.presentation.fragment.BaseDialogFragment
import com.remid.hangmangame.shared.presentation.fragment.DialogClosedListener
import com.remid.hangmangame.shared.presentation.viewmodel.ViewModelFactory
import javax.inject.Inject

class GameFinishedDialogFragment(private val dialogClosedListener: DialogClosedListener) : BaseDialogFragment() {

    @Inject
    lateinit var myViewModelFactory: ViewModelFactory


    private val viewModel: HangManGameWonViewModel by lazy {
        ViewModelProvider(this, myViewModelFactory)[HangManGameWonViewModel::class.java]
    }

    private lateinit var dialog : AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injector.inject(this)
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog  {
        dialog = AlertDialog.Builder(requireContext())
            .setMessage("")
            .setPositiveButton(getString(R.string.new_game),null)
            .setCancelable(false)
            .create()
        return dialog
    }

    private fun onNewGameClicked() {
        dialog.dismiss()
        dialogClosedListener.onDialogClosed()
    }
    override fun onResume() {
        Log.d(TAG, "onResume() called ")
        viewModel.getCurrentWord()
        viewModel.viewState.observe(this){
            when(it.content.isGameWon) {
                true -> {
                    dialog.setMessage(String.format(getString(R.string.won_game_title), it.content.word))
                }
                else -> {
                    dialog.setMessage(String.format(getString(R.string.lost_dialog_title), it.content.word))
                }
            }

            dialog.show()
        }
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            onNewGameClicked()
        }
        super.onResume()
    }

    override fun onDetach() {
        Log.d(TAG, "onDetach() called ")
        super.onDetach()
    }

    companion object {
        const val TAG = "GameWonDialogFragment"
    }
}