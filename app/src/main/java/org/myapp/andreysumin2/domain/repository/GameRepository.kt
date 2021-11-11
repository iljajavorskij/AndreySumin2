package org.myapp.andreysumin2.domain.repository

import org.myapp.andreysumin2.domain.entity.GameSettings
import org.myapp.andreysumin2.domain.entity.Level
import org.myapp.andreysumin2.domain.entity.Questions

interface GameRepository {

    fun generateQuestions(
        maxSumValue:Int,
        countOfOptions:Int
    ):Questions

    fun getGameSettings(level: Level):GameSettings
}