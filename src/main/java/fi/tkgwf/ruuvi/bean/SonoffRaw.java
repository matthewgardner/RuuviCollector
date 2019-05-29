package fi.tkgwf.ruuvi.bean;

public class SonoffRaw {

   private String deviceName;
   private Double voltage;
   private Double current;
   private Double power;
   private Double reactivePower;
   private Double apparentPower;

   //TODO: Use Builder pattern
   public SonoffRaw(String name, Double voltage, Double current, Double power, Double reactivePower, Double apparentPower) {
      this.deviceName = name;
      this.voltage = voltage;
      this.current = current;
      this.power = power;
      this.reactivePower = reactivePower;
      this.apparentPower = apparentPower;
   }

   public Double getVoltage() {
      return this.voltage;
   }

   public Double getCurrent() {
      return this.current;
   }

   public Double getPower() {
      return this.power;
   }

   public Double getReactivePower() {
      return this.reactivePower;
   }

   public Double getApparentPower() {
      return this.apparentPower;
   }

   public String getDeviceName() {
      return this.deviceName;
   }

}
