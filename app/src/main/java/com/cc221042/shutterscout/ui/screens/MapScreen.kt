package com.cc221042.shutterscout.ui.screens

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.viewinterop.AndroidView
import com.cc221042.shutterscout.Place
import com.cc221042.shutterscout.R
import kotlinx.coroutines.flow.StateFlow
import org.osmdroid.config.Configuration
import org.osmdroid.views.MapView
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat

// Function to get a drawable from the icon name
fun getMarkerIcon(context: Context, iconName: String): Drawable? {
    // Convert the iconName to a drawable resource ID
    val resourceId = when (iconName) {
        "star" -> R.drawable.ic_star // Replace with actual drawable resource IDs
        "heart" -> R.drawable.ic_heart
        "mountain" -> R.drawable.mountain
        "building" -> R.drawable.building
        "tree" -> R.drawable.tree
        "university" -> R.drawable.university
        "water" -> R.drawable.water
        // Add more mappings as needed
        else -> R.drawable.ic_default_marker // Default marker icon
    }
    // Return the drawable
    return ContextCompat.getDrawable(context, resourceId)
}

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
                    marker.icon = getMarkerIcon(context, place.icon) // Set the custom icon
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
                marker.icon = getMarkerIcon(mapView.context, place.icon) // Set the custom icon
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_TOP)
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
        ContextCompat.getDrawable(context, R.drawable.ic_default_marker)
    }
}