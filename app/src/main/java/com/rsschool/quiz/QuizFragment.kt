package com.rsschool.quiz

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.FragmentQuizBinding
import java.io.Serializable


class QuizFragment : Fragment() {
    private var uAnswerID: Int = 0

    private var page: Int = 0
    private var theme: Int = 0
    private var position: Communicator? = null

    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        page = arguments?.getInt(PAGE) ?: 0
        theme = arguments?.getInt(THEME) ?: 0
        requireContext().theme.applyStyle(theme, true);
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pageCount = position?.countOfPages() ?:0
        binding.toolbar.title = "Question $page"

        when (page) {
            1 -> {
                setData(BaseOfQuiz.First)
                binding.apply {
                    previousButton.isVisible = false
                    toolbar.navigationIcon = null
                }
            }
            2 -> setData(BaseOfQuiz.Second)
            3 -> setData(BaseOfQuiz.Third)
            4 -> setData(BaseOfQuiz.Fourth)
            5 -> {
                setData(BaseOfQuiz.Fifth)
                binding.nextButton.text = "Submit"
            }
        }
        binding?.radioGroup?.setOnCheckedChangeListener { _, checkedId ->
            run {
                binding?.nextButton?.isEnabled = true
                when (checkedId) {
                    binding?.optionOne?.id -> uAnswerID = 0
                    binding?.optionTwo?.id -> uAnswerID = 1
                    binding?.optionThree?.id -> uAnswerID = 2
                    binding?.optionFour?.id -> uAnswerID = 3
                    binding?.optionFive?.id -> uAnswerID = 4
                }
            }
        }

        binding.nextButton.setOnClickListener{
            if(page == pageCount-1) {
                position?.onSubmit(uAnswerID, page-1)
            } else {
                position?.onNextQuestion(uAnswerID, page-1)
            }
        }

        binding.previousButton.setOnClickListener {
            position?.onPreviosQuestion()
        }

        binding.toolbar.setNavigationOnClickListener {
            position?.onPreviosQuestion()
        }

        binding.nextButton.isEnabled = false
    }

    private fun setData(quiz: BaseOfQuiz) {
        with(binding) {
            question.text = quiz.question
            optionOne.text = quiz.answers[0]
            optionTwo.text = quiz.answers[1]
            optionThree.text = quiz.answers[2]
            optionFour.text = quiz.answers[3]
            optionFive.text = quiz.answers[4]
        }
    }
    override fun onAttach(context: Context) {
        if (context is Communicator) {
            position = context }
        else {
            throw RuntimeException("$context must implement FragmentQuiz.FragmentAction")
        }
        super.onAttach(context)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(page:Int, question: BaseOfQuiz, theme:Int): QuizFragment {
            val fragment = QuizFragment()
            val args = Bundle()
            args.putInt(THEME, theme)
            args.putInt(PAGE, page)
            args.putSerializable(QUESTION,question as Serializable)
            fragment.arguments = args
            return fragment
        }
        private const val PAGE = "PAGE"
        private const val THEME = "THEME"
        private const val QUESTION="QUESTION"
    }

}
