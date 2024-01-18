package com.remid.hangmangame.hangman_game.business.entity

data class HangmanGameDetails(
    val victories: Int,
    val gameNumber: Int,
    val leftTriesNumber: Int,
    val guessWord: String?,
    val letterList : List<String>
)