package org.myapp.andreysumin2.presentation

import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.*
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.myapp.andreysumin2.R
import org.myapp.andreysumin2.databinding.FragmentGameBinding
import org.myapp.andreysumin2.domain.entity.GameResult
import org.myapp.andreysumin2.domain.entity.GameSettings
import org.myapp.andreysumin2.domain.entity.Level


class GameFragment : Fragment() {



    private val tvOptions by lazy {
        mutableListOf<TextView >().apply {
            add(mBinbing.options1)
            add(mBinbing.options2)
            add(mBinbing.options3)
            add(mBinbing.options4)
            add(mBinbing.options5)
            add(mBinbing.options6)
        }

    }

    private val args by navArgs<GameFragmentArgs>()

    private val gameViewModelFactory by lazy {
        GameViewModelFactory(requireActivity().application,args.level)
    }

    private val viewModel by lazy {
        ViewModelProvider(this,gameViewModelFactory)[GameFragmentViewModel::class.java]
    }//оператор лэйзи означает что переменная проинициализируется при первом обращении к ней(лениая реализация)

    private var _mBinding:FragmentGameBinding? = null
    private val mBinbing:FragmentGameBinding
    get() = _mBinding ?: throw RuntimeException("FragmentGameBinding == null")



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _mBinding = FragmentGameBinding.inflate(inflater,container,false)
        return mBinbing.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setClickListener()

    }

    private fun setClickListener(){
        for (tvOption in tvOptions){
            tvOption.setOnClickListener {
                viewModel.chooseAnswer(tvOption.text.toString().toInt())
            }
        }
    }

    private fun observeViewModel(){
        viewModel.question.observe(viewLifecycleOwner){
            mBinbing.textSum.text = it.sum.toString()
            mBinbing.textNum.text = it.visibleNumber.toString()
            for (i in 0 until tvOptions.size) {
                tvOptions[i].text = it.options[i].toString()
            }
        }
        viewModel.percentAnswer.observe(viewLifecycleOwner){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mBinbing.progress.setProgress(it,true)
            }
        }
        viewModel.enoughRightAnswer.observe(viewLifecycleOwner){
            mBinbing.answerProgress.setTextColor(getColorByState(it))
        }
        viewModel.enoughPercentAnswer.observe(viewLifecycleOwner){
             val color = getColorByState(it)
            mBinbing.progress.progressTintList = ColorStateList.valueOf(color)
        }
        viewModel.formatedTime.observe(viewLifecycleOwner){
            mBinbing.timer.text = it
        }
        viewModel.minPercent.observe(viewLifecycleOwner){
            mBinbing.progress.secondaryProgress = it
        }
        viewModel.gameResult.observe(viewLifecycleOwner){
            launchResultFragment(it)
        }
        viewModel.progressAnswer.observe(viewLifecycleOwner){
            mBinbing.answerProgress.text = it
        }
    }

    fun launchResultFragment(gameResult: GameResult){

        findNavController().navigate(GameFragmentDirections
             .actionGameFragmentToResultFragment(gameResult))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _mBinding = null
    }

    private fun getColorByState(state:Boolean): Int{
        val colorId = if (state){
            android.R.color.holo_green_light
        }else{
            android.R.color.holo_red_light
        }
        return ContextCompat.getColor(requireContext(),colorId)
    }
}