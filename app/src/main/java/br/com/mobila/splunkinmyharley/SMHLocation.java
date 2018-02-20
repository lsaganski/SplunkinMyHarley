package br.com.mobila.splunkinmyharley;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * Created by Leonardo Saganski on 27/11/16.
 */
public class SMHLocation implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static SMHLocation instance;

    public static final String TAG = "SMHLocation";
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    Context _context;

    public static SMHLocation shared() {
        if (instance == null)
            instance = new SMHLocation();

        return instance;
    }

    public SMHLocation() {
        try {
            _context = Holder.shared().context;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                checkForPermission();

            mGoogleApiClient = new GoogleApiClient.Builder(_context)
                    .addOnConnectionFailedListener(this)
                    .addConnectionCallbacks(this)
                    .addApi(LocationServices.API)
                    .build();
        } catch (Exception e) { }
    }

    public void startLocation() {
        if (!mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        }
    }

    public void stopLocation() {
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    public void stopService() {
        try {
            if (mGoogleApiClient.isConnected()) {
                Intent intent = new Intent(_context, SMHLocationService.class);
                PendingIntent pendingIntent = PendingIntent.getService(_context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, pendingIntent);
                pendingIntent.cancel();
            }
        } catch (Exception e) {

        }
    }

    // LISTENER
    @Override
    public void onConnected(Bundle bundle) {
        try {
            if (ContextCompat.checkSelfPermission(_context, Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(_context, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                Location l = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

                mLocationRequest = LocationRequest.create()
                        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                        .setInterval(5000)
                        .setFastestInterval(2500);

                Intent intent = new Intent(_context, SMHLocationService.class);
                PendingIntent pendingIntent = PendingIntent.getService(_context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, pendingIntent);
            } else {
                checkForPermission();
            }
        } catch (Exception e) {


        }
    }

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {}

    @TargetApi(Build.VERSION_CODES.M)
    private void checkForPermission() {
        int okCoarse = ContextCompat.checkSelfPermission(_context, Manifest.permission.ACCESS_COARSE_LOCATION);
        int okFine = ContextCompat.checkSelfPermission(_context, Manifest.permission.ACCESS_FINE_LOCATION);
        if (okCoarse == PackageManager.PERMISSION_GRANTED && okFine == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Granted");
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale((HarleyDroid)Holder.shared().context, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                Log.d(TAG, "Contacts Permission Required!!");
            }
            if (ActivityCompat.shouldShowRequestPermissionRationale((HarleyDroid)Holder.shared().context, Manifest.permission.CAMERA)) {
                Log.d(TAG, "Contacts Permission Required!!");
            }
            ActivityCompat.
                    requestPermissions((HarleyDroid)Holder.shared().context, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }
}
