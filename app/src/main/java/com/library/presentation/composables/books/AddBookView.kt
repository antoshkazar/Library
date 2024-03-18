package com.library.presentation.composables.books

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.library.presentation.theme.Brown40

@Composable
@Preview
fun AddBookView(
    modifier: Modifier = Modifier,
    onAddBookClick: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .padding(8.dp)
            .size(BOOK_VIEW_SIZE.dp)
            .clip(RoundedCornerShape(16.dp))
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .align(Alignment.Center)
                .clip(CircleShape)
                .background(Brown40)
                .clickable { onAddBookClick() }
        )
        {
            Icon(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(32.dp),
                imageVector = Icons.Filled.Add,
                tint = Color.White,
                contentDescription = ""
            )
        }
    }
}