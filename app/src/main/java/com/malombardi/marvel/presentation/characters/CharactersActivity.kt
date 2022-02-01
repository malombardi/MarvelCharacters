package com.malombardi.marvel.presentation.characters

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.malombardi.marvel.R
import com.malombardi.marvel.databinding.ActivityCharactersBinding
import com.malombardi.marvel.presentation.characters.list.CharacterListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharactersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCharactersBinding

    private lateinit var viewModel: CharactersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[CharactersViewModel::class.java]
        binding = ActivityCharactersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
    }

    private fun setupView() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, CharacterListFragment()).commit()
    }
}