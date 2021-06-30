package com.rsschool.quiz

enum class BaseOfQuiz (
    val question: String,
    val answers: Array<String>
)
{
    First(
    "Who teaches Potions class at Hogwarts?",
        arrayOf("Professor Snape", "Professor Flitwick", "Professor Dumbledore", "Professor McGonagall", "Hagrid")
    ),

    Second(
    "And who teaches the flying lessons?",
    arrayOf("Professor Flitwick", "Madam Hooch", "Madam Pince", "Professor McGonagall", "Madam Pomfrey")
    ),

    Third(
    "In Charms class, what do the students have to try and make float?",
    arrayOf("A fruit bowl", "A ball", "A rock", "A feather", "A wand")
    ),

    Fourth(
        "In Defence Against the Dark Arts, Professor Quirrell’s classroom smelt like...",
        arrayOf("Smelly cheese", "Onions", "Garlic", "Perfume", "Cat")
    ),

    Fifth(
        "What faculty doesn't exist at Hogwarts",
        arrayOf("The Slytherin", "The Hufflepuff", "The Gryffindor", "The Durmstrang", "The Ravenclaw")
    )
}

