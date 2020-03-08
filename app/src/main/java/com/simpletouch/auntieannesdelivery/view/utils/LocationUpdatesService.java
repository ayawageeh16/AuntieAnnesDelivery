package com.simpletouch.auntieannesdelivery.view.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.simpletouch.auntieannesdelivery.R;
import com.simpletouch.auntieannesdelivery.view.view.MainActivity;
import com.simpletouch.business.repositories.OrderFireBaseRepository;
import com.simpletouch.entities.model.OrderDelivery;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

public class LocationUpdatesService extends Service {
    private final int PERMISSION_REQUEST_CODE = 100;
    // private long MAX_WAIT_TIME = 5 * 1000;  /* 10 secs */
    // private long FASTEST_INTERVAL = 2000; /* 2 sec */

    private long UPDATE_INTERVAL = 10 * 1000;  /* 10 secs */ /*location updates every 10 secs */
    private long MAX_WAIT_TIME = 60 * 1000;  /* 1 min */ /* 6 location updates batch will be delivered every minute */
    private long FASTEST_INTERVAL = 2 * 60 * 1000; /* 2 min */ /* if any other app requested location updates it will update this app in 2 min */
    private int REQUEST_CHECK_SETTINGS = 101;
    public static final String CHANNEL_ID = "ForegroundServiceChannel";

    public String input;
    private boolean mLocationPermissionGranted;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private LocationRequest mLocationRequest;
    private NotificationManager notificationManager;
    private Notification notification;
    private OrderFireBaseRepository orderFireBaseRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                onLocationChanged(locationResult.getLastLocation());
            }
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        input = intent.getStringExtra("inputExtra");

        createNotificationChannel();
        createNotification();

        orderFireBaseRepository = new OrderFireBaseRepository();
        fusedLocationClient = getFusedLocationProviderClient(this);

        // do heavy work on a background thread
        // stopSelf();
        // Location setup
        createLocationRequest();
        startLocationUpdates();

        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int code = googleApiAvailability.isGooglePlayServicesAvailable(this);
        switch (code) {
            case ConnectionResult.SUCCESS:
                Log.d("googleApiAvailability", "Google Play services APK is up-to-date ");
                break;
            case ConnectionResult.SERVICE_MISSING:
                Log.d("googleApiAvailability", "Google Play services is missing on this device.");
                break;
            case ConnectionResult.SERVICE_DISABLED:
                Log.d("googleApiAvailability", "then the user needs to install an update");
                break;
        }

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopLocationUpdates();
    }

    private void stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_LOW
            );
            notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(serviceChannel);
        }
    }

    private void createNotification() {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("AuntieAnne's Delivery")
                .setContentText(input)
                .setSmallIcon(R.drawable.layer_594_copy_2)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);
    }

    /**
     * Create Location Request
     */
    private void createLocationRequest() {
        // Create the location request to start receiving updates
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setMaxWaitTime(MAX_WAIT_TIME);
    }

    /**
     * Trigger new location updates at interval
     */
    private void startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(mLocationRequest,
                locationCallback,
                Looper.getMainLooper());
    }

    /**
     * On Location Changed Update Firebase With New Location
     *
     * @param location
     */
    public void onLocationChanged(Location location) {
        // New location has now been determined
        String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        input = msg;

        OrderDelivery orderDelivery = new OrderDelivery();
        orderDelivery.setDeliveryManId(1);
        orderDelivery.setOrderId(20);

        orderDelivery.setLatitude(location.getLatitude());
        orderDelivery.setLongitude(location.getLongitude());

        updateCurrentOrderLatAndLongOnFirebase(orderDelivery);
    }

    public void updateCurrentOrderLatAndLongOnFirebase(OrderDelivery orderDelivery) {
        orderFireBaseRepository.addNewNodeToFirebaseDatabase(orderDelivery);
    }

    /**
     * Get last known recent location using new Google Play Services SDK (v11+)
     * Unused
     */
    public void getLastLocation() {

        FusedLocationProviderClient locationClient = getFusedLocationProviderClient(this);
        locationClient.getLastLocation()
                .addOnSuccessListener(location -> {
                    // GPS location can be null if GPS is switched off
                    if (location != null) {
                        //  onLocationChanged(location);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.d("MapDemoActivity", "Error trying to get last GPS location");
                    e.printStackTrace();
                });
    }

}
