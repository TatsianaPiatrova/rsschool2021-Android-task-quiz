package com.rsschool.quiz

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.FragmentResultBinding
import kotlin.system.exitProcess

class ResultFragment : Fragment(){

    private var _binding: FragmentResultBinding? = null
    private val binding get() = requireNotNull(_binding)

    private var result: Communicator? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResultBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rebootApp.setOnClickListener{
            result?.reboot()
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (activity is Communicator) {
            result = activity as Communicator
        } else {
            throw RuntimeException(activity.toString() + " must implement Communicator interface")
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
    override fun onDetach() {
        super.onDetach()
        result = null
    }

    companion object {
        fun newInstance(result:Int): ResultFragment {
            val fragment = ResultFragment()
            val args = Bundle()
            args.putInt(RESULT, result)
            fragment.arguments = args
            return fragment
        }
        private const val RESULT = "RESULT"
    }

}