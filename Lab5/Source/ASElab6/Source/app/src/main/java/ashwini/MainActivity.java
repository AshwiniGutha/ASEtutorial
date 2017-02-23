package ashwini;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.*;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LocationListener {

    public static final int IMAGE_GALLERY_REQUEST = 20;
    //Global variables.
    double longitude = 0;
    double latitude = 0;
    Bitmap photo = null;
    String address;
    private Uri outputFileUri;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void locateMe(View v) {
        //Variables
        LocationManager mLocationManager;
        Boolean isGpsEnabled = false;
        Boolean isNetworkEnable = false;
        Location location = null;
        double loni = 0;
        double lati = 0;
        Geocoder geo;
        StringBuilder userAddress = null;


        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        isGpsEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnable = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGpsEnabled && !isNetworkEnable) {
            EditText edit = (EditText) findViewById(R.id.txt_address);
            edit.setText("GPS and N/W not available, set manually.", TextView.BufferType.EDITABLE);
        } else {
            if (isNetworkEnable) {
                mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, this);
            }
            if (mLocationManager == null) {
                location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (location != null) {
                    loni = location.getLongitude();
                    lati = location.getLatitude();
                }
            }

            if (isGpsEnabled) {
                if (location == null) {
                    mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);

                    if (mLocationManager != null)
                        location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    if (location != null) {
                        loni = location.getLongitude();
                        lati = location.getLatitude();
                    }
                }
            }
        }
        //we have loni or longitude and lati or latitude at this point.
        geo = new Geocoder(this);

        try {

            List<Address> addresses = geo.getFromLocation(lati, loni, 1);
            Address address = addresses.get(0);
            userAddress = new StringBuilder();
            for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                userAddress.append(address.getAddressLine(i)).append("\t");
            }
            userAddress.append(address.getCountryName()).append("\t");
        } catch (Exception ex) {
            EditText edit = (EditText) findViewById(R.id.txt_address);
            edit.setText("Something went wrong while getting address", TextView.BufferType.EDITABLE);
        }


        EditText edit = (EditText) findViewById(R.id.txt_address);
        edit.setText(userAddress.toString(), TextView.BufferType.EDITABLE);

        this.latitude = lati;
        this.longitude = loni;
        this.address = userAddress.toString();
    }

    public void capturePhoto(View v) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, 1337);

    }

    //Upload gallery image
    public void uploadGimage(View v) {
        Intent pickIntent = new Intent(Intent.ACTION_PICK);
        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String pictureDirectoryPath = pictureDirectory.getPath();
        Uri picdata = Uri.parse(pictureDirectoryPath);
        pickIntent.setDataAndType(picdata, "image/*");
        startActivityForResult(pickIntent, IMAGE_GALLERY_REQUEST);


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1337 && resultCode == RESULT_OK) {
            this.photo = (Bitmap) data.getExtras().get("data");
            ImageView iv = (ImageView) findViewById(R.id.imageView);
            iv.setImageBitmap(this.photo);
        }

        if (requestCode == 12345) {
            final boolean isCamera;


            String action = null;
            action = data.getAction();
            if (action == null) {
                isCamera = false;
            } else {
                isCamera = true;
            }

            Uri selectedImageUri;
            if (isCamera) {

                this.photo = (Bitmap) data.getExtras().get("data");
                ImageView iv = (ImageView) findViewById(R.id.imageView);
                iv.setImageBitmap(this.photo);

            } else {
                selectedImageUri = data == null ? null : data.getData();
                ImageView iv = (ImageView) findViewById(R.id.imageView);
                iv.setImageURI(selectedImageUri);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;
                try {
                    InputStream input = getContentResolver().openInputStream(selectedImageUri);
                    this.photo = BitmapFactory.decodeStream(input,new Rect(),options);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }


        }
    }

    public void naviToMap(View v) {
        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
        intent.putExtra("latitude", this.latitude);
        intent.putExtra("longitude", this.longitude);
        intent.putExtra("address", this.address);
        intent.putExtra("bmp_image", this.photo);


        startActivity(intent);
    }

    public void openImageIntent(View v) {

// Determine Uri of camera image to save.
        final File root = new File(Environment.getExternalStorageDirectory() + File.separator + "MyDir" + File.separator);
        root.mkdirs();
        final String fname = "img_" + System.currentTimeMillis() + ".jpg";
        final File sdImageMainDirectory = new File(root, fname);
        outputFileUri = Uri.fromFile(sdImageMainDirectory);

        // Camera.
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Filesystem.
        Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

        // Chooser of filesystem options.
        Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Source");

        // Add the camera options.
        //chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[cameraIntents.size()]));
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{cameraIntent});

        startActivityForResult(chooserIntent, 12345);

    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.ankita.locationandcamera/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.ankita.locationandcamera/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
