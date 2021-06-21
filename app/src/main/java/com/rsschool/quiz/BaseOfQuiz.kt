package com.rsschool.quiz

enum class BaseOfQuiz (
    val question: String,
    val answers: Array<String>
)
{
    First(
    "Test question number 1",
        arrayOf("1", "2", "3", "4", "5")
    ),

    Second(
    "Test question number 2",
    arrayOf("1", "2", "3", "4", "5")
    ),

    Third(
    "Test question number 3",
    arrayOf("1", "2", "3", "4", "5")
    ),

    Fourth(
        "Test question number 4",
        arrayOf("1", "2", "3", "4", "5")
    ),

    Fifth(
        "Test question number 5",
        arrayOf("1", "2", "3", "4", "5")
    )
}