package model;

import java.time.LocalDate;

public class Equipment {
    private int id;
    private String name;
    private double temperature;
    private int voltage;
    private int current;
    private LocalDate lastCheck;

    public Equipment (int id, String name, double temperature, int voltage, int current, LocalDate lastCheck) {
        this.id = id;
        this.name = name;
        this.temperature = temperature;
        this.voltage = voltage;
        this.current = current;
        this.lastCheck = lastCheck;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getTemperature() {
        return temperature;
    }

    public int getVoltage(){
        return voltage;
    }

    public int getCurrent(){
        return current;
    }

    public LocalDate getLastCheck(){
        return lastCheck;
    }

    public void setId (int id){
        this.id = id;
    }

    public void setName (String name) {
        this.name = name;
    }

    public void setTemperature (double temperature){
        this.temperature = temperature;
    }

    public void setVoltage (int voltage) {
        this.voltage = voltage;
    }

    public void setCurrent (int current) {
        this.current = current;
    }

    public void setLastCheck (LocalDate lastCheck) {
        this.lastCheck = lastCheck;
    }

    public void chekName (String name) {
        System.out.println("Оборудование: " + name);
    }

    public boolean chekTemperature (double temperature) {
        return temperature >= 0 && temperature <= 80;
    }

    public boolean chekVoltage (int voltage) {
        return voltage >= 210 && voltage <= 230;
    }

    public boolean chekCurrent (int current) {
        return current >= 0 && current <= 20;
    }

    public boolean chekStatus() {
        return chekVoltage(voltage) && chekCurrent(current) && chekTemperature(temperature);
    }

}
