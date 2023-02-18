package com.oneandonly.inventationblock

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oneandonly.inventationblock.ui.theme.InventationBlockTheme

class ComposeExActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SignInScreen()
        }
    }
}

@Preview (
    showBackground = true,
    showSystemUi = true
)
@Composable
fun SignInScreen() {
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement =  Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = "One Market",
            color = Color.Black,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
        Button(onClick = { /*TODO*/ }) {
            Text(text = "로그인")
        }
    }
}

@Preview (
    showBackground = true,
    showSystemUi = true
)
@Composable
fun SearchScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Cyan),
            verticalAlignment = Alignment.Top
        ) {
            TextField(
                value = "",
                onValueChange = {},
                modifier = Modifier
                    .weight(8f)
                    .background(Color.Blue),)
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .weight(2f)
            ) {
            }
        }
    }
}

@Preview (
    showBackground = true,
    showSystemUi = true
)
@Composable
fun LoginScreen() {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter)
        ) {
            TextField(
                value = "",
                onValueChange = {},
                modifier = Modifier
                    .fillMaxSize(),
                label = { Text(text = "제목을 입력해주세요") }
            )
            TextField(
                value = "",
                onValueChange = {},
                modifier = Modifier
                    .fillMaxSize(),
                label = { Text(text = "제목을 입력해주세요") }
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.BottomStart)
        ) {
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = "등록하기")
            }
        }
    }
}

