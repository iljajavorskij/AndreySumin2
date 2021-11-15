package org.myapp.andreysumin2.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.myapp.andreysumin2.R
import org.myapp.andreysumin2.databinding.FragmentGameBinding
import org.myapp.andreysumin2.databinding.FragmentResultBinding
import org.myapp.andreysumin2.domain.entity.GameResult
import java.lang.RuntimeException


class ResultFragment : Fragment() {



    private var _mBinding: FragmentResultBinding? = null
    private val mBinbing: FragmentResultBinding
        get() = _mBinding ?: throw RuntimeException("FragmentGameBinding == null")

     private val args by navArgs<ResultFragmentArgs>()

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
             ,args.gameResult.gameSettings.minCountForRightAnswers,args.gameResult.countOfQuestions)

            scoreAnswer.text = String.format(
                getString(R.string.score_s),args.gameResult.countOfRightAnswer)
            percenteg.text = String.format(
                getString(R.string.required_percentage_of_correct_answers_s)
                ,args.gameResult.gameSettings.minPercentOfRightAnswers)
            percentRightAnswer.text = String.format(
                getString(R.string.percentage_of_correct_answers_s_min_s),getPercent(),args.gameResult.gameSettings.minPercentOfRightAnswers
            )
        }
    }

    private fun getPercent() = with(args.gameResult){
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
        mBinbing.buttonRestart.setOnClickListener {
            retryGame()
        }
    }

    

    private fun retryGame(){
        findNavController().popBackStack()
    }
}