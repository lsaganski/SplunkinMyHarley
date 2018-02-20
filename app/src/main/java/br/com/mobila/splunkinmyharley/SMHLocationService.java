package br.com.mobila.splunkinmyharley;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.location.Location;

import com.google.android.gms.location.LocationResult;

/**
 * Created by leonardo.saganski on 30/11/16.
 */

public class SMHLocationService extends IntentService {

    private static SMHLocationService instance;

    public static final String TAG = "SMHLocationService";
    Context _context;

    public static SMHLocationService shared() {
        if (instance == null)
            instance = new SMHLocationService();

        return instance;
    }

    public SMHLocationService() {
        super("SMHLocationService");
        _context = Holder.shared().context;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            if (LocationResult.hasResult(intent)) {
                LocationResult result = LocationResult.extractResult(intent);

                if (result != null) {
                    Location location = result.getLastLocation();

                    Holder.shared().STORED_LATITUDE = location.getLatitude();
                    Holder.shared().STORED_LONGITUDE = location.getLongitude();
                }
            }
        } catch (Exception e) {}
    }
}
