package com.remid.hangmangame.hangman_game.presentation.viewstate.viewstate_content

data class HangmanGameContent(
    val victories: Int,
    val gameNumber: Int,
    val leftTriesNumber: Int,
    val guessWord: String?,
)