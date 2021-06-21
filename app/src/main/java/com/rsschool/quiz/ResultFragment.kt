package com.rsschool.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.FragmentResultBinding
import kotlin.system.exitProcess

class ResultFragment : Fragment(){

    private var _binding: FragmentResultBinding?=null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val theme = arguments?.getInt(THEME) ?: 0
        requireContext().theme.applyStyle(theme, true);
        _binding = FragmentResultBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.rebootApp.setOnClickListener{

        }
        binding.closeApp.setOnClickListener{
            requireActivity().finish();
            exitProcess(0);
        }
    }

    fun update(){
        val result = arguments?.getInt(RESULT)
        binding.resultTextView.text = "Your result is ${result.toString()}%"
    }
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        fun newInstance(result:Int, theme:Int): ResultFragment {
            val fragment = ResultFragment()
            val args = Bundle()
            args.putInt(THEME, theme)
            args.putInt(RESULT, result)
            fragment.arguments = args
            return fragment
        }
        private const val RESULT = "RESULT"
        private const val THEME = "THEME"
    }

}