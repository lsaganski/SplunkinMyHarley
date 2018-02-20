package br.com.mobila.splunkinmyharley;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by leonardo.saganski on 10/01/17.
 */

public class SMHSyncBroadcast extends BroadcastReceiver {

    public static final int REQUEST_CODE = 12345;
    public static final String ACTION = "splunkin.my.harley.SEND";

    @Override
    public void onReceive(Context context, Intent intent) {
        Send();
    }

    private void Send() {
/*
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
        DateFormat format = new SimpleDateFormat("'wallclock('yyyy-MM-dd'T'HH:mm:ss')'");
        msg.setTime(format.format(new Date()));

        msg.event = ev;

        String obj = new Gson().toJson(msg);

        SMHApi.shared().SendToSplunk(obj);*/
    }
}
