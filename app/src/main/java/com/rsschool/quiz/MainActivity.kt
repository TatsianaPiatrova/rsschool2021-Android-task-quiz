package com.rsschool.quiz

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.rsschool.quiz.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() , Communicator{

    private var uAnswers = arrayOfNulls<String>(5)
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var fragmentList:List<Fragment>
    private val rightAnswers = arrayOf("1", "1", "2", "2", "2")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewPager = binding.viewPager
        viewPager.isUserInputEnabled=false

        fragmentList = getFragmentList()
        viewPager.adapter = ViewPagerAdapter(this,fragmentList)
        viewPager.offscreenPageLimit = fragmentList.size

        viewPager.registerOnPageChangeCallback(object:ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }
        })
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
            ResultFragment.newInstance(0,R.style.Theme_Quiz_First)
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

    override fun onNextQuestion(answer: Array<String?>){
        uAnswers[viewPager.currentItem] = answer.toString()
        ++viewPager.currentItem
    }

    override fun onPreviosQuestion() {
        --viewPager.currentItem
    }

    override fun onSubmit(answer: Array<String?>) {
        val fragmentResult = fragmentList[fragmentList.size-1]
        uAnswers[viewPager.currentItem] = answer.toString()
        val rightAnswersCount = answer.filter { rightAnswers.contains(it) }.count()
        val percent = ((rightAnswersCount.toDouble() / rightAnswers.count()) * 100).toInt()
        if(fragmentResult is ResultFragment) {
            fragmentResult.arguments = bundleOf("RESULT" to percent)
            fragmentResult.update()
        }
        ++viewPager.currentItem
    }

}