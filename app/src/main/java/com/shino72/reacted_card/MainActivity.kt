package com.shino72.reacted_card

import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shino72.reacted_card.ui.theme.ReactedcardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReactedcardTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ClickAnimateCard()
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
private fun ClickAnimateCard(modifier: Modifier = Modifier){
    var rotationX by remember { mutableStateOf(0f) }
    var rotationY by remember { mutableStateOf(0f) }

    var cardSize by remember {
        mutableStateOf(Rect.Zero)
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
            .background(Color.DarkGray)
    ){
        Image(
            painter = painterResource(id = R.drawable.rukario),
            contentDescription = "rokario",
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
                .onGloballyPositioned { coordinates ->
                    cardSize = coordinates.boundsInParent()
                }
                .graphicsLayer {
                    this.rotationY = rotationY
                    this.rotationX = rotationX
                    cameraDistance = 12f * density
                }
                .pointerInteropFilter { motionEvent ->
                    when (motionEvent.action) {
                        MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                            val touchX = motionEvent.x
                            val touchY = motionEvent.y
                            rotationY = ((touchX - cardSize.width / 2) / (cardSize.width / 2)) * 15f
                            rotationX =
                                ((touchY - cardSize.height / 2) / (cardSize.height / 2)) * -15f
                            true
                        }

                        MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                            rotationX = 0f
                            rotationY = 0f
                            true
                        }

                        else -> false
                    }
                }
        )
    }
}
