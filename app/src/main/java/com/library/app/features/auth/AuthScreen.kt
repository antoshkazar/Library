package com.library.app.features.auth

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.library.R
import com.library.presentation.composables.containers.ScaffoldUI
import com.library.presentation.composables.containers.Screen
import com.library.presentation.composables.input.InputTextField
import com.library.presentation.composables.input.InputTextFieldUI
import com.library.presentation.composables.input.InputType
import com.library.presentation.theme.LibraryHeaderStyle

@Composable
fun AuthScreen(
    viewModel: AuthViewModel,
    windowSizeClass: WindowSizeClass,
) {

    val login by remember { viewModel.login }
    val password by remember { viewModel.password }

    val localFocusManager = LocalFocusManager.current
    Screen(
        viewModel = viewModel,
        scaffoldUI = ScaffoldUI(
            modifier = Modifier.pointerInput(Unit) {
                detectTapGestures(onTap = {
                    localFocusManager.clearFocus()
                })
            },
            isNeedToShowNavigationBar = false,
            content = {
               // AnimatedBook(modifier = Modifier.wrapContentSize())
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = stringResource(id = R.string.my_library), style = LibraryHeaderStyle)
                Spacer(modifier = Modifier.height(32.dp))
                Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center)
                {

                    InputTextField(
                        modifier = Modifier.padding(horizontal = 24.dp),
                        uiData = InputTextFieldUI(text = login),
                        onValueChange = viewModel::onLoginChange
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    InputTextField(
                        modifier = Modifier.padding(horizontal = 24.dp),
                        uiData = InputTextFieldUI(text = password, type = InputType.PASSWORD),
                        onValueChange = viewModel::onPasswordChange
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        onClick = viewModel::onButtonClick,
                    ) {
                        Text(text = stringResource(id = R.string.login))
                    }
                }
            }
        )
    )
}