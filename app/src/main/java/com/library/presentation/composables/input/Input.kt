package com.library.presentation.composables.input

import android.view.KeyEvent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.library.R
import com.library.core.extensions.toSingleTrimIndent
import com.library.presentation.composables.EyeToggle
import com.library.presentation.theme.LibraryTypography


//region Preview samples
class InputTextFieldUIProvider : PreviewParameterProvider<InputTextFieldUI> {
    override val values = sequenceOf(
        InputTextFieldUI(
            text = "",
            placeHolderText = "Placeholder"
        ),
        InputTextFieldUI(
            text = "Short text",
        ),
        InputTextFieldUI(
            text = "Text disabled",
            enabled = false,
        ),
        InputTextFieldUI(
            text = "With clear text mode",
            clearTextModeOn = true,
        ),
        InputTextFieldUI(
            text = "",
            placeHolderText = "Placeholder with very long text",
        ),
        InputTextFieldUI(
            text = "Very long long long long long text",
        ),
        InputTextFieldUI(
            text = "Text with error",
            hasError = true,
            errorText = "Error text",
        ),
        InputTextFieldUI(
            text = "Password",
            type = InputType.PASSWORD,
        ),
    )
}
//endregion

enum class InputType {
    TEXT, PASSWORD, NUMERIC, NUMERIC_DOTTED
}

data class InputTextFieldUI(
    val text: String = "",
    val placeHolderText: String = "",
    val hasError: Boolean = false,
    val errorText: String? = null,
    val clearTextModeOn: Boolean = false,
    val enabled: Boolean = true,
    val type: InputType = InputType.TEXT,
    val endIconResId: Int? = null,
    val textStyle: TextStyle = LibraryTypography.bodyMedium,
    val lengthLimit: Int = Int.MAX_VALUE,
    val keyboardImeAction: ImeAction? = null,
    val decimalPartNumberCount: Int = 0,
    val imeAction: ImeAction = ImeAction.None,
)


@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(showBackground = true)
@Composable
fun InputTextField(
    @PreviewParameter(InputTextFieldUIProvider::class) uiData: InputTextFieldUI,
    modifier: Modifier = Modifier,
    textFieldModifier: Modifier = Modifier,
    onValueChange: (String) -> Unit = {},
    endIconAction: (String) -> Unit = {},
    onFocusChange: (Boolean) -> Unit = {},
    extraOnDoneAction: () -> Unit = {},
    callIconActionOnFinish: Boolean = true,
) {
    var hasFocus by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val isClearTextMode = uiData.enabled
            && uiData.clearTextModeOn
            && uiData.text.isNotEmpty()
            && hasFocus

    val showPassword = remember { mutableStateOf(false) }
    val isShowPasswordMode = uiData.enabled
            && uiData.type == InputType.PASSWORD
            && uiData.text.isNotEmpty()

    Column(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        TextField(
            modifier = textFieldModifier
                .onFocusChanged { focusState ->
                    hasFocus = focusState.isFocused
                    onFocusChange(focusState.hasFocus)
                }
                .fillMaxWidth()
                .onKeyEvent {
                    if (it.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_ENTER) {
                        val canHandleEndIconAction = !isClearTextMode
                                && !isShowPasswordMode
                                && uiData.endIconResId != null

                        if (canHandleEndIconAction) {
                            endIconAction(uiData.text)
                            focusManager.clearFocus()
                            true
                        } else {
                            false
                        }
                    } else {
                        false
                    }
                },
            value = uiData.text,
            textStyle = uiData.textStyle,
            placeholder = {
                Text(
                    text = uiData.placeHolderText,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = uiData.textStyle,
                )
            },
            enabled = uiData.enabled,
            singleLine = true,
            maxLines = 1,
            keyboardOptions = KeyboardOptions.Default.copy(
                autoCorrect = true,
                keyboardType = when (uiData.type) {
                    InputType.TEXT,
                    InputType.PASSWORD -> KeyboardType.Text

                    InputType.NUMERIC -> KeyboardType.NumberPassword
                    InputType.NUMERIC_DOTTED -> KeyboardType.Number
                },
                imeAction = uiData.keyboardImeAction ?: when (uiData.type) {
                    InputType.TEXT -> when {
                        uiData.imeAction != ImeAction.None -> uiData.imeAction
                        uiData.endIconResId != null -> ImeAction.Done
                        else -> ImeAction.Next
                    }

                    InputType.PASSWORD -> ImeAction.Done
                    InputType.NUMERIC -> ImeAction.Done
                    InputType.NUMERIC_DOTTED -> ImeAction.Done
                }
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Next)
                },
                onDone = {
                    if (callIconActionOnFinish) {
                        endIconAction(uiData.text)
                    }
                    focusManager.clearFocus()
                    extraOnDoneAction()
                }
            ),

            visualTransformation = when (uiData.type) {
                InputType.TEXT,
                InputType.NUMERIC_DOTTED,
                InputType.NUMERIC -> VisualTransformation.None

                InputType.PASSWORD -> if (showPassword.value) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                }
            },
            isError = uiData.hasError,
            onValueChange = { textValue ->
                val out = textValue.toSingleTrimIndent()
                if (out.length <= uiData.lengthLimit) {
                    onValueChange(out)
                }
            },
            trailingIcon = {
                when {
                    isClearTextMode -> IconButton(onClick = { onValueChange("") }) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(R.drawable.ic32_close),
                            contentDescription = "clear text",
                        )
                    }

                    isShowPasswordMode -> IconButton(
                        onClick = { showPassword.value = showPassword.value.not() }) {
                        EyeToggle(
                            checked = showPassword.value,
                        )
                    }

                    uiData.endIconResId != null -> IconButton(
                        onClick = {
                            focusManager.clearFocus()
                            endIconAction(uiData.text)
                        },
                        enabled = uiData.enabled,
                    ) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(uiData.endIconResId),
                            contentDescription = null,
                        )
                    }

                    else -> Unit
                }
            },
        )
    }
}

