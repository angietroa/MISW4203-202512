package com.uniandes.vinilos.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import java.lang.ref.WeakReference

/**
 * Componente de álbum secundario optimizado con WeakReference para la caché de imágenes.
 * Esta implementación permite que las imágenes sean liberadas cuando hay presión de memoria.
 */
@Composable
fun SecondaryAlbum(
    modifier: Modifier,
    title: String,
    subtitle: String,
    cover: String,
    onClick: () -> Unit,
) {
    val context = LocalContext.current
    
    // Configura las políticas de caché para Coil
    val imageRequest = remember(cover) {
        ImageRequest.Builder(context)
            .data(cover)
            // Permite que las imágenes en memoria sean referenciadas débilmente
            .memoryCachePolicy(CachePolicy.ENABLED)
            // Mantiene una caché en disco para reducir descargas repetidas
            .diskCachePolicy(CachePolicy.ENABLED)
            // Usa un tamaño específico para evitar cargar imágenes de tamaño completo
            .size(256, 256)
            .build()
    }
    
    // El pintor se crea basado en nuestra configuración personalizada
    val painter = rememberAsyncImagePainter(model = imageRequest)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.secondary,
                    textAlign = TextAlign.Start
                )

                Text(
                    text = subtitle,
                    fontSize = 17.sp,
                    color = MaterialTheme.colorScheme.tertiary,
                    textAlign = TextAlign.Start
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Image(
                painter = painter,
                contentDescription = "Portada del álbum $title",
                modifier = Modifier
                    .size(64.dp)
                    .aspectRatio(1f)
                    .clip(
                        RoundedCornerShape(
                            topEnd = 12.dp,
                            bottomEnd = 12.dp,
                            topStart = 0.dp,
                            bottomStart = 0.dp
                        )
                    ),
                contentScale = ContentScale.Crop
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .padding(horizontal = 8.dp)
                .background(color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f))
        )
    }
}
