package yaremchuken.quizknight.compose.gamesmanager

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import yaremchuken.quizknight.R
import yaremchuken.quizknight.dimensions.FontDimensions
import yaremchuken.quizknight.dimensions.UIDefaults
import java.util.Locale

@Preview
@Composable
fun GameCreationDialog(
    onDismiss: () -> Unit = {},
    onApprove: (name: String, original: Locale, studied: Locale) -> Unit = { _: String, _: Locale, _: Locale -> {} },
    existingGames: List<String> = listOf(),
    visible: Boolean = true
) {
    var gameName by remember { mutableStateOf("") }
    var original by remember { mutableStateOf(Locale("ru")) }
    var studied by remember { mutableStateOf(Locale.ENGLISH) }
    var error by remember { mutableStateOf("") }

    val nameExistsError = stringResource(R.string.such_name_already_exists)

    if (visible) {
        Dialog(onDismissRequest = { gameName = ""; error = ""; onDismiss() }) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(colorResource(R.color.palette_b7))
            ) {
                Text(
                    text = stringResource(R.string.create_new_game),
                    Modifier
                        .padding(vertical = dimensionResource(R.dimen.padding_default))
                        .align(Alignment.CenterHorizontally),
                    fontSize = FontDimensions.MEDIUM,
                    fontWeight = FontWeight.Bold
                )
                TextField(
                    value = gameName,
                    onValueChange = { gameName = it },
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = dimensionResource(R.dimen.padding_large))
                        .align(Alignment.CenterHorizontally),
                    textStyle = TextStyle.Default.copy(fontSize = FontDimensions.MEDIUM),
                    placeholder = { Text(text = stringResource(R.string.game_name)) },
                    shape = UIDefaults.ROUNDED_CORNER
                )
                if (error.isNotBlank()) {
                    Text(
                        text = error,
                        Modifier.padding(start = dimensionResource(R.dimen.padding_large)),
                        color = colorResource(R.color.red),
                        fontSize = FontDimensions.X_SMALL
                    )
                }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(
                            start = dimensionResource(R.dimen.padding_large),
                            top = dimensionResource(R.dimen.padding_large),
                            end = dimensionResource(R.dimen.padding_large)
                        ),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = stringResource(R.string.original_language),
                            Modifier
                                .padding(all = 10.dp)
                                .align(Alignment.CenterHorizontally),
                            fontSize = FontDimensions.LARGE,
                            fontWeight = FontWeight.Bold
                        )
                        Column(modifier = Modifier.selectableGroup()) {
                            listOf(Locale("ru"), Locale("en")).forEach { locale ->
                                LanguageSelectBtn(locale, { original = locale }, locale == original)
                            }
                        }
                    }
                    Column {
                        Text(
                            text = stringResource(R.string.studied_language),
                            Modifier
                                .padding(all = 10.dp)
                                .align(Alignment.CenterHorizontally),
                            fontSize = FontDimensions.LARGE,
                            fontWeight = FontWeight.Bold
                        )
                        Column(modifier = Modifier.selectableGroup()) {
                            listOf(Locale("en"), Locale("ru")).forEach { locale ->
                                LanguageSelectBtn(locale, { studied = locale }, locale == studied)
                            }
                        }
                    }
                }
                Button(
                    onClick = { if (existingGames.contains(gameName)) error = nameExistsError
                    else { onApprove(gameName, original, studied); onDismiss() } },
                    Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = dimensionResource(R.dimen.padding_default),
                            vertical = dimensionResource(R.dimen.padding_default)
                        )
                        .height(52.dp),
                    shape = UIDefaults.ROUNDED_CORNER,
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.button_primary)),
                    border = BorderStroke(1.dp, colorResource(R.color.white)),
                    enabled = gameName.isNotBlank()
                ) {
                    Text(
                        text = stringResource(R.string.btn_title_lets_go),
                        fontSize = FontDimensions.LARGE
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun LanguageSelectBtn(
    locale: Locale = Locale.ENGLISH,
    onClick: () -> Unit = {},
    selected: Boolean = false
) {

    val flagSrc = when (locale.language) {
        "ru" -> R.drawable.ic_flag_ru
        "en" -> R.drawable.ic_flag_uk
        else -> R.drawable.ic_flag_us
    }

    val colorSrc = if (selected) R.color.palette_dd else R.color.white

    Row(
        Modifier
            .padding(bottom = 10.dp)
            .background(colorResource(colorSrc), shape = UIDefaults.ROUNDED_CORNER)
            .border(if (selected) 1.dp else 0.dp, colorResource(R.color.black), UIDefaults.ROUNDED_CORNER)
            .selectable(selected, true, onClick = { onClick() })
    ) {
        Text(
            text = locale.language.uppercase(),
            Modifier
                .align(alignment = Alignment.CenterVertically)
                .padding(start = 10.dp, top = 10.dp, end = 30.dp, bottom = 10.dp),
            fontSize = FontDimensions.LARGE,
            fontWeight = FontWeight.Bold
        )
        Image(
            bitmap = ImageBitmap.imageResource(flagSrc),
            contentDescription = null,
            Modifier
                .size(52.dp)
                .clip(UIDefaults.ROUNDED_CORNER),
            contentScale = ContentScale.Crop
        )
    }
}