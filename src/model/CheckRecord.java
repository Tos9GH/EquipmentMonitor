package model;
import java.time.LocalDateTime;


public class CheckRecord {
    private int equipmentId;
    private String equipmentName;
    private LocalDateTime checkDateTime;
    private double temperature;
    private int voltage;
    private int current;
    private boolean temperatureNormal;
    private boolean voltageNormal;
    private boolean currentNormal;
    private EquipmentStatus overallStatus;

   public CheckRecord (int equipmentId, String equipmentName, LocalDateTime checkDateTime, double temperature, int voltage, int current, boolean temperatureNormal, boolean voltageNormal, boolean currentNormal, EquipmentStatus overallStatus) {
        this.equipmentId = equipmentId;
        this.equipmentName = equipmentName;
        this.checkDateTime = checkDateTime;
        this.temperature = temperature;
        this.voltage = voltage;
        this.current = current;
        this.temperatureNormal = temperatureNormal;
        this.voltageNormal = voltageNormal;
        this.currentNormal = currentNormal;
        this.overallStatus = overallStatus;

    }

    public int getEquipmentId() {
        return equipmentId;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public LocalDateTime getCheckDateTime() {
        return checkDateTime;
    }

    public double getTemperature() {
        return temperature;
    }

    public int getVoltage() {
        return voltage;
    }

    public int getCurrent() {
        return current;
    }

    public boolean isTemperatureNormal() {
        return temperatureNormal;
    }

    public boolean isVoltageNormal() {
        return voltageNormal;
    }

    public boolean isCurrentNormal() {
        return currentNormal;
    }

    public EquipmentStatus getOverallStatus() {
        return overallStatus;
    }
}
