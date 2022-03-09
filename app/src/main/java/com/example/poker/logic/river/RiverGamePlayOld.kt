//package com.example.poker.logic.river
//
//import androidx.lifecycle.MutableLiveData
//import com.example.poker.levon.BaseState
//import com.example.poker.logic.*
//import com.example.poker.logic.preflop.preFlopGamePlay
//import com.example.poker.logic.turn.Cards
//import com.example.poker.methods.Player
//
//
//fun riverGamePlay(
//    player: List<Player>,
//    deck: List<Cards>,
//    inGame: MutableList<Boolean>,
//    lastRaiseTurn: MutableList<Boolean>,
//    potTurn: Int,
//    turnLiveData: MutableLiveData<BaseState>
//) {
//
//    val indexes: List<Int> = setPositionFlopAndAfter(round)
//    var folded = foldedAfterFlop(inGame) + 1
//    var lastRaise = lastRaiseTurn
//    var raiseDone = 0
//    var pot = potTurn
//    var raiseDoneCounter = 0
//    var callDoneCounter = 0
//    val tempRaise = mutableListOf(0, 0, 0, 0, 0, 0)
//
//
//
//    do {
//        if (inGame[4] && ((tempRaise[4] != raiseDone) || (raiseDoneCounter == 0))) {
//
//            if (raiseDoneCounter == 0) {
//                if (lastRaise[4]) {
//                    if ((1..8).random() == 4) {
//                        check()
//                    } else {
//                        raiseDone = raise(player[4], (pot * ((56..87).random()) / 100))
//                        lastRaise = lastRaiseCheck(lastRaise, 4)
//                        pot += raiseDone
//                        raiseDoneCounter += 1
//                        tempRaise[4] = raiseDone
//                    }
//                } else
//                    if (playableRiverRaise(
//                            player[4].cart1,
//                            player[4].cart2,
//                            deck[0],
//                            deck[1],
//                            deck[2],
//                            deck[3],
//                            deck[4],
//                            pot,
//                            callDoneCounter,
//                            raiseDoneCounter,
//                            (raiseDone - tempRaise[4]),
//                            false
//                        ) && ((1..8).random() == 1)
//                    ) {
//                        raiseDone = raise(player[4], (pot * ((31..63).random()) / 100))
//                        lastRaise = lastRaiseCheck(lastRaise, 4)
//                        pot += raiseDone
//                        tempRaise[4] = raiseDone
//                        raiseDoneCounter += 1
//                    } else {
//                        check()
//                    }
//            } else
//                if (playableRiverRaise(
//                        player[4].cart1,
//                        player[4].cart2,
//                        deck[0],
//                        deck[1],
//                        deck[2],
//                        deck[3],
//                        deck[4],
//                        pot,
//                        callDoneCounter,
//                        raiseDoneCounter,
//                        (raiseDone - tempRaise[4]),
//                        (tempRaise[4] == raiseDone)
//                    )
//                ) {
//                    raiseDone = raise(player[4], (raiseDone * 8 / 3 - tempRaise[4]))
//                    lastRaise = lastRaiseCheck(lastRaise, 4)
//                    pot += raiseDone
//                    raiseDone += tempRaise[4]
//                    tempRaise[4] = raiseDone
//                    raiseDoneCounter += 1
//                } else
//                    if (playableRiverCall(
//                            (tempRaise[4] == raiseDone)
//                        )
//                    ) {
//                        callDoneCounter += 1
//                        raise(player[4], (raiseDone - tempRaise[4]))
//                        pot += (raiseDone - tempRaise[4])
//                        tempRaise[4] = raiseDone
//                    } else {
//                        fold()
//                        folded++
//                        inGame[4] = false
//                        if (folded == 5) {
//                            notFolded(player, inGame, pot)
//                            preFlopGamePlay(player, turnLiveData)
//                        }
//                    }
//        }
//
//
//        if (inGame[5] && ((tempRaise[5] != raiseDone) || (raiseDoneCounter == 0))) {
//
//            if (raiseDoneCounter == 0) {
//                if (lastRaise[5]) {
//                    if ((1..8).random() == 4) {
//                        check()
//                    } else {
//                        raiseDone = raise(player[5], (pot * ((56..87).random()) / 100))
//                        lastRaise = lastRaiseCheck(lastRaise, 5)
//                        pot += raiseDone
//                        raiseDoneCounter += 1
//                        tempRaise[5] = raiseDone
//                    }
//                } else
//                    if (playableRiverRaise(
//                            player[5].cart1,
//                            player[5].cart2,
//                            deck[0],
//                            deck[1],
//                            deck[2],
//                            deck[3],
//                            deck[4],
//                            pot,
//                            callDoneCounter,
//                            raiseDoneCounter,
//                            (raiseDone - tempRaise[5]),
//                            false
//                        ) && ((1..8).random() == 1)
//                    ) {
//                        raiseDone = raise(player[5], (pot * ((31..63).random()) / 100))
//                        lastRaise = lastRaiseCheck(lastRaise, 5)
//                        pot += raiseDone
//                        tempRaise[5] = raiseDone
//                        raiseDoneCounter += 1
//                    } else {
//                        check()
//                    }
//            } else
//                if (playableRiverRaise(
//                        player[5].cart1,
//                        player[5].cart2,
//                        deck[0],
//                        deck[1],
//                        deck[2],
//                        deck[3],
//                        deck[4],
//                        pot,
//                        callDoneCounter,
//                        raiseDoneCounter,
//                        (raiseDone - tempRaise[5]),
//                        (tempRaise[5] == raiseDone)
//                    )
//                ) {
//                    raiseDone = raise(player[5], (raiseDone * 8 / 3 - tempRaise[5]))
//                    lastRaise = lastRaiseCheck(lastRaise, 5)
//                    pot += raiseDone
//                    raiseDone += tempRaise[5]
//                    tempRaise[5] = raiseDone
//                    raiseDoneCounter += 1
//                } else
//                    if (playableRiverCall(
//                            (tempRaise[5] == raiseDone)
//                        )
//                    ) {
//                        callDoneCounter += 1
//                        raise(player[5], (raiseDone - tempRaise[5]))
//                        pot += (raiseDone - tempRaise[5])
//                        tempRaise[5] = raiseDone
//                    } else {
//                        fold()
//                        folded++
//                        inGame[5] = false
//                        if (folded == 5) {
//                            notFolded(player, inGame, pot)
//                            preFlopGamePlay(player, turnLiveData)
//                        }
//                    }
//        }
//
/////heto es du es
//
//
//        if (inGame[1] && ((tempRaise[1] != raiseDone) || (raiseDoneCounter == 0))) {
//
//            if (raiseDoneCounter == 0) {
//                if (lastRaise[1]) {
//                    if ((1..8).random() == 4) {
//                        check()
//                    } else {
//                        raiseDone = raise(player[1], (pot * ((56..87).random()) / 100))
//                        lastRaise = lastRaiseCheck(lastRaise, 1)
//                        pot += raiseDone
//                        raiseDoneCounter += 1
//                        tempRaise[1] = raiseDone
//                    }
//                } else
//                    if (playableRiverRaise(
//                            player[1].cart1,
//                            player[1].cart2,
//                            deck[0],
//                            deck[1],
//                            deck[2],
//                            deck[3],
//                            deck[4],
//                            pot,
//                            callDoneCounter,
//                            raiseDoneCounter,
//                            (raiseDone - tempRaise[1]),
//                            false
//                        ) && ((1..8).random() == 1)
//                    ) {
//                        raiseDone = raise(player[1], (pot * ((31..63).random()) / 100))
//                        lastRaise = lastRaiseCheck(lastRaise, 1)
//                        pot += raiseDone
//                        tempRaise[1] = raiseDone
//                        raiseDoneCounter += 1
//                    } else {
//                        check()
//                    }
//            } else
//                if (playableRiverRaise(
//                        player[1].cart1,
//                        player[1].cart2,
//                        deck[0],
//                        deck[1],
//                        deck[2],
//                        deck[3],
//                        deck[4],
//                        pot,
//                        callDoneCounter,
//                        raiseDoneCounter,
//                        (raiseDone - tempRaise[1]),
//                        (tempRaise[1] == raiseDone)
//                    )
//                ) {
//                    raiseDone = raise(player[1], (raiseDone * 3 - tempRaise[1]))
//                    lastRaise = lastRaiseCheck(lastRaise, 1)
//                    pot += raiseDone
//                    raiseDone += tempRaise[1]
//                    tempRaise[1] = raiseDone
//                    raiseDoneCounter += 1
//                } else
//                    if (playableRiverCall(
//                            (tempRaise[1] == raiseDone)
//                        )
//                    ) {
//                        callDoneCounter += 1
//                        raise(player[1], (raiseDone - tempRaise[1]))
//                        pot += (raiseDone - tempRaise[1])
//                        tempRaise[1] = raiseDone
//                    } else {
//                        fold()
//                        folded++
//                        inGame[1] = false
//                        if (folded == 5) {
//                            notFolded(player, inGame, pot)
//                            preFlopGamePlay(player, turnLiveData)
//                        }
//                    }
//        }
//
//
//        if (inGame[2] && ((tempRaise[2] != raiseDone) || (raiseDoneCounter == 0))) {
//
//            if (raiseDoneCounter == 0) {
//                if (lastRaise[2]) {
//                    if ((1..8).random() == 4) {
//                        check()
//                    } else {
//                        raiseDone = raise(player[2], (pot * ((56..87).random()) / 100))
//                        lastRaise = lastRaiseCheck(lastRaise, 2)
//                        pot += raiseDone
//                        raiseDoneCounter += 1
//                        tempRaise[2] = raiseDone
//                    }
//                } else
//                    if (playableRiverRaise(
//                            player[2].cart1,
//                            player[2].cart2,
//                            deck[0],
//                            deck[1],
//                            deck[2],
//                            deck[3],
//                            deck[4],
//                            pot,
//                            callDoneCounter,
//                            raiseDoneCounter,
//                            (raiseDone - tempRaise[2]),
//                            false
//                        ) && ((1..8).random() == 1)
//                    ) {
//                        raiseDone = raise(player[2], (pot * ((31..63).random()) / 100))
//                        lastRaise = lastRaiseCheck(lastRaise, 2)
//                        pot += raiseDone
//                        tempRaise[2] = raiseDone
//                        raiseDoneCounter += 1
//                    } else {
//                        check()
//                    }
//            } else
//                if (playableRiverRaise(
//                        player[2].cart1,
//                        player[2].cart2,
//                        deck[0],
//                        deck[1],
//                        deck[2],
//                        deck[3],
//                        deck[4],
//                        pot,
//                        callDoneCounter,
//                        raiseDoneCounter,
//                        (raiseDone - tempRaise[2]),
//                        (tempRaise[2] == raiseDone)
//                    )
//                ) {
//                    raiseDone = raise(player[2], (raiseDone * 3 - tempRaise[2]))
//                    lastRaise = lastRaiseCheck(lastRaise, 2)
//                    pot += raiseDone
//                    raiseDone += tempRaise[2]
//                    tempRaise[2] = raiseDone
//                    raiseDoneCounter += 1
//                } else
//                    if (playableRiverCall(
//                            (tempRaise[2] == raiseDone)
//                        )
//                    ) {
//                        callDoneCounter += 1
//                        raise(player[2], (raiseDone - tempRaise[2]))
//                        pot += (raiseDone - tempRaise[2])
//                        tempRaise[2] = raiseDone
//                    } else {
//                        fold()
//                        folded++
//                        inGame[2] = false
//                        if (folded == 5) {
//                            notFolded(player, inGame, pot)
//                            preFlopGamePlay(player, turnLiveData)
//                        }
//                    }
//        }
//
//        if (inGame[3] && ((tempRaise[3] != raiseDone) || (raiseDoneCounter == 0))) {
//
//            if (raiseDoneCounter == 0) {
//                if (lastRaise[3]) {
//                    if ((1..8).random() == 4) {
//                        check()
//                    } else {
//                        raiseDone = raise(player[3], (pot * ((56..87).random()) / 100))
//                        lastRaise = lastRaiseCheck(lastRaise, 3)
//                        pot += raiseDone
//                        raiseDoneCounter += 1
//                        tempRaise[3] = raiseDone
//                    }
//                } else
//                    if (playableRiverRaise(
//                            player[3].cart1,
//                            player[3].cart2,
//                            deck[0],
//                            deck[1],
//                            deck[2],
//                            deck[3],
//                            deck[4],
//                            pot,
//                            callDoneCounter,
//                            raiseDoneCounter,
//                            (raiseDone - tempRaise[3]),
//                            false
//                        ) && ((1..8).random() == 1)
//                    ) {
//                        raiseDone = raise(player[3], (pot * ((31..63).random()) / 100))
//                        lastRaise = lastRaiseCheck(lastRaise, 3)
//                        pot += raiseDone
//                        tempRaise[3] = raiseDone
//                        raiseDoneCounter += 1
//                    } else {
//                        check()
//                    }
//            } else
//                if (playableRiverRaise(
//                        player[3].cart1,
//                        player[3].cart2,
//                        deck[0],
//                        deck[1],
//                        deck[2],
//                        deck[3],
//                        deck[4],
//                        pot,
//                        callDoneCounter,
//                        raiseDoneCounter,
//                        (raiseDone - tempRaise[3]),
//                        (tempRaise[3] == raiseDone)
//                    )
//                ) {
//                    raiseDone = raise(player[3], (raiseDone * 3 - tempRaise[3]))
//                    lastRaise = lastRaiseCheck(lastRaise, 3)
//                    pot += raiseDone
//                    raiseDone += tempRaise[3]
//                    tempRaise[3] = raiseDone
//                    raiseDoneCounter += 1
//                } else
//                    if (playableRiverCall(
//                            (tempRaise[3] == raiseDone)
//                        )
//                    ) {
//                        callDoneCounter += 1
//                        raise(player[3], (raiseDone - tempRaise[3]))
//                        pot += (raiseDone - tempRaise[3])
//                        tempRaise[3] = raiseDone
//                    } else {
//                        fold()
//                        folded++
//                        inGame[3] = false
//                        if (folded == 5) {
//                            notFolded(player, inGame, pot)
//                            preFlopGamePlay(player, turnLiveData)
//                        }
//                    }
//        }
//
//    } while (!(
//                (((tempRaise[0] == raiseDone) && (inGame[0])) || (!inGame[0])) &&
//                        (((tempRaise[1] == raiseDone) && (inGame[1])) || (!inGame[1])) &&
//                        (((tempRaise[2] == raiseDone) && (inGame[2])) || (!inGame[2])) &&
//                        (((tempRaise[3] == raiseDone) && (inGame[3])) || (!inGame[3])) &&
//                        (((tempRaise[4] == raiseDone) && (inGame[4])) || (!inGame[4])) &&
//                        (((tempRaise[5] == raiseDone) && (inGame[5])) || (!inGame[5]))
//                )
//    )
//
//
//    val winnerList: MutableMap<Int, Int> = winner(player, inGame, deck, pot)
//    println(winnerList)
//
//
//    preFlopGamePlay(player, turnLiveData)
//
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
