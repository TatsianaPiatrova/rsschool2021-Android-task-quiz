package com.rsschool.quiz

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.rsschool.quiz.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() , Communicator{

    private var userAnswers = mutableListOf(-1, -1, -1, -1, -1)
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var fragmentList:List<Fragment>
    private val rightAnswers = listOf(0, 1, 3, 1, 3)
    var count = 0
    private val statusBarColors = listOf(
        R.color.deep_orange_100_dark
        ,R.color.yellow_100_dark
        ,R.color.deep_purple_100_dark
        ,R.color.light_green_100_dark
        ,R.color.cyan_100_dark
        ,R.color.white)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewPager = binding.viewPager
        viewPager.isUserInputEnabled=false

        fragmentList = getFragmentList()
        viewPager.adapter = ViewPagerAdapter(this,fragmentList)
        viewPager.offscreenPageLimit = fragmentList.size

        viewPager.isUserInputEnabled = false

        viewPager.registerOnPageChangeCallback(object:ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                changeColorStatusBar(position)
            }
        })
    }

    fun changeColorStatusBar(page: Int){
        val fragmentQuiz = fragmentList[page]
        if (fragmentQuiz is QuizFragment) {
            window.statusBarColor = ResourcesCompat
                .getColor(resources, statusBarColors[page], null)
        } else
            if (fragmentQuiz is ResultFragment) {
                window.statusBarColor = ResourcesCompat
                    .getColor(resources, statusBarColors[page], null)
            }
    }


    override fun countOfPages():Int {
        return  requireNotNull(viewPager.adapter).itemCount
    }


    fun getFragmentList():List<Fragment>{
        return listOf(
            QuizFragment.newInstance(1, BaseOfQuiz.First, R.style.Theme_Quiz_First),
            QuizFragment.newInstance(2, BaseOfQuiz.Second, R.style.Theme_Quiz_Second),
            QuizFragment.newInstance(3, BaseOfQuiz.Third, R.style.Theme_Quiz_Third),
            QuizFragment.newInstance(4, BaseOfQuiz.Fourth, R.style.Theme_Quiz_Fourth),
            QuizFragment.newInstance(5, BaseOfQuiz.Fifth, R.style.Theme_Quiz_Fifth),
            ResultFragment.newInstance(0)
        )
    }
    inner class ViewPagerAdapter(fa: FragmentActivity,var fragmentList:List<Fragment>) : FragmentStateAdapter(fa){

        override fun getItemCount(): Int {
            return fragmentList.size
        }

        override fun createFragment(position: Int): Fragment {
            return when(position){
                0 ->fragmentList[position]
                1 ->fragmentList[position]
                2 ->fragmentList[position]
                3 ->fragmentList[position]
                4 ->fragmentList[position]
                5 ->fragmentList[position]
                else -> throw IllegalArgumentException("non position!")
            }
        }
    }

    override fun onNextQuestion(answer: Int, position: Int){
        userAnswers[position] = answer
        ++viewPager.currentItem
    }

    override fun onPreviosQuestion() {
        --viewPager.currentItem
    }

    override fun onSubmit(answer: Int, position: Int) {
        val fragmentResult = fragmentList[fragmentList.size-1]
        userAnswers[position] = answer
        for (index in userAnswers.indices) {
            if (userAnswers[index] == rightAnswers[index]) {
                count++
            }
        }
        count*=20
        if(fragmentResult is ResultFragment) {
            fragmentResult.arguments = bundleOf("RESULT" to count)
            fragmentResult.update()
        }
        ++viewPager.currentItem
    }
    override fun reboot() {
        fragmentList = getFragmentList()

        viewPager.also {
            it.adapter = ViewPagerAdapter(this, fragmentList)
            it.offscreenPageLimit = fragmentList.size
            it.currentItem = 0}

        userAnswers = mutableListOf(-1, -1, -1, -1, -1)
    }

    override fun share() {
        val result = buildString{
        append("Your result is ${count} %")
        append("\n\n")
        appendLine("1)${BaseOfQuiz.First.question}")
        appendLine("Your answer: ${BaseOfQuiz.First.answers[userAnswers[0]]}")
        appendLine("2)${BaseOfQuiz.Second.question}")
        appendLine("Your answer: ${BaseOfQuiz.Second.question[userAnswers[1]]}")
        appendLine("3)${BaseOfQuiz.Third.question}")
        appendLine("Your answer: ${BaseOfQuiz.Third.answers[userAnswers[2]]}")
        appendLine("4)${BaseOfQuiz.Fourth.question}")
        appendLine("Your answer: ${BaseOfQuiz.Fourth.answers[userAnswers[3]]}")
        appendLine("5)${BaseOfQuiz.Fifth.question}")
        appendLine("Your answer: ${BaseOfQuiz.Fifth.answers[userAnswers[4]]}")
        }
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, "Results of quiz")
        intent.putExtra(Intent.EXTRA_TEXT, result)
        startActivity(intent)
    }
}