package com.example.poker

import com.example.poker.logic.*
import com.example.poker.logic.turn.Cards

/**
stugac 95%ov ashxatogh funkcia
talis es carter u 5 kard hashvuma inch kombinacyaya mott
hamematuma urishi combinacyai het tvi mecutyan hakadarc hamematakan
 */


/**
preflopgame kanchel +1
lastraise initialise true to other
 */


fun combination(playerCard1: Cards, playerCard2: Cards, deck: List<Cards>): Int {

    val card1WithFixedId = if (playerCard1.id < 13) {
        playerCard1.id
    } else
        ((playerCard1.id) % 13)

    val card2WithFixedId = if (playerCard2.id < 13) {
        playerCard2.id
    } else
        ((playerCard2.id) % 13)

//    val deckWithIncreasedIds = deckIdIncreasing(deck)
//    val deckAndPlayersCardsWithIncreasedIds =
//        cardOfSevenIdIncreasing(playerCard1, playerCard2, deck)


    val flushableList = flushable(playerCard1, playerCard2, deck)

    val repeatable = repeatableCardsNumbers(playerCard1, playerCard2, deck)

    /***********************************************************************************************
    StreetFlush
     **********************************************************************************************/


    if (flushableList != null) {
        val streetableList = streetable(flushableList)
        if (streetableList != null) {
            for (i in (streetableList.size - 1) downTo (streetableList.size - 3))// mb size-4
                if ((playerCard1.id == streetableList[i]) ||
                    (playerCard1.id == streetableList[i])
                ) {
                    println("StreetFlush 1-2")
                    return 1
                } else
                    println("StreetFlush 1-2")
            return 2
        }

    }

    /*********************************************************************************************
     *4 OF A KInd
     *******************************************************************************************/


    if (repeatable != null) {
        val repeatTimes = repeatedTimes(playerCard1, playerCard2, deck)
        val repeatIds = repeatableCarsIdList(playerCard1, playerCard2, deck)

        if (repeatTimes != null && repeatIds != null) {
            for (i in repeatTimes.indices) {
                if (repeatTimes[i] == 4) {
                    if ((card1WithFixedId == repeatIds[i]) ||
                        (card2WithFixedId == repeatIds[i])
                    ) {
                        if (repeatable == 1) {
                            println("4 OF A KInd 3 - 16 ")
                            return 3
                        } else
                            if (repeatable == 2) {
                                for (k in repeatTimes.indices) {
                                    if ((repeatTimes[k] == 2) || (repeatTimes[k] == 3)) {
                                        if (repeatIds[i] > repeatIds[k]) {
                                            println("4 OF A KInd 3 - 16 ")
                                            return 3
                                        } else {
                                            println("4 OF A KInd 3 - 16 ")
                                            return 4
                                        }
                                    } else {
                                        if (k == (repeatTimes.size - 1)) {
                                            println("4 OF A KInd 3 - 16 ")
                                            return 3
                                        }
                                    }
                                }
                            }
                    } else {
                        println("4 OF A KInd 3 - 16 ")
                        return (17 - highOfThree(
                            card1WithFixedId, card2WithFixedId, deckIdFix(deck), repeatIds[i]
                        ))

                    }
                }
            }
        }
    }

    /***********************************************************************************************
     *4 FULL HOUSE 17+
     **********************************************************************************************/

    if (repeatable != null) {
        var duplicated1 = 0
        var duplicated2 = 0
        var tripled = 0
        var tripleBool = false
        val repeatTimes2 = repeatedTimes(playerCard1, playerCard2, deck)
        val repeatIds2 = repeatableCarsIdList(playerCard1, playerCard2, deck)

        if (repeatable == 3) {

            if (repeatTimes2 != null && repeatIds2 != null) {
                for (i in repeatTimes2.indices) {
                    if (repeatTimes2[i] == 3) {
                        tripled = i
                        tripleBool = true
                    }
                }
                for (k in 0..2) {
                    if (repeatTimes2[k] == 2) {
                        duplicated1 = k
                        break
                    }
                }
                for (k in 2 downTo 0) {
                    if (repeatTimes2[k] == 2) {
                        duplicated2 = k
                        break
                    }
                }
                val duplicated = if (repeatIds2[duplicated1] < repeatIds2[duplicated2]) {
                    repeatIds2[duplicated2]
                } else
                    repeatIds2[duplicated1]

                if (tripleBool) {
                    println("FULL HOUSE 17 -269")
                    return (269 - (20 * (repeatIds2[tripled]) + duplicated))
                }
            }
        } else
            if (repeatable == 2) {
                var tempTripled = 0
                if (repeatTimes2 != null && repeatIds2 != null) {
                    for (i in repeatTimes2.indices) {
                        if (repeatTimes2[i] == 3) {
                            if (tripleBool) {
                                tempTripled = i
                                break
                            }
                            tripleBool = true
                            tripled = i
                        }
                    }
                    if (repeatIds2[tripled] < repeatIds2[tempTripled]) {
                        tripled = tempTripled
                    }

                    for (k in 0..1) {
                        if (repeatTimes2[k] == 2) {
                            duplicated1 = k
                        }
                    }
                    if (tempTripled != 0) {
                        duplicated1 = tempTripled
                    }
                    if (tripleBool) {
                        println("FULL HOUSE 17 -269")
                        return (269 - (20 * repeatIds2[tripled] + repeatIds2[duplicated1]))
                    }
                }
            }
    }


    /***********************************************************************************************
     *Flush 269+
     **********************************************************************************************/

    if (flushable(playerCard1, playerCard2, deck) != null) {

        var flushList: MutableList<Int>? = flushable(playerCard1, playerCard2, deck)
        if (flushList != null) {
            flushList = deckIdFixOfIdList(flushList)
            flushList = flushList.sortedDescending().toMutableList()
            val playCardMax: Int
            var tempCard1 = 0
            var tempCard2 = 0
            val suit: String = flushSuitIs(playerCard1, playerCard2, deck)

            if (flushList.size > 5) {
                flushList.removeLast()
            }
            if (flushList.size > 5) {
                flushList.removeLast()
            }

            if (flushList.contains(card1WithFixedId) && playerCard1.cardSuit == suit) {
                tempCard1 = card1WithFixedId
            }
            if (flushList.contains(card2WithFixedId) && playerCard2.cardSuit == suit) {
                tempCard2 = card2WithFixedId
            }
            if (tempCard1 > tempCard2) {
                playCardMax = tempCard1
            } else
                playCardMax = tempCard2

            println("Flush 270-282")
            return (282 - playCardMax)

        }
    }

    /***********************************************************************************************
     *Street 282+
     **********************************************************************************************/

    if (streetable(playerCard1, playerCard2, deck) != null) {

        val streetList: List<Int>? = streetable(playerCard1, playerCard2, deck)

        /**
         *Chi kara lini  null stegh baic stugum em vor toghi
         */
        if (streetList != null) {
            println("Street 283 - 292")
            return (295 - streetList.maxOrNull()!!)
        }
    }

    /***********************************************************************************************
     *3 of a kind / set 292+
     *********************************************************************************************/


    if (repeatable != null) {
        val repeatTimes3 = repeatedTimes(playerCard1, playerCard2, deck)
        val repeatIds3 = repeatableCarsIdList(playerCard1, playerCard2, deck)

        if (repeatTimes3 != null && repeatIds3 != null) {
            /**
             *kara chlini iharke baic karogha xangaruma voobshe ?
             */
            if ((repeatable == 1) && (repeatTimes3[0] == 3)) {

                println("3 of a kind 293 - 12183")
                return (13336 - (1000 * (repeatIds3[0] + 1) + highOf(
                    card1WithFixedId, card2WithFixedId, deckIdFix(deck), repeatIds3[0]
                )))

            }
        }
    }


    /***********************************************************************************************
     *2 Pairs 12183+
     **********************************************************************************************/


    if (repeatable != null) {
        val repeatTimes4 = repeatedTimes(playerCard1, playerCard2, deck)
        val repeatIds4 = repeatableCarsIdList(playerCard1, playerCard2, deck)
        var highCardId = 0
        var lowCardId = 0
        if (repeatTimes4 != null && repeatIds4 != null) {
            if (repeatable == 2) {
                if (repeatIds4[0] < repeatIds4[1]) {
                    highCardId = repeatIds4[1]
                    lowCardId = repeatIds4[0]
                } else {
                    highCardId = repeatIds4[0]
                    lowCardId = repeatIds4[1]
                }

                println("2 PAIRS 12184-23518")
                return (24554 - (1000 * highCardId + 30 * (lowCardId + 1) + highOfTwoPair(
                    card1WithFixedId, card2WithFixedId,
                    deckIdFix(deck), highCardId, lowCardId
                )))

            } else
                if (repeatable == 3) {
                    val idIncreseList = idIcreaseSort(repeatIds4[0], repeatIds4[1], repeatIds4[2])
                    println("2 PAIRS 12184-23518")
                    return (24554 - (1000 * idIncreseList[0] + 30 * (idIncreseList[1] + 1) + highOfTwoPair(
                        card1WithFixedId, card2WithFixedId,
                        deckIdFix(deck), idIncreseList[0], idIncreseList[1]
                    )))
                }
        }
    }


    /***********************************************************************************************
     *1 Pair 23518+
     **********************************************************************************************/

    if (repeatable != null) {
        val repeatTimes5 = repeatedTimes(playerCard1, playerCard2, deck)
        val repeatIds5 = repeatableCarsIdList(playerCard1, playerCard2, deck)

        if (repeatTimes5 != null && repeatIds5 != null) {
            if ((repeatable == 1) && (repeatTimes5[0] == 2)) {

                println("ONE PAIR 23519 - 388677")
                return (424831 - (30000 * (repeatIds5[0] + 1) + highOfOnePair(
                    card1WithFixedId, card2WithFixedId, deckIdFix(deck), repeatIds5[0]
                )))

            }
        }
    }


    /***********************************************************************************************
     *High Card 388677+
     **********************************************************************************************/
    val highCardSum: Int = 1011413 - highCardSum(
        card1WithFixedId, card2WithFixedId, deckIdFix(deck)
    )
    println("High Card 388678-649,051")

    /**
     *MAX VALUE 649,051
     */

    return highCardSum

}


fun deckIdIncreasing(deck: List<Cards>): List<Int> {

    return deckIdFix(deck).sortedBy { it }
}

fun cardOfSevenIdIncreasing(playerCar1: Cards, playerCard2: Cards, deck: List<Cards>): List<Int> {

    return deckAndPlayerCardsIdFix(playerCar1, playerCard2, deck).sortedBy { it }
}

fun cardOfSixIdIncreasing(playerCar1: Cards, playerCard2: Cards, deck: List<Cards>): List<Int> {

    val deckIds = mutableListOf<Int>()
    val fixDeck = deckAndPlayerCardsIdFix(playerCar1, playerCard2, deck)

    for (i in fixDeck.indices) {
        deckIds.add(fixDeck[i])
    }

    deckIds.removeLast()


    return deckIds.sortedBy { it }
}

fun cardOfFiveIdIncreasing(playerCar1: Cards, playerCard2: Cards, deck: List<Cards>): List<Int> {

    val deckIds = mutableListOf<Int>()
    val fixDeck = deckAndPlayerCardsIdFix(playerCar1, playerCard2, deck)

    for (i in fixDeck.indices) {
        deckIds.add(fixDeck[i])
    }

    deckIds.removeLast()
    deckIds.removeLast()

    return deckIds.sortedBy { it }
}

/**
 * ete flush chka 7 cartov return null ete return flash sarqac carteri ID-ner@ mlistum
 */

fun flushable(playerCard1: Cards, playerCard2: Cards, deck: List<Cards>): MutableList<Int>? {

    val allCards = mutableListOf<Cards>()
    val suits = mutableListOf<String>()

    allCards.add(playerCard1)
    allCards.add(playerCard2)
    for (i in deck.indices) {
        allCards.add(deck[i])
    }
    for (i in allCards.indices) {
        suits.add(allCards[i].cardSuit)
    }

    val mapOfSuitsTimes = suits.groupingBy { it }.eachCount().filter {
        it.value > 1
    }

    val suitedList = mutableListOf<Int>()
    var suitIs: String? = null
    val nullReturn: MutableList<Int>? = null
    val suitsKeys: MutableList<String> = mapToKeyList(mapOfSuitsTimes)
    val timesValues: MutableList<Int> = mapToValuesList(mapOfSuitsTimes)

    for (i in timesValues.indices) {

        if (timesValues[i] > 4) {

            suitIs = suitsKeys[i]
            break
        } else
            if (i == timesValues.lastIndex) {
                return nullReturn
            }
    }

    for (i in allCards.indices) {
        if (allCards[i].cardSuit == suitIs)
            suitedList.add(allCards[i].id)
    }

//    suitedList.sortBy { it } // senca petq te sovorakan hertakanutyamb
    /**
     * es method@ mi qich xarna sovorakan ogtagorcman hamar
     */

    return suitedList
}



/**
 * return Suit String
 * only call if flush is not null
 */
fun flushSuitIs(playerCard1: Cards, playerCard2: Cards, deck: List<Cards>): String {

    val allCards = mutableListOf<Cards>()
    val suits = mutableListOf<String>()

    allCards.add(playerCard1)
    allCards.add(playerCard2)
    for (i in deck.indices) {
        allCards.add(deck[i])
    }
    for (i in allCards.indices) {
        suits.add(allCards[i].cardSuit)
    }

    val mapOfSuitsTimes = suits.groupingBy { it }.eachCount().filter {
        it.value > 1
    }

    val suitedList = mutableListOf<Int>()
    var suitIs: String = "NotFLUSH"
    val nullReturn: MutableList<Int>? = null
    val suitsKeys: MutableList<String> = mapToKeyList(mapOfSuitsTimes)
    val timesValues: MutableList<Int> = mapToValuesList(mapOfSuitsTimes)

    for (i in timesValues.indices) {

        if (timesValues[i] > 4) {

            suitIs = suitsKeys[i]
            break
        }
    }
    return suitIs

}


fun streetable(playerCar1: Cards, playerCard2: Cards, deck: List<Cards>): List<Int>? {

    var listOfFixedIds =
        deckAndPlayerCardsIdFix(playerCar1, playerCard2, deck).distinct().sorted().toMutableList()


    if (listOfFixedIds.contains(12)) {
        listOfFixedIds = (mutableListOf(-1) + listOfFixedIds).toMutableList()
    }

    if (listOfFixedIds.size < 5) {

        return null
    }

    var temp = 0
    var temp2 = 0
    for (i in 0 until (listOfFixedIds.size - 1)) {
        if (listOfFixedIds[i - temp2] != (listOfFixedIds[i + 1 - temp2] - 1)) {
            if (temp < 4) {
                for (k in 0..temp) {
                    listOfFixedIds.remove(listOfFixedIds[0])
                    temp2++
                }
            } else
                for (k in 0..(listOfFixedIds.size - temp - 2)) {
                    listOfFixedIds.remove(listOfFixedIds.last())
                    temp2++
                }
            temp = 0
            if (listOfFixedIds.size < 5) {

                return null
            }
        } else {
            temp++
        }
    }

    return listOfFixedIds

}

fun streetable(list: MutableList<Int>): List<Int>? {

    /**
     * menak street flushi hamara use arvum
     *
     */

    var listOfOriginalIds = list.distinct().sorted().toMutableList()

    when {
        listOfOriginalIds.contains(12) -> {
            listOfOriginalIds = (mutableListOf(-1) + listOfOriginalIds).toMutableList()
        }
        listOfOriginalIds.contains(25) -> {
            listOfOriginalIds = (mutableListOf(12) + listOfOriginalIds).toMutableList()
        }
        listOfOriginalIds.contains(38) -> {
            listOfOriginalIds = (mutableListOf(25) + listOfOriginalIds).toMutableList()
        }
        listOfOriginalIds.contains(51) -> {
            listOfOriginalIds = (mutableListOf(38) + listOfOriginalIds).toMutableList()
        }
    }


    if (listOfOriginalIds.size < 5) {

        return null
    }

    var temp = 0
    var temp2 = 0
    for (i in 0 until (listOfOriginalIds.size - 1)) {
        if (listOfOriginalIds[i - temp2] != (listOfOriginalIds[i + 1 - temp2] - 1)) {
            if (temp < 4) {
                for (k in 0..temp) {
                    listOfOriginalIds.remove(listOfOriginalIds[0])
                    temp2++
                }
            } else
                for (k in 0..(listOfOriginalIds.size - temp - 2)) {
                    listOfOriginalIds.remove(listOfOriginalIds.last())
                    temp2++
                }
            temp = 0
            if (listOfOriginalIds.size < 5) {

                return null
            }
        } else {
            temp++
        }
    }
    return listOfOriginalIds
}



fun repeatableCardsNumbers(card1: Cards, card2: Cards, deck: List<Cards>): Int? {

    var allCards = mutableListOf<Int>()


    allCards.add(card1.id)
    allCards.add(card2.id)

    for (i in deck.indices) {
        allCards.add(deck[i].id)
    }
    allCards = deckIdFixOfIdList(allCards)


    val mapOfSuitsTimes = allCards.groupingBy { it }.eachCount().filter {
        it.value > 1
    }
    val cardRepeatedId: MutableList<Int> = mapToKeyList(mapOfSuitsTimes)
    val repeatedNumber = cardRepeatedId.size

    if (repeatedNumber == 0) {
        return null
    }

    return repeatedNumber
}

fun repeatableCarsIdList(card1: Cards, card2: Cards, deck: List<Cards>): MutableList<Int>? {

    var allCards = mutableListOf<Int>()
    val nullReturn: MutableList<Int>? = null
    allCards.add(card1.id)
    allCards.add(card2.id)

    for (i in deck.indices) {
        allCards.add(deck[i].id)
    }
    allCards = deckIdFixOfIdList(allCards)


    val mapOfSuitsTimes = allCards.groupingBy { it }.eachCount().filter {
        it.value > 1
    }
    val cardRepeatedId: MutableList<Int> = mapToKeyList(mapOfSuitsTimes)

    val repeatedNumber = cardRepeatedId.size

    if (repeatedNumber == 0) {
        return nullReturn
    }

    return cardRepeatedId
}

fun repeatedTimes(card1: Cards, card2: Cards, deck: List<Cards>): MutableList<Int>? {

    var allCards = mutableListOf<Int>()
    val nullReturn: MutableList<Int>? = null

    allCards.add(card1.id)
    allCards.add(card2.id)

    for (i in deck.indices) {
        allCards.add(deck[i].id)
    }
    allCards = deckIdFixOfIdList(allCards)

    val mapOfSuitsTimes = allCards.groupingBy { it }.eachCount().filter {
        it.value > 1
    }
    val timesValues: MutableList<Int> = mapToValuesList(mapOfSuitsTimes)
    val repeatedNumber = timesValues.size

    if (repeatedNumber == 0) {
        return nullReturn
    }

    return timesValues
}


//fun streatable(playerCar1: Cards, playerCard2: Cards, deck: List<Cards>): List<Int>{
//
//
//
//}

fun highOfThree(card1Temp: Int, card2Temp: Int, deck: List<Int>, cardToEraseId: Int): Int {
    /**
     * 1 time use method lol
     */
    val tempList = mutableListOf<Int>()
    tempList.add(card1Temp)
    tempList.add(card2Temp)
    for (i in deck.indices) {
        tempList.add(deck[i])
    }

//    for (i in tempList.indices) {
//        if (tempList[i] == cardToEraseId) {
//            tempList.removeAt(i)
//        }
//    }

    while (tempList.contains(cardToEraseId)) {
        tempList.remove(cardToEraseId)
    }


    return tempList.maxOrNull()!!
}

fun highOfOnePair(
    card1Temp: Int,
    card2Temp: Int,
    deck: List<Int>,
    cardToEraseId: Int
): Int {
    /**
     * 1 time use method lol
     */
    var tempList = mutableListOf<Int>()
    tempList.add(card1Temp)
    tempList.add(card2Temp)
    for (i in deck.indices) {
        tempList.add(deck[i])
    }


    tempList = tempList.distinct().sortedDescending().toMutableList()


    return (1000 * tempList[0] + 30 * tempList[1] + tempList[2])
}


fun highCardSum(
    card1Temp: Int,
    card2Temp: Int,
    deck: List<Int>
): Int {
    /**
     * 1 time use method lol
     */
    var tempList = mutableListOf<Int>()
    tempList.add(card1Temp)
    tempList.add(card2Temp)
    for (i in deck.indices) {
        tempList.add(deck[i])
    }

    tempList = tempList.sortedDescending().toMutableList()

    return (50000 * tempList[0] + 2000 * tempList[1] + 70 * tempList[2] + 3 * tempList[3] + tempList[4])
}

fun highOf(card1Temp: Int, card2Temp: Int, deck: List<Int>, cardToEraseId: Int): Int {
    /**
     * 1 time use method lol
     */

    var tempList = mutableListOf<Int>()
    tempList.add(card1Temp)
    tempList.add(card2Temp)
    for (i in deck.indices) {
        tempList.add(deck[i])
    }

//    for (i in tempList.indices) {
//        if (tempList[i] == cardToEraseId) {
//            tempList.removeAt(i)
//        }
//    }
    while (tempList.contains(cardToEraseId)) {
        tempList.remove(cardToEraseId)
    }


    tempList = tempList.distinct().sortedDescending().toMutableList()


    return (30 * tempList[0] + tempList[1])
}

fun highOfTwoPair(
    card1Temp: Int,
    card2Temp: Int,
    deck: List<Int>,
    card1ToEraseId: Int,
    card2ToEraseId: Int
): Int {
    /**
     * 1 time use method lol
     */
    val tempList = mutableListOf<Int>()
    tempList.add(card1Temp)
    tempList.add(card2Temp)
    for (i in deck.indices) {
        tempList.add(deck[i])
    }

//    for (i in tempList.indices) {
//        if (tempList[i] == card1ToEraseId) {
//            tempList.removeAt(i)
//        }
//    }
    while (tempList.contains(card1ToEraseId)) {
        tempList.remove(card1ToEraseId)
    }

//
//    for (i in tempList.indices) {
//        if (tempList[i] == card2ToEraseId) {
//            tempList.removeAt(i)
//        }
//    }

    while (tempList.contains(card2ToEraseId)) {
        tempList.remove(card2ToEraseId)
    }


    return tempList.maxOrNull()!!
}


fun listOfIdSort(card1Temp: Int, card2Temp: Int, deck: List<Int>): MutableList<Int> {

    var tempList = mutableListOf<Int>()
    tempList.add(card1Temp)
    tempList.add(card2Temp)
    for (i in deck.indices) {
        tempList.add(deck[i])
    }
    tempList = tempList.distinct().sortedDescending().toMutableList()

    return tempList
}

fun listToDistinctSize(card1Temp: Int, card2Temp: Int, deck: List<Int>): Int {

    var tempList = mutableListOf<Int>()
    tempList.add(card1Temp)
    tempList.add(card2Temp)
    for (i in deck.indices) {
        tempList.add(deck[i])
    }
    tempList = tempList.distinct().sortedDescending().toMutableList()

    return tempList.size
}

fun idIcreaseSort(id1: Int, id2: Int, id3: Int): MutableList<Int> {
    return mutableListOf(id1, id2, id3).sortedDescending().toMutableList()
}
