package com.cc221042.shutterscout.ui.screens

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.graphics.Color
import android.graphics.Paint
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toDrawable
import com.cc221042.shutterscout.Place
import com.cc221042.shutterscout.R
import com.cc221042.shutterscout.ui.MapViewModel
import kotlinx.coroutines.flow.StateFlow
import org.osmdroid.config.Configuration
import org.osmdroid.views.MapView
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
@Composable
fun MapScreen(places: StateFlow<List<Place>>) {
    val placesList by places.collectAsState()

    AndroidView(
        factory = { context ->
            MapView(context).apply {
                Configuration.getInstance().load(context, context.getSharedPreferences("prefs", Context.MODE_PRIVATE))
                setMultiTouchControls(true)
                controller.setZoom(16)
                controller.setCenter(GeoPoint(48.21607180853806, 16.343526740792633)) // Default location
                val marker = Marker(this)
                // Add markers for places with non-null latitude and longitude
                placesList.filter { place -> place.latitude != null && place.longitude != null }.forEach { place ->
                    marker.position = GeoPoint(place.latitude!!, place.longitude!!)
                    marker.title = place.title // Set the title of the place
                    //marker.image = context.getDrawable(R.drawable.thumbtack)
                    //marker.icon = getDrawable(R.drawable.thumbtack)
                    marker.image = ResourcesCompat.getDrawable(context.resources, R.drawable.ic_launcher_foreground, null)
                    //marker.icon = getIconDrawable(context, place.icon) // Custom function to get drawable
                    marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                    overlays.add(marker)
                }
            }
        },
        update = { mapView ->
            mapView.overlays.clear()
            placesList.filter { place -> place.latitude != null && place.longitude != null }.forEach { place ->
                val marker = Marker(mapView)
                marker.position = GeoPoint(place.latitude!!, place.longitude!!)
                marker.title = place.title // Set the title of the place
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                mapView.overlays.add(marker)
            }
            mapView.invalidate()
        }
    )
}

// Utility function to map icon names to drawables
private fun getIconDrawable(context: Context, iconName: String): Drawable? {
    val resourceId = context.resources.getIdentifier(iconName, "drawable", context.packageName)
    return if (resourceId != 0) {
        ContextCompat.getDrawable(context, resourceId)
    } else {
        // Fallback drawable or null if no drawable should be used in case of error
        ContextCompat.getDrawable(context, R.drawable.thumbtack)
    }}