package service;

import model.Equipment;

import java.util.ArrayList;
import java.util.List;

public class EquipmentService {
    private final List<Equipment> equipmentList;
    public EquipmentService (List<Equipment> equipmentList) {
        this.equipmentList = equipmentList;
    }

    public Equipment findById (int id) {
        for (Equipment equipment : equipmentList) {
            if (equipment.getId() == id) {
                return equipment;
            }
        }
            return null;
    }

    public boolean addEquipment (Equipment equipment) {
        if (findById(equipment.getId()) != null) {
            return false;
        } else {
            equipmentList.add(equipment);
            return true;
        }
    }

    public List<Equipment> getAllEquipment () {
        return equipmentList;
    }

    public void setEquipmentList (List<Equipment> loadedEquipment) {
        equipmentList.clear();
        equipmentList.addAll(loadedEquipment);
    }

    public List<Equipment> getEquipmentWithDeviations() {
        List<Equipment> equipmentWithDeviations = new ArrayList<>();
        for (Equipment equipment : equipmentList) {
            if (!equipment.chekStatus()) {
                equipmentWithDeviations.add(equipment);
            }
        }
        return equipmentWithDeviations;
    }
}
