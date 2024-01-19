package com.remid.hangmangame.hangman_game.presentation.fragment.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.remid.hangmangame.R
import com.remid.hangmangame.shared.presentation.fragment.BaseDialogFragment

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