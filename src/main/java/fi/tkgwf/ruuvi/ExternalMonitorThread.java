package fi.tkgwf.ruuvi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.influxdb.dto.Point;
import org.json.JSONObject;

import fi.tkgwf.ruuvi.bean.SonoffRaw;
import fi.tkgwf.ruuvi.config.Config;
import fi.tkgwf.ruuvi.db.InfluxDBConnection;
import fi.tkgwf.ruuvi.utils.TasmotaUtil;

public class ExternalMonitorThread implements Runnable {

    private static final Logger LOG = Logger.getLogger(ExternalMonitorThread.class);
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private static final String DEVICE_ADDRESS = "192.168.87.31";
    private static final String DEVICE_NAME = "pump_energy";

    public void start() {
        final Runnable monitorRunner = new ExternalMonitorThread();
        scheduler.scheduleAtFixedRate(monitorRunner, 10, 10, TimeUnit.SECONDS);
    }

    public void run() {
        try {
            readUrl();
        } catch (Exception e) {
            LOG.error("InterruptedException", e);
        }
    }

    private static void readUrl() {
        try {
            URL url = new URL("http://" + DEVICE_ADDRESS + "/cm?cmnd=status%2010"); // TODO:make setting
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String output;
            while ((output = br.readLine()) != null) {
                System.out.println(output);
                processInput(output);
            }
            conn.disconnect();
        } catch (MalformedURLException e) {
            LOG.error("MalformedURLException", e);
        } catch (IOException e) {
            LOG.error("IOException", e);
        }

    }

    private static void processInput(String input) {
        SonoffRaw sonoffRaw = TasmotaUtil.convertToSonoffRaw(DEVICE_NAME, input);
        Point point = TasmotaUtil.convertToInfluxdbPoint(sonoffRaw);
        if (Config.getDBConnection() instanceof InfluxDBConnection) {
            ((InfluxDBConnection) Config.getDBConnection()).save(point);
        } else {
            LOG.warn("No database found to persist to");
        }
    }
}
