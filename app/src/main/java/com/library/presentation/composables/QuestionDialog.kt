package com.library.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.library.R
import com.library.presentation.theme.LibraryTypography


data class QuestionDialogUI(
    val positiveAction: () -> Unit = {},
    val negativeAction: () -> Unit = {},
    val dismissAction: () -> Unit = {},
    val questionText: String = "",
    val positiveButtonText: String = "",
    val negativeButtonText: String = "",
)

class QuestionDialogProvider : PreviewParameterProvider<QuestionDialogUI> {
    override val values = sequenceOf(
        QuestionDialogUI(questionText = "Сохранить изменения?",
            positiveAction = {},
            negativeAction = {}),
    )
}

@Composable
fun QuestionDialog(
    ui: QuestionDialogUI,
    dialogOpened: MutableState<Boolean> = mutableStateOf(false),
) {
    val openDialog = remember { dialogOpened }

    if (openDialog.value) {
        Dialog(
            onDismissRequest = {
                openDialog.value = false
                ui.negativeAction()
            }
        ) {
            QuestionDialogContent(ui, openDialog)
        }
    }
}

@Composable
@Preview()
fun QuestionDialogContent(
    @PreviewParameter(QuestionDialogProvider::class) ui: QuestionDialogUI,
    openDialog: MutableState<Boolean> = mutableStateOf(true),
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent),
        verticalArrangement = Arrangement.Center,
    ) {
        Box(
            Modifier
                .background(Color.Transparent)
                .clip(shape = RoundedCornerShape(8.dp)),
        ) {
            Column(
                modifier = Modifier
                    .background(White)
                    .padding(
                        horizontal = 8.dp,
                        vertical = 16.dp
                    ),
            ) {
                if (ui.questionText.isNotEmpty()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            modifier = Modifier.weight(1f, false),
                            text = ui.questionText,
                            style = LibraryTypography.titleMedium
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Image(
                            painter = painterResource(id = R.drawable.ic32_close),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(top = 6.dp)
                                .size(42.dp)
                                .clickable {
                                    openDialog.value = false
                                    ui.dismissAction()
                                }
                        )
                    }
                    Spacer(Modifier.height(16.dp))
                    BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            val buttonWidth = 140.dp
                            Box(Modifier.width(buttonWidth)) {
                                OutlinedButton(
                                    onClick = ui.negativeAction,
                                    modifier = Modifier.fillMaxWidth(),
                                ) {
                                    Text(text = ui.negativeButtonText.ifEmpty { stringResource(id = R.string.close) })
                                }
                            }
                            Spacer(modifier = Modifier.padding(8.dp))
                            Box(Modifier.width(buttonWidth)) {
                                Button(
                                    onClick = ui.positiveAction,
                                    modifier = Modifier.fillMaxWidth(),
                                ) {
                                    Text(text = ui.positiveButtonText.ifEmpty { stringResource(id = R.string.confirm) })
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}