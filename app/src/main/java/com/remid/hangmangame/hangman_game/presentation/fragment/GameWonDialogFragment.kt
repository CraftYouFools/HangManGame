package com.remid.hangmangame.hangman_game.presentation.fragment

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.remid.hangmangame.R
import com.remid.hangmangame.shared.presentation.fragment.BaseDialogFragment

class GameWonDialogFragment : BaseDialogFragment() {

    private lateinit var dialog : AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injector.inject(this)
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog  {
        dialog = AlertDialog.Builder(requireContext())
            .setMessage(getString(R.string.won_game_title))
            .setPositiveButton(getString(R.string.new_game)) { _, _ -> }
            .setCancelable(false).create()
        return dialog
    }

    companion object {
        const val TAG = "GameWonDialogFragment"
    }
}