package fi.tkgwf.ruuvi.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.influxdb.dto.Point;
import org.junit.jupiter.api.Test;

import fi.tkgwf.ruuvi.bean.SonoffRaw;

class TasmotaUtilTest {

    @Test
    void convertToSonoffRawTest() {
        String testLine = "{\"StatusSNS\":{\"Time\":\"2019-05-28T23:04:17\",\"ENERGY\":{\"TotalStartTime\":\"2019-05-27T16:38:57\",\"Total\":0.049,\"Yesterday\":0.032,\"Today\":0.017,\"Power\":18,\"ApparentPower\":29,\"ReactivePower\":23,\"Factor\":0.63,\"Voltage\":238,\"Current\":0.124}}}";
        SonoffRaw value = TasmotaUtil.convertToSonoffRaw("test", testLine);
        assertEquals(value.getVoltage(),238d,0.1);
        assertEquals(value.getPower(),18d,0.01);
    }
}
