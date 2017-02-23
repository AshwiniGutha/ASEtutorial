package ashwini;

import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    double longitude;
    double latitude;
    Bitmap photo;
    String address;
    LatLng currentLatLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
        mMap = mapFragment.getMap();

        // Receiving latitude from MainActivity screen
        this.latitude = getIntent().getDoubleExtra("latitude", 0);

        // Receiving longitude from MainActivity screen
        this.longitude = getIntent().getDoubleExtra("longitude", 0);

        //Receiving address
        this.address = getIntent().getStringExtra("address");

        //Receiving address
        this.photo = (Bitmap)(getIntent().getParcelableExtra("bmp_image"));

        this.currentLatLng = new LatLng(this.latitude,this.longitude);



    }


        @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
            mMap.addMarker(new MarkerOptions().position(this.currentLatLng).title("Your current Location is:").snippet(this.address).icon(BitmapDescriptorFactory.fromBitmap(this.photo)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(this.currentLatLng,7));
    }
}
