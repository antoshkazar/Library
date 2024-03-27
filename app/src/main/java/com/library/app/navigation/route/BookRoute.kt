package com.library.app.navigation.route

import android.net.Uri
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.library.app.features.books.BookScreen
import com.library.app.features.books.BookViewModel
import com.library.core.extensions.toJson
import com.library.data.models.books.BookModel
import com.library.presentation.navigation.route.NavRoute

object BookRoute : NavRoute<BookViewModel> {

    const val KEY_BOOK = "KEY_BOOK"
    override val route = "book_screen/{$KEY_BOOK}"

    fun routeWithParams(
        bookUi: BookModel,
    ): String {
        val uri = Uri.encode(
            Param(
                bookUi = bookUi
            ).toJson()
        )
        return route.replace("{$KEY_BOOK}", uri)
    }

    data class Param(
        val bookUi: BookModel,
    )

    override fun getArguments(): List<NamedNavArgument> = listOf(
        navArgument(KEY_BOOK) { type = NavType.StringType },
    )

    @Composable
    override fun viewModel(): BookViewModel = hiltViewModel()

    @Composable
    override fun Content(
        viewModel: BookViewModel,
        windowSizeClass: WindowSizeClass,
    ) = BookScreen(viewModel, windowSizeClass)
}