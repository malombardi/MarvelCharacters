package com.malombardi.marvel.presentation.comics

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.malombardi.marvel.R
import com.malombardi.marvel.data.Constants
import com.malombardi.marvel.databinding.DialogComicsBinding
import com.malombardi.marvel.domain.usecases.GetComicsUseCase
import com.malombardi.marvel.presentation.characters.CharactersViewModel
import com.malombardi.marvel.presentation.characters.list.CharacterAdapter
import com.malombardi.marvel.presentation.collectFlow
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@AndroidEntryPoint
class CharacterComicsDialog : DialogFragment() {

    private lateinit var binding: DialogComicsBinding
    private val viewModel: CharactersComicsViewModel by viewModels()
    private val sharedViewModel: CharactersViewModel by activityViewModels()

    private lateinit var adapter: ComicsAdapter
    private lateinit var layoutManager: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialog)
        viewModel.startFetching(arguments?.getString(Constants.CHARACTER_ID))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogComicsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
        with(binding) {
            layoutManager = GridLayoutManager(requireContext(), 3)
            comicsList.layoutManager = layoutManager
            comicsList.setHasFixedSize(true)
            adapter = ComicsAdapter()

            lifecycleScope.collectFlow(viewModel.uiState) { state ->
                when (state) {
                    is CharacterComicsUiState.Loading -> {
                        comicsList.visibility = View.VISIBLE
                        comicsProgress.visibility = View.VISIBLE
                    }
                    is CharacterComicsUiState.Success -> {
                        comicsProgress.visibility = View.GONE
                        adapter.submitList(state.data)
                    }
                    is CharacterComicsUiState.Error -> {
                        dismiss()
                        sharedViewModel.onReturnedWithError()
                    }
                }
            }

            comicsList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    viewModel.lastItemVisible.value = layoutManager.findLastVisibleItemPosition()
                }
            })

            comicsList.adapter = adapter
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        sharedViewModel.onActivityReturned()
    }

    companion object {
        private const val NAME = "comicsDialog"
        fun newInstance(characterId: String, fragmentManager: FragmentManager): CharacterComicsDialog {
            val bioDialog = CharacterComicsDialog().apply {
                val args = Bundle().apply {
                    putString(Constants.CHARACTER_ID, characterId)
                }
                arguments = args
            }

            bioDialog.show(fragmentManager, NAME);
            return bioDialog
        }
    }
}