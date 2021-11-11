package org.myapp.andreysumin2.data

import org.myapp.andreysumin2.domain.entity.GameSettings
import org.myapp.andreysumin2.domain.entity.Level
import org.myapp.andreysumin2.domain.entity.Questions
import org.myapp.andreysumin2.domain.repository.GameRepository
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

object GameRepositiryImpl:GameRepository {

    const val MIN_SUM_VALUE = 2
    const val MIN_VISIBLE_NUM = 1

    override fun generateQuestions(maxSumValue: Int, countOfOptions: Int): Questions {
        val sum = Random.nextInt(MIN_SUM_VALUE,maxSumValue + 1)
        val visibleValue = Random.nextInt(MIN_VISIBLE_NUM,sum)
        val options = HashSet<Int>()
        val rightAnswer = sum - visibleValue
        options.add(rightAnswer)
        val from = max(rightAnswer - countOfOptions, MIN_VISIBLE_NUM)
        val to = min(maxSumValue,rightAnswer + countOfOptions)
        while (options.size < countOfOptions){
            options.add(Random.nextInt(from,to))
        }
        return Questions(sum,visibleValue,options.toList())
    }

    override fun getGameSettings(level: Level): GameSettings {
        return when (level){

            Level.TEST -> GameSettings(
                10,
                3,
                50,
                8
            )
            Level.EASY -> GameSettings(20
                ,10
                ,70
                ,40)

            Level.NORMAL -> GameSettings(30
                ,20
                ,80
                ,30)

            Level.HARD -> GameSettings(40
                ,30
                ,80
                ,20)
        }

    }
}