package com.remid.hangmangame.hangman_game.business.entity

data class HangmanGameDetails(
    val victories: Int,
    val gameNumber: Int,
    val TriesNumber: Int,
    val guessWord: String?,
    val letterList : List<String>
)