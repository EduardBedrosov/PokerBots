package com.example.poker.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.poker.databinding.FragmentBaseBinding



class BaseFragment : Fragment() {
    private lateinit var binding: FragmentBaseBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBaseBinding.inflate(inflater, container, false)
        binding.startGameButton.setOnClickListener {
            it.findNavController().navigate(BaseFragmentDirections.
                actionBaseFragmentToGameFragment(
                    account = binding.editTextNickName.text.toString())
            )
        }

        return binding.root
    }
}