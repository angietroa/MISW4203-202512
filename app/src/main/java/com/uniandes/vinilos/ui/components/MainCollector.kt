package com.uniandes.vinilos.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter

@Composable
fun MainCollector (
    modifier: Modifier,
    name: String,
) {
    val painter = rememberAsyncImagePainter(model = "https://wallpapers.com/images/hd/neon-purple-4k-z32z5va3r8itqjvq.jpg")

    Column(
        modifier = modifier
            .width(114.dp)
            .height(180.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .size(110.dp, 150.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = name,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.Left,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
        )
    }
}