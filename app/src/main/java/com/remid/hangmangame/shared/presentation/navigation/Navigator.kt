package com.remid.hangmangame.shared.presentation.navigation

import androidx.fragment.app.FragmentManager
import com.remid.hangmangame.R
import com.remid.hangmangame.shared.business.INavigator
import javax.inject.Inject

class Navigator @Inject constructor(private val fragmentManager: FragmentManager) : INavigator {

    override fun attachHangmanGame() {
      /*  fragmentManager.beginTransaction()
            .add(R.id.container, ListFragment.newInstance())
            .commit()*/
    }

}