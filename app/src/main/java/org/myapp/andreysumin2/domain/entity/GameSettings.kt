package org.myapp.andreysumin2.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable
@Parcelize
data class GameSettings(val maxSumValue :Int,
                   val minCountForRightAnswers:Int,
                   val minPercentOfRightAnswers :Int,
                   val gameTimeInSeconds:Int) : Parcelable