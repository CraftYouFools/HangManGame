package com.remid.hangmangame.shared.business

sealed class HangAppResult<out T> {
    data class OnFailure(val exception: Exception) : HangAppResult<Nothing>()
    data class OnSuccess<T>(val data: T) : HangAppResult<T>()

}