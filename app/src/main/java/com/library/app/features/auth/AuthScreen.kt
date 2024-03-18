package com.library.app.features.auth

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    viewModel: AuthViewModel,
    windowSizeClass: WindowSizeClass,
) {

    val login by remember { viewModel.login }
    val password by remember { viewModel.password }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showRegistrationBottomSheet by remember { mutableStateOf(false) }

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
                //AnimatedBook(modifier = Modifier.wrapContentSize())
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = stringResource(id = R.string.my_library), style = LibraryHeaderStyle)
                Spacer(modifier = Modifier.height(32.dp))
                Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center)
                {

                    InputTextField(
                        modifier = Modifier.padding(horizontal = 24.dp),
                        uiData = InputTextFieldUI(
                            text = login,
                            label = stringResource(id = R.string.login)
                        ),
                        onValueChange = viewModel::onLoginChange
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    InputTextField(
                        modifier = Modifier.padding(horizontal = 24.dp),
                        uiData = InputTextFieldUI(
                            text = password,
                            type = InputType.PASSWORD,
                            label = stringResource(id = R.string.password)
                        ),
                        onValueChange = viewModel::onPasswordChange
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        onClick = viewModel::onLoginClick,
                        shape = ButtonDefaults.filledTonalShape
                    ) {
                        Text(text = stringResource(id = R.string.enter))
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        onClick = { showRegistrationBottomSheet = true },
                        shape = ButtonDefaults.outlinedShape
                    ) {
                        Text(text = stringResource(id = R.string.register))
                    }

                    if (showRegistrationBottomSheet) {
                        ModalBottomSheet(
                            modifier = Modifier,
                            onDismissRequest = {
                                showRegistrationBottomSheet = false
                            },
                            sheetState = sheetState
                        ) {

                            Button(onClick = {
                                scope.launch { sheetState.hide() }.invokeOnCompletion {
                                    if (!sheetState.isVisible) {
                                        showRegistrationBottomSheet = false
                                    }
                                }
                            }) {
                                Text("Hide bottom sheet")
                            }
                        }
                    }
                }
            }
        )
    )
}