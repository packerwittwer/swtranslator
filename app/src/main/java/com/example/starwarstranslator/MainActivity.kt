package com.example.starwarstranslator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.*
//import androidx.compose.material3.DropdownMenu
//import androidx.compose.material3.DropdownMenuItem
//import androidx.compose.material3.Text
//import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.starwarstranslator.ui.theme.StarWarsTranslatorTheme
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.ui.text.font.FontFamily


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StarWarsTranslatorTheme {
                val isHomeScreen = remember { mutableStateOf(true) }

                if (isHomeScreen.value) {
                    HomeScreen {
                        isHomeScreen.value = false
                    }
                } else {
                    TranslatorScreen()
                }
            }
        }
    }
}
@Composable
fun HomeScreen(click: () -> Unit) {
    Image(
        painter = painterResource(id = R.drawable.hyperspace),
        contentDescription = null,
        modifier = Modifier
            .fillMaxSize(),
        contentScale = ContentScale.Crop
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
         Text(
             text = "Star Wars Translator",
            modifier = Modifier.padding(top = 230.dp, bottom = 40.dp),//.graphicsLayer(alpha = .5f),
            color = Color(0xFFffffff),
            fontFamily = FontProvider.star_warsFontFamily,
            fontSize = 50.sp,
            textAlign = TextAlign.Center,
            lineHeight = 50.sp,)

        Button(onClick = click) {
            Text(
                text = "Start",
                style = TextStyle(fontFamily = FontProvider.aurebesh_englishFontFamily,
                fontSize = 24.sp)
            )
        }
    }
}

@Composable
fun TranslatorScreen() {
    Image(
        painter = painterResource(id = R.drawable.black_metal_wallpaper),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )

    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        val selectedLang = remember { mutableStateOf("Aurebesh") }
        Dropdown(selectedLang = selectedLang)

        Text(
            text = "Type to Translate:",
            style = TextStyle(fontFamily = FontProvider.aurebesh_englishFontFamily),
            modifier = Modifier.padding(top = 20.dp),
            fontSize = 20.sp,
            color = Color(0xFFdcdcd9)
        )

        InputBox(selectedLang = selectedLang)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dropdown(selectedLang: MutableState<String>) {
//    var selectedLang by remember { mutableStateOf("Aurebesh") }
    val langOptions = listOf("Aurebesh", "Mandalorian", "Sith")
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 36.dp, vertical = 16.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it }
        ) {
            OutlinedTextField(
                value = selectedLang.value,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                label = { Text("Select Language") }
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .exposedDropdownSize()
            ) {
                langOptions.forEach { lang ->
                    DropdownMenuItem(
                        text = { Text(text = lang, color = Color(0xFFdcdcd9)) },
                        onClick = {
                            selectedLang.value = lang
                            expanded = false
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
fun InputBox(selectedLang: MutableState<String>) {
    var text by remember { mutableStateOf(TextFieldValue("")) } //initialize the text to be nothing
    val fontFamily: FontFamily = when (selectedLang.value) {
        "Aurebesh" -> FontProvider.aurebeshFontFamily
        "Mandalorian" -> FontProvider.mandalorFontFamily
        "Sith" -> FontProvider.sithFontFamily
        else -> FontProvider.aurebeshFontFamily
    }

    Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.Start
    ) {
        TextField(value = text,
                    onValueChange = { newText -> text = newText}, //set the text to be the new text typed in
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    singleLine = true
        )
        SelectionContainer { //print out the letters typed in the new language
            Text(text = text.text,
                fontFamily = fontFamily,
                modifier = Modifier.padding(32.dp),
                color = Color(0xFFa2a29f),
                fontSize = 20.sp)
        }
    }
}