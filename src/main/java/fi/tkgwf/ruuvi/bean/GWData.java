package fi.tkgwf.ruuvi.bean;

public class GWData {

    public String timestamp;
    public String type;
    public String mac;
    public String bleName;
    public Byte rssi;
    public String rawData;

    @Override
    public String toString() {
        return "GWData{" + "timestamp=" + timestamp + ", type=" + type + ", mac=" + mac + ", bleName=" + bleName + ", rssi=" + rssi + ", rawData=" + rawData + '}';
    }
}
