package com.simpletouch.auntieannesdelivery.view.view.features;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.simpletouch.auntieannesdelivery.R;
import com.simpletouch.auntieannesdelivery.databinding.ActivityOrdersDetailsBinding;
import com.simpletouch.auntieannesdelivery.view.listener.OrderDetailsListener;
import com.simpletouch.auntieannesdelivery.view.utils.LocationUpdatesService;
import com.simpletouch.auntieannesdelivery.view.utils.ViewUtils;
import com.simpletouch.entities.model.Order;

public class OrderDetailsActivity extends AppCompatActivity implements OnMapReadyCallback, OrderDetailsListener {

    private final int PERMISSION_REQUEST_CODE = 100;
    private String ORDER_OBJECT = "ORDER_OBJECT";
    private ActivityOrdersDetailsBinding ordersDetailsBinding;
    private OrdersViewModel ordersViewModel;

    // Map
    private GoogleMap googleMap;
    private MarkerOptions markerPlace1;
    private boolean mLocationPermissionGranted;
    private LocationRequest mLocationRequest;
    private int REQUEST_CHECK_SETTINGS = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ordersDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_orders_details);
        getExtras();
        init();
    }

    private void getExtras() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(ORDER_OBJECT)) {
                Order order = intent.getParcelableExtra(ORDER_OBJECT);
                ordersDetailsBinding.setOrder(order);
            }
        }
    }

    private void init() {
        ordersViewModel = ViewModelProviders.of(this).get(OrdersViewModel.class);
        ordersDetailsBinding.setOrderDetailsListener(this);

        // Map setup
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        markerPlace1 = new MarkerOptions().position(new LatLng(26.905400, 31.489300)).title("Destination");
        googleMap.addMarker(markerPlace1);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(26.905400, 31.489300), 15.0f));
    }

    /**
     * Handling Coarse And Background Location Permissions Request
     */
    private boolean requestPermission() {
        // If device uses OS more than 8 it needs permission first
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(OrderDetailsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            boolean permissionAccessCoarseLocationApproved =
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED;

            if (permissionAccessCoarseLocationApproved) {

                boolean backgroundLocationPermissionApproved = false;

                // If this device running OS 10 or higher ask for background location permission
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                    backgroundLocationPermissionApproved = ActivityCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                            == PackageManager.PERMISSION_GRANTED;

                    if (backgroundLocationPermissionApproved) {
                        // App can access location both in the foreground and in the background.
                        // Start your service that doesn't have a foreground service type
                        // defined.
                        Toast.makeText(this, "App can access location both in the foreground and in the background", Toast.LENGTH_SHORT).show();
                        // startLocationUpdates();
                        //getLastLocation();
                        startService();
                        return true;
                    } else {
                        // Background permission denied
                        // Display a dialog warning the user that your app must have all-the-time access to
                        // location in order to function properly. Then, request background location.
                        Toast.makeText(this, "App can only access location in the foreground", Toast.LENGTH_SHORT).show();
                        stopService();
                        ViewUtils.showAlertDialogWithSingleAction(this, new DialogInterface() {
                            @Override
                            public void cancel() {
                                ActivityCompat.requestPermissions(OrderDetailsActivity.this, new String[]{
                                                Manifest.permission.ACCESS_BACKGROUND_LOCATION},
                                        PERMISSION_REQUEST_CODE);
                            }

                            @Override
                            public void dismiss() {
                                dismiss();
                            }
                        }, getString(R.string.allow_permission), getString(R.string.please_allow_access_location_on_background));

                        return false;
                    }
                }
                // If device runs OS less than 10 start location updates with the granted coarse permission
                else {
                    //  startLocationUpdates();
                    startService();
                    return true;
                }
            } else {
                // App doesn't have access to the device's location at all. Make full request for permission.
                ActivityCompat.requestPermissions(this, new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_BACKGROUND_LOCATION
                        },
                        PERMISSION_REQUEST_CODE);
                return false;
            }
        } else {
            // If device uses OS less than 8 it doesn't need permission
            // startLocationUpdates();
            startService();
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (PERMISSION_REQUEST_CODE == requestCode) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted so start Location updates
                //startLocationUpdates();
                startService();
            } else {
                // Permission denied display dialog to inform user why app needs this permission and ask for permission again
                ViewUtils.showAlertDialogWithSingleAction(this, new DialogInterface() {
                    @Override
                    public void cancel() {
                        requestPermission();
                    }

                    @Override
                    public void dismiss() {
                    }
                }, getString(R.string.allow_permission), getString(R.string.please_allow_access_location));
            }
        }
    }


    /**
     * Check If Location Settings Enabled If Not Display Dialog To The User To Enable Location Settings
     */
    private void checkLocationSettings() {
        // Create LocationSettingsRequest object using location request
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        // Check whether location settings are satisfied
        // https://developers.google.com/android/reference/com/google/android/gms/location/SettingsClient
        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        settingsClient.checkLocationSettings(locationSettingsRequest).addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(Task<LocationSettingsResponse> task) {
                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    Log.d("locationSettingsRequest", " All location settings are satisfied. The client can initialize location requests here.");
                    // All location settings are satisfied. The client can initialize location
                    // requests here.

                } catch (ApiException exception) {
                    switch (exception.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            Log.d("locationSettingsRequest", "Location settings are not satisfied. But could be fixed by showing the user a dialog.");
                            // Location settings are not satisfied. But could be fixed by showing the
                            // user a dialog.
                            try {
                                // Cast to a resolvable exception.
                                ResolvableApiException resolvable = (ResolvableApiException) exception;
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                resolvable.startResolutionForResult(
                                        OrderDetailsActivity.this,
                                        REQUEST_CHECK_SETTINGS);
                            } catch (IntentSender.SendIntentException e) {
                                Log.d("locationSettingsRequest", e.getMessage());
                                // Ignore the error.
                            } catch (ClassCastException e) {
                                Log.d("locationSettingsRequest", e.getMessage());
                                // Ignore, should be an impossible error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            Log.d("locationSettingsRequest", " Location settings are not satisfied. However, we have no way to fix the settings so we won't show the dialog.");
                            // Location settings are not satisfied. However, we have no way to fix the
                            // settings so we won't show the dialog.
                            break;
                    }
                }
            }
        });
    }


    @Override
    public void onStartDeliveryClicked() {
        checkLocationSettings();
        if (requestPermission()) {
            startService();
        }
    }

    @Override
    public void onStopDeliveryClicked() {
        stopService();
    }

    public void startService() {

        Intent serviceIntent = new Intent(this, LocationUpdatesService.class);
        serviceIntent.putExtra("inputExtra", "Location tracking is working");
        ContextCompat.startForegroundService(this, serviceIntent);
    }

    public void stopService() {
        Intent serviceIntent = new Intent(this, LocationUpdatesService.class);
        stopService(serviceIntent);
    }

}