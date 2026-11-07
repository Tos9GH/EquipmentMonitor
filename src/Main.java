import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;
import model.EquipmentStatus;
import model.CheckRecord;

public class Main {
    public static void main(String[] args) {
        ArrayList<Equipment> equipmentList = new ArrayList<>();
        ArrayList<CheckRecord> historyList = new ArrayList<>();
        equipmentList.add(new Equipment(1, "Насос", 45.1, 220, 10, LocalDate.of(2026,10,5)));
        equipmentList.add(new Equipment(2, "Трансформатор", 50, 210,15, LocalDate.of(2026, 9, 15)));
        Scanner scanner = new Scanner(System.in);
        int numMenu = 0;
        int idInput = 0;
        String nameInput = null;
        double temperatureInput = 0;
        int voltageInput = 0;
        int currentInput = 0;
        LocalDate newLocalDate = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

        while (numMenu != 9) {
            try {
                menuConsole();
                System.out.println("Выберете необходимое действие:");
                numMenu = scanner.nextInt();
                switch (numMenu) {
                    case 1:
                        System.out.println("Добавление оборудования");
                        System.out.println("Введите ID");
                            boolean whileStatus = false;
                                while (!whileStatus) {
                                    whileStatus = true;
                                    try {
                                    idInput = scanner.nextInt();
                                    for (Equipment equipment : equipmentList) {
                                        if (equipment.getId() == idInput) {
                                            System.out.println("Введен повторяющийся ID");
                                            System.out.println("Введите новый ID");
                                            whileStatus = false;
                                            break;
                                            }
                                        }
                                    }
                                    catch (InputMismatchException e) {
                                        System.out.println("Введите уникальный ID (целое число)");
                                        scanner.next();
                                        whileStatus = false;
                                    }
                                }

                            System.out.println("Введите название");
                        scanner.nextLine();
                                boolean nameStatus = false;
                                while (!nameStatus) {
                                    nameInput = scanner.nextLine();
                                    if (!nameInput.isBlank()) {
                                        nameStatus = true;
                                    } else {
                                        System.out.println("Название не может быть пустым");
                                        nameStatus = false;
                                    }
                                }

                            System.out.println("Введите температуру");
                            boolean temperatureStatus = false;
                            while (!temperatureStatus) {
                                try {
                                    temperatureInput = scanner.nextDouble();
                                    temperatureStatus = true;
                                } catch (InputMismatchException e) {
                                    System.out.println("Введите температуру");
                                    scanner.next();
                                    temperatureStatus = false;
                                }
                            }
                        System.out.println("Введите напряжение");
                            boolean voltageStatus = false;
                            while (!voltageStatus) {
                                try {
                                    voltageInput = scanner.nextInt();
                                    voltageStatus = true;
                                } catch (InputMismatchException e) {
                                    System.out.println("Введите напряжение");
                                    scanner.next();
                                    voltageStatus = false;
                                }
                            }
                        System.out.println("Введите ток");
                            boolean currentStatus = false;
                            while (!currentStatus) {
                                try {
                                    currentInput = scanner.nextInt();
                                    currentStatus = true;
                                } catch (InputMismatchException e) {
                                    System.out.println("Введите ток");
                                    scanner.next();
                                    currentStatus = false;
                                }
                            }

                            boolean dateStatus = false;
                        System.out.println("Введите дату последней проверки (в формате ГГГГ-ММ-ДД)");
                            while (!dateStatus) {
                                try {

                                    String localDateInput = scanner.next();
                                    newLocalDate = LocalDate.parse(localDateInput);
                                    dateStatus = true;

                                } catch (DateTimeParseException e) {
                                    System.out.println("Введите дату последней проверки (в формате ГГГГ-ММ-ДД");
                                }
                            }

                        equipmentList.add(new Equipment(idInput ,nameInput, temperatureInput, voltageInput, currentInput, newLocalDate));
                        System.out.println("Оборудование успешно добавлено");
                        break;
                    case 2:
                        System.out.println("Список оборудования");
                        for (Equipment equipment : equipmentList) {
                            System.out.println("ID: " + equipment.getId()
                                    + " | Название: " + equipment.getName()
                                    + " | Температура: " + equipment.getTemperature()
                                    + " | Напряжение: " + equipment.getVoltage()
                                    + " | Ток: " + equipment.getCurrent()
                                    + " | Дата последней проверки: " + equipment.getLastCheck());
                            if (equipment.chekTemperature(equipment.getTemperature())) {
                                System.out.println("Температура: норма");
                            } else {
                                System.out.println("Температура: отклонение");
                            }
                            if (equipment.chekVoltage(equipment.getVoltage())) {
                                System.out.println("Напряжение: норма");
                            } else {
                                System.out.println("Напряжение: отклонение");
                            }
                            if (equipment.chekCurrent(equipment.getCurrent())) {
                                System.out.println("Ток: норма");
                            } else {
                                System.out.println("Ток: отклонение");
                            }
                            if (equipment.chekStatus()) {
                                System.out.println("Общий статус: оборудование в норме");
                            } else {
                                System.out.println("Общий статус: обнаружено отклонение");
                            }
                            System.out.println();
                        }
                        break;
                    case 3:
                        boolean found = false;

                        try {
                            System.out.println("Введите ID");
                            int searchID = scanner.nextInt();

                            for (Equipment equipment : equipmentList) {
                                if (equipment.getId() == searchID) {
                                    System.out.println("Найдено оборудование:");
                                    System.out.println("ID: " + equipment.getId()
                                            + " | Название: " + equipment.getName());

                                    found = true;
                                    break;
                                }
                            }

                            if (!found) {
                                System.out.println("Оборудование не найдено");
                            }

                        } catch (InputMismatchException e) {
                            System.out.println("Введите целое число");
                            scanner.next();
                        }
                        break;
                    case 4:
                        boolean foundID = false;

                                try {
                                System.out.println("Введите ID");
                                int searchStatusID = scanner.nextInt();

                                for (Equipment equipment : equipmentList) {
                                    if (equipment.getId() == searchStatusID) {
                                        boolean temperatureNormal = equipment.chekTemperature(equipment.getTemperature());
                                        boolean voltageNormal = equipment.chekVoltage(equipment.getVoltage());
                                        boolean currentNormal = equipment.chekCurrent(equipment.getCurrent());
                                        EquipmentStatus overallStatus = temperatureNormal && currentNormal && voltageNormal ? EquipmentStatus.NORMAL : EquipmentStatus.WARNING;
                                        if (temperatureNormal) {
                                            System.out.println("Температура: норма");
                                        } else {
                                            System.out.println("Температура: отклонение");
                                        }
                                        if (voltageNormal) {
                                            System.out.println("Напряжение: норма");
                                        } else {
                                            System.out.println("Напряжение: отклонение");
                                        }
                                        if (currentNormal) {
                                            System.out.println("Ток: норма");
                                        } else {
                                            System.out.println("Ток: отклонение");
                                        }
                                        if (overallStatus == EquipmentStatus.NORMAL) {
                                            System.out.println("Общий статус: оборудование в норме");
                                        } else {
                                            System.out.println("Общий статус: обнаружено отклонение");
                                        }
                                        LocalDateTime nowDateTime = LocalDateTime.now();
                                        historyList.add(new CheckRecord(equipment.getId(), equipment.getName(), nowDateTime, equipment.getTemperature(), equipment.getVoltage(), equipment.getCurrent(), temperatureNormal, voltageNormal, currentNormal, overallStatus));
                                        equipment.setLastCheck(nowDateTime.toLocalDate());
                                        foundID = true;
                                        break;
                                    }
                                }

                                if (!foundID) {
                                    System.out.println("Оборудование не найдено");
                                }

                            } catch (InputMismatchException e) {
                                System.out.println("Введен неверный ID");
                                scanner.next();
                                }

                        break;
                    case 5:
                        boolean listStatusEquipment = false;
                        System.out.println("Оборудование с отклонениями:");
                        for (Equipment equipment : equipmentList) {
                            if (!equipment.chekStatus()) {
                                System.out.println("ID: " + equipment.getId()
                                        + " | Название: " + equipment.getName()
                                        + " | Температура: " + equipment.getTemperature()
                                        + " | Напряжение: " + equipment.getVoltage()
                                        + " | Ток: " + equipment.getCurrent()
                                        + " | Дата последней проверки: " + equipment.getLastCheck());
                                System.out.println("Общий статус: обнаружено отклонение");
                                listStatusEquipment = true;
                            }
                        }
                        if (!listStatusEquipment) {
                            System.out.println("Оборудование с отклонениями отсутствует");
                        }
                        break;
                    case 6:
                        System.out.println("История проверок");
                        if (historyList.isEmpty()) {
                            System.out.println("История проверок отсутствует");
                        } else {
                            historyList.forEach(checkRecord -> System.out.println("ID: " + checkRecord.getEquipmentId()
                                    + " | Название: " + checkRecord.getEquipmentName()
                                    + "\nПоследняя проверка: " + checkRecord.getCheckDateTime().format(formatter)
                                    + "\nТемпература: " + checkRecord.getTemperature()
                                    + " - " + (checkRecord.isTemperatureNormal() ? "Норма" : "Отклонение")
                                    + "\nНапряжение: " + checkRecord.getVoltage()
                                    + " - " + (checkRecord.isVoltageNormal() ? "Норма" : "Отклонение")
                                    + "\nТок: " + checkRecord.getCurrent()
                                    + " - " + (checkRecord.isCurrentNormal() ? "Норма" : "Отклонение")
                                    + "\nОбщий статус: " + (checkRecord.getOverallStatus() == EquipmentStatus.NORMAL ? "Оборудование работает в нормальном режиме" : "Обнаружено отклонение")
                                    + "\n------------------------"));
                        }
                        break;
                    case 7:
                        System.out.println("Данные сохранены в файл");
                        break;
                    case 8:
                        System.out.println("Данные из файла загружены");
                        break;
                    case 9:
                        System.out.println("Завершение программы");
                        break;
                    default:
                        System.out.println("Такого пункта нет");
                }

            } catch (InputMismatchException e) {
                System.out.println("Введите целое число");
                scanner.next();
            }
        }
        scanner.close();

        }
        public static void menuConsole (){
        System.out.println("1. Добавить оборудование");
            System.out.println("2. Показать все оборудование");
            System.out.println("3. Найти оборудование по ID");
            System.out.println("4. Выполнить провереку");
            System.out.println("5. Показать оборудование с отклонениями");
            System.out.println("6. Показать историю проверок");
            System.out.println("7. Сохранить данные в файл");
            System.out.println("8. Загрузить данные из файла");
            System.out.println("9. Выход");
        }
}
class Equipment {
      private int id;
   private String name;
   private double temperature;
   private int voltage;
   private int current;
   private LocalDate lastCheck;

    Equipment (int id, String name, double temperature, int voltage, int current, LocalDate lastCheck) {
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