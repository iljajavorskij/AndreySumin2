package org.myapp.andreysumin2.domain.entity

import java.io.Serializable

data class GameSettings(val maxSumValue :Int,
                   val minCountForRightAnswers:Int,
                   val minPercentOfRightAnswers :Int,
                   val gameTimeInSeconds:Int) : Serializable