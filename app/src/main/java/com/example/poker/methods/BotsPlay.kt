package com.example.poker.logic


import com.example.poker.combination
import com.example.poker.logic.turn.Cards
import com.example.poker.methods.Player

fun playablePreFlopRaise(
    card1: Cards,
    card2: Cards,
    position: Int,
    pot: Int,
    raiseCounter: Int,
    playerRaiseSize: Int,
    callDoneCounter: Int,
    bool: Boolean
): Boolean {

    if (bool) {
        return false
    }
    if (!playing) {
        ((1..6).random() == 1)
        return false
    }
    if (raiseCounter > 8) {
        callAll = true
        return false
    }
    var raiseCounterTemp = raiseCounter
    if (callDoneCounter == 2) {
        raiseCounterTemp++
    }

    if (callDoneCounter == 4) {
        raiseCounterTemp++
        raiseCounterTemp++
    }


    val card1WithFixedId = if (card1.id < 13) {
        card1.id
    } else
        ((card1.id) % 13)

    val card2WithFixedId = if (card2.id < 13) {
        card2.id
    } else
        ((card2.id) % 13)


    var normalRaiseSize: Boolean = normalRaisePreFlop(pot, playerRaiseSize, callDoneCounter)
    var notNormalRaiseSize: Boolean = notNormalRaisePreFlop(pot, playerRaiseSize, callDoneCounter)
    var wrongRaiseSize: Boolean = wrongRaisePreFlop(pot, playerRaiseSize, callDoneCounter)

    if (agroPlayer()) {
        raiseCounterTemp--
    }
    if (veryAgroPlayer()) {
        raiseCounterTemp--
    }
    if (notNormalRaisePlayer()) {
        if (notNormalRaiseSize) {
            notNormalRaiseSize = false
            normalRaiseSize = true
        }
    }
    if (wrongRaisePlayer()) {
        if (wrongRaiseSize) {
            wrongRaiseSize = false
            notNormalRaiseSize = true
        }
    }
    /**
     * only raise
     */

    if (((card1WithFixedId == 12) && (card2WithFixedId == 12)) ||
        ((card1WithFixedId == 11) && (card2WithFixedId == 11)) ||
        ((card1WithFixedId == 10) && (card2WithFixedId == 10)) ||
        ((card1WithFixedId == 12) && (card2WithFixedId == 11)) ||
        ((card1WithFixedId == 11) && (card2WithFixedId == 12))
    ) {
        return true
    }
    /**
     * mostly raise ,somtimes call
     */
    if (((card1WithFixedId == 9) && (card2WithFixedId == 9)) ||
        ((card1WithFixedId == 8) && (card2WithFixedId == 8)) ||
        ((card1WithFixedId == 7) && (card2WithFixedId == 7)) ||
        ((card1WithFixedId == 12) && (card2WithFixedId == 10)) ||
        ((card1WithFixedId == 10) && (card2WithFixedId == 12))
    ) {

        if (raiseCounter == 0) {
            return true
        }
        if (normalRaiseSize) {

            if (raiseCounterTemp < 3) {
                return true
            } else
                if (raiseCounterTemp < 4) {
                    if (raiseCounter > 0) {
                        callAll = true
                        return false
                    }
                }
        } else
            if (notNormalRaiseSize) {
                if (raiseCounterTemp < 2) {
                    if (((card1WithFixedId == 9) && (card2WithFixedId == 9)) ||
                        ((card1WithFixedId == 8) && (card2WithFixedId == 8))
                    ) {
                        return true
                    }
                } else {
                    if (raiseCounter > 0) {
                        callAll = true
                        return false
                    }
                }

            } else
                if (wrongRaiseSize) {
                    if (raiseCounterTemp < 1) {
                        if (((card1WithFixedId == 9) && (card2WithFixedId == 9)) ||
                            ((card1WithFixedId == 8) && (card2WithFixedId == 8))
                        ) {
                            return true
                        } else {
                            if (raiseCounter > 0) {
                                callAll = true
                                return false
                            }
                        }
                    } else {
                        if (raiseCounterTemp < 2) {
                            if (((card1WithFixedId == 9) && (card2WithFixedId == 9)) ||
                                ((card1WithFixedId == 8) && (card2WithFixedId == 8))
                            ) {
                                if (raiseCounter > 0) {
                                    callAll = true
                                    return false
                                }
                            } else {
                                return false
                            }
                        }
                    }
                }
    }

    /**
     * rare raise , mostly call
     */
    if (((card1WithFixedId == 6) && (card2WithFixedId == 6)) ||
        ((card1WithFixedId == 5) && (card2WithFixedId == 5)) ||
        ((card1WithFixedId == 4) && (card2WithFixedId == 4)) ||
        ((card1WithFixedId == 3) && (card2WithFixedId == 3)) ||
        ((card1WithFixedId == 2) && (card2WithFixedId == 2)) ||
        ((card1WithFixedId == 1) && (card2WithFixedId == 1)) ||
        ((card1WithFixedId == 0) && (card2WithFixedId == 0)) ||
        ((card1WithFixedId == 12) && (card2WithFixedId == 9)) ||
        ((card1WithFixedId == 9) && (card2WithFixedId == 12)) ||
        ((card1WithFixedId == 12) && (card2WithFixedId == 8)) ||
        ((card1WithFixedId == 8) && (card2WithFixedId == 12)) ||
        ((card1WithFixedId == 11) && (card2WithFixedId == 10)) ||
        ((card1WithFixedId == 10) && (card2WithFixedId == 11))
    ) {
        if (raiseCounter == 0) {
            return true
        }
        if (normalRaiseSize) {

            if (raiseCounterTemp < 2) {
                return true
            } else
                if (raiseCounterTemp < 4) {
                    if (raiseCounter > 0) {
                        callAll = true
                        return false
                    }
                } else {
                    return false
                }
        } else
            if (notNormalRaiseSize) {
                if (raiseCounterTemp < 2) {
                    if (((card1WithFixedId == 6) && (card2WithFixedId == 6)) ||
                        ((card1WithFixedId == 5) && (card2WithFixedId == 5)) ||
                        ((card1WithFixedId == 12) && (card2WithFixedId == 9)) ||
                        ((card1WithFixedId == 9) && (card2WithFixedId == 12)) ||
                        ((card1WithFixedId == 11) && (card2WithFixedId == 10)) ||
                        ((card1WithFixedId == 10) && (card2WithFixedId == 11))
                    ) {
                        if (raiseCounter > 0) {
                            callAll = true
                            return false
                        }
                    }
                } else {
                    return false
                }
            } else
                if (wrongRaiseSize) {
                    return false
                }
    }
    /**
     * sometimes call to raiser and opens
     */


    if (((card1WithFixedId == 12) && (card2WithFixedId == 7)) ||
        ((card1WithFixedId == 7) && (card2WithFixedId == 12)) ||
        ((card1WithFixedId == 11) && (card2WithFixedId == 9)) ||
        ((card1WithFixedId == 9) && (card2WithFixedId == 11)) ||
        ((card1WithFixedId == 10) && (card2WithFixedId == 9)) ||
        ((card1WithFixedId == 9) && (card2WithFixedId == 10))
    ) {
        if (raiseCounter == 0) {
            return true
        }
        if (normalRaiseSize) {
            if (raiseCounterTemp < 1) {
                return true
            } else
                if (raiseCounterTemp < 2) {
                    if (raiseCounter > 0) {
                        callAll = true
                        return false
                    }
                } else {
                    return false
                }
        } else
            if (notNormalRaiseSize) {
                if (raiseCounterTemp < 0) {
                    if (raiseCounter > 0) {
                        callAll = true
                        return false
                    }
                } else {
                    return false
                }
            } else {
                return false
            }
    }


    /**
     * raise only if opens
     */

    if (((
                (card1WithFixedId == 12) && (card2WithFixedId == 7)) ||
                ((card1WithFixedId == 7) && (card2WithFixedId == 12)) ||
                ((card1WithFixedId == 12) && (card2WithFixedId == 6)) ||
                ((card1WithFixedId == 6) && (card2WithFixedId == 12)) ||
                ((card1WithFixedId == 12) && (card2WithFixedId == 5)) ||
                ((card1WithFixedId == 5) && (card2WithFixedId == 12)) ||
                ((card1WithFixedId == 12) && (card2WithFixedId == 4)) ||
                ((card1WithFixedId == 4) && (card2WithFixedId == 12)) ||
                ((card1WithFixedId == 10) && (card2WithFixedId == 8)) ||
                ((card1WithFixedId == 8) && (card2WithFixedId == 10)) ||
                ((card1WithFixedId == 10) && (card2WithFixedId == 7)) ||
                ((card1WithFixedId == 7) && (card2WithFixedId == 10)) ||
                ((card1WithFixedId == 9) && (card2WithFixedId == 8)) ||
                ((card1WithFixedId == 8) && (card2WithFixedId == 9)) ||
                ((card1WithFixedId == 9) && (card2WithFixedId == 7)) ||
                ((card1WithFixedId == 7) && (card2WithFixedId == 9)) ||
                ((card1WithFixedId == 8) && (card2WithFixedId == 7)) ||
                ((card1WithFixedId == 7) && (card2WithFixedId == 8)) ||
                ((card1WithFixedId == 8) && (card2WithFixedId == 6)) ||
                ((card1WithFixedId == 6) && (card2WithFixedId == 8)) ||
                ((card1WithFixedId == 7) && (card2WithFixedId == 6)) ||
                ((card1WithFixedId == 6) && (card2WithFixedId == 7))
                )
    ) {
        if ((raiseCounterTemp < 1) && (position == 1 || position == 2 || position == 3 || position == 5)) {
            if (raiseCounter < 1) {
                return true
            } else {
                if ((normalRaiseSize) && (raiseCounter == 1) && ((1..2).random() == 1)) {
                    callAll = true
                    return false
                } else
                    return false
            }
        }
    }
    /**
     * sometimes call to raiser suited
     */
    if (card1.cardSuit == card2.cardSuit) {
        if (((card1WithFixedId == 12) && (card2WithFixedId == 6)) ||
            ((card1WithFixedId == 6) && (card2WithFixedId == 12)) ||
            ((card1WithFixedId == 12) && (card2WithFixedId == 5)) ||
            ((card1WithFixedId == 5) && (card2WithFixedId == 12)) ||
            ((card1WithFixedId == 12) && (card2WithFixedId == 4)) ||
            ((card1WithFixedId == 4) && (card2WithFixedId == 12)) ||
            ((card1WithFixedId == 12) && (card2WithFixedId == 3)) ||
            ((card1WithFixedId == 3) && (card2WithFixedId == 12)) ||
            ((card1WithFixedId == 12) && (card2WithFixedId == 2)) ||
            ((card1WithFixedId == 2) && (card2WithFixedId == 12)) ||
            ((card1WithFixedId == 12) && (card2WithFixedId == 1)) ||
            ((card1WithFixedId == 1) && (card2WithFixedId == 12)) ||
            ((card1WithFixedId == 12) && (card2WithFixedId == 0)) ||
            ((card1WithFixedId == 12) && (card2WithFixedId == 0)) ||
            ((card1WithFixedId == 11) && (card2WithFixedId == 9)) ||
            ((card1WithFixedId == 9) && (card2WithFixedId == 11)) ||
            ((card1WithFixedId == 11) && (card2WithFixedId == 8)) ||
            ((card1WithFixedId == 8) && (card2WithFixedId == 11)) ||
            ((card1WithFixedId == 11) && (card2WithFixedId == 7)) ||
            ((card1WithFixedId == 7) && (card2WithFixedId == 11)) ||
            ((card1WithFixedId == 10) && (card2WithFixedId == 8)) ||
            ((card1WithFixedId == 8) && (card2WithFixedId == 10)) ||
            ((card1WithFixedId == 10) && (card2WithFixedId == 7)) ||
            ((card1WithFixedId == 7) && (card2WithFixedId == 10)) ||
            ((card1WithFixedId == 9) && (card2WithFixedId == 8)) ||
            ((card1WithFixedId == 8) && (card2WithFixedId == 9)) ||
            ((card1WithFixedId == 9) && (card2WithFixedId == 7)) ||
            ((card1WithFixedId == 7) && (card2WithFixedId == 9)) ||
            ((card1WithFixedId == 8) && (card2WithFixedId == 7)) ||
            ((card1WithFixedId == 7) && (card2WithFixedId == 8))

        ) {
            if (raiseCounter == 0) {
                return true
            } else
                if ((raiseCounter == 1) && (normalRaiseSize)) {
                    callAll = true
                    return false
                } else
                    return false
        }
    }




    if (card1.cardSuit == card2.cardSuit) {
        if (
            ((card1WithFixedId == 8) && (card2WithFixedId == 6)) ||
            ((card1WithFixedId == 6) && (card2WithFixedId == 8)) ||
            ((card1WithFixedId == 7) && (card2WithFixedId == 6)) ||
            ((card1WithFixedId == 6) && (card2WithFixedId == 7)) ||
            ((card1WithFixedId == 6) && (card2WithFixedId == 5)) ||
            ((card1WithFixedId == 5) && (card2WithFixedId == 6)) ||
            ((card1WithFixedId == 5) && (card2WithFixedId == 4)) ||
            ((card1WithFixedId == 4) && (card2WithFixedId == 5)) ||
            ((card1WithFixedId == 4) && (card2WithFixedId == 3)) ||
            ((card1WithFixedId == 3) && (card2WithFixedId == 2)) ||
            ((card1WithFixedId == 3) && (card2WithFixedId == 2)) ||
            ((card1WithFixedId == 2) && (card2WithFixedId == 3))
        ) {
            if ((1..2).random() == 1) {
                if ((raiseCounter == 0) && (position == 2 || position == 3)) {
                    return true
                } else
                    if ((raiseCounter == 1) && (normalRaiseSize) && (position == 2 || position == 3 || position == 5)) {
                        callAll = true
                        return false
                    } else
                        return false
            }
        }
    }

    if ((position == 5) && (raiseCounter == 1) && (normalRaiseSize) && (callDoneCounter < 2)) {
        if (((1..3).random() == 1) || ((1..2).random() == 2)) {
            callAll = true
            return false
        }
    }

    return false
}


fun playablePreFlopCall(
    bool: Boolean
): Boolean {

    if (bool) {
        return false
    }
    if (callAll) {
        callAll = false
        return true

    }
    return false
}


fun playableFlopRaise(
    card1: Cards,
    card2: Cards,
    boardCard1: Cards,
    boardCard2: Cards,
    boardCard3: Cards,
    pot: Int,
    callDoneCounter: Int = 0,
    raiseCounter: Int = 0,
    raiseSize: Int = 0,
    bool: Boolean
): Boolean {

    if (bool) {
        return false
    }

    if (!playing) {
        ((1..6).random() == 1)
        return false
    }

    if (raiseCounter > 6) {
        callAll = true
        return false
    }

    var raiseCounterTemp = raiseCounter

    if (callDoneCounter == 2) {
        raiseCounterTemp++
    }

    if (callDoneCounter == 4) {
        raiseCounterTemp++
        raiseCounterTemp++
    }

    val fakeCards4: Cards = Cards(-15, "Fake1", "NoSuit1")
    val fakeCards5: Cards = Cards(-18, "Fake2", "NoSuit2")

    val card1WithFixedId = if (card1.id < 13) {
        card1.id
    } else
        ((card1.id) % 13)

    val card2WithFixedId = if (card2.id < 13) {
        card2.id
    } else
        ((card2.id) % 13)

    val deck = creatDeck(
        boardCard1, boardCard2, boardCard3,
        fakeCards4, fakeCards5
    )
    val combo = combination(card1, card2, deck)

    val lowRaiseSize = lowRaise(pot, raiseSize, raiseCounter, callDoneCounter)
    var normalRaiseSize: Boolean = normalRaise(pot, raiseSize, raiseCounter, callDoneCounter)
    var notNormalRaiseSize: Boolean = notNormalRaise(pot, raiseSize, raiseCounter, callDoneCounter)
    var wrongRaiseSize: Boolean = wrongRaise(pot, raiseSize, raiseCounter, callDoneCounter)

    if (lowRaiseSize) {
        raiseCounterTemp--
    }
    if (agroPlayer()) {
        raiseCounterTemp--
    }
    if (veryAgroPlayer()) {
        raiseCounterTemp--
    }
    if (notNormalRaisePlayer()) {
        if (notNormalRaiseSize) {
            notNormalRaiseSize = false
            normalRaiseSize = true
        }
    }
    if (wrongRaisePlayer()) {
        if (wrongRaiseSize) {
            wrongRaiseSize = false
            notNormalRaiseSize = true
        }
    }
    /**
     * only raise noric sarqel schechikner@
     */

    if (combo == 1 || combo == 2 || combo == 3 || combo == 4) {
        return if (((1..3).random() == 1) && (raiseCounter > 0)) {
            callAll = true
            false
        } else {
            true
        }
    }
    /**
     * Mostly Raise
     */
    if ((combo in 5..6) || (combo in 18..68) ||
        (combo in 270..274) || (combo in 283..286) ||
        (combo in 293..5000)
    ) {
        if (raiseCounter == 0) {
            return true
        }
        if (lowRaiseSize) {
            return true
        }
        if (normalRaiseSize) {
            if (raiseCounterTemp < 2) {
                return if (((1..5).random() == 1) && (raiseCounter > 0)) {
                    callAll = true
                    false
                } else {
                    true
                }
            } else
                if (raiseCounterTemp < 3) {
                    if (((1..4).random() == 1) && (raiseCounter > 0)) {
                        callAll = true
                        return false
                    } else {
                        return true
                    }
                } else
                    if (raiseCounterTemp < 4) {
                        if (((combo == 5) || (combo == 6)) || (combo in 18..35) ||
                            (combo in 270..271) || (combo in 283..285) ||
                            ((highestPairOrNo(deck)) && (combo in 293..5000))
                        ) {
                            if (((1..3).random() == 1) && (raiseCounter > 0)) {
                                callAll = true
                                return false
                            } else {
                                return true
                            }
                        }
                    } else

                    /**
                     * stegh arden ankax raisi qanakic
                     */
                        if (raiseCounterTemp > 3) {
                            if ((combo == 5) || (combo in 18..25) ||
                                (combo in 270..271) || (combo in 283..284) ||
                                ((highestPairOrNo(deck)) && (combo in 293..3000))
                            ) {
                                if (((1..2).random() == 1) && (raiseCounter > 0)) {
                                    callAll = true
                                    return false
                                } else {
                                    return true
                                }
                            }
                        }
        } else
            if (notNormalRaiseSize) {
                if ((combo == 5) || (combo == 6) || (combo in 18..35) ||
                    (combo in 270..271) || (combo in 283..285) ||
                    ((highestPairOrNo(deck)) && (combo in 293..5000))
                ) {
                    if (((1..3).random() == 1) && (raiseCounter > 0)) {
                        callAll = true
                        return false
                    } else {
                        return true
                    }
                }
            } else
                if (wrongRaiseSize) {
                    if ((combo == 5) || (combo in 18..25) ||
                        (combo in 270..271) || (combo in 283..284) ||
                        ((highestPairOrNo(deck)) && (combo in 293..3000))
                    ) {
                        if (((1..3).random() == 1) && (raiseCounter > 0)) {
                            callAll = true
                            return false
                        } else {
                            return true
                        }
                    }
                }
    }


    /**
     * Mostly Call sometime Raise
     */

    if ((combo in 7..8) || (combo in 69..268) ||
        (combo in 275..282) || (combo in 287..292) ||
        (combo in 5001..10300) || (combo in 12184..15184)
    ) {
        if (raiseCounter == 0) {
            return true
        }
        if (lowRaiseSize) {
            return true
        }
        if (normalRaiseSize) {
            if (raiseCounterTemp < 2) {
                return true
            } else
                if (raiseCounterTemp < 3) {
                    if ((combo in 6..7) || (combo in 69..168) ||
                        (combo in 275..280) || (combo in 287..290) ||
                        ((highestPairOrNo(deck)) && (combo in 5001..8001)) ||
                        ((highestPairOrNo(deck)) && (combo in 12184..13584))
                    ) {
                        if (((1..3).random() == 1) && (raiseCounter > 0)) {
                            callAll = true
                            return false
                        } else {
                            return true
                        }
                    }
                } else
                    if ((raiseCounterTemp < 4) && ((combo in 6..7) || (combo in 69..168) ||
                                (combo in 275..280) || (combo in 287..290) ||
                                ((highestPairOrNo(deck)) && (combo in 5001..8001)) ||
                                ((highestPairOrNo(deck)) && (combo in 12184..13584))
                                )
                    ) {
                        if (raiseCounter > 0) {
                            callAll = true
                            return false
                        }
                    } else
                        if (((1..4).random() == 1) && (raiseCounter > 0)) {
                            callAll = true
                            return false
                        } else {
                            return false
                        }
        } else
            if (notNormalRaiseSize) {
                if ((combo in 6..7) || (combo in 69..168) ||
                    (combo in 275..280) || (combo in 287..290) ||
                    ((highestPairOrNo(deck)) && (combo in 5001..8001)) ||
                    ((highestPairOrNo(deck)) && (combo in 12184..13584))
                ) {
                    if (((1..2).random() == 1) && (raiseCounter > 0)) {
                        callAll = true
                        return false
                    } else if (((1..2).random() == 1) && (raiseCounter > 0)) {
                        callAll = true
                        return false
                    } else {
                        return false
                    }
                }
            } else
                if (wrongRaiseSize) {
                    if (((1..2).random() == 1) && (raiseCounter > 0) && ((combo in 6..7) || (combo in 69..168) ||
                                (combo in 275..280) || (combo in 287..290) ||
                                ((highestPairOrNo(deck)) && (combo in 5001..8001)) ||
                                ((highestPairOrNo(deck)) && (combo in 12184..13584))
                                )
                    ) {
                        callAll = true
                        return false
                    } else {
                        return false
                    }
                }
    }


    /**
     * aranc patrasti dzeri
     * havanakan kombinacyaner unenalov call
     * STREET AND FLEESH DRAW
     */


    if ((flushableWithFiveCards(card1, card2, deck)) &&
        (streetableWithFiveCards(card1, card2, deck))
    ) {
        if (raiseCounter == 0) {
            return true
        }
        if (lowRaiseSize) {
            return true
        }
        if (!((deck[0].cardSuit == deck[1].cardSuit) && (deck[0].cardSuit == deck[2].cardSuit))) {
            if (normalRaiseSize) {
                if (raiseCounterTemp < 3) {
                    return true
                } else
                    if (raiseCounterTemp < 4) {
                        if (((1..3).random() == 1) && (raiseCounter > 0)) {
                            callAll = true
                            return false
                        } else {
                            return true
                        }
                    } else
                        if (raiseCounterTemp > 0) {
                            if (raiseCounter > 0) {
                                callAll = true
                                return false
                            }
                        }
            } else
                if (notNormalRaiseSize) {

                    if (((card1WithFixedId > 9) || (card2WithFixedId > 9)) && (raiseCounter > 0)) {
                        callAll = true
                        return false
                    } else if (((1..3).random() == 1) && (raiseCounter > 0)) {
                        callAll = true
                        return false
                    } else {
                        return false
                    }
                } else
                    if (wrongRaiseSize) {

                        if (((card1WithFixedId > 10) || (card2WithFixedId > 10)) && (raiseCounter > 0)) {
                            callAll = true
                            return false
                        } else {
                            return false
                        }
                    }
        } else
            if (normalRaiseSize) {
                if (raiseCounterTemp < 2) {
                    return true
                } else
                    if (raiseCounterTemp < 3) {
                        if (((card1WithFixedId > 10) || (card2WithFixedId > 10)) && (raiseCounter > 0)) {
                            callAll = true
                            return false
                        } else {
                            return false
                        }
                    }
            } else
                if (notNormalRaiseSize) {
                    if (((card1WithFixedId > 10) || (card2WithFixedId > 10)) && (raiseCounter > 0)) {
                        callAll = true
                        return false
                    } else {
                        return false
                    }
                } else
                    if (wrongRaiseSize) {
                        return false
                    }
    }

    /**
     * Mostly Pass sometime Call rare Raise
     */

    if (((combo in 10301..12183) || (combo in 15185..23518) ||
                (combo in 23519..124831) || (combo in 1300..1520)) ||
        ((card1WithFixedId == 12) && (card2WithFixedId == 11)) ||
        ((card1WithFixedId == 11) && (card2WithFixedId == 12)) ||
        ((card1WithFixedId == 12) && (card2WithFixedId == 10)) ||
        ((card1WithFixedId == 10) && (card2WithFixedId == 12))
    ) {
        if (((1..2).random() == 1) && (raiseCounter == 0)) {
            return true
        }
        if (lowRaiseSize) {
            return true
        }
        if (normalRaiseSize) {
            if (raiseCounterTemp < 2) {
                return if (((1..2).random() == 1) && (raiseCounter > 0)) {
                    callAll = true
                    false
                } else {
                    true
                }
            } else
                if ((highestPairOrNo(deck)) && (raiseCounterTemp < 3)) {
                    return if (raiseCounter > 0) {
                        callAll = true
                        false
                    } else {
                        false
                    }
                }
        } else
            if ((highestPairOrNo(deck)) && (notNormalRaiseSize)) {
                if (raiseCounter > 0) {
                    callAll = true
                    return false
                }
            } else
                if (wrongRaiseSize) {
                    return false
                }
    }

    /**
     * check for flush draw
     */


    if (flushableWithFiveCards(card1, card2, deck)
    ) {
        if (((1..2).random() == 1) && (raiseCounter == 0)) {
            return true
        }
        if (lowRaiseSize) {
            return true
        }
        if (!((deck[0].cardSuit == deck[1].cardSuit) && (deck[0].cardSuit == deck[2].cardSuit))) {
            if (normalRaiseSize) {
                if (raiseCounterTemp < 2) {
                    return true
                } else
                    if (raiseCounterTemp < 3) {
                        if (((1..3).random() == 1) && (raiseCounter > 0)) {
                            callAll = true
                            return false
                        }
                    } else
                        if (raiseCounterTemp in -10..4) {
                            if (((card1WithFixedId > 9) || (card2WithFixedId > 9)) && (raiseCounter > 0)) {
                                callAll = true
                                return false
                            }
                        }
            } else
                if (notNormalRaiseSize) {

                    if (((card1WithFixedId > 10) || (card2WithFixedId > 10))
                        && (raiseCounter > 0) && (raiseCounterTemp < 2)
                    ) {
                        callAll = true
                        return false
                    } else {
                        return false
                    }
                } else
                    if (wrongRaiseSize) {
                        return false
                    }
        } else
            if (normalRaiseSize) {
                if (raiseCounterTemp < 2) {
                    if ((1..3).random() == 1) {
                        return true
                    } else {
                        if (raiseCounter > 0) {
                            callAll = true
                            return false
                        }
                    }
                } else
                    if (raiseCounterTemp < 3) {
                        if (((card1WithFixedId > 10) || (card2WithFixedId > 10)) && (raiseCounter > 0)) {
                            callAll = true
                            return false
                        } else {
                            return false
                        }
                    }
            } else
                if (notNormalRaiseSize) {
                    if (((card1WithFixedId > 10) || (card2WithFixedId > 10))
                        && (raiseCounter > 0) && (raiseCounterTemp < 2)
                    ) {
                        callAll = true
                        return false
                    } else {
                        return false
                    }
                } else
                    if (wrongRaiseSize) {
                        return false
                    }
    }

    /**
     * check for street draw
     */


    if (streetableWithFiveCards(card1, card2, deck)
    ) {
        if (lowRaiseSize) {
            return true
        }
        if (((1..3).random() == 1) && (raiseCounter == 0)) {
            return true
        }
        if (normalRaiseSize) {
            if (raiseCounterTemp < 2) {
                if ((1..3).random() == 1) {
                    return true
                } else {
                    if (raiseCounter > 0) {
                        callAll = true
                        return false
                    }
                }
            } else
                if (raiseCounterTemp < 3) {
                    if (raiseCounter > 0) {
                        callAll = true
                        return false
                    } else {
                        return false
                    }
                }
        } else
            if (notNormalRaiseSize) {
                if (((card1WithFixedId > 10) || (card2WithFixedId > 10))
                    && (raiseCounter > 0) && (raiseCounterTemp < 2)
                ) {
                    callAll = true
                    return false
                } else {
                    return false
                }
            } else
                if (wrongRaiseSize) {
                    return false
                }
    }


    if ((combo in 1..312677) && (raiseCounterTemp < 2) && (normalRaiseSize) && (callDoneCounter == 0)) {
        if (((1..2).random() == 1) && (lowRaiseSize)) {
            return true
        }
        if (((1..2).random() == 1) && (raiseCounter > 0)) {
            callAll = true
            return false
        } else
            if (((1..4).random() == 1) && (raiseCounter == 0)) {
                return true
            }
    }
    /**
     * before finish in raise size is Very low Call any hand
     */

    if ((lowRaiseSize) && (raiseCounter > 0) && (callDoneCounter < 3)) {
        callAll = true
        return false
    }
    return false
}


fun playableFlopCall(
    bool: Boolean
): Boolean {
    if (bool) {
        return false
    }
    if (callAll) {
        callAll = false
        return true
    }
    return false
}


fun playableTurnRaise(
    card1: Cards,
    card2: Cards,
    boardCard1: Cards,
    boardCard2: Cards,
    boardCard3: Cards,
    boardCard4: Cards,
    pot: Int,
    callDoneCounter: Int = 0,
    raiseCounter: Int = 0,
    raiseSize: Int = 0,
    bool: Boolean
): Boolean {

    if (bool) {
        return false
    }

    if (!playing) {
        ((1..6).random() == 1)
        return false
    }

    if (raiseCounter > 6) {
        callAll = true
        return false
    }

    var raiseCounterTemp = raiseCounter

    if (callDoneCounter == 2) {
        raiseCounterTemp++
    }

    if (callDoneCounter == 4) {
        raiseCounterTemp++
        raiseCounterTemp++
    }


    val fakeCards5: Cards = Cards(-18, "Fake2", "NoSuit2")

    val card1WithFixedId = if (card1.id < 13) {
        card1.id
    } else
        ((card1.id) % 13)

    val card2WithFixedId = if (card2.id < 13) {
        card2.id
    } else
        ((card2.id) % 13)

    val deck = creatDeck(
        boardCard1, boardCard2, boardCard3,
        boardCard4, fakeCards5
    )
    val combo = combination(card1, card2, deck)

    val lowRaiseSize = lowRaise(pot, raiseSize, raiseCounter, callDoneCounter)
    if (lowRaiseSize) {
        raiseCounterTemp--
    }

    var normalRaiseSize: Boolean = normalRaise(pot, raiseSize, raiseCounter, callDoneCounter)
    var notNormalRaiseSize: Boolean = notNormalRaise(pot, raiseSize, raiseCounter, callDoneCounter)
    var wrongRaiseSize: Boolean = wrongRaise(pot, raiseSize, raiseCounter, callDoneCounter)

    if (agroPlayer()) {
        raiseCounterTemp--
    }
    if (veryAgroPlayer()) {
        raiseCounterTemp--
    }
    if (notNormalRaisePlayer()) {
        if (notNormalRaiseSize) {
            notNormalRaiseSize = false
            normalRaiseSize = true
        }
    }
    if (wrongRaisePlayer()) {
        if (wrongRaiseSize) {
            wrongRaiseSize = false
            notNormalRaiseSize = true
        }
    }
    /**
     * only raise
     */

    if (combo == 1 || combo == 2 || combo == 3 || combo == 4) {
        return if (((1..4).random() == 1) && (raiseCounter > 0)) {
            callAll = true
            false
        } else {
            true
        }
    }
    /**
     * Mostly Raise
     */
    if ((combo in 5..6) || (combo in 18..68) ||
        (combo in 270..274) || (combo in 283..286) ||
        (combo in 293..5000)
    ) {
        if (raiseCounter == 0) {
            return true
        }
        if (lowRaiseSize) {
            return true
        }
        if (normalRaiseSize) {
            if (raiseCounterTemp < 3) {
                return if (((1..5).random() == 1) && (raiseCounter > 0)) {
                    callAll = true
                    false
                } else {
                    true
                }
            } else
                if (raiseCounterTemp < 4) {
                    if (((1..4).random() == 1) && (raiseCounter > 0)) {
                        callAll = true
                        return false
                    } else {
                        return true
                    }
                } else
                    if (raiseCounterTemp < 5) {
                        if (((combo == 5) || (combo == 6)) || (combo in 18..35) ||
                            (combo in 270..271) || (combo in 283..285) ||
                            ((highestPairOrNo(deck)) && (combo in 293..5000))
                        ) {
                            if (((1..3).random() == 1) && (raiseCounter > 0)) {
                                callAll = true
                                return false
                            } else {
                                return true
                            }
                        }
                    } else
                    /**
                     * stegh arden ankax raisi qanakic
                     */
                        if (raiseCounterTemp > 3) {
                            if ((combo == 5) || (combo in 18..25) ||
                                (combo in 270..271) || (combo in 283..284) ||
                                ((highestPairOrNo(deck)) && (combo in 293..3000))
                            ) {
                                if (((1..2).random() == 1) && (raiseCounter > 0)) {
                                    callAll = true
                                    return false
                                } else {
                                    return true
                                }
                            }
                        }
        } else
            if (notNormalRaiseSize) {
                if ((combo == 5) || (combo == 6) || (combo in 18..35) ||
                    (combo in 270..271) || (combo in 283..285) ||
                    ((highestPairOrNo(deck)) && (combo in 293..5000))
                ) {
                    if (((1..3).random() == 1) && (raiseCounter > 0)) {
                        callAll = true
                        return false
                    } else {
                        return true
                    }
                }
            } else
                if (wrongRaiseSize) {
                    if ((combo == 5) || (combo in 18..25) ||
                        (combo in 270..271) || (combo in 283..284) ||
                        ((highestPairOrNo(deck)) && (combo in 293..3000))
                    ) {
                        if (((1..3).random() == 1) && (raiseCounter > 0)) {
                            callAll = true
                            return false
                        } else {
                            return true
                        }
                    }
                }
    }


    /**
     * Mostly Call sometime Raise
     */

    if ((combo in 7..8) || (combo in 69..268) ||
        (combo in 275..282) || (combo in 287..292) ||
        (combo in 5001..10300) || (combo in 12184..15184)
    ) {
        if (raiseCounter == 0) {
            return true
        }
        if (lowRaiseSize) {
            return true
        }
        if (normalRaiseSize) {
            if (raiseCounterTemp < 3) {
                return true
            } else
                if (raiseCounterTemp < 4) {
                    if ((combo in 6..7) || (combo in 69..168) ||
                        (combo in 275..280) || (combo in 287..290) ||
                        ((highestPairOrNo(deck)) && (combo in 5001..8001)) ||
                        ((highestPairOrNo(deck)) && (combo in 12184..13584))
                    ) {
                        if (((1..3).random() == 1) && (raiseCounter > 0)) {
                            callAll = true
                            return false
                        } else {
                            return true
                        }
                    }
                } else
                    if ((raiseCounterTemp < 5) && ((combo in 6..7) || (combo in 69..168) ||
                                (combo in 275..280) || (combo in 287..290) ||
                                ((highestPairOrNo(deck)) && (combo in 5001..8001)) ||
                                ((highestPairOrNo(deck)) && (combo in 12184..13584))
                                )
                    ) {
                        if (raiseCounter > 0) {
                            callAll = true
                            return false
                        }
                    } else
                        if (((1..4).random() == 1) && (raiseCounter > 0)) {
                            callAll = true
                            return false
                        } else {
                            return false
                        }
        } else
            if (notNormalRaiseSize) {
                if ((combo in 6..7) || (combo in 69..168) ||
                    (combo in 275..280) || (combo in 287..290) ||
                    ((highestPairOrNo(deck)) && (combo in 5001..8001)) ||
                    ((highestPairOrNo(deck)) && (combo in 12184..13584))
                ) {
                    if (((1..2).random() == 1) && (raiseCounter > 0)) {
                        callAll = true
                        return false
                    } else if (((1..2).random() == 1) && (raiseCounter > 0)) {
                        callAll = true
                        return false
                    } else {
                        return false
                    }
                }
            } else

                if (wrongRaiseSize) {
                    if ((raiseCounter > 0) && ((combo in 6..7) || (combo in 69..168) ||
                                (combo in 275..280) || (combo in 287..290) ||
                                ((highestPairOrNo(deck)) && (combo in 5001..8001)) ||
                                ((highestPairOrNo(deck)) && (combo in 12184..13584))
                                )
                    ) {
                        callAll = true
                        return false
                    } else {
                        return false
                    }
                }
    }


    /**
     * aranc patrasti dzeri
     * havanakan kombinacyaner unenalov call
     * STREET AND FLEESH DRAW
     */


    if ((flushableWithFiveCards(card1, card2, deck)) &&
        (streetableWithSixCards(card1, card2, deck))
    ) {
        if (raiseCounter == 0) {
            return true
        }
        if (lowRaiseSize) {
            return true
        }
        if ((!((deck[0].cardSuit == deck[1].cardSuit) && (deck[0].cardSuit == deck[2].cardSuit))) ||
            (!((deck[0].cardSuit == deck[1].cardSuit) && (deck[0].cardSuit == deck[3].cardSuit))) ||
            (!((deck[1].cardSuit == deck[2].cardSuit) && (deck[1].cardSuit == deck[3].cardSuit)))
        ) {
            if (normalRaiseSize) {
                if (raiseCounterTemp < 3) {
                    return true
                } else
                    if (raiseCounterTemp < 4) {
                        if (((1..3).random() == 1) && (raiseCounter > 0)) {
                            callAll = true
                            return false
                        } else {
                            return true
                        }
                    } else
                        if (raiseCounterTemp > 0) {
                            if (raiseCounter > 0) {
                                callAll = true
                                return false
                            }
                        }
            } else
                if (notNormalRaiseSize) {

                    if (((card1WithFixedId > 9) || (card2WithFixedId > 9)) && (raiseCounter > 0)) {
                        callAll = true
                        return false
                    } else {
                        return false
                    }
                } else
                    if (wrongRaiseSize) {

                        if (((card1WithFixedId > 10) || (card2WithFixedId > 10)) && (raiseCounter > 0)) {
                            callAll = true
                            return false
                        } else {
                            return false
                        }
                    }
        } else
            if (normalRaiseSize) {
                if (raiseCounterTemp < 3) {
                    return true
                } else
                    if (raiseCounterTemp < 4) {
                        if (((card1WithFixedId > 10) || (card2WithFixedId > 10)) && (raiseCounter > 0)) {
                            callAll = true
                            return false
                        } else {
                            return false
                        }
                    }
            } else
                if (notNormalRaiseSize) {
                    if (((card1WithFixedId > 10) || (card2WithFixedId > 10)) && (raiseCounter > 0)) {
                        callAll = true
                        return false
                    } else {
                        return false
                    }
                } else
                    if (wrongRaiseSize) {
                        return false
                    }
    }

    /**
     * Mostly Pass sometime Call rare Raise
     */


    if (((combo in 10301..12183) || (combo in 15185..23518) ||
                (combo in 23519..124831) || (combo in 1300..1520)) && (highestPairOrNo(deck))
    ) {
        if (((1..2).random() == 1) && (raiseCounter == 0)) {
            return true
        }
        if (lowRaiseSize) {
            return true
        }
        if (normalRaiseSize) {
            if (raiseCounterTemp < 3) {
                return if (((1..3).random() == 1) && (raiseCounter > 0)) {
                    callAll = true
                    false
                } else {
                    true
                }
            } else
                if ((highestPairOrNo(deck)) && (raiseCounterTemp < 3)) {
                    return if (((1..2).random() == 1) && (raiseCounter > 0)) {
                        callAll = true
                        false
                    } else {
                        false
                    }
                }
        } else
            if ((highestPairOrNo(deck)) && (notNormalRaiseSize)) {
                return if (((1..2).random() == 1) && (raiseCounter > 0)) {
                    callAll = true
                    false
                } else {
                    false
                }
            } else
                if (wrongRaiseSize) {
                    return false
                }
    }

    /**
     * check for flush draw
     */


    if (flushableWithFiveCards(card1, card2, deck)
    ) {
        if (((1..2).random() == 1) && (raiseCounter == 0)) {
            return true
        }
        if ((!((deck[0].cardSuit == deck[1].cardSuit) && (deck[0].cardSuit == deck[2].cardSuit))) ||
            (!((deck[0].cardSuit == deck[1].cardSuit) && (deck[0].cardSuit == deck[3].cardSuit))) ||
            (!((deck[1].cardSuit == deck[2].cardSuit) && (deck[1].cardSuit == deck[3].cardSuit)))
        ) {
            if (lowRaiseSize) {
                return true
            }
            if (normalRaiseSize) {
                if (raiseCounterTemp < 3) {
                    return true
                } else
                    if (raiseCounterTemp < 4) {
                        if (((1..3).random() == 1) && (raiseCounter > 0)) {
                            callAll = true
                            return false
                        }
                    } else
                        if (raiseCounterTemp in -10..5) {
                            if (((card1WithFixedId > 9) || (card2WithFixedId > 9)) && (raiseCounter > 0)) {
                                callAll = true
                                return false
                            }
                        }
            } else
                if (notNormalRaiseSize) {

                    if (((card1WithFixedId > 10) || (card2WithFixedId > 10))
                        && (raiseCounter > 0) && (raiseCounterTemp < 2)
                    ) {
                        callAll = true
                        return false
                    } else {
                        return false
                    }
                } else
                    if (wrongRaiseSize) {
                        return false
                    }
        } else
            if (normalRaiseSize) {
                if (raiseCounterTemp < 3) {
                    if (raiseCounter > 0) {
                        callAll = true
                        return false
                    }
                } else
                    if (raiseCounterTemp < 4) {
                        if (((card1WithFixedId > 10) || (card2WithFixedId > 10)) && (raiseCounter > 0)) {
                            callAll = true
                            return false
                        } else {
                            return false
                        }
                    }
            } else
                if (notNormalRaiseSize) {
                    if (((card1WithFixedId > 10) || (card2WithFixedId > 10))
                        && (raiseCounter > 0) && (raiseCounterTemp < 2)
                    ) {
                        callAll = true
                        return false
                    } else {
                        return false
                    }
                } else
                    if (wrongRaiseSize) {
                        return false
                    }
    }

    /**
     * check for street draw
     */


    if (streetableWithFiveCards(card1, card2, deck)
    ) {
        if (((1..3).random() == 1) && (raiseCounter == 0)) {
            return true
        }
        if ((lowRaiseSize) && (1..2).random() == 1) {
            return true
        }
        if (normalRaiseSize) {
            if (raiseCounterTemp < 3) {
                if ((1..3).random() == 1) {
                    return true
                } else {
                    if (raiseCounter > 0) {
                        callAll = true
                        return false
                    }
                }
            } else
                if (raiseCounterTemp < 4) {
                    if (((1..3).random() == 1) && (raiseCounter > 0)) {
                        callAll = true
                        return false
                    } else {
                        return false
                    }
                }
        } else {
            return false
        }
    }


    if ((combo in 1..152677) && (raiseCounterTemp < 2) && (normalRaiseSize) && (callDoneCounter == 0)) {
        if ((lowRaiseSize) && (1..3).random() == 1) {
            return true
        }
        if (((1..2).random() == 1) && (raiseCounter > 0)) {
            callAll = true
            return false
        }
    }
    /**
     * before finish in raise size is Very low Call any hand
     */

    if ((lowRaiseSize) && (raiseCounter > 0) && (callDoneCounter < 3)) {
        callAll = true
        return false
    }
    return false
}

fun playableTurnCall(

    bool: Boolean
): Boolean {
    if (bool) {
        return false
    }

    if (callAll) {
        callAll = false
        return true
    }

    return false
}

fun playableRiverRaise(
    card1: Cards,
    card2: Cards,
    boardCard1: Cards,
    boardCard2: Cards,
    boardCard3: Cards,
    boardard4: Cards,
    boardpCard5: Cards,
    pot: Int,
    callDoneCounter: Int = 0,
    raiseCounter: Int = 0,
    raiseSize: Int = 0,
    bool: Boolean
): Boolean {

    if (bool) {
        return false
    }

    if (!playing) {
        ((1..6).random() == 1)
        return false
    }

    if (raiseCounter > 6) {
        callAll = true
        return false
    }

    var raiseCounterTemp = raiseCounter

    if (callDoneCounter == 2) {
        raiseCounterTemp++
    }

    if (callDoneCounter == 4) {
        raiseCounterTemp++
        raiseCounterTemp++
    }


    val deck = creatDeck(
        boardCard1, boardCard2, boardCard3,
        boardard4, boardpCard5
    )
    val combo = combination(card1, card2, deck)

    val lowRaiseSize = lowRaise(pot, raiseSize, raiseCounter, callDoneCounter)
    if (lowRaiseSize) {
        raiseCounterTemp--
    }
    var normalRaiseSize: Boolean = normalRaise(pot, raiseSize, raiseCounter, callDoneCounter)
    var notNormalRaiseSize: Boolean = notNormalRaise(pot, raiseSize, raiseCounter, callDoneCounter)
    var wrongRaiseSize: Boolean = wrongRaise(pot, raiseSize, raiseCounter, callDoneCounter)

    if (agroPlayer()) {
        raiseCounterTemp--
    }
    if (veryAgroPlayer()) {
        raiseCounterTemp--
    }
    if (notNormalRaisePlayer()) {
        if (notNormalRaiseSize) {
            notNormalRaiseSize = false
            normalRaiseSize = true
        }
    }
    if (wrongRaisePlayer()) {
        if (wrongRaiseSize) {
            wrongRaiseSize = false
            notNormalRaiseSize = true
        }
    }
    /**
     * only raise
     */

    if (combo == 1 || combo == 2 || combo == 3 || combo == 4) {
        return true
    }
    /**
     * Mostly Raise
     */
    if ((combo in 5..6) || (combo in 18..68) ||
        (combo in 270..274) || (combo in 283..286) ||
        (combo in 293..5000)
    ) {
        if (raiseCounter == 0) {
            return true
        }
        if (lowRaiseSize) {
            return true
        }
        if (normalRaiseSize) {
            if (raiseCounterTemp < 3) {
                return true
            } else
                if (raiseCounterTemp < 4) {
                    if (((combo == 5) || (combo == 6)) || (combo in 18..35) ||
                        (combo in 270..271) || (combo in 283..285) ||
                        ((highestPairOrNo(deck)) && (combo in 293..5000))
                    ) {
                        if (((1..3).random() == 1) && (raiseCounter > 0)) {
                            callAll = true
                            return false
                        } else {
                            return true
                        }
                    }
                } else
                /**
                 * stegh arden ankax raisi qanakic
                 */
                    if (raiseCounterTemp > 3) {
                        if ((combo == 5) || (combo in 18..25) ||
                            (combo in 270..271) || (combo in 283..284) ||
                            ((highestPairOrNo(deck)) && (combo in 293..3000))
                        ) {
                            return true
                        }
                    } else {
                        if (raiseCounter > 0) {
                            callAll = true
                            return false
                        }
                    }
        } else
            if (notNormalRaiseSize) {
                if ((combo == 5) || (combo == 6) || (combo in 18..35) ||
                    (combo in 270..271) || (combo in 283..285) ||
                    ((highestPairOrNo(deck)) && (combo in 293..5000))
                ) {
                    return true
                } else {
                    if (raiseCounter > 0) {
                        callAll = true
                        return false
                    }
                }
            } else
                if (wrongRaiseSize) {
                    if ((combo == 5) || (combo in 18..25) ||
                        (combo in 270..271) || (combo in 283..284) ||
                        ((highestPairOrNo(deck)) && (combo in 293..3000))
                    ) {
                        return true
                    } else {
                        if (raiseCounter > 0) {
                            callAll = true
                            return false
                        }
                    }
                }
    }


    /**
     * Mostly Call sometime Raise
     */

    if ((combo in 7..8) || (combo in 69..268) ||
        (combo in 275..282) || (combo in 287..292) ||
        (combo in 5001..10300) || (combo in 12184..15184)
    ) {
        if (raiseCounter == 0) {
            return true
        }
        if (lowRaiseSize) {
            return true
        }
        if (normalRaiseSize) {
            if (raiseCounterTemp < 3) {
                return true
            } else
                if (raiseCounterTemp < 4) {
                    if ((combo in 6..7) || (combo in 69..168) ||
                        (combo in 275..280) || (combo in 287..290) ||
                        ((highestPairOrNo(deck)) && (combo in 5001..8001)) ||
                        ((highestPairOrNo(deck)) && (combo in 12184..13584))
                    ) {
                        if (((1..3).random() == 1) && (raiseCounter > 0)) {
                            callAll = true
                            return false
                        } else {
                            return true
                        }
                    }
                } else
                    if ((raiseCounterTemp < 5) && ((combo in 6..7) || (combo in 69..168) ||
                                (combo in 275..280) || (combo in 287..290) ||
                                ((highestPairOrNo(deck)) && (combo in 5001..8001)) ||
                                ((highestPairOrNo(deck)) && (combo in 12184..13584))
                                )
                    ) {
                        if (raiseCounter > 0) {
                            callAll = true
                            return false
                        }
                    } else {
                        callAll = true
                        return false
                    }
        } else
            if (notNormalRaiseSize) {
                if ((combo in 6..7) || (combo in 69..168) ||
                    (combo in 275..280) || (combo in 287..290) ||
                    ((highestPairOrNo(deck)) && (combo in 5001..8001)) ||
                    ((highestPairOrNo(deck)) && (combo in 12184..13584))
                ) {
                    if (((1..2).random() == 1) && (raiseCounter > 0)) {
                        callAll = true
                        return false
                    } else {
                        return true
                    }
                }
            } else

                if (wrongRaiseSize) {
                    if ((raiseCounter > 0) && ((combo in 6..7) || (combo in 69..168) ||
                                (combo in 275..280) || (combo in 287..290) ||
                                ((highestPairOrNo(deck)) && (combo in 5001..8001)) ||
                                ((highestPairOrNo(deck)) && (combo in 12184..13584))
                                )
                    ) {
                        callAll = true
                        return false
                    } else {
                        if (((1..2).random() == 1) && (raiseCounter > 0)) {
                            callAll = true
                            return false
                        } else {
                            return false
                        }
                    }
                }
    }

    /**
     * Mostly Pass sometime Call rare Raise
     */

    if (((combo in 10301..12183) || (combo in 15185..23518) ||
                (combo in 23519..124831) || (combo in 1300..1520)) && (highestPairOrNo(deck))
    ) {
        if (raiseCounter == 0) {
            return true
        }
        if (lowRaiseSize) {
            return true
        }
        if (normalRaiseSize) {
            if (raiseCounterTemp < 3) {
                return if (((1..2).random() == 1) && (raiseCounter > 0)) {
                    callAll = true
                    false
                } else {
                    true
                }
            } else
                if ((highestPairOrNo(deck)) && (raiseCounterTemp < 4)) {
                    if (raiseCounter > 0) {
                        callAll = true
                        return false
                    }
                }
        } else
            if ((highestPairOrNo(deck)) && (notNormalRaiseSize)) {
                if (raiseCounter > 0) {
                    callAll = true
                    return false
                }
            } else
                if ((highestPairOrNo(deck)) && (wrongRaiseSize)) {
                    return if (((1..2).random() == 1) && (raiseCounter > 0)) {
                        callAll = true
                        false
                    } else {
                        false
                    }
                }
    }
    if ((combo in 1..132677) && (raiseCounterTemp < 2) && (normalRaiseSize) && (callDoneCounter == 0)) {
        if ((1..2).random() == 1 && (lowRaiseSize)) {
            return true
        }
        if (((1..2).random() == 1) && (raiseCounter > 0)) {
            callAll = true
            return false
        }
    }
    /**
     * before finish in raise size is Very low Call any hand
     */

    if ((lowRaiseSize) && (raiseCounter > 0) && (callDoneCounter < 3)) {
        callAll = true
        return false
    }
    return false
}

fun playableRiverCall(
    bool: Boolean
): Boolean {
    if (bool) {
        return false
    }

    if (callAll) {
        callAll = false
        return true
    }
    return false
}


fun raise(player: Player, raiseSize: Int): Int {

//    if(raiseSize>player.chips){
//
//    }

    player.chips = player.chips - raiseSize


    return raiseSize

}

fun check() {
    //toest check
}


fun fold() {
    //toest fold
}

fun notFolded(
    chipsList: List<Int>,
    playersTemp: List<Player>,
    inGameTemp: List<Boolean>,
    potTemp: Int
): List<Int> {

    val chipsListTemp = chipsList.toMutableList()
    for (i in inGameTemp.indices) {
        if (inGameTemp[i]) {
            playersTemp[i].chips += potTemp
        }
    }
    for (i in chipsListTemp.indices) {
        chipsListTemp[i] = playersTemp[i].chips
    }
    return chipsListTemp
}

fun lastRaiseCheck(boolRaise: MutableList<Boolean>, indexOfRaiser: Int): MutableList<Boolean> {
    for (i in boolRaise.indices) {
        boolRaise[i] = false
    }
    boolRaise[indexOfRaiser] = true
    return boolRaise
}

fun foldedAfterFlop(boolFolded: List<Boolean>): Int {
    var tempInt = 0
    for (i in boolFolded.indices) {
        if (boolFolded[i]) {
            tempInt++
        }
    }

    return (6 - tempInt)
}


fun getChips(player: List<Player>): List<Int> {
    val chips = mutableListOf<Int>()
    for (i in 0..5) {
        chips.add(player[i].chips)

    }

    return chips
}

fun setBlinds(roundNumber: Int) {
    if (roundNumber % 11 == 0) {
        bigBlind *= 2
        smallBlind *= 2
        //asel blind up
    }
}

fun setPositions(roundNumber: Int): List<Int> {
    var tempRound = roundNumber
    var indexList: MutableList<Int> = mutableListOf(0, 1, 2, 3, 4, 5)
    if (roundNumber > 5) {
        tempRound = roundNumber % 6
    }
    when (tempRound) {
        0 -> {
            indexList = mutableListOf(5, 0, 1, 2, 3, 4)
        }
        1 -> {
            indexList = mutableListOf(0, 1, 2, 3, 4, 5)
        }
        2 -> {
            indexList = mutableListOf(1, 2, 3, 4, 5, 0)
        }
        3 -> {
            indexList = mutableListOf(2, 3, 4, 5, 0, 1)
        }
        4 -> {
            indexList = mutableListOf(3, 4, 5, 0, 1, 2)
        }
        5 -> {
            indexList = mutableListOf(4, 5, 0, 1, 2, 3)
        }

    }

    return indexList
}

fun setPositionFlopAndAfter(roundNumber: Int): List<Int> {

    var tempRound = roundNumber
    var indexList: MutableList<Int> = mutableListOf(0, 1, 2, 3, 4, 5)
    if (roundNumber > 5) {
        tempRound = roundNumber % 6
    }

    when (tempRound) {
        0 -> {
            indexList = mutableListOf(3, 4, 5, 0, 1, 2)
        }
        1 -> {
            indexList = mutableListOf(4, 5, 0, 1, 2, 3)
        }
        2 -> {
            indexList = mutableListOf(5, 0, 1, 2, 3, 4)
        }
        3 -> {
            indexList = mutableListOf(0, 1, 2, 3, 4, 5)
        }
        4 -> {
            indexList = mutableListOf(1, 2, 3, 4, 5, 0)
        }
        5 -> {
            indexList = mutableListOf(2, 3, 4, 5, 0, 1)
        }
    }
    return indexList
}


fun playerChips(chips: List<Int>, player: List<Player>) {
    for (i in 0..5) {
        player[i].chips = chips[i]
    }
}


private fun creatDeck(
    boardCard1: Cards, boardCard2: Cards, boardCard3: Cards,
    boardard4: Cards, boardpCard5: Cards
): List<Cards> {
    return mutableListOf(
        boardCard1, boardCard2, boardCard3, boardard4, boardpCard5
    )
}


fun deckIdFix(deck: List<Cards>): List<Int> {

    var tempId: Int
    val idFixedList = mutableListOf<Int>()

    for (i in deck.indices) {
        if (deck[i].id > 12) {
            tempId = ((deck[i].id) % 13)
            idFixedList.add(tempId)
        } else {
            idFixedList.add(deck[i].id)
        }
    }
    return idFixedList
}

fun deckIdFixOfIdList(list: List<Int>): MutableList<Int> {

    var tempId: Int
    val idFixedList = mutableListOf<Int>()

    for (i in list.indices) {
        if (list[i] > 12) {
            tempId = ((list[i]) % 13)
            idFixedList.add(tempId)
        } else {
            idFixedList.add(list[i])
        }
    }
    return idFixedList
}

fun deckAndPlayerCardsIdFix(card1: Cards, card2: Cards, deck: List<Cards>): List<Int> {

    var tempId: Int
    val idFixedList = mutableListOf<Int>()

    if (card1.id > 12) {
        tempId = ((card1.id) % 13)
        idFixedList.add(tempId)
    } else {
        idFixedList.add(card1.id)
    }
    if (card2.id > 12) {
        tempId = ((card2.id) % 13)
        idFixedList.add(tempId)
    } else {
        idFixedList.add(card2.id)
    }

    for (i in deck.indices) {
        if (deck[i].id > 12) {
            tempId = ((deck[i].id) % 13)
            idFixedList.add(tempId)
        } else {
            idFixedList.add(deck[i].id)
        }
    }

    return idFixedList
}


fun <T, M> mapToKeyList(map: Map<T, M>): MutableList<T> {
    val keys = mutableListOf<T>()

    for (i in map.values.indices) {
        keys.add(map.keys.elementAt(i))    //nuynna tarber dzevov add arac
    }
    return keys
}

fun <T, M> mapToValuesList(map: Map<T, M>): MutableList<M> {
    val values = mutableListOf<M>()

    for (i in map.values.indices) {
        values.add(map.values.elementAt(i))
    }
    return values
}

fun normalRaisePreFlop(pot: Int, raise: Int, call: Int): Boolean {
    val tempPot = pot - call * raise
    var persent = tempPot - raise
    if (persent == 0) {
        ++persent
    }
    persent = raise / persent
    return persent <= 4
}

fun notNormalRaisePreFlop(pot: Int, raise: Int, call: Int): Boolean {
    val tempPot = pot - call * raise
    var persent = tempPot - raise
    persent = raise / persent

    if (persent in 4..8) {
        return true
    }
    return false
}

fun wrongRaisePreFlop(pot: Int, raise: Int, call: Int): Boolean {
    val tempPot = pot - call * raise
    var persent = tempPot - raise
    if (persent == 0) {
        ++persent
    }
    persent = raise / persent
    if (persent > 8) {
        return true
    } else
        return false
}

fun lowRaise(pot: Int, raise: Int, raisCounter: Int, call: Int): Boolean {
    val tempPot = pot - call * raise
    var persent = tempPot - raise
    if (persent == 0) {
        ++persent
    }

    var persDouble = persent.toDouble()

    persDouble = raise / persDouble
    if ((persDouble > 0) && (persDouble < 0.11)) {
        return true
    }
    return false

}

fun normalRaise(pot: Int, raise: Int, raisCounter: Int, call: Int): Boolean {
    val tempPot = pot - call * raise
    var persent = tempPot - raise
    if (persent == 0) {
        ++persent
    }
    var persDouble = persent.toDouble()

    persDouble = raise / persDouble
    if ((persDouble > 0.11) && (persDouble < 1.5)) {
        return true
    }
    return false

}

fun notNormalRaise(pot: Int, raise: Int, raisCounter: Int, call: Int): Boolean {
    val tempPot = pot - call * raise
    var persent = tempPot - raise
    if (persent == 0) {
        ++persent
    }
    var persDouble = persent.toDouble()

    persDouble = raise / persDouble
    if ((persDouble >= 1.5) && (persDouble <= 2.5)) {
        return true
    }
    return false

}

fun wrongRaise(pot: Int, raise: Int, raisCounter: Int, call: Int): Boolean {
    val tempPot = pot - call * raise
    var persent = tempPot - raise
    if (persent == 0) {
        ++persent
    }
    var persDouble = persent.toDouble()

    persDouble = raise / persDouble

    if (persDouble > 2.5) {
        return true
    } else
        return false
}


fun checkable(bool: Boolean): Boolean {

    if (bool) {
        return true
    }
    return false
}


/**
 * stuguma ete flushes spasum 4 cartov true else false
 */

fun flushableWithFiveCards(playerCard1: Cards, playerCard2: Cards, deck: List<Cards>): Boolean {

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

    val timesValues: MutableList<Int> = mapToValuesList(mapOfSuitsTimes)

    for (i in timesValues.indices) {

        if (timesValues[i] > 3) {

            return true
        } else
            if (i == timesValues.lastIndex) {
                return false
            }
    }
    return false
}

/**
 * stuguma ete street spasum 4 cartov true else false
 *
 */

fun streetableWithFiveCards(playerCar1: Cards, playerCard2: Cards, deck: List<Cards>): Boolean {

    var listOfFixedIds =
        deckAndPlayerCardsIdFix(playerCar1, playerCard2, deck).distinct().sorted().toMutableList()

    listOfFixedIds.removeFirst()
    listOfFixedIds.removeFirst()

    if (listOfFixedIds.contains(12)) {
        listOfFixedIds = (mutableListOf(-1) + listOfFixedIds).toMutableList()
    }

    if (listOfFixedIds.size < 4) {
        return false
    }

    var temp = 0
    var temp2 = 0
    for (i in 0 until (listOfFixedIds.size - 1)) {
        if (listOfFixedIds[i - temp2] != (listOfFixedIds[i + 1 - temp2] - 1)) {
            for (k in 0..temp) {
                temp2++
                listOfFixedIds.remove(listOfFixedIds[0])
            }
            if (listOfFixedIds.size < 4) {
                return false
            }
            temp = 0
        } else {
            temp++
            if (temp > 2) {
                return true
            }
        }
    }

    if (listOfFixedIds.size > 3) {
        return true
    }
    return false
}

/**
 * stuguma ete street spasum 4 cartov true else false
 *
 */

fun streetableWithSixCards(playerCar1: Cards, playerCard2: Cards, deck: List<Cards>): Boolean {

    var listOfFixedIds =
        deckAndPlayerCardsIdFix(playerCar1, playerCard2, deck).distinct().sorted().toMutableList()

    listOfFixedIds.removeFirst()

    if (listOfFixedIds.contains(12)) {
        listOfFixedIds = (mutableListOf(-1) + listOfFixedIds).toMutableList()
    }

    if (listOfFixedIds.size < 4) {
        return false
    }

    var temp = 0
    var temp2 = 0
    for (i in 0 until (listOfFixedIds.size - 1)) {
        if (listOfFixedIds[i - temp2] != (listOfFixedIds[i + 1 - temp2] - 1)) {
            for (k in 0..temp) {
                temp2++
                listOfFixedIds.remove(listOfFixedIds[0])
            }
            if (listOfFixedIds.size < 4) {
                return false
            }
            temp = 0
        } else {
            temp++
            if (temp > 2) {
                return true
            }
        }
    }
//vrodi avelord stuguma baic ok vrodi es foric durs galuc sagh meka depqerum kam true kam false kta
    if (listOfFixedIds.size > 3) {
        return true
    }
    return false
}


fun highestPairOrNo(deck: List<Cards>): Boolean {
//
//    var highest = 0
//
//    val tempHigh = deckIdFix(deck).maxOrNull()

    var deckTemp: List<Int> = deckIdFix(deck)

//    for (i in deckTemp.indices) {
//        if (deckTemp[i] == tempHigh) {
//            highest++
//        }
//    }
    deckTemp = deckTemp.distinct()

    if (deckTemp.size == 5) {
        return true
    }

//    if (highest < 2) {
//        return true
//    }

    return false
}

//
fun allChecked(inGame: MutableList<Boolean>, checked: MutableList<Boolean>): Boolean {
    for (i in inGame.indices) {
        if (inGame[i] && (!checked[i])) {
            return false
        }
    }
    return true
}

private fun agroPlayer(): Boolean {
    var tempRound = round
    if (tempRound >= 15) {
        tempRound %= 15
    }
    if (tempRound == 0) {
        playerAgression = 0
    }
    return if (tempRound < 5) {
        false
    } else
        (playerAgression > 0)
}

private fun veryAgroPlayer(): Boolean {
    var tempRound = round
    if (tempRound >= 15) {
        tempRound %= 15
    }
    if (tempRound == 0) {
        playerAgression = 0
    }
    return if (tempRound < 5) {
        false
    } else
        playerAgression > (25 - tempRound)
}

private fun notNormalRaisePlayer(): Boolean {
    var tempRound = round
    if (tempRound >= 15) {
        tempRound %= 15
    }
    if (tempRound == 0) {
        playerNotNormalRaise = 0
    }
    return if (tempRound < 5) {
        false
    } else
        playerNotNormalRaise > 3
}

private fun wrongRaisePlayer(): Boolean {

    var tempRound = round
    if (tempRound >= 20) {
        tempRound %= 20
    }
    if (tempRound == 0) {
        playerWrongRaise = 0
    }
    return if (tempRound < 5) {
        false
    } else
        playerWrongRaise > 3

}
