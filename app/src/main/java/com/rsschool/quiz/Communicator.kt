package com.rsschool.quiz

interface Communicator {
    fun onNextQuestion(userResult: Array<String?>)
    fun onPreviosQuestion()
    fun onSubmit(userResult: Array<String?>)
    fun countOfPages(): Int
}