package com.uniandes.vinilos.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter

@Composable
fun SecondaryArtist(
    modifier: Modifier,
    title: String,
    cover: String,
    onClick: () -> Unit,
) {
    val painter = rememberAsyncImagePainter(model = cover)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row {
            Image(
                painter = painter,
                contentDescription = "Artista $title",
                modifier = Modifier
                    .size(80.dp)
                    .aspectRatio(1f)
                    .clip(
                        RoundedCornerShape(100.dp)
                    ),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(15.dp))

            Column {
                Spacer(modifier = Modifier.height(8.dp).semantics { contentDescription = title })

                Text(
                    text = title,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.secondary,
                    textAlign = TextAlign.Start
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(0.5.dp)
                        .background(color = MaterialTheme.colorScheme.secondary)
                )
            }
        }
    }
}