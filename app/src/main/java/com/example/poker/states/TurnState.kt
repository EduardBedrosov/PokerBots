package com.example.poker.states

import androidx.lifecycle.MutableLiveData
import com.example.poker.logic.turn.Cards
import com.example.poker.methods.Player

sealed class TurnState : BaseState {

    data class Fold(val playerNumber: Int) : TurnState()
    data class Check(val playerNumber: Int) : TurnState()
    data class Call(val playerNumber: Int, val raiseCount: Int, val playersChips: Int) : TurnState()
    data class Raise(val playerNumber: Int, val raiseCount: Int, val playersChips: Int) :
        TurnState()

    data class Pot(val pot: Int) : TurnState()
    data class Chips(val playerNumber: Int, val chip: Int) : TurnState()
    data class StartGameVisibilityDefault(val playerNumber: Int) : TurnState()
    data class StartFlopVisibility(val playerNumber: Int) : TurnState()
    data class StartTurnVisibility(val playerNumber: Int): TurnState()
    data class StartRiverVisibility(val playerNumber: Int) : TurnState()
    data class DealerAndPlayerCards(
        val playerNumber: Int,
        val deck: List<Cards>,
        val card1: Cards,
        val card2: Cards,
        val card3: Cards,
        val card4: Cards,
        val card5: Cards,
        val card6: Cards,
        val card7: Cards,
        val card8: Cards,
        val card9: Cards,
        val card10: Cards,
        val card11: Cards,
        val card12: Cards

    ) : TurnState()

    data class AllChips(val chips: List<Int>) : TurnState()

    data class UserGamePlay(
        val state: Int, val indexNumber: Int,
        val turnLiveData: MutableLiveData<BaseState>,
        val raiseCount: Int
    ) : TurnState()

    data class NextPlayer(
        val indexNumber: Int, val turnLiveData: MutableLiveData<BaseState>,
        val IsBigBlind: Boolean
    ) : TurnState()

    data class NextLoop(
        val turnLiveData: MutableLiveData<BaseState>,
        val indexNumber: Int
    ) : TurnState()

    data class NextPlayerFlop(
        val indexNumber: Int, val turnLiveData: MutableLiveData<BaseState>
    ) : TurnState()

    data class DeckFlop(val deck: List<Cards>) : TurnState()
    data class DeckTurn(val card: Cards) : TurnState()
    data class DeckRiver(val card: Cards) : TurnState()

    data class NextLoopFlop(
        val turnLiveData: MutableLiveData<BaseState>,
        val indexNumber: Int
    ) : TurnState()

    data class NextPlayerTurn(
        val indexNumber: Int, val turnLiveData: MutableLiveData<BaseState>
    ) : TurnState()

    data class NextLoopTurn(
        val turnLiveData: MutableLiveData<BaseState>,
        val indexNumber: Int
    ) : TurnState()

    data class NextPlayerRiver(
        val indexNumber: Int, val turnLiveData: MutableLiveData<BaseState>
    ) : TurnState()

    data class NextLoopRiver(
        val turnLiveData: MutableLiveData<BaseState>,
        val indexNumber: Int
    ) : TurnState()

    data class NewGame(val chipsList: List<Int>) : TurnState()

    data class Ending(val players: List<Player>, val inGame: List<Boolean>, val chips: List<Int>,
                      val pot: Int, val winners: Map<Int, String>) : TurnState()
}