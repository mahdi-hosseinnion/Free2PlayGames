package com.ssmmhh.free2playgames.feature_game.presentation.games

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssmmhh.free2playgames.databinding.FragmentGamesBinding
import com.ssmmhh.free2playgames.feature_game.presentation.games.viewstate.GameListViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GamesFragment
    : Fragment() {

    private val gamesRecyclerViewAdapter = GameListRecyclerViewAdapter()

    private val viewModel by viewModels<GamesViewModel>()

    private var _binding: FragmentGamesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGamesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        subscribeCollectors()

    }

    private fun setupUI() {
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.recyclerGameList.apply {
            layoutManager = LinearLayoutManager(this@GamesFragment.requireContext())
            adapter = gamesRecyclerViewAdapter
        }
    }

    private fun subscribeCollectors() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.gameListViewState.collect {
                    handleRecyclerViewViewState(it)
                }
            }
        }
    }

    private fun handleRecyclerViewViewState(vs: GameListViewState) {
        if (vs.games.isNotEmpty()) {
            binding.txtGameListError.visibility = View.GONE
            binding.recyclerGameList.visibility = View.VISIBLE
            gamesRecyclerViewAdapter.submitList(vs.games)
        }
        //handle loading
        binding.prgGameList.visibility = if (vs.isLoading) {
            View.VISIBLE
        } else {
            View.GONE
        }
        //handle error
        if (!vs.errorMessage.isNullOrBlank()) {
            binding.txtGameListError.visibility = View.VISIBLE
            binding.recyclerGameList.visibility = View.GONE
            binding.txtGameListError.text = vs.errorMessage
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}