package com.example.poker.logic.flop

import androidx.lifecycle.MutableLiveData
import com.example.poker.states.BaseState
import com.example.poker.states.TurnState
import com.example.poker.logic.*

import com.example.poker.logic.turn.Cards
import com.example.poker.methods.Player
import kotlinx.coroutines.delay


class FlopGamePlay(
    playerPreRound: List<Player>,
    deckPreRound: List<Cards>,
    inGamePreRound: MutableList<Boolean>,
    lastRaisePreFlop: MutableList<Boolean>,

    potPreFlop: Int,
) {
    val player = playerPreRound
    val deck = deckPreRound
    val inGame = inGamePreRound
    val indexes: List<Int> = setPositionFlopAndAfter(round)
    var folded = foldedAfterFlop(inGame)
    var lastRaise = lastRaisePreFlop
    private val stateFlop = 2
    var raiseDone = 0
    var pot = potPreFlop
    var raiseDoneCounter = 0
    var callDoneCounter = 0
    val tempRaise = mutableListOf(0, 0, 0, 0, 0, 0)
    val check = mutableListOf(false, false,false, false, false, false)
    var chips = setCorrectChips(player)
    var playersIndex = -1


    suspend fun initFlopGame(
        flopLiveData: MutableLiveData<BaseState>
    ){
        flopLiveData.postValue(TurnState.StartFlopVisibility(pot))
        delay(1000)
        chips = setCorrectChips(player)
        flopLiveData.postValue(TurnState.AllChips(chips))
        delay(1000)
        flopLiveData.postValue(TurnState.DeckFlop(deck))
        delay(1000)
    }

    suspend fun flopLoop(
        flopLiveData: MutableLiveData<BaseState>,
        indexNumber: Int = 0
    ) {

        val fixedIndexNumber = fixIndex(indexNumber)
        playersIndex = indexes[fixedIndexNumber]

        if (isHumanPreFlop(playersIndex)) {
            if (inGame[playersIndex] && ((tempRaise[playersIndex] != raiseDone) || (raiseDoneCounter == 0))) {

                check[playersIndex] = true
                flopLiveData.postValue(
                    TurnState.UserGamePlay(stateFlop, indexNumber, flopLiveData, raiseDone)
                )
                delay(2000)
            } else {
                flopLiveData.postValue(
                    TurnState.NextLoopFlop(
                        flopLiveData, indexNumber
                    )
                )
                delay(2000)
            }
        } else {
            flopLiveData.postValue(
                TurnState.NextPlayerFlop(
                    indexNumber, flopLiveData)
            )
            delay(2000)
        }
    }


    suspend fun botsTurnFlop(indexNumber: Int, flopLiveData: MutableLiveData<BaseState>) {
        var isNExtRoundCalled = false

        if (inGame[playersIndex] && ((tempRaise[playersIndex] != raiseDone) || (raiseDoneCounter == 0))) {
            check[playersIndex] = true
            if (raiseDoneCounter == 0) {
                if (lastRaise[playersIndex]) {
                    if ((1..6).random() == 2) {
                        check()
                        flopLiveData.postValue(TurnState.Check(playersIndex))
                        delay(1000)
                    } else {
                        raiseDone = raise(player[playersIndex], (pot * ((29..44).random()) / 100))
                        flopLiveData.postValue(
                            TurnState.Raise(
                                playersIndex,
                                raiseDone,
                                player[playersIndex].chips
                            )
                        )
                        delay(1000)
                        lastRaise = lastRaiseCheck(lastRaise, playersIndex)
                        flopLiveData.postValue(
                            TurnState.Raise(
                                playersIndex,
                                raiseDone + tempRaise[playersIndex],
                                player[playersIndex].chips
                            )
                        )
                        delay(1000)
                        chips = setCorrectChips(player)
                        flopLiveData.postValue(TurnState.AllChips(chips))
                        delay(1000)
                        pot += raiseDone
                        flopLiveData.postValue(TurnState.Pot(pot))
                        delay(1000)
                        raiseDoneCounter += 1
                        tempRaise[playersIndex] = raiseDone
                    }
                } else
                    if (playableFlopRaise(
                            player[playersIndex].cart1,
                            player[playersIndex].cart2,
                            deck[0],
                            deck[1],
                            deck[2],
                            pot,
                            callDoneCounter,
                            raiseDoneCounter,
                            (raiseDone - tempRaise[playersIndex]),
                            false
                        ) && ((((1..2).random() == 1) && allChecked(inGame, check)) ||
                                ((1..5).random() == 1))
                    ) {
                        raiseDone = raise(player[playersIndex], (pot * ((22..33).random()) / 100))
                        flopLiveData.postValue(
                            TurnState.Raise(
                                playersIndex,
                                raiseDone,
                                player[playersIndex].chips
                            )
                        )
                        delay(1000)
                        lastRaise = lastRaiseCheck(lastRaise, playersIndex)
                        pot += raiseDone
                        flopLiveData.postValue(TurnState.Pot(pot))
                        delay(1000)
                        tempRaise[playersIndex] = raiseDone
                        chips = setCorrectChips(player)
                        flopLiveData.postValue(TurnState.AllChips(chips))
                        delay(1000)
                        raiseDoneCounter += 1
                    } else {
                        check()
                        flopLiveData.postValue(TurnState.Check(playersIndex))
                        delay(1000)
                    }
            } else
                if (playableFlopRaise(
                        player[playersIndex].cart1,
                        player[playersIndex].cart2,
                        deck[0],
                        deck[1],
                        deck[2],
                        pot,
                        callDoneCounter,
                        raiseDoneCounter,
                        (raiseDone - tempRaise[playersIndex]),
                        (tempRaise[playersIndex] == raiseDone)
                    )
                ) {
                    if(lowRaise(pot, raiseDone, 0, callDoneCounter)){
                        raiseDone = pot/6
                    }
                    raiseDone =
                        raise(player[playersIndex], (raiseDone * ((24..32).random())/10 - tempRaise[playersIndex]))
                    flopLiveData.postValue(
                        TurnState.Raise(
                            playersIndex,
                            raiseDone + tempRaise[playersIndex],
                            player[playersIndex].chips
                        )
                    )
                    delay(1000)
                    lastRaise = lastRaiseCheck(lastRaise, playersIndex)
                    pot += raiseDone
                    flopLiveData.postValue(TurnState.Pot(pot))
                    delay(1000)
                    raiseDone += tempRaise[playersIndex]
                    chips = setCorrectChips(player)
                    flopLiveData.postValue(TurnState.AllChips(chips))
                    delay(1000)
                    tempRaise[playersIndex] = raiseDone
                    raiseDoneCounter += 1
                } else
                    if (playableFlopCall(
                            (tempRaise[playersIndex] == raiseDone)
                        )
                    ) {
                        callDoneCounter += 1
                        raise(player[playersIndex], (raiseDone - tempRaise[playersIndex]))
                        flopLiveData.postValue(
                            TurnState.Call(
                                playersIndex,
                                raiseDone,
                                player[playersIndex].chips
                            )
                        )
                        delay(1000)
                        pot += (raiseDone - tempRaise[playersIndex])
                        flopLiveData.postValue(TurnState.Pot(pot))
                        delay(1000)
                        chips = setCorrectChips(player)
                        flopLiveData.postValue(TurnState.AllChips(chips))
                        delay(1000)
                        tempRaise[playersIndex] = raiseDone
                    } else {
                        fold()
                        flopLiveData.postValue(TurnState.Fold(playersIndex))
                        delay(1000)
                        folded++
                        inGame[playersIndex] = false
                        if (folded == 5) {
                            chips = notFolded(chips, player, inGame, pot)
                            flopLiveData.postValue(
                                TurnState.Chips(
                                    playersIndex,
                                    player[playersIndex].chips
                                )
                            )
                            delay(1000)
                            isNExtRoundCalled = true
                            chips = setCorrectChips(player)
                            flopLiveData.postValue(
                                TurnState.NewGame(
                                    chips
                                )
                            )
                            delay(2000)
                        }
                    }
        }
        if (!isNExtRoundCalled) {
            flopLiveData.postValue(
                TurnState.NextLoopFlop(
                    flopLiveData, indexNumber
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

    suspend fun playersRaiseFlop(
        indexNumber: Int, preFlopLifeData: MutableLiveData<BaseState>,
        raiseOfPlayer: Int
    ) {
        playerAgression += 2
        raiseDone = raise(
            player[playersIndex],
            (raiseOfPlayer - tempRaise[playersIndex])
        )
        raiseDoneCounter += 1
        lastRaise = lastRaiseCheck(lastRaise, playersIndex)
        preFlopLifeData.postValue(
            TurnState.Raise(
                playersIndex,
                raiseDone + tempRaise[playersIndex],
                player[playersIndex].chips
            )
        )
        delay(1000)
        pot += raiseDone
        preFlopLifeData.postValue(TurnState.Pot(pot))
        delay(1000)
        raiseDone += tempRaise[playersIndex]
        notNormalRaiseFlop(pot, raiseDone, raiseDoneCounter, callDoneCounter)
        wrongRaiseFlop(pot, raiseDone, raiseDoneCounter, callDoneCounter)
        tempRaise[playersIndex] = raiseDone
        chips = setCorrectChips(player)
        preFlopLifeData.postValue(TurnState.AllChips(chips))
        delay(1000)
        preFlopLifeData.postValue(
            TurnState.NextLoopFlop(
                preFlopLifeData, indexNumber
            )
        )
        delay(2000)
    }

    suspend fun playersCallFlop(indexNumber: Int, flopLiveData: MutableLiveData<BaseState>) {
        playerAgression++
        callDoneCounter += 1
        raise(player[playersIndex], (raiseDone - tempRaise[playersIndex]))
        flopLiveData.postValue(
            TurnState.Call(
                playersIndex,
                raiseDone,
                player[playersIndex].chips
            )
        )
        delay(1000)
        pot += (raiseDone - tempRaise[playersIndex])
        flopLiveData.postValue(TurnState.Pot(pot))
        delay(1000)
        tempRaise[playersIndex] = raiseDone
        chips = setCorrectChips(player)
        flopLiveData.postValue(TurnState.AllChips(chips))
        delay(1000)
        flopLiveData.postValue(
            TurnState.NextLoopFlop(
                flopLiveData, indexNumber
            )
        )
        delay(2000)
    }
    suspend fun playersCheckFlop(indexNumber: Int, flopLiveData: MutableLiveData<BaseState>) {

        flopLiveData.postValue(TurnState.Check(playersIndex))
        delay(1000)
        flopLiveData.postValue(
            TurnState.NextLoopFlop(
                flopLiveData, indexNumber
            )
        )
        delay(2000)
    }

    suspend fun playersFoldFlop(indexNumber: Int, flopLiveData: MutableLiveData<BaseState>) {
        playerAgression -= 2
        fold()
        playing = false
        flopLiveData.postValue(TurnState.Fold(playersIndex))
        delay(1000)
        folded++
        inGame[playersIndex] = false
        if (folded == 5) {
            chips = notFolded(chips, player, inGame, pot)
            flopLiveData.postValue(
                TurnState.Chips(
                    playersIndex,
                    player[playersIndex].chips
                )
            )
            delay(1000)
            chips = setCorrectChips(player)
            flopLiveData.postValue(
                TurnState.NewGame(
                    chips
                )
            )
            delay(2000)
        } else {
            flopLiveData.postValue(
                TurnState.NextLoopFlop(
                    flopLiveData, indexNumber
                )
            )
            delay(2000)
        }
    }

    fun moveToTurnPlay(): Boolean {
        var tempCheck = true
        for (i in 0..5) {
            if ((raiseDoneCounter == 0) && (!check[i]) && (inGame[i])) {
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

    private fun notNormalRaiseFlop(pot: Int, raise: Int, raisCounter: Int, call: Int){
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

    private fun wrongRaiseFlop(pot: Int, raise: Int, raisCounter: Int, call: Int) {
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

