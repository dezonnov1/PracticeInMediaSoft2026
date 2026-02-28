package com.dezonnov1;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        System.out.println("===Task1===");
        task1();
        System.out.println("===Task2===");
        task2();

    }


    public static void task1(){
        Random random = new Random();
        // 50 случайных машин (от 2000 до 2025).
        int[] years = random.ints(50,2000,2025+1)
                .toArray();
        System.out.println("Все 50 машин:\n"+Arrays.toString(years));

        // Выведите только машины, выпущенные после 2015 года.
        int[] yearsAfter2015 =
                Arrays.stream(years)
                .filter(value -> value > 2015)
                .toArray();
        System.out.println("Выпущенные после 2015 года:\n"+Arrays.toString(yearsAfter2015));

        // Средний возраст авто
        double avgAge = LocalDate.now().getYear() - (double)Arrays.stream(years).sum() / years.length;
        System.out.println("Средний возраст авто:\n"+avgAge);
    }
    public static void task2(){
        // Список с названиями моделей машин
        String[] carMas = {
                "Toyota Camry",
                "Honda Civic",
                "Hyundai Tucson",
                "Ford Mustang",
                "Ford Mustang",
                "Tesla model X",
                "Ford Mustang",
                "BMW M5 E34",
                "Mercedes-Benz E320 C-Class",
                "Audi A4",
                "Audi A4",
                "Tesla Model S",
                "Tesla Model S",
                "Nissan Altima",
                "Hyundai Tucson",
                "Kia RIO",
                "Hyundai Tucson",
                "Kia RIO",
                "Kia RIO"
        };

        // Удаление дубликатов и отсортировка моделей в обратном алфавитном порядке

        Set<String> carSetTeslaLess = Arrays.stream(carMas)
                .distinct()
                .map(value -> value.contains("Tesla") ? "ELECTRO_CAR" : value)// Проверка на "Tesla" и замена "ELECTRO_CAR"
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toCollection(LinkedHashSet::new));
        // Вывод результата
        System.out.println("Результат замены \"Tesla\" на \"ELECTRO_CAR\" :\n"+carSetTeslaLess);
    }
}