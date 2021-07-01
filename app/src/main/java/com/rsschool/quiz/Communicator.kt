package com.rsschool.quiz

interface Communicator {
    fun onNextQuestion(userResult: Int, position: Int)
    fun onPreviosQuestion()
    fun onSubmit(userResult: Int, position: Int)
    fun countOfPages(): Int
    fun reboot()
    fun share()
}