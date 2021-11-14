package org.myapp.andreysumin2.presentation

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.myapp.andreysumin2.R
import org.myapp.andreysumin2.data.GameRepositiryImpl
import org.myapp.andreysumin2.domain.entity.GameResult
import org.myapp.andreysumin2.domain.entity.GameSettings
import org.myapp.andreysumin2.domain.entity.Level
import org.myapp.andreysumin2.domain.entity.Questions
import org.myapp.andreysumin2.domain.repository.GameRepository
import org.myapp.andreysumin2.domain.usecases.GenerateQuestionUseCase
import org.myapp.andreysumin2.domain.usecases.GetGameSettingsUseCase

class GameFragmentViewModel(application: Application):AndroidViewModel(application) {

    private lateinit var gameSettings:GameSettings
    private lateinit var level: Level
    
    private val context = application

    val repository = GameRepositiryImpl

    private var countRightAnswer = 0
    private var countQuestions = 0

    val generateQuestionUseCase = GenerateQuestionUseCase(repository)
    val getGameSettingsUseCase = GetGameSettingsUseCase(repository)

    private var timer:CountDownTimer? = null

    private val _formatedTime = MutableLiveData<String>()
    val formatedTime:LiveData<String>
    get() = _formatedTime

    private val _question = MutableLiveData<Questions>()
    val question:LiveData<Questions>
    get() = _question


    private val _percentAnswer = MutableLiveData<Int>()
    val percentAnswer:LiveData<Int >
        get() = _percentAnswer

    private val _progressAnswer = MutableLiveData<String>()
    val progressAnswer:LiveData<String>
        get() = _progressAnswer

    private val _enoughRightAnswer = MutableLiveData<Boolean>()
    val enoughRightAnswer:LiveData<Boolean>
        get() = _enoughRightAnswer

    private val _enoughPercentAnswer = MutableLiveData<Boolean>()
    val enoughPercentAnswer:LiveData<Boolean>
        get() = _enoughPercentAnswer

    private val _minPercent = MutableLiveData<Int>()
    val minPercent:LiveData<Int>
        get() = _minPercent

    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult:LiveData<GameResult>
        get() = _gameResult

    fun startGame(level: Level){
        getGameSettings(level)
        startTimer()
        generateQuestions()
        updateProgress()
    }

    fun chooseAnswer(number:Int){
        checkAnswer(number)
        updateProgress()
        generateQuestions()
    }
    
    private fun updateProgress(){
      val percent = calcProgressRightAnswer()
        _percentAnswer.value = percent
        _progressAnswer.value = String.format(
            context.resources.getString(R.string.percentage_of_correct_answers_s_min_s),
            countRightAnswer,
            gameSettings.minCountForRightAnswers
        )
        _enoughRightAnswer.value = countRightAnswer >= gameSettings.minCountForRightAnswers
        _enoughPercentAnswer.value = percent >= gameSettings.minPercentOfRightAnswers
    }
    
    private fun calcProgressRightAnswer():Int{
        if(countRightAnswer == 0){
            return 0
        }
         return((countRightAnswer / countQuestions.toDouble())*100).toInt()
    }
    private fun checkAnswer(number:Int){
        val rightAnswer = question.value?.rightAnswer
        if (number == rightAnswer){
            countRightAnswer++
        }
        countQuestions++
    }

    private fun getGameSettings(level: Level){
        this.level = level
        this.gameSettings = getGameSettingsUseCase(level)
    }

    private fun startTimer(){
        timer = object : CountDownTimer(gameSettings.gameTimeInSeconds * MILLIS_IN
            , MILLIS_IN){
            override fun onTick(millis: Long) {
                _formatedTime.value = formateTime(millis)

            }

            override fun onFinish() {
                finishGame()            }

         }
        timer?.start()
    }

    private fun formateTime(millis: Long): String{
        val second = millis / MILLIS_IN
        val minutes = second / SECOND_IN_MINUTES
        val leftSeconds = second - (minutes * SECOND_IN_MINUTES )
        return String.format("%02d:%02d",minutes,leftSeconds)
    }

    private fun generateQuestions(){
       _question.value =  generateQuestionUseCase(gameSettings.maxSumValue)
    }

    private fun finishGame(){
       _gameResult.value = GameResult(
            enoughRightAnswer.value == true && enoughPercentAnswer.value == true,
            countRightAnswer,
            countQuestions,
            gameSettings
        )
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }


    companion object{
       const val MILLIS_IN = 1000L
        const val SECOND_IN_MINUTES = 60
    }

}