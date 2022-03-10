package com.example.poker.logic.preflop

import androidx.lifecycle.MutableLiveData
import com.example.poker.states.BaseState
import com.example.poker.states.TurnState
import com.example.poker.logic.*
import com.example.poker.methods.Player
//import com.example.poker.logic.flop.flopGamePlay
import com.example.poker.methods.Table
import kotlinx.coroutines.delay

class PreFlopGamePlay(
    chipsOfPlayers: List<Int> = mutableListOf(
        1500000, 1500000, 1500000, 1500000, 1500000, 1500000
    )
) {
    private val indexes: List<Int> = setPositions(round + 1)
    var playersIndex: Int = -1
    private val statePreFlop = 1
    private var chips = chipsOfPlayers
    private val table = Table()
    var deck = table.createdDeck
    var players = table.createdPlayer
    val inGame = mutableListOf(
        true, true, true, true, true, true
    )
    var lastRaise = mutableListOf(
        false, false, false, false, true, false
    )
    var folded = 0
    var pot: Int = 0
    val check = mutableListOf(true, true,true, true, true, false)
    var raiseDone = bigBlind
    var raiseDoneCounter = 0
    var callDoneCounter = 0
    val tempRaise = mutableListOf(0, 0, 0, 0, 0, 0)

    suspend fun initPreFlopGame(
        preFlopLifeData: MutableLiveData<BaseState>
    ) {
        preFlopLifeData.postValue(TurnState.StartGameVisibilityDefault(pot))
        delay(2000)
        playing = true
        round++
        setBlinds(round)
        table.createTable(chips)
        players = table.createdPlayer
        deck = table.createdDeck
        raiseDone = bigBlind
        val dealer: Int = indexes[3]
        preFlopLifeData.postValue(
            TurnState.DealerAndPlayerCards(
                dealer,
                deck[0],
                deck[1],
                deck[2],
                deck[3],
                deck[4],
                players[0].cart1,
                players[0].cart2,
                players[1].cart1,
                players[1].cart2,
                players[2].cart1,
                players[2].cart2,
                players[3].cart1,
                players[3].cart2,
                players[4].cart1,
                players[4].cart2,
                players[5].cart1,
                players[5].cart2
            )
        )
        delay(2000)
        raise(players[indexes[4]], smallBlind)
        raise(players[indexes[5]], bigBlind)
        tempRaise[indexes[4]] = smallBlind
        tempRaise[indexes[5]] = bigBlind
        preFlopLifeData.postValue(
            TurnState.Raise(
                indexes[4],
                smallBlind,
                players[indexes[4]].chips
            )
        )
        delay(1000)
        preFlopLifeData.postValue(TurnState.Raise(indexes[5], bigBlind, players[indexes[5]].chips))
        delay(1000)
        pot = smallBlind + bigBlind
        preFlopLifeData.postValue(TurnState.Pot(pot))
        delay(1000)
        chips = setCorrectChipsPre(players)
        preFlopLifeData.postValue(TurnState.AllChips(chips))
        delay(1000)
    }


    suspend fun preFlopLoop(
        preFlopLifeData: MutableLiveData<BaseState>,
        indexNumber: Int = 0
    ) {

        val fixedIndexNumber = fixIndex(indexNumber)
        playersIndex = indexes[fixedIndexNumber]

        if (isHumanPreFlop(playersIndex)) {
            if (inGame[playersIndex] && ((tempRaise[playersIndex] != raiseDone) || (raiseDoneCounter == 0))) {
                
                preFlopLifeData.postValue(
                    TurnState.UserGamePlay(statePreFlop, indexNumber, preFlopLifeData, raiseDone)
                )
            } else {
                preFlopLifeData.postValue(
                    TurnState.NextLoop(
                        preFlopLifeData, indexNumber
                    )
                )
                delay(2000)
            }
        } else {
            preFlopLifeData.postValue(
                TurnState.NextPlayer(
                    indexNumber, preFlopLifeData, isBigBlind(indexNumber)
                )
            )
            delay(2000)
        }


//        turnLiveData.postValue(
//            TurnState.NextLoop(
//                players, turnLiveData,
//                indexNumber
//            )
//        )

    }

    suspend fun playersRaise(
        indexNumber: Int, preFlopLifeData: MutableLiveData<BaseState>,
        raiseOfPlayer: Int
    ) {
        playerAgression++
        raiseDone = raise(
            players[playersIndex],
            (raiseOfPlayer - tempRaise[playersIndex])
        )
        raiseDoneCounter += 1
        lastRaise = lastRaiseCheck(lastRaise, playersIndex)
        preFlopLifeData.postValue(
            TurnState.Raise(
                playersIndex,
                raiseDone + tempRaise[playersIndex],
                players[playersIndex].chips
            )
        )
        delay(1000)
        pot += raiseDone
        preFlopLifeData.postValue(TurnState.Pot(pot))
        delay(1000)
        raiseDone += tempRaise[playersIndex]
        notNormalRaisePreFlop(pot,raiseDone,callDoneCounter)
        wrongRaisePreFlop(pot,raiseDone,callDoneCounter)
        tempRaise[playersIndex] = raiseDone
        chips = setCorrectChipsPre(players)
        preFlopLifeData.postValue(TurnState.AllChips(chips))
        delay(1000)
        preFlopLifeData.postValue(
            TurnState.NextLoop(
                preFlopLifeData, indexNumber
            )
        )
        delay(1000)
    }

    suspend fun playersCall(indexNumber: Int, preFlopLifeData: MutableLiveData<BaseState>) {
        playerAgression++
        callDoneCounter += 1
        raise(players[playersIndex], (raiseDone - tempRaise[playersIndex]))
        preFlopLifeData.postValue(
            TurnState.Call(
                playersIndex,
                raiseDone,
                players[playersIndex].chips
            )
        )
        delay(1000)
        pot += (raiseDone - tempRaise[playersIndex])
        preFlopLifeData.postValue(TurnState.Pot(pot))
        delay(1000)
        tempRaise[playersIndex] = raiseDone
        chips = setCorrectChipsPre(players)
        preFlopLifeData.postValue(TurnState.AllChips(chips))
        delay(1000)
        preFlopLifeData.postValue(
            TurnState.NextLoop(
                preFlopLifeData, indexNumber
            )
        )
        delay(1000)
    }

    suspend fun playersFold(indexNumber: Int, preFlopLifeData: MutableLiveData<BaseState>) {
        fold()
        playerAgression -= 3
        playing = false
        preFlopLifeData.postValue(TurnState.Fold(playersIndex))
        delay(1000)
        folded++
        inGame[playersIndex] = false
        if (folded == 5) {
            chips = notFolded(chips, players, inGame, pot)
            preFlopLifeData.postValue(
                TurnState.Chips(
                    playersIndex,
                    players[playersIndex].chips
                )
            )
            delay(1000)
            chips = setCorrectChipsPre(players)
            preFlopLifeData.postValue(
                TurnState.NewGame(
                    chips
                )
            )
            delay(2000)
        } else {
            preFlopLifeData.postValue(
                TurnState.NextLoop(
                    preFlopLifeData, indexNumber
                )
            )
            delay(1000)
        }
    }

    fun moveToFlopPlay(): Boolean {
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

    private fun isBigBlind(position: Int): Boolean {
        return (position == 5)
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

    suspend fun botsTurn(indexNumber: Int, preFlopLifeData: MutableLiveData<BaseState>) {

        var isNExtRoundCalled = false

        if (inGame[playersIndex] && ((tempRaise[playersIndex] != raiseDone) || (raiseDoneCounter == 0))) {
            if (raiseDoneCounter == 0) {
                if (playablePreFlopRaise(
                        players[playersIndex].cart1,
                        players[playersIndex].cart2,
                        indexNumber,
                        pot,
                        raiseDoneCounter,
                        raiseDone,
                        callDoneCounter,
                        tempRaise[playersIndex] == raiseDone
                    )
                ) {
                    raiseDone = raise(
                        players[playersIndex],
                        (raiseDone * 8 / 3 - tempRaise[playersIndex])
                    )
                    lastRaise = lastRaiseCheck(lastRaise, playersIndex)
                    preFlopLifeData.postValue(
                        TurnState.Raise(
                            playersIndex,
                            raiseDone + tempRaise[playersIndex],
                            players[playersIndex].chips
                        )
                    )
                    delay(1000)
                    pot += raiseDone
                    preFlopLifeData.postValue(TurnState.Pot(pot))
                    delay(1000)
                    chips = setCorrectChipsPre(players)
                    preFlopLifeData.postValue(TurnState.AllChips(chips))
                    delay(1000)
                    raiseDone += tempRaise[playersIndex]
                    raiseDoneCounter += 1
                    tempRaise[playersIndex] = raiseDone
                } else {
                    fold()
                    preFlopLifeData.postValue(TurnState.Fold(playersIndex))
                    delay(2000)
                    folded++
                    inGame[playersIndex] = false
                    if (folded == 5) {
                        chips = notFolded(chips, players, inGame, pot)
                        preFlopLifeData.postValue(
                            TurnState.Chips(
                                playersIndex,
                                players[playersIndex].chips
                            )
                        )
                        delay(1000)
                        isNExtRoundCalled = true
                        chips = setCorrectChipsPre(players)
                        preFlopLifeData.postValue(
                            TurnState.NewGame(
                                chips
                            )
                        )
                        delay(2000)
                    }
                }
            } else
                if (playablePreFlopRaise(
                        players[playersIndex].cart1,
                        players[playersIndex].cart2,
                        indexNumber,
                        pot,
                        raiseDoneCounter,
                        raiseDone,
                        callDoneCounter,
                        tempRaise[playersIndex] == raiseDone
                    )
                ) {
                    raiseDone = raise(
                        players[playersIndex],
                        (raiseDone * 8 / 3 - tempRaise[playersIndex])
                    )
                    preFlopLifeData.postValue(
                        TurnState.Raise(
                            playersIndex,
                            raiseDone + tempRaise[playersIndex],
                            players[playersIndex].chips
                        )
                    )
                    delay(1000)
                    pot += raiseDone
                    preFlopLifeData.postValue(TurnState.Pot(pot))
                    delay(1000)
                    chips = setCorrectChipsPre(players)
                    preFlopLifeData.postValue(TurnState.AllChips(chips))
                    delay(1000)
                    raiseDone += tempRaise[playersIndex]
                    lastRaise = lastRaiseCheck(lastRaise, playersIndex)
                    raiseDoneCounter += 1
                    tempRaise[playersIndex] = raiseDone
                } else
                    if (playablePreFlopCall(
                            tempRaise[playersIndex] == raiseDone
                        )
                    ) {
                        callDoneCounter += 1
                        raise(players[playersIndex], (raiseDone - tempRaise[playersIndex]))
                        preFlopLifeData.postValue(
                            TurnState.Call(
                                playersIndex,
                                raiseDone,
                                players[playersIndex].chips
                            )
                        )
                        delay(1000)
                        pot += (raiseDone - tempRaise[playersIndex])
                        preFlopLifeData.postValue(TurnState.Pot(pot))
                        delay(1000)
                        chips = setCorrectChipsPre(players)
                        preFlopLifeData.postValue(TurnState.AllChips(chips))
                        delay(1000)
                        tempRaise[playersIndex] = raiseDone
                    } else {

                        fold()
                        preFlopLifeData.postValue(TurnState.Fold(playersIndex))
                        delay(1000)
                        folded++
                        inGame[playersIndex] = false
                        if (folded == 5) {
                            chips = notFolded(chips, players, inGame, pot)
                            preFlopLifeData.postValue(
                                TurnState.Chips(
                                    playersIndex,
                                    players[playersIndex].chips
                                )
                            )
                            delay(1000)
                            isNExtRoundCalled = true
                            chips = setCorrectChipsPre(players)
                            preFlopLifeData.postValue(
                                TurnState.NewGame(
                                    chips
                                )
                            )
                            delay(2000)
                        }
                    }
        }
        if (!isNExtRoundCalled) {
            preFlopLifeData.postValue(
                TurnState.NextLoop(
                    preFlopLifeData, indexNumber
                )
            )
        }
        delay(2000)
    }


    suspend fun botsTurnBigBlind(indexNumber: Int, preFlopLifeData: MutableLiveData<BaseState>) {
        var isNExtRoundCalled = false
        if (inGame[playersIndex] && ((tempRaise[playersIndex] != raiseDone) || (raiseDoneCounter == 0))) {
            check[playersIndex] = true
            if (raiseDoneCounter == 0 && callDoneCounter == 0) {
                chips = notFolded(chips, players, inGame, pot)
                preFlopLifeData.postValue(
                    TurnState.Chips(
                        playersIndex,
                        players[playersIndex].chips
                    )
                )
                delay(1000)
                chips = setCorrectChipsPre(players)
                preFlopLifeData.postValue(
                    TurnState.NewGame(
                        chips
                    )
                )
                delay(2000)
            } else
                if (raiseDoneCounter != 0) {
                    if (playablePreFlopRaise(
                            players[playersIndex].cart1,
                            players[playersIndex].cart2,
                            indexNumber,
                            pot,
                            raiseDoneCounter,
                            raiseDone,
                            callDoneCounter,
                            tempRaise[playersIndex] == raiseDone
                        )
                    ) {
                        raiseDone = raise(
                            players[playersIndex],
                            (raiseDone * 8/3 - tempRaise[playersIndex])
                        )
                        preFlopLifeData.postValue(
                            TurnState.Raise(
                                playersIndex,
                                raiseDone + tempRaise[playersIndex],
                                players[playersIndex].chips
                            )
                        )
                        delay(1000)
                        pot += raiseDone
                        preFlopLifeData.postValue(TurnState.Pot(pot))
                        delay(1000)
                        chips = setCorrectChipsPre(players)
                        preFlopLifeData.postValue(TurnState.AllChips(chips))
                        delay(1000)
                        raiseDone += tempRaise[playersIndex]
                        lastRaise = lastRaiseCheck(lastRaise, playersIndex)
                        raiseDoneCounter += 1
                        tempRaise[playersIndex] = raiseDone
                    } else
                        if (playablePreFlopCall(
                                tempRaise[playersIndex] == raiseDone
                            )
                        ) {
                            raise(players[playersIndex], (raiseDone - tempRaise[playersIndex]))
                            preFlopLifeData.postValue(
                                TurnState.Call(
                                    playersIndex,
                                    raiseDone,
                                    players[playersIndex].chips
                                )
                            )
                            delay(1000)
                            pot += (raiseDone - tempRaise[playersIndex])
                            preFlopLifeData.postValue(TurnState.Pot(pot))
                            delay(1000)
                            chips = setCorrectChipsPre(players)
                            preFlopLifeData.postValue(TurnState.AllChips(chips))
                            delay(1000)
                            tempRaise[playersIndex] = raiseDone
                        } else {
                            fold()
                            preFlopLifeData.postValue(TurnState.Fold(playersIndex))
                            delay(1000)
                            folded++
                            inGame[playersIndex] = false
                            if (folded == 5) {
                                chips = notFolded(chips, players, inGame, pot)
                                preFlopLifeData.postValue(
                                    TurnState.Chips(
                                        playersIndex,
                                        players[playersIndex].chips
                                    )
                                )
                                delay(1000)
                                chips = setCorrectChipsPre(players)
                                isNExtRoundCalled = true
                                preFlopLifeData.postValue(
                                    TurnState.NewGame(
                                        chips
                                    )
                                )
                                delay(2000)
                            }
                        }
                } else {
                    if (callDoneCounter != 0) {
                        if (playablePreFlopRaise(
                                players[playersIndex].cart1,
                                players[playersIndex].cart2,
                                indexNumber,
                                pot,
                                raiseDoneCounter,
                                raiseDone,
                                callDoneCounter,
                                tempRaise[playersIndex] == raiseDone
                            )
                        ) {
                            raiseDone =
                                raise(
                                    players[playersIndex],
                                    (raiseDone * 8 / 3 - tempRaise[playersIndex])
                                )
                            preFlopLifeData.postValue(
                                TurnState.Raise(
                                    playersIndex,
                                    raiseDone + tempRaise[playersIndex],
                                    players[playersIndex].chips
                                )
                            )
                            delay(1000)
                            chips = setCorrectChipsPre(players)
                            preFlopLifeData.postValue(TurnState.AllChips(chips))
                            delay(1000)
                            pot += raiseDone
                            preFlopLifeData.postValue(TurnState.Pot(pot))
                            delay(1000)
                            raiseDone += tempRaise[playersIndex]
                            lastRaise = lastRaiseCheck(lastRaise, playersIndex)
                            raiseDoneCounter += 1
                            tempRaise[playersIndex] = raiseDone

                        } else {
                            check()
                            preFlopLifeData.postValue(TurnState.Check(playersIndex))
                            delay(1000)
                        }
                    }
                }
        }
        if (!isNExtRoundCalled) {
            chips = setCorrectChipsPre(players)
            preFlopLifeData.postValue(
                TurnState.NextLoop(
                    preFlopLifeData, indexNumber
                )
            )
            delay(2000)
        }
    }

    private fun setCorrectChipsPre(player: List<Player>): List<Int> {
        val chipsList = mutableListOf<Int>()
        for (i in player.indices) {
            chipsList.add(player[i].chips)
        }
        return chipsList
    }
}
private fun notNormalRaisePreFlop(pot: Int, raise: Int, call: Int){
    val tempPot = pot - call*raise
    var persent = tempPot - raise
    persent = raise / persent
    if (persent in 4..8) {
        playerNotNormalRaise++
    }
}

private fun wrongRaisePreFlop(pot: Int, raise: Int, call: Int){
    val tempPot = pot - call*raise
    var persent = tempPot - raise
    if (persent == 0) {
        ++persent
    }
    persent = raise / persent
    if (persent > 8) {
        playerWrongRaise++
    }
}