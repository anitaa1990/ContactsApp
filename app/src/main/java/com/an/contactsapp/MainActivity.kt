package com.an.contactsapp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.an.contactsapp.databinding.ActivityMainBinding
import com.an.contactsapp.ui.adapter.ContactsAdapter
import com.an.contactsapp.ui.viewmodel.ContactsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ContactsAdapter

    private val viewModel: ContactsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            viewModel.uiState.collectLatest { contactsUiState ->
               if (contactsUiState.loading) {
                   // TODO
               } else {
                   adapter = ContactsAdapter(contactsUiState.contacts)
                   binding.contentMain.recyclerView.adapter = adapter
               }
            }
        }
    }
}
