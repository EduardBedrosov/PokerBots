package com.example.poker.logic.flop

import androidx.lifecycle.MutableLiveData
import com.example.poker.combination
import com.example.poker.states.BaseState
import com.example.poker.states.TurnState
import com.example.poker.logic.*

import com.example.poker.logic.turn.Cards
import com.example.poker.methods.Player
import kotlinx.coroutines.delay


class RiverGamePlay(
    playerPreRound: List<Player>,
    deckPreRound: List<Cards>,
    inGamePreRound: MutableList<Boolean>,
    lastRaisePreRound: MutableList<Boolean>,
    potPreRound: Int,
) {

    val player = playerPreRound
    val deck = deckPreRound
    val inGame = inGamePreRound
    val indexes: List<Int> = setPositionFlopAndAfter(round)
    var folded = foldedAfterFlop(inGame)
    var lastRaise = lastRaisePreRound
    private val stateRiver = 4
    var raiseDone = 0
    var pot = potPreRound
    var raiseDoneCounter = 0
    var callDoneCounter = 0
    val tempRaise = mutableListOf(0, 0, 0, 0, 0, 0)
    var chips = setCorrectChips(player)
    val check = mutableListOf(false, false, false, false, false, false)
    var playersIndex = -1



    suspend fun initRiverGame(
        turnLifeData: MutableLiveData<BaseState>
    ){
        turnLifeData.postValue(TurnState.StartRiverVisibility(pot))
        delay(1000)
        chips = setCorrectChips(player)
        turnLifeData.postValue(TurnState.AllChips(chips))
        delay(1000)
        turnLifeData.postValue(TurnState.DeckRiver(deck[4]))
        delay(1000)
    }

    suspend fun riverLoop(
        turnLifeData: MutableLiveData<BaseState>,
        indexNumber: Int = 0
    ) {

        val fixedIndexNumber = fixIndex(indexNumber)
        playersIndex = indexes[fixedIndexNumber]
        if (isHumanPreFlop(playersIndex)) {
            if (inGame[playersIndex] && ((tempRaise[playersIndex] != raiseDone) || (raiseDoneCounter == 0))) {
                check[playersIndex] = true
                turnLifeData.postValue(
                    TurnState.UserGamePlay(stateRiver, indexNumber, turnLifeData, raiseDone)
                )
                delay(2000)
            } else {
                turnLifeData.postValue(
                    TurnState.NextLoopRiver(
                        turnLifeData, indexNumber
                    )
                )
                delay(2000)
            }
        } else {
            turnLifeData.postValue(
                TurnState.NextPlayerRiver(
                    indexNumber, turnLifeData
                )
            )
            delay(2000)
        }
    }


    suspend fun botsTurnOnRiver(indexNumber: Int, turnLifeData: MutableLiveData<BaseState>) {
        var isNExtRoundCalled = false
        if (inGame[playersIndex] && ((tempRaise[playersIndex] != raiseDone) || (raiseDoneCounter == 0))) {
            check[playersIndex] = true
            if (raiseDoneCounter == 0) {
                    if (playableRiverRaise(
                            player[playersIndex].cart1,
                            player[playersIndex].cart2,
                            deck[0],
                            deck[1],
                            deck[2],
                            deck[3],
                            deck[4],
                            pot,
                            callDoneCounter,
                            raiseDoneCounter,
                            (raiseDone - tempRaise[playersIndex]),
                            false
                        )
                    ) {
                        raiseDone = raise(player[playersIndex], (pot * ((59..72).random()) / 100))
                        turnLifeData.postValue(
                            TurnState.Raise(
                                playersIndex,
                                raiseDone,
                                player[playersIndex].chips
                            )
                        )
                        delay(1000)
                        lastRaise = lastRaiseCheck(lastRaise, playersIndex)
                        pot += raiseDone
                        turnLifeData.postValue(TurnState.Pot(pot))
                        delay(1000)
                        chips = setCorrectChips(player)
                        turnLifeData.postValue(TurnState.AllChips(chips))
                        delay(1000)
                        tempRaise[playersIndex] = raiseDone
                        raiseDoneCounter += 1
                    } else {
                        check()
                        turnLifeData.postValue(TurnState.Check(playersIndex))
                        delay(2000)
                    }
            } else
                if (playableRiverRaise(
                        player[playersIndex].cart1,
                        player[playersIndex].cart2,
                        deck[0],
                        deck[1],
                        deck[2],
                        deck[3],
                        deck[4],
                        pot,
                        callDoneCounter,
                        raiseDoneCounter,
                        (raiseDone - tempRaise[playersIndex]),
                        (tempRaise[playersIndex] == raiseDone)
                    )
                ) {
                    if(lowRaise(pot, raiseDone, 0, callDoneCounter)){
                        raiseDone = pot/4
                    }
                    raiseDone =
                        raise(player[playersIndex], (raiseDone * 3 - tempRaise[playersIndex]))
                    turnLifeData.postValue(
                        TurnState.Raise(
                            playersIndex,
                            raiseDone + tempRaise[playersIndex],
                            player[playersIndex].chips
                        )
                    )
                    delay(1000)
                    lastRaise = lastRaiseCheck(lastRaise, playersIndex)
                    pot += raiseDone
                    raiseDone += tempRaise[playersIndex]
                    turnLifeData.postValue(TurnState.Pot(pot))
                    delay(1000)
                    chips = setCorrectChips(player)
                    turnLifeData.postValue(TurnState.AllChips(chips))
                    delay(1000)
                    tempRaise[playersIndex] = raiseDone
                    raiseDoneCounter += 1
                } else
                    if (playableRiverCall(
                            (tempRaise[playersIndex] == raiseDone)
                        )
                    ) {
                        raise(player[playersIndex], (raiseDone - tempRaise[playersIndex]))
                        callDoneCounter += 1
                        turnLifeData.postValue(
                            TurnState.Call(
                                playersIndex,
                                raiseDone,
                                player[playersIndex].chips
                            )
                        )
                        delay(1000)
                        chips = setCorrectChips(player)
                        turnLifeData.postValue(TurnState.AllChips(chips))
                        delay(1000)
                        pot += (raiseDone - tempRaise[playersIndex])
                        tempRaise[playersIndex] = raiseDone
                        turnLifeData.postValue(TurnState.Pot(pot))
                        delay(1000)

                    } else {
                        fold()
                        turnLifeData.postValue(TurnState.Fold(playersIndex))
                        delay(1000)
                        folded++
                        inGame[playersIndex] = false
                        if (folded == 5) {
                            chips = notFolded(chips, player, inGame, pot)
                            turnLifeData.postValue(
                                TurnState.Chips(
                                    playersIndex,
                                    player[playersIndex].chips
                                )
                            )
                            delay(1000)
                            isNExtRoundCalled = true
                            chips = setCorrectChips(player)
                            turnLifeData.postValue(
                                TurnState.NewGame(
                                    chips
                                )
                            )
                            delay(2000)
                        }
                    }
        }
        if (!isNExtRoundCalled) {
            turnLifeData.postValue(
                TurnState.NextLoopRiver(
                    turnLifeData, indexNumber
                )
            )
            delay(3000)
        }
    }


    private fun setCorrectChips(player: List<Player>): List<Int> {
        val chipsList = mutableListOf<Int>()
        for (i in player.indices) {
            chipsList.add(player[i].chips)
        }
        return chipsList
    }

    suspend fun playersRaiseRiver(
        indexNumber: Int, turnLifeData: MutableLiveData<BaseState>,
        raiseOfPlayer: Int
    ) {
        playerAgression += 4
        raiseDone = raise(
            player[playersIndex],
            (raiseOfPlayer - tempRaise[playersIndex])
        )
        raiseDoneCounter += 1
        lastRaise = lastRaiseCheck(lastRaise, playersIndex)
        turnLifeData.postValue(
            TurnState.Raise(
                playersIndex,
                raiseDone + tempRaise[playersIndex],
                player[playersIndex].chips
            )
        )
        delay(1000)
        pot += raiseDone
        turnLifeData.postValue(TurnState.Pot(pot))
        delay(1000)
        raiseDone += tempRaise[playersIndex]
        notNormalRaiseRiver(pot, raiseDone, raiseDoneCounter, callDoneCounter)
        wrongRaiseRiver(pot, raiseDone, raiseDoneCounter, callDoneCounter)
        tempRaise[playersIndex] = raiseDone
        chips = setCorrectChips(player)
        turnLifeData.postValue(TurnState.AllChips(chips))
        delay(1000)
        turnLifeData.postValue(
            TurnState.NextLoopRiver(
                turnLifeData, indexNumber
            )
        )
        delay(2000)
    }

    suspend fun playersCallRiver(indexNumber: Int, turnLifeData: MutableLiveData<BaseState>) {
        playerAgression += 2
        callDoneCounter += 1
        raise(player[playersIndex], (raiseDone - tempRaise[playersIndex]))
        turnLifeData.postValue(
            TurnState.Call(
                playersIndex,
                raiseDone,
                player[playersIndex].chips
            )
        )
        delay(1000)
        pot += (raiseDone - tempRaise[playersIndex])
        turnLifeData.postValue(TurnState.Pot(pot))
        delay(1000)
        tempRaise[playersIndex] = raiseDone
        chips = setCorrectChips(player)
        turnLifeData.postValue(TurnState.AllChips(chips))
        delay(1000)
        turnLifeData.postValue(
            TurnState.NextLoopRiver(
                turnLifeData, indexNumber
            )
        )
        delay(2000)
    }
    suspend fun playersCheckRiver(indexNumber: Int, flopLiveData: MutableLiveData<BaseState>) {

        flopLiveData.postValue(TurnState.Check(playersIndex))
        delay(1000)
        chips = setCorrectChips(player)
        flopLiveData.postValue(TurnState.AllChips(chips))
        delay(1000)
        flopLiveData.postValue(
            TurnState.NextLoopRiver(
                flopLiveData, indexNumber
            )
        )
        delay(2000)
    }

    suspend fun playersFoldRiver(indexNumber: Int, turnLifeData: MutableLiveData<BaseState>) {
        playerAgression -= 8
        fold()
        playing = false
        turnLifeData.postValue(TurnState.Fold(playersIndex))
        delay(1000)
        folded++
        inGame[playersIndex] = false
        if (folded == 5) {
            chips = notFolded(chips, player, inGame, pot)
            turnLifeData.postValue(
                TurnState.Chips(
                    playersIndex,
                    player[playersIndex].chips
                )
            )
            delay(1000)
            chips = setCorrectChips(player)
            turnLifeData.postValue(
                TurnState.NewGame(
                    chips
                )
            )
            delay(2000)
        } else {
            turnLifeData.postValue(
                TurnState.NextLoopRiver(
                    turnLifeData, indexNumber
                )
            )
            delay(2000)
        }
    }

    fun moveToEndingPlay(): Boolean {
        var tempCheck = true
        for (i in 0..5) {
            if ((raiseDoneCounter == 0) && ((!check[i]) && (inGame[i]))) {
                tempCheck = false
            }
            if  (!(((tempRaise[i] == raiseDone) && (inGame[i])) || (!inGame[i]))) {
                tempCheck = false
            }
        }
        return tempCheck
    }

    private fun fixIndex(indexNum: Int): Int {
        var tempNum = indexNum
        if (indexNum > 5) {
            tempNum = indexNum % 6
        }
        return tempNum
    }

    private fun isHumanPreFlop(index: Int): Boolean {
        return index == 0
    }

    suspend fun ending(riverLiveData: MutableLiveData<BaseState>) {


        val winner = winner(player, inGame, deck, pot)

        chips = setCorrectChips(player)
        riverLiveData.postValue(TurnState.AllChips(chips))
        delay(1000)

        riverLiveData.postValue(
            TurnState.Ending(player, inGame, chips, pot, winner)
        )
        delay(2000)
    }

    private fun winner(
        player: List<Player>,
        inGame: List<Boolean>,
        deck: List<Cards>,
        pot: Int
    ): MutableMap<Int, String> {

        val winnerList = mutableListOf<Int>(10, 10, 10, 10, 10, 10)


        for (i in 0..5) {
            when (i) {
                0 -> {
                    if (inGame[0]) {
                        winnerList[i] = combination(player[i].cart1, player[i].cart2, deck)
                    } else {
                        winnerList[i] = 5000000
                    }
                }
                1 -> {
                    if (inGame[1]) {
                        winnerList[i] = combination(player[i].cart1, player[i].cart2, deck)
                    } else {
                        winnerList[i] = 5000000
                    }
                }
                2 -> {
                    if (inGame[2]) {
                        winnerList[i] = combination(player[i].cart1, player[i].cart2, deck)
                    } else {
                        winnerList[i] = 5000000
                    }
                }
                3 -> {
                    if (inGame[3]) {
                        winnerList[i] = combination(player[i].cart1, player[i].cart2, deck)
                    } else {
                        winnerList[i] = 5000000
                    }
                }
                4 -> {
                    if (inGame[4]) {
                        winnerList[i] = combination(player[i].cart1, player[i].cart2, deck)
                    } else {
                        winnerList[i] = 5000000
                    }
                }
                5 -> {
                    if (inGame[5]) {
                        winnerList[i] = combination(player[i].cart1, player[i].cart2, deck)
                    } else {
                        winnerList[i] = 5000000
                    }
                }
            }
        }
        val winnerAndCombo = mutableMapOf<Int, String>()
        val win = winnerList.minOrNull()
        var winnerComboName: String = "WINNERNOONE"
        if (win != null) {
            when {
                win < 3 -> {
                    winnerComboName = "StreetFlush"
                }
                win < 18 -> {
                    winnerComboName = "4 OF A KInd"
                }
                win < 270 -> {
                    winnerComboName = "FULL HOUSE "
                }
                win < 283 -> {
                    winnerComboName = "Flush"
                }
                win < 293 -> {
                    winnerComboName = "Street"
                }
                win < 12184 -> {
                    winnerComboName = "3 of a kind"
                }
                win < 23519 -> {
                    winnerComboName = "2 Pair"
                }
                win < 388678 -> {
                    winnerComboName = "Pair"
                }
                else -> {
                    winnerComboName = "HighCard"
                }
            }
        }
        for (i in winnerList.indices) {
            if (winnerList[i] > 0) {
                if (winnerList[i] == winnerList.minOrNull()) {
                    winnerAndCombo.set(i, winnerComboName)
                }
            }
        }


        val winnersIndexes = mapToKeyList(winnerAndCombo)
        val winnerCombo = mapToValuesList(winnerAndCombo)
        val winnerNumbers = winnersIndexes.size


        for (i in winnersIndexes.indices) {
            when (i) {
                0 -> {
                    (player[winnersIndexes[i]]).chips =
                        player[winnersIndexes[i]].chips + pot / winnerNumbers
                }
                1 -> {
                    (player[winnersIndexes[i]]).chips =
                        (player[winnersIndexes[i]]).chips + pot / winnerNumbers
                }
                2 -> {
                    (player[winnersIndexes[i]]).chips =
                        (player[winnersIndexes[i]]).chips + pot / winnerNumbers
                }
                3 -> {
                    (player[winnersIndexes[i]]).chips =
                        (player[winnersIndexes[i]]).chips + pot / winnerNumbers
                }
                4 -> {
                    (player[winnersIndexes[i]]).chips =
                        (player[winnersIndexes[i]]).chips + pot / winnerNumbers
                }
                5 -> {
                    (player[winnersIndexes[i]]).chips =
                        (player[winnersIndexes[i]]).chips + pot / winnerNumbers
                }

            }
        }
        return winnerAndCombo
    }
    private fun notNormalRaiseRiver(pot: Int, raise: Int, raisCounter: Int, call: Int) {
        val tempPot = pot - call*raise
        var persent = tempPot - raise
        if (persent == 0) {
            ++persent
        }
        var persDouble = persent.toDouble()

        persDouble = raise / persDouble
        if ((persDouble >= 1.5) && (persDouble <= 2.5)) {
            playerNotNormalRaise++
        }

    }

    private fun wrongRaiseRiver(pot: Int, raise: Int, raisCounter: Int, call: Int) {
        val tempPot = pot - call*raise
        var persent = tempPot - raise
        if (persent == 0) {
            ++persent
        }
        var persDouble = persent.toDouble()
        persDouble = raise / persDouble
        if (persDouble > 2.5) {
            playerWrongRaise++
        }
    }
}

