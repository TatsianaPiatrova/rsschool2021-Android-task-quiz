package com.rsschool.quiz

interface Communicator {
    fun onNextQuestion(userResult: Int, position: Int)
    fun onPreviosQuestion()
    fun onSubmit(userResult: Int, position: Int)
    fun countOfPages(): Int
    fun reboot()
    //TODO: SetUpStatusBar
    //window.statusBarColor = resources.getColor(statusBarColors[screenCount()])
    //fun getResult() : String
}