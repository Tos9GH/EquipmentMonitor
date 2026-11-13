import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;
import model.EquipmentStatus;
import model.CheckRecord;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.util.stream.Collectors;
import model.Equipment;
import service.CheckService;
import service.EquipmentService;

public class Main {
    public static void main(String[] args) {
        ArrayList<Equipment> equipmentList = new ArrayList<>();
        EquipmentService equipmentService = new EquipmentService(equipmentList);
        ArrayList<CheckRecord> historyList = new ArrayList<>();
        CheckService checkService = new CheckService(equipmentList, historyList);
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
                                        if (equipmentService.findById(idInput) != null) {
                                            whileStatus = false;
                                            System.out.println("Введен повторяющийся ID");
                                            System.out.println("Введите новый  ID");
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
                            Equipment addNewEquipment = new Equipment(idInput ,nameInput, temperatureInput, voltageInput, currentInput, newLocalDate);
                            if (equipmentService.addEquipment(addNewEquipment)) {
                                System.out.println("Оборудование успешно добавлено");
                            } else {
                                System.out.println("Такой ID уже существует");
                            }
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
                        try {
                            System.out.println("Введите ID");
                            int searchID = scanner.nextInt();

                            Equipment foundEquipment = equipmentService.findById(searchID);

                            if (foundEquipment != null) {
                                    System.out.println("Найдено оборудование:");
                                    System.out.println("ID: " + foundEquipment.getId()
                                            + " | Название: " + foundEquipment.getName());
                            } else {
                                System.out.println("Оборудование не найдено");
                            }

                        } catch (InputMismatchException e) {
                            System.out.println("Введите целое число");
                            scanner.next();
                        }
                        break;
                    case 4:
                                try {
                                System.out.println("Введите ID");
                                int searchStatusID = scanner.nextInt();
                                CheckRecord checkRecord = checkService.checkEquipment(searchStatusID);
                                    if (checkRecord != null) {
                                        if (checkRecord.isTemperatureNormal()) {
                                            System.out.println("Температура: норма");
                                        } else {
                                            System.out.println("Температура: отклонение");
                                        }
                                        if (checkRecord.isVoltageNormal()) {
                                            System.out.println("Напряжение: норма");
                                        } else {
                                            System.out.println("Напряжение: отклонение");
                                        }
                                        if (checkRecord.isCurrentNormal()) {
                                            System.out.println("Ток: норма");
                                        } else {
                                            System.out.println("Ток: отклонение");
                                        }
                                        if (checkRecord.getOverallStatus() == EquipmentStatus.NORMAL) {
                                            System.out.println("Общий статус: оборудование в норме");
                                        } else {
                                            System.out.println("Общий статус: обнаружено отклонение");
                                        }
                                    } else {
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
                        String equipmentToString = equipmentList.stream()
                               .map(equipment -> equipment.getId() + ";" + equipment.getName() + ";" + equipment.getTemperature()
                               + ";" + equipment.getVoltage() + ";" +  equipment.getCurrent() + ";" + equipment.getLastCheck())
                               .collect(Collectors.joining("\n"));
                        String historyToString = historyList.stream()
                                        .map( checkRecord -> checkRecord.getEquipmentId() + ";" + checkRecord.getEquipmentName() + ";" + checkRecord.getCheckDateTime() + ";"
                                        + checkRecord.getTemperature() + ";" + checkRecord.getVoltage() + ";" + checkRecord.getCurrent() + ";" + checkRecord.isTemperatureNormal()
                                                + ";" + checkRecord.isVoltageNormal() + ";" + checkRecord.isCurrentNormal() + ";" + checkRecord.getOverallStatus())
                                                .collect(Collectors.joining("\n"));
                        Path equipmentPath = Path.of("data", "equipment.txt");
                        Path historyPath = Path.of("data", "history.txt");
                        try {
                            Files.writeString(equipmentPath, equipmentToString);
                            System.out.println("Данные сохранены в файл");
                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                            System.out.println("Данные не внесены в файл");
                        }
                        try {
                            Files.writeString(historyPath, historyToString);
                            System.out.println("История проверки сохранена в файл");
                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                            System.out.println("История проверорк не внесена в файл");
                        }
                        break;
                    case 8:
                        Path equipmentReadPath = Path.of("data", "equipment.txt");
                        try {
                            List<String> equipmentRead = Files.readAllLines(equipmentReadPath);
                            System.out.println("Количество записей: " + equipmentRead.size());
                            ArrayList<Equipment> loadedEquipment = new ArrayList<>();
                            boolean validData = true;
                            try {
                                for (String line : equipmentRead) {
                                    String[] splitLine = line.split(";", -1);
                                    if (splitLine.length != 6) {
                                        System.out.println("Некорретная строка в файле оборудования");
                                        validData = false;
                                        break;
                                    }
                                    int id;
                                    String name;
                                    double temperature;
                                    int voltage;
                                    int current;
                                    LocalDate lastCheck;
                                    id = Integer.parseInt(splitLine[0]);
                                    name = splitLine[1];
                                    temperature = Double.parseDouble(splitLine[2]);
                                    voltage = Integer.parseInt(splitLine[3]);
                                    current = Integer.parseInt(splitLine[4]);
                                    lastCheck = LocalDate.parse(splitLine[5]);
                                    loadedEquipment.add(new Equipment(id, name, temperature, voltage, current, lastCheck));
                                }
                                if (validData) {
                                    equipmentList.clear();
                                    equipmentList.addAll(loadedEquipment);
                                }

                            } catch (IllegalArgumentException | DateTimeParseException e) {
                                System.out.println("Ошибка формата данных оборудования. Проверьте файл equipment.txt");
                            }
                            System.out.println("Загружено оборудования: " + equipmentList.size());
                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }
                        Path historyReadPath = Path.of("data", "history.txt");
                        try {
                            List<String> historyRead = Files.readAllLines(historyReadPath);
                            System.out.println("Количество записей: " + historyRead.size());
                            ArrayList<CheckRecord> loadedHistory = new ArrayList<>();
                            boolean validHistory = true;
                            try {
                                for (String lineHistory : historyRead) {
                                    String[] historySplit = lineHistory.split(";", -1);
                                    if (historySplit.length != 10) {
                                        System.out.println("Некорретная строка в файле истории");
                                        validHistory = false;
                                        break;
                                    }
                                    int equipmentId = Integer.parseInt(historySplit[0]);
                                    String equipmentName = historySplit[1];
                                    LocalDateTime checkDateTime = LocalDateTime.parse(historySplit[2]);
                                    double temperature = Double.parseDouble(historySplit[3]);
                                    int voltage = Integer.parseInt(historySplit[4]);
                                    int current = Integer.parseInt(historySplit[5]);
                                    if (!historySplit[6].equals("true") && !historySplit[6].equals("false")) {
                                        System.out.println("Неверно указано состояние проверки температуры");
                                        validHistory = false;
                                        break;
                                    }
                                    boolean temperatureNormal = Boolean.parseBoolean(historySplit[6]);
                                    if (!historySplit[7].equals("true") && !historySplit[7].equals("false")) {
                                        System.out.println("Неверно указано состояние проверки напряжения");
                                        validHistory = false;
                                        break;
                                    }
                                    boolean voltageNormal = Boolean.parseBoolean(historySplit[7]);
                                    if (!historySplit[8].equals("true") && !historySplit[8].equals("false")) {
                                        System.out.println("Неверно указано состояние проверки тока");
                                        validHistory = false;
                                        break;
                                    }
                                    boolean currentNormal = Boolean.parseBoolean(historySplit[8]);
                                        EquipmentStatus overallStatus = EquipmentStatus.valueOf(historySplit[9]);
                                    if (temperatureNormal && currentNormal && voltageNormal) {
                                        if (!EquipmentStatus.NORMAL.equals(overallStatus)) {
                                            System.out.println("Неккоректное значение статуса оборудования");
                                            validHistory = false;
                                            break;
                                        }
                                    } else {
                                        if (!EquipmentStatus.WARNING.equals(overallStatus)) {
                                            System.out.println("Некорректное значение статуса оборудования");
                                            validHistory = false;
                                            break;
                                        }
                                    }
                                        loadedHistory.add(new CheckRecord(equipmentId, equipmentName, checkDateTime, temperature, voltage, current, temperatureNormal, voltageNormal, currentNormal, overallStatus));
                                }
                                if (validHistory) {
                                    historyList.clear();
                                    historyList.addAll(loadedHistory);
                                }
                            } catch (IllegalArgumentException | DateTimeParseException e) {
                                System.out.println("Ошибка формата данных истории. Проверьте файл history.txt");
                            }
                            System.out.println("Загружено записей истории: " + historyList.size());
                        } catch (IOException e) {
                            System.out.println("Не удалось загрузить историю проверок: " + e.getMessage());
                        }
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
