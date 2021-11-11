package org.myapp.andreysumin2.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.myapp.andreysumin2.R
import org.myapp.andreysumin2.databinding.FragmentGameBinding
import org.myapp.andreysumin2.domain.entity.GameResult
import org.myapp.andreysumin2.domain.entity.GameSettings
import org.myapp.andreysumin2.domain.entity.Level


class GameFragment : Fragment() {

    private lateinit var level: Level

    private var _mBinding:FragmentGameBinding? = null
    private val mBinbing:FragmentGameBinding
    get() = _mBinding ?: throw RuntimeException("FragmentGameBinding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parsArguments()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _mBinding = FragmentGameBinding.inflate(inflater,container,false)
        return mBinbing.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinbing.textSum.setOnClickListener {
            launchResultFragment(GameResult(true
                ,12
                ,11
                , GameSettings(0
                    ,0
                    ,0
                     ,0)))
        }
    }

    fun launchResultFragment(result:GameResult){
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_container,ResultFragment.newInstance(result))
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _mBinding = null
    }

    fun parsArguments(){
        level = requireArguments().getSerializable(KEY_LEVEL) as Level
    }


    companion object{

        const val NAME = "GameFragment"

        private const val KEY_LEVEL = "level"

        fun newInstance(level:Level):GameFragment{
            return GameFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(KEY_LEVEL,level)
                }
            }
        }

    }
}