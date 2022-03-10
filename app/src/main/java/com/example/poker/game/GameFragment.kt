package com.example.poker.game

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.poker.R
import com.example.poker.databinding.FragmentGameBinding
import com.example.poker.logic.bigBlind
import com.example.poker.logic.mapToKeyList
import com.example.poker.logic.mapToValuesList
import com.example.poker.methods.setTextToList
import com.example.poker.methods.setToPlayerOfNumber
import com.example.poker.states.TurnState
import kotlinx.coroutines.*


class GameFragment : Fragment() {

    private lateinit var viewModel: GameViewModel
    private lateinit var binding: FragmentGameBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentGameBinding.inflate(inflater, container, false)
        val userName = GameFragmentArgs.fromBundle(requireArguments())
        binding.tvBot0Name.text = userName.account.toString()

//        try {
//            this.requireActivity().actionBar!!.hide()
//        }
//        catch (e: NullPointerException) {
//        }

//        var p1 = Cards(1,"erku" ,"hearts" , 0)
//        var p2 = Cards(10,"erku" ,"heaaarts" , 0)
//        var p3 = Cards(4,"erku" ,"heaarts" , 0)
//        var p4 = Cards(6,"erku" ,"heaaarts" , 0)
//        var p5 = Cards(10,"erku" ,"heaadrats" , 0)
//        var p6 = Cards(5,"erku" ,"heartas" , 0)
//        var p7 = Cards(12,"erku" ,"heaarts" , 0)
//
//        val deck1 = listOf<Cards>(p3,p4,p5,p6,p7)
//
//        val card1WithFixedId = if (p1.id < 13) {
//            p1.id
//        } else
//            ((p1.id) % 13)
//
//        val card2WithFixedId = if (p2.id < 13) {
//            p2.id
//        } else
//            ((p2.id) % 13)
//
//        val deckFixed = deckIdFix(deck1)
//
//
//        println(deck1)
//        println(deckFixed)
//        println(card1WithFixedId)
//        println(card2WithFixedId)
//
//        val y = combination(p1,p2,deck1)
//        println(y)
//
//        val x = combination(player[0].cart1, player[0].cart2, deck)
//        println(x)
////
//        println(deck)
//        println(player)
//        val tempPot = 500
//        val inGame = mutableListOf(
//            false, true, true, true, true, true
//        )
//
//        val lastRaise = mutableListOf(
//            false, false, false, false, true, false
//        )
//
//       preFlopGamePlay(player,turnLiveData)
//        flopGamePlay(player,deck,inGame,lastRaise,tempPot,turnLiveData)
//        turnGamePlay(player,deck,inGame,lastRaise,tempPot,turnLiveData)
//        riverGamePlay(player,deck,inGame,lastRaise,tempPot,turnLiveData)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chipsViewList = setTextToList(
            binding.tvBot0ChipCount, binding.tvBot1ChipCount,
            binding.tvBot2ChipCount, binding.tvBot3ChipCount,
            binding.tvBot4ChipCount, binding.tvBot5ChipCount
        )

        val deckString = listOf(
            binding.tvDeck0,
            binding.tvDeck1,
            binding.tvDeck2,
            binding.tvDeck3,
            binding.tvDeck4
        )

        val raiseTextViewList = setTextToList(
            binding.tvRaiseBotO, binding.tvRaiseBot1,
            binding.tvRaiseBot2, binding.tvRaiseBot3, binding.tvRaiseBot4, binding.tvRaiseBot5
        )

        val raiseImageViewList = setTextToList(
            binding.ivRaiseBot0, binding.ivRaiseBot1,
            binding.ivRaiseBot2, binding.ivRaiseBot3, binding.ivRaiseBot4, binding.ivRaiseBot5
        )

        val checkedImageViewList = setTextToList(
            binding.ivCheckBot0, binding.ivCheckBot1,
            binding.ivCheckBot2, binding.ivCheckBot3, binding.ivCheckBot4, binding.ivCheckBot5
        )
        val botNameList = setTextToList(
            binding.tvBot0Name, binding.tvBot1Name,
            binding.tvBot2Name, binding.tvBot3Name, binding.tvBot4Name, binding.tvBot5Name
        )
        val checkedTextViewList = setTextToList(
            binding.tvCheckBotO, binding.tvCheckBot1,
            binding.tvCheckBot2, binding.tvCheckBot3, binding.tvCheckBot4, binding.tvCheckBot5
        )

        val foldImageViewList = setTextToList(
            binding.ivFoldBot0, binding.ivFoldBot1,
            binding.ivFoldBot2, binding.ivFoldBot3, binding.ivFoldBot4, binding.ivFoldBot5
        )
        val foldTextViewList = setTextToList(
            binding.tvFoldBotO, binding.tvFoldBot1,
            binding.tvFoldBot2, binding.tvFoldBot3, binding.tvFoldBot4, binding.tvFoldBot5
        )


        val card1Image = setTextToList(
            binding.tvBot0Card1, binding.tvBot1Card1,
            binding.tvBot2Card1, binding.tvBot3Card1, binding.tvBot4Card1, binding.tvBot5Card1
        )

        val card2Image = setTextToList(
            binding.tvBot0Card2, binding.tvBot1Card2,
            binding.tvBot2Card2, binding.tvBot3Card2, binding.tvBot4Card2, binding.tvBot5Card2
        )

        val button = setTextToList(
            binding.ivButtonBot0, binding.ivButtonBot1,
            binding.ivButtonBot2, binding.ivButtonBot3, binding.ivButtonBot4, binding.ivButtonBot5
        )

        val frameRed = setTextToList(
            binding.ivBot0FrameHighlightRed,binding.ivBot1FrameHighlightRed,binding.ivBot2FrameHighlightRed,
            binding.ivBot3FrameHighlightRed,binding.ivBot4FrameHighlightRed,binding.ivBot5FrameHighlightRed
        )
        val frameGreen = setTextToList(
            binding.ivBot0FrameHighlightGreen,binding.ivBot1FrameHighlightGreen,binding.ivBot2FrameHighlightGreen,
            binding.ivBot3FrameHighlightGreen,binding.ivBot4FrameHighlightGreen,binding.ivBot5FrameHighlightGreen
        )

        val potText = binding.potSize
        val foldButton = binding.playerFoldButton
        val callButton = binding.playerCallButton
        val raiseButton = binding.playerRaiseButton
        val checkButton = binding.playerCheckButton

        viewModel = ViewModelProvider(this)[GameViewModel::class.java]

        viewModel.turnLiveData.observe(
            viewLifecycleOwner,
            {
                when (it) {
                    is TurnState.StartGameVisibilityDefault -> {
                        for (i in 0..5) {

                            frameRed[i].visibility = INVISIBLE
                            frameGreen[i].visibility = INVISIBLE
                            button[i].visibility = INVISIBLE
                            checkedTextViewList[i].visibility = INVISIBLE
                            foldTextViewList[i].visibility = INVISIBLE
                            foldImageViewList[i].visibility = INVISIBLE
                            checkedImageViewList[i].visibility = INVISIBLE
                            raiseImageViewList[i].visibility = INVISIBLE
                            raiseTextViewList[i].visibility = INVISIBLE
                            chipsViewList[i].visibility = View.VISIBLE
                            card1Image[i].setImageResource(R.drawable.ic__back_of_card)
                            card2Image[i].setImageResource(R.drawable.ic__back_of_card)
                            card1Image[i].visibility = View.VISIBLE
                            card2Image[i].visibility = View.VISIBLE
                        }
                        for (i in 0..4) {
                            deckString[i].setImageResource(R.drawable.ic__back_of_card)
                            deckString[i].visibility = View.INVISIBLE
                        }
                        potText.visibility = INVISIBLE
                        foldButton.visibility = INVISIBLE
                        callButton.visibility = INVISIBLE
                        raiseButton.visibility = INVISIBLE
                        checkButton.visibility = INVISIBLE
                    }

                    is TurnState.DealerAndPlayerCards -> {
                        setToPlayerOfNumber(it.playerNumber, button).visibility =
                            View.VISIBLE
                        binding.apply {
                            tvBot0Card1.setImageResource(it.card1.cardImage)
                            tvBot0Card2.setImageResource(it.card2.cardImage)
                            tvBot0Card1.visibility = View.VISIBLE
                            tvBot0Card2.visibility = View.VISIBLE

                            tvDeck0.setImageResource(it.deck0.cardImage)
                            tvDeck1.setImageResource(it.deck1.cardImage)
                            tvDeck2.setImageResource(it.deck2.cardImage)
                            tvDeck3.setImageResource(it.deck3.cardImage)
                            tvDeck4.setImageResource(it.deck4.cardImage)
                        }
//                        for (i in deckString.indices) {
//                            deckString[i].setImageResource(it.deck[i].cardImage)
//                        }
                    }
                    is TurnState.StartFlopVisibility -> {
                        foldButton.visibility = INVISIBLE
                        callButton.visibility = INVISIBLE
                        raiseButton.visibility = INVISIBLE
                        checkButton.visibility = INVISIBLE
                        binding.tvDeck0.visibility = View.VISIBLE
                        binding.tvDeck1.visibility = View.VISIBLE
                        binding.tvDeck2.visibility = View.VISIBLE
                        for (i in 0..5) {
                            foldImageViewList[i].visibility = INVISIBLE
                            checkedImageViewList[i].visibility = INVISIBLE
                            raiseImageViewList[i].visibility = INVISIBLE
                            raiseTextViewList[i].visibility = INVISIBLE
                            checkedTextViewList[i].visibility = INVISIBLE
                            foldTextViewList[i].visibility = INVISIBLE
                        }
                    }

                    is TurnState.StartTurnVisibility -> {
                        foldButton.visibility = INVISIBLE
                        callButton.visibility = INVISIBLE
                        raiseButton.visibility = INVISIBLE
                        checkButton.visibility = INVISIBLE
                        binding.tvDeck3.visibility = View.VISIBLE

                        for (i in 0..5) {
                            foldImageViewList[i].visibility = INVISIBLE
                            checkedImageViewList[i].visibility = INVISIBLE
                            raiseImageViewList[i].visibility = INVISIBLE
                            raiseTextViewList[i].visibility = INVISIBLE
                            checkedTextViewList[i].visibility = INVISIBLE
                            foldTextViewList[i].visibility = INVISIBLE
                        }
                    }
                    is TurnState.StartRiverVisibility -> {
                        foldButton.visibility = INVISIBLE
                        callButton.visibility = INVISIBLE
                        raiseButton.visibility = INVISIBLE
                        checkButton.visibility = INVISIBLE
                        binding.tvDeck4.visibility = View.VISIBLE
                        for (i in 0..5) {
                            foldImageViewList[i].visibility = INVISIBLE
                            checkedImageViewList[i].visibility = INVISIBLE
                            raiseImageViewList[i].visibility = INVISIBLE
                            raiseTextViewList[i].visibility = INVISIBLE
                            checkedTextViewList[i].visibility = INVISIBLE
                            foldTextViewList[i].visibility = INVISIBLE
                        }
                    }
                    is TurnState.Chips -> {
                        setToPlayerOfNumber(it.playerNumber, chipsViewList).text =
                            it.chip.toString()
                    }
                    is TurnState.AllChips -> {
                        for (i in chipsViewList.indices) {
                            chipsViewList[i].text = it.chips[i].toString()
                        }
                    }
                    is TurnState.Pot -> {
                        binding.potSize.visibility = View.VISIBLE
                        binding.potSize.text = it.pot.toString()

                    }
                    is TurnState.Raise -> {
                        for (i in 0..5) {
                            frameGreen[i].visibility = INVISIBLE
                        }
                        setToPlayerOfNumber(it.playerNumber, frameGreen).visibility =
                            View.VISIBLE
                        setToPlayerOfNumber(it.playerNumber, checkedImageViewList).visibility =
                            View.INVISIBLE
                        setToPlayerOfNumber(it.playerNumber, checkedTextViewList).visibility =
                            View.INVISIBLE
                        setToPlayerOfNumber(it.playerNumber, raiseImageViewList).visibility =
                            View.VISIBLE
                        setToPlayerOfNumber(it.playerNumber, raiseTextViewList).text =
                            it.raiseCount.toString()
                        setToPlayerOfNumber(it.playerNumber, raiseTextViewList).visibility =
                            View.VISIBLE
                        setToPlayerOfNumber(it.playerNumber, chipsViewList).text =
                            it.playersChips.toString()
                    }
                    is TurnState.Fold -> {
                        for (i in 0..5) {
                            frameGreen[i].visibility = INVISIBLE
                        }
                        setToPlayerOfNumber(it.playerNumber, frameRed).visibility =
                            View.VISIBLE
                        card1Image[it.playerNumber].visibility = View.INVISIBLE
                        card2Image[it.playerNumber].visibility = View.INVISIBLE

                        setToPlayerOfNumber(it.playerNumber, checkedImageViewList).visibility =
                            View.INVISIBLE
                        setToPlayerOfNumber(it.playerNumber, checkedTextViewList).visibility =
                            View.INVISIBLE
                        setToPlayerOfNumber(it.playerNumber, raiseImageViewList).visibility =
                            View.INVISIBLE
                        setToPlayerOfNumber(it.playerNumber, raiseTextViewList).visibility =
                            INVISIBLE

                        setToPlayerOfNumber(it.playerNumber, foldImageViewList).visibility =
                            View.VISIBLE
                        setToPlayerOfNumber(it.playerNumber, foldTextViewList).visibility =
                            View.VISIBLE
                    }
                    is TurnState.Check -> {
                        for (i in 0..5) {
                            frameGreen[i].visibility = INVISIBLE
                        }
                        setToPlayerOfNumber(it.playerNumber, frameGreen).visibility =
                            View.VISIBLE
                        setToPlayerOfNumber(it.playerNumber, raiseImageViewList).visibility =
                            View.INVISIBLE
                        setToPlayerOfNumber(it.playerNumber, raiseTextViewList).visibility =
                            INVISIBLE
                        setToPlayerOfNumber(it.playerNumber, checkedImageViewList).visibility =
                            View.VISIBLE
                        setToPlayerOfNumber(it.playerNumber, checkedTextViewList).visibility =
                            View.VISIBLE
                    }
                    is TurnState.Call -> {
                        for (i in 0..5) {
                            frameGreen[i].visibility = INVISIBLE
                        }
                        setToPlayerOfNumber(it.playerNumber, frameGreen).visibility =
                            View.VISIBLE
                        setToPlayerOfNumber(it.playerNumber, checkedImageViewList).visibility =
                            View.INVISIBLE
                        setToPlayerOfNumber(it.playerNumber, checkedTextViewList).visibility =
                            View.INVISIBLE
                        setToPlayerOfNumber(it.playerNumber, raiseImageViewList).visibility =
                            View.VISIBLE
                        setToPlayerOfNumber(it.playerNumber, raiseTextViewList).text =
                            it.raiseCount.toString()
                        setToPlayerOfNumber(it.playerNumber, raiseTextViewList).visibility =
                            View.VISIBLE
                        setToPlayerOfNumber(it.playerNumber, chipsViewList).text =
                            it.playersChips.toString()
                    }
                    is TurnState.UserGamePlay -> {
                        val state = it.state
                        val indexNumber = it.indexNumber
                        val turnLiveData = it.turnLiveData
                        val raiseDefault = it.raiseCount
                        for (i in 0..5) {
                            frameGreen[i].visibility = INVISIBLE
                        }
                        setToPlayerOfNumber(0, frameGreen).visibility =
                            View.VISIBLE
                        binding.apply {
                            raiseSlider.visibility = View.VISIBLE
                            playerRaiseButton.visibility = View.VISIBLE
                            playerRaiseButton.isEnabled = true
                            playerFoldButton.visibility = View.VISIBLE
                            playerFoldButton.isEnabled = true
                        }
                        if (raiseDefault == 0) {
                            binding.playerCheckButton.visibility = View.VISIBLE
                            binding.playerCheckButton.isEnabled = true
                        } else {
                            binding.playerCallButton.visibility = View.VISIBLE
                            binding.playerCallButton.isEnabled = true
                        }
                        binding.playerFoldButton.setOneTimeClickListener {
                            binding.playerFoldButton.visibility = View.GONE
                            binding.playerCallButton.visibility = View.GONE
                            binding.playerCheckButton.visibility = View.GONE
                            binding.playerRaiseButton.visibility = View.GONE
                            binding.raiseSlider.visibility = View.GONE
                            for (i in 0..5) {
                                frameGreen[i].visibility = INVISIBLE
                            }
                            setToPlayerOfNumber(0, frameRed).visibility =
                                View.VISIBLE
                            when (state) {
                                1 -> {
                                    viewModel.playerFold(indexNumber, turnLiveData)
                                }
                                2 -> {
                                    viewModel.playerFoldFlop(indexNumber, turnLiveData)
                                }
                                3 -> {
                                    viewModel.playerFoldTurn(indexNumber, turnLiveData)
                                }
                                4 -> {
                                    viewModel.playerFoldRiver(indexNumber, turnLiveData)
                                }
                            }
                        }
                        binding.playerCheckButton.setOneTimeClickListener {
                            binding.playerFoldButton.visibility = View.GONE
                            binding.playerCallButton.visibility = View.GONE
                            binding.playerCheckButton.visibility = View.GONE
                            binding.playerRaiseButton.visibility = View.GONE
                            binding.raiseSlider.visibility = View.GONE
                            when (state) {
                                2 -> {
                                    viewModel.playersCheckFlop(indexNumber, turnLiveData)
                                }
                                3 -> {
                                    viewModel.playersCheckTurn(indexNumber, turnLiveData)
                                }
                                4 -> {
                                    viewModel.playersCheckRiver(indexNumber, turnLiveData)
                                }
                            }
                        }
                        binding.playerCallButton.setOneTimeClickListener {
                            binding.playerFoldButton.visibility = View.GONE
                            binding.playerCallButton.visibility = View.GONE
                            binding.playerCheckButton.visibility = View.GONE
                            binding.playerRaiseButton.visibility = View.GONE
                            binding.raiseSlider.visibility = View.GONE
                            when (state) {
                                1 -> {
                                    viewModel.playerCall(indexNumber, turnLiveData)
                                }
                                2 -> {
                                    viewModel.playerCallFlop(indexNumber, turnLiveData)
                                }
                                3 -> {
                                    viewModel.playerCallTurn(indexNumber, turnLiveData)
                                }
                                4 -> {
                                    viewModel.playerCallRiver(indexNumber, turnLiveData)
                                }
                            }
                        }
                        binding.playerRaiseButton.setOneTimeClickListener {

                            if (binding.raiseSlider.length() > 0) {
                                //  senc toInt sarqelu tegh@ inch en anum voobshe ?
                                val raiseSliderText = binding.raiseSlider.text.toString()
                                val raiseSliderInt: Int = raiseSliderText.toInt()
                                hideSoftKeyboard(this.requireActivity())

                                if ((binding.raiseSlider.length() < 1) || (raiseSliderInt == 0)) {
                                    binding.playerFoldButton.visibility = View.GONE
                                    binding.playerCallButton.visibility = View.GONE
                                    binding.playerCheckButton.visibility = View.GONE
                                    binding.playerRaiseButton.visibility = View.GONE
                                    binding.raiseSlider.visibility = View.GONE
                                    when (state) {
                                        1 -> {
                                            if (raiseDefault == bigBlind) {
                                                viewModel.playerRaise(
                                                    indexNumber,
                                                    turnLiveData,
                                                    bigBlind * 2
                                                )
                                            } else {
                                                viewModel.playerRaise(
                                                    indexNumber,
                                                    turnLiveData,
                                                    raiseDefault * 2
                                                )
                                            }
                                        }
                                        2 -> {
                                            if (raiseDefault == 0) {
                                                viewModel.playerRaiseFlop(
                                                    indexNumber,
                                                    turnLiveData,
                                                    bigBlind
                                                )
                                            } else {
                                                viewModel.playerRaiseFlop(
                                                    indexNumber,
                                                    turnLiveData,
                                                    raiseDefault * 2
                                                )
                                            }
                                        }
                                        3 -> {
                                            if (raiseDefault == 0) {
                                                viewModel.playerRaiseTurn(
                                                    indexNumber,
                                                    turnLiveData,
                                                    bigBlind
                                                )
                                            } else {
                                                viewModel.playerRaiseTurn(
                                                    indexNumber,
                                                    turnLiveData,
                                                    raiseDefault * 2
                                                )
                                            }
                                        }
                                        4 -> {
                                            if (raiseDefault == 0) {
                                                viewModel.playerRaiseRiver(
                                                    indexNumber,
                                                    turnLiveData,
                                                    bigBlind
                                                )
                                            } else {
                                                viewModel.playerRaiseRiver(
                                                    indexNumber,
                                                    turnLiveData,
                                                    raiseDefault * 2
                                                )
                                            }
                                        }
                                    }
                                } else if (raiseSliderInt < raiseDefault * 2) {
                                    if (raiseSliderInt != 0) {
                                        Toast.makeText(
                                            activity,
                                            "CAN'T RAISE LESS DOUBLE",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                } else if (raiseSliderInt >= raiseDefault * 2) {
                                    binding.playerFoldButton.visibility = View.GONE
                                    binding.playerCallButton.visibility = View.GONE
                                    binding.playerCheckButton.visibility = View.GONE
                                    binding.playerRaiseButton.visibility = View.GONE
                                    binding.raiseSlider.visibility = View.GONE
                                    when (state) {
                                        1 -> {
                                            viewModel.playerRaise(
                                                indexNumber,
                                                turnLiveData,
                                                raiseSliderInt
                                            )
                                        }
                                        2 -> {
                                            viewModel.playerRaiseFlop(
                                                indexNumber,
                                                turnLiveData,
                                                raiseSliderInt
                                            )
                                        }
                                        3 -> {
                                            viewModel.playerRaiseTurn(
                                                indexNumber,
                                                turnLiveData,
                                                raiseSliderInt
                                            )
                                        }
                                        4 -> {
                                            viewModel.playerRaiseRiver(
                                                indexNumber,
                                                turnLiveData,
                                                raiseSliderInt
                                            )
                                        }
                                    }
                                }
                                binding.raiseSlider.setText("0")
                            } else {
                                Toast.makeText(
                                    activity,
                                    "How Much You Want To Raise ?",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                    is TurnState.NextPlayer -> {
                        if (it.IsBigBlind) {
                            viewModel.startBotsTurnBigBlind(it.indexNumber, it.turnLiveData)
                        } else
                            viewModel.startBotsTurn(it.indexNumber, it.turnLiveData)
                    }
                    is TurnState.NextPlayerFlop -> {
                        viewModel.startBotsTurnOnFlop(it.indexNumber, it.turnLiveData)
                    }

                    is TurnState.NextPlayerTurn -> {
                        viewModel.startBotsTurnOnTurn(it.indexNumber, it.turnLiveData)
                    }
                    is TurnState.NextPlayerRiver -> {
                        viewModel.startBotsTurnOnRiver(it.indexNumber, it.turnLiveData)
                    }

                    is TurnState.NextLoop -> {
                        viewModel.nextLoop(it.turnLiveData, it.indexNumber)
                    }
                    is TurnState.NextLoopFlop -> {
                        viewModel.nextLoopFlop(it.turnLiveData, it.indexNumber)
                    }
                    is TurnState.NextLoopTurn -> {
                        viewModel.nextLoopTurn(it.turnLiveData, it.indexNumber)
                    }
                    is TurnState.NextLoopRiver -> {
                        viewModel.nextLoopRiver(it.turnLiveData, it.indexNumber)
                    }
                    is TurnState.NewGame -> {
                        viewModel.startNewGame(it.chipsList)
                    }
                    is TurnState.Ending -> {
                        val chips = it.chips
                        val pot = it.pot
                        val players = it.players
                        val winners = it.winners
                        val inGame = it.inGame
                        val winnersIndexes = mapToKeyList(winners)
                        val winnerCombo = mapToValuesList(winners)
                        CoroutineScope(Dispatchers.Main).launch {
                            for(i in inGame.indices){
                                if (inGame[i]){
                                    card1Image[i].setImageResource(players[i].cart1.cardImage)
                                    card2Image[i].setImageResource(players[i].cart2.cardImage)
                                }
                            }
                            for (i in winnersIndexes.indices) {
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(
                                        activity,
                                        "WINNER IS",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    delay(1000)
                                }
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(
                                        activity,
                                        botNameList[winnersIndexes[i]].text.toString(),
//                                        winnersIndexes[i].toString(),
                                        Toast.LENGTH_LONG
                                    )
                                        .show()
                                    delay(1000)
                                }
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(
                                        activity,
                                        "WITH COMBO",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    delay(1000)
                                }

                                delay(5000)

                                withContext(Dispatchers.Main) {
                                    Toast.makeText(
                                        activity,
                                        winnerCombo[i].toString(),
                                        Toast.LENGTH_LONG
                                    )
                                        .show()
                                    delay(5000)
                                }
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(activity, (pot/winnersIndexes.size).toString(), Toast.LENGTH_LONG)
                                        .show()
                                    delay(1000)
                                }
                                if(winnersIndexes.size>1){
                                    withContext(Dispatchers.Main) {
                                        Toast.makeText(
                                            activity,
                                            "And Also",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        delay(1000)
                                    }
                                }
                            }
                            viewModel.startNewGame(chips)
                        }
                    }
                }
            }
        )
        val startingChips = mutableListOf(
            1500000, 1500000, 1500000, 1500000, 1500000, 1500000
        )

        viewModel.startNewGame(startingChips)

    }
}

private class OneTimeClickListener(private val action: (v: View) -> Unit) : View.OnClickListener {
    private val blockTime = 1000
    private var lastClick: Long = 0

    override fun onClick(v: View) {
        val clickTime = System.currentTimeMillis()
        if (clickTime - blockTime > lastClick) {
            lastClick = clickTime
            action(v)
        }
    }
}

fun View.setOneTimeClickListener(listener: (v: View) -> Unit) {
    this.setOnClickListener(OneTimeClickListener(listener))
}

private fun hideSoftKeyboard(activity: Activity) {
    val inputMethodManager: InputMethodManager = activity.getSystemService(
        Activity.INPUT_METHOD_SERVICE
    ) as InputMethodManager
    if (inputMethodManager.isAcceptingText) {
        inputMethodManager.hideSoftInputFromWindow(
            activity.currentFocus!!.windowToken,
            0
        )
    }
}
//private fun userGamePreFlop(indexNumber: Int, turnLiveData: MutableLiveData<BaseState>, raiseCount: Int,
//binding: FragmentGameBinding, viewModel: GameViewModel){
//
//    val raiseDefault = raiseCount
//
//    binding.playerCallButton.visibility = View.VISIBLE
//    binding.playerRaiseButton.visibility = View.VISIBLE
//    binding.raiseSlider.visibility = View.VISIBLE
//    binding.playerFoldButton.visibility = View.VISIBLE
//
//    binding.playerFoldButton.setOnClickListener {
//        binding.playerFoldButton.visibility = View.GONE
//        binding.playerCallButton.visibility = View.GONE
//        binding.playerRaiseButton.visibility = View.GONE
//        binding.raiseSlider.visibility = View.GONE
//        viewModel.playerFold(indexNumber, turnLiveData)
//    }
//
//    binding.playerCallButton.setOnClickListener {
//        binding.playerFoldButton.visibility = View.GONE
//        binding.playerCallButton.visibility = View.GONE
//        binding.playerRaiseButton.visibility = View.GONE
//        binding.raiseSlider.visibility = View.GONE
//        viewModel.playerCall(indexNumber, turnLiveData)
//    }
//
//    binding.playerRaiseButton.setOnClickListener {
//        //kaxuma shat arag arag sxmeluc
//        binding.playerFoldButton.isClickable = false
//
//        val raiseSliderText = binding.raiseSlider.text.toString()
//        val raiseSliderInt = raiseSliderText.toInt()
//        hideSoftKeyboard(this.requireActivity())
//
//        if ((binding.raiseSlider.length() < 1) || (raiseSliderInt == 0)){
//            binding.playerFoldButton.isClickable = true
//            binding.playerFoldButton.visibility = View.GONE
//            binding.playerCallButton.visibility = View.GONE
//            binding.playerRaiseButton.visibility = View.GONE
//            binding.playerFoldButton.isClickable = false
//            binding.raiseSlider.visibility = View.GONE
//            viewModel.playerRaise(indexNumber, turnLiveData, raiseDefault*2)
//        }
//        if(raiseSliderInt < raiseDefault*2) {
//            CoroutineScope(Dispatchers.IO).launch {
//                delay(1000)
//                if(raiseSliderInt != 0) {
//                    withContext(Dispatchers.Main) {
//                        Toast.makeText(
//                            activity,
//                            "CAN'T RAISE LESS DOUBLE",
//                            Toast.LENGTH_LONG
//                        ).show()
//                    }
//                }
//            }
//        }else if (raiseSliderInt >= raiseDefault*2){
//            binding.playerFoldButton.isClickable = true
//            binding.playerFoldButton.visibility = View.GONE
//            binding.playerCallButton.visibility = View.GONE
//            binding.playerRaiseButton.visibility = View.GONE
//            binding.playerFoldButton.isClickable = false
//            binding.raiseSlider.visibility = View.GONE
//            viewModel.playerRaise(indexNumber, turnLiveData, raiseSliderInt)
//        }
////                            binding.playerRaiseButton.setText(0)
//    }
//}
