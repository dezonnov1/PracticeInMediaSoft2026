package com.dezonnov1;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        //
        task1();


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

}