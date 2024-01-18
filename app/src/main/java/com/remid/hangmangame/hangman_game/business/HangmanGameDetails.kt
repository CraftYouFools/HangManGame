package com.remid.hangmangame.hangman_game.business

data class HangmanGameDetails(
    val victories: Int,
    val gameNumber: Int,
    val leftTriesNumber: Int,
    val guessWord: String?,
)