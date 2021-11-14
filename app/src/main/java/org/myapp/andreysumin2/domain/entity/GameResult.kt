package org.myapp.andreysumin2.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class GameResult(
    val winner:Boolean,
    val countOfRightAnswer:Int,
    val countOfQuestions:Int,
    val gameSettings: GameSettings
): Parcelable
