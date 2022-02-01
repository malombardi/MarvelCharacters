package com.malombardi.marvel.presentation.characters.bio

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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.malombardi.marvel.data.Constants
import com.malombardi.marvel.databinding.DialogBioBinding
import com.malombardi.marvel.presentation.characters.CharactersViewModel
import com.malombardi.marvel.presentation.collectFlow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterBioDialog : DialogFragment() {
    private lateinit var binding: DialogBioBinding
    private lateinit var viewModel: CharactersBioViewModel
    private val sharedViewModel: CharactersViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[CharactersBioViewModel::class.java]
        viewModel.setUrl(arguments?.getString(Constants.URL_BIO_TYPE))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogBioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
        with(binding) {
            lifecycleScope.collectFlow(viewModel.uiState) { state ->
                when (state) {
                    is CharacterBioUiState.InitialLoading -> {
                        bioContainer.visibility = View.INVISIBLE
                        bioProgress.visibility = View.VISIBLE
                    }
                    is CharacterBioUiState.Loading -> {
                        bioContainer.webViewClient = object : WebViewClient() {
                            override fun onPageFinished(view: WebView, url: String) {
                                viewModel.onPageLoaded()
                            }
                        }
                        bioContainer.loadUrl(state.link)
                    }
                    is CharacterBioUiState.DataLoaded -> {
                        bioProgress.visibility = View.GONE
                        bioContainer.visibility = View.VISIBLE
                    }
                    is CharacterBioUiState.Error -> {
                        bioContainer.visibility = View.GONE
                        dismiss()
                        sharedViewModel.onReturnedWithError()
                    }
                }
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        sharedViewModel.onActivityReturned()
    }

    companion object {
        const val NAME = "bioDialog"
        fun newInstance(link: String, fragmentManager: FragmentManager): CharacterBioDialog {
            val bioDialog = CharacterBioDialog().apply {
                val args = Bundle().apply {
                    putString(Constants.URL_BIO_TYPE, link)
                }
                arguments = args
            }

            bioDialog.show(fragmentManager, NAME);
            return bioDialog
        }
    }
}