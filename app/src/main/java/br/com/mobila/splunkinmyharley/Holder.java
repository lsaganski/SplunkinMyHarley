package br.com.mobila.splunkinmyharley;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;

import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by lsaganski on 05/09/17.
 */

public class Holder {

    private static Holder instance;

    public int STORED_RPM;
    public int STORED_SPEED_IMPERIAL;
    public int STORED_SPEED_METRIC;
    public int STORED_ENGINETEMP_IMPERIAL;
    public int STORED_ENGINETEMP_METRIC;
    public int STORED_FUELGAUGE;
    public boolean STORED_LOW_FUEL;
    public int STORED_TURNSIGNALS;
    public boolean STORED_NEUTRAL;
    public boolean STORED_CLUTCH;
    public int STORED_GEAR;
    public boolean STORED_CHECKENGINE;
    public int STORED_ODOMETER_IMPERIAL;
    public int STORED_ODOMETER_METRIC;
    public int STORED_FUEL_IMPERIAL;
    public int STORED_FUEL_METRIC;
    public int STORED_FUEL_AVERAGE_IMPERIAL;
    public int STORED_FUEL_AVERAGE_METRIC;
    public int STORED_FUEL_INSTANT_IMPERIAL;
    public int STORED_FUEL_INSTANT_METRIC;
    public String STORED_VIN;
    public double STORED_LATITUDE;
    public double STORED_LONGITUDE;
    public String STORED_ECM_PN;
    public String STORED_ECM_CALL_ID;
    public int STORED_ECM_SW_LEVEL;
    public List<String> STORED_CURRENT_DTC;
    public List<String> STORED_HISTORIC_DTC;

    public Context context;
    public Handler UIHandler;

    Intent broadcastIntent;
    PendingIntent pIntent;

    public Handler handler = new Handler();
    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Send();
            handler.postDelayed(this, 5000);
        }
    };

    public static Holder shared() {
        if (instance == null)
            instance = new Holder();

        return instance;
    }

    public void reset() {
        instance = new Holder();
    }

/*    public void setupAlarm() {
        cancelAlarm();
        // Setup periodic alarm every 5 seconds
        AlarmManager alarm = (AlarmManager) ((HarleyDroid) context).getSystemService(Context.ALARM_SERVICE);
        // First parameter is the type: ELAPSED_REALTIME, ELAPSED_REALTIME_WAKEUP, RTC_WAKEUP
        // Interval can be INTERVAL_FIFTEEN_MINUTES, INTERVAL_HALF_HOUR, INTERVAL_HOUR, INTERVAL_DAY

        long interval = 5000;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
     //       alarm.setExactAndAllowWhileIdle(Alarm);
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {

        } else {
            alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, getNextTime(),
                    interval, pIntent);    // AlarmManager.INTERVAL_FIFTEEN_MINUTES
        }
    }

    public void cancelAlarm() {
        // Create a PendingIntent to be triggered when the alarm goes off
        broadcastIntent = new Intent(SMHSyncBroadcast.ACTION);
        pIntent = PendingIntent.getBroadcast(context, SMHSyncBroadcast.REQUEST_CODE,
                broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarm = (AlarmManager) ((HarleyDroid) context).getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(pIntent);
    }

    private long getNextTime() {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        c.add(Calendar.SECOND, 5);
        long time = c.getTimeInMillis();
        return time;
    }*/

    private void Send() {

        Holder.shared().UIHandler.sendEmptyMessage(100);

        SplunkMessage msg = new SplunkMessage();
        SplunkEvent ev = new SplunkEvent();
        ev.setSpeedMetric(Holder.shared().STORED_SPEED_METRIC);
        ev.setSpeedImperial(Holder.shared().STORED_SPEED_IMPERIAL);
        ev.setRPM(Holder.shared().STORED_RPM);
        ev.setLatitude(Holder.shared().STORED_LATITUDE);
        ev.setLongitude(Holder.shared().STORED_LONGITUDE);
        ev.setGear(Holder.shared().STORED_GEAR);
        ev.setNeutral(Holder.shared().STORED_NEUTRAL);
        ev.setVIN(Holder.shared().STORED_VIN);
        ev.setOdometerMetric(Holder.shared().STORED_ODOMETER_METRIC);
        ev.setOdometerImperial(Holder.shared().STORED_ODOMETER_IMPERIAL);
        ev.setTurnSignal(Holder.shared().STORED_TURNSIGNALS == 1 ? "Right" :
                Holder.shared().STORED_TURNSIGNALS == 2 ? "Left" :
                        Holder.shared().STORED_TURNSIGNALS == 3 ? "Both" : "None");
        ev.setCheckEngine(Holder.shared().STORED_CHECKENGINE);
        ev.setFuelGauge(Holder.shared().STORED_FUELGAUGE);
        ev.setFuelConsumptionInstantMetric(Holder.shared().STORED_FUEL_INSTANT_METRIC);
        ev.setFuelConsumptionInstantImperial(Holder.shared().STORED_FUEL_INSTANT_IMPERIAL);
        ev.setFuelConsumptionAverageMetric(Holder.shared().STORED_FUEL_AVERAGE_METRIC);
        ev.setFuelConsumptionAverageImperial(Holder.shared().STORED_FUEL_AVERAGE_IMPERIAL);
        ev.setEngineTemp(Holder.shared().STORED_FUEL_AVERAGE_IMPERIAL);
        ev.setFuelLow(Holder.shared().STORED_LOW_FUEL);
        ev.setClutch(Holder.shared().STORED_CLUTCH);
        ev.setFuelMetric(Holder.shared().STORED_FUEL_METRIC);
        ev.setFuelImperial(Holder.shared().STORED_FUEL_IMPERIAL);
        ev.setECMPN(Holder.shared().STORED_ECM_PN);
        ev.setECMCalID(Holder.shared().STORED_ECM_CALL_ID);
        ev.setECMSWLevel(Holder.shared().STORED_ECM_SW_LEVEL);
        ev.setmCurrentDTC(Holder.shared().STORED_CURRENT_DTC);
        ev.setHistoricDTC(Holder.shared().STORED_HISTORIC_DTC);

        msg.setHost(Holder.shared().STORED_VIN);

        msg.setTime(String.valueOf(new Date().getTime() / 1000));

        msg.event = ev;

        String obj = new Gson().toJson(msg);

        SMHApi.shared().SendToSplunk(obj);
    }
}
