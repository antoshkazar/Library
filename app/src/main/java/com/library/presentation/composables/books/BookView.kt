package com.library.presentation.composables.books

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.library.data.models.books.BookMetadata
import com.library.presentation.theme.Brown20
import com.library.presentation.theme.Brown30
import com.library.presentation.theme.Brown40
import com.library.presentation.theme.Brown60
import com.library.presentation.theme.Brown80
import com.library.presentation.theme.LibraryTypography

const val BOOK_VIEW_SIZE = 150

@Preview
@Composable
fun BookView(
    modifier: Modifier = Modifier,
    bookUi: BookMetadata = BookMetadata(
        title = "Мастер и Маргарита",
    ),
) {
    val brightColors = mutableListOf(Brown60, Brown80)
    val allColors = mutableListOf(Brown30, Brown20, Brown40, Brown60, Brown80)
    val randomColor = allColors
        .asSequence()
        .shuffled()
        .firstOrNull() ?: Brown30
    Box(
        modifier = modifier
            .padding(8.dp)
            .size(BOOK_VIEW_SIZE.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                randomColor
            )
    ) {
        Text(
            modifier = Modifier
                .wrapContentSize()
                .padding(horizontal = 4.dp)
                .align(Alignment.Center),
            text = bookUi.title,
            color = if (randomColor in brightColors) {
                Color.Black
            } else {
                Color.White
            },
            style = LibraryTypography.bodyMedium
        )
    }
}