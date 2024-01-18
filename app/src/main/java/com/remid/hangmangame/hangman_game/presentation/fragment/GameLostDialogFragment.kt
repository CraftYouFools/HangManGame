package com.remid.hangmangame.hangman_game.presentation.fragment

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.remid.hangmangame.R
import com.remid.hangmangame.hangman_game.presentation.HangmanGameViewState
import com.remid.hangmangame.hangman_game.presentation.viewmodel.HangManGameViewModel
import com.remid.hangmangame.shared.presentation.fragment.BaseDialogFragment
import com.remid.hangmangame.shared.presentation.viewmodel.ViewModelFactory
import javax.inject.Inject

class GameLostDialogFragment : BaseDialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injector.inject(this)
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog  {


       return AlertDialog.Builder(requireContext())
            .setMessage(getString(R.string.lost_dialog_title) )
            .setPositiveButton(getString(R.string.new_game)) { _, _ -> }
            .setCancelable(false)
            .create()
    }


    companion object {
        const val TAG = "GameLostDialogFragment"
    }
}