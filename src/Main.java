import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Main {
    public static void main(String[] args) {
        ArrayList<Equipment> equipmentList = new ArrayList<>();
        equipmentList.add(new Equipment(1, "Насос", 45.1, 220, 10, LocalDate.of(2026,10,5)));
        equipmentList.add(new Equipment(2, "Трансформатор", 50, 210,15, LocalDate.of(2026, 9, 15)));
        System.out.println(equipmentList.size());
        Scanner scanner = new Scanner(System.in);
        int numMenu = 0;
        while (numMenu != 9) {
            try {
                menuConsole();
                System.out.println("Выберете необходимое действие:");
                numMenu = scanner.nextInt();
                switch (numMenu) {
                    case 1:
                        System.out.println("Добавление оборудования");
                        break;
                    case 2:
                        System.out.println("Список оборудования");
                        for (Equipment equipment : equipmentList) {
                            System.out.println("ID: " + equipment.getId()
                                    + " | Название: " + equipment.getName());
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
                        equipmentList.get(0).chekStatus();
                        break;
                    case 5:
                        System.out.println("Оборудование с отклонениями");
                        break;
                    case 6:
                        System.out.println("История проверок");
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
    boolean chekCurrStatus;
    boolean chekVoltStatus;
    boolean chekTempStatus;
    public boolean chekTemperature (double temperature) {
        if (temperature >= 0 && temperature <= 80) {
            System.out.println("Температура: " + temperature + " - норма");
            chekTempStatus = true;
            return chekTempStatus;
        } else {
            System.out.println("Температура: " + temperature + " - отклонение");
            chekTempStatus = false;
            return chekTempStatus;
        }
    }

    public boolean chekVoltage (int voltage) {
        if (voltage >= 210 && voltage <= 230) {
            System.out.println("Напряжение: " + voltage + " - норма");
            chekVoltStatus = true;
            return chekVoltStatus;
        } else {
            System.out.println("Напряжение: " + voltage + " - отклонение");
            chekVoltStatus = false;
            return chekVoltStatus;
        }
    }

    public boolean chekCurrent (int current) {
        if (current >= 0 && current <= 20) {
            System.out.println("Ток: " + current + " - норма");
            chekCurrStatus = true;
            return chekCurrStatus;
        } else {
            System.out.println("Ток: " + current + " - отклонение");
            chekCurrStatus = false;
            return chekCurrStatus;
        }
    }

    public void chekStatus() {
        chekCurrent(current);
        chekTemperature(temperature);
        chekVoltage(voltage);
        if (chekVoltStatus && chekCurrStatus && chekTempStatus) {
           System.out.println("Общий статус: - Оборудование в норме");
        } else {
            System.out.println("Общий статус - ОБНАРУЖЕНО ОТКЛОНЕНИЕ");
        }
    }

}