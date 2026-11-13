package service;

import model.CheckRecord;
import model.Equipment;
import model.EquipmentStatus;

import java.time.LocalDateTime;
import java.util.List;

public class CheckService {
    private final List<Equipment> equipmentList;
    private final HistoryService historyService;
        public CheckService (List<Equipment> equipmentList, HistoryService historyService) {
        this.equipmentList = equipmentList;
        this.historyService = historyService;
    }
    public CheckRecord checkEquipment(int id) {
            for (Equipment equipment : equipmentList) {
                if (equipment.getId() == id) {
                    boolean temperatureNormal = equipment.chekTemperature(equipment.getTemperature());
                    boolean voltageNormal = equipment.chekVoltage(equipment.getVoltage());
                    boolean currentNormal = equipment.chekCurrent(equipment.getCurrent());
                    EquipmentStatus overallStatus = temperatureNormal && currentNormal && voltageNormal ? EquipmentStatus.NORMAL : EquipmentStatus.WARNING;
                    LocalDateTime nowDateTime = LocalDateTime.now();
                    CheckRecord historyCheck = new CheckRecord(equipment.getId(), equipment.getName(), nowDateTime, equipment.getTemperature(), equipment.getVoltage(), equipment.getCurrent(), temperatureNormal, voltageNormal, currentNormal, overallStatus);
                    historyService.addRecord(historyCheck);
                    equipment.setLastCheck(nowDateTime.toLocalDate());
                    return historyCheck;
                }
            }
        return null;
    }
}
