package org.myapp.andreysumin2.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentManager
import org.myapp.andreysumin2.R
import org.myapp.andreysumin2.databinding.FragmentGameBinding
import org.myapp.andreysumin2.databinding.FragmentResultBinding
import org.myapp.andreysumin2.domain.entity.GameResult
import java.lang.RuntimeException


class ResultFragment : Fragment() {

    private lateinit var result: GameResult

    private var _mBinding: FragmentResultBinding? = null
    private val mBinbing: FragmentResultBinding
        get() = _mBinding ?: throw RuntimeException("FragmentGameBinding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parsParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _mBinding = FragmentResultBinding.inflate(inflater,container,false)
        return mBinbing.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
        bindViews()
    }

    private fun bindViews() {
        with(mBinbing){
            reqAnswer.text = String.format(
             getString(
                 R.string.the_required_number_s_of_correct_answers_s)
             ,result.gameSettings.minCountForRightAnswers,result.countOfQuestions)

            scoreAnswer.text = String.format(
                getString(R.string.score_s),result.countOfRightAnswer)
            percenteg.text = String.format(
                getString(R.string.required_percentage_of_correct_answers_s)
                ,result.gameSettings.minPercentOfRightAnswers)
            percentRightAnswer.text = String.format(
                getString(R.string.percentage_of_correct_answers_s_min_s),getPercent(),result.gameSettings.minPercentOfRightAnswers
            )
        }
    }

    private fun getPercent() = with(result){
       if (countOfQuestions == 0){
           0
       }else{
           ((countOfRightAnswer / countOfQuestions.toDouble())*100).toInt()
       }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _mBinding = null
    }

    private fun setClickListeners(){
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,object :OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                retryGame()
            }
        })
        mBinbing.buttonRestart.setOnClickListener {
            retryGame()
        }
    }

    fun parsParams(){
        requireArguments().getParcelable<GameResult>(KEY_GAME_RESULT)?.let {
            result = it
        }
    }

    private fun retryGame(){
        requireActivity().supportFragmentManager
            .popBackStack(ChooseLevelFragment.NAME,FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    companion object{

        private const val KEY_GAME_RESULT = "result"
        fun newInstance(gameResult: GameResult):ResultFragment{
            return ResultFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_GAME_RESULT,gameResult)
                }
            }
        }
    }
}