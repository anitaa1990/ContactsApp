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
    private val groupedContacts: GroupedContacts
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_ITEM = 1
    }

    // Prepare a flat list of sections where each section has a header and contacts
    private val sections: List<Pair<String, List<ContactModel>>> = groupedContacts.toList()

    override fun getItemCount(): Int {
        // Total item count: 1 for header + size of contact list for each section
        return sections.sumOf { it.second.size + 1 }
    }

    override fun getItemViewType(position: Int): Int {
        var currentPosition = 0

        // Iterate through sections and determine if the position is for a header or contact item
        for ((header, contacts) in sections) {
            // The first item in the section is a header
            if (position == currentPosition) {
                return TYPE_HEADER
            }
            currentPosition++

            // The remaining items in the section are contact items
            if (position in currentPosition until currentPosition + contacts.size) {
                return TYPE_ITEM
            }
            currentPosition += contacts.size
        }

        throw IllegalStateException("Unknown item type")
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
        var currentPosition = 0

        for ((header, contacts) in sections) {

            if (position == currentPosition) {
                val headerHolder = holder as HeaderViewHolder
                headerHolder.bind(header)
                return
            }
            currentPosition++

            if (position in currentPosition until currentPosition + contacts.size) {
                val contact = contacts[position - currentPosition]
                val contactHolder = holder as ContactItemViewHolder
                contactHolder.bind(contact)
                return
            }
            currentPosition += contacts.size
        }
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
