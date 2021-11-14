package org.myapp.andreysumin2.domain.usecases

import org.myapp.andreysumin2.domain.entity.Questions
import org.myapp.andreysumin2.domain.repository.GameRepository

class GenerateQuestionUseCase (
private val repository: GameRepository
) {
    operator fun invoke(maxSumValue:Int):Questions{
       return repository.generateQuestions(maxSumValue, COUNT_OF_OPTIONS)
    }

    private companion object{
     private const val COUNT_OF_OPTIONS = 6
    }
}