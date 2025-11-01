package com.varforge.todolist

import android.os.Bundle
import android.widget.Space
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.varforge.todolist.ui.theme.TODOListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TODOListTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainPage(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MainPage(modifier: Modifier = Modifier) {

    val myContext = LocalContext.current

    val todoName = remember {
        mutableStateOf("")
    }
    val itemList = readData(myContext)
    val focusedManager = LocalFocusManager.current

    // modifier = modifier instead of modifier = Modifier,
    // as "modifier" is passed from scaffold so we can use it
    Column(modifier = modifier.fillMaxSize()) {

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            TextField(
                value = todoName.value,
                onValueChange = {todoName.value = it},
                label = {Text(text = "Enter TODO")},
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedLabelColor = Color.Green,
                    unfocusedLabelColor = Color.White,
                    focusedContainerColor = MaterialTheme.colorScheme.primary,
                    unfocusedContainerColor = MaterialTheme.colorScheme.primary,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    cursorColor = Color.White
                ),
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier
                    .border(1.dp, color = Color.Black, RoundedCornerShape(5.dp))
                    .weight(7F) //3F for the button weight
                    .height(60.dp)
                ,
                textStyle = TextStyle(textAlign = TextAlign.Center)
            )

            Spacer(modifier = Modifier.width(5.dp))

            Button(
                onClick = {
                    if (todoName.value.isNotEmpty()){
                        itemList.add(todoName.value)
                        writeData(itemList,myContext)
                        todoName.value = ""
                        focusedManager.clearFocus()
                    }else{
                        Toast.makeText(myContext, "Please enter a TODO", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .weight(3F)
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.green),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(5.dp),
                border = BorderStroke(1.dp,Color.Black)
            ) {
                Text(text = "Add", fontSize = 20.sp)
            }

        }

        LazyColumn{

            items(
                count = itemList.size,
                itemContent = {index->

                    val item = itemList[index]

                    Card(
                        modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 1.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(0.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                            ){
                            Text(
                                text = item,
                                color = Color.White,
                                fontSize = 10.sp,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.width(300.dp)
                                )

                            Row() {
                                IconButton(onClick = {}) {
                                    Icon(Icons.Filled.Edit, contentDescription = "edit", tint = Color.White)
                                }

                                IconButton(onClick = {}) {
                                    Icon(Icons.Filled.Delete, contentDescription = "delete", tint = Color.White)
                                }
                            }
                        }
                    }

                }
            )

        }

    }

}