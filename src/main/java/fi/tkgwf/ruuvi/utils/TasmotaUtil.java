package fi.tkgwf.ruuvi.utils;

import org.influxdb.dto.Point;
import org.json.JSONObject;
import fi.tkgwf.ruuvi.bean.SonoffRaw;

public class TasmotaUtil {

    public static SonoffRaw convertToSonoffRaw(String name, String input) {

        JSONObject inputObj = new JSONObject(input);
        JSONObject statusObj = inputObj.getJSONObject("StatusSNS");
        JSONObject energyObj = statusObj.getJSONObject("ENERGY");
        Double voltage = energyObj.getDouble("Voltage");
        Double current = energyObj.getDouble("Current");
        Double power = energyObj.getDouble("Power");
        Double reactivePower = energyObj.getDouble("ReactivePower");
        Double apparentPower = energyObj.getDouble("ApparentPower");
        SonoffRaw sonoffRaw = new SonoffRaw(name, voltage, current, power, reactivePower, apparentPower);
        return sonoffRaw;
    }

    public static Point convertToInfluxdbPoint(SonoffRaw sonoffRaw) {
        Point.Builder p = Point.measurement("power_monitor").tag("device", sonoffRaw.getDeviceName());
        p.addField("Voltage", sonoffRaw.getVoltage());
        p.addField("Current", sonoffRaw.getCurrent());
        p.addField("Power", sonoffRaw.getPower());
        p.addField("ReactivePower", sonoffRaw.getReactivePower());
        p.addField("ApparentPower", sonoffRaw.getApparentPower());
        return p.build();
    }
}
