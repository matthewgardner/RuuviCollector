package fi.tkgwf.ruuvi.utils;

import fi.tkgwf.ruuvi.bean.GWData;
import fi.tkgwf.ruuvi.bean.HCIData;
import org.apache.commons.lang3.StringUtils;

public class GWParser {

    public HCIData readData(GWData data) {
        if (data == null || StringUtils.length(data.mac) != 12 || StringUtils.isBlank(data.rawData) || data.rssi == null) {
            return null;
        }
        HCIParser hciParser = new HCIParser();
        StringBuilder sb = new StringBuilder("> 04 3E ");
        sb.append(toByte(data.rawData.length() / 2 + 11));
        sb.append(" 02 01 03 01 ");
        sb.append(addSpacesAndReverseMac(data.mac));
        sb.append(" ");
        sb.append(toByte(data.rawData.length() / 2 - 1));
        sb.append(" ");
        sb.append(addSpaces(data.rawData));
        sb.replace(sb.length() - 2, sb.length(), toByte(data.rssi));
        return hciParser.readLine(sb.toString());
    }

    private StringBuilder addSpacesAndReverseMac(String in) {
        if (StringUtils.isBlank(in)) {
            return new StringBuilder();
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < in.length(); i += 2) {
            sb.insert(0, (in.substring(i, i + 2))).insert(0, " ");
        }
        return sb.deleteCharAt(0);
    }

    private StringBuilder addSpaces(String in) {
        if (StringUtils.isBlank(in)) {
            return new StringBuilder();
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < in.length(); i += 2) {
            sb.append(in.substring(i, i + 2)).append(" ");
        }
        return sb.deleteCharAt(sb.length() - 1);
    }

    private String toByte(Number in) {
        return String.format("%02X", in.byteValue());
    }
}
