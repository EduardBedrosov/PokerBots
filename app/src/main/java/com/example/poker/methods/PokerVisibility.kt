package com.example.poker.methods


//data class PokerVisibility(
//    val playersViews: List<Player>,
//    val chipsTextViews: List<String>,
//    val checkTextViews: List<String>,
//    val foldTextViews: List<String>,
//    val callTextViews: List<String>,
//    )


fun <T> setTextToList(
    text1: T, text2: T, text3: T,
    text4: T, text5: T, text6: T
): List<T> {
    return listOf(text1, text2, text3, text4, text5, text6)
}

fun <T> setToPlayerOfNumber(
    number: Int, listOfT: List<T>
): T {
    return listOfT[number]
}
