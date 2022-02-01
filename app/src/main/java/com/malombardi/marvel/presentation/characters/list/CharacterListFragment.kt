package com.malombardi.marvel.presentation.characters.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.malombardi.marvel.databinding.FragmentCharacterListBinding
import com.malombardi.marvel.presentation.characters.CharactersActivity
import com.malombardi.marvel.presentation.characters.CharactersViewModel
import com.malombardi.marvel.presentation.collectFlow
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
class CharacterListFragment : Fragment() {

    private lateinit var binding: FragmentCharacterListBinding

    val viewModel: CharacterListViewModel by viewModels()
    val sharedViewModel: CharactersViewModel by activityViewModels()

    @ExperimentalCoroutinesApi
    private lateinit var adapter: CharacterAdapter
    private lateinit var layoutManager: GridLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharacterListBinding.inflate(inflater, container, false)
        return binding.root
    }

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            layoutManager = GridLayoutManager(requireContext(), 3)
            characterList.layoutManager = layoutManager
            characterList.setHasFixedSize(true)
            adapter = CharacterAdapter(viewModel, lifecycleScope)

            lifecycleScope.collectFlow(viewModel.uiState) { state ->
                when(state){
                    is CharacterListUiState.LoadingState -> {
                        characterProgress.visibility = if (state.spinner) View.VISIBLE else View.GONE
                    }
                    is CharacterListUiState.SuccessState -> {
                        characterProgress.visibility = View.GONE
                        adapter.submitList(state.data)
                    }
                    is CharacterListUiState.ErrorState -> {
                        characterProgress.visibility = View.GONE
                    }
                    is CharacterListUiState.CharacterSelectedState -> {
                        sharedViewModel.onCharacterSelected(state.character)
                    }
                }

            }

            characterList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    viewModel.lastItemVisible.value = layoutManager.findLastVisibleItemPosition()
                }
            })

            characterList.adapter = adapter
        }
    }
}
