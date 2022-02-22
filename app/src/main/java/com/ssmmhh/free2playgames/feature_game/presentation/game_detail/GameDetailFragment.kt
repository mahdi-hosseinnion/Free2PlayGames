package com.ssmmhh.free2playgames.feature_game.presentation.game_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ssmmhh.free2playgames.databinding.FragmentGameDetailBinding
import com.ssmmhh.free2playgames.feature_game.presentation.game_detail.viewstate.GameDetailViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GameDetailFragment : Fragment() {

    private val viewModel by viewModels<GameDetailViewModel>()
    private var _binding: FragmentGameDetailBinding? = null
    private val binding get() = _binding!!

    private fun setupUi() {

    }

    private fun subscribeCollectors() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.gameDetailViewState.collect {
                    handleGameDetailViewState(it)
                }
            }
        }
    }

    private fun handleGameDetailViewState(vs: GameDetailViewState) {

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        subscribeCollectors()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}