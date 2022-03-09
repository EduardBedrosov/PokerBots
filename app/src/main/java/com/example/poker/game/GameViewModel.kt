package com.example.poker.game

//import com.example.poker.logic.flop.flopGamePlay
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.poker.states.BaseState
import com.example.poker.logic.flop.FlopGamePlay
import com.example.poker.logic.flop.RiverGamePlay
import com.example.poker.logic.flop.TurnGamePlay
import com.example.poker.logic.preflop.PreFlopGamePlay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class GameViewModel() : ViewModel() {


    val turnLiveData = MutableLiveData<BaseState>()
    private var preFlop = PreFlopGamePlay()
    private lateinit var flop: FlopGamePlay
    private lateinit var turn: TurnGamePlay
    private lateinit var river: RiverGamePlay


    private var job: Job? = null

    fun startNewGame(
        chips: List<Int> = mutableListOf(
            1500000, 1500000, 1500000, 1500000, 1500000, 1500000
        )
    ) {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.Default) {
            preFlop = PreFlopGamePlay(chips)
            preFlop.initPreFlopGame(turnLiveData)
            preFlop.preFlopLoop(
                turnLiveData
            )
        }
    }

    fun startBotsTurn(
        indexNumber: Int, turnLiveData: MutableLiveData<BaseState>
    ) {
        viewModelScope.launch(Dispatchers.Default) {
            preFlop.botsTurn(indexNumber, turnLiveData)
        }
    }

    fun startBotsTurnBigBlind(
        indexNumber: Int, turnLiveData: MutableLiveData<BaseState>
    ) {
        viewModelScope.launch(Dispatchers.Default) {
            preFlop.botsTurnBigBlind(indexNumber, turnLiveData)
        }
    }

    fun startBotsTurnOnFlop(
        indexNumber: Int, flopLiveData: MutableLiveData<BaseState>
    ) {
        viewModelScope.launch(Dispatchers.Default) {
            flop.botsTurnFlop(indexNumber, flopLiveData)
        }
    }

    fun startBotsTurnOnTurn(
        indexNumber: Int, turnLiveData: MutableLiveData<BaseState>
    ) {
        viewModelScope.launch(Dispatchers.Default) {
            turn.botsTurnOnTurn(indexNumber, turnLiveData)
        }
    }

    fun startBotsTurnOnRiver(
        indexNumber: Int, riverLiveData: MutableLiveData<BaseState>
    ) {
        viewModelScope.launch(Dispatchers.Default) {
            river.botsTurnOnRiver(indexNumber, riverLiveData)
        }
    }

    fun nextLoop(
        turnLiveData: MutableLiveData<BaseState>, indexNumber: Int
    ) {
        if (preFlop.moveToFlopPlay()) {

            job?.cancel()
            job = viewModelScope.launch(Dispatchers.Default) {
                flop = FlopGamePlay(
                    preFlop.players, preFlop.deck, preFlop.inGame,
                    preFlop.lastRaise, preFlop.pot
                )
                flop.initFlopGame(turnLiveData)
                flop.flopLoop(turnLiveData)
            }

        } else {
            viewModelScope.launch(Dispatchers.Default) {
                preFlop.preFlopLoop(turnLiveData, (indexNumber + 1))
            }
        }
    }

    fun nextLoopFlop(
        turnLiveData: MutableLiveData<BaseState>, indexNumber: Int
    ) {
        if (flop.moveToTurnPlay()) {
            job?.cancel()
            job = viewModelScope.launch(Dispatchers.Default) {
                turn = TurnGamePlay(
                    flop.player, flop.deck, flop.inGame,
                    flop.lastRaise, flop.pot
                )
                turn.initTurnGame(turnLiveData)
                turn.turnLoop(turnLiveData)
            }
        } else {
            viewModelScope.launch(Dispatchers.Default) {
                flop.flopLoop(turnLiveData, (indexNumber + 1))
            }
        }
    }

    fun nextLoopTurn(
        turnLiveData: MutableLiveData<BaseState>, indexNumber: Int
    ) {
        if (turn.moveToRiverPlay()) {
            job?.cancel()
            job = viewModelScope.launch(Dispatchers.Default) {
                river = RiverGamePlay(
                    turn.player, turn.deck, turn.inGame,
                    turn.lastRaise, turn.pot
                )
                river.initRiverGame(turnLiveData)
                river.riverLoop(turnLiveData)
            }
        } else {
            viewModelScope.launch(Dispatchers.Default) {
                turn.turnLoop(turnLiveData, (indexNumber + 1))
            }
        }
    }

    fun nextLoopRiver(
        turnLiveData: MutableLiveData<BaseState>, indexNumber: Int
    ) {
        if (river.moveToEndingPlay()) {
            job?.cancel()
            job = viewModelScope.launch(Dispatchers.Default) {
                river.ending(turnLiveData)
            }
        } else {
            viewModelScope.launch(Dispatchers.Default) {
                river.riverLoop(turnLiveData, (indexNumber + 1))
            }
        }
    }

    fun playerFold(indexNumber: Int, turnLiveData: MutableLiveData<BaseState>) {
        viewModelScope.launch(Dispatchers.Default) {
            preFlop.playersFold(indexNumber, turnLiveData)
        }
    }

    fun playerCall(indexNumber: Int, turnLiveData: MutableLiveData<BaseState>) {
        viewModelScope.launch(Dispatchers.Default) {
            preFlop.playersCall(indexNumber, turnLiveData)
        }
    }

    fun playerRaise(indexNumber: Int, turnLiveData: MutableLiveData<BaseState>, raiseSize: Int) {
        viewModelScope.launch(Dispatchers.Default) {
            preFlop.playersRaise(indexNumber, turnLiveData, raiseSize)
        }
    }

    fun playerFoldFlop(indexNumber: Int, turnLiveData: MutableLiveData<BaseState>) {
        viewModelScope.launch(Dispatchers.Default) {
            flop.playersFoldFlop(indexNumber, turnLiveData)
        }
    }
    fun playersCheckFlop(indexNumber: Int, turnLiveData: MutableLiveData<BaseState>) {
        viewModelScope.launch(Dispatchers.Default) {
            flop.playersCheckFlop(indexNumber, turnLiveData)
        }
    }
    fun playerCallFlop(indexNumber: Int, turnLiveData: MutableLiveData<BaseState>) {
        viewModelScope.launch(Dispatchers.Default) {
            flop.playersCallFlop(indexNumber, turnLiveData)
        }
    }

    fun playerRaiseFlop(
        indexNumber: Int,
        turnLiveData: MutableLiveData<BaseState>,
        raiseSize: Int
    ) {
        viewModelScope.launch(Dispatchers.Default) {
            flop.playersRaiseFlop(indexNumber, turnLiveData, raiseSize)
        }
    }

    fun playerFoldTurn(indexNumber: Int, turnLiveData: MutableLiveData<BaseState>) {
        viewModelScope.launch(Dispatchers.Default) {
            turn.playersFoldTurn(indexNumber, turnLiveData)
        }
    }
    fun playersCheckTurn(indexNumber: Int, turnLiveData: MutableLiveData<BaseState>) {
        viewModelScope.launch(Dispatchers.Default) {
            turn.playersCheckTurn(indexNumber, turnLiveData)
        }
    }
    fun playerCallTurn(indexNumber: Int, turnLiveData: MutableLiveData<BaseState>) {
        viewModelScope.launch(Dispatchers.Default) {
            turn.playersCallTurn(indexNumber, turnLiveData)
        }
    }

    fun playerRaiseTurn(
        indexNumber: Int,
        turnLiveData: MutableLiveData<BaseState>,
        raiseSize: Int
    ) {
        viewModelScope.launch(Dispatchers.Default) {
            turn.playersRaiseTurn(indexNumber, turnLiveData, raiseSize)
        }
    }

    fun playerFoldRiver(indexNumber: Int, turnLiveData: MutableLiveData<BaseState>) {
        viewModelScope.launch(Dispatchers.Default) {
            river.playersFoldRiver(indexNumber, turnLiveData)
        }
    }
    fun playersCheckRiver(indexNumber: Int, turnLiveData: MutableLiveData<BaseState>) {
        viewModelScope.launch(Dispatchers.Default) {
            river.playersCheckRiver(indexNumber, turnLiveData)
        }
    }
    fun playerCallRiver(indexNumber: Int, turnLiveData: MutableLiveData<BaseState>) {
        viewModelScope.launch(Dispatchers.Default) {
            river.playersCallRiver(indexNumber, turnLiveData)
        }
    }

    fun playerRaiseRiver(
        indexNumber: Int,
        turnLiveData: MutableLiveData<BaseState>,
        raiseSize: Int
    ) {
        viewModelScope.launch(Dispatchers.Default) {
            river.playersRaiseRiver(indexNumber, turnLiveData, raiseSize)
        }
    }
}


