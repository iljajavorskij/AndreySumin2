package org.myapp.andreysumin2.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.myapp.andreysumin2.R
import org.myapp.andreysumin2.databinding.FragmentChooseLevelBinding
import org.myapp.andreysumin2.domain.entity.Level

class ChooseLevelFragment : Fragment() {

    private var _mBinding: FragmentChooseLevelBinding? = null
    private val mBinding:FragmentChooseLevelBinding
    get() = _mBinding ?: throw RuntimeException("FragmentChooseLevelBinding == null")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _mBinding = FragmentChooseLevelBinding.inflate(inflater,container,false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.buttonTest.setOnClickListener {
            launchGameFragment(Level.TEST)
        }
        mBinding.buttonEasy.setOnClickListener {
            launchGameFragment(Level.EASY)
        }
        mBinding.buttonNormal.setOnClickListener {
            launchGameFragment(Level.NORMAL)
        }
        mBinding.buttonHard.setOnClickListener {
            launchGameFragment(Level.HARD)
        }
    }


    private fun launchGameFragment(level:Level){
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_container,GameFragment.newInstance(level ))
            .addToBackStack(GameFragment.NAME)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _mBinding = null
    }

    companion object{

        const val NAME = "ChooseLevelFragment"

        fun newInstance():ChooseLevelFragment{
            return ChooseLevelFragment()
        }
    }

}