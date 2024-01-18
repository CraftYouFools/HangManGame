package com.remid.hangmangame.di.presentation


import com.remid.hangmangame.hangman_game.presentation.fragment.GameLostDialogFragment
import com.remid.hangmangame.hangman_game.presentation.fragment.GameWonDialogFragment
import com.remid.hangmangame.hangman_game.presentation.fragment.HangmanFragment
import com.remid.hangmangame.shared.presentation.activity.MainActivity
import dagger.Subcomponent

@PresentationScope
@Subcomponent(modules = [PresentationModule::class])
interface PresentationComponent {

    fun inject(activity : MainActivity)

    fun inject(fragment : HangmanFragment)
    fun inject(gameWonDialogFragment: GameWonDialogFragment)
    fun inject(gameLostDialogFragment: GameLostDialogFragment)
}