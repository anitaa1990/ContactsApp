package com.an.contactsapp.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.an.contactsapp.R
import com.an.contactsapp.model.ContactModel
import java.util.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContactsList(
    modifier: Modifier = Modifier,
    contacts: Map<String, List<ContactModel>> = Collections.emptyMap()
){
    LazyColumn(modifier) {
        contacts.map { entry ->
            stickyHeader {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .padding(start = 12.dp, top = 6.dp, bottom = 6.dp)
                ) {
                    Text(
                        text = entry.key,
                        style = TextStyle(color = MaterialTheme.colorScheme.primary, fontSize = 20.sp),
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif,
                    )
                }
            }
            items(
                entry.value.size
            ) { index ->
                ContactListItem(contact = entry.value[index])
            }
        }
    }
}

@Composable
fun ContactListItem(contact: ContactModel) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(end = 10.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_profile_icon),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(10.dp)
                .size(60.dp)
                .clip(CircleShape)
        )
        Column(modifier = Modifier.weight(1f, true)) {
            Text(
                text = contact.displayName,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
            )
            Text(
                text = contact.phoneNumber,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.outline
            )
        }
        Image(
            imageVector = Icons.Filled.Call,
            contentDescription ="",
            modifier = Modifier.padding(10.dp),
            colorFilter = ColorFilter.tint(
                color = MaterialTheme.colorScheme.primary
            )
        )
        Image(
            painter = painterResource(id = R.drawable.ic_message),
            contentDescription ="",
            modifier = Modifier.padding(10.dp),
            colorFilter = ColorFilter.tint(
                color = MaterialTheme.colorScheme.primary
            )
        )
    }
}

@Composable
@Preview(showBackground = true, name = "ContactListItemPreview")
fun ContactListItemPreview(){
    val fakeContact = ContactModel(
        id = "1",
        displayName = "Anitaa",
        phoneNumber = "9586747364",
        photoUri = "",
        photoThumbnailUri = ""
    )

    ContactListItem(contact = fakeContact)
}