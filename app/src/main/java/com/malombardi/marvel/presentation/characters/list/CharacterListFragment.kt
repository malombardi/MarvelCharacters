package com.malombardi.marvel.presentation.characters.list

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
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
import android.widget.TextView




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
    ): View {
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
            adapter = CharacterAdapter(sharedViewModel, lifecycleScope)

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
                }
            }

            characterList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    viewModel.lastItemVisible.value = layoutManager.findLastVisibleItemPosition()
                }
            })

            characterSearch.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    // do nothing
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    viewModel.searchText.value = p0.toString()
                }

                override fun afterTextChanged(p0: Editable?) {
                    // do nothing
                }
            })

            characterSearch.setOnEditorActionListener { textView, action, keyEvent ->
                if (action == EditorInfo.IME_ACTION_DONE) {
                    val imm: InputMethodManager = textView.context
                        .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(textView.windowToken, 0)
                    true
                }else {
                    false
                }
            }

            characterList.adapter = adapter
        }
    }
}
