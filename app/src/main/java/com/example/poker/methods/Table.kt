package com.example.poker.methods

import com.example.poker.logic.turn.Cards
import com.example.poker.logic.turn.cardImageList
import com.example.poker.logic.turn.cardNameList
import com.example.poker.logic.turn.cardSuitList

class Table {

    lateinit var createdDeck: List<Cards>
    lateinit var createdPlayer: List<Player>

    init {
        createTable()
    }


    fun createTable(
        chipOfPlayers: List<Int> = mutableListOf(
            1500000, 1500000, 1500000, 1500000, 1500000, 1400000
        )
    ) {
        val cardsObj = createCarts()
        cardsObj.shuffle()

        val players: MutableList<Player> = mutableListOf()
        val deckLists: MutableList<Cards> = mutableListOf()


        for (i in 0..15) {
            if (i - 1 == 12) {
                for (k in 0..4) {
                    deckLists.add(cardsObj[i + 1 + k + 1])
                }
                break
            } else
                if ((i + 1) % 2 == 0) {
                    players.add(
                        createPlayer(
                            cardsObj[i - 1],
                            cardsObj[i],
                            chipOfPlayers[(i-1)/2]
                        )
                    )
                }
        }

        val play: List<Player> = players
        val decks: List<Cards> = deckLists

        createdDeck = decks
        createdPlayer = play
    }

    private fun createPlayer(card1: Cards, card2: Cards, chipsHave: Int): Player {
        return Player(card1, card2, chipsHave)
    }

    private fun createCarts(): MutableList<Cards> {
        val listOfCards: MutableList<Cards> = mutableListOf()
        for (i in 0..51) {
            when {
                i < 13 -> {
                    when {
                        i < 5 -> {
                            val temp = Cards(i, cardNameList[i], cardSuitList[0], cardImageList[i])
                            listOfCards.add(temp)
                        }
                        i < 7 -> {
                            val temp = Cards(i, cardNameList[i], cardSuitList[0], cardImageList[i])
                            listOfCards.add(temp)
                        }
                        i == 7 -> {
                            val temp = Cards(i, cardNameList[i], cardSuitList[0], cardImageList[i])
                            listOfCards.add(temp)
                        }
                        i == 8 -> {
                            val temp = Cards(i, cardNameList[i], cardSuitList[0], cardImageList[i])
                            listOfCards.add(temp)
                        }
                        i == 9 -> {
                            val temp = Cards(i, cardNameList[i], cardSuitList[0], cardImageList[i])
                            listOfCards.add(temp)
                        }
                        i == 10 -> {
                            val temp = Cards(i, cardNameList[i], cardSuitList[0], cardImageList[i])
                            listOfCards.add(temp)
                        }
                        i == 11 -> {
                            val temp = Cards(i, cardNameList[i], cardSuitList[0], cardImageList[i])
                            listOfCards.add(temp)
                        }
                        i == 12 -> {
                            val temp = Cards(i, cardNameList[i], cardSuitList[0], cardImageList[i])
                            listOfCards.add(temp)
                        }
                    }
                }
                i < 26 -> {
                    when {
                        i < 18 -> {
                            val temp = Cards(i, cardNameList[i % 13], cardSuitList[1], cardImageList[i])
                            listOfCards.add(temp)
                        }
                        i < 20 -> {
                            val temp = Cards(i, cardNameList[i % 13], cardSuitList[1], cardImageList[i])
                            listOfCards.add(temp)
                        }
                        i == 20 -> {
                            val temp = Cards(i, cardNameList[i % 13], cardSuitList[1], cardImageList[i])
                            listOfCards.add(temp)
                        }
                        i == 21 -> {
                            val temp = Cards(i, cardNameList[i % 13], cardSuitList[1], cardImageList[i])
                            listOfCards.add(temp)
                        }
                        i == 22 -> {
                            val temp = Cards(i, cardNameList[i % 13], cardSuitList[1], cardImageList[i])
                            listOfCards.add(temp)
                        }
                        i == 23 -> {
                            val temp = Cards(i, cardNameList[i % 13], cardSuitList[1], cardImageList[i])
                            listOfCards.add(temp)
                        }
                        i == 24 -> {
                            val temp = Cards(i, cardNameList[i % 13], cardSuitList[1], cardImageList[i])
                            listOfCards.add(temp)
                        }
                        i == 25 -> {
                            val temp = Cards(i, cardNameList[i % 13], cardSuitList[1], cardImageList[i])
                            listOfCards.add(temp)
                        }
                    }
                }
                i < 39 -> {
                    when {
                        i < 30 -> {
                            val temp = Cards(i, cardNameList[i % 13], cardSuitList[2], cardImageList[i])
                            listOfCards.add(temp)
                        }
                        i < 32 -> {
                            val temp = Cards(i, cardNameList[i % 13], cardSuitList[2], cardImageList[i])
                            listOfCards.add(temp)
                        }
                        i == 33 -> {
                            val temp = Cards(i, cardNameList[i % 13], cardSuitList[2], cardImageList[i])
                            listOfCards.add(temp)
                        }
                        i == 34 -> {
                            val temp = Cards(i, cardNameList[i % 13], cardSuitList[2], cardImageList[i])
                            listOfCards.add(temp)
                        }
                        i == 35 -> {
                            val temp = Cards(i, cardNameList[i % 13], cardSuitList[2], cardImageList[i])
                            listOfCards.add(temp)
                        }
                        i == 36 -> {
                            val temp = Cards(i, cardNameList[i % 13], cardSuitList[2], cardImageList[i])
                            listOfCards.add(temp)
                        }
                        i == 37 -> {
                            val temp = Cards(i, cardNameList[i % 13], cardSuitList[2], cardImageList[i])
                            listOfCards.add(temp)
                        }
                        i == 38 -> {
                            val temp = Cards(i, cardNameList[i % 13], cardSuitList[2], cardImageList[i])
                            listOfCards.add(temp)
                        }
                    }
                }
                else -> {
                    when {
                        i < 44 -> {
                            val temp = Cards(i, cardNameList[i % 13], cardSuitList[3], cardImageList[i])
                            listOfCards.add(temp)
                        }
                        i < 46 -> {
                            val temp = Cards(i, cardNameList[i % 13], cardSuitList[3], cardImageList[i])
                            listOfCards.add(temp)
                        }
                        i == 46 -> {
                            val temp = Cards(i, cardNameList[i % 13], cardSuitList[3], cardImageList[i])
                            listOfCards.add(temp)
                        }
                        i == 47 -> {
                            val temp = Cards(i, cardNameList[i % 13], cardSuitList[3], cardImageList[i])
                            listOfCards.add(temp)
                        }
                        i == 48 -> {
                            val temp = Cards(i, cardNameList[i % 13], cardSuitList[3], cardImageList[i])
                            listOfCards.add(temp)
                        }
                        i == 49 -> {
                            val temp = Cards(i, cardNameList[i % 13], cardSuitList[3], cardImageList[i])
                            listOfCards.add(temp)
                        }
                        i == 50 -> {
                            val temp = Cards(i, cardNameList[i % 13], cardSuitList[3], cardImageList[i])
                            listOfCards.add(temp)
                        }
                        i == 51 -> {
                            val temp = Cards(i, cardNameList[i % 13], cardSuitList[3], cardImageList[i])
                            listOfCards.add(temp)
                        }
                    }
                }
            }
        }
        return listOfCards
    }
}
