package com.example.poker.logic.flop

import androidx.lifecycle.MutableLiveData
import com.example.poker.states.BaseState
import com.example.poker.states.TurnState
import com.example.poker.logic.*

import com.example.poker.logic.turn.Cards
import com.example.poker.methods.Player
import kotlinx.coroutines.delay


class TurnGamePlay(
    playerPreRound: List<Player>,
    deckPreRound: List<Cards>,
    inGamePreRound: MutableList<Boolean>,
    lastRaisePreRound: MutableList<Boolean>,
    potPreRound: Int
) {

    val player = playerPreRound
    val deck = deckPreRound
    val inGame = inGamePreRound
    val indexes: List<Int> = setPositionFlopAndAfter(round)
    var folded = foldedAfterFlop(inGame)
    var lastRaise = lastRaisePreRound
    private val stateTurn = 3
    var raiseDone = 0
    var pot = potPreRound
    var raiseDoneCounter = 0
    var callDoneCounter = 0
    val tempRaise = mutableListOf(0, 0, 0, 0, 0, 0)
    var chips = setCorrectChips(player)
    val check = mutableListOf(false, false, false, false, false, false)
    var playersIndex = -1


    suspend fun initTurnGame(
        turnLifeData: MutableLiveData<BaseState>
    ) {
        turnLifeData.postValue(TurnState.StartTurnVisibility(pot))
        delay(1000)
        chips = setCorrectChips(player)
        turnLifeData.postValue(TurnState.AllChips(chips))
        delay(1000)
        turnLifeData.postValue(TurnState.DeckTurn(deck[3]))
        delay(1000)
    }


    suspend fun turnLoop(
        turnLifeData: MutableLiveData<BaseState>,
        indexNumber: Int = 0
    ) {
        val fixedIndexNumber = fixIndex(indexNumber)
        playersIndex = indexes[fixedIndexNumber]
        if (isHumanPreFlop(playersIndex)) {
            if (inGame[playersIndex] && ((tempRaise[playersIndex] != raiseDone) || (raiseDoneCounter == 0))) {
                check[playersIndex] = true

                turnLifeData.postValue(
                    TurnState.UserGamePlay(stateTurn, indexNumber, turnLifeData, raiseDone)
                )
                delay(2000)
            } else {
                turnLifeData.postValue(
                    TurnState.NextLoopTurn(
                        turnLifeData, indexNumber
                    )
                )
                delay(2000)
            }
        } else {
            turnLifeData.postValue(
                TurnState.NextPlayerTurn(
                    indexNumber, turnLifeData
                )
            )
            delay(2000)
        }
    }

    suspend fun botsTurnOnTurn(indexNumber: Int, turnLifeData: MutableLiveData<BaseState>) {
        var isNExtRoundCalled = false
        if (inGame[playersIndex] && ((tempRaise[playersIndex] != raiseDone) || (raiseDoneCounter == 0))) {
            check[playersIndex] = true
            if (raiseDoneCounter == 0) {
                if (lastRaise[playersIndex]) {
                    if ((1..5).random() == 2) {
                        check()
                        turnLifeData.postValue(TurnState.Check(playersIndex))
                        delay(1000)
                    } else {
                        raiseDone = raise(player[playersIndex], (pot * ((59..76).random()) / 100))
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
                        raiseDoneCounter += 1
                        tempRaise[playersIndex] = raiseDone
                    }
                } else
                    if (playableTurnRaise(
                            player[playersIndex].cart1,
                            player[playersIndex].cart2,
                            deck[0],
                            deck[1],
                            deck[2],
                            deck[3],
                            pot,
                            callDoneCounter,
                            raiseDoneCounter,
                            (raiseDone - tempRaise[playersIndex]),
                            false
                        ) && ((((1..2).random() == 1) && allChecked(inGame, check)) ||
                                ((1..3).random() == 1))
                    ) {
                        raiseDone = raise(player[playersIndex], (pot * ((49..67).random()) / 100))
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
                        delay(1000)
                    }
            } else
                if (playableTurnRaise(
                        player[playersIndex].cart1,
                        player[playersIndex].cart2,
                        deck[0],
                        deck[1],
                        deck[2],
                        deck[3],
                        pot,
                        callDoneCounter,
                        raiseDoneCounter,
                        (raiseDone - tempRaise[playersIndex]),
                        (tempRaise[playersIndex] == raiseDone)
                    )
                ) {
                    if (lowRaise(pot, raiseDone, 0, callDoneCounter )) {
                        raiseDone = pot / 4
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
                    turnLifeData.postValue(TurnState.Pot(pot))
                    delay(1000)
                    raiseDone += tempRaise[playersIndex]
                    chips = setCorrectChips(player)
                    turnLifeData.postValue(TurnState.AllChips(chips))
                    delay(1000)
                    tempRaise[playersIndex] = raiseDone
                    raiseDoneCounter += 1
                } else
                    if (playableTurnCall(
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
                        pot += (raiseDone - tempRaise[playersIndex])
                        turnLifeData.postValue(TurnState.Pot(pot))
                        delay(1000)
                        chips = setCorrectChips(player)
                        turnLifeData.postValue(TurnState.AllChips(chips))
                        delay(1000)
                        tempRaise[playersIndex] = raiseDone
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
                TurnState.NextLoopTurn(
                    turnLifeData, indexNumber
                )
            )
            delay(2000)
        }
    }


    private fun setCorrectChips(player: List<Player>): List<Int> {
        val chipsList = mutableListOf<Int>()
        for (i in player.indices) {
            chipsList.add(player[i].chips)
        }
        return chipsList
    }

    suspend fun playersRaiseTurn(
        indexNumber: Int, turnLifeData: MutableLiveData<BaseState>,
        raiseOfPlayer: Int
    ) {
        playerAgression += 3
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
        notNormalRaiseTurn(pot, raiseDone, raiseDoneCounter, callDoneCounter)
        wrongRaiseTurn(pot, raiseDone, raiseDoneCounter, callDoneCounter)
        tempRaise[playersIndex] = raiseDone
        chips = setCorrectChips(player)
        turnLifeData.postValue(TurnState.AllChips(chips))
        delay(1000)
        turnLifeData.postValue(
            TurnState.NextLoopTurn(
                turnLifeData, indexNumber
            )
        )
        delay(2000)
    }

    suspend fun playersCallTurn(indexNumber: Int, turnLifeData: MutableLiveData<BaseState>) {
        playerAgression++
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
            TurnState.NextLoopTurn(
                turnLifeData, indexNumber
            )
        )
        delay(2000)
    }

    suspend fun playersCheckTurn(indexNumber: Int, flopLiveData: MutableLiveData<BaseState>) {

        flopLiveData.postValue(TurnState.Check(playersIndex))
        delay(1000)
        flopLiveData.postValue(
            TurnState.NextLoopTurn(
                flopLiveData, indexNumber
            )
        )
        delay(2000)
    }

    suspend fun playersFoldTurn(indexNumber: Int, turnLifeData: MutableLiveData<BaseState>) {
        playerAgression -= 6
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
                TurnState.NextLoopTurn(
                    turnLifeData, indexNumber
                )
            )
            delay(2000)
        }
    }

    fun moveToRiverPlay(): Boolean {
        var tempCheck = true
        for (i in 0..5) {
            if ((raiseDoneCounter == 0) && ((!check[i]) && (inGame[i]))) {
                tempCheck = false
            }
            if (!(((tempRaise[i] == raiseDone) && (inGame[i])) || (!inGame[i]))) {
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
    private fun notNormalRaiseTurn(pot: Int, raise: Int, raisCounter: Int, call: Int){
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

    private fun wrongRaiseTurn(pot: Int, raise: Int, raisCounter: Int, call: Int) {
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

