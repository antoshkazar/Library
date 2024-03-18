package com.library.app.features.main

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.library.presentation.composables.containers.ScaffoldUI
import com.library.presentation.composables.containers.Screen

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel,
    windowSizeClass: WindowSizeClass,
) {
    Screen(
        viewModel = viewModel,
        scaffoldUI = ScaffoldUI(
            isNeedToShowNavigationBar = true,
            content = {
//                val state = rememberLazyStaggeredGridState()
//
//                LazyVerticalStaggeredGrid(
//                    columns = StaggeredGridCells.Fixed(2),
//                    modifier = Modifier.fillMaxSize(),
//                    state = state,
//                    horizontalArrangement = Arrangement.spacedBy(10.dp),
//                    content = {
//
//                        items(count) {
//                            //item content
//                        }
//                    }
//                )
                FlowRow(modifier = Modifier) {

                }
            }
        )
    )
}