package com.cc221042.shutterscout.ui.screens

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.viewinterop.AndroidView
import com.cc221042.shutterscout.Place
import com.cc221042.shutterscout.ui.MapViewModel
import kotlinx.coroutines.flow.StateFlow
import org.osmdroid.config.Configuration
import org.osmdroid.views.MapView
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker

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

                // Add markers for places with non-null latitude and longitude
                placesList.filter { place -> place.latitude != null && place.longitude != null }.forEach { place ->
                    val marker = Marker(this)
                    marker.position = GeoPoint(place.latitude!!, place.longitude!!)
                    marker.title = place.title // Set the title of the place
                    // If you have a custom drawable for the marker, set it here
                    // marker.icon = ResourcesCompat.getDrawable(resources, R.drawable.your_custom_marker, null)
                    //ensure that the drawable resource (R.drawable.your_custom_marker) is correctly placed in your project's res/drawable directory.
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
                // If you have a custom drawable for the marker, set it here
                // marker.icon = ResourcesCompat.getDrawable(resources, R.drawable.your_custom_marker, null)
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                mapView.overlays.add(marker)
            }
            mapView.invalidate()
        }
    )
}