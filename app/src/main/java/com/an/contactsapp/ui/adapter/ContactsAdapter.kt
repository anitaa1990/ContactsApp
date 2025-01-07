package com.an.contactsapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.an.contactsapp.R
import com.an.contactsapp.databinding.HeaderItemBinding
import com.an.contactsapp.databinding.ListItemBinding
import com.an.contactsapp.model.ContactModel
import com.an.contactsapp.ui.state.GroupedContacts

class ContactsAdapter(
    private val contactsMap: GroupedContacts
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val sections = contactsMap.keys.toList()

    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_ITEM = 1
    }

    override fun getItemViewType(position: Int): Int {
        // Check if the position corresponds to a header or an item
        return if (position % 2 == 0) TYPE_HEADER else TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HEADER -> {
                val view = HeaderItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                HeaderViewHolder(view)
            }
            else -> {
                val view = ListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ContactItemViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_HEADER) {
            val headerHolder = holder as HeaderViewHolder
            val section = sections[position / 2]
            headerHolder.bind(section)
        } else {
            val contactHolder = holder as ContactItemViewHolder
            val contact = contactsMap[sections[position / 2]]?.get(position % 2)
            contact?.let { contactHolder.bind(it) }
        }
    }

    override fun getItemCount(): Int {
        var totalItemCount = 0
        sections.forEach {
            totalItemCount += contactsMap[it]?.size?.times(2) ?: 0
        }
        return totalItemCount
    }


    inner class HeaderViewHolder(private val binding: HeaderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(header: String) {
            binding.tvHeader.text = header
        }
    }

    inner class ContactItemViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(contact: ContactModel) {
            with(binding) {
                tvProfileName.text = contact.displayName
                tvProfilePhone.text = contact.phoneNumber
                ivProfilePic.load(contact.photoThumbnailUri) {
                    placeholder(R.drawable.ic_profile_icon)
                    error(R.drawable.ic_profile_icon)
                }
            }
        }
    }
}
