package com.example.poker.logic.turn

import android.graphics.drawable.Drawable
import com.example.poker.R

data class Cards(
    val id: Int,
    val cardName: String,
    val cardSuit: String,
    val cardImage: Int = 0
)

val cardNameList = listOf(
    "Two",
    "Three",
    "Four",
    "Five",
    "Six",
    "Seven",
    "Eight",
    "Nine",
    "Ten",
    "J",
    "Q",
    "K",
    "A"
)

val cardSuitList = listOf(
    "hearts",
    "diamonds",
    "clubs",
    "spades"
)

val cardImageList = listOf(
   R.drawable.ic__0,
   R.drawable.ic__1,
    R.drawable.ic__2,
    R.drawable.ic__3,
    R.drawable.ic__4,
    R.drawable.ic__5,
    R.drawable.ic__6,
    R.drawable.ic__7,
    R.drawable.ic__8,
    R.drawable.ic__9,
    R.drawable.ic__10,
    R.drawable.ic__11,
    R.drawable.ic__12,
    R.drawable.ic__13,
    R.drawable.ic__14,
    R.drawable.ic__15,
    R.drawable.ic__16,
    R.drawable.ic__17,
    R.drawable.ic__18,
    R.drawable.ic__19,
    R.drawable.ic__20,
    R.drawable.ic__21,
    R.drawable.ic__22,
    R.drawable.ic__23,
    R.drawable.ic__24,
    R.drawable.ic__25,
    R.drawable.ic__26,
    R.drawable.ic__27,
    R.drawable.ic__28,
    R.drawable.ic__29,
    R.drawable.ic__30,
    R.drawable.ic__31,
    R.drawable.ic__32,
    R.drawable.ic__33,
    R.drawable.ic__34,
    R.drawable.ic__35,
    R.drawable.ic__36,
    R.drawable.ic__37,
    R.drawable.ic__38,
    R.drawable.ic__39,
    R.drawable.ic__40,
    R.drawable.ic__41,
    R.drawable.ic__42,
    R.drawable.ic__43,
    R.drawable.ic__44,
    R.drawable.ic__45,
    R.drawable.ic__46,
    R.drawable.ic__47,
    R.drawable.ic__48,
    R.drawable.ic__49,
    R.drawable.ic__50,
    R.drawable.ic__51
)