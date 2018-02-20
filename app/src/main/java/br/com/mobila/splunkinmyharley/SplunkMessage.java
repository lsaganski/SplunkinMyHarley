package br.com.mobila.splunkinmyharley;

import java.util.List;

/**
 * Created by lsaganski on 05/09/17.
 */

public class SplunkMessage {
    public String time;
    public String host;
    public String source;
    public String sourcetype;
    public SplunkEvent event;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourcetype() {
        return sourcetype;
    }

    public void setSourcetype(String sourcetype) {
        this.sourcetype = sourcetype;
    }

    public SplunkEvent getEvent() {
        return event;
    }

    public void setEvent(SplunkEvent event) {
        this.event = event;
    }
}

class SplunkEvent {
    public String parser;
    public String parserVersion;
    public int SpeedMetric;
    public int SpeedImperial;
    public int RPM;
    public double Latitude;
    public double Longitude;
    public int Gear;
    public boolean Neutral;
    public int OdometerMetric;
    public int OdometerImperial;
    public String TurnSignal;
    public int FuelGauge;
    public int FuelConsumptionInstantMetric;
    public int FuelConsumptionInstantImperial;
    public int FuelConsumptionAverageMetric;
    public int FuelConsumptionAverageImperial;
    public boolean CheckEngine;
    public int FuelMetric;
    public int FuelImperial;
    public boolean Clutch;
    public int EngineTemp;
    public boolean FuelLow;
    public String VIN;
    public String ECMPN;
    public String ECMCalID;
    public int ECMSWLevel;
    public List<String> HistoricDTC;
    public List<String> mCurrentDTC;

    public String getParser() {
        return parser;
    }

    public void setParser(String parser) {
        this.parser = parser;
    }

    public String getParserVersion() {
        return parserVersion;
    }

    public void setParserVersion(String parserVersion) {
        this.parserVersion = parserVersion;
    }

    public int getRPM() {
        return RPM;
    }

    public void setRPM(int RPM) {
        this.RPM = RPM;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public int getGear() {
        return Gear;
    }

    public void setGear(int gear) {
        Gear = gear;
    }

    public boolean isNeutral() {
        return Neutral;
    }

    public void setNeutral(boolean neutral) {
        Neutral = neutral;
    }

    public String getVIN() {
        return VIN;
    }

    public void setVIN(String VIN) {
        this.VIN = VIN;
    }

    public String getTurnSignal() {
        return TurnSignal;
    }

    public void setTurnSignal(String turnSignal) {
        TurnSignal = turnSignal;
    }

    public int getFuelGauge() {
        return FuelGauge;
    }

    public void setFuelGauge(int fuelGauge) {
        FuelGauge = fuelGauge;
    }

    public boolean isCheckEngine() {
        return CheckEngine;
    }

    public void setCheckEngine(boolean checkEngine) {
        CheckEngine = checkEngine;
    }

    public int getSpeedMetric() {
        return SpeedMetric;
    }

    public void setSpeedMetric(int speedMetric) {
        SpeedMetric = speedMetric;
    }

    public int getSpeedImperial() {
        return SpeedImperial;
    }

    public void setSpeedImperial(int speedImperial) {
        SpeedImperial = speedImperial;
    }

    public int getOdometerMetric() {
        return OdometerMetric;
    }

    public void setOdometerMetric(int odometerMetric) {
        OdometerMetric = odometerMetric;
    }

    public int getOdometerImperial() {
        return OdometerImperial;
    }

    public void setOdometerImperial(int odometerImperial) {
        OdometerImperial = odometerImperial;
    }

    public int getFuelConsumptionInstantMetric() {
        return FuelConsumptionInstantMetric;
    }

    public void setFuelConsumptionInstantMetric(int fuelConsumptionInstantMetric) {
        FuelConsumptionInstantMetric = fuelConsumptionInstantMetric;
    }

    public int getFuelConsumptionInstantImperial() {
        return FuelConsumptionInstantImperial;
    }

    public void setFuelConsumptionInstantImperial(int fuelConsumptionInstantImperial) {
        FuelConsumptionInstantImperial = fuelConsumptionInstantImperial;
    }

    public int getFuelConsumptionAverageMetric() {
        return FuelConsumptionAverageMetric;
    }

    public void setFuelConsumptionAverageMetric(int fuelConsumptionAverageMetric) {
        FuelConsumptionAverageMetric = fuelConsumptionAverageMetric;
    }

    public int getFuelConsumptionAverageImperial() {
        return FuelConsumptionAverageImperial;
    }

    public void setFuelConsumptionAverageImperial(int fuelConsumptionAverageImperial) {
        FuelConsumptionAverageImperial = fuelConsumptionAverageImperial;
    }

    public String getECMPN() {
        return ECMPN;
    }

    public void setECMPN(String ECMPN) {
        this.ECMPN = ECMPN;
    }

    public String getECMCalID() {
        return ECMCalID;
    }

    public void setECMCalID(String ECMCalID) {
        this.ECMCalID = ECMCalID;
    }

    public int getECMSWLevel() {
        return ECMSWLevel;
    }

    public void setECMSWLevel(int ECMSWLevel) {
        this.ECMSWLevel = ECMSWLevel;
    }

    public List<String> getHistoricDTC() {
        return HistoricDTC;
    }

    public void setHistoricDTC(List<String> historicDTC) {
        HistoricDTC = historicDTC;
    }

    public List<String> getmCurrentDTC() {
        return mCurrentDTC;
    }

    public void setmCurrentDTC(List<String> mCurrentDTC) {
        this.mCurrentDTC = mCurrentDTC;
    }

    public boolean isClutch() {
        return Clutch;
    }

    public void setClutch(boolean clutch) {
        Clutch = clutch;
    }

    public int getEngineTemp() {
        return EngineTemp;
    }

    public void setEngineTemp(int engineTemp) {
        EngineTemp = engineTemp;
    }

    public boolean isFuelLow() {
        return FuelLow;
    }

    public void setFuelLow(boolean fuelLow) {
        FuelLow = fuelLow;
    }

    public int getFuelMetric() {
        return FuelMetric;
    }

    public void setFuelMetric(int fuelMetric) {
        FuelMetric = fuelMetric;
    }

    public int getFuelImperial() {
        return FuelImperial;
    }

    public void setFuelImperial(int fuelImperial) {
        FuelImperial = fuelImperial;
    }
}
