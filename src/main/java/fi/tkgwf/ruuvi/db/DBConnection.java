package fi.tkgwf.ruuvi.db;

import org.influxdb.dto.Point;

import fi.tkgwf.ruuvi.bean.EnhancedRuuviMeasurement;

public interface DBConnection {

    /**
     * Saves the measurement
     *
     * @param measurement
     */
    void save(EnhancedRuuviMeasurement measurement);

    void save(Point point);
    
    /**
     * Closes the DB connection
     */
    void close();
}
