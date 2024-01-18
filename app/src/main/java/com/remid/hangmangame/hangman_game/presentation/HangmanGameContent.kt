package com.remid.hangmangame.hangman_game.presentation

data class HangmanGameContent(
    val victories: Int,
    val gameNumber: Int,
    val leftTriesNumber: Int,
    val guessWord: String?,
)