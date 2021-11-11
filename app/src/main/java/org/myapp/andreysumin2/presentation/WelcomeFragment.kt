package org.myapp.andreysumin2.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.myapp.andreysumin2.R
import org.myapp.andreysumin2.databinding.FragmentWelcomeBinding
import java.lang.RuntimeException


class  WelcomeFragment : Fragment() {

    private var _mBinding:FragmentWelcomeBinding? = null
    private val mBinding:FragmentWelcomeBinding
    get() = _mBinding ?: throw RuntimeException("FragmentWelcomeBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _mBinding = FragmentWelcomeBinding.inflate(inflater,container,false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.buttonStart.setOnClickListener {
            launchChooseLevelFragment()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _mBinding = null
    }

    private fun launchChooseLevelFragment(){
        requireActivity().supportFragmentManager
            .beginTransaction()
            .addToBackStack(ChooseLevelFragment.NAME)
            .replace(R.id.main_container,ChooseLevelFragment.newInstance())
            .commit()
    }


}