package com.uniandes.vinilos.ui.components

import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics

val InvisibleToUserKey = SemanticsPropertyKey<Boolean>("invisibleToUser")
var SemanticsPropertyReceiver.invisibleToUser by InvisibleToUserKey

@Composable
fun SecondaryCollector(
    modifier: Modifier = Modifier,
    buttonModifier: Modifier = Modifier,
    name: String,
    albumCount: String,
    onClick: () -> Unit,
) {
    val painter = rememberAsyncImagePainter(model = "https://wallpapers.com/images/hd/neon-purple-4k-z32z5va3r8itqjvq.jpg")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .semantics {
                contentDescription = "Coleccionista $name con $albumCount álbumes"
            },
        shape = RoundedCornerShape(8.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) {
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colorStops = arrayOf(
                                    0.5f to Color.Black.copy(alpha = 0f),
                                    1.0f to Color.Black.copy(alpha = 1f)
                                )
                            )
                        )
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black)
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column (
                    modifier = Modifier.semantics(mergeDescendants = true) {
                        contentDescription = "$name con $albumCount álbumes"
                    }
                )
                {
                    Text(
                        text = albumCount,
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp),
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.semantics {
                            invisibleToUser = true
                        }
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = name,
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
                        color = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.semantics {
                            contentDescription = name
                        }
                    )
                }

                Button(
                    onClick = { onClick() },
                    modifier = buttonModifier.semantics {
                        contentDescription = "Agregar a la colección de $name con $albumCount álbumes"
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF059BFF).copy(alpha = 0.1f),
                        contentColor = MaterialTheme.colorScheme.tertiary
                    ),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text(text = "Agregar")
                }
            }
        }
    }
}

