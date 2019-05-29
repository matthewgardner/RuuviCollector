package fi.tkgwf.ruuvi.db;

import fi.tkgwf.ruuvi.bean.EnhancedRuuviMeasurement;
import org.apache.log4j.Logger;
import org.influxdb.dto.Point;

public class DummyDBConnection implements DBConnection {

    private static final Logger LOG = Logger.getLogger(DummyDBConnection.class);

    @Override
    public void save(EnhancedRuuviMeasurement measurement) {
        LOG.debug(measurement);
    }

    public void save(Point point) {
        LOG.debug(point);
    }
    
    @Override
    public void close() {
    }
}
